package com.board.hotarticle.service.eventHandler;

import com.board.common.event.Event;
import com.board.common.event.EventPayload;
import com.board.common.event.EventType;
import com.board.common.event.payload.CommentCreatedEventPayload;
import com.board.hotarticle.repository.ArticleCommentCountRedisRepository;
import com.board.hotarticle.utils.TimeCalculatorUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentCreatedEventHandler implements EventHandler {
    private final ArticleCommentCountRedisRepository articleCommentCountRepository;

    @Override
    public void handle(Event<? extends EventPayload> event) {
        CommentCreatedEventPayload payload = (CommentCreatedEventPayload) event.getPayload();
        articleCommentCountRepository.createOrUpdate(
                payload.getArticleId(),
                payload.getArticleCommentCount(),
                TimeCalculatorUtils.calculateDurationToMidnight()
        );
    }

    @Override
    public boolean supports(Event<? extends EventPayload> event) {
        return EventType.COMMENT_CREATED == event.getType();
    }

    @Override
    public Long findArticleId(Event<? extends EventPayload> event) {
        CommentCreatedEventPayload payload = (CommentCreatedEventPayload) event.getPayload();
        return payload.getArticleId();
    }
}
