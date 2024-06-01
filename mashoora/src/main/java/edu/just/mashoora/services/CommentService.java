package edu.just.mashoora.services;

import edu.just.mashoora.components.Comment;
import edu.just.mashoora.components.CommentVotesCounts;
import edu.just.mashoora.models.User;
import edu.just.mashoora.payload.request.CommentRequest;
import edu.just.mashoora.payload.response.CommentResponse;

import java.util.List;

public interface CommentService {

    CommentResponse postComment(Long questionId, CommentRequest commentRequest);

    Comment getCommentById(Long commentId);

    List<CommentResponse> getCommentsByQuestionId(Long questionId);

    boolean VoteOnComment(User user, Comment comment, boolean vote);
}
