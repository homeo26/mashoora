package com.just.mashoora.services;

import com.just.mashoora.models.Lawyer;

import java.util.List;

public interface ILawyerService {

    boolean doesLawyerExist(Long id);

    Lawyer insertLawyer(Lawyer lawyer);

    List<Lawyer> fetchLawyersList();

}
