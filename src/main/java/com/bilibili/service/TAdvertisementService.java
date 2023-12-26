package com.bilibili.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bilibili.dao.domain.TAdvertisement;
import java.util.Map;
import java.util.Set;

/**
 * @author
 * @description 针对表【t_advertisement(广告表)】的数据库操作Service
 * @createDate 2023-12-10 16:14:55
 */
public interface TAdvertisementService extends IService<TAdvertisement> {

  TAdvertisement getByAdSpaceId(Long adSpaceId);

  /**
   * 获取正在工作的广告位
   * @return
   */
  Map<Long, Long> getDoingAdSpace();
}
