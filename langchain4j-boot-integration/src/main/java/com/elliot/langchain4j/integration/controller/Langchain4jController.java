package com.elliot.langchain4j.integration.controller;

import com.elliot.langchain4j.integration.service.ChatAssistant;
import dev.langchain4j.model.chat.ChatModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class Langchain4jController {

    private final ChatModel chatModel;

    private final ChatAssistant qwenChatAssistant;

    @GetMapping("/chat")
    public String message(@RequestParam(defaultValue = "你是谁") String message) {
        String answer = chatModel.chat(message);
        log.info(answer);
        return answer;
    }


    @GetMapping("/chat/qwen")
    public String chatQwen(@RequestParam(defaultValue = "你是谁") String message) {
        String answer = qwenChatAssistant.sendMessage(message);
        log.info(answer);
        return answer;
    }

}
