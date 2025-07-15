package com.elliot.langchain4j.chatimage.config;


import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.community.model.dashscope.WanxImageModel;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class LLMConfig {

    @Value("${alibaba-api-key}")
    private String apiKey;

    @Bean
    public ChatModel picChatModel() {
        return OpenAiChatModel.builder()
                .apiKey(apiKey)
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .modelName("qwen-vl-max")
                .build();
    }

    @Bean
    public WanxImageModel picQwenModel() {
        return WanxImageModel.builder()
                .modelName("wanx2.1-t2i-plus")
                .apiKey(apiKey)
                .build();
    }
}
