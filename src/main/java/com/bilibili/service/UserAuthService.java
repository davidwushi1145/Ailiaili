package com.bilibili.service;


import com.bilibili.dao.domain.auth.UserAuthorities;

public interface UserAuthService {
    UserAuthorities getUserAuthorities(Long userId);

    void addUserDefaultRole(Long id);
}
