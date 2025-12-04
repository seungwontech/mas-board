package com.board.hotarticle.service.eventHandler;

import com.board.common.event.Event;
import com.board.common.event.EventPayload;
import com.board.common.event.EventType;
import com.board.common.event.payload.ArticleDeletedEventPayload;
import com.board.hotarticle.repository.ArticleCreatedTimeRedisRepository;
import com.board.hotarticle.repository.HotArticleRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleDeletedEventHandler implements EventHandler {
    private final HotArticleRedisRepository hotArticleListRepository;
    private final ArticleCreatedTimeRedisRepository articleCreatedTimeRepository;

    @Override
    public void handle(Event<? extends EventPayload> event) {
        ArticleDeletedEventPayload payload = (ArticleDeletedEventPayload) event.getPayload();
        articleCreatedTimeRepository.delete(payload.getArticleId());
        hotArticleListRepository.remove(payload.getArticleId(), payload.getCreatedAt());
    }

    @Override
    public boolean supports(Event<? extends EventPayload> event) {
        return EventType.ARTICLE_DELETED == event.getType();
    }

    @Override
    public Long findArticleId(Event<? extends EventPayload> event) {
        ArticleDeletedEventPayload payload = (ArticleDeletedEventPayload) event.getPayload();
        return payload.getArticleId();
    }
}
