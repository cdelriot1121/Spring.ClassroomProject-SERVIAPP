package com.example.ServiApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ServiApp.model.AdminModel;
import com.example.ServiApp.model.Falla_Ser_Model;
import com.example.ServiApp.model.ServicioModel;
import com.example.ServiApp.model.UsuarioModel;
import com.example.ServiApp.services.AdminService;
import com.example.ServiApp.services.FallasUserService;
import com.example.ServiApp.services.ServiciosService;
import com.example.ServiApp.services.UsuarioService;

import jakarta.servlet.http.HttpSession;



@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/login-admin")
    public String login(@RequestParam("usuario-admin") String usuario,
                        @RequestParam("password") String password,
                        @RequestParam("pin") String pin,
                        Model model, HttpSession sessionadmin) {
        AdminModel admin = adminService.authenticate(usuario, password, pin);
        if (admin != null) {
            sessionadmin.setAttribute("adminLogueado", admin);
            return "redirect:/interfaz-admin"; 
        } else {
            model.addAttribute("error", "Los datos son incorrectos");
            return "login-admin";
        }
    }

    //metodo para listar usuarios en administrador

    @Autowired
    private UsuarioService usuarioService;

        @GetMapping("/interfaz-admin")
        public String listarUsuarios(Model model) {
            List<UsuarioModel> usuarios = usuarioService.obtenerUsuarios();
            model.addAttribute("usuarios", usuarios); // Asegúrate de que la variable es 'usuarios'
            return "interfaz-admin"; // El nombre de la vista
        }

        // Método para eliminar un usuario
    @PostMapping("/eliminar-usuario/{id}")
    public String eliminarUsuario(@PathVariable("id") Long id) {
        usuarioService.eliminarUsuario(id); // Llamamos al servicio para eliminar el usuario
        return "redirect:/interfaz-admin"; 
    
    }

    


    @Autowired
    private ServiciosService serviciosService;

    //METODO PARA VER LOS SERVICIOS EN ges-servicios-admin
    @GetMapping("/gestionar-servicios-admin")
    public String listarServicios(Model model){

        List<ServicioModel> servicios = serviciosService.ObtenerServicios();
        model.addAttribute("servicios", servicios);
        return "ges-servicios-admin";
    }

    //METODO PARA ELIMINAR SERVICIO

    @PostMapping("/eliminar-servicio/{id}")
    public String eliminarServicio (@PathVariable("id") Long id){
        serviciosService.eliminarServicio(id);
        return "redirect:/gestionar-servicios-admin";

    }



    //METODOS PARA VER LOS REPORTES DE USUARIOS en ResportesUserAdmin

    @Autowired
    private FallasUserService fallasUserService;

    @GetMapping("/reportes_usuarios")
    public String listarFallasUser(Model model){
        List<Falla_Ser_Model> fallas = fallasUserService.obtenerFallas();
        model.addAttribute("fallas", fallas);
        return "ReportesUserAdmin";

    }
    


}
