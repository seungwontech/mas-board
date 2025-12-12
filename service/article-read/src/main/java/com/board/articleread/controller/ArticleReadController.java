package com.board.articleread.controller;

import com.board.articleread.service.ArticleReadService;
import com.board.articleread.service.response.ArticleReadPageResponse;
import com.board.articleread.service.response.ArticleReadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ArticleReadController {

    private final ArticleReadService articleReadService;

    @GetMapping("/v1/articles/{articleId}")
    public ResponseEntity<ArticleReadResponse> read(@PathVariable Long articleId) {
        return ResponseEntity.ok(articleReadService.read(articleId));
    }


    @GetMapping("/v1/articles")
    public ResponseEntity<ArticleReadPageResponse> readAll(
            @RequestParam("boardId") Long boardId,
            @RequestParam("page") Long page,
            @RequestParam("pageSize") Long pageSize
    ) {
        return ResponseEntity.ok(articleReadService.readAll(boardId, page, pageSize));
    }
}
