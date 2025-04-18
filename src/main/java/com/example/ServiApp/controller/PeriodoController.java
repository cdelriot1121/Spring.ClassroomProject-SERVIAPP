package com.example.ServiApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

              // === CONVERSIÓN DEL MES NUMÉRICO A TEXTO Y SEPARACIÓN DEL AÑO ===
        String mesYAnio = periodo.getMes(); // por ejemplo "2025-04"
        if (mesYAnio != null && mesYAnio.matches("\\d{4}-\\d{2}")) {
            String[] partes = mesYAnio.split("-");
            int ano = Integer.parseInt(partes[0]);
            String mesNumero = partes[1];

            String mesTxt = switch (mesNumero) {
                case "01" -> "Enero";
                case "02" -> "Febrero";
                case "03" -> "Marzo";
                case "04" -> "Abril";
                case "05" -> "Mayo";
                case "06" -> "Junio";
                case "07" -> "Julio";
                case "08" -> "Agosto";
                case "09" -> "Septiembre";
                case "10" -> "Octubre";
                case "11" -> "Noviembre";
                case "12" -> "Diciembre";
                default -> "Desconocido";
            };

            periodo.setMes(mesTxt);   // Guardar el mes como texto
            periodo.setAno(ano);      // Guardar el año por separado
        }



            final float PROMEDIO_AGUA = 4.3f;
            final float PROMEDIO_ENERGIA = 80.7f;
            final float PROMEDIO_GAS = 3.9f;

            float promedioCartagena = 0;
            float promedioHogar = periodo.getConsumo();
            float promedioHabitante = promedioHogar / habitantes;
            float promedioSemanal = promedioHogar / 4;
            String categoriaConsumo = ""; 
            String unidad = ""; 

            
            switch (servicioSeleccionado.getTipo_servicio().trim().toLowerCase()) {
                case "agua":
                    promedioCartagena = PROMEDIO_AGUA * habitantes;
                    unidad = "m3";
                    categoriaConsumo = categorizarConsumo(promedioHogar, promedioCartagena, 2);
                    break;
                case "energía":
                    promedioCartagena = PROMEDIO_ENERGIA * habitantes;
                    unidad = "kWh";
                    categoriaConsumo = categorizarConsumo(promedioHogar, promedioCartagena, 8);
                    break;
                case "gas":
                    promedioCartagena = PROMEDIO_GAS * habitantes;
                    unidad = "m3";
                    categoriaConsumo = categorizarConsumo(promedioHogar, promedioCartagena, 2);
                    break;
                default:
                    throw new IllegalArgumentException("Tipo de servicio no válido: " + servicioSeleccionado.getTipo_servicio());
            }

            periodo.setUnidad(unidad);

            String clasePromedioCartagena = promedioHogar > promedioCartagena ? "alto" : "bajo";

            
            periodoService.registrarPeriodo(periodo);

            
            List<ConsejosModel> consejosPersonalizados = consejosService.obtenerConsejosTipoServ_TipoCateg(categoriaConsumo, servicioSeleccionado.getTipo_servicio());
            
            periodo.setConsejos(consejosPersonalizados);
            periodoService.registrarPeriodo(periodo); 

            model.addAttribute("promedioCartagena", promedioCartagena);
            model.addAttribute("promedioHogar", promedioHogar);
            model.addAttribute("promedioHabitante", promedioHabitante);
            model.addAttribute("promedioSemanal", promedioSemanal);
            model.addAttribute("unidad", unidad);
            model.addAttribute("consejos", consejosPersonalizados);
            model.addAttribute("clasePromedioCartagena", clasePromedioCartagena);

            return "gestionar_serv";
        }


        private String categorizarConsumo(float consumoHogar, float promedio, float margen) {
            if (consumoHogar < promedio - margen) {
                return "Bajo";
            } else if (consumoHogar > promedio + margen) {
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


        @GetMapping("/consejos-personzalidos")
        public String consejosPersonalizados(Model model, HttpSession session) {
            UsuarioModel usuarioLogueado = (UsuarioModel) session.getAttribute("usuarioLogueado");
            if (usuarioLogueado == null) {
                return "redirect:/login"; 
            }

            List<ServicioModel> servicios = servicioService.obtenerServiciosPorUsuario(usuarioLogueado);

            List<PeriodoModel> periodos = servicios.stream()
                    .flatMap(servicio -> {
                        List<PeriodoModel> periodosServicio = periodoService.obtenerPeriodosPorServicios(servicio);
                        // Cargar los consejos para cada periodo
                        periodosServicio.forEach(periodo -> {
                            List<ConsejosModel> consejos = consejosService.obtenerConsejosPorPeriodo(periodo.getId());
                            periodo.setConsejos(consejos);
                        });
                        return periodosServicio.stream();
                    })
                    .toList();

            model.addAttribute("periodos", periodos);
            return "consejos_personalizados"; 
        }

        @GetMapping("/mis-consumos")
        public String misConsumos(Model model, HttpSession session) {
            UsuarioModel usuarioLogueado = (UsuarioModel) session.getAttribute("usuarioLogueado");
            if (usuarioLogueado == null) {
                return "redirect:/login"; 
            }

            List<ServicioModel> servicios = servicioService.obtenerServiciosPorUsuario(usuarioLogueado);

            // Obtener todos los periodos de todos los servicios del usuario
            List<PeriodoModel> periodos = servicios.stream()
                    .flatMap(servicio -> periodoService.obtenerPeriodosPorServicios(servicio).stream())
                    .toList();

            model.addAttribute("periodos", periodos);
            model.addAttribute("section", "mis-consumos");
            return "perfil_datos";
        }

        @GetMapping("/editar-consumo/{id}")
        public String editarConsumo(@PathVariable Long id, Model model, HttpSession session) {
            periodoService.obtenerPeriodoPorId(id).orElseThrow(() ->
                    new IllegalArgumentException("Consumo no encontrado con id: " + id));

            model.addAttribute("editarConsumoId", id);
            
            // Redirigimos a mis-consumos para mostrar todos los periodos con el modo edición activado
            return misConsumos(model, session);
        }

        @PostMapping("/actualizar-consumo/{id}")
        public String actualizarConsumo(@PathVariable Long id, @ModelAttribute PeriodoModel periodo) {
            PeriodoModel periodoExistente = periodoService.obtenerPeriodoPorId(id)
                    .orElseThrow(() -> new IllegalArgumentException("Consumo no encontrado con id: " + id));

            periodoExistente.setConsumo(periodo.getConsumo());
            periodoExistente.setMes(periodo.getMes());
            periodoExistente.setAno(periodo.getAno());
            
            periodoService.guardarPeriodo(periodoExistente);

            return "redirect:/mis-consumos";
        }

        @PostMapping("/eliminar-consumo/{id}")
        public String eliminarConsumo(@PathVariable Long id) {
            periodoService.eliminarPeriodo(id);
            return "redirect:/mis-consumos";
        }

}
