package com.bilibili.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bilibili.dao.domain.File;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 
* @description 针对表【t_file(上传文件信息表)】的数据库操作Mapper
* @Entity com.bilibili.dao.domain.File
*/
@Mapper
public interface FileMapper extends BaseMapper<File> {

}




