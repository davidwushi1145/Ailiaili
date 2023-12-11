package com.bilibili.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bilibili.dao.domain.TAdPerformance;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 下水道的小老鼠
 * @description 针对表【t_ad_performance(广告性能表)】的数据库操作Mapper
 * @createDate 2023-12-10 16:14:55
 * @Entity com.bilibili.dao.domain.TAdPerformance
 */
@Mapper
public interface TAdPerformanceMapper extends BaseMapper<TAdPerformance> {}
