package com.board.article.service;

import com.board.article.dto.ArticleCreateReq;
import com.board.article.dto.ArticlePageRes;
import com.board.article.dto.ArticleRes;
import com.board.article.dto.ArticleUpdateReq;
import com.board.article.entity.Article;
import com.board.article.entity.BoardArticleCount;
import com.board.article.repository.ArticleRepository;
import com.board.article.repository.BoardArticleCountRepository;
import com.board.common.event.EventType;
import com.board.common.event.payload.ArticleCreatedEventPayload;
import com.board.common.event.payload.ArticleDeletedEventPayload;
import com.board.common.event.payload.ArticleUpdatedEventPayload;
import com.board.common.outboxmessagerelay.OutboxEventPublisher;
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
    private final BoardArticleCountRepository boardArticleCountRepository;
    private final OutboxEventPublisher outboxEventPublisher;
    private final PageableHandlerMethodArgumentResolverCustomizer pageableCustomizer;


    @Transactional
    public ArticleRes create(ArticleCreateReq request) {
        Article article = articleRepository.save(
                Article.create(snowflake.nextId(), request.title(), request.content(), request.boardId(), request.memberId())
        );

        int result = boardArticleCountRepository.increase(article.getBoardId());
        if(result == 0 ) {
            boardArticleCountRepository.save(
                    BoardArticleCount.init(request.boardId(), 1L)
            );
        }
        outboxEventPublisher.publish(
                EventType.ARTICLE_CREATED,
                ArticleCreatedEventPayload.builder()
                        .articleId(article.getArticleId())
                        .title(article.getTitle())
                        .content(article.getContent())
                        .boardId(article.getBoardId())
                        .memberId(article.getMemberId())
                        .createdAt(article.getCreatedAt())
                        .updatedAt(article.getUpdatedAt())
                        .boardArticleCount(
                                count(article.getBoardId())
                        )
                        .build(),
                article.getBoardId()
        );

        return ArticleRes.from(article);
    }

    @Transactional
    public ArticleRes update(Long articleId, ArticleUpdateReq request) {
        Article article = articleRepository.findById(articleId).orElseThrow();
        article.update(request.title(), request.content());

        outboxEventPublisher.publish(
                EventType.ARTICLE_UPDATED,
                ArticleUpdatedEventPayload.builder()
                        .articleId(article.getArticleId())
                        .title(article.getTitle())
                        .content(article.getContent())
                        .boardId(article.getBoardId())
                        .memberId(article.getMemberId())
                        .createdAt(article.getCreatedAt())
                        .updatedAt(article.getUpdatedAt())
                        .build(),
                article.getBoardId()
        );

        return ArticleRes.from(article);
    }

    public ArticleRes read(Long articleId) {
        return ArticleRes.from(articleRepository.findById(articleId).orElseThrow());
    }

    @Transactional
    public void delete(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow();
        articleRepository.deleteById(articleId);
        boardArticleCountRepository.decrease(article.getBoardId());

        outboxEventPublisher.publish(
                EventType.ARTICLE_DELETED,
                ArticleDeletedEventPayload.builder()
                        .articleId(article.getArticleId())
                        .title(article.getTitle())
                        .content(article.getContent())
                        .boardId(article.getBoardId())
                        .memberId(article.getMemberId())
                        .createdAt(article.getCreatedAt())
                        .updatedAt(article.getUpdatedAt())
                        .build(),
                article.getBoardId()
        );
    }

    public ArticlePageRes readAll(Long boardId, Long page, Long pageSize) {

        List<ArticleRes> list = articleRepository.findAll(boardId, pageSize, (page - 1) * pageSize).stream().map(ArticleRes::from).toList();
        Long limit = PageLimitCalculator.calculatePageLimit(page, pageSize, 10L);
        Long total = articleRepository.count(boardId, limit);
        return ArticlePageRes.of(list, total);

    }

    public Long count(Long boardId) {
        return boardArticleCountRepository.findById(boardId).map(BoardArticleCount::getArticleCount).orElse(0L);
    }
}
