package com.bilibili.api;

import com.bilibili.api.support.UserSupport;
import com.bilibili.dao.domain.FollowingGroup;
import com.bilibili.dao.domain.JsonResponse;
import com.bilibili.dao.domain.UserFollowing;
import com.bilibili.service.UserFollowingService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserFollowingApi {

  @Autowired private UserFollowingService userFollowingService;

  @Autowired private UserSupport userSupport;

  // 添加关注信息
  @PostMapping("/user-followings")
  public JsonResponse<String>
  addUserFollowings(@RequestBody UserFollowing userFollowing) {
    Long userId = userSupport.getCurrentUserId();
    userFollowing.setUserId(userId);
    userFollowingService.addUserFollowings(userFollowing);
    return JsonResponse.success();
  }

  // 取消关注
  @DeleteMapping("delete-following")
  public JsonResponse<Boolean> deleteFollowing(Long followUserId) {
    Long userId = userSupport.getCurrentUserId();
    boolean flag = userFollowingService.deleteFollow(userId, followUserId);
    return new JsonResponse<>(flag);
  }

  // 获取用户关注列表
  @GetMapping("/user-followings")
  public JsonResponse<List<FollowingGroup>> getUserFollowings() {
    Long userId = userSupport.getCurrentUserId();
    List<FollowingGroup> userFollowings =
        userFollowingService.getUserFollowings(userId);
    return new JsonResponse<>(userFollowings);
  }

  // 获取用户粉丝列表
  @GetMapping("/user-fans")
  public JsonResponse<List<UserFollowing>> getFans() {
    Long userId = userSupport.getCurrentUserId();
    List<UserFollowing> userFans = userFollowingService.getUserFans(userId);
    return new JsonResponse<>(userFans);
  }

  // 添加分组
  @PostMapping("/user-following-groups")
  public JsonResponse<Long>
  addUserFollowingsGroups(@RequestBody FollowingGroup followingGroup) {
    Long userId = userSupport.getCurrentUserId();
    followingGroup.setUserId(userId);
    Long groupId = userFollowingService.addUserFollowingGroups(followingGroup);
    return new JsonResponse<>(groupId);
  }

  // 查询用户分组
  @GetMapping("/user-following-groups")
  public JsonResponse<List<FollowingGroup>> getUserFollowingGroups() {
    Long userId = userSupport.getCurrentUserId();
    List<FollowingGroup> list =
        userFollowingService.getUserFollowingGroups(userId);
    return new JsonResponse<>(list);
  }

  // 获取用户粉丝列表
  @GetMapping("/getUserFansByUserId")
  public JsonResponse<List<UserFollowing>> getFans(Long userId) {
    List<UserFollowing> userFans = userFollowingService.getUserFans(userId);
    return new JsonResponse<>(userFans);
  }

  // 适配前端 查看某个用户是不是已经被当前用户关注
  @GetMapping("/getIsFollow")
  public JsonResponse<Boolean> getIsFollow(Long followUserId) {
    Long userId = userSupport.getCurrentUserId();
    boolean flag = userFollowingService.getIsFollow(userId, followUserId);
    return new JsonResponse<>(flag);
  }
}
