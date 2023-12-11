package com.bilibili.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bilibili.dao.domain.TPartnershipRequests;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 下水道的小老鼠
 * @description 针对表【t_partnership_requests(合作请求表)】的数据库操作Mapper
 * @createDate 2023-12-11 11:06:24
 * @Entity com.bilibili.dao.domain.TPartnershipRequests
 */
@Mapper
public interface TPartnershipRequestsMapper
    extends BaseMapper<TPartnershipRequests> {}
