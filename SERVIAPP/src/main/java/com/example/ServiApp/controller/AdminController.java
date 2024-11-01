package com.example.ServiApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ServiApp.model.AdminModel;
import com.example.ServiApp.services.AdminService;



@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;


    @PostMapping("/login-admin")
    public String login(@RequestParam("usuario-admin") String usuario,
                        @RequestParam("password") String password,
                        @RequestParam("pin") String pin,
                        Model model) {
        AdminModel admin = adminService.authenticate(usuario, password, pin);
        if (admin != null) {
            return "interfaz-admin"; 
        } else {
            model.addAttribute("error", "Los datos son incorrectos");
            return "login-admin";
        }
    }

}