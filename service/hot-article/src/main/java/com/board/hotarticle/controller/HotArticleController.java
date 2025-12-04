package com.board.hotarticle.controller;

import com.board.hotarticle.dto.HotArticleRes;
import com.board.hotarticle.service.HotArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class HotArticleController {

    private final HotArticleService hotArticleService;

    @GetMapping("/v1/hot-articles/articles/date/{dataStr}")
    public ResponseEntity<List<HotArticleRes>> readAll(@PathVariable("dataStr") String dataStr) {
        System.out.println(dataStr);
        return ResponseEntity.ok(hotArticleService.readAll(dataStr));
    }
}
