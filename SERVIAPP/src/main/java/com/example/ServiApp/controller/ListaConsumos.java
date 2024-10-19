package com.example.ServiApp.controller;

import java.util.ArrayList;

import com.example.ServiApp.model.ConsumoModel;

public class ListaConsumos {
    private ArrayList<ConsumoModel> listaConsumos;

    public ListaConsumos() {
        listaConsumos = new ArrayList<>();
    }

    public void agregarConsumo(ConsumoModel consumo) {
        listaConsumos.add(consumo);
    }

  
    public ConsumoModel obtenerConsumo(int indice) {
        return listaConsumos.get(indice);
    }

    public void eliminarConsumo(int indice) {
        listaConsumos.remove(indice);
    }

    public ArrayList<ConsumoModel> obtenerTodosLosConsumos() {
        return listaConsumos;
    }
}
