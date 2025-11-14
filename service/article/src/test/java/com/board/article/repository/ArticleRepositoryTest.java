package com.board.article.repository;

import com.board.article.entity.Article;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@Slf4j
@SpringBootTest
class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    void findAllTest() {
        List<Article> list = articleRepository.findAll(1L, 30L, 1455580L);
        log.info("list.size = {}", list.size());

        for(Article article : list){
            log.info("article = {}", article);
        }
    }
    @Test
    void countTest() {
        Long count = articleRepository.count(1L, 30L);

        log.info("count = {}", count);
    }
}