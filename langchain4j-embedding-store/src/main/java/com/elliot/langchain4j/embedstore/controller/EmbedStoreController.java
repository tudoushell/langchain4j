package com.elliot.langchain4j.embedstore.controller;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.filter.comparison.IsEqualTo;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.grpc.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/embedding-store")
@RestController
@RequiredArgsConstructor
public class EmbedStoreController {

    private final EmbeddingModel embeddingModel;

    private final EmbeddingStore<TextSegment> embeddingStore;

    private final QdrantClient qdrantClient;

    @GetMapping("/get")
    public String getEmbeddingStoreStr(String message) {
        TextSegment segment1 = TextSegment.from(message);
        Response<Embedding> embed = embeddingModel.embed(segment1);
        log.info("Embedding added: {}", embed.content());
        return embed.content().toString();
    }

    @GetMapping("/create-collection")
    public void createCollection() {
        try {
            Collections.CollectionOperationResponse collectionOperationResponse = qdrantClient.createCollectionAsync("test-qdrant",
                    Collections.VectorParams.newBuilder()
                            .setDistance(Collections.Distance.Cosine)
                            .setSize(1024)
                            .build()).get();
            log.info(collectionOperationResponse.toString());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @GetMapping("/delete-collection")
    public void deleteCollection() {
        try {
            Collections.CollectionOperationResponse collectionOperationResponse = qdrantClient.deleteCollectionAsync("test-qdrant").get();
            log.info(collectionOperationResponse.toString());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @GetMapping("/add")
    public String add() {
        TextSegment segment = TextSegment.from("Hello World");
        segment.metadata().put("id", "first");
        Embedding embedding = embeddingModel.embed(segment).content();
        String result = embeddingStore.add(embedding, segment);
        log.info(result);
        return result;
    }

    @GetMapping("/query")
    public String query() {
        TextSegment segment = TextSegment.from("World");
        Embedding embedding = embeddingModel.embed(segment).content();

        EmbeddingSearchRequest searchRequest = EmbeddingSearchRequest.builder()
                .queryEmbedding(embedding)
                .maxResults(1)
                .build();
        EmbeddingSearchResult<TextSegment> search = embeddingStore.search(searchRequest);
        if (!CollectionUtils.isEmpty(search.matches())) {
            return search.matches().get(0).embedded().text();
        }
        return null;
    }

    @GetMapping("/query2")
    public String query2() {
        TextSegment segment = TextSegment.from("d");
        Embedding embedding = embeddingModel.embed(segment).content();
        EmbeddingSearchRequest searchRequest = EmbeddingSearchRequest.builder()
                .filter(new IsEqualTo("id", "first"))
                .queryEmbedding(embedding)
                .maxResults(1)
                .build();
        EmbeddingSearchResult<TextSegment> search = embeddingStore.search(searchRequest);
        if (!CollectionUtils.isEmpty(search.matches())) {
            return search.matches().get(0).embedded().text();
        }
        return null;
    }


}
