package com.elliot.langchain4j.parameter;

import dev.langchain4j.model.chat.listener.ChatModelErrorContext;
import dev.langchain4j.model.chat.listener.ChatModelListener;
import dev.langchain4j.model.chat.listener.ChatModelRequestContext;
import dev.langchain4j.model.chat.listener.ChatModelResponseContext;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class ChatListener implements ChatModelListener {

    @Override
    public void onRequest(ChatModelRequestContext requestContext) {
        String traceId = UUID.randomUUID().toString();
        requestContext.attributes().put("traceId", traceId);
        log.info("------> onRequest traceId: {}", traceId);

    }

    @Override
    public void onResponse(ChatModelResponseContext responseContext) {
        String traceId = responseContext.attributes().get("traceId").toString();
        log.info("------> onResponse traceId: {}", traceId);
    }

    @Override
    public void onError(ChatModelErrorContext errorContext) {
        log.info("------> onError");
    }
}
