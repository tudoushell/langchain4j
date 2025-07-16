package com.elliot.langchain4j.chatmemory.controller;

import com.elliot.langchain4j.chatmemory.service.ChatMemoryAssistant;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/chat/memory")
@RequiredArgsConstructor
public class ChatMemoryController {

    private final ChatModel chatModel;

    private final ChatMemory chatMemory;

    private final ChatMemoryAssistant chatMemoryAssistant;

    private final static int memoryId = 1;

    @GetMapping("")
    public String chatMemory(String message) {
        chatMemory.add(UserMessage.from(message));
        return chatModel.chat(chatMemory.messages()).aiMessage().text();
    }

    @GetMapping("/advance")
    public String chatMemory2(String message) {
        return chatMemoryAssistant.chat(memoryId, message);
    }
}
