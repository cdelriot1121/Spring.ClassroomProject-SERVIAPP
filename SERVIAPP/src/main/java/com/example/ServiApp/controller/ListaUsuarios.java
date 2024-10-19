package com.example.ServiApp.controller;

import java.util.ArrayList;

import com.example.ServiApp.model.UsuarioModel;



public class ListaUsuarios {
    private ArrayList<UsuarioModel> listaUsuarios;

    public ListaUsuarios() {
        listaUsuarios = new ArrayList<>();
    }

    public void agregarUsuario(UsuarioModel usuario) {
        listaUsuarios.add(usuario);
    }

    public UsuarioModel obtenerUsuario(int indice) {
        return listaUsuarios.get(indice);
    }

    public void eliminarUsuario(int indice) {
        listaUsuarios.remove(indice);
    }

    public ArrayList<UsuarioModel> obtenerTodosLosUsuarios() {
        return listaUsuarios;
    }

}
