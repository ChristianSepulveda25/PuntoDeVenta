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
public class PreciosPorcentajesM {
    public int id = 0;
    public int id_cat = 0;
    public int precio = 0;
    public int mayoreo = 0;
    public int menudeo = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_cat() {
        return id_cat;
    }

    public void setId_cat(int id_cat) {
        this.id_cat = id_cat;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getMayoreo() {
        return mayoreo;
    }

    public void setMayoreo(int mayoreo) {
        this.mayoreo = mayoreo;
    }

    public int getMenudeo() {
        return menudeo;
    }

    public void setMenudeo(int menudeo) {
        this.menudeo = menudeo;
    }
    
    public boolean GuardarPreciosPorcentajes(){
       
       Conexion cc = new Conexion();
       java.sql.Connection cn = cc.getConexion();
       
       try {
            CallableStatement proc = cn.prepareCall("CALL GuardarPreciosPorcentaje(?,?,?,?,?)");
            proc.setInt(1, getId());
            proc.setInt(2, getId_cat());
            proc.setInt(3, getPrecio());
            proc.setInt(4, getMayoreo());
            proc.setInt(5, getMenudeo());
            
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
    
    public DefaultTableModel CargarPreciosPorcentajes(){
        Conexion cc = new Conexion();
        Connection cn = cc.getConexion();  
        DefaultTableModel modelo_pp = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        }; 
        
        try{
            
            Statement smt = cn.createStatement();
            ResultSet rs = null;
            CallableStatement proc = cn.prepareCall("CALL GuardarPreciosPorcentaje(?,?,?,?,?);");
            proc.setInt(1, getId());
            proc.setInt(2, getId_cat());
            proc.setInt(3, getPrecio());
            proc.setInt(4, getMayoreo());
            proc.setInt(5, getMenudeo());
            
            rs = proc.executeQuery();
            ResultSetMetaData md = rs.getMetaData();
            int columnas= md.getColumnCount();
            for (int i = 1; i <= columnas; i++) {
                modelo_pp.addColumn(md.getColumnLabel(i));
            }
            
            while(rs.next()){
                Object[] fila = new Object[columnas];
                for (int i = 0; i < columnas; i++) {fila[i]=rs.getObject(i+1);}
                modelo_pp.addRow(fila);
            }
        }catch(SQLException sqlex){
            System.out.println("Problema CargarDatos " + sqlex.getMessage() + "/" + sqlex.getErrorCode() + " PreciosPorcentajes");
        }finally{
            try{
                cn.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        }
        
        return modelo_pp;
    }
    
    public int[] obtenerPreciosPorCategoria(int idCat) {
        Conexion cc = new Conexion();
        Connection cn = cc.getConexion();
        int[] precios = new int[3]; // [0] = precio, [1] = mayoreo, [2] = menudeo

        try {
            CallableStatement proc = cn.prepareCall("CALL GuardarPreciosPorcentaje(?, ?, ?, ?, ?);");
            proc.setInt(1, -2);      // ID -2 para consulta específica
            proc.setInt(2, idCat);   // ID de la categoría
            proc.setInt(3, 0);       // Relleno para parámetros que no se usan aquí
            proc.setInt(4, 0);
            proc.setInt(5, 0);

            ResultSet rs = proc.executeQuery();

            if (rs.next()) {
                precios[0] = rs.getInt("precio");
                precios[1] = rs.getInt("mayoreo");
                precios[2] = rs.getInt("menudeo");
            }
        } catch (SQLException sqlex) {
            System.out.println("Error al obtener precios por categoría: " + sqlex.getMessage());
        } finally {
            try {
                if (cn != null) cn.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }

        return precios; // Devuelve un arreglo con los 3 enteros
    }
    
}
