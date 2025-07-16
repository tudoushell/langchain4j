package com.elliot.langchain4j.chatmemory.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;

public interface ChatMemoryAssistant {
    String chat(@MemoryId int memoryId, @UserMessage String userMessage);
}
