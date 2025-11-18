package com.board.like.controller;

import com.board.like.dto.ArticleLikeRes;
import com.board.like.service.ArticleLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ArticleLikeController {

    private final ArticleLikeService articleLikeService;

    @GetMapping("/v1/article-likes/articles/{articleId}/members/{memberId}")
    public ResponseEntity<ArticleLikeRes> read(@PathVariable Long articleId, @PathVariable Long memberId) {
        return ResponseEntity.ok(articleLikeService.read(articleId, memberId));
    }

    @GetMapping("/v1/article-likes/articles/{articleId}/count")
    public ResponseEntity<Long> count(@PathVariable Long articleId) {
        return ResponseEntity.ok(articleLikeService.count(articleId));
    }

    @PostMapping("/v1/article-likes/articles/{articleId}/members/{memberId}/like/nopess")
    public ResponseEntity<Void> like(@PathVariable Long articleId, @PathVariable Long memberId){
        articleLikeService.like(articleId, memberId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/v1/article-likes/articles/{articleId}/members/{memberId}/unlike/nopess")
    public ResponseEntity<Void> unlike(@PathVariable Long articleId, @PathVariable Long memberId){
        articleLikeService.unlike(articleId, memberId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/v1/article-likes/articles/{articleId}/members/{memberId}/like/pess")
    public ResponseEntity<Void> likePessimisticLock(@PathVariable Long articleId, @PathVariable Long memberId){
        articleLikeService.likePessimisticLock(articleId, memberId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/v1/article-likes/articles/{articleId}/members/{memberId}/unlike/pess")
    public ResponseEntity<Void> unlikePessimisticLock(@PathVariable Long articleId, @PathVariable Long memberId){
        articleLikeService.unlikePessimisticLock(articleId, memberId);
        return ResponseEntity.noContent().build();
    }

}
