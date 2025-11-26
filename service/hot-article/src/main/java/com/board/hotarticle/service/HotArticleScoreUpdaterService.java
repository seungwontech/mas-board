package com.board.hotarticle.service;

import com.board.common.event.Event;
import com.board.common.event.EventPayload;
import com.board.hotarticle.repository.ArticleCreatedTimeRedisRepository;
import com.board.hotarticle.repository.HotArticleRedisRepository;
import com.board.hotarticle.service.eventHandler.EventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class HotArticleScoreUpdaterService {
    private final HotArticleRedisRepository hotArticleRedisRepository;
    private final HotArticleScoreCalculatorService hotArticleScoreCalculatorService;
    private final ArticleCreatedTimeRedisRepository articleCreatedTimeRedisRepository;

    private static final long HOT_ARTICLE_COUNT = 10;
    private static final Duration HOT_ARTICLE_TTL = Duration.ofDays(5);

    public void update(Event<EventPayload> event, EventHandler<EventPayload> eventHandler) {
        Long articleId = eventHandler.findArticleId(event);
        LocalDateTime createdTime = articleCreatedTimeRedisRepository.read(articleId);

        if (!isArticleCreatedToday(createdTime)) {
            return;
        }

        eventHandler.handle(event);

        long score = hotArticleScoreCalculatorService.calculate(articleId);

        hotArticleRedisRepository.add(articleId, createdTime, score, HOT_ARTICLE_COUNT, HOT_ARTICLE_TTL);
    }

    private boolean isArticleCreatedToday(LocalDateTime createdTime) {
        return createdTime != null && createdTime.toLocalDate().equals(LocalDate.now());
    }
}
