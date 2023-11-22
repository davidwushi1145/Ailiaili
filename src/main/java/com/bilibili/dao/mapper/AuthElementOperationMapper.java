package com.bilibili.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bilibili.dao.domain.auth.AuthElementOperation;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 
* @description 针对表【t_auth_element_operation(权限控制--页面元素操作表)】的数据库操作Mapper
* @Entity com.bilibili.dao.domain.auth.AuthElementOperation
*/
@Mapper
public interface AuthElementOperationMapper extends BaseMapper<AuthElementOperation> {

}




