package com.board.article.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PageLimitCalculatorTest {

    @Test
    void calculatePageLimit() {
        calculatePageLimitTest(1L, 30L, 10L, 301L);
        calculatePageLimitTest(11L, 30L, 10L, 601L);
    }

    void calculatePageLimitTest(Long page, Long pageSize, Long movablePageCount, Long expeted) {
        Long result = PageLimitCalculator.calculatePageLimit(page, pageSize, movablePageCount);
        assertThat(result).isEqualTo(expeted);
    }
}