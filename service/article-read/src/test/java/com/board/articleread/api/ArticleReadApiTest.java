package com.board.articleread.api;

import com.board.articleread.service.response.ArticleReadPageResponse;
import com.board.articleread.service.response.ArticleReadResponse;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

public class ArticleReadApiTest {
    RestClient articleReadRestClient = RestClient.create("http://localhost:9002");
    RestClient articleRestClient = RestClient.create("http://localhost:9001");

    @Test
    void readTest() {
        ArticleReadResponse response = articleReadRestClient.get()
                .uri("/v1/articles/{articleId}", 255245290905579520L)
                .retrieve()
                .body(ArticleReadResponse.class);

        System.out.println("response = " + response);
    }

    @Test
    void readAllTest() {
        ArticleReadPageResponse response1 = articleReadRestClient.get()
                .uri("/v1/articles?boardId=%s&page=%s&pageSize=%s".formatted(1L, 5L, 5))
                .retrieve()
                .body(ArticleReadPageResponse.class);

        System.out.println("response1.getArticleCount() = " + response1.articleCount());
        for (ArticleReadResponse article : response1.articles()) {
            System.out.println("article.getArticleId() = " + article.getArticleId());
        }

        ArticleReadPageResponse response2 = articleRestClient.get()
                .uri("/v1/articles?boardId=%s&page=%s&pageSize=%s".formatted(1L, 5L, 5))
                .retrieve()
                .body(ArticleReadPageResponse.class);

        System.out.println("response2.getArticleCount() = " + response2.articleCount());
        for (ArticleReadResponse article : response2.articles()) {
            System.out.println("article.getArticleId() = " + article.getArticleId());
        }
    }

}
