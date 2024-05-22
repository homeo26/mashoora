package edu.just.mashoora.repository;

import edu.just.mashoora.components.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
}
