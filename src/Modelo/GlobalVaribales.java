/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author ChristianSepulveda
 */
public class GlobalVaribales {
    private static GlobalVaribales instance;
    public String usuarioLogueado;
    public String nombreLogueado;

    private GlobalVaribales() {
        // Constructor privado
    }

    public static GlobalVaribales getInstance() {
        if (instance == null) {
            instance = new GlobalVaribales();
        }
        return instance;
    }
    
}
