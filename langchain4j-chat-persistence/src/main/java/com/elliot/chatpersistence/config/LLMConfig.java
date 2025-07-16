package com.elliot.chatpersistence.config;


import com.elliot.chatpersistence.service.ChatPersistService;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.service.AiServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class LLMConfig {

    @Value("${alibaba-api-key}")
    private String apiKey;

    private final RedisChatMemStore redisChatMemStore;


    @Bean
    public StreamingChatModel alibabaChatMode() {
        return OpenAiStreamingChatModel.builder()
                .apiKey(apiKey)
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .modelName("qwen-turbo")
                .build();
    }

    @Bean
    public ChatPersistService chatPersistService(StreamingChatModel streamingChatModel) {
        MessageWindowChatMemory chatMemory = MessageWindowChatMemory.builder()
                .maxMessages(100)
                .chatMemoryStore(redisChatMemStore)
                .build();
        return AiServices.builder(ChatPersistService.class)
                .streamingChatModel(streamingChatModel)
                .chatMemoryProvider(memoryId -> chatMemory)
                .build();
    }
}
