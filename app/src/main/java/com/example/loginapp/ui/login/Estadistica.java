package com.example.loginapp.ui.login;

import java.io.Serializable;
import java.util.ArrayList;

public class Estadistica implements Serializable {
    private String usuario;
    private String inicio;
    private String fin;
    private String tiradas;
    private ArrayList<String> movimientos;

    public Estadistica() {
    }

    public Estadistica(String usuario, String inicio, String fin, String tiradas, ArrayList<String> movimientos) {
        this.usuario = usuario;
        this.inicio = inicio;
        this.fin = fin;
        this.tiradas = tiradas;
        this.movimientos = movimientos;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public String getTiradas() {
        return tiradas;
    }

    public void setTiradas(String tiradas) {
        this.tiradas = tiradas;
    }

    public ArrayList<String> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(ArrayList<String> movimientos) {
        this.movimientos = movimientos;
    }

    @Override
    public String toString() {
        return "Estadistica{" +
                "usuario=" + usuario +
                ", inicio=" + inicio +
                ", fin=" + fin +
                ", tiradas=" + tiradas +
                ", movimientos='" + movimientos + '\'' +
                '}';
    }
}
