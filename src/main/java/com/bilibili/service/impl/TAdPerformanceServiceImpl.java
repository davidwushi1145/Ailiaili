package com.bilibili.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bilibili.dao.domain.TAdPerformance;
import com.bilibili.dao.mapper.TAdPerformanceMapper;
import com.bilibili.service.TAdPerformanceService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 下水道的小老鼠
 * @description 针对表【t_ad_performance(广告性能表)】的数据库操作Service实现
 * @createDate 2023-12-10 16:14:55
 */
@Service
public class TAdPerformanceServiceImpl
    extends ServiceImpl<TAdPerformanceMapper, TAdPerformance>
    implements TAdPerformanceService {

  @Autowired private TAdPerformanceMapper tAdPerformanceMapper;

  @Override
  public TAdPerformance getByAdId(Long adId) {
    QueryWrapper<TAdPerformance> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("ad_id", adId);
    return tAdPerformanceMapper.selectOne(queryWrapper);
  }
}
