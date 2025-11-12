package com.board.article.dto;

public record ArticleCreateReq(String title, String content, Long memberId, Long boardId) {
}
