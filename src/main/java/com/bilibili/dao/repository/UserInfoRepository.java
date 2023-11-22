package com.bilibili.dao.repository;

import com.bilibili.dao.domain.UserInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends ElasticsearchRepository<UserInfo,Long> {

}
