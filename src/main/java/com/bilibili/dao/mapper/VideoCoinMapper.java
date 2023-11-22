package com.bilibili.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bilibili.dao.domain.VideoCoin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author 
* @description 针对表【t_video_coin(视频投币记录表)】的数据库操作Mapper
* @Entity com.bilibili.dao.domain.VideoCoin
*/
@Mapper
public interface VideoCoinMapper extends BaseMapper<VideoCoin> {

    Long getVideoCoins(@Param("videoId") Long videoId);
}




