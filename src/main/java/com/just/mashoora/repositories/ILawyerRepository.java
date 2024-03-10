package com.just.mashoora.repositories;

import com.just.mashoora.models.Lawyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILawyerRepository extends JpaRepository<Lawyer, Long> {

}
