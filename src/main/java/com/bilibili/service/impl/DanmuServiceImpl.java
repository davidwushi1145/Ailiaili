package com.bilibili.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bilibili.dao.domain.Danmu;
import com.bilibili.dao.domain.PageResult;
import com.bilibili.dao.domain.exception.ConditionException;
import com.bilibili.dao.mapper.DanmuMapper;
import com.bilibili.service.DanmuService;
import com.bilibili.service.VideoService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author
 * @description 针对表【t_danmu(弹幕表)】的数据库操作Service实现
 */
@Service
public class DanmuServiceImpl
    extends ServiceImpl<DanmuMapper, Danmu> implements DanmuService {

  public static final String DANMU_KEY = "now-danmu-video-";
  @Autowired private VideoService videoService;

  @Autowired private RedisTemplate<String, String> redisTemplate;

  @Override
  public List<Danmu> getDanmus(Map<String, Object> map) throws ParseException {
    String videoId = String.valueOf(map.get("videoId"));
    String startDate = (String)map.get("startDate");
    String endDate = (String)map.get("endDate");
    if (StringUtils.isBlank(videoId)) {
      throw new ConditionException("参数异常！");
    }
    if (videoService.getById(videoId) == null) {
      throw new ConditionException("非法视频！");
    }
    String key = DANMU_KEY + videoId;
    String value = redisTemplate.opsForValue().get(key);
    List<Danmu> list;
    // 如果redis里有最近查询过的弹幕，那就从redis里取
    if (StringUtils.isNotBlank(value)) {
      list = JSONArray.parseArray(value, Danmu.class);
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      List<Danmu> childList = new ArrayList<>();
      for (Danmu danmu : list) {
        if (this.inTime(startDate, endDate, danmu.getCreateTime(), sdf)) {
          childList.add(danmu);
        }
      }
      list = childList;
    } else {
      QueryWrapper<Danmu> queryWrapper = new QueryWrapper<>();
      queryWrapper.eq("video_id", videoId);
      if (StringUtils.isNotBlank(startDate)) {
        queryWrapper.ge("create_time", startDate);
      }
      if (StringUtils.isNotBlank(endDate)) {
        queryWrapper.le("create_time", endDate);
      }
      list = this.list(queryWrapper);
      redisTemplate.opsForValue().set(key, JSONObject.toJSONString(list));
    }
    return list;
  }

  @Override
  public void addDanmuToRedis(Danmu danmu) {
    String key = "new-danmu-video-" + danmu.getVideoId();
    String value = redisTemplate.opsForValue().get(key);
    List<Danmu> list;
    if (StringUtils.isNotBlank(value)) {
      // 解析value为Danmu列表
      list = JSONArray.parseArray(value, Danmu.class);
    } else {
      // 初始化空列表
      list = new ArrayList<>();
    }
    list.add(danmu);
    redisTemplate.opsForValue().set(key, JSONObject.toJSONString(list));

    // 把新发送弹幕放到old-danmu-video
    key = DANMU_KEY + danmu.getVideoId();
    value = redisTemplate.opsForValue().get(key);
    List<Danmu> nowList = new ArrayList<>();
    if (StringUtils.isNotBlank(value)) {
      nowList = JSONArray.parseArray(value, Danmu.class);
      nowList.add(danmu);
      redisTemplate.opsForValue().set(key, JSONObject.toJSONString(nowList));
    } else {
      nowList.add(danmu);
      redisTemplate.opsForValue().set(key, JSONObject.toJSONString(nowList));
    }
  }

  @Override
  @Async
  public void asyncAddDanmu(Danmu danmu) {
    this.save(danmu);
  }

  @Override
  public PageResult<Danmu> getUnpassDanmus(JSONObject params) {
    Integer page = params.getInteger("page");
    Integer size = params.getInteger("size");
    Integer videoId = params.getInteger("videoId");
    QueryWrapper<Danmu> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("pass", 0);
    queryWrapper.eq("video_id", videoId);
    queryWrapper.orderByDesc("id");
    int count = (int)this.count(queryWrapper);
    List<Danmu> list = new ArrayList<>();
    PageResult<Danmu> result = new PageResult<>(count, list);
    if (count > 0) {
      IPage<Danmu> iPage =
          new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(
              (long)(page - 1) * size, size);
      iPage = this.page(iPage, queryWrapper);
      list = iPage.getRecords();
      result.setTotal(count);
      result.setList(list);
    }
    return result;
  }

  @Override
  public void passDanmu(Long danmuId) {
    Danmu danmu = this.getById(danmuId);
    danmu.setPass(1);
    this.updateById(danmu);
  }

  public boolean inTime(String startTime, String endTime, Date createTime,
                        SimpleDateFormat sdf) throws ParseException {
    if (StringUtils.isNotBlank(startTime) &&
        sdf.parse(startTime).after(createTime)) {
      return false;
    }
    if (StringUtils.isNotBlank(endTime) &&
        sdf.parse(endTime).before(createTime)) {
      return false;
    }
    return true;
  }
}
