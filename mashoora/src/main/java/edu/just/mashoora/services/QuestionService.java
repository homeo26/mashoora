package edu.just.mashoora.services;

import edu.just.mashoora.components.Comment;
import edu.just.mashoora.components.Question;
import edu.just.mashoora.payload.request.QuestionRequest;

import java.util.Optional;
import java.util.Set;

public interface QuestionService {

    Question postQuestion(QuestionRequest questionRequest);

    Optional<Question> getQuestionById(Long questionId);

    Set<Comment> getAllCommentsOfQuestion(Long questionId);

}
