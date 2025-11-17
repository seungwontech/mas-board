package com.board.comment.dto;

import java.util.List;

public record CommentPageRes(List<CommentRes> comments, Long commentCount) {
    public static CommentPageRes of(List<CommentRes> comments, Long commentCount) {
        return new CommentPageRes(comments, commentCount);
    }
}
