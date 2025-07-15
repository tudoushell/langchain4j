package com.elliot.langchain4j.streaming.config;


import com.elliot.langchain4j.streaming.service.StreamingChatService;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class LLMConfig {

    @Value("${alibaba-api-key}")
    private String apiKey;


    @Bean
    public StreamingChatModel streamingChatModel() {
        return OpenAiStreamingChatModel.builder()
                .apiKey(apiKey)
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .modelName("qwen-turbo")
                .build();
    }


    /**
     * 高阶用法，通过接口
     * @param chatModel
     * @return
     */
    @Bean
    public StreamingChatService streamingChatService(StreamingChatModel chatModel) {
        return AiServices.create(StreamingChatService.class, chatModel);
    }

}
