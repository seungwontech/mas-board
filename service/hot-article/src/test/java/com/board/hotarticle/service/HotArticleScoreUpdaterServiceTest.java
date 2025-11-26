package com.board.hotarticle.service;

import com.board.common.event.Event;
import com.board.hotarticle.repository.ArticleCreatedTimeRedisRepository;
import com.board.hotarticle.repository.HotArticleRedisRepository;
import com.board.hotarticle.service.eventHandler.EventHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class HotArticleScoreUpdaterServiceTest {

    @InjectMocks
    HotArticleScoreUpdaterService hotArticleScoreUpdaterService;

    @Mock
    HotArticleRedisRepository hotArticleRedisRepository;

    @Mock
    ArticleCreatedTimeRedisRepository articleCreatedTimeRedisRepository;

    @Test
    void updateIfArticleNotCreatedTodayTest() {
        // Given
        Long articleId = 1L;

        Event event = mock(Event.class);
        EventHandler eventHandler = mock(EventHandler.class);

        given(eventHandler.findArticleId(event)).willReturn(articleId);
        LocalDateTime createdTime = LocalDateTime.now().minusDays(1);

        given(articleCreatedTimeRedisRepository.read(articleId)).willReturn(createdTime);

        // When
        hotArticleScoreUpdaterService.update(event, eventHandler);
        // Then
        verify(eventHandler, never()).handle(event);
        verify(hotArticleRedisRepository, never()).add(anyLong(), any(LocalDateTime.class), anyLong(), anyLong(), any(Duration.class));

    }

    @Test
    void updateTest() {
        // Given
        Long articleId = 1L;

        Event event = mock(Event.class);
        EventHandler eventHandler = mock(EventHandler.class);

        given(eventHandler.findArticleId(event)).willReturn(articleId);
        LocalDateTime createdTime = LocalDateTime.now();

        given(articleCreatedTimeRedisRepository.read(articleId)).willReturn(createdTime);

        // When
        hotArticleScoreUpdaterService.update(event, eventHandler);
        // Then
        verify(eventHandler).handle(event);
        verify(hotArticleRedisRepository).add(anyLong(), any(LocalDateTime.class), anyLong(), anyLong(), any(Duration.class));

    }


}