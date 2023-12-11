package com.bilibili.dao.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 合作关系表
 * @TableName t_partnership
 */
@TableName(value = "t_partnership")
@Data
public class TPartnership implements Serializable {
  /**
   * 主键id
   */
  @TableId(value = "id", type = IdType.AUTO) private Long id;

  /**
   * 广告主id
   */
  @TableField(value = "advertiser_id") private Long advertiserId;

  /**
   * 内容创作者id
   */
  @TableField(value = "creator_id") private Long creatorId;

  /**
   * 合作开始时间
   */
  @TableField(value = "start_time") private Date startTime;

  /**
   * 合作结束时间
   */
  @TableField(value = "end_time") private Date endTime;

  /**
   * 合作内容
   */
  @TableField(value = "content") private String content;

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
