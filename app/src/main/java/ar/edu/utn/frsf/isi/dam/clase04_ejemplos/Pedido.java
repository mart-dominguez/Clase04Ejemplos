package ar.edu.utn.frsf.isi.dam.clase04_ejemplos;

import java.util.Random;

public class Pedido {
    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    private int id;
    private String nombre;

    private int cantidad;
    public Pedido(int id, String nombre) {
        this();
        this.id = id;
        this.nombre = nombre;
    }

    public Pedido() {
        Random r = new Random();
        this.cantidad = r.nextInt(10);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
