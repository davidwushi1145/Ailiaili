package com.bilibili.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bilibili.dao.domain.TMessages;
import java.util.List;
import java.util.Set;

/**
 * @author
 * @description 针对表【t_messages(消息通信表)】的数据库操作Service
 * @createDate 2023-12-10 16:14:55
 */
public interface TMessagesService extends IService<TMessages> {

  Set<Long> getIdList(Long userId);

  List<TMessages> getChatList(Long userId, Long consumerId);
}
