package com.bilibili.dao.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 合作请求表
 * @TableName t_partnership_requests
 */
@TableName(value = "t_partnership_requests")
@Data
public class TPartnershipRequests implements Serializable {
  /**
   * 合作请求ID
   */
  @TableId(value = "id", type = IdType.AUTO) private Long id;

  /**
   * 广告主ID
   */
  @TableField(value = "advertiser_id") private Long advertiserId;

  /**
   * 内容创作者ID
   */
  @TableField(value = "content_creator_id") private Long contentCreatorId;

  /**
   * 请求时间
   */
  @TableField(value = "request_time") private Date requestTime;

  /**
   * 请求状态（等待、接受、拒绝）
   */
  @TableField(value = "status") private Object status;

  /**
   * 合作详情
   */
  @TableField(value = "details") private String details;

  /**
   * 预期的合作期限（以天为单位）
   */
  @TableField(value = "expected_duration") private Integer expectedDuration;

  /**
   * 预期的报酬
   */
  @TableField(value = "expected_compensation")
  private BigDecimal expectedCompensation;

  @TableField(exist = false) private static final long serialVersionUID = 1L;
}