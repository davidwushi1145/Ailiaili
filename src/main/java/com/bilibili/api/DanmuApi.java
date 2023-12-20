package com.bilibili.api;

import com.bilibili.api.support.UserSupport;
import com.bilibili.dao.domain.Danmu;
import com.bilibili.dao.domain.JsonResponse;
import com.bilibili.service.DanmuService;
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
    danmuService.addDanmuToRedis(danmu);
    return new JsonResponse<>(true);
  }
}
