package com.board.view.service;

import com.board.view.repository.ArticleViewCountBackUpRepository;
import com.board.view.repository.ArticleViewCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleViewService {

    private final ArticleViewCountRepository articleViewCountRepository;
    private final ArticleViewCountBackUpProcessor articleViewCountBackUpProcessor;

    private static final int BACK_UP_BACH_SIZE = 100;

    public Long increase(Long articleId, Long userId) {
        Long count = articleViewCountRepository.increment(articleId);

        if(count % BACK_UP_BACH_SIZE == 0) {
            articleViewCountBackUpProcessor.backUp(articleId, count);
        }

        return count ;
    }

    public Long count(Long articleId) {
        return articleViewCountRepository.read(articleId);
    }


}
