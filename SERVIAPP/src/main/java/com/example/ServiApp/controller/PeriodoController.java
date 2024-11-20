package com.example.ServiApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.ServiApp.model.PeriodoModel;
import com.example.ServiApp.model.ServicioModel;
import com.example.ServiApp.model.UsuarioModel;
import com.example.ServiApp.services.PeriodoService;
import com.example.ServiApp.services.ServiciosService;

import jakarta.servlet.http.HttpSession;

@Controller
public class PeriodoController {
        @Autowired
        private ServiciosService servicioService;

        @Autowired
        private PeriodoService periodoService;

        @PostMapping("/calcular-consumo")
        public String calcularConsumo(@ModelAttribute PeriodoModel periodo, Model model, HttpSession session) {
            UsuarioModel usuarioLogueado = (UsuarioModel) session.getAttribute("usuarioLogueado");
            if (usuarioLogueado == null) {
                throw new IllegalArgumentException("Usuario no logueado.");
            }

            List<ServicioModel> servicios = servicioService.obtenerServiciosPorUsuario(usuarioLogueado);
            model.addAttribute("servicios", servicios);
            ServicioModel servicioSeleccionado = servicios.stream()
                    .filter(servicio -> servicio.getId() == periodo.getServicio().getId())
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado para el usuario actual."));

            long habitantes = servicioSeleccionado.getHabitantes();
            if (habitantes <= 0) {
                throw new IllegalArgumentException("El número de habitantes debe ser mayor a cero.");
            }

            periodo.setServicio(servicioSeleccionado);

            final float PROMEDIO_AGUA = 4.3f;
            final float PROMEDIO_ENERGIA = 80.7f;
            final float PROMEDIO_GAS = 3.9f;

            float promedioCartagena = 0;
            float promedioHogar = periodo.getConsumo();
            float promedioHabitante = promedioHogar / habitantes;
            float promedioSemanal = promedioHogar / 4;
            String unidad = ""; 

            switch (servicioSeleccionado.getTipo_servicio().trim().toLowerCase()) {
                case "agua":
                    promedioCartagena = PROMEDIO_AGUA * habitantes;
                    unidad = "m³";
                    break;
                case "energía":
                    promedioCartagena = PROMEDIO_ENERGIA * habitantes;
                    unidad = "kWh";
                    break;
                case "gas":
                    promedioCartagena = PROMEDIO_GAS * habitantes;
                    unidad = "m³";
                    break;
                default:
                    throw new IllegalArgumentException("Tipo de servicio no válido: " + servicioSeleccionado.getTipo_servicio());
            }

            String clasePromedioCartagena = promedioHogar > promedioCartagena ? "alto" : "bajo";



            periodoService.registrarPeriodo(periodo);

            model.addAttribute("promedioCartagena", promedioCartagena);
            model.addAttribute("promedioHogar", promedioHogar);
            model.addAttribute("promedioHabitante", promedioHabitante);
            model.addAttribute("promedioSemanal", promedioSemanal);
            model.addAttribute("unidad", unidad);
            model.addAttribute("clasePromedioCartagena", clasePromedioCartagena);

            return "gestionar_serv";
        }


        @GetMapping("/gestionar-servicio")
        public String gestionarServicio(Model model, HttpSession session) {
            UsuarioModel usuarioLogueado = (UsuarioModel) session.getAttribute("usuarioLogueado");
            if (usuarioLogueado == null) {
                return "redirect:/login"; 
            }

            List<ServicioModel> servicios = servicioService.obtenerServiciosPorUsuario(usuarioLogueado);
            model.addAttribute("servicios", servicios);
            return "gestionar_serv";
        }



}
