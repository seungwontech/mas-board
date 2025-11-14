package com.board.article.dto;

import java.util.List;

public record ArticlePageRes(List<ArticleRes> articles, Long articleCount) {
    public static ArticlePageRes of(List<ArticleRes> articles, Long articleCount) {
        return new ArticlePageRes(articles, articleCount);
    }
}
