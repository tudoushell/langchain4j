package com.elliot.langchain4j.lowhigh.controller;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/low")
@RestController
@RequiredArgsConstructor
public class LowApiController {

    @Qualifier("qwen")
    @Autowired
    private ChatModel qwenChatModel;


    /**
     * 低阶可以调用底层API查看信息
     *
     * @param message
     * @return
     */
    @GetMapping("/message/token")
    public String chatMessage(@RequestParam(defaultValue = "你是谁") String message) {
        ChatResponse chat = qwenChatModel.chat(UserMessage.from(message));
        log.info(chat.toString());
        return chat.toString();
    }
}
