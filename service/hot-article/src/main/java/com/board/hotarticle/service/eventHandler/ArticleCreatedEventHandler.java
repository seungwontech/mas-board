package com.board.hotarticle.service.eventHandler;


import com.board.common.event.Event;
import com.board.common.event.EventType;
import com.board.common.event.payload.ArticleCreatedEventPayload;
import com.board.hotarticle.repository.ArticleCreatedTimeRedisRepository;
import com.board.hotarticle.utils.TimeCalculatorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleCreatedEventHandler implements EventHandler<ArticleCreatedEventPayload> {
private final ArticleCreatedTimeRedisRepository articleCreatedTimeRedisRepository;
    @Override
    public void handle(Event<ArticleCreatedEventPayload> event) {
        ArticleCreatedEventPayload payload = event.getPayload();
        articleCreatedTimeRedisRepository.createOrUpdate(
                payload.getArticleId(),
                payload.getCreatedAt(),
                TimeCalculatorUtils.calculateDurationToMidnight()

        );
    }

    @Override
    public boolean supports(Event<ArticleCreatedEventPayload> event) {
        return EventType.ARTICLE_CREATED == event.getType();
    }

    @Override
    public Long findArticleId(Event<ArticleCreatedEventPayload> event) {
        return event.getPayload().getArticleId();
    }
}
