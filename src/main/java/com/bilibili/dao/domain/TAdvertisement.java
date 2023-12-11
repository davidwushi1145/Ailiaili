package com.bilibili.dao.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 广告表
 * @TableName t_advertisement
 */
@TableName(value = "t_advertisement")
@Data
public class TAdvertisement implements Serializable {
  /**
   * 主键id
   */
  @TableId(value = "id", type = IdType.AUTO) private Long id;

  /**
   * 广告主id
   */
  @TableField(value = "advertiser_id") private Long advertiserId;

  /**
   * 广告内容id（关联到t_file表）
   */
  @TableField(value = "content_id") private Long contentId;

  /**
   * 广告位id（关联到t_ad_space表）
   */
  @TableField(value = "ad_space_id") private Long adSpaceId;

  /**
   * 投放开始时间
   */
  @TableField(value = "start_time") private Date startTime;

  /**
   * 投放结束时间
   */
  @TableField(value = "end_time") private Date endTime;

  /**
   * 状态（如：激活、暂停、结束）
   */
  @TableField(value = "status") private String status;

  /**
   * 广告链接
   */
  @TableField(value = "advertisement_path") private String advertisementPath;

  /**
   * 创建时间
   */
  @TableField(value = "create_time") private Date createTime;

  /**
   * 更新时间
   */
  @TableField(value = "update_time") private Date updateTime;

  @TableField(exist = false) private static final long serialVersionUID = 1L;
}