/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author ChristianSepulveda
 */
public class TotalMetodoPagoM {
    private int metodo;
    private String nombre;
    private double total;

    public TotalMetodoPagoM(int metodo, String nombre, double total) {
        this.metodo = metodo;
        this.nombre = nombre;
        this.total = total;
    }

    public int getMetodo() {
        return metodo;
    }

    public String getNombre() {
        return nombre;
    }

    public double getTotal() {
        return total;
    }
}
