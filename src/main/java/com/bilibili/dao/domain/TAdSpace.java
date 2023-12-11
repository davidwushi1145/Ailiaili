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
 * 广告位表
 * @TableName t_ad_space
 */
@TableName(value = "t_ad_space")
@Data
public class TAdSpace implements Serializable {
  /**
   * 主键id
   */
  @TableId(value = "id", type = IdType.AUTO) private Long id;

  /**
   * 广告位描述（如页面位置、尺寸等）
   */
  @TableField(value = "description") private String description;

  /**
   * 价格
   */
  @TableField(value = "price") private BigDecimal price;

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
