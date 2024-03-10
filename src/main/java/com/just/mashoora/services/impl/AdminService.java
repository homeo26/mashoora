package com.just.mashoora.services.impl;

import com.just.mashoora.models.Admin;
import com.just.mashoora.repositories.IAdminRepository;
import com.just.mashoora.services.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService implements IAdminService {

    @Autowired
    private IAdminRepository adminRepository;

    @Override
    public Admin insertAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public List<Admin> fetchAdminList() {
        return adminRepository.findAll();
    }
}
