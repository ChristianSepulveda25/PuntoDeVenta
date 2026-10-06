/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

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
public class InventarioStockM {
    public int id_inv = 0;
    public int id_prod = 0;
    public int id_alm = 0;
    public int cantidad = 0;
    public String buscar = "";
    public int id = 0;
    public int estatus = 0;
    
    public int id_cat = 0;
    public String nombreBuscar = null;

    public int getId_inv() {
        return id_inv;
    }

    public void setId_inv(int id_inv) {
        this.id_inv = id_inv;
    }

    public int getId_prod() {
        return id_prod;
    }

    public void setId_prod(int id_prod) {
        this.id_prod = id_prod;
    }

    public int getId_alm() {
        return id_alm;
    }

    public void setId_alm(int id_alm) {
        this.id_alm = id_alm;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getBuscar() {
        return buscar;
    }

    public void setBuscar(String buscar) {
        this.buscar = buscar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public int getId_cat() {
        return id_cat;
    }

    public void setId_cat(int id_cat) {
        this.id_cat = id_cat;
    }

    public String getNombreBuscar() {
        return nombreBuscar;
    }

    public void setNombreBuscar(String nombreBuscar) {
        this.nombreBuscar = nombreBuscar;
    }
    
    public DefaultTableModel CargarInventario(){
        Conexion cc = new Conexion();
        Connection cn = cc.getConexion();  
        DefaultTableModel modelohormas = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        try{
            
            Statement smt = cn.createStatement();
            ResultSet rs = null;
            //System.out.println("CALL GuardarInventario("+getId_inv()+","+getId_prod()+","+getId_alm()+","+getCantidad()+","+getBuscar()+")");
            rs = smt.executeQuery("CALL GuardarInventario("+getId_inv()+","+getId_prod()+","+getId_alm()+","+getCantidad()+","+getBuscar()+")");
            
            ResultSetMetaData md = rs.getMetaData();
            int columnas= md.getColumnCount();
            for (int i = 1; i <= columnas; i++) {
                modelohormas.addColumn(md.getColumnLabel(i));
            }
            
            while(rs.next()){
                Object[] fila = new Object[columnas];
                for (int i = 0; i < columnas; i++) {fila[i]=rs.getObject(i+1);}
                modelohormas.addRow(fila);
                //   fila=null;
            }
            //return modeloalu;
            //av.tblalumnos.setModel(modeloalu);
            //cn.close();
        }catch(SQLException sqlex){
            System.out.println("Problema CargarDatos " + sqlex.getMessage() + " Inventario");
        }finally{
            try{
                cn.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        }
        
        return modelohormas;
    }
    
    public DefaultTableModel CargarInventarioStock(){
        Conexion cc = new Conexion();
        Connection cn = cc.getConexion();  
        DefaultTableModel modelohormas = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        try{
            
            Statement smt = cn.createStatement();
            ResultSet rs = null;
            rs = smt.executeQuery("CALL ConsultarInventarioStock("+getId()+","+getId_alm()+","+getEstatus()+","+getId_cat()+",'"+getNombreBuscar()+"')");
            
            ResultSetMetaData md = rs.getMetaData();
            int columnas= md.getColumnCount();
            for (int i = 1; i <= columnas; i++) {
                modelohormas.addColumn(md.getColumnLabel(i));
            }
            
            while(rs.next()){
                Object[] fila = new Object[columnas];
                for (int i = 0; i < columnas; i++) {fila[i]=rs.getObject(i+1);}
                modelohormas.addRow(fila);
                //   fila=null;
            }
            //return modeloalu;
            //av.tblalumnos.setModel(modeloalu);
            //cn.close();
        }catch(SQLException sqlex){
            System.out.println("Problema CargarDatos " + sqlex.getMessage() + " Inventario");
        }finally{
            try{
                cn.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        }
        
        return modelohormas;
    }
    
}
