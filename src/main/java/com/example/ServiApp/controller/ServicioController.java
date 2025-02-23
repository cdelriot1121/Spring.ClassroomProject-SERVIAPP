package com.example.ServiApp.controller;

import java.util.List;
import java.util.Map;

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
import com.example.ServiApp.services.ServiciosService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/servicios")
public class ServicioController {

    // estos dos metodos se usan para estructurar las respuestas del backend en caso de errores o exito. 
    //si algo sale mal (como servicio duplicado o usuario no autenticado), se usa ErrorResponse para mandar un mensaje de error y un detalle. 
    //si todo esta bien y la validacion pasa, se usa 
    //SuccessResponse para mandar un mensaje de exito, todo en formato JSON.


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
    




//-----------------------------------------------------------------------------------------
    

    @Autowired
    private ServiciosService servicioService;

    @PostMapping("/registrar-servicio")
    public String registrarServicio(@ModelAttribute ServicioModel servicio, Model model, HttpSession session) {

        UsuarioModel usuarioLogueado = (UsuarioModel) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado != null) {

            if (servicioService.existeServicioPorTipoYUsuario(servicio.getTipo_servicio(),usuarioLogueado)){
                model.addAttribute("errorDuplicado", "Ya tienes este servicio registrado");
                return "redirect:/registrar-servicio";
            }
             servicio.setUsuario(usuarioLogueado);
            servicioService.registrarservicio(servicio);

            model.addAttribute("mensaje", "Servicio registrado exitosamente");
            return "redirect:/registrar-servicio";
        } else {
            model.addAttribute("error", "No hay usuario autenticado");
            return "iniciosesion";  
        }
    }

     @GetMapping("/editar/{id}")
    public String editarServicio(@PathVariable Long id, Model model) {
        servicioService.obtenerServicioPorId(id).orElseThrow(() ->
        new IllegalArgumentException("Servicio no encontrado con id: " + id));

        model.addAttribute("editarServicioId", id);
        model.addAttribute("servicios", servicioService.ObtenerServicios());
        model.addAttribute("section", "mis-servicios");

        return "perfil_datos";
        }


    @PostMapping("/actualizar/{id}")
    public String actualizarServicio(@PathVariable Long id, @ModelAttribute ServicioModel servicio) {
        ServicioModel servicioExistente = servicioService.obtenerServicioPorId(id)
        .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado con id: " + id));

        servicioExistente.setPoliza(servicio.getPoliza());
        servicioExistente.setHabitantes(servicio.getHabitantes());
        servicioService.actualizarServicio(id, servicioExistente);

        return "redirect:/mis-servicios"; 
    }



    @PostMapping("/eliminar/{id}")
    public String eliminarServicio(@PathVariable Long id){
        servicioService.eliminarServicio(id);
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
    if (servicioService.existeServicioPorTipoYUsuario(tipoServicio, usuarioLogueado)) {
        return ResponseEntity.ok(new ErrorResponse("Ya tienes este servicio registrado", "No puedes registrar un servicio duplicado."));
    }

    // Verificar si el usuario ha alcanzado el limite de servicios
    List<ServicioModel> servicios = servicioService.obtenerServiciosPorUsuario(usuarioLogueado);
    if (servicios.size() >= 3) {
        return ResponseEntity.ok(new ErrorResponse("Has alcanzado el límite de servicios", "Solo puedes registrar hasta 3 servicios diferentes."));
    }

    return ResponseEntity.ok(new SuccessResponse("Servicio válido para registro"));
}





}
