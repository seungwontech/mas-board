package com.board.articleread.service.eventhandler;

import com.board.articleread.repository.ArticleQueryModelRepository;
import com.board.common.event.Event;
import com.board.common.event.EventPayload;
import com.board.common.event.EventType;
import com.board.common.event.payload.ArticleCreatedEventPayload;
import com.board.common.event.payload.ArticleLikedEventPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleLikedEventHandler implements EventHandler {
    private final ArticleQueryModelRepository articleQueryModelRepository;

    @Override
    public void handle(Event<? extends EventPayload> event) {

        ArticleLikedEventPayload payload = (ArticleLikedEventPayload) event.getPayload();

        articleQueryModelRepository.read( payload.getArticleId())
                .ifPresent(articleQueryModel -> {
                    articleQueryModel.updateBy(payload);
                    articleQueryModelRepository.update(articleQueryModel);
                });
    }

    @Override
    public boolean supports(Event<? extends EventPayload> event) {
        return EventType.ARTICLE_LIKED == event.getType();
    }
}
