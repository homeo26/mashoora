package edu.just.mashoora.repository;

import edu.just.mashoora.components.Comment;
import edu.just.mashoora.components.CommentVote;
import edu.just.mashoora.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentVoteRepository extends JpaRepository<CommentVote, Long> {
    int countByIdAndVoteTrue(Long commentId);
    int countByIdAndVoteFalse(Long commentId);
    Optional<CommentVote> findByUserAndComment(User user, Comment comment);

}
