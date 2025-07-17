package com.elliot.langchain4j.tool.config;


import com.elliot.langchain4j.tool.service.WeatherService;
import com.elliot.langchain4j.tool.service.WeatherTool;
import com.elliot.langchain4j.tool.service.WeatherToolService;
import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.chat.request.json.JsonObjectSchema;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.tool.ToolExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;


@Slf4j
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


    @Bean
    public WeatherService weatherService(StreamingChatModel chatModel) {
        //根据用户输入出的话，匹配关键字，进行工具调用
        //工具定义
        ToolSpecification toolSpecification = ToolSpecification.builder()
                .name("天气查询")
                .description("城市天气查询")
                .parameters(JsonObjectSchema.builder()
                        .addStringProperty("city", "城市")
                        .build())
                .build();
        //工具调用 可写业务逻辑，同时返回给用户，不再通过大模型处理
        //工具实现
        ToolExecutor toolExecutor = (toolExecutionRequest, memoryId) -> {
            log.info(toolExecutionRequest.id());
            log.info(toolExecutionRequest.name());
            log.info("argus --> {}", toolExecutionRequest.arguments());
            return "天气为雨天";
        };

        return AiServices.builder(WeatherService.class)
                .streamingChatModel(chatModel)
                .tools(Map.of(toolSpecification, toolExecutor))
                .build();
    }

    @Bean
    public WeatherToolService weatherToolService(StreamingChatModel chatModel) {
        return AiServices.builder(WeatherToolService.class)
                .streamingChatModel(chatModel)
                .tools(new WeatherTool())
                .build();
    }

}
