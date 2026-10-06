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
public class HistorialCorteCajaM {
    public int id_cc = 0;
    public double can_inicio = 0;
    public String fec_inicio = null;
    public double can_cierre = 0;
    public String fec_cierre = null;
    public int usu_inicio = 0;
    public int usu_cierre = 0;
    public double ventas_realizadas = 0;
    public double ventas_canceladas = 0;
    public double mov_entradas = 0;
    public double mov_salidas = 0;
    public String estatus = null;

    public int getId_cc() {
        return id_cc;
    }

    public void setId_cc(int id_cc) {
        this.id_cc = id_cc;
    }

    public double getCan_inicio() {
        return can_inicio;
    }

    public void setCan_inicio(double can_inicio) {
        this.can_inicio = can_inicio;
    }

    public String getFec_inicio() {
        return fec_inicio;
    }

    public void setFec_inicio(String fec_inicio) {
        this.fec_inicio = fec_inicio;
    }

    public double getCan_cierre() {
        return can_cierre;
    }

    public void setCan_cierre(double can_cierre) {
        this.can_cierre = can_cierre;
    }

    public String getFec_cierre() {
        return fec_cierre;
    }

    public void setFec_cierre(String fec_cierre) {
        this.fec_cierre = fec_cierre;
    }

    public int getUsu_inicio() {
        return usu_inicio;
    }

    public void setUsu_inicio(int usu_inicio) {
        this.usu_inicio = usu_inicio;
    }

    public int getUsu_cierre() {
        return usu_cierre;
    }

    public void setUsu_cierre(int usu_cierre) {
        this.usu_cierre = usu_cierre;
    }

    public double getVentas_realizadas() {
        return ventas_realizadas;
    }

    public void setVentas_realizadas(double ventas_realizadas) {
        this.ventas_realizadas = ventas_realizadas;
    }

    public double getVentas_canceladas() {
        return ventas_canceladas;
    }

    public void setVentas_canceladas(double ventas_canceladas) {
        this.ventas_canceladas = ventas_canceladas;
    }

    public double getMov_entradas() {
        return mov_entradas;
    }

    public void setMov_entradas(double mov_entradas) {
        this.mov_entradas = mov_entradas;
    }

    public double getMov_salidas() {
        return mov_salidas;
    }

    public void setMov_salidas(double mov_salidas) {
        this.mov_salidas = mov_salidas;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
    
    public DefaultTableModel CargarHistorialCortes(){
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
            rs = smt.executeQuery("CALL ConsultarHistorialCorteCaja(" + getId_cc()+ ",'"+getFec_inicio()+"','"+getFec_cierre()+"');");
            
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
            System.out.println("Problema CargarDatos " + sqlex.getMessage() + " Historial Corte Caja");
        }finally{
            try{
                cn.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        }
        
        return modelocp;
    }
    
}
