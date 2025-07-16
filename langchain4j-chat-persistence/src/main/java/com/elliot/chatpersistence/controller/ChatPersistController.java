package com.elliot.chatpersistence.controller;

import com.elliot.chatpersistence.service.ChatPersistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/persist")
@RequiredArgsConstructor
@Slf4j
public class ChatPersistController {
    private final ChatPersistService chatPersistService;

    @GetMapping("")
    public Flux<String> persisMemory(String message) {
        return chatPersistService.chat("1", message);
    }

}
