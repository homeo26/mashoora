package edu.just.mashoora.services;

import edu.just.mashoora.components.CommentVotesCounts;
import edu.just.mashoora.payload.request.QuestionRequest;
import edu.just.mashoora.payload.response.CommentResponse;
import edu.just.mashoora.payload.response.QuestionResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface QuestionService {

    QuestionResponse postQuestion(QuestionRequest questionRequest);

    QuestionResponse getQuestionById(Long questionId);

    List<CommentResponse> getAllCommentsOfQuestion(Long questionId);

    Page<QuestionResponse> getQuestionsByPage(int pageNumber, int pageSize);

    CommentVotesCounts getCommentVotes(Long commentId);

}