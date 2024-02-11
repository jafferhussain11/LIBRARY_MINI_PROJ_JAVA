package com.example.demo.controller;

import com.example.demo.dto.CreateAdminRequest;
import com.example.demo.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/admin")
    public void createAdmin(@RequestBody @Valid CreateAdminRequest createAdminRequest){ //
        adminService.createAdmin(createAdminRequest.toAdmin() ); //convert the DTO to Entity and pass it to the service
    }

}
