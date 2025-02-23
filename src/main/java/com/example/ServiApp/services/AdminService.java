package com.example.ServiApp.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ServiApp.model.AdminModel;
import com.example.ServiApp.repository.AdminRepository;


@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public AdminModel authenticate(String usuario, String password, String pin) {
        return adminRepository.findAll().stream()
            .filter(admin -> admin.getUsuario().equals(usuario) && 
                             admin.getPassword().equals(password) &&
                             admin.getPin().equals(pin))
            .findFirst()
            .orElse(null);
    }
}
