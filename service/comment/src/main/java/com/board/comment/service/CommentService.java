package com.board.comment.service;

import com.board.comment.dto.CommentCreateReq;
import com.board.comment.dto.CommentPageRes;
import com.board.comment.dto.CommentRes;
import com.board.comment.entity.Comment;
import com.board.comment.repository.CommentRepository;
import com.board.common.snowflake.Snowflake;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final Snowflake snowflake = new Snowflake();
    private final CommentRepository commentRepository;

    @Transactional
    public CommentRes create(CommentCreateReq request) {
        Long parentCommentId = request.parentCommentId();
        Comment parentComment = null;

        if(parentCommentId != null) {
            parentComment = commentRepository.findById(parentCommentId).orElseThrow(()-> new IllegalArgumentException("존재 하지 않은 루트 댓글입니다."));

            if (parentComment.getDeleted()) {
                throw new IllegalArgumentException("이미 논리적으로 삭제된 루트 댓글입니다.");
            }

            if(!parentComment.isRoot()) {
                // 루트 댓글이 아니면 대댓글 금지 정책
                throw new IllegalArgumentException("루트 댓글이 아닙니다.");
            }
        }

        Comment createComment = Comment.create(
                snowflake.nextId(),
                request.content(),
                parentComment == null ? null : parentComment.getCommentId(),
                request.memberId(),
                request.articleId()
        );
        Comment comment = commentRepository.save(createComment);
        return CommentRes.from(comment);
    }


    public CommentRes read(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        return CommentRes.from(comment);
    }

    @Transactional
    public void delete(Long commentId) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        if (commentOptional.isEmpty()) {
            return;
        }

        Comment comment = commentOptional.get();
        if(!comment.getDeleted()) {
            if(hasChildren(comment)) {
                comment.deleted();
            } else {
                cascadeDelete(comment);
            }
        }
    }

    private void cascadeDelete(Comment comment) {
        commentRepository.delete(comment);
        if(!comment.isRoot()) {

            Optional<Comment> parentOptional = commentRepository.findById(comment.getParentCommentId());

            if(parentOptional.isPresent()) {
                Comment parent = parentOptional.get();

                if(parent.getDeleted() && !hasChildren(parent)) {
                    cascadeDelete(parent);
                }
            }
        }
    }

    private boolean hasChildren(Comment comment) {
        return commentRepository.countBy(comment.getArticleId(), comment.getCommentId(), 2L) == 2;
    }

    public CommentPageRes readAll(Long articleId, Long page, Long pageSize) {
        List<Comment> comments = commentRepository.findAll(articleId, (page - 1) * pageSize, pageSize);
        Long commentCount = commentRepository.count(articleId, PageLimitCalculator.calculatePageLimit(page, pageSize, 10L));

        return CommentPageRes.of(comments.stream().map(CommentRes::from).toList(), commentCount);
    }

}
