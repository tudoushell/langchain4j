package com.elliot.langchain4j.tool.service;

import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.service.V;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WeatherTool {
    @Tool(name = "天气查询", value = {"城市天气查询"})
    public String getWeather(@V("城市") String city) {
        log.info("city -> " + city);
        return "雨天";
    }
}
