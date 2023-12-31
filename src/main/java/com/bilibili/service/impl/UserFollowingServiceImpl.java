package com.bilibili.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bilibili.dao.domain.FollowingGroup;
import com.bilibili.dao.domain.User;
import com.bilibili.dao.domain.UserFollowing;
import com.bilibili.dao.domain.UserInfo;
import com.bilibili.dao.domain.constant.UserConstant;
import com.bilibili.dao.domain.exception.ConditionException;
import com.bilibili.dao.mapper.UserFollowingMapper;
import com.bilibili.service.FollowingGroupService;
import com.bilibili.service.UserFollowingService;
import com.bilibili.service.UserInfoService;
import com.bilibili.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author
 * @description 针对表【t_user_following(用户关注表)】的数据库操作Service实现
 */
@Service
public class UserFollowingServiceImpl
    extends ServiceImpl<UserFollowingMapper, UserFollowing>
    implements UserFollowingService {

  @Autowired private FollowingGroupService followingGroupService;

  @Autowired private UserService userService;

  @Autowired private UserInfoService userInfoService;

  @Autowired private UserFollowingMapper userFollowingMapper;

  // 根据user_id获取所有的关注信息
  @Override
  public List<UserFollowing> getUserFollowingsByUserId(Long userId) {
    QueryWrapper<UserFollowing> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("user_id", userId);
    List<UserFollowing> list = this.list(queryWrapper);
    return list;
  }

  // 根据user_id获取所有粉丝关注信息
  @Override
  public List<UserFollowing> getFansByUserId(Long userId) {
    QueryWrapper<UserFollowing> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("following_id", userId);
    List<UserFollowing> list = this.list(queryWrapper);
    return list;
  }

  @Override
  @Transactional
  // 添加关注用户（先放入默认分组）
  public void addUserFollowings(UserFollowing userFollowing) {
    Long groupId = userFollowing.getGroupId();
    if (groupId == null) {
      FollowingGroup followingGroup = followingGroupService.getByType(
          UserConstant.USER_FOLLOWING_GROUP_TYPE_DEFAULT);
      userFollowing.setGroupId(followingGroup.getId());
    } else {
      FollowingGroup followingGroup =
          followingGroupService.getById(userFollowing.getGroupId());
      if (followingGroup == null) {
        throw new ConditionException("关注分组不存在！");
      }
    }
    Long followingId = userFollowing.getFollowingId();
    User user = userService.getById(followingId);
    if (user == null) {
      throw new ConditionException("关注用户不存在！");
    }
    // 应该是再次点击关注按钮 取消关注
    this.deleteUserFollowing(userFollowing.getUserId(), followingId);
    this.save(userFollowing);
  }

  @Override
  public void deleteUserFollowing(Long userId, Long followingId) {
    QueryWrapper<UserFollowing> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("user_id", userId);
    queryWrapper.eq("following_id", followingId);
    this.remove(queryWrapper);
  }

  // 获取用户关注列表
  // 查询关注用户的基本信息
  // 将关注用户按分组进行分类
  @Override
  public List<FollowingGroup> getUserFollowings(Long userId) {
    QueryWrapper<UserFollowing> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq(userId != null, "user_id", userId);
    //        所有关注者的列表
    List<UserFollowing> list = this.list(queryWrapper);
    Set<Long> followingIdSet = list.stream()
                                   .map(UserFollowing::getFollowingId)
                                   .collect(Collectors.toSet());
    List<UserInfo> userInfoList = new ArrayList<>();
    if (followingIdSet.size() > 0) {
      userInfoList = userInfoService.getListByUserIds(followingIdSet);
    }
    for (UserFollowing userFollowing : list) {
      for (UserInfo userInfo : userInfoList) {
        if (userFollowing.getFollowingId().equals(userInfo.getUserId())) {
          userFollowing.setUserInfo(userInfo);
        }
      }
    }
    //        默认分组以及用户创建的分组列表
    List<FollowingGroup> groupList = followingGroupService.getByUserId(userId);
    // 全部关注分组构建
    FollowingGroup allGroup = new FollowingGroup();
    allGroup.setId((long)-1);
    allGroup.setName(UserConstant.USER_FOLLOWING_GROUP_ALL_NAME);
    allGroup.setFollowingUserInfoList(userInfoList);
    List<FollowingGroup> result = new ArrayList<>();
    result.add(allGroup);
    //        对每一种关注构建列表
    for (FollowingGroup group : groupList) {
      List<UserInfo> infoList = new ArrayList<>();
      for (UserFollowing userFollowing : list) {
        if (group.getId().equals(userFollowing.getGroupId())) {
          infoList.add(userFollowing.getUserInfo());
        }
      }
      group.setFollowingUserInfoList(infoList);
      result.add(group);
    }
    return result;
  }

  // 获取粉丝列表
  // 根据id查询用户基本信息
  // 查询当前用户是否关注该粉丝
  @Override
  public List<UserFollowing> getUserFans(Long userId) {
    //        所有粉丝关注列表
    List<UserFollowing> fanList = this.getFansByUserId(userId);
    //        所有粉丝id
    Set<Long> fanSet = fanList.stream()
                           .map(UserFollowing::getUserId)
                           .collect(Collectors.toSet());
    //        所有粉丝的信息列表
    List<UserInfo> userInfoList = new ArrayList<>();
    if (fanSet.size() > 0) {
      userInfoList = userInfoService.getListByUserIds(fanSet);
    }
    List<UserFollowing> followingList = this.getUserFollowingsByUserId(userId);
    for (UserFollowing fan : fanList) {
      for (UserInfo userInfo : userInfoList) {
        if (fan.getUserId().equals(userInfo.getUserId())) {
          userInfo.setFollowed(false);
          fan.setUserInfo(userInfo);
        }
      }
      for (UserFollowing userFollowing : followingList) {
        if (userFollowing.getFollowingId().equals(fan.getUserId())) {
          fan.getUserInfo().setFollowed(true);
        }
      }
    }
    return fanList;
  }

  // 添加关注分组
  @Override
  public Long addUserFollowingGroups(FollowingGroup followingGroup) {
    followingGroup.setType(UserConstant.USER_FOLLOWING_GROUP_TYPE_USER);
    followingGroupService.save(followingGroup);
    return followingGroup.getId();
  }

  @Override
  public List<FollowingGroup> getUserFollowingGroups(Long userId) {
    List<FollowingGroup> list = followingGroupService.getByUserId(userId);
    return list;
  }

  @Override
  public List<UserInfo> checkFollowingStatus(List<UserInfo> list, Long userId) {
    List<UserFollowing> userFollowingList =
        this.getUserFollowingsByUserId(userId);
    for (UserInfo userInfo : list) {
      userInfo.setFollowed(false);
      for (UserFollowing userFollowing : userFollowingList) {
        if (userFollowing.getFollowingId().equals(userInfo.getUserId())) {
          userInfo.setFollowed(true);
        }
      }
    }
    return list;
  }

  @Override
  public int getFollowingsNumber(Long userId) {
    QueryWrapper<UserFollowing> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq(userId != null, "user_id", userId);
    List<UserFollowing> list = userFollowingMapper.selectList(queryWrapper);
    return list.size();
  }

  @Override
  public Boolean getIsFollow(Long userId, Long followUserId) {
    QueryWrapper<UserFollowing> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("user_id", userId);
    queryWrapper.eq("following_id", followUserId);
    UserFollowing userFollowing = userFollowingMapper.selectOne(queryWrapper);
    return !(userFollowing == null);
  }

  @Override
  public Boolean deleteFollow(Long userId, Long followUserId) {
    QueryWrapper<UserFollowing> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("user_id", userId);
    queryWrapper.eq("following_id", followUserId);
    int flag = userFollowingMapper.delete(queryWrapper);
    return flag == 1;
  }
}
