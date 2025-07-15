package com.elliot.langchain4j.streaming.service;

import reactor.core.publisher.Flux;

public interface StreamingChatService {
    Flux<String> chat(String message);
}
