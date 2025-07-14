package com.elliot.langchain4j.integration.service;

import dev.langchain4j.service.spring.AiService;

@AiService
public interface ChatAssistant {
    String sendMessage(String message);
}
