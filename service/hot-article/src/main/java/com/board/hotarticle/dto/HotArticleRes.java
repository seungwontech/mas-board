package com.board.hotarticle.dto;

import com.board.hotarticle.client.ArticleClient;

import java.time.LocalDateTime;

public record HotArticleRes(Long articleId, String title, LocalDateTime createdAt) {

    public static HotArticleRes from(ArticleClient.ArticleRes article) {
        return new HotArticleRes(article.articleId(), article.title(), article.createdAt());
    }
}
