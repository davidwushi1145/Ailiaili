package com.bilibili.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bilibili.dao.domain.TPartnership;
import com.bilibili.dao.mapper.TPartnershipMapper;
import com.bilibili.service.TPartnershipService;
import org.springframework.stereotype.Service;

/**
 * @author
 * @description 针对表【t_partnership(合作关系表)】的数据库操作Service实现
 * @createDate 2023-12-10 16:14:55
 */
@Service
public class TPartnershipServiceImpl
    extends ServiceImpl<TPartnershipMapper, TPartnership>
    implements TPartnershipService {}
