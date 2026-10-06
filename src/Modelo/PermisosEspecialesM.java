/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

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
public class PermisosEspecialesM {
    public int id = 0;
    public String nombre = null;
    public int permiso = 0;

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

    public int getPermiso() {
        return permiso;
    }

    public void setPermiso(int permiso) {
        this.permiso = permiso;
    }
    
    public boolean GuardarPermisosEspeciales(){
       
       Conexion cc = new Conexion();
       java.sql.Connection cn = cc.getConexion();
       
       try {
            CallableStatement proc = cn.prepareCall("CALL GuardarPermisosEspeciales(?,?,?)");
            proc.setInt(1, getId());
            proc.setString(2, getNombre());
            proc.setInt(3, getPermiso());
            
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
    
    public DefaultTableModel CargarPermisosEspeciales(){
        Conexion cc = new Conexion();
        Connection cn = cc.getConexion();  
        DefaultTableModel modelo_pe = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        }; 
        
        try{
            Statement smt = cn.createStatement();
            ResultSet rs = null;
            CallableStatement proc = cn.prepareCall("CALL GuardarPermisosEspeciales(?,?,?);");
            proc.setInt(1, getId());
            proc.setString(2, getNombre());
            proc.setInt(3, getPermiso());
            
            rs = proc.executeQuery();
            ResultSetMetaData md = rs.getMetaData();
            int columnas= md.getColumnCount();
            for (int i = 1; i <= columnas; i++) {
                modelo_pe.addColumn(md.getColumnLabel(i));
            }
            
            while(rs.next()){
                Object[] fila = new Object[columnas];
                for (int i = 0; i < columnas; i++) {fila[i]=rs.getObject(i+1);}
                modelo_pe.addRow(fila);
            }
        }catch(SQLException sqlex){
            System.out.println("Problema CargarDatos " + sqlex.getMessage() + "/" + sqlex.getErrorCode() + " Reporte Permisos Especiales");
        }finally{
            try{
                cn.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        }
        
        return modelo_pe;
    }
    
    public int obtenerPermisoEspecial(String nombrePermiso) {
        Conexion cc = new Conexion();
        Connection cn = cc.getConexion();
        int permiso = 0;

        try {
            CallableStatement proc = cn.prepareCall(
                "CALL GuardarPermisosEspeciales(?, ?, ?)"
            );
            proc.setInt(1, -2);
            proc.setString(2, nombrePermiso);
            proc.setInt(3, 0);

            ResultSet rs = proc.executeQuery();

            if (rs.next()) {
                permiso = rs.getInt("PERMISO"); // o índice 1
            }

        } catch (SQLException e) {
            System.out.println("Error obtenerPermisoEspecial: " + e.getMessage());
        } finally {
            try { cn.close(); } catch (SQLException e) {}
        }

        return permiso;
    }
    
}
