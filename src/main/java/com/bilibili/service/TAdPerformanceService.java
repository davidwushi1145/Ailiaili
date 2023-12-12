package com.bilibili.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bilibili.dao.domain.TAdPerformance;

/**
 * @author
 * @description 针对表【t_ad_performance(广告性能表)】的数据库操作Service
 * @createDate 2023-12-10 16:14:55
 */
public interface TAdPerformanceService extends IService<TAdPerformance> {

  TAdPerformance getByAdId(Long adId);
}
