package com.elliot.langchain4j.prompt.controller;

import com.elliot.langchain4j.prompt.entity.StructuredPromptExample;
import com.elliot.langchain4j.prompt.service.ComputerAssistant;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/prompt")
@RequiredArgsConstructor
public class PromptController {

    private final ComputerAssistant computerAssistant;

    @GetMapping("/cs")
    public Flux<String> computerAnswer(String question) {
        return computerAssistant.prompt(question);
    }

    @GetMapping("/cs2")
    public Flux<String> computerAnswer2(String question) {
        return computerAssistant.prompt2(question);
    }

    @GetMapping("/cs3")
    public Flux<String> computerAnswer3(String question) {
        StructuredPromptExample structuredPromptExample = new StructuredPromptExample();
        structuredPromptExample.setMajor("计算机科学与技术");
        structuredPromptExample.setQuestion(question);
        return computerAssistant.chat(structuredPromptExample);
    }

    @GetMapping("/cs4")
    public Flux<String> computerAnswer4(String question) {
        PromptTemplate promptTemplate = PromptTemplate.from("你是{{major}}专业老师，你能回答{{major}}问题，不是相关专业的问题，回复：暂不能回答非计算机问题,问题是{{question}}");
        Map<String, Object> variables = new HashMap<>();
        variables.put("major", "计算机科学与技术");
        variables.put("question", question);
        Prompt prompt = promptTemplate.apply(variables);
        return computerAssistant.chat(prompt.toUserMessage());
    }


}
