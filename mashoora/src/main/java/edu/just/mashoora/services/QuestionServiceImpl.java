package edu.just.mashoora.services;

import edu.just.mashoora.components.Comment;
import edu.just.mashoora.components.Question;
import edu.just.mashoora.models.User;
import edu.just.mashoora.payload.request.QuestionRequest;
import edu.just.mashoora.repository.QuestionRepository;
import edu.just.mashoora.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public Question postQuestion(QuestionRequest questionRequest) {
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
        return questionRepository.save(question);
    }

    public Optional<Question> getQuestionById(Long questionId) {
        // Retrieve the question by ID
        return questionRepository.findById(questionId);
    }



    public Set<Comment> getAllCommentsOfQuestion(Long questionId) {
        // Find the question by ID
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Question not found with id: " + questionId));

        // Return all comments associated with the question
        return question.getComments();
    }
}
