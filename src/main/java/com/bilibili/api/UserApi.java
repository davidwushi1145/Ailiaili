package com.bilibili.api;

import com.alibaba.fastjson.JSONObject;
import com.bilibili.api.support.UserSupport;
import com.bilibili.dao.domain.JsonResponse;
import com.bilibili.dao.domain.PageResult;
import com.bilibili.dao.domain.User;
import com.bilibili.dao.domain.UserInfo;
import com.bilibili.dao.domain.auth.UserAuthorities;
import com.bilibili.dao.domain.exception.ConditionException;
import com.bilibili.service.*;
import com.bilibili.service.util.RSAUtil;
import io.swagger.models.auth.In;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserApi {

  @Autowired private UserService userService;

  @Autowired private UserSupport userSupport;

  @Autowired private UserFollowingService userFollowingService;

  @Autowired private UserCoinService userCoinService;

  @Autowired private UserAuthService userAuthService;

  @Autowired private ElasticSearchService elasticSearchService;

  @GetMapping("/users")
  public JsonResponse<User> getUserInfo() {
    Long userId = userSupport.getCurrentUserId();
    User user = userService.getUserInfo(userId);
    return new JsonResponse<>(user);
  }

  // 获取rsa公钥
  @GetMapping("/rsa-pks")
  public JsonResponse<String> getRsaPublicKey() {
    String publicKeyStr = RSAUtil.getPublicKeyStr();
    return JsonResponse.success(publicKeyStr);
  }

  // 用户注册
  @PostMapping("/users")
  public JsonResponse<String> addUser(@RequestBody User user) {
    userService.addUser(user);
    elasticSearchService.addUserInfo(user.getUserInfo());
    return JsonResponse.success();
  }

  // 登录 生成token
  @PostMapping("/user-tokens")
  public JsonResponse<String> login(@RequestBody User user) throws Exception {
    String token = userService.login(user);
    return JsonResponse.success(token);
  }

  // 更新当前用户信息
  @PutMapping("/users")
  public JsonResponse<String> updateUsers(@RequestBody User user) {
    Long userId = userSupport.getCurrentUserId();
    user.setId(userId);
    userService.updateById(user);
    return JsonResponse.success();
  }

  @PutMapping("/user-infos")
  public JsonResponse<String> updateUserInfos(@RequestBody UserInfo userInfo) {
    Long userId = userSupport.getCurrentUserId();
    userInfo.setUserId(userId);
    userService.updateUserInfos(userInfo);
    elasticSearchService.updateUserInfo(userInfo);
    return JsonResponse.success();
  }

  // 用户分页查询
  @GetMapping("/user-infos")
  public JsonResponse<PageResult<UserInfo>>
  pageListUserInfos(@RequestParam Integer page, @RequestParam Integer size,
                    @RequestParam String nick) {
    Long userId = userSupport.getCurrentUserId();
    JSONObject params = new JSONObject();
    params.put("page", page);
    params.put("size", size);
    params.put("nick", nick);
    params.put("userId", userId);
    PageResult<UserInfo> result = userService.pageListUserInfos(params);
    // 判断是否关注该用户
    if (result.getTotal() > 0) {
      List<UserInfo> checkedUserInfoList =
          userFollowingService.checkFollowingStatus(result.getList(), userId);
      result.setList(checkedUserInfoList);
    }
    return new JsonResponse<>(result);
  }

  // 双token机制，带刷新
  @PostMapping("/user-dts")
  public JsonResponse<Map<String, Object>> loginForDts(@RequestBody User user)
      throws Exception {
    Map<String, Object> map = userService.loginForDts(user);
    return new JsonResponse<>(map);
  }

  // 双令牌退出登录
  @DeleteMapping("/refresh-tokens")
  public JsonResponse<String> logout(HttpServletRequest request) {
    String refreshToken = request.getHeader("refreshToken");
    Long userId = userSupport.getCurrentUserId();
    userService.logout(refreshToken, userId);
    return JsonResponse.success();
  }

  @PostMapping("/access-tokens")
  public JsonResponse<String> refreshAccessToken(HttpServletRequest request)
      throws Exception {
    String refreshToken = request.getHeader("refreshToken");
    String accessToken = userService.refreshAccessToken(refreshToken);
    return new JsonResponse<>(accessToken);
  }

  // 获取用户硬币数量
  @GetMapping("/getUserCoins")
  public JsonResponse<Long> getUserCoins() {
    Long userId = userSupport.getCurrentUserId();
    return new JsonResponse<>(userCoinService.getUserCoinAmount(userId));
  }

  // 获取用户粉丝数
  @GetMapping("/getFollowers")
  public JsonResponse<Integer> getFollowers() {
    Long userId = userSupport.getCurrentUserId();
    return new JsonResponse<>(userFollowingService.getUserFans(userId).size());
  }

  // 获取用户关注数
  @GetMapping("/getUserFollowing")
  public JsonResponse<Integer> getUserFollowing() {
    Long userId = userSupport.getCurrentUserId();
    return new JsonResponse<>(userFollowingService.getFollowingsNumber(userId));
  }

  // 根据userId获取用户信息
  @GetMapping("/getUserInfoByUserId")
  public JsonResponse<UserInfo> getUserInfoByUserId(Long userId) {
    User user = userService.getUserInfo(userId);
    return new JsonResponse<>(user.getUserInfo());
  }

  // 展示被封禁的用户
  @GetMapping("/getBannedUsers")
  public JsonResponse<PageResult<User>>
  getBannedUsers(@RequestParam Integer page, @RequestParam Integer size) {
    Long userId = userSupport.getCurrentUserId();

    // 判断是否有权限
    UserAuthorities userAuthorities =
        userAuthService.getUserAuthorities(userId);
    boolean hasPermission =
        userAuthorities.getRoleElementOperationList().stream().anyMatch(
            roleElementOperation
            -> roleElementOperation.getElementOperationId().equals(2L));
    PageResult<User> result;
    if (hasPermission) {

      JSONObject params = new JSONObject();
      params.put("page", page);
      params.put("size", size);
      result = userService.getBannedUsers(params);
    } else {
      throw new ConditionException("没有权限");
    }
    return new JsonResponse<>(result);
  }

  // 封禁用户
  @PutMapping("/banUser")
  public JsonResponse<String> banUser(@RequestParam Long userId) {
    Long currentUserId = userSupport.getCurrentUserId();
    // 判断是否有权限
    UserAuthorities userAuthorities =
        userAuthService.getUserAuthorities(currentUserId);
    boolean hasPermission =
        userAuthorities.getRoleElementOperationList().stream().anyMatch(
            roleElementOperation
            -> roleElementOperation.getElementOperationId().equals(2L));
    if (hasPermission) {
      userService.banUser(userId);
    } else {
      throw new ConditionException("没有权限");
    }
    return JsonResponse.success();
  }

  // 解封用户
  @PutMapping("/unbanUser")
  public JsonResponse<String> unbanUser(@RequestParam Long userId) {
    Long currentUserId = userSupport.getCurrentUserId();
    // 判断是否有权限
    UserAuthorities userAuthorities =
        userAuthService.getUserAuthorities(currentUserId);
    boolean hasPermission =
        userAuthorities.getRoleElementOperationList().stream().anyMatch(
            roleElementOperation
            -> roleElementOperation.getElementOperationId().equals(2L));
    if (hasPermission) {
      userService.unbanUser(userId);
    } else {
      throw new ConditionException("没有权限");
    }
    return JsonResponse.success();
  }

  // 获取所有用户
  @GetMapping("/getAllUser")
  public JsonResponse<List<User>> getAllUser() {
    Long currentUserId = userSupport.getCurrentUserId();
    // 判断是否有权限
    UserAuthorities userAuthorities =
        userAuthService.getUserAuthorities(currentUserId);
    boolean hasPermission =
        userAuthorities.getRoleElementOperationList().stream().anyMatch(
            roleElementOperation
            -> roleElementOperation.getElementOperationId().equals(2L));
    if (hasPermission) {
      List<User> userList = userService.getAllUser();
      return new JsonResponse<>(userList);
    } else {
      throw new ConditionException("没有权限");
    }
  }

  // 更改指定ID用户信息
  @PutMapping("/updateSignificantUser")
  public JsonResponse<String> updateSignificantUser(@RequestBody User user)
      throws Exception {
    Long currentUserId = userSupport.getCurrentUserId();
    // 判断是否有权限
    UserAuthorities userAuthorities =
        userAuthService.getUserAuthorities(currentUserId);
    boolean hasPermission =
        userAuthorities.getRoleElementOperationList().stream().anyMatch(
            roleElementOperation
            -> roleElementOperation.getElementOperationId().equals(2L));
    if (hasPermission) {
      System.out.println(user);
      userService.updateUser(user);
      return JsonResponse.success();
    } else {
      throw new ConditionException("没有权限");
    }
  }
}
