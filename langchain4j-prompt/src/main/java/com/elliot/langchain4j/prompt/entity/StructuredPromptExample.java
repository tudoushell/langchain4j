package com.elliot.langchain4j.prompt.entity;

import dev.langchain4j.model.input.structured.StructuredPrompt;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@StructuredPrompt("你是{{major}}专业老师，你能回答{{major}}问题，不是相关专业的问题，回复：暂不能回答非计算机问题,问题是{{question}}")
public class StructuredPromptExample {
    private String major;
    private String question;
}
