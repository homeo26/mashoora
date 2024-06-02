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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<StandardResponse<CommentResponse>> postComment(@PathVariable Long questionId, @Valid @RequestBody CommentRequest commentRequest) {
        StandardResponse<CommentResponse> response = new StandardResponse<>();

        try {
            // Create a new comment on the specified question
            CommentResponse commentResponse = commentService.postComment(questionId, commentRequest);

            response.setStatus("success");
            response.setStatusCode(HttpStatus.CREATED.value());
            response.setDetailedStatusCode("Comment posted successfully on Question " + questionId);
            response.setData(commentResponse);
        } catch (Exception e) {
            response.setStatus("error");
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setDetailedStatusCode("Failed to post comment on Question " + questionId);
        }

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/vote/{commentId}")
    @PreAuthorize("hasRole('LAWYER')")
    public ResponseEntity<StandardResponse<String>> voteOnComment(@PathVariable("commentId") Long commentId, @RequestParam boolean commentVote) {
        StandardResponse<String> response = new StandardResponse<>();

        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new NoSuchElementException("User not found with username: " + username));

            Comment comment = commentService.getCommentById(commentId);
            commentService.VoteOnComment(user, comment, commentVote);

            response.setStatus("success");
            response.setStatusCode(HttpStatus.CREATED.value());
            response.setDetailedStatusCode("Vote updated successfully");
            response.setData("Vote updated successfully");
        } catch (NoSuchElementException e) {
            response.setStatus("error");
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setDetailedStatusCode(e.getMessage());
        } catch (Exception e) {
            response.setStatus("error");
            response.setStatusCode(HttpStatus.EXPECTATION_FAILED.value());
            response.setDetailedStatusCode("Failed to update vote");
        }

        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}