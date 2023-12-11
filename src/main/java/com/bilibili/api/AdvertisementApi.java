package com.bilibili.api;

import java.util.Calendar;
import java.util.Date;

import com.bilibili.api.support.UserSupport;
import com.bilibili.dao.domain.*;
import com.bilibili.dao.domain.exception.ConditionException;
import com.bilibili.service.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class AdvertisementApi {

    @Autowired
    private UserSupport userSupport;

    @Autowired
    private TAdvertisementService tAdvertisementService;

    @Autowired
    private TAdPerformanceService tAdPerformanceService;

    @Autowired
    private TAdSpaceService tAdSpaceService;

    @Autowired
    private TPartnershipRequestsService tPartnershipRequestsService;

    @Autowired
    private TPartnershipService tPartnershipService;

    @Autowired
    private TMessagesService tMessagesService;

    //获取广告位信息
    @GetMapping("/get-adSpaceList")
    public JsonResponse<List<TAdSpace>> getAdSpaceList() {
        List<TAdSpace> tAdSpaceList = tAdSpaceService.list();
        return new JsonResponse<>(tAdSpaceList);
    }

    //购买广告位，并投放广告
    @PostMapping("/buy-advertisement-space")
    public JsonResponse<TAdvertisement> buyAdvertisementSpace(@RequestBody TAdvertisement tAdvertisement) {
        Long userId = userSupport.getCurrentUserId();
        tAdvertisement.setAdvertiserId(userId);
        if (ObjectUtils.allNull(tAdvertisement.getContentId())) {
            throw new ConditionException("参数异常");
        }
        if (ObjectUtils.allNull(tAdvertisement.getAdSpaceId())) {
            throw new ConditionException("参数异常");
        }
        if (StringUtils.isBlank(tAdvertisement.getAdvertisementPath())) {
            throw new ConditionException("参数异常");
        }
        tAdvertisementService.save(tAdvertisement);
        return new JsonResponse<>(tAdvertisement);
    }

    //展示广告
    @GetMapping("/get-advertisement")
    public JsonResponse<TAdvertisement> getAdvertisement(@RequestParam Long adSpaceId) {
        return new JsonResponse<>(tAdvertisementService.getByAdSpaceId(adSpaceId));
    }

    //广告被展示
    @PostMapping("/impressions-advertisement")
    public JsonResponse<Boolean> impressionsAdvertisement(@RequestParam Long adId) {
        TAdPerformance tAdPerformance = tAdPerformanceService.getByAdId(adId);
        tAdPerformance.setImpressions(tAdPerformance.getImpressions() + 1);
        tAdPerformance.setConversions(tAdPerformance.getClicks() / tAdPerformance.getConversions());
        tAdPerformanceService.save(tAdPerformance);
        return new JsonResponse<>(true);
    }

    //广告被点击
    @PostMapping("/click-advertisement")
    public JsonResponse<Boolean> clickAdvertisement(@RequestParam Long adId) {
        TAdPerformance tAdPerformance = tAdPerformanceService.getByAdId(adId);
        tAdPerformance.setImpressions(tAdPerformance.getClicks() + 1);
        tAdPerformance.setConversions(tAdPerformance.getClicks() / tAdPerformance.getConversions());
        tAdPerformanceService.save(tAdPerformance);
        return new JsonResponse<>(true);
    }

    // 广告主发送合作请求
    @PostMapping("/partnership/sentRequest")
    public JsonResponse<String> sendPartnershipRequest(@RequestParam Long contentCreatorId,
                                                       @RequestParam String details,
                                                       @RequestParam int expectedDuration,
                                                       @RequestParam BigDecimal expectedCompensation) {
        Long advertiserId = userSupport.getCurrentUserId();
        tPartnershipRequestsService.createPartnershipRequest(advertiserId,
                contentCreatorId,
                details,
                expectedDuration,
                expectedCompensation);
        return new JsonResponse<>("你成功发出合作请求");
    }

    //内容创作者查看未处理的合作请求
    @GetMapping("/partnership/getNoRequests")
    public JsonResponse<List<TPartnershipRequests>> getNoRequests() {
        Long userId = userSupport.getCurrentUserId();
        return new JsonResponse<>(tPartnershipRequestsService.getTPartnershipRequests(userId, "pending"));
    }

    //内容创作者查看已经处理的合作请求
    @GetMapping("/partnership/getRequests")
    public JsonResponse<List<TPartnershipRequests>> getRequests() {
        Long userId = userSupport.getCurrentUserId();
        return new JsonResponse<>(tPartnershipRequestsService.getTPartnershipRequests(userId, ""));
    }

    // 内容创作者接受合作请求
    @PostMapping("/partnership/acceptedRequest")
    public JsonResponse<String> acceptPartnershipRequest(@RequestParam Long requestId) {
        tPartnershipRequestsService.acceptPartnershipRequest(requestId);

        TPartnershipRequests tPartnershipRequests = tPartnershipRequestsService.getByRequestId(requestId);
        //更新合作表
        TPartnership tPartnership = new TPartnership();
        tPartnership.setAdvertiserId(tPartnershipRequests.getAdvertiserId());
        tPartnership.setCreatorId(tPartnershipRequests.getContentCreatorId());
        tPartnership.setStartTime(new Date());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date()); // 设置为当前时间
        calendar.add(Calendar.DATE, tPartnershipRequests.getExpectedDuration()); // 添加期望的合作期限
        Date endTime = calendar.getTime(); // 获得结束时间
        tPartnership.setEndTime(endTime);

        tPartnership.setContent(tPartnershipRequests.getDetails());

        tPartnershipService.save(tPartnership);

        return new JsonResponse<>("你成功接受了该合作");
    }

    // 内容创作者拒绝合作请求
    @PostMapping("/partnership/rejectRequest")
    public JsonResponse<String> rejectPartnershipRequest(@RequestParam Long requestId) {
        tPartnershipRequestsService.rejectPartnershipRequest(requestId);
        return new JsonResponse<>("你成功拒绝了该合作");
    }

    //发送消息
    public JsonResponse<Boolean> sendMessage(@RequestParam Long consumerId, String message) {

        Long producerId = userSupport.getCurrentUserId();

        TMessages tMessages = new TMessages();
        tMessages.setSenderId(producerId);
        tMessages.setReceiverId(consumerId);
        tMessages.setMessage(message);

        tMessagesService.save(tMessages);
        return new JsonResponse<>(true);
    }

    //获取当前用户的聊天列表

    //获取当前用户语某个用户消息

}
