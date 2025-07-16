package com.elliot.langchain4j.prompt.config;


import com.elliot.langchain4j.prompt.service.ComputerAssistant;
import dev.langchain4j.model.chat.StreamingChatModel;
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
    public StreamingChatModel alibabaChatMode() {
        return OpenAiStreamingChatModel.builder()
                .apiKey(apiKey)
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .modelName("qwen-turbo")
                .build();
    }


    @Bean
    public ComputerAssistant computerAssistant(StreamingChatModel chatModel) {
        return AiServices.builder(ComputerAssistant.class).streamingChatModel(chatModel).build();
    }

}
