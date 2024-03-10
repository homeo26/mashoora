package com.just.mashoora.services;

import com.just.mashoora.models.Admin;

import java.util.List;

public interface IAdminService {

    Admin insertAdmin(Admin admin);

    List<Admin> fetchAdminList();

}
