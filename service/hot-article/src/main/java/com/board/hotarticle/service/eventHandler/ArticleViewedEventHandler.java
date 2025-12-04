package com.board.hotarticle.service.eventHandler;

import com.board.common.event.Event;
import com.board.common.event.EventPayload;
import com.board.common.event.EventType;
import com.board.common.event.payload.ArticleViewedEventPayload;
import com.board.hotarticle.repository.ArticleViewCountRedisRepository;
import com.board.hotarticle.utils.TimeCalculatorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleViewedEventHandler implements EventHandler {
    private final ArticleViewCountRedisRepository articleViewCountRepository;

    @Override
    public void handle(Event<? extends EventPayload> event) {
        ArticleViewedEventPayload payload = (ArticleViewedEventPayload) event.getPayload();
        articleViewCountRepository.createOrUpdate(
                payload.getArticleId(),
                payload.getArticleViewCount(),
                TimeCalculatorUtils.calculateDurationToMidnight()
        );
    }

    @Override
    public boolean supports(Event<? extends EventPayload> event) {
        return EventType.ARTICLE_VIEWED == event.getType();
    }

    @Override
    public Long findArticleId(Event<? extends EventPayload> event) {
        ArticleViewedEventPayload payload = (ArticleViewedEventPayload) event.getPayload();
        return payload.getArticleId();
    }
}
