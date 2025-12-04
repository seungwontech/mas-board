package com.board.hotarticle.service.eventHandler;

import com.board.common.event.Event;
import com.board.common.event.EventPayload;
import com.board.common.event.EventType;
import com.board.common.event.payload.ArticleLikedEventPayload;
import com.board.hotarticle.repository.ArticleLikeCountRedisRepository;
import com.board.hotarticle.utils.TimeCalculatorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleLikedEventHandler implements EventHandler {
    private final ArticleLikeCountRedisRepository articleLikeCountRepository;

    @Override
    public void handle(Event<? extends EventPayload> event) {
        ArticleLikedEventPayload payload = (ArticleLikedEventPayload) event.getPayload();
        articleLikeCountRepository.createOrUpdate(
                payload.getArticleId(),
                payload.getArticleLikeCount(),
                TimeCalculatorUtils.calculateDurationToMidnight()
        );
    }

    @Override
    public boolean supports(Event<? extends EventPayload> event) {
        return EventType.ARTICLE_LIKED == event.getType();
    }

    @Override
    public Long findArticleId(Event<? extends EventPayload> event) {
        ArticleLikedEventPayload payload = (ArticleLikedEventPayload) event.getPayload();
        return payload.getArticleId();
    }
}
