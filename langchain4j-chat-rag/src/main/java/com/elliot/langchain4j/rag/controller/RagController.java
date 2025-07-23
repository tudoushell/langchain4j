package com.elliot.langchain4j.rag.controller;

import com.elliot.langchain4j.rag.service.EasyRagService;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.apache.tika.ApacheTikaDocumentParser;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.nio.file.Paths;

@RequestMapping("/api/rag")
@Slf4j
@RequiredArgsConstructor
@RestController
public class RagController {

    private final EasyRagService easyRagService;


    private final EmbeddingStore<TextSegment> embeddingStore;

    private final EmbeddingModel embeddingModel;

    @GetMapping("/easy")
    public Flux<String> chat(String message) {
        //
        ClassPathResource classPathResource = new ClassPathResource("easyRag.txt");
        Document document = null;
        try {
            //load file
            document = FileSystemDocumentLoader.loadDocument(Paths.get(classPathResource.getURL().toURI()), new ApacheTikaDocumentParser());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //build store
        EmbeddingStoreIngestor embeddingStoreIngestor = EmbeddingStoreIngestor
                .builder()
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build();
        embeddingStoreIngestor.ingest(document);
        return easyRagService.chat(message);
    }
}
