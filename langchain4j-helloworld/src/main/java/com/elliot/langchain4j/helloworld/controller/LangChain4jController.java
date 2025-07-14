package com.elliot.langchain4j.helloworld.controller;

import dev.langchain4j.model.chat.ChatModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api")
@RestController
public class LangChain4jController {

    @Qualifier("qwen")
    @Autowired
    private ChatModel chatModel;

    @Qualifier("chatGpt")
    @Autowired
    private ChatModel gptModel;

    @GetMapping("/qwen")
    public String chat(@RequestParam(defaultValue = "你是谁") String message) {
        String chat = chatModel.chat(message);
        log.info("answer: {}", chat);
        return chat;
    }

    @GetMapping("/gpt")
    public String gptChat(@RequestParam(defaultValue = "你是谁") String message) {
        String chat = gptModel.chat(message);
        log.info("answer: {}", chat);
        return chat;
    }
}
