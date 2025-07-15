package com.elliot.langchain4j.chatimage.controller;

import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.community.model.dashscope.WanxImageModel;
import dev.langchain4j.data.image.Image;
import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.output.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

@RestController
@RequestMapping("/api/chat/image")
@RequiredArgsConstructor
@Slf4j
public class ChatImageController {

    private final ChatModel imageChatModel;

    private final WanxImageModel imageModel;
    

    @GetMapping("/test")
    public String descPic() {
        try (InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("test.png")) {
            if (resourceAsStream == null) {
                throw new RuntimeException("file not found");
            }
            byte[] bytes = resourceAsStream.readAllBytes();
            String picBase64 = Base64.getEncoder().encodeToString(bytes);
            ImageContent imageContent = ImageContent.from(picBase64, "image/png");
            ChatResponse chatResponse = imageChatModel.chat(UserMessage.from(TextContent.from("这个是什么"),
                    imageContent));
            log.info(chatResponse.toString());
            return chatResponse.aiMessage().text();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据文字生成图片
     *
     * @param desc
     * @return
     */
    @GetMapping("/generator")
    public String generatorPicByDesc(@RequestParam String desc) {
        Response<Image> response = imageModel.generate(desc);
        log.info(response.toString());
        return response.content().url().toString();
    }


}
