package com.elliot.langchain4j.tool.controller;

import com.elliot.langchain4j.tool.service.WeatherService;
import com.elliot.langchain4j.tool.service.WeatherToolService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@Slf4j
@RequestMapping("/api/tool")
@RequiredArgsConstructor
public class ToolController {

    private final WeatherService weatherService;

    private final WeatherToolService weatherToolService;


    @GetMapping("")
    public Flux<String> getTool(String message) {
        return weatherService.chat(message);
    }

    @GetMapping("/advance")
    public Flux<String> getWeatherTool(String message) {
        return weatherToolService.chat(message);
    }

}
