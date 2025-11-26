package com.board.common.event;

import com.board.common.dataserializer.DataSerializer;
import lombok.Getter;

@Getter
public class Event<T extends EventPayload> {
    private Long eventId;
    private EventType type;
    private T payload;


    public static Event<EventPayload> of(Long eventId, EventType type, EventPayload payload) {
        Event<EventPayload> event = new Event<>();
        event.eventId = eventId;
        event.type = type;
        event.payload = payload;
        return event;
    }

    public String toJson() {
        return DataSerializer.serialize(this);
    }

    public static Event<EventPayload> fromJson(String json) {

        EventRaw eventRaw = DataSerializer.deserialize(json, EventRaw.class);
        if (eventRaw == null) {
            return null;
        }

        EventType type = EventType.from(eventRaw.type());
        if (type == null) {
            return null;
        }

        EventPayload payload = DataSerializer.deserialize(eventRaw.payload(), type.getPayloadClass());

        return Event.of(eventRaw.eventId(), type, payload);
    }
}
