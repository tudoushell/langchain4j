package com.elliot.chatpersistence.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;
import reactor.core.publisher.Flux;

public interface ChatPersistService {
    Flux<String> chat(@MemoryId String id, @UserMessage String message);
}
