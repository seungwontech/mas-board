package com.board.articleread.service.eventhandler;

import com.board.common.event.EventPayload;
import com.board.articleread.repository.ArticleQueryModelRepository;
import com.board.common.event.Event;
import com.board.common.event.EventType;
import com.board.common.event.payload.ArticleLikedEventPayload;
import com.board.common.event.payload.ArticleUnlikedEventPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleUnlikedEventHandler implements EventHandler {
    private final ArticleQueryModelRepository articleQueryModelRepository;

    @Override
    public void handle(Event<? extends EventPayload> event) {
        ArticleUnlikedEventPayload payload = (ArticleUnlikedEventPayload) event.getPayload();
        articleQueryModelRepository.read(payload.getArticleId())
                .ifPresent(articleQueryModel -> {
                    articleQueryModel.updateBy(payload);
                    articleQueryModelRepository.update(articleQueryModel);
                });
    }

    @Override
    public boolean supports(Event<? extends EventPayload> event) {
        return EventType.ARTICLE_UNLIKED == event.getType();
    }
}
