package com.elliot.langchain4j.tool.service;

import reactor.core.publisher.Flux;

public interface WeatherToolService {
    Flux<String> chat(String message);
}
