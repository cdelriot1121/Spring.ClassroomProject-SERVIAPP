package com.example.ServiApp.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ServiApp.model.PredioModel;
import com.example.ServiApp.model.ServicioModel;
import com.example.ServiApp.model.UsuarioModel;
import com.example.ServiApp.repository.UsuarioRepository;
import com.mongodb.client.result.UpdateResult;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Registrar un nuevo usuario (con contraseña encriptada)
    public UsuarioModel registrarUsuario(UsuarioModel usuario) {
        // Validar si el email ya está registrado
        if (emailYaRegistrado(usuario.getEmail())) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado.");
        }

        // Encriptar la contraseña antes de guardar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    public Page<UsuarioModel> obtenerUsuariosPaginados(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    // Guardar o actualizar un usuario
    public UsuarioModel guardarUsuario(UsuarioModel usuario) {
        return usuarioRepository.save(usuario);
    }

    public long contarUsuarios() {
        return usuarioRepository.count();
    }

    public Optional<UsuarioModel> obtenerUsuarioPorId(String id) {
        return usuarioRepository.findById(id);
    }

    public List<UsuarioModel> obtenerUsuarios() {
        return usuarioRepository.findAll();
    }

    public void inhabilitarUsuario(String id) {
        Optional<UsuarioModel> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            UsuarioModel usuario = usuarioOptional.get();
            usuario.setEstado(UsuarioModel.EstadoUsuario.DESHABILITADO);
            usuarioRepository.save(usuario);
        }
    }

    public void habilitarUsuario(String id) {
        Optional<UsuarioModel> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            UsuarioModel usuario = usuarioOptional.get();
            usuario.setEstado(UsuarioModel.EstadoUsuario.HABILITADO);
            usuarioRepository.save(usuario);
        }
    }

    public boolean emailYaRegistrado(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    // Autenticación manual
    public UsuarioModel autenticar(String email, String contraseña) {
        return usuarioRepository.findByEmail(email)
                .filter(u -> passwordEncoder.matches(contraseña, u.getPassword()))
                .orElse(null);
    }

    // Autenticación solo para administradores
    public UsuarioModel autenticarAdministrador(String email, String contraseña) {
        return usuarioRepository.findByEmail(email)
                .filter(u -> u.getRol() == UsuarioModel.Rol.ROLE_ADMINISTRADOR &&
                        passwordEncoder.matches(contraseña, u.getPassword()))
                .orElse(null);
    }

    public List<UsuarioModel> obtenerUsuariosNormales() {
        return usuarioRepository.findByRol(UsuarioModel.Rol.ROLE_USUARIO);
    }

    public List<UsuarioModel> obtenerAdministradores() {
        return usuarioRepository.findByRol(UsuarioModel.Rol.ROLE_ADMINISTRADOR);
    }

    // ------------ Métodos para gestionar predios embebidos ------------

    public void registrarPredioPara(String usuarioId, PredioModel predio) {
        Optional<UsuarioModel> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isPresent()) {
            UsuarioModel usuario = usuarioOpt.get();
            usuario.setPredio(predio);
            usuarioRepository.save(usuario);
        }
    }

    public Optional<PredioModel> obtenerPredioPorUsuario(String usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .map(UsuarioModel::getPredio);
    }

    public boolean existePredioParaUsuario(String usuarioId) {
        return obtenerPredioPorUsuario(usuarioId).isPresent();
    }

    public void actualizarPredio(String usuarioId, PredioModel predio) {
        Optional<UsuarioModel> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isPresent()) {
            UsuarioModel usuario = usuarioOpt.get();
            usuario.setPredio(predio);
            usuarioRepository.save(usuario);
        }
    }

    // ------------ Métodos para gestionar servicios embebidos ------------

    public ServicioModel registrarServicio(String usuarioId, ServicioModel servicio) {
        Optional<UsuarioModel> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isPresent()) {
            UsuarioModel usuario = usuarioOpt.get();

            // Generar un ID único para el servicio
            servicio.setId(UUID.randomUUID().toString());

            usuario.addServicio(servicio);
            usuarioRepository.save(usuario);
            return servicio;
        }
        return null;
    }

    public List<ServicioModel> obtenerServiciosPorUsuario(String usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .map(UsuarioModel::getServicios)
                .orElse(Collections.emptyList());
    }

    public Optional<ServicioModel> obtenerServicioPorId(String usuarioId, String servicioId) {
        return usuarioRepository.findById(usuarioId)
                .flatMap(usuario -> usuario.getServicios().stream()
                        .filter(s -> s.getId().equals(servicioId))
                        .findFirst());
    }

    public boolean existeServicioPorTipoYUsuario(String tipoServicio, String usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .map(usuario -> usuario.getServicios().stream()
                        .anyMatch(s -> s.getTipo_servicio().equals(tipoServicio)))
                .orElse(false);
    }

    public boolean actualizarServicio(String usuarioId, String servicioId, ServicioModel servicioActualizado) {
        try {
            // Primero, encontrar el usuario por ID
            Optional<UsuarioModel> usuarioOpt = usuarioRepository.findById(usuarioId);
            if (usuarioOpt.isEmpty()) {
                System.err.println("Usuario no encontrado con ID: " + usuarioId);
                return false;
            }

            UsuarioModel usuario = usuarioOpt.get();
            boolean servicioEncontrado = false;

            // Buscar el servicio específico en el array de servicios
            for (int i = 0; i < usuario.getServicios().size(); i++) {
                if (usuario.getServicios().get(i).getId().equals(servicioId)) {
                    // Actualizamos todas las propiedades del servicio, preservando el ID
                    ServicioModel servicioExistente = usuario.getServicios().get(i);
                    servicioExistente.setTipo_servicio(servicioActualizado.getTipo_servicio());
                    servicioExistente.setEmpresa(servicioActualizado.getEmpresa());
                    servicioExistente.setPoliza(servicioActualizado.getPoliza());
                    servicioExistente.setHabitantes(servicioActualizado.getHabitantes());

                    // SOLUCIÓN: No sobrescribir la lista de períodos existente
                    // Conservar la lista de períodos original en lugar de sobrescribirla
                    // servicioExistente.setPeriodosIds(servicioActualizado.getPeriodosIds());

                    servicioEncontrado = true;
                    break;
                }
            }

            if (!servicioEncontrado) {
                System.err.println("Servicio no encontrado con ID: " + servicioId + " en usuario: " + usuarioId);
                return false;
            }

            // Guardar el usuario actualizado en la base de datos
            usuarioRepository.save(usuario);

            System.out.println("Servicio actualizado correctamente con el período");
            return true;
        } catch (Exception e) {
            System.err.println("Error al actualizar servicio: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void eliminarServicio(String usuarioId, String servicioId) {
        Optional<UsuarioModel> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isPresent()) {
            UsuarioModel usuario = usuarioOpt.get();
            usuario.setServicios(usuario.getServicios().stream()
                    .filter(s -> !s.getId().equals(servicioId))
                    .collect(java.util.stream.Collectors.toList()));
            usuarioRepository.save(usuario);
        }
    }

    public List<ServicioModel> obtenerTodosLosServicios() {
        List<UsuarioModel> usuarios = usuarioRepository.findAll();
        List<ServicioModel> todosLosServicios = new ArrayList<>();

        for (UsuarioModel usuario : usuarios) {
            todosLosServicios.addAll(usuario.getServicios());
        }

        return todosLosServicios;
    }

    // Añadir este método al final de la clase UsuarioService
    public String encontrarUsuarioIdPorServicioId(String servicioId) {
        for (UsuarioModel usuario : usuarioRepository.findAll()) {
            for (ServicioModel servicio : usuario.getServicios()) {
                if (servicio.getId().equals(servicioId)) {
                    return usuario.getId();
                }
            }
        }
        return null;
    }

    // En UsuarioService.java
    @Autowired
    private MongoTemplate mongoTemplate;

    // En UsuarioService.java
    public boolean añadirPeriodoAServicio(String usuarioId, String servicioId, String periodoId) {
        try {
            // Implementar directamente con una actualización atómica en MongoDB
            // Usando $push para agregar el periodoId al array sin cargar todo el documento
            Query query = new Query(Criteria.where("_id").is(usuarioId)
                    .and("servicios.servicio_id").is(servicioId));

            Update update = new Update().push("servicios.$.periodos_ids", periodoId);

            UpdateResult result = mongoTemplate.updateFirst(query, update, UsuarioModel.class);

            boolean actualizado = result.getModifiedCount() > 0;
            if (actualizado) {
                System.out.println("✅ Período " + periodoId + " añadido al servicio " + servicioId);
            } else {
                System.err.println("❌ No se pudo añadir el período al servicio");
            }
            return actualizado;
        } catch (Exception e) {
            System.err.println("Error al añadir período a servicio: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<Map<String, Object>> recolectarServiciosUsuarios() {
        List<UsuarioModel> usuarios = usuarioRepository.findAll();
        List<Map<String, Object>> serviciosConUsuario = new ArrayList<>();

        for (UsuarioModel usuario : usuarios) {
            for (ServicioModel servicio : usuario.getServicios()) {
                Map<String, Object> servicioMap = new HashMap<>();
                servicioMap.put("id", servicio.getId());
                servicioMap.put("tipo_servicio", servicio.getTipo_servicio());
                servicioMap.put("empresa", servicio.getEmpresa());
                servicioMap.put("poliza", servicio.getPoliza());
                servicioMap.put("habitantes", servicio.getHabitantes());
                servicioMap.put("usuario", new HashMap<String, Object>() {
                    {
                        put("id", usuario.getId());
                        put("nombre", usuario.getNombre());
                    }
                });

                serviciosConUsuario.add(servicioMap);
            }
        }

        return serviciosConUsuario;
    }
}
