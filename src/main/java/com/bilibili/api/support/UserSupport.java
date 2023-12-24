package com.bilibili.api.support;

import com.bilibili.dao.domain.exception.ConditionException;
import com.bilibili.service.util.TokenUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class UserSupport {
  public Long getCurrentUserId() {
    ServletRequestAttributes requestAttributes =
        (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
    //        String token = requestAttributes.getRequest().getHeader("token");
    //        Long userId = TokenUtil.verifyToken(token);

    // openAPI 将 token 放在了 Authorization 头部，并使用了 Bearer 方案
    String authHeader =
        requestAttributes.getRequest().getHeader("Authorization");
    // 去掉 "Bearer " 前缀
    String token = authHeader.substring(7);
    Long userId = TokenUtil.verifyToken(token);
    if (userId < 0) {
      throw new ConditionException("非法用户！");
    }
    return userId;
  }
}
