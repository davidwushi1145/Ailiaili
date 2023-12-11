package com.bilibili.service.impl;

import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bilibili.dao.domain.TPartnershipRequests;
import com.bilibili.service.TPartnershipRequestsService;
import com.bilibili.dao.mapper.TPartnershipRequestsMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 下水道的小老鼠
 * @description 针对表【t_partnership_requests(合作请求表)】的数据库操作Service实现
 * @createDate 2023-12-11 11:06:25
 */
@Service
public class TPartnershipRequestsServiceImpl extends ServiceImpl<TPartnershipRequestsMapper, TPartnershipRequests>
        implements TPartnershipRequestsService {

    @Autowired
    private TPartnershipRequestsMapper tPartnershipRequestsMapper;

    @Override
    public void createPartnershipRequest(Long advertiserId, Long contentCreatorId, String details, int expectedDuration, BigDecimal expectedCompensation) {
        TPartnershipRequests tPartnershipRequests = new TPartnershipRequests();
        tPartnershipRequests.setAdvertiserId(advertiserId);
        tPartnershipRequests.setContentCreatorId(contentCreatorId);
        tPartnershipRequests.setRequestTime(new Date());
        tPartnershipRequests.setStatus("pending");
        tPartnershipRequests.setDetails(details);
        tPartnershipRequests.setExpectedDuration(expectedDuration);
        tPartnershipRequests.setExpectedCompensation(expectedCompensation);
        this.save(tPartnershipRequests);
    }

    @Override
    public void acceptPartnershipRequest(Long requestId) {
        QueryWrapper<TPartnershipRequests> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", requestId);
        TPartnershipRequests tPartnershipRequests = tPartnershipRequestsMapper.selectById(requestId);
        tPartnershipRequests.setStatus("accepted");
        tPartnershipRequestsMapper.update(tPartnershipRequests, queryWrapper);
    }

    @Override
    public List<TPartnershipRequests> getTPartnershipRequests(Long userId, String status) {
        QueryWrapper<TPartnershipRequests> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("content_creator_id", userId);
        if (StringUtils.isNotEmpty(status)) {
            queryWrapper.eq("status", status);
        }
        return tPartnershipRequestsMapper.selectList(queryWrapper);
    }

    @Override
    public void rejectPartnershipRequest(Long requestId) {
        QueryWrapper<TPartnershipRequests> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", requestId);
        TPartnershipRequests tPartnershipRequests = tPartnershipRequestsMapper.selectById(requestId);
        tPartnershipRequests.setStatus("rejected");
        tPartnershipRequestsMapper.update(tPartnershipRequests, queryWrapper);
    }
}




