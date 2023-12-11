package com.bilibili.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bilibili.dao.domain.TMessages;
import com.bilibili.service.TMessagesService;
import com.bilibili.dao.mapper.TMessagesMapper;
import org.springframework.stereotype.Service;

/**
* @author 下水道的小老鼠
* @description 针对表【t_messages(消息通信表)】的数据库操作Service实现
* @createDate 2023-12-10 16:14:55
*/
@Service
public class TMessagesServiceImpl extends ServiceImpl<TMessagesMapper, TMessages>
    implements TMessagesService{

}




