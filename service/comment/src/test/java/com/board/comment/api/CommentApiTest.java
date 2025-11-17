package com.board.comment.api;

import com.board.comment.dto.CommentCreateReq;
import com.board.comment.dto.CommentPageRes;
import com.board.comment.dto.CommentRes;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

public class CommentApiTest {

    RestClient restClient = RestClient.create("http://localhost:9003");

    @Test
    void create() {
        CommentRes response1 = createComment(new CommentCreateReq(1L, "my comment1", null, 1L));
        CommentRes response2 = createComment(new CommentCreateReq(1L, "my comment2", response1.commentId(), 1L));
        CommentRes response3 = createComment(new CommentCreateReq(1L, "my comment3", response1.commentId(), 1L));

        System.out.println("commentId=%s".formatted(response1.commentId()));
        System.out.println("commentId=%s".formatted(response2.commentId()));
        System.out.println("commentId=%s".formatted(response3.commentId()));
    }
    CommentRes createComment(CommentCreateReq request) {
        return restClient.post().uri("/v1/comments").body(request).retrieve().body(CommentRes.class);
    }

//248728690962083840
//248728691280850944
//248728691326988288

    @Test
    void read() {
        CommentRes response = restClient.get().uri("/v1/comments/{commentId}", 248728690962083840L).retrieve().body(CommentRes.class);
        System.out.println("response = " + response);
    }

    @Test
    void delete() {
        restClient.delete().uri("/v1/comments/{commentId}", 248728761480916992L).retrieve().body(Void.class);
    }



    @Test
    void readAll() {
        CommentPageRes response = restClient.get().uri("/v1/comments?articleId=1&page=1&pageSize=10").retrieve().body(CommentPageRes.class);
        System.out.println("response.getCommentCount() = " + response.commentCount());
        for (CommentRes comment : response.comments()) {
            if (!comment.commentId().equals(comment.parentCommentId())) {
                System.out.print("\t");
            }
            System.out.println("comment.getCommentId() = " + comment.commentId());
        }
    }
}
