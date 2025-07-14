package com.elliot.langchain4j.parameter.config;


import com.elliot.langchain4j.parameter.ChatListener;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.List;

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
                .logRequests(true)
                .logResponses(true) //开启日志
                .listeners(List.of(new ChatListener())) //监控
                .maxRetries(3)       //重试
                .timeout(Duration.ofSeconds(3))   // 重试超时时间
                .build();
    }

}
