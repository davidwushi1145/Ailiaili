package com.bilibili.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bilibili.dao.domain.Video;
import com.bilibili.dao.domain.VideoCoin;
import com.bilibili.dao.domain.exception.ConditionException;
import com.bilibili.dao.mapper.VideoCoinMapper;
import com.bilibili.service.UserCoinService;
import com.bilibili.service.VideoCoinService;
import com.bilibili.service.VideoService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author
 * @description 针对表【t_video_coin(视频投币记录表)】的数据库操作Service实现
 */
@Service
public class VideoCoinServiceImpl
    extends ServiceImpl<VideoCoinMapper, VideoCoin>
    implements VideoCoinService {

  @Autowired private UserCoinService userCoinService;

  @Autowired private VideoService videoService;

  @Override
  @Transactional
  public void addVideoCions(VideoCoin videoCoin, Long userId) {
    if (videoCoin == null || videoCoin.getVideoId() == null) {
      throw new ConditionException("参数异常！");
    }
    Long videoId = videoCoin.getVideoId();
    Video video = videoService.getById(videoId);
    if (video == null) {
      throw new ConditionException("非法视频！");
    }
    Integer amount = videoCoin.getAmount();
    if (amount != 1 && amount != 2) {
      throw new ConditionException("参数异常！");
    }
    // 查询是否拥有足够硬币
    Long userCoinAmount = userCoinService.getUserCoinAmount(userId);
    if (amount > userCoinAmount) {
      throw new ConditionException("硬币数量不足！");
    }
    // 查询当前用户对视频投了多少硬币
    VideoCoin dbVideoCoin = this.getCoinByVideoIdAndUserId(videoId, userId);
    // 新增视频投币
    if (dbVideoCoin == null) {
      videoCoin.setUserId(userId);
      this.save(videoCoin);
    } else {
      Integer dbAmount = dbVideoCoin.getAmount();
      dbAmount += amount;
      // 更新视频投币
      videoCoin.setUserId(userId);
      videoCoin.setAmount(dbAmount);
      this.updateVideoCion(videoCoin);
    }
    // 更新用户硬币数量
    userCoinService.updateUserCoinAmount(userId, userCoinAmount - amount);
    // Increase the coin count
    video.setCoins(video.getCoins() + amount);
    // Update the video
    videoService.updateById(video);
  }

  @Override
  public VideoCoin getCoinByVideoIdAndUserId(Long videoId, Long userId) {
    QueryWrapper<VideoCoin> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("user_id", userId);
    queryWrapper.eq("video_id", videoId);
    VideoCoin videoCoin = this.getOne(queryWrapper);
    return videoCoin;
  }

  @Override
  public void updateVideoCion(VideoCoin videoCoin) {
    UpdateWrapper<VideoCoin> updateWrapper = new UpdateWrapper<>();
    updateWrapper.eq("user_id", videoCoin.getUserId());
    updateWrapper.eq("video_id", videoCoin.getVideoId());
    this.update(videoCoin, updateWrapper);
  }

  @Override
  public Map<String, Object> getVideoCoins(Long videoId, Long userId) {
    // Get the video
    Video video = videoService.getById(videoId);

    // Get the coin count from the video
    int count = video.getCoins();

    // Check if the user has given coins to the video
    VideoCoin videoCoin = this.getCoinByVideoIdAndUserId(videoId, userId);
    boolean given = videoCoin != null;

    // Return the result
    Map<String, Object> map = new HashMap<>();
    map.put("count", count);
    map.put("given", given);
    return map;
  }
}
