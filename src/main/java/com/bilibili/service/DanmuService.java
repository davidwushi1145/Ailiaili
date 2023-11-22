package com.bilibili.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bilibili.dao.domain.Danmu;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
* @author 
* @description 针对表【t_danmu(弹幕表)】的数据库操作Service
*/
public interface DanmuService extends IService<Danmu> {


    List<Danmu> getDanmus(Map<String,Object> map) throws ParseException;

    void addDanmuToRedis(Danmu danmu);

    void asyncAddDanmu(Danmu danmu);
}
