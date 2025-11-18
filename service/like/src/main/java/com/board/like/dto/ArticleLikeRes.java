package com.board.like.dto;

import com.board.like.entity.ArticleLike;

import java.time.LocalDateTime;

public record ArticleLikeRes(Long likeId, Long articleId, LocalDateTime createdAt) {
    public static ArticleLikeRes from(ArticleLike like) {
        return new ArticleLikeRes(like.getArticleLikeId(), like.getArticleId(), like.getCreatedAt());
    }
}
