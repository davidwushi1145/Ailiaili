package com.bilibili.service.util;

import com.bilibili.dao.domain.exception.ConditionException;
import com.bilibili.service.config.CosClientConfig;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.*;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.TransferManagerConfiguration;
import com.qcloud.cos.transfer.Upload;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class COSUtil {

    @Resource
    private COSClient cosClient;

    @Resource
    private CosClientConfig cosClientConfig;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    public static final String PATH_KEY = "path-key:";
    public static final String UPLOADED_SIZE_KEY = "uploaded-size-key:";
    public static final String UPLOADED_NUMBER_KEY = "uploaded-number-key:";
    public static final String UPLOAD_ID_KEY = "uploaded-id-key";

    public static final int SLICE_SIZE = 1024 * 1024 * 2;

    // 创建 TransferManager 实例，这个实例用来后续调用高级接口
    public TransferManager createTransferManager() {

        // 自定义线程池大小，建议在客户端与 COS 网络充足（例如使用腾讯云的 CVM，同地域上传 COS）的情况下，设置成16或32即可，可较充分的利用网络资源
        // 对于使用公网传输且网络带宽质量不高的情况，建议减小该值，避免因网速过慢，造成请求超时。
        ExecutorService threadPool = Executors.newFixedThreadPool(16);

        // 传入一个 threadpool, 若不传入线程池，默认 TransferManager 中会生成一个单线程的线程池。
        TransferManager transferManager = new TransferManager(cosClient);

        // 设置高级接口的配置项
        // 分块上传阈值和分块大小分别为 5MB 和 1MB
        TransferManagerConfiguration transferManagerConfiguration = new TransferManagerConfiguration();
        transferManagerConfiguration.setMultipartUploadThreshold(5 * 1024 * 1024);
        transferManagerConfiguration.setMinimumUploadPartSize(1 * 1024 * 1024);
        transferManager.setConfiguration(transferManagerConfiguration);

        return transferManager;
    }

    public void shutdownTransferManager(TransferManager transferManager) {
        // 指定参数为 true, 则同时会关闭 transferManager 内部的 COSClient 实例。
        // 指定参数为 false, 则不会关闭 transferManager 内部的 COSClient 实例。
        transferManager.shutdownNow(true);
    }


    public String upload(String keyName, MultipartFile multipartFile) {

        //file里面填写本地图片的位置 我这里是相对项目的位置，在项目下有src/test/demo.jpg这张图片
        cosClient.getClientConfig().setHttpProtocol(HttpProtocol.https);

        InputStream inputStream = null;
        try {
            inputStream = multipartFile.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());

        PutObjectRequest putObjectRequest = new PutObjectRequest(cosClientConfig.getBucket(), keyName, inputStream, objectMetadata);

        TransferManager transferManager = this.createTransferManager();

        try {
            // 高级接口会返回一个异步结果Upload
            // 可同步地调用 waitForUploadResult 方法等待上传完成，成功返回 UploadResult, 失败抛出异常
            Upload upload = transferManager.upload(putObjectRequest);
            UploadResult uploadResult = upload.waitForUploadResult();
        } catch (CosClientException | InterruptedException e) {
            e.printStackTrace();
        }

        try {
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return String.valueOf(this.getUrl(keyName));
    }

    //获取文件类型
    public String getFileType(MultipartFile file) {
        if (file == null) {
            throw new ConditionException("非法文件！");
        }
        String fileName = file.getOriginalFilename();
        int index = 0;
        if (fileName != null) {
            index = fileName.lastIndexOf(".");
        }
        String fileType = null;
        if (fileName != null) {
            fileType = fileName.substring(index + 1);
        }
        return fileType;
    }

    //根据对象键来获取链接
    public URL getUrl(String keyName) {

        // 设置生成的 url 的请求协议, http 或者 https
        // 5.6.54 及更高版本，默认使用了 https
        cosClient.getClientConfig().setHttpProtocol(HttpProtocol.https);

        return cosClient.getObjectUrl(cosClientConfig.getBucket(), keyName);
    }

    public void deleteVideoFile(String md5, String type) {
        DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(cosClientConfig.getBucket());
        // 设置要删除的key列表, 最多一次删除1000个
        ArrayList<DeleteObjectsRequest.KeyVersion> keyList = new ArrayList<>();

        keyList.add(new DeleteObjectsRequest.KeyVersion("bilibili/" + md5 + "." + type));
        keyList.add(new DeleteObjectsRequest.KeyVersion("bilibili/流畅" + md5 + "." + type));
        keyList.add(new DeleteObjectsRequest.KeyVersion("bilibili/高清" + md5 + "." + type));
        keyList.add(new DeleteObjectsRequest.KeyVersion("bilibili/标清" + md5 + "." + type));
        keyList.add(new DeleteObjectsRequest.KeyVersion("bilibili/超清" + md5 + "." + type));

        deleteObjectsRequest.setKeys(keyList);

        DeleteObjectsResult deleteObjectsResult = cosClient.deleteObjects(deleteObjectsRequest);
        List<DeleteObjectsResult.DeletedObject> deleteObjectResultArray = deleteObjectsResult.getDeletedObjects();

    }

    public void deleteAdvertisementFile(String md5, String type) {
        cosClient.deleteObject(cosClientConfig.getBucket(), "bilibili/advertisement" + md5 + "." + type);
    }

    public String initiateMultipartUpload(String key) {
        InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(cosClientConfig.getBucket(), key);

        // 分块上传的过程中，仅能通过初始化分块指定文件上传之后的 metadata
        // 需要的头部可以在这里指定
        ObjectMetadata objectMetadata = new ObjectMetadata();
        request.setObjectMetadata(objectMetadata);

        InitiateMultipartUploadResult initResult = cosClient.initiateMultipartUpload(request);
        return initResult.getUploadId();
    }

    public PartETag uploadPart(MultipartFile file, String uploadId, Integer slicerNumber, String key) {
        UploadPartRequest uploadPartRequest = new UploadPartRequest();
        uploadPartRequest.setBucketName(cosClientConfig.getBucket());
        uploadPartRequest.setKey(key);
        uploadPartRequest.setUploadId(uploadId);
        try {
            uploadPartRequest.setInputStream(file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 设置分块的长度 20MB
        uploadPartRequest.setPartSize(20 * 1024 * 1024);
        // 设置要上传的分块编号，从 1 开始
        uploadPartRequest.setPartNumber(slicerNumber);

        UploadPartResult uploadPartResult = cosClient.uploadPart(uploadPartRequest);
        return uploadPartResult.getPartETag();
    }

    public URL completeMultipartUpload(String key, String uploadIdKey, String uploadedNumberKey) {
        ObjectMapper objectMapper = new ObjectMapper();
        // 保存已上传的分片信息，实际情况里，这里的内容是从上传分块接口中获取到的
        List<PartETag> partETags = new LinkedList<>();

        int number = Integer.parseInt(Objects.requireNonNull(redisTemplate.opsForValue().get(uploadedNumberKey)));
        for (int i = 1; i <= number; i++) {
            try {
                PartETag partETag = objectMapper.readValue(redisTemplate.opsForValue().get(uploadIdKey + String.valueOf(i)), PartETag.class);
                partETags.add(partETag);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        String uploadId = redisTemplate.opsForValue().get(uploadIdKey);

        // 分片上传结束后，调用 complete 完成分片上传
        CompleteMultipartUploadRequest completeMultipartUploadRequest =
                new CompleteMultipartUploadRequest(cosClientConfig.getBucket(), key, uploadId, partETags);
        cosClient.completeMultipartUpload(completeMultipartUploadRequest);
        return this.getUrl(key);
    }

    public String uploadFileBySlices(MultipartFile file, String fileMD5, Integer sliceNumber, Integer totalSliceNumber) throws IOException {
        if (file == null || sliceNumber == null || totalSliceNumber == null) {
            throw new ConditionException("参数异常！");
        }

        ObjectMapper objectMapper = new ObjectMapper();

        String uploadedSizeKey = UPLOADED_SIZE_KEY + fileMD5;
        String uploadedNumberKey = UPLOADED_NUMBER_KEY + fileMD5;
        String uploadIdKey = UPLOAD_ID_KEY + fileMD5;

        String uploadedSizeStr = redisTemplate.opsForValue().get(uploadedSizeKey);
        Long uploadedSize = 0L;

        if (StringUtils.isNotBlank(uploadedSizeStr)) {
            uploadedSize = Long.valueOf(uploadedSizeStr);
        }
        String fileType = this.getFileType(file);

        String key = "bilibili/" + fileMD5 + "." + fileType;

        if (sliceNumber == 1) { //上传第一个分片
            String uploadId = this.initiateMultipartUpload(key);  // 初始化分片上传
            if (StringUtils.isBlank(uploadId)) {
                throw new ConditionException("上传失败！");
            }

            redisTemplate.opsForValue().set(uploadIdKey, uploadId);

            PartETag partETag = this.uploadPart(file, uploadId, sliceNumber, key);  //

            redisTemplate.opsForValue().increment(uploadedNumberKey, 1);
            redisTemplate.opsForValue().set(uploadIdKey + redisTemplate.opsForValue().get(uploadedNumberKey), objectMapper.writeValueAsString(partETag));

        } else {
            String uploadId = redisTemplate.opsForValue().get(uploadIdKey);
            if (StringUtils.isBlank(uploadId)) {
                throw new ConditionException("上传失败");
            }

            PartETag partETag = this.uploadPart(file, uploadId, sliceNumber, key);  // 上传分片

            redisTemplate.opsForValue().increment(uploadedNumberKey, 1);
            redisTemplate.opsForValue().set(uploadIdKey + redisTemplate.opsForValue().get(uploadedNumberKey), objectMapper.writeValueAsString(partETag));
        }

        //修改历史上传文件大小
        uploadedSize += file.getSize();
        redisTemplate.opsForValue().set(uploadedSizeKey, String.valueOf(uploadedSize));

        //上传完毕，清空redis值
        String uploadedNumberStr = redisTemplate.opsForValue().get(uploadedNumberKey);
        Integer uploadedNumber = null;
        if (uploadedNumberStr != null) {
            uploadedNumber = Integer.valueOf(uploadedNumberStr);
        }

        String resultPath = "";
        if (uploadedNumber != null && uploadedNumber.equals(totalSliceNumber)) {
            String uploadId = redisTemplate.opsForValue().get(uploadIdKey);
            resultPath = String.valueOf(this.completeMultipartUpload(uploadId, uploadIdKey, uploadedNumberKey));  // 完成分片上传

            int number = Integer.parseInt(Objects.requireNonNull(redisTemplate.opsForValue().get(uploadedNumberKey)));
            for (int i = 1; i <= number; i++) {
                redisTemplate.delete(uploadIdKey + String.valueOf(i));
            }

            List<String> keyList = Arrays.asList(uploadedNumberKey, uploadIdKey, uploadedSizeKey);
            redisTemplate.delete(keyList);
        }
        return resultPath;
    }

}
