package com.bilibili.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bilibili.dao.domain.TMessages;
import com.bilibili.service.TMessagesService;
import com.bilibili.dao.mapper.TMessagesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 下水道的小老鼠
 * @description 针对表【t_messages(消息通信表)】的数据库操作Service实现
 * @createDate 2023-12-10 16:14:55
 */
@Service
public class TMessagesServiceImpl extends ServiceImpl<TMessagesMapper, TMessages>
        implements TMessagesService {

    @Autowired
    private TMessagesMapper tMessagesMapper;

    @Override
    public List<Long> getIdList(Long userId) {
        List<Long> list = new ArrayList<>();
        List<TMessages> temp = new ArrayList<>();
        QueryWrapper<TMessages> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("sender_id", userId);
        temp = tMessagesMapper.selectList(queryWrapper1);
        for (TMessages tMessages : temp) {
            list.add(tMessages.getReceiverId());
        }

        QueryWrapper<TMessages> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("receiver_id", userId);
        temp = tMessagesMapper.selectList(queryWrapper1);
        for (TMessages tMessages : temp) {
            list.add(tMessages.getSenderId());
        }

        return list;
    }

    @Override
    public List<TMessages> getChatList(Long userId, Long consumerId) {
        QueryWrapper<TMessages> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("sender_id", userId).eq("receiver_id", consumerId)
                .or()
                .eq("receiver_id", userId).eq("sender_id", consumerId)
                .orderByDesc("send_time");

        return tMessagesMapper.selectList(queryWrapper);
    }
}




