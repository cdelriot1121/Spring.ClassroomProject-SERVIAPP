package com.example.ServiApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.ServiApp.model.ConsejosModel;
import com.example.ServiApp.model.PeriodoModel;
import com.example.ServiApp.model.ServicioModel;
import com.example.ServiApp.model.UsuarioModel;
import com.example.ServiApp.services.ConsejosService;
import com.example.ServiApp.services.PeriodoService;
import com.example.ServiApp.services.ServiciosService;

import jakarta.servlet.http.HttpSession;

@Controller
public class PeriodoController {
        @Autowired
        private ServiciosService servicioService;

        @Autowired
        private PeriodoService periodoService;

        @Autowired
        private ConsejosService consejosService;

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

            float promedioHabitanteCartagena = 0;
            float promedioHogar = periodo.getConsumo();
            float promedioHabitante = promedioHogar / habitantes;
            String unidad = "";
            String categoriaConsumo = ""; 


            switch (servicioSeleccionado.getTipo_servicio().trim().toLowerCase()) {
                case "agua":
                    promedioHabitanteCartagena = PROMEDIO_AGUA;
                    unidad = "m³";
                    categoriaConsumo = categorizarConsumo(promedioHabitante, PROMEDIO_AGUA, 2);
                    break;
                case "energía":
                    promedioHabitanteCartagena = PROMEDIO_ENERGIA;
                    unidad = "kWh";
                    categoriaConsumo = categorizarConsumo(promedioHabitante, PROMEDIO_ENERGIA, 10);
                    break;
                case "gas":
                    promedioHabitanteCartagena = PROMEDIO_GAS;
                    unidad = "m³";
                    categoriaConsumo = categorizarConsumo(promedioHabitante, PROMEDIO_GAS, 2);
                    break;
                default:
                    throw new IllegalArgumentException("Tipo de servicio no válido: " + servicioSeleccionado.getTipo_servicio());
            }


            String clasePromedioCartagena = promedioHogar > promedioCartagena ? "alto" : "bajo";


            periodoService.registrarPeriodo(periodo);

           
            List<ConsejosModel> consejosPersonalizados = consejosService.obtenerConsejosTipoServ_TipoCateg(categoriaConsumo, servicioSeleccionado.getTipo_servicio());

           
            model.addAttribute("promedioCartagena", promedioHabitanteCartagena * habitantes);
            model.addAttribute("promedioHogar", promedioHogar);
            model.addAttribute("promedioHabitante", promedioHabitante);
            model.addAttribute("promedioSemanal", promedioSemanal);
            model.addAttribute("unidad", unidad);
            model.addAttribute("consejos", consejosPersonalizados);
            model.addAttribute("clasePromedioCartagena", clasePromedioCartagena);

            return "gestionar_serv";
        }

        
        private String categorizarConsumo(float consumoHabitante, float promedio, float margen) {
            if (consumoHabitante < promedio - margen) {
                return "Bajo";
            } else if (consumoHabitante > promedio + margen) {
                return "Elevado";
            } else {
                return "Moderado";
            }
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
