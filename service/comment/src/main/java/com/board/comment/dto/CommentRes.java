package com.board.comment.dto;

import com.board.comment.entity.Comment;

import java.time.LocalDateTime;

public record CommentRes(
        Long commentId,
        String content,
        Long parentCommentId,
        Long articleId,
        Long memberId,
        Boolean deleted,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static CommentRes from(Comment entity) {
        return new CommentRes(
                entity.getCommentId(), entity.getContent(), entity.getParentCommentId(),
                entity.getArticleId(), entity.getMemberId(), entity.getDeleted(),
                entity.getCreatedAt(), entity.getUpdatedAt());
    }
}
