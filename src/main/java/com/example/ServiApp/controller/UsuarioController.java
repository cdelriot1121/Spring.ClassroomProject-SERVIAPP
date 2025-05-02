package com.example.ServiApp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.ServiApp.model.PredioModel;
import com.example.ServiApp.model.ServicioModel;
import com.example.ServiApp.model.UsuarioModel;
import com.example.ServiApp.services.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

@PostMapping("/usuarios/registrar")
@ResponseBody // si estás devolviendo un mensaje o resultado como texto o JSON
public ResponseEntity<?> registrarUsuario(@RequestBody UsuarioModel usuario) {
    System.out.println("Contraseña antes de encriptar (primeros 4 caracteres): " +
                       (usuario.getPassword().length() > 10 ? usuario.getPassword().substring(0, 4) + "..." : usuario.getPassword()));

    usuario.setRol(UsuarioModel.Rol.ROLE_USUARIO);
    UsuarioModel usuarioRegistrado = usuarioService.registrarUsuario(usuario);

    if (usuarioRegistrado != null) {
        return ResponseEntity.ok("Registro exitoso");
    } else {
        return ResponseEntity.status(500).body("No se pudo registrar el usuario");
    }
}


    @GetMapping("/usuarios/verificar-email")
    public ResponseEntity<Boolean> verificarEmail(@RequestParam String email) {
        boolean existe = usuarioService.emailYaRegistrado(email);
        return ResponseEntity.ok(existe);
    }

    @GetMapping("/usuarios/contador")
    public ResponseEntity<Long> obtenerContadorUsuarios() {
        return ResponseEntity.ok(usuarioService.contarUsuarios());
    }

    @GetMapping("/interfaz_inicio")
    public String mostrarInterfazInicio(Model model, HttpSession session) {
        UsuarioModel usuarioLogueado = (UsuarioModel) session.getAttribute("usuarioLogueado");
        if (usuarioLogueado != null) {
            model.addAttribute("nombreUsuario", usuarioLogueado.getNombre());
        }
        return "interfaz_inicio";
    }

    @GetMapping("/datos-personales")
    public String mostrarDatosPersonales(Model model, HttpSession session) {
        UsuarioModel usuarioLogueado = (UsuarioModel) session.getAttribute("usuarioLogueado");
        model.addAttribute("section", "datos-personales");
        if (usuarioLogueado != null) {
            model.addAttribute("usuario", usuarioLogueado);
        } else {
            return "redirect:/login";
        }
        return "perfil_datos";
    }

    @GetMapping("/usuarios/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable String id, Model model) {
        UsuarioModel usuario = usuarioService.obtenerUsuarioPorId(id).orElseThrow(
            () -> new IllegalArgumentException("Usuario no encontrado con id: " + id)
        );
        model.addAttribute("usuario", usuario);
        model.addAttribute("editarUsuarioId", id);
        model.addAttribute("section", "datos-personales");
        return "perfil_datos";
    }

    @PostMapping("/usuarios/actualizar/{id}")
    public String actualizarUsuario(@PathVariable String id, 
                                  @RequestParam(value = "nombre", required = false) String nombre,
                                  @RequestParam(value = "email", required = false) String email,
                                  Model model,
                                  HttpSession session) {
        
        try {
            // Para debugging
            System.out.println("Actualizando usuario ID: " + id);
            System.out.println("Nombre recibido: [" + nombre + "]");
            System.out.println("Email recibido: [" + email + "]");
            
            UsuarioModel usuarioExistente = usuarioService.obtenerUsuarioPorId(id)
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con id: " + id));
            
            // Verificar y actualizar cada campo si no es nulo o vacío
            if (nombre != null && !nombre.trim().isEmpty()) {
                usuarioExistente.setNombre(nombre.trim());
            }
            
            if (email != null && !email.trim().isEmpty()) {
                usuarioExistente.setEmail(email.trim());
            }
            
            // Guardar el usuario actualizado
            UsuarioModel usuarioActualizado = usuarioService.guardarUsuario(usuarioExistente);
            
            // Actualizar la sesión con el usuario actualizado
            session.setAttribute("usuarioLogueado", usuarioActualizado);
            
            model.addAttribute("usuario", usuarioActualizado);
            model.addAttribute("section", "datos-personales");
            model.addAttribute("mensaje", "Datos actualizados correctamente");
            
            return "perfil_datos";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al actualizar: " + e.getMessage());
            model.addAttribute("section", "datos-personales");
            
            // Recuperar el usuario para mostrarlo en la vista
            UsuarioModel usuario = usuarioService.obtenerUsuarioPorId(id).orElse(new UsuarioModel());
            model.addAttribute("usuario", usuario);
            
            return "perfil_datos";
        }
    }

    @GetMapping("/cambiar-contrasena")
    public String mostrarCambiarContrasena(Model model) {
        model.addAttribute("section", "cambiar-contrasena");
        return "perfil_datos";
    }

    @PostMapping("/usuarios/cambiar-contrasena")
    public String cambiarContraseña(@RequestParam String currentPassword,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword,
            HttpSession session,
            Model model) {

        UsuarioModel usuarioLogueado = (UsuarioModel) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado == null) {
            return "redirect:/login";
        }

        if (!passwordEncoder.matches(currentPassword, usuarioLogueado.getPassword())) {
            model.addAttribute("error", "La contraseña actual es incorrecta");
            model.addAttribute("section", "cambiar-contrasena");
            return "perfil_datos";
        }

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Las nuevas contraseñas no coinciden");
            model.addAttribute("section", "cambiar-contrasena");
            return "perfil_datos";
        }

        // Obtener el usuario fresco de la base de datos
        Optional<UsuarioModel> usuarioActualizado = usuarioService.obtenerUsuarioPorId(usuarioLogueado.getId());
        
        if (usuarioActualizado.isPresent()) {
            // Actualizar solo la contraseña
            usuarioActualizado.get().setPassword(passwordEncoder.encode(newPassword));
            // Guardar el usuario con todos sus datos
            UsuarioModel usuarioGuardado = usuarioService.guardarUsuario(usuarioActualizado.get());
            // Actualizar la sesión
            session.setAttribute("usuarioLogueado", usuarioGuardado);
        } else {
            model.addAttribute("error", "Error al actualizar la contraseña");
            model.addAttribute("section", "cambiar-contrasena");
            return "perfil_datos";
        }

        model.addAttribute("mensaje", "¡Contraseña cambiada con éxito!");
        model.addAttribute("section", "cambiar-contrasena");
        return "perfil_datos";
    }

    @GetMapping("/mis-servicios")
    public String listarServicios(Model model, HttpSession session) {
        UsuarioModel usuarioLogueado = (UsuarioModel) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado != null) {
            List<ServicioModel> servicios = usuarioService.obtenerServiciosPorUsuario(usuarioLogueado.getId());
            model.addAttribute("servicios", servicios);
            model.addAttribute("section", "mis-servicios");
            return "perfil_datos";
        } else {
            model.addAttribute("error", "No hay usuario autenticado");
            return "iniciosesion";
        }
    }

    @GetMapping("/mi-predio")
    public String mostrarMiPredio(Model model, HttpSession session) {
        UsuarioModel usuarioLogueado = (UsuarioModel) session.getAttribute("usuarioLogueado");
        
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }
        
        // Verificar si el usuario tiene un predio registrado
        Optional<PredioModel> predio = usuarioService.obtenerPredioPorUsuario(usuarioLogueado.getId());
        
        if (predio.isPresent()) {
            model.addAttribute("predio", predio.get());
        }
        
        // Añadir tipos de predio para el formulario
        model.addAttribute("tiposPredio", PredioModel.TipoPredio.values());
        model.addAttribute("section", "mi-predio");
        
        return "perfil_datos";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, 
                       @RequestParam String password,
                       Model model, 
                       HttpSession session) {
        
        UsuarioModel usuario = usuarioService.autenticar(email, password);
        
        if (usuario != null) {
            // Verificar si el usuario está habilitado
            if (usuario.getEstado() == UsuarioModel.EstadoUsuario.DESHABILITADO) {
                return "redirect:/error/usuario-inhabilitado";
            }
            
            session.setAttribute("usuarioLogueado", usuario);
            
            // Verificar si necesita completar el registro
            if (!usuario.isRegistroCompleto()) {
                return "redirect:/completar-registro";
            }
            
            // Verificar si tiene un predio registrado
            boolean tienePredio = usuarioService.existePredioParaUsuario(usuario.getId());
            
            if (!tienePredio) {
                return "redirect:/registrar-predio-obligatorio";
            }
            
            return "redirect:/interfaz_inicio";
        } else {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "iniciosesion";
        }
    }
    
    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
