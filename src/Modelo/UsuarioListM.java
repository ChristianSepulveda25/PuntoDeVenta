/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author ChristianSepulveda
 */
public class UsuarioListM {
    private int id;
    private String nombre;
    private String user;

    public UsuarioListM(int id, String nombre, String user) {
        this.id = id;
        this.nombre = nombre;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUser() {
        return user;
    }

    @Override
    public String toString() {
        return nombre; // Esto hará que el JComboBox solo muestre el nombre
    }
}
