package com.bilibili.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bilibili.dao.domain.TPartnership;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author
 * @description 针对表【t_partnership(合作关系表)】的数据库操作Mapper
 * @createDate 2023-12-10 16:14:55
 * @Entity com.bilibili.dao.domain.TPartnership
 */
@Mapper

public interface TPartnershipMapper extends BaseMapper<TPartnership> {}
