package edu.just.mashoora.services.impl;

import edu.just.mashoora.components.Comment;
import edu.just.mashoora.components.Question;
import edu.just.mashoora.models.User;
import edu.just.mashoora.payload.request.QuestionRequest;
import edu.just.mashoora.payload.response.CommentResponse;
import edu.just.mashoora.payload.response.QuestionResponse;
import edu.just.mashoora.repository.CommentRepository;
import edu.just.mashoora.repository.QuestionRepository;
import edu.just.mashoora.repository.UserRepository;
import edu.just.mashoora.services.QuestionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public QuestionResponse postQuestion(QuestionRequest questionRequest) {
        // Get the authenticated user details
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Fetch the user from the database using the username from the UserDetailsImpl
        User authenticatedUser = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Authenticated user not found"));

        // Map DTO to entity
        Question question = new Question();
        question.setTitle(questionRequest.getTitle());
        question.setContent(questionRequest.getContent());

        // Set the authenticated user as the owner of the question
        question.setUser(authenticatedUser);

        // Save the question
        Question savedQuestion = questionRepository.save(question);

        return QuestionResponse.builder()
                .id(savedQuestion.getId())
                .title(savedQuestion.getTitle())
                .content(savedQuestion.getContent())
                .comments(null)
                .timestamp(savedQuestion.getTimestamp())
                .userId(savedQuestion.getUser().getId())
                .username(savedQuestion.getUser().getUsername())
                .firstName(savedQuestion.getUser().getFirstName())
                .lastName(savedQuestion.getUser().getLastName())
                .build();
    }

    public QuestionResponse getQuestionById(Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Question not found with id: " + questionId));

        return new QuestionResponse(question);
    }

    @Override
    public List<CommentResponse> getAllCommentsOfQuestion(Long questionId) {
        List<Comment> comments = commentRepository.findByQuestionId(questionId);
        return comments.stream()
                .map(CommentResponse::new)
                .collect(Collectors.toList());
    }
    @Override
    public Page<QuestionResponse> getQuestionsByPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<Question> questionPage = questionRepository.findAllByOrderByTimestampDesc(pageable);

        return questionPage.map(QuestionResponse::new);
    }
}
