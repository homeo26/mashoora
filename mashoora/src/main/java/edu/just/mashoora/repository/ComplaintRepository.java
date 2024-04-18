package edu.just.mashoora.repository;


import edu.just.mashoora.components.Complaint;
import edu.just.mashoora.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    List<Complaint> findAll();

    Optional<Complaint> findById(Long id);

    Complaint save(Complaint complaint);

    void deleteById(Long id);

    List<Complaint> findBySubject(String subject);

    List<Complaint> findByUsers(User user);
}
