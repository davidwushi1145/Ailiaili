package com.bilibili.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bilibili.dao.domain.TPartnershipRequests;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author
 * @description 针对表【t_partnership_requests(合作请求表)】的数据库操作Service
 * @createDate 2023-12-11 11:06:25
 */
public interface TPartnershipRequestsService
    extends IService<TPartnershipRequests> {

  void createPartnershipRequest(Long advertiserId, Long contentCreatorId,
                                String details, int expectedDuration,
                                BigDecimal expectedCompensation);

  void acceptPartnershipRequest(Long requestId);

  List<TPartnershipRequests> getTPartnershipRequests(Long userId,
                                                     String status);

  void rejectPartnershipRequest(Long requestId);

  TPartnershipRequests getByRequestId(Long requestId);
}
