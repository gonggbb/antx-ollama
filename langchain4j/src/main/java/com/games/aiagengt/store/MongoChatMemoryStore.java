package com.games.aiagengt.store;

import com.games.aiagengt.bean.ChatMessages;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;


/*
 *
 * 记忆存储
 * */

@Component
public class MongoChatMemoryStore implements ChatMemoryStore {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    // 重写getMessages方法，根据memoryId获取聊天消息
    public List<ChatMessage> getMessages(Object memoryId) {
        // 创建查询条件，memoryId等于传入的memoryId
        Criteria criteria = Criteria.where("memoryId").is(memoryId);
        // 创建查询对象
        Query query = new Query(criteria);
        // 根据查询对象从mongoTemplate中获取ChatMessages对象
        ChatMessages chatMessages = mongoTemplate.findOne(query, ChatMessages.class);
        // 如果获取到的ChatMessages对象为空，则返回一个空的LinkedList
        if (chatMessages == null) return new LinkedList<>();
        // 否则，将ChatMessages对象中的content字段转换为List<ChatMessage>类型并返回
        return ChatMessageDeserializer.messagesFromJson(chatMessages.getContent());
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> messages) {
        //创建查询条件，根据memoryId查询
        Criteria criteria = Criteria.where("memoryId").is(memoryId);
        //根据查询条件创建查询对象
        Query query = new Query(criteria);
        //创建更新对象
        Update update = new Update();
        //将消息列表序列化为json字符串，并设置到更新对象中
        update.set("content", ChatMessageSerializer.messagesToJson(messages));
//根据query条件能查询出文档，则修改文档；否则新增文档
        mongoTemplate.upsert(query, update, ChatMessages.class);
    }

    @Override
    public void deleteMessages(Object memoryId) {
        // 创建一个Criteria对象，用于查询条件
        Criteria criteria = Criteria.where("memoryId").is(memoryId);
        // 创建一个Query对象，用于执行查询
        Query query = new Query(criteria);
        // 使用mongoTemplate执行删除操作，删除ChatMessages类中满足查询条件的文档
        mongoTemplate.remove(query, ChatMessages.class);
    }

}
