package com.board.common.outboxmessagerelay;

import lombok.Getter;

@Getter
public class OutboxEvent {
    private Outbox outbox;

    public static OutboxEvent of(Outbox outbox) {
        OutboxEvent event = new OutboxEvent();
        event.outbox = outbox;
        return event;
    }
}
