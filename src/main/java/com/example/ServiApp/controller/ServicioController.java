package com.example.ServiApp.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.ServiApp.model.ServicioModel;
import com.example.ServiApp.model.UsuarioModel;
import com.example.ServiApp.repository.UsuarioRepository;
import com.example.ServiApp.services.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/servicios")
public class ServicioController {

        @Autowired
    private UsuarioRepository usuarioRepository;

    // Clases para respuestas estructuradas JSON
    public class ErrorResponse {
        private String error;
        private String errorMessage;
    
        public ErrorResponse(String error, String errorMessage) {
            this.error = error;
            this.errorMessage = errorMessage;
        }
    
        public String getError() {
            return error;
        }
    
        public String getErrorMessage() {
            return errorMessage;
        }
    }
    
    public class SuccessResponse {
        private String message;
    
        public SuccessResponse(String message) {
            this.message = message;
        }
    
        public String getMessage() {
            return message;
        }
    }
    
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registrar-servicio")
    public String registrarServicio(@ModelAttribute ServicioModel servicio, Model model, HttpSession session) {
        UsuarioModel usuarioLogueado = (UsuarioModel) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado != null) {
            // Verificar si ya existe el servicio
            if (usuarioService.existeServicioPorTipoYUsuario(servicio.getTipo_servicio(), usuarioLogueado.getId())) {
                model.addAttribute("errorDuplicado", "Ya tienes este servicio registrado");
                return "redirect:/registrar-servicio";
            }
            
            // Registrar el servicio
            usuarioService.registrarServicio(usuarioLogueado.getId(), servicio);

            model.addAttribute("mensaje", "Servicio registrado exitosamente");
            return "redirect:/registrar-servicio";
        } else {
            model.addAttribute("error", "No hay usuario autenticado");
            return "iniciosesion";  
        }
    }

    @GetMapping("/editar/{id}")
    public String editarServicio(@PathVariable String id, Model model, HttpSession session) {
        UsuarioModel usuarioLogueado = (UsuarioModel) session.getAttribute("usuarioLogueado");
        
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }
        
        Optional<ServicioModel> servicio = usuarioService.obtenerServicioPorId(usuarioLogueado.getId(), id);
        if (!servicio.isPresent()) {
            return "redirect:/mis-servicios";
        }

        model.addAttribute("editarServicioId", id);
        model.addAttribute("servicios", usuarioService.obtenerServiciosPorUsuario(usuarioLogueado.getId()));
        model.addAttribute("servicioEditar", servicio.get());
        model.addAttribute("section", "mis-servicios");

        return "perfil_datos";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarServicio(@PathVariable String id, @ModelAttribute ServicioModel servicio, HttpSession session) {
        UsuarioModel usuarioLogueado = (UsuarioModel) session.getAttribute("usuarioLogueado");
        
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }
        
        usuarioService.actualizarServicio(usuarioLogueado.getId(), id, servicio);

        return "redirect:/mis-servicios"; 
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarServicio(@PathVariable String id, HttpSession session) {
        UsuarioModel usuarioLogueado = (UsuarioModel) session.getAttribute("usuarioLogueado");
        
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }
        
        usuarioService.eliminarServicio(usuarioLogueado.getId(), id);
        return "redirect:/mis-servicios";
    }

    @PostMapping("/validar-servicio")
    @ResponseBody
    public ResponseEntity<?> validarServicio(@RequestBody Map<String, String> request, HttpSession session) {
        String tipoServicio = request.get("tipo_servicio");
        UsuarioModel usuarioLogueado = (UsuarioModel) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("Usuario no autenticado", tipoServicio));
        }

        // Verificar si el usuario ya tiene el servicio registrado
        if (usuarioService.existeServicioPorTipoYUsuario(tipoServicio, usuarioLogueado.getId())) {
            return ResponseEntity.ok(new ErrorResponse("Ya tienes este servicio registrado", "No puedes registrar un servicio duplicado."));
        }

        // Verificar si el usuario ha alcanzado el limite de servicios
        List<ServicioModel> servicios = usuarioService.obtenerServiciosPorUsuario(usuarioLogueado.getId());
        if (servicios.size() >= 3) {
            return ResponseEntity.ok(new ErrorResponse("Has alcanzado el límite de servicios", "Solo puedes registrar hasta 3 servicios diferentes."));
        }

        return ResponseEntity.ok(new SuccessResponse("Servicio válido para registro"));
    }

 

 // Contador de servicios
 @GetMapping("/contador")
 public ResponseEntity<Long> contarServicios() {
     // Obtener todos los usuarios desde la base de datos
     List<UsuarioModel> usuarios = usuarioRepository.findAll();

     // Contar todos los servicios embebidos en los usuarios
     long totalServicios = usuarios.stream()
         .filter(u -> u.getServicios() != null)  // Filtrar usuarios que tienen servicios
         .mapToLong(u -> u.getServicios().size())  // Sumar el tamaño de la lista de servicios de cada usuario
         .sum();  // Sumar todos los servicios

     return ResponseEntity.ok(totalServicios);  // Retornar el total de servicios
 }




}
