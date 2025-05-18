package com.example.ServiApp.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ServiApp.model.UsuarioModel;
import com.example.ServiApp.services.UsuarioService;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioControllerAPI {

     @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @ResponseBody
    public Map<String, Object> obtenerUsuariosPaginados(
            @RequestParam("draw") int draw,
            @RequestParam("start") int start,
            @RequestParam("length") int length) {

        int page = start / length;
        PageRequest pageable = PageRequest.of(page, length);
        Page<UsuarioModel> pageUsuarios = usuarioService.obtenerUsuariosPaginados(pageable);

        List<Map<String, Object>> data = pageUsuarios.getContent().stream().map(usuario -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", usuario.getId());
            map.put("nombre", usuario.getNombre());
            map.put("email", usuario.getEmail());
            map.put("estrato", usuario.getEstratoDesdePredio() != null ? usuario.getEstratoDesdePredio() : "No definido");
            map.put("estado", usuario.getEstado().toString());
            return map;
        }).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("draw", draw);
        response.put("recordsTotal", pageUsuarios.getTotalElements());
        response.put("recordsFiltered", pageUsuarios.getTotalElements());
        response.put("data", data);

        return response;
    }

}
