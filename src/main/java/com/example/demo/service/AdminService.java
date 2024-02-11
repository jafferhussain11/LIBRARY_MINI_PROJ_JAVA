package com.example.demo.service;

import com.example.demo.models.Admin;
import com.example.demo.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;
    public void createAdmin(Admin admin){
        //save the admin to the database
        adminRepository.save(admin);
    }
}
