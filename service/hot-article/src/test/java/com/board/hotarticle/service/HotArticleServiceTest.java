//package com.board.hotarticle.service;
//
//import com.board.common.event.Event;
//import com.board.common.event.EventPayload;
//import com.board.common.event.EventType;
//import com.board.hotarticle.service.eventHandler.EventHandler;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.List;
//import java.util.stream.Stream;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class HotArticleServiceTest {
//
//    @InjectMocks
//    HotArticleService hotArticleService;
//
//    @Mock
//    List<EventHandler<EventPayload>> eventHandlers;
//
//    @Mock
//    HotArticleScoreUpdaterService hotArticleScoreUpdaterService;
//
//    @Test
//    void handleEventIfEventHandlerIsNull() {
//        // Given
//        Event event = mock(Event.class);
//        EventHandler<EventPayload> eventHandler = mock(EventHandler.class);
//        given(eventHandler.supports(event)).willReturn(false);
//        given(eventHandlers.stream()).willReturn(Stream.of(eventHandler));
//        // When
//        hotArticleService.handleEvent(event);
//        // Then
//        verify(eventHandler, never()).handle(event);
//        verify(hotArticleScoreUpdaterService, never()).update(event, eventHandler);
//    }
//
//
//    @Test
//    void handleEventIfArticleCreatedEventTest() {
//        // Given
//        Event event = mock(Event.class);
//        given(event.getType()).willReturn(EventType.ARTICLE_CREATED);
//
//        EventHandler<EventPayload> eventHandler = mock(EventHandler.class);
//        given(eventHandler.supports(event)).willReturn(true);
//        given(eventHandlers.stream()).willReturn(Stream.of(eventHandler));
//        // When
//        hotArticleService.handleEvent(event);
//        // Then
//        verify(eventHandler).handle(event);
//        verify(hotArticleScoreUpdaterService, never()).update(event, eventHandler);
//    }
//
//
//    @Test
//    void handleEventIfArticleDeletedEventTest() {
//        // Given
//        Event event = mock(Event.class);
//        given(event.getType()).willReturn(EventType.ARTICLE_DELETED);
//
//        EventHandler<EventPayload> eventHandler = mock(EventHandler.class);
//        given(eventHandler.supports(event)).willReturn(true);
//        given(eventHandlers.stream()).willReturn(Stream.of(eventHandler));
//        // When
//        hotArticleService.handleEvent(event);
//        // Then
//        verify(eventHandler).handle(event);
//        verify(hotArticleScoreUpdaterService, never()).update(event, eventHandler);
//    }
//
//
//    @Test
//    void handleEventIfArticleUpdateEventTest() {
//        // Given
//        Event event = mock(Event.class);
//        given(event.getType()).willReturn(mock(EventType.class));
//
//        EventHandler<EventPayload> eventHandler = mock(EventHandler.class);
//        given(eventHandler.supports(event)).willReturn(true);
//        given(eventHandlers.stream()).willReturn(Stream.of(eventHandler));
//        // When
//        hotArticleService.handleEvent(event);
//        // Then
//        verify(eventHandler, never()).handle(event);
//        verify(hotArticleScoreUpdaterService).update(event, eventHandler);
//    }
//
//}