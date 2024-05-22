package edu.just.mashoora.repository;


import edu.just.mashoora.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    User findByVerificationToken(String token);

    User findByEmailAndEnabledTrue(String email);

    User findByEmailAndEnabledFalse(String email);

    User findByUsernameAndEnabledFalse(String email);

    void deleteByEmailAndEnabledFalse(String email);
}
