package com.elliot.langchain4j.prompt.service;

import com.elliot.langchain4j.prompt.entity.StructuredPromptExample;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import reactor.core.publisher.Flux;

public interface ComputerAssistant {


    Flux<String> chat(dev.langchain4j.data.message.UserMessage userMessage);

    Flux<String> chat(StructuredPromptExample structuredPromptExample);

    @SystemMessage("你是计算机科学与技术的老师，你只能回答计算机科学与技术专业的知识，不是相关专业的问题，回复：暂不能回答非计算机问题")
    Flux<String> prompt(@UserMessage String question);

    @SystemMessage("你是计算机科学与技术的老师，你只能回答计算机科学与技术专业的知识，不是相关专业的问题，回复：暂不能回答非计算机问题")
    @UserMessage("计算机{{question}}是什么，字数限制在10个")
    Flux<String> prompt2(@V("question") String question);
}
