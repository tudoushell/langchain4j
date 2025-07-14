package com.elliot.langchain4j.lowhigh.config;

import com.elliot.langchain4j.lowhigh.service.HighChatService;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LLMConfig {

    @Value("${alibaba-api-key}")
    private String apiKey;

    @Bean("chatGpt")
    public ChatModel chatGpt() {
        return OpenAiChatModel.builder()
                .baseUrl("http://langchain4j.dev/demo/openai/v1")
                .apiKey("demo")
                .modelName("gpt-4o-mini")
                .build();
    }

    @Bean(name = "qwen")
    public ChatModel alibabaChatMode() {
        return OpenAiChatModel.builder()
                .apiKey(apiKey)
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .modelName("qwen-turbo")
                .build();
    }

    /**
     * 通过AiServices去调用
     *
     * @param chatModel
     * @return
     */
    @Bean
    public HighChatService highChatService(@Qualifier("chatGpt") ChatModel chatModel) {
        return AiServices.create(HighChatService.class, chatModel);
    }
}
