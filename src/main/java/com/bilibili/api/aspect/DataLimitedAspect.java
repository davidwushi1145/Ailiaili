package com.bilibili.api.aspect;

import com.bilibili.api.support.UserSupport;
import com.bilibili.dao.domain.UserMoments;
import com.bilibili.dao.domain.auth.UserRole;
import com.bilibili.dao.domain.constant.AuthRoleConstant;
import com.bilibili.dao.domain.exception.ConditionException;
import com.bilibili.service.UserRoleService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Order(1)
@Component
@Aspect
public class DataLimitedAspect {

    @Autowired
    private UserSupport userSupport;

    @Autowired
    private UserRoleService userRoleService;

    //定义切点
    @Pointcut("@annotation(com.bilibili.dao.domain.annotation.DataLimited)")
    public void check(){
    }

    @Before("check()")
    public void doBefore(JoinPoint joinPoint){
        Long userId = userSupport.getCurrentUserId();
        List<UserRole> userRoleList = userRoleService.getUserRoleByUserId(userId);
        Set<String> roleCodeSet = userRoleList.stream().map(UserRole::getRoleCode).collect(Collectors.toSet());
        Object[] args = joinPoint.getArgs();
        for(Object arg:args){
            if(arg instanceof UserMoments){
                UserMoments userMoments = (UserMoments) arg;
                String type = userMoments.getType();
                if(roleCodeSet.contains(AuthRoleConstant.ROLE_LV0) && !"0".equals(type)){
                    throw new ConditionException("参数异常！");
                }
            }
        }
    }
}
