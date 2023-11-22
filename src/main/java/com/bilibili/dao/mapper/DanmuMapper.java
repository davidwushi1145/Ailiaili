package com.bilibili.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bilibili.dao.domain.Danmu;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 
* @description 针对表【t_danmu(弹幕表)】的数据库操作Mapper
* @Entity com.bilibili.dao.domain.Danmu
*/
@Mapper
public interface DanmuMapper extends BaseMapper<Danmu> {

}




