package com.bilibili.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bilibili.dao.domain.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
* @author 
* @description 针对表【t_file(上传文件信息表)】的数据库操作Service
*/
public interface FileService extends IService<File> {

    String uploadFileBySlices(MultipartFile slice, String fileMD5, Integer sliceNumber, Integer totalSliceNumber) throws IOException;

    void deleteFile(String filePath);

    String getFileMD5(MultipartFile file) throws IOException;
}
