package com.board.article.controller;

import com.board.article.dto.ArticlePageRes;
import com.board.article.dto.ArticleRes;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

class ArticleControllerTest {

    RestClient restClient = RestClient.create("http://localhost:9001");

    @Test
    void getArticles() {
        ArticlePageRes response = restClient.get()
                .uri("/v1/articles?boardId=1&pageSize=30&page=50000")
                .retrieve()
                .body(ArticlePageRes.class);

        System.out.println("response.getArticleCount() = " + response.articleCount());
        for (ArticleRes article : response.articles()) {
            System.out.println("articleId = " + article.articleId());
        }
    }
}