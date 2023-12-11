package com.bilibili.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bilibili.dao.domain.File;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author
 * @description 针对表【t_file(上传文件信息表)】的数据库操作Service
 */
public interface FileService extends IService<File> {

  String uploadFileBySlices(MultipartFile slice, String fileMD5,
                            Integer sliceNumber, Integer totalSliceNumber)
      throws IOException;

  Long uploadAdvertisementFile(MultipartFile slice, String fileMD5)
      throws IOException;

  String uploadThumbnailFile(MultipartFile file, String fileMD5)
      throws IOException;

  void deleteVideoFile(String filePath);

  String getFileMD5(MultipartFile file) throws IOException;

  void deleteAdvertisementFile(String filePath);
}
