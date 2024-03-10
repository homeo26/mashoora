package com.just.mashoora.services.impl;

import com.just.mashoora.models.Lawyer;
import com.just.mashoora.repositories.ILawyerRepository;
import com.just.mashoora.services.ILawyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LawyerService implements ILawyerService {

    @Autowired
    private ILawyerRepository lawyerRepository;

    @Override
    public boolean doesLawyerExist(Long id) {
        return lawyerRepository.existsById(id);
    }

    @Override
    public Lawyer insertLawyer(Lawyer lawyer) {
        return lawyerRepository.save(lawyer);
    }

    @Override
    public List<Lawyer> fetchLawyersList() {
        return lawyerRepository.findAll();
    }
}
