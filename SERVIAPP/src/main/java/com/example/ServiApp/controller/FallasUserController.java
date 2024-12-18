package com.example.ServiApp.controller;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.ServiApp.model.Falla_Ser_Model;
import com.example.ServiApp.model.UsuarioModel;
import com.example.ServiApp.services.FallasUserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class FallasUserController {

    @Autowired
    private FallasUserService fallasUserService;

    @PostMapping("/reportar-falla")
public String reportarFalla(@ModelAttribute Falla_Ser_Model falla, 
                             @RequestParam("horaInicio") String horaInicio,
                             HttpSession session, RedirectAttributes redirectAttributes) {
    
    System.out.println("Comentario recibido: " + falla.getComentarios());

    UsuarioModel usuarioLogueado = (UsuarioModel) session.getAttribute("usuarioLogueado");
    
    if (usuarioLogueado == null) {
        redirectAttributes.addFlashAttribute("error", "Debe iniciar sesión para reportar una falla.");
        return "redirect:/login";
    }
    
    try {
        // cambiar hora de tipo string a time para que la bd lo pueda aceptar
        if (horaInicio != null && !horaInicio.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            java.util.Date horaDate = sdf.parse(horaInicio);
            falla.setHora(new java.sql.Time(horaDate.getTime()));
        }

        fallasUserService.reportarFalla(falla, usuarioLogueado);
        redirectAttributes.addFlashAttribute("mensaje", "Falla reportada exitosamente.");
        return "redirect:/cortes";
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("error", "Error al procesar la hora.");
        return "redirect:/reportar-falla";
    }
}

}
