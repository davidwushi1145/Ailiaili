package com.bilibili.api;

import com.alibaba.fastjson.JSONObject;
import com.bilibili.api.support.UserSupport;
import com.bilibili.dao.domain.Danmu;
import com.bilibili.dao.domain.JsonResponse;
import com.bilibili.dao.domain.PageResult;
import com.bilibili.dao.domain.User;
import com.bilibili.dao.domain.auth.UserAuthorities;
import com.bilibili.dao.domain.exception.ConditionException;
import com.bilibili.service.DanmuService;
import com.bilibili.service.UserAuthService;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class DanmuApi {

  @Autowired private UserSupport userSupport;

  @Autowired private DanmuService danmuService;

  @Autowired private UserAuthService userAuthService;
  // 查询弹幕
  @GetMapping("/danmus")
  public JsonResponse<List<Danmu>> getDanmus(@RequestParam Long videoId,
                                             String startTime, String endTime)
      throws ParseException {
    List<Danmu> list;
    try {
      userSupport.getCurrentUserId();
      // 登录模式可以使用按时间筛选
      Map<String, Object> map = new HashMap<>();
      map.put("startDate", startTime);
      map.put("endDate", endTime);
      map.put("videoId", videoId);
      list = danmuService.getDanmus(map);
    } catch (Exception e) {
      Map<String, Object> map = new HashMap<>();
      map.put("videoId", String.valueOf(videoId));
      list = danmuService.getDanmus(map);
    }
    return new JsonResponse<>(list);
  }

  @PostMapping("/addDanmu")
  public JsonResponse<Boolean> addDanmu(@RequestBody Danmu danmu) {
    Long userId = userSupport.getCurrentUserId();
    if (ObjectUtils.isEmpty(userId)) {
      return new JsonResponse<>(false);
    }
    danmu.setUserId(userId);
    // danmu.setDanmuTime(String.valueOf(Long.parseLong(danmu.getDanmuTime()) *
    // 1000));
    danmuService.addDanmuToRedis(danmu);
    return new JsonResponse<>(true);
  }

  // 查询审核未通过的弹幕
  @GetMapping("/UnpassDanmus")
  public JsonResponse<PageResult<Danmu>>
  getUnpassDanmus(@RequestParam Integer page, @RequestParam Integer size) {
    Long userId = userSupport.getCurrentUserId();

    // 判断是否有权限
    UserAuthorities userAuthorities =
        userAuthService.getUserAuthorities(userId);
    boolean hasPermission =
        userAuthorities.getRoleElementOperationList().stream().anyMatch(
            roleElementOperation
            -> roleElementOperation.getElementOperationId().equals(2L));
    PageResult<Danmu> result;
    if (hasPermission) {

      JSONObject params = new JSONObject();
      params.put("page", page);
      params.put("size", size);
      result = danmuService.getUnpassDanmus(params);
    } else {
      throw new ConditionException("没有权限");
    }
    return new JsonResponse<>(result);
  }

  // 审核弹幕
  @PostMapping("/passDanmu")
  public JsonResponse<Boolean> passDanmu(@RequestParam Long danmuId) {
    Long userId = userSupport.getCurrentUserId();
    // 判断是否有权限
    UserAuthorities userAuthorities =
        userAuthService.getUserAuthorities(userId);
    boolean hasPermission =
        userAuthorities.getRoleElementOperationList().stream().anyMatch(
            roleElementOperation
            -> roleElementOperation.getElementOperationId().equals(2L));
    if (hasPermission) {
      danmuService.passDanmu(danmuId);
    } else {
      throw new ConditionException("没有权限");
    }
    return new JsonResponse<>(true);
  }
}
