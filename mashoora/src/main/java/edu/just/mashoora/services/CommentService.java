package edu.just.mashoora.services;

import edu.just.mashoora.payload.request.CommentRequest;
import edu.just.mashoora.payload.response.CommentResponse;

import java.util.List;

public interface CommentService {

    CommentResponse postComment(Long questionId, CommentRequest commentRequest);

    CommentResponse getCommentById(Long commentId);

    List<CommentResponse> getCommentsByQuestionId(Long questionId);
}
