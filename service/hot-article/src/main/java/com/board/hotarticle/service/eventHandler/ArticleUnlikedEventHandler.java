package com.board.hotarticle.service.eventHandler;


import com.board.common.event.Event;
import com.board.common.event.EventPayload;
import com.board.common.event.EventType;
import com.board.common.event.payload.ArticleUnlikedEventPayload;
import com.board.hotarticle.repository.ArticleLikeCountRedisRepository;
import com.board.hotarticle.utils.TimeCalculatorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleUnlikedEventHandler implements EventHandler {
    private final ArticleLikeCountRedisRepository articleLikeCountRepository;

    @Override
    public void handle(Event<? extends EventPayload> event) {
        ArticleUnlikedEventPayload payload = (ArticleUnlikedEventPayload) event.getPayload();
        articleLikeCountRepository.createOrUpdate(
                payload.getArticleId(),
                payload.getArticleLikeCount(),
                TimeCalculatorUtils.calculateDurationToMidnight()
        );
    }

    @Override
    public boolean supports(Event<? extends EventPayload> event) {
        return EventType.ARTICLE_UNLIKED == event.getType();
    }

    @Override
    public Long findArticleId(Event<? extends EventPayload> event) {
        ArticleUnlikedEventPayload payload = (ArticleUnlikedEventPayload) event.getPayload();
        return payload.getArticleId();
    }
}
