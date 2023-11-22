package com.bilibili.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bilibili.dao.domain.VideoCoin;

import java.util.Map;

/**
* @author 
* @description 针对表【t_video_coin(视频投币记录表)】的数据库操作Service
*/
public interface VideoCoinService extends IService<VideoCoin> {

    void addVideoCions(VideoCoin videoCoin, Long userId);

    VideoCoin getCoinByVideoIdAndUserId(Long videoId, Long userId);

    void updateVideoCion(VideoCoin videoCoin);

    Map<String, Object> getVideoCoins(Long videoId, Long userId);
}
