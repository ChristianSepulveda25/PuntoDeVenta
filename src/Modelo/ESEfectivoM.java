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
import java.sql.Types;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Christian Sepulveda
 */
public class ESEfectivoM {
    public int id_ese = 0;
    public String con_ese = null;
    public Double can_ese = 0.0;
    public int tip_ese = 0;
    public String fec_ese = null;
    public int sta_ese = 0;
    public int id_pro = 0;

    public int getId_ese() {
        return id_ese;
    }

    public void setId_ese(int id_ese) {
        this.id_ese = id_ese;
    }

    public String getCon_ese() {
        return con_ese;
    }

    public void setCon_ese(String con_ese) {
        this.con_ese = con_ese;
    }

    public Double getCan_ese() {
        return can_ese;
    }

    public void setCan_ese(Double can_ese) {
        this.can_ese = can_ese;
    }

    public int getTip_ese() {
        return tip_ese;
    }

    public void setTip_ese(int tip_ese) {
        this.tip_ese = tip_ese;
    }

    public String getFec_ese() {
        return fec_ese;
    }

    public void setFec_ese(String fec_ese) {
        this.fec_ese = fec_ese;
    }

    public int getSta_ese() {
        return sta_ese;
    }

    public void setSta_ese(int sta_ese) {
        this.sta_ese = sta_ese;
    }

    public int getId_pro() {
        return id_pro;
    }

    public void setId_pro(int id_pro) {
        this.id_pro = id_pro;
    }
    
    public boolean GuardarESEfectivo(){
       
       Conexion cc = new Conexion();
       java.sql.Connection cn = cc.getConexion();
       
       try {
            CallableStatement proc = cn.prepareCall("CALL GuardarESEfectivo(?,?,?,?,?,?,?,?)");
            proc.setInt(1, getId_ese());
            proc.setString(2, getCon_ese());
            proc.setDouble(3, getCan_ese());
            proc.setInt(4, getTip_ese());
            proc.setString(5, getFec_ese()); 
            proc.setInt(6, getSta_ese()); 
            proc.setInt(7, getId_pro()); 
            proc.setString(8, GlobalVaribales.getInstance().usuarioLogueado); 
            
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
    
    public DefaultTableModel CargarESEfectivo(){
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
            CallableStatement proc = cn.prepareCall("CALL GuardarESEfectivo(?,?,?,?,?,?,?,?)");
            proc.setInt(1, getId_ese());
            proc.setString(2, getCon_ese());
            proc.setDouble(3, getCan_ese());
            proc.setInt(4, getTip_ese());
            proc.setString(5, getFec_ese());
            proc.setInt(6, getSta_ese());
            proc.setInt(7, getId_pro());
            proc.setString(8, GlobalVaribales.getInstance().usuarioLogueado); 
            
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
            System.out.println("Problema CargarDatos " + sqlex.getMessage() + "/" + sqlex.getErrorCode() + " ESEfectivo");
        }finally{
            try{
                cn.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        }
        
        return modelo_ese;
    }
    
}
