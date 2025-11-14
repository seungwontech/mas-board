package com.board.article.service;

import com.board.article.dto.ArticleCreateReq;
import com.board.article.dto.ArticlePageRes;
import com.board.article.dto.ArticleRes;
import com.board.article.dto.ArticleUpdateReq;
import com.board.article.entity.Article;
import com.board.article.repository.ArticleRepository;
import com.board.common.snowflake.Snowflake;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ArticleService {
    private final Snowflake snowflake = new Snowflake();
    private final ArticleRepository articleRepository;
    private final PageableHandlerMethodArgumentResolverCustomizer pageableCustomizer;


    @Transactional
    public ArticleRes create(ArticleCreateReq request) {
        Article article = articleRepository.save(
                Article.create(snowflake.nextId(), request.title(), request.content(), request.boardId(), request.memberId())
        );
        return ArticleRes.from(article);
    }

    @Transactional
    public ArticleRes update(Long articleId, ArticleUpdateReq request) {
        Article article = articleRepository.findById(articleId).orElseThrow();
        article.update(request.title(), request.content());
        return ArticleRes.from(article);
    }

    public ArticleRes read(Long articleId) {
        return ArticleRes.from(articleRepository.findById(articleId).orElseThrow());
    }

    @Transactional
    public void delete(Long articleId) {
        articleRepository.deleteById(articleId);
    }

    public ArticlePageRes readAll(Long boardId, Long page, Long pageSize) {

        List<ArticleRes> list = articleRepository.findAll(boardId, pageSize, (page - 1) * pageSize).stream().map(ArticleRes::from).toList();
        Long limit = PageLimitCalculator.calculatePageLimit(page, pageSize, 10L);
        Long total = articleRepository.count(boardId, limit);
        return ArticlePageRes.of(list, total);

    }
}
