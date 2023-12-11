package com.bilibili.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bilibili.dao.domain.TMessages;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 下水道的小老鼠
 * @description 针对表【t_messages(消息通信表)】的数据库操作Mapper
 * @createDate 2023-12-10 16:14:55
 * @Entity com.bilibili.dao.domain.TMessages
 */
@Mapper

public interface TMessagesMapper extends BaseMapper<TMessages> {}
