package com.elliot.langchain4j.chatmemory.config;


import com.elliot.langchain4j.chatmemory.service.ChatMemoryAssistant;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.TokenWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiTokenCountEstimator;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static dev.langchain4j.model.openai.OpenAiChatModelName.GPT_4_O_MINI;


@Configuration
public class LLMConfig {

    @Value("${alibaba-api-key}")
    private String apiKey;


    @Bean
    public ChatModel alibabaChatMode() {
        return OpenAiChatModel.builder()
                .apiKey(apiKey)
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .modelName("qwen-turbo")
                .build();
    }

    @Bean
    public ChatMemory chatMemory() {
        return TokenWindowChatMemory.withMaxTokens(100, new OpenAiTokenCountEstimator(GPT_4_O_MINI));
    }

    @Bean
    public ChatMemoryAssistant chatMemoryAssistant(ChatModel chatModel) {
        return AiServices.builder(ChatMemoryAssistant.class)
                .chatModel(chatModel)
                .chatMemoryProvider(memoryId -> TokenWindowChatMemory.withMaxTokens(100, new OpenAiTokenCountEstimator(GPT_4_O_MINI)))
                .build();
    }

}
