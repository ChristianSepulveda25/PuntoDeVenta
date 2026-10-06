/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ChristianSepulveda
 */
public class UsuariosM {
    public int id = 0;
    public String nombre = null;
    public String usuario = null;
    public String contrasena = null;
    public String contrasenaValidation = null;
    public String nomUsuario = null;
    public int tipo = 0;
    public int estatus = 0;

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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public String getContrasenaValidation() {
        return contrasenaValidation;
    }

    public void setContrasenaValidation(String contrasenaValidation) {
        this.contrasenaValidation = contrasenaValidation;
    }

    public String getNomUsuario() {
        return nomUsuario;
    }

    public void setNomUsuario(String nomUsuario) {
        this.nomUsuario = nomUsuario;
    }
    
    public boolean GuardarUsuarios(){
       
       Conexion cc = new Conexion();
       java.sql.Connection cn = cc.getConexion();
       
       try {
            CallableStatement proc = cn.prepareCall("CALL GuardarUsuarios(?,?,?,?,?,?)");
            proc.setInt(1, getId());
            proc.setString(2, getNombre());
            proc.setString(3, getUsuario());
            proc.setString(4, getContrasena());
            proc.setInt(5, getTipo());
            proc.setInt(6, getEstatus());
            
            proc.execute();
            
            return true;
        }catch (SQLException e) {                  
            System.out.println(e);
            return false;
        }finally{
            try{
                cn.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        }
    }
    
    public DefaultTableModel CargarUsuarios(){
        Conexion cc = new Conexion();
        Connection cn = cc.getConexion();  
        DefaultTableModel modelo_ese = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        }; 
        
        try{
            
            Statement smt = cn.createStatement();
            ResultSet rs = null;
            CallableStatement proc = cn.prepareCall("CALL GuardarUsuarios(?,?,?,?,?,?);");
            proc.setInt(1, getId());
            proc.setString(2, getNombre());
            proc.setString(3, getUsuario());
            proc.setString(4, getContrasena());
            proc.setInt(5, getTipo());
            proc.setInt(6, getEstatus());
            
            rs = proc.executeQuery();
            ResultSetMetaData md = rs.getMetaData();
            int columnas= md.getColumnCount();
            for (int i = 1; i <= columnas; i++) {
                modelo_ese.addColumn(md.getColumnLabel(i));
            }
            
            while(rs.next()){
                Object[] fila = new Object[columnas];
                for (int i = 0; i < columnas; i++) {fila[i]=rs.getObject(i+1);}
                modelo_ese.addRow(fila);
            }
        }catch(SQLException sqlex){
            System.out.println("Problema CargarDatos " + sqlex.getMessage() + "/" + sqlex.getErrorCode() + " Reporte ESProductos");
        }finally{
            try{
                cn.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        }
        
        return modelo_ese;
    }
    
    public int validarUsuarioYContrasena(String usuario, String contrasena) {
        Conexion cc = new Conexion();
        Connection cn = cc.getConexion();  
        CallableStatement stmt = null;
        ResultSet rs = null;

        try {
            // Conectar a la base de datos
            stmt = cn.prepareCall("{call validarUsuario(?, ?)}");
            stmt.setString(1, usuario);
            stmt.setString(2, contrasena);

            // Ejecutar el procedimiento
            rs = stmt.executeQuery();

            // Procesar el resultado
            if (rs.next()) {
                String pass = rs.getString("contra");
                String nomUsuario_ = rs.getString("nom_usuario");
                int existe = rs.getInt("existe");

                if (existe == 0) {
                    // return "¡Usuario no encontrado!";
                    return 0;
                } else {
                    // return "¡Usuario encontrado!";
                    this.setContrasenaValidation(pass);
                    this.setNomUsuario(nomUsuario_);
                    return 1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // return "Error en la validación.";
            return 3;
        } finally {
            // Cerrar recursos
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // return "Error en la validación.";
        return 4;
    }

    
}
