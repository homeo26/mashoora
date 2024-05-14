package edu.just.mashoora.payload.response;

import edu.just.mashoora.components.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionResponse {

    private Long id;

    private String title;

    private String content;

    private Long userId;

    private String username;

    private Timestamp timestamp;

    private List<CommentResponse> comments;

    public QuestionResponse(Question question) {
        this.id = question.getId();
        this.title = question.getTitle();
        this.content = question.getContent();
        this.userId = question.getUser().getId();
        this.username = question.getUser().getUsername();
        this.timestamp = question.getTimestamp();
        this.comments = question.getComments().stream().map(CommentResponse::new).toList();
    }
}
