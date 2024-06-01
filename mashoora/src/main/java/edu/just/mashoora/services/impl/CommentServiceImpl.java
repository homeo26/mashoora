package edu.just.mashoora.services.impl;

import edu.just.mashoora.components.*;
import edu.just.mashoora.models.User;
import edu.just.mashoora.payload.request.CommentRequest;
import edu.just.mashoora.payload.response.CommentResponse;
import edu.just.mashoora.repository.CommentRepository;
import edu.just.mashoora.repository.CommentVoteRepository;
import edu.just.mashoora.repository.QuestionRepository;
import edu.just.mashoora.repository.UserRepository;
import edu.just.mashoora.services.CommentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentVoteRepository commentVoteRepository;

    @Transactional
    public CommentResponse postComment(Long questionId, CommentRequest commentRequest) {
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
        commentRepository.save(comment);

        return new CommentResponse(comment);
    }


    @Transactional
    public Comment getCommentById(Long commentId) {

        Optional<Comment> comment = commentRepository.findCommentById(commentId);
        if(comment == null) throw new NoSuchElementException("Comment with id " + commentId + " not found");

        return comment.get();
    }

    @Override
    public List<CommentResponse> getCommentsByQuestionId(Long questionId) {
        List<Comment> comments = commentRepository.findByQuestionId(questionId);
        return comments.stream()
                .map(CommentResponse::new)
                .collect(Collectors.toList());
    }

    public boolean VoteOnComment(User user,Comment comment, boolean vote){
        Optional<CommentVote> existingVoteOpt = commentVoteRepository.findByUserAndComment(user, comment);
        CommentVote commentVote;

        if (existingVoteOpt.isPresent()) {
            commentVote = existingVoteOpt.get();
            commentVote.setVote(vote); // Update the vote
        } else {
            commentVote = new CommentVote();
            commentVote.setUser(user);
            commentVote.setComment(comment);
            commentVote.setVote(vote); // Set the vote
        }
        commentVoteRepository.save(commentVote);
        return true;
    }

}
