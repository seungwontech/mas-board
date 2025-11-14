package com.board.article.controller;

import com.board.article.dto.ArticleCreateReq;
import com.board.article.dto.ArticlePageRes;
import com.board.article.dto.ArticleRes;
import com.board.article.dto.ArticleUpdateReq;
import com.board.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/v1/articles/{articleId}")
    public ResponseEntity<ArticleRes> getArticle(@PathVariable Long articleId){
        return ResponseEntity.ok(articleService.read(articleId));
    }

    @GetMapping("/v1/articles")
    public ResponseEntity<ArticlePageRes> getArticles(
            @RequestParam("boardId") Long boardId,
            @RequestParam("page") Long page,
            @RequestParam("pageSize") Long pageSize) {
        return ResponseEntity.ok(articleService.readAll(boardId, page, pageSize));
    }

    @PostMapping("/v1/articles")
    public ResponseEntity<ArticleRes> create(@RequestBody ArticleCreateReq articleCreateReq){
        return ResponseEntity.ok(articleService.create(articleCreateReq));
    }

    @PutMapping("/v1/articles/{articleId}")
    public ResponseEntity<ArticleRes> update(@PathVariable Long articleId, @RequestBody ArticleUpdateReq articleUpdateReq){
        return ResponseEntity.ok(articleService.update(articleId, articleUpdateReq));
    }

    @DeleteMapping("/v1/articles/{articleId}")
    public ResponseEntity<Void> delete(@PathVariable Long articleId){
        articleService.delete(articleId);
        return ResponseEntity.noContent().build();
    }
}
