package edu.just.mashoora.components;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentVotesCounts {
    private Long id;
    private int upVotes;
    private int downVotes;
}
