package com.bilibili.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bilibili.dao.domain.File;
import com.bilibili.dao.domain.exception.ConditionException;
import com.bilibili.dao.mapper.FileMapper;
import com.bilibili.service.FileService;
import com.bilibili.service.util.COSUtil;
import com.bilibili.service.util.FastDFSUtil;
import com.bilibili.service.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author
 * @description 针对表【t_file(上传文件信息表)】的数据库操作Service实现
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File>
        implements FileService {

    @Autowired
    private FastDFSUtil fastDFSUtil;

    @Autowired
    private COSUtil cosUtil;

    private final String VIDEO_FILE_NAME = "bilibili/";

    private final String ADVERTISEMENT_FILE_NAME = "bilibili/advertisement";

    private final String THUMBNAIL_FILE_NAME = "bilibili/thumbnail";

    //小文件上传
    @Override
    public String uploadFileBySlices(MultipartFile slice, String fileMD5, Integer sliceNumber, Integer totalSliceNumber) throws IOException {
        if (StringUtils.isBlank(fileMD5)) {
            throw new ConditionException("参数异常！");
        }

        File dbFileMD5 = this.getFileByMD5(MD5Util.getFileMD5(slice));
        //秒传
        if (dbFileMD5 != null) {
            return dbFileMD5.getUrl();
        }

        String filePath;

        if (totalSliceNumber == 1) {
            //COS对象存储
            filePath = cosUtil.upload(VIDEO_FILE_NAME + fileMD5 + "." + cosUtil.getFileType(slice), slice);
        } else {
            filePath = cosUtil.uploadFileBySlices(slice, fileMD5, sliceNumber, totalSliceNumber);
        }

        if (StringUtils.isNotBlank(filePath)) {
            dbFileMD5 = new File();
            dbFileMD5.setUrl(filePath);
            dbFileMD5.setMd5(MD5Util.getFileMD5(slice));
            dbFileMD5.setType(cosUtil.getFileType(slice));
            this.save(dbFileMD5);
        }
        return filePath;
    }

    @Override
    public Long uploadAdvertisementFile(MultipartFile file, String fileMD5) throws IOException {
        if (StringUtils.isBlank(fileMD5)) {
            throw new ConditionException("参数异常！");
        }
        File dbFileMD5 = this.getFileByMD5(MD5Util.getFileMD5(file));
        //秒传
        if (dbFileMD5 != null) {
            return dbFileMD5.getId();
        }

        String filePath;
        //COS对象存储
        filePath = cosUtil.upload(ADVERTISEMENT_FILE_NAME + fileMD5 + "." + cosUtil.getFileType(file), file);

        if (StringUtils.isNotBlank(filePath)) {
            dbFileMD5 = new File();
            dbFileMD5.setUrl(filePath);
            dbFileMD5.setMd5(MD5Util.getFileMD5(file));
            dbFileMD5.setType(cosUtil.getFileType(file));
            this.save(dbFileMD5);
        }
        return dbFileMD5.getId();
    }

    @Override
    public String uploadThumbnailFile(MultipartFile file, String fileMD5) throws IOException {
        if (StringUtils.isBlank(fileMD5)) {
            throw new ConditionException("参数异常！");
        }
        File dbFileMD5 = this.getFileByMD5(MD5Util.getFileMD5(file));
        //秒传
        if (dbFileMD5 != null) {
            return dbFileMD5.getUrl();
        }

        String filePath;
        //COS对象存储
        filePath = cosUtil.upload(THUMBNAIL_FILE_NAME + fileMD5 + "." + cosUtil.getFileType(file), file);

        if (StringUtils.isNotBlank(filePath)) {
            dbFileMD5 = new File();
            dbFileMD5.setUrl(filePath);
            dbFileMD5.setMd5(MD5Util.getFileMD5(file));
            dbFileMD5.setType(cosUtil.getFileType(file));
            this.save(dbFileMD5);
        }
        return filePath;
    }

    @Override
    public void deleteVideoFile(String filePath) {
        QueryWrapper<File> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("url", filePath);
        File file = this.getOne(queryWrapper);
        this.remove(queryWrapper);
        //fastDFSUtil.deleteFile(filePath);
        cosUtil.deleteVideoFile(file.getMd5(), file.getType());
    }

    @Override
    public void deleteAdvertisementFile(String filePath) {
        QueryWrapper<File> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("url", filePath);
        File file = this.getOne(queryWrapper);
        this.remove(queryWrapper);
        cosUtil.deleteAdvertisementFile(file.getMd5(), file.getType());
    }

    public File getFileByMD5(String fileMD5) {
        QueryWrapper<File> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5", fileMD5);
        return this.getOne(queryWrapper);
    }

    @Override
    public String getFileMD5(MultipartFile file) throws IOException {
        return MD5Util.getFileMD5(file);
    }
}




