package com.board.hotarticle.service;

import com.board.common.event.Event;
import com.board.common.event.EventPayload;
import com.board.common.event.EventType;
import com.board.hotarticle.client.ArticleClient;
import com.board.hotarticle.dto.HotArticleRes;
import com.board.hotarticle.repository.HotArticleRedisRepository;
import com.board.hotarticle.service.eventHandler.EventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class HotArticleService {

    private final ArticleClient articleClient;
    private final List<EventHandler<EventPayload>> eventHandlers;
    private final HotArticleScoreUpdaterService hotArticleScoreUpdaterService;
    private final HotArticleRedisRepository hotArticleRedisRepository;


    public void handleEvent(Event<EventPayload> event) {
        EventHandler<EventPayload> eventHandler = findEventHandler(event);

        if (eventHandler == null) return;

        if (isArticleCreatedOrDeleted(event)) {
            eventHandler.handle(event);
        } else {
            hotArticleScoreUpdaterService.update(event, eventHandler);
        }
    }

    private EventHandler<EventPayload> findEventHandler(Event<EventPayload> event) {
        return eventHandlers.stream()
                .filter(eventHandler -> eventHandler.supports(event))
                .findAny().orElse(null);
    }

    private boolean isArticleCreatedOrDeleted(Event<EventPayload> event) {
        return EventType.ARTICLE_CREATED == event.getType() || EventType.ARTICLE_DELETED == event.getType();
    }


    public List<HotArticleRes> readAll(String dateStr) {
        List<Long> hotArticleIds = hotArticleRedisRepository.readAll(dateStr);

        List<ArticleClient.ArticleRes> articles = hotArticleIds.stream()
                .map(articleClient::read)
                .filter(Objects::nonNull)
                .toList();
        return articles.stream().map(HotArticleRes::from).toList();
    }

}
