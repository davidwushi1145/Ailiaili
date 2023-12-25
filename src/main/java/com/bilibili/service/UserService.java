package com.bilibili.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bilibili.dao.domain.PageResult;
import com.bilibili.dao.domain.User;
import com.bilibili.dao.domain.UserInfo;
import java.util.Map;
import org.springframework.data.domain.Page;

/**
 * @author
 * @description 针对表【t_user(用户表)】的数据库操作Service
 */
public interface UserService extends IService<User> {

  void addUser(User user);

  String login(User user) throws Exception;

  User getUserInfo(Long userId);

  void updateUserInfos(UserInfo userInfo);

  PageResult<UserInfo> pageListUserInfos(JSONObject params);

  Map<String, Object> loginForDts(User user) throws Exception;

  void logout(String refreshToken, Long userId);

  String refreshAccessToken(String refreshToken) throws Exception;

  PageResult<User> getBannedUsers(JSONObject params);

  void banUser(Long userId);

  void unbanUser(Long userId);
}
