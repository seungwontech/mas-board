package com.board.article.dto;

import com.board.article.entity.Article;

import java.time.LocalDateTime;

public record ArticleRes(
        Long articleId,
        String title,
        String content,
        Long boardId,
        Long memberId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ArticleRes from(Article entity) {
        return new ArticleRes(
                entity.getArticleId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getBoardId(),
                entity.getMemberId(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
