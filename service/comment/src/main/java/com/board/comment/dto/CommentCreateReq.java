package com.board.comment.dto;

public record CommentCreateReq(Long articleId, String content, Long parentCommentId, Long memberId) {
}
