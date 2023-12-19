package com.bilibili.dao.repository;

import com.bilibili.dao.domain.TAdPerformance;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface AdPerformanceRepository extends ElasticsearchRepository<TAdPerformance,Long> {
    TAdPerformance findByAdId(Long adId);
}
