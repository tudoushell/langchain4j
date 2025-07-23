package com.elliot.langchain4j.rag.service;

import reactor.core.publisher.Flux;

public interface EasyRagService {
    Flux<String> chat(String message);
}
