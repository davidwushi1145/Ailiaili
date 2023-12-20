package com.bilibili.dao.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 广告性能表
 * @TableName t_ad_performance
 */
@TableName(value = "t_ad_performance")
@Data
@Document(indexName = "tadperformance")
public class TAdPerformance implements Serializable {
  /**
   * 主键id
   */
  @TableId(type = IdType.AUTO) private Long id;

  /**
   * 广告id（关联到t_advertisement表）
   */
  @TableField(value = "ad_id") @Field(type = FieldType.Long) private Long adId;

  /**
   * 点击次数
   */
  @TableField(value = "clicks")
  @Field(type = FieldType.Integer)
  private Integer clicks;

  /**
   * 展示次数
   */
  @TableField(value = "impressions")
  @Field(type = FieldType.Integer)
  private Integer impressions;

  /**
   * 转化次数
   */
  @TableField(value = "conversions")
  @Field(type = FieldType.Integer)
  private Integer conversions;

  /**
   * 日期
   */
  @TableField(value = "date") @Field(type = FieldType.Long) private Date date;

  /**
   * 创建时间
   */
  @TableField(value = "create_time")
  @Field(type = FieldType.Date)
  private Date createTime;

  @TableField(exist = false) private static final long serialVersionUID = 1L;
}