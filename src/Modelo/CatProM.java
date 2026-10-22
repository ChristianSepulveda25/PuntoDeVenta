/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 * @author Christian Sepulveda
 */
public class CatProM {
    public int id = 0;
    public String con = null;
    public String nom = null;
    public int est = 0;
    public int porcentaje = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCon() {
        return con;
    }

    public void setCon(String con) {
        this.con = con;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getEst() {
        return est;
    }

    public void setEst(int est) {
        this.est = est;
    }

    public int getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
    }
    
    public boolean GuardarCatPro(){
       
       Conexion cc = new Conexion();
       java.sql.Connection cn = cc.getConexion();
       
       try {
            CallableStatement proc = cn.prepareCall("CALL GuardarCatPro(?,?,?,?,?)");
            proc.setInt(1, getId());
            proc.setString(2, getCon());
            proc.setString(3, getNom());
            proc.setInt(4, getEst());
            proc.setInt(5, getPorcentaje());
            
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
    
    public DefaultTableModel CargarCatPro(){
        Conexion cc = new Conexion();
        Connection cn = cc.getConexion();  
        DefaultTableModel modelocp = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        }; 
        
        try{
            
            Statement smt = cn.createStatement();
            ResultSet rs = null;
            rs = smt.executeQuery("CALL GuardarCatPro(" 
                + getId() + "," 
                + getCon() + "," 
                + getNom() + "," 
                + getEst() + "," 
                + getPorcentaje()+ ");"
            );
            
            ResultSetMetaData md = rs.getMetaData();
            int columnas= md.getColumnCount();
            for (int i = 1; i <= columnas; i++) {
                modelocp.addColumn(md.getColumnLabel(i));
            }
            
            while(rs.next()){
                Object[] fila = new Object[columnas];
                for (int i = 0; i < columnas; i++) {fila[i]=rs.getObject(i+1);}
                modelocp.addRow(fila);
            }
        }catch(SQLException sqlex){
            System.out.println("Problema CargarDatos " + sqlex.getMessage() + " CatPro");
        }finally{
            try{
                cn.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        }
        
        return modelocp;
    }
    
    public java.util.Map<Integer, Double> obtenerDescuentosDepartamentos() {
        java.util.Map<Integer, Double> descuentosMap = new java.util.HashMap<>();
        Conexion cc = new Conexion();
        Connection cn = cc.getConexion();

        try {
            // Usamos tu procedimiento almacenado con un ID de control (ej. -1 para consultar)
            CallableStatement proc = cn.prepareCall("CALL GuardarCatPro(?,?,?,?,?)");
            proc.setInt(1, -6);      // ID de control para la consulta (ej. -1)
            proc.setString(2, "");   // Parámetros de relleno que no se usan en esta consulta
            proc.setString(3, "");
            proc.setInt(4, 0);
            proc.setInt(5, 0);

            ResultSet rs = proc.executeQuery();

            while (rs.next()) {
                // Suponiendo que tu consulta regresa el ID del departamento y su porcentaje de descuento
                int idDepartamento = rs.getInt("id_catpro"); // O el nombre de tu columna ID
                double porcentajeDescuento = rs.getDouble("porcentaje_desc"); // O la columna de descuento

                descuentosMap.put(idDepartamento, porcentajeDescuento);
            }
        } catch (SQLException sqlex) {
            System.out.println("Error al obtener descuentos de departamentos: " + sqlex.getMessage());
        } finally {
            try {
                if (cn != null) cn.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }

        return descuentosMap;
    }
    
}
