package com.bilibili.api;

import com.bilibili.api.support.UserSupport;
import com.bilibili.dao.domain.JsonResponse;
import com.bilibili.dao.domain.UserMoments;
import com.bilibili.dao.domain.annotation.ApiLimitedRole;
import com.bilibili.dao.domain.annotation.DataLimited;
import com.bilibili.dao.domain.constant.AuthRoleConstant;
import com.bilibili.service.UserMomentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserMomentApi {

    @Autowired
    private UserMomentsService userMomentsService;

    @Autowired
    private UserSupport userSupport;

    //boot上下文


    @ApiLimitedRole(limitedRoleCodeList = {AuthRoleConstant.ROLE_LV0})
    @DataLimited
    @PostMapping("/user-moments")
    public JsonResponse<String> addUserMoments(@RequestBody UserMoments userMoments) throws Exception {
        Long userId = userSupport.getCurrentUserId();
        userMoments.setUserId(userId);
        userMomentsService.addUserMoments(userMoments);
        return JsonResponse.success();
    }

    //获取关注用户的动态
    @GetMapping("/user-subscribed-moments")
    public JsonResponse<List<UserMoments>> getUserSubscribedMoments() {
        Long userId = userSupport.getCurrentUserId();
        List<UserMoments> list = userMomentsService.getUserSubscribedMoments(userId);
        return new JsonResponse<>(list);
    }

}
