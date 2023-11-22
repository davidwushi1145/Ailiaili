package com.bilibili.api;

import com.bilibili.dao.domain.JsonResponse;
import com.bilibili.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class FileApi {

    @Autowired
    private FileService fileService;

    //上传文件+秒传
    @PutMapping("/file-slices")
    public JsonResponse<String> uploadFileBySlices(MultipartFile slice,
                                                   String fileMD5,
                                                   Integer sliceNumber,
                                                   Integer totalSliceNumber) throws IOException {
        String filePath = fileService.uploadFileBySlices(slice,fileMD5,sliceNumber,totalSliceNumber);
        return new JsonResponse<>(filePath);
    }

    //删除文件
    @DeleteMapping("/file-delete")
    public JsonResponse<String> deleteFile(String filePath){
        fileService.deleteFile(filePath);
        return new JsonResponse<>("删除成功！");
    }

    @PostMapping("/md5files")
    public JsonResponse<String> getFileMD5(MultipartFile file) throws IOException {
        String fileMD5 = fileService.getFileMD5(file);
        return new JsonResponse<>(fileMD5);
    }


}
