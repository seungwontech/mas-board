package com.board.articleread.service.eventhandler;


import com.board.articleread.repository.ArticleQueryModel;
import com.board.articleread.repository.ArticleQueryModelRepository;
import com.board.common.event.Event;
import com.board.common.event.EventPayload;
import com.board.common.event.EventType;
import com.board.common.event.payload.ArticleCreatedEventPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class ArticleCreatedEventHandler implements EventHandler {
    private final ArticleQueryModelRepository articleQueryModelRepository;

    @Override
    public void handle(Event<? extends EventPayload> event) {
        ArticleCreatedEventPayload payload = (ArticleCreatedEventPayload) event.getPayload();
        articleQueryModelRepository.create(
                ArticleQueryModel.create(payload),
                Duration.ofDays(1)
        );
        // articleIdListRepository.add(payload.getBoardId(), payload.getArticleId(), 1000L);
        // boardArticleCountRepository.createOrUpdate(payload.getBoardId(), payload.getBoardArticleCount());
    }

    @Override
    public boolean supports(Event<? extends EventPayload> event) {
        return EventType.ARTICLE_CREATED == event.getType();
    }
}
