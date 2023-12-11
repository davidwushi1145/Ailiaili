package com.bilibili.service;

import com.bilibili.dao.domain.TAdvertisement;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 下水道的小老鼠
* @description 针对表【t_advertisement(广告表)】的数据库操作Service
* @createDate 2023-12-10 16:14:55
*/
public interface TAdvertisementService extends IService<TAdvertisement> {

    TAdvertisement getByAdSpaceId(Long adSpaceId);
}
