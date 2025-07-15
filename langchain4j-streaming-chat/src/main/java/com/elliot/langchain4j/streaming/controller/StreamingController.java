package com.elliot.langchain4j.streaming.controller;

import com.elliot.langchain4j.streaming.service.StreamingChatService;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@Slf4j
@RequestMapping("/api/streaming")
@RequiredArgsConstructor
public class StreamingController {

    private final StreamingChatModel streamingChatModel;

    private final StreamingChatService streamingChatService;

    @GetMapping("chat")
    public Flux<String> getStreamingChatModel(@RequestParam(defaultValue = "世界最好吃的东西是什么") String message) {
        return Flux.create(msg -> {
            streamingChatModel.chat(message, new StreamingChatResponseHandler() {
                @Override
                public void onPartialResponse(String partialResponse) {
                    msg.next(partialResponse);
                }

                @Override
                public void onCompleteResponse(ChatResponse completeResponse) {
                    log.info("complete response: {}", completeResponse);
                    msg.complete();
                }

                @Override
                public void onError(Throwable error) {
                    msg.error(error);
                }
            });
        });
    }

    @GetMapping("/chat2")
    public Flux<String> getAdvanceStreamingChatModel(@RequestParam(defaultValue = "世界最好吃的东西是什么") String message) {
        return streamingChatService.chat(message);
    }

}
