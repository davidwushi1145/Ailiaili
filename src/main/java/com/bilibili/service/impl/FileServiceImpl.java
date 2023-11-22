package com.bilibili.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bilibili.dao.domain.File;
import com.bilibili.dao.mapper.FileMapper;
import com.bilibili.service.FileService;
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

    @Override
    public String uploadFileBySlices(MultipartFile slice, String fileMD5, Integer sliceNumber, Integer totalSliceNumber) throws IOException {
        File dbFileMD5 = this.getFileByMD5(fileMD5);
        //秒传
        if(dbFileMD5!=null){
            return dbFileMD5.getUrl();
        }
        String filePath = fastDFSUtil.uploadFileBySlices(slice, fileMD5, sliceNumber, totalSliceNumber);
        if(StringUtils.isNotBlank(filePath)){
            dbFileMD5 = new File();
            dbFileMD5.setUrl(filePath);
            dbFileMD5.setMd5(fileMD5);
            dbFileMD5.setType(fastDFSUtil.getFileType(slice));
            this.save(dbFileMD5);
        }
        return filePath;
    }

    @Override
    public void deleteFile(String filePath) {
        QueryWrapper<File> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("url",filePath);
        this.remove(queryWrapper);
        fastDFSUtil.deleteFile(filePath);
    }

    public File getFileByMD5(String fileMD5){
        QueryWrapper<File> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5",fileMD5);
        File file = this.getOne(queryWrapper);
        return file;
    }

    @Override
    public String getFileMD5(MultipartFile file) throws IOException {
        return MD5Util.getFileMD5(file);
    }
}




