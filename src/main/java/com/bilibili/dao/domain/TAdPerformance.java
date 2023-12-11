package com.bilibili.dao.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 广告性能表
 * @TableName t_ad_performance
 */
@TableName(value = "t_ad_performance")
@Data
public class TAdPerformance implements Serializable {
  /**
   * 主键id
   */
  @TableId(value = "id", type = IdType.AUTO) private Long id;

  /**
   * 广告id（关联到t_advertisement表）
   */
  @TableField(value = "ad_id") private Long adId;

  /**
   * 点击次数
   */
  @TableField(value = "clicks") private Integer clicks;

  /**
   * 展示次数
   */
  @TableField(value = "impressions") private Integer impressions;

  /**
   * 转化次数
   */
  @TableField(value = "conversions") private Integer conversions;

  /**
   * 日期
   */
  @TableField(value = "date") private Date date;

  /**
   * 创建时间
   */
  @TableField(value = "create_time") private Date createTime;

  @TableField(exist = false) private static final long serialVersionUID = 1L;
}