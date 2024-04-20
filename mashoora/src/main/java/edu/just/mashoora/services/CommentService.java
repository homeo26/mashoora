package edu.just.mashoora.services;

import edu.just.mashoora.components.Comment;
import edu.just.mashoora.payload.request.CommentRequest;

import java.util.Optional;

public interface CommentService {

    Comment postComment(Long questionId, CommentRequest commentRequest);

    Optional<Comment> getCommentById(Long commentId);
}
