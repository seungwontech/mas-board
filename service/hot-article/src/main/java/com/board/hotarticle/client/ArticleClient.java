package com.board.hotarticle.client;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class ArticleClient {

    private RestClient restClient;

    @Value("${endpoints.board-article-service.url}")
    private String articleServiceUrl;

    @PostConstruct
    void init() {
        restClient = RestClient.create(articleServiceUrl);
    }

    public ArticleRes read(Long articleId) {
        try {
            return restClient.get().uri("/v1/articles/{articleId}", articleId)
                    .retrieve().body(ArticleRes.class);
        } catch (Exception e) {
            log.error("[ArticleClient.read] articleId = {}", articleId, e);
        }
        return null;
    }

    public record ArticleRes(Long articleId, String title, String content, LocalDateTime createdAt) {
    }

}
