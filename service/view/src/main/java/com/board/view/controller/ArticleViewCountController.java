package com.board.view.controller;

import com.board.view.service.ArticleViewService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ArticleViewCountController {

    private final ArticleViewService articleViewService;


    @PostMapping("/v1/article-views/articles/{articleId}/users/{userId}")
    public ResponseEntity<Long> increase(@PathVariable("articleId") Long articleId, @PathVariable("userId") Long userId) {
        return ResponseEntity.ok(articleViewService.increase(articleId, userId));
    }

    @GetMapping("/v1/article-views/articles/{articleId}/count")
    public ResponseEntity<Long> count(@PathVariable("articleId") Long articleId) {
        return ResponseEntity.ok(articleViewService.count(articleId));
    }
}
