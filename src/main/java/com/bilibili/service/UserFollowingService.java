package com.bilibili.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bilibili.dao.domain.FollowingGroup;
import com.bilibili.dao.domain.UserFollowing;
import com.bilibili.dao.domain.UserInfo;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author
 * @description 针对表【t_user_following(用户关注表)】的数据库操作Service
 */
public interface UserFollowingService extends IService<UserFollowing> {

  // 根据user_id获取所有的关注信息
  List<UserFollowing> getUserFollowingsByUserId(Long userId);

  // 根据user_id获取所有粉丝关注信息
  List<UserFollowing> getFansByUserId(Long userId);

  @Transactional
  // 添加关注用户（先放入默认分组）
  void addUserFollowings(UserFollowing userFollowing);

  void deleteUserFollowing(Long userId, Long followingId);

  // 获取用户关注列表
  // 查询关注用户的基本信息
  // 将关注用户按分组进行分类
  List<FollowingGroup> getUserFollowings(Long userId);

  // 获取粉丝列表
  // 根据id查询用户基本信息
  // 查询当前用户是否关注该粉丝
  List<UserFollowing> getUserFans(Long userId);

  Long addUserFollowingGroups(FollowingGroup followingGroup);

  List<FollowingGroup> getUserFollowingGroups(Long userId);

  List<UserInfo> checkFollowingStatus(List<UserInfo> list, Long userId);

  // 获取用户关注的数量
  int getFollowingsNumber(Long userId);
}
