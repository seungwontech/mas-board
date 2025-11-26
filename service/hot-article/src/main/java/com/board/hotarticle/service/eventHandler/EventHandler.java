package com.board.hotarticle.service.eventHandler;

import com.board.common.event.Event;
import com.board.common.event.EventPayload;

public interface EventHandler<T extends EventPayload> {

    void handle(Event<T> event);
    boolean supports(Event<T> event);
    Long findArticleId(Event<T> event);
}
