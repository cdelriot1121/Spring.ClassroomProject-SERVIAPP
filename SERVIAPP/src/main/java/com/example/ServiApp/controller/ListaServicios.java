package com.example.ServiApp.controller;

import java.util.ArrayList;

import com.example.ServiApp.model.ServicioModel;

public class ListaServicios {
    private ArrayList<ServicioModel> listaServicios;

    public ListaServicios() {
        listaServicios = new ArrayList<>();
    }

    public void agregarServicio(ServicioModel servicio) {
        listaServicios.add(servicio);
    }

    public ServicioModel obtenerServicio(int indice) {
        return listaServicios.get(indice);
    }

    public void eliminarServicio(int indice) {
        listaServicios.remove(indice);
    }

    public ArrayList<ServicioModel> obtenerTodosLosServicios() {
        return listaServicios;
    }

}
