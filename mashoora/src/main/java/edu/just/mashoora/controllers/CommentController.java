package edu.just.mashoora.controllers;

import edu.just.mashoora.components.Comment;
import edu.just.mashoora.models.User;
import edu.just.mashoora.payload.request.CommentRequest;
import edu.just.mashoora.payload.response.CommentResponse;
import edu.just.mashoora.repository.UserRepository;
import edu.just.mashoora.services.CommentService;
import edu.just.mashoora.utils.StandardResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserRepository userRepository;



    @PostMapping("/{questionId}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('LAWYER') or hasRole('ADMIN')")
    public ResponseEntity<?> postComment(@PathVariable Long questionId, @Valid @RequestBody CommentRequest commentRequest) {
        StandardResponse<CommentResponse> response = new StandardResponse<>();

        // Create a new comment on the specified question
        CommentResponse commentResponse = commentService.postComment(questionId, commentRequest);

        response.setStatus(HttpStatus.CREATED.getReasonPhrase());
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setDetailedStatusCode("Comment posted successfully on Question " + questionId);
        response.setData(commentResponse);

        return ResponseEntity.status(HttpStatus.CREATED.value()).body(response);
    }

    @PostMapping("/vote/{commentId}")
    @PreAuthorize("hasRole('LAWYER')")
    public ResponseEntity<?> voteOnComment(@PathVariable("commentId") Long commentId, @RequestParam boolean commentVote){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User not found with username: " + username));
        try {
            Comment comment = commentService.getCommentById(commentId);
            commentService.VoteOnComment(user, comment, commentVote);
            return ResponseEntity.status(HttpStatus.CREATED).body("Vote updated successfully");
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ex.getMessage());
        }
    }
}