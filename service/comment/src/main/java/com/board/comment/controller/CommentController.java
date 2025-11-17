package com.board.comment.controller;

import com.board.comment.dto.CommentCreateReq;
import com.board.comment.dto.CommentPageRes;
import com.board.comment.dto.CommentRes;
import com.board.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/v1/comments/{commentId}")
    public ResponseEntity<CommentRes> read(@PathVariable("commentId") Long commentId) {
        return ResponseEntity.ok(commentService.read(commentId));
    }

    @GetMapping("/v1/comments")
    public ResponseEntity<CommentPageRes> readAll(
            @RequestParam("articleId") Long articleId,
            @RequestParam("page") Long page,
            @RequestParam("pageSize") Long pageSize
    ) {
        return ResponseEntity.ok(commentService.readAll(articleId, page, pageSize));

    }

    @PostMapping("/v1/comments")
    public ResponseEntity<CommentRes> create(@RequestBody CommentCreateReq request) {
        return ResponseEntity.ok(commentService.create(request));
    }

    @DeleteMapping("/v1/comments/{commentId}")
    public ResponseEntity<Void> delete(@PathVariable("commentId") Long commentId) {
        commentService.delete(commentId);
        return ResponseEntity.noContent().build();
    }

}
