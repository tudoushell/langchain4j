package com.elliot.langchain4j.tool.service;

import reactor.core.publisher.Flux;

public interface WeatherService {
    
    Flux<String> chat(String message);
}
