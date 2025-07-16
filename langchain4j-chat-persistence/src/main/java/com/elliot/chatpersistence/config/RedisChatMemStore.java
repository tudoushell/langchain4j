package com.elliot.chatpersistence.config;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RedisChatMemStore implements ChatMemoryStore {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final String CHAT_MEMORY_PREFIX = "LLM_";

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        String id = CHAT_MEMORY_PREFIX + memoryId;
        return ChatMessageDeserializer.messagesFromJson(redisTemplate.opsForValue().get(id));
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> messages) {
        String id = CHAT_MEMORY_PREFIX + memoryId;
        redisTemplate.opsForValue().set(id, ChatMessageSerializer.messagesToJson(messages));
    }

    @Override
    public void deleteMessages(Object memoryId) {
        redisTemplate.delete(CHAT_MEMORY_PREFIX + memoryId);
    }
}
