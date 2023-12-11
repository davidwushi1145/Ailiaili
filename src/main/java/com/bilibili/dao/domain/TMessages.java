package com.bilibili.dao.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 消息通信表
 * @TableName t_messages
 */
@TableName(value = "t_messages")
@Data
public class TMessages implements Serializable {
  /**
   * 主键id
   */
  @TableId(value = "id", type = IdType.AUTO) private Long id;

  /**
   * 发送者id
   */
  @TableField(value = "sender_id") private Long senderId;

  /**
   * 接收者id
   */
  @TableField(value = "receiver_id") private Long receiverId;

  /**
   * 消息内容
   */
  @TableField(value = "message") private String message;

  /**
   * 发送时间
   */
  @TableField(value = "send_time") private Date sendTime;

  @TableField(exist = false) private static final long serialVersionUID = 1L;
}
