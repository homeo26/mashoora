package edu.just.mashoora.repository;


import edu.just.mashoora.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
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

    @Transactional
    void deleteByEmail(String email);

    User findByEmail(String email);

    @Query(value = "SELECT user_id FROM user_roles WHERE role_id = :roleId", nativeQuery = true)
    List<Long> findUserIdsByRoleId(@Param("roleId") Integer roleId);
}
