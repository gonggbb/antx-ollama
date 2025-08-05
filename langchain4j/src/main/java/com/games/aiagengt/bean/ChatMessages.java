package com.games.aiagengt.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


/*
*
* @Data
Lombok 注解，自动生成 getter、setter、toString、equals、hashCode 等方法。
*
@AllArgsConstructor
Lombok 注解，生成全参构造函数。
*
@NoArgsConstructor
Lombok 注解，生成无参构造函数（必要，用于 Spring 和 MongoDB 反序列化）。
*
@Document("chat_message")
Spring Data MongoDB 注解，表示该类映射到 MongoDB 中名为 chat_message 的集合（collection）。
*
*
* @Id
表示这是文档的主键，MongoDB 默认使用 _id 字段存储。
*
ObjectId
MongoDB 提供的唯一标识类型，适合用作主键。比 Long 更安全且支持分布式系统。
⚠️ 注意：如果你希望使用 String 或 Long 类型作为 ID，需要配合自定义 ID 生成策略。
*
*
* */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("chat_message")
public class ChatMessages {
    //唯一标识，映射到 MongoDB 文档的 _id 字段
    @Id
    private ObjectId id;

    private int messageId;

    //private Long messageId;
    private String content; //存储当前聊天记录列表的json字符串
}
