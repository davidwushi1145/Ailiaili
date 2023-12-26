package com.bilibili.api;

import com.bilibili.dao.domain.File;
import com.bilibili.dao.domain.JsonResponse;
import com.bilibili.service.FileService;
import com.bilibili.service.util.MD5Util;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileApi {

  @Autowired private FileService fileService;

  // 上传视频文件+秒传
  @PutMapping("/file-video-slices")
  public JsonResponse<String>
  uploadFileBySlices(MultipartFile slice, String fileMD5, Integer sliceNumber,
                     Integer totalSliceNumber) throws IOException {
    String filePath = fileService.uploadFileBySlices(
        slice, fileMD5, sliceNumber, totalSliceNumber);
    return new JsonResponse<>(filePath);
  }

  // 删除视频文件
  @DeleteMapping("/file-video-delete")
  public JsonResponse<String> deleteFile(String filePath) {
    fileService.deleteVideoFile(filePath);
    return new JsonResponse<>("删除成功！");
  }

  // 上传广告文件
  @PutMapping("/file-advertisement")
  public JsonResponse<Long> uploadAdvertisementFile(MultipartFile file,
                                                    String fileMD5)
      throws IOException {
    Long contentId = fileService.uploadAdvertisementFile(file, fileMD5);
    return new JsonResponse<>(contentId);
  }

  // 获取广告文件
  @GetMapping("file-advertisement")
  public JsonResponse<File> getFileAdvertisement(Long id) {
    File file = fileService.getById(id);
    return new JsonResponse<>(file);
  }

  // 删除广告文件
  @DeleteMapping("/file-advertisement-delete")
  public JsonResponse<String> deleteAdvertisementFile(String filePath) {
    fileService.deleteAdvertisementFile(filePath);
    return new JsonResponse<>("删除成功！");
  }

  // 上传视频封面
  @PutMapping("/file-thumbnail")
  public JsonResponse<String> uploadThumbnailFile(MultipartFile file,
                                                  String fileMD5)
      throws IOException {
    String path = fileService.uploadThumbnailFile(file, fileMD5);
    return new JsonResponse<>(path);
  }

  @PostMapping("/md5files")
  public JsonResponse<String> getFileMD5(MultipartFile file)
      throws IOException {
    String fileMD5 = fileService.getFileMD5(file);
    return new JsonResponse<>(fileMD5);
  }
}
