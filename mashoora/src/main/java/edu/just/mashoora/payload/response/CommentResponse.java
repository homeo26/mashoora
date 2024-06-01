package edu.just.mashoora.payload.response;

import edu.just.mashoora.components.Comment;
import edu.just.mashoora.components.CommentVote;
import edu.just.mashoora.components.CommentVotesCounts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {

    private Long id;

    private String content;

    private Long userId;

    private String firstName;

    private String lastName;

    private String username;

    private Long questionId;

    private Timestamp timestamp;

    private CommentVotesCounts vote;


    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.userId = comment.getUser().getId();
        this.firstName = comment.getUser().getFirstName();
        this.lastName = comment.getUser().getLastName();
        this.username = comment.getUser().getUsername();
        this.questionId = comment.getQuestion().getId();
        this.timestamp = comment.getTimestamp();
    }

    public CommentResponse(Comment comment, CommentVotesCounts vote) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.userId = comment.getUser().getId();
        this.firstName = comment.getUser().getFirstName();
        this.lastName = comment.getUser().getLastName();
        this.username = comment.getUser().getUsername();
        this.questionId = comment.getQuestion().getId();
        this.timestamp = comment.getTimestamp();
        this.vote = vote;
    }

}
