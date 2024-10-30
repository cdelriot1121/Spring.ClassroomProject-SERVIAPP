package com.example.ServiApp.controller;

import java.util.ArrayList;

import com.example.ServiApp.model.CortesModel;

public class ListaConsumos {
    private ArrayList<CortesModel> listaConsumos;

    public ListaConsumos() {
        listaConsumos = new ArrayList<>();
    }

    public void agregarConsumo(CortesModel consumo) {
        listaConsumos.add(consumo);
    }

  
    public CortesModel obtenerConsumo(int indice) {
        return listaConsumos.get(indice);
    }

    public void eliminarConsumo(int indice) {
        listaConsumos.remove(indice);
    }

    public ArrayList<CortesModel> obtenerTodosLosConsumos() {
        return listaConsumos;
    }
}
