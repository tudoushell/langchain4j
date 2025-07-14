package com.elliot.langchain4j.lowhigh.controller;

import com.elliot.langchain4j.lowhigh.service.HighChatService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/high")
@RestController
@AllArgsConstructor
public class HighApiController {

    private final HighChatService highChatService;


    /**
     * 高阶API，通过自定义的接口去调用
     *
     * @param message
     * @return
     */
    @GetMapping("/message")
    public String highChatMessage(@RequestParam(defaultValue = "你是谁") String message) {
        return highChatService.highChat(message);
    }


}
