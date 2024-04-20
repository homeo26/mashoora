package edu.just.mashoora.services;

import edu.just.mashoora.components.Comment;
import edu.just.mashoora.components.Question;
import edu.just.mashoora.models.User;
import edu.just.mashoora.payload.request.CommentRequest;
import edu.just.mashoora.repository.CommentRepository;
import edu.just.mashoora.repository.QuestionRepository;
import edu.just.mashoora.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Comment postComment(Long questionId, CommentRequest commentRequest) {
        // Find the question by ID
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Question not found with id: " + questionId));

        // Get the authenticated user details
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Fetch the user from the database using the username from the UserDetailsImpl
        User authenticatedUser = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Authenticated user not found"));

        // Map DTO to entity
        Comment comment = new Comment();
        comment.setContent(commentRequest.getContent());
        comment.setQuestion(question);

        // Set the authenticated user as the owner of the question
        comment.setUser(authenticatedUser);

        // Save the comment
        return commentRepository.save(comment);
    }


    public Optional<Comment> getCommentById(Long commentId) {
        // Retrieve the comment by ID
        return commentRepository.findById(commentId);
    }
}
