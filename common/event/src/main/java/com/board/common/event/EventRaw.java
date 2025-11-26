package com.board.common.event;

public record EventRaw(
        Long eventId,
        String type,
        Object payload
) {
}
