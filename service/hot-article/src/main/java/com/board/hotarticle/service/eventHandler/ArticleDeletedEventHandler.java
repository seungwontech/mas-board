package com.board.hotarticle.service.eventHandler;

import com.board.common.event.Event;
import com.board.common.event.EventType;
import com.board.common.event.payload.ArticleDeletedEventPayload;
import com.board.hotarticle.repository.ArticleCreatedTimeRedisRepository;
import com.board.hotarticle.repository.HotArticleRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleDeletedEventHandler implements EventHandler<ArticleDeletedEventPayload> {
    private final HotArticleRedisRepository hotArticleListRepository;
    private final ArticleCreatedTimeRedisRepository articleCreatedTimeRepository;

    @Override
    public void handle(Event<ArticleDeletedEventPayload> event) {
        ArticleDeletedEventPayload payload = event.getPayload();
        articleCreatedTimeRepository.delete(payload.getArticleId());
        hotArticleListRepository.remove(payload.getArticleId(), payload.getCreatedAt());
    }

    @Override
    public boolean supports(Event<ArticleDeletedEventPayload> event) {
        return EventType.ARTICLE_DELETED == event.getType();
    }

    @Override
    public Long findArticleId(Event<ArticleDeletedEventPayload> event) {
        return event.getPayload().getArticleId();
    }
}
