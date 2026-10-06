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
public class ProveedoresM {
    public int id_pro = 0;
    public String nom_pro = null;
    public String cal_pro = null;
    public int no_pro = 0;
    public String let_pro = null;
    public int int_pro = 0;
    public String col_pro = null;
    public String ent_pro = null;
    public String cp_pro = null;
    public String tel_pro = null;
    public String con_pro = null;
    public String tel_con_pro = null;
    public int sta_pro = 0;

    public int getId_pro() {
        return id_pro;
    }

    public void setId_pro(int id_pro) {
        this.id_pro = id_pro;
    }

    public String getNom_pro() {
        return nom_pro;
    }

    public void setNom_pro(String nom_pro) {
        this.nom_pro = nom_pro;
    }

    public String getCal_pro() {
        return cal_pro;
    }

    public void setCal_pro(String cal_pro) {
        this.cal_pro = cal_pro;
    }

    public int getNo_pro() {
        return no_pro;
    }

    public void setNo_pro(int no_pro) {
        this.no_pro = no_pro;
    }

    public String getLet_pro() {
        return let_pro;
    }

    public void setLet_pro(String let_pro) {
        this.let_pro = let_pro;
    }

    public int getInt_pro() {
        return int_pro;
    }

    public void setInt_pro(int int_pro) {
        this.int_pro = int_pro;
    }

    public String getCol_pro() {
        return col_pro;
    }

    public void setCol_pro(String col_pro) {
        this.col_pro = col_pro;
    }

    public String getEnt_pro() {
        return ent_pro;
    }

    public void setEnt_pro(String ent_pro) {
        this.ent_pro = ent_pro;
    }

    public String getCp_pro() {
        return cp_pro;
    }

    public void setCp_pro(String cp_pro) {
        this.cp_pro = cp_pro;
    }

    public String getTel_pro() {
        return tel_pro;
    }

    public void setTel_pro(String tel_pro) {
        this.tel_pro = tel_pro;
    }

    public String getCon_pro() {
        return con_pro;
    }

    public void setCon_pro(String con_pro) {
        this.con_pro = con_pro;
    }

    public String getTel_con_pro() {
        return tel_con_pro;
    }

    public void setTel_con_pro(String tel_con_pro) {
        this.tel_con_pro = tel_con_pro;
    }

    public int getSta_pro() {
        return sta_pro;
    }

    public void setSta_pro(int sta_pro) {
        this.sta_pro = sta_pro;
    }
    
    public boolean GuardarProveedor(){
       
       Conexion cc = new Conexion();
       java.sql.Connection cn = cc.getConexion();
       
       try {
            CallableStatement proc = cn.prepareCall("CALL GuardarProveedor(?,?,?,?,?,?,?,?,?,?,?,?,?)");
            proc.setInt(1, getId_pro());
            proc.setString(2, getNom_pro());
            proc.setString(3, getCal_pro());
            proc.setInt(4, getNo_pro());
            proc.setString(5, getLet_pro());
            proc.setInt(6, getInt_pro());
            proc.setString(7, getCol_pro());
            proc.setString(8, getEnt_pro());
            proc.setString(9, getCp_pro());
            proc.setString(10, getTel_pro());
            proc.setString(11, getCon_pro());
            proc.setString(12, getTel_con_pro());
            proc.setInt(13, getSta_pro());
            
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
    
    public DefaultTableModel CargarProveedor(){
        Conexion cc = new Conexion();
        Connection cn = cc.getConexion();  
        DefaultTableModel modelo_pro = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        }; 
        
        try{
            
            Statement smt = cn.createStatement();
            ResultSet rs = null;
            CallableStatement proc = cn.prepareCall("CALL GuardarProveedor(?,?,?,?,?,?,?,?,?,?,?,?,?)");
            proc.setInt(1, getId_pro());
            proc.setString(2, getNom_pro());
            proc.setString(3, getCal_pro());
            proc.setInt(4, getNo_pro());
            proc.setString(5, getLet_pro());
            proc.setInt(6, getInt_pro());
            proc.setString(7, getCol_pro());
            proc.setString(8, getEnt_pro());
            proc.setString(9, getCp_pro());
            proc.setString(10, getTel_pro());
            proc.setString(11, getCon_pro());
            proc.setString(12, getTel_con_pro());
            proc.setInt(13, getSta_pro());
            
            rs = proc.executeQuery();
            ResultSetMetaData md = rs.getMetaData();
            int columnas= md.getColumnCount();
            for (int i = 1; i <= columnas; i++) {
                modelo_pro.addColumn(md.getColumnLabel(i));
            }
            
            while(rs.next()){
                Object[] fila = new Object[columnas];
                for (int i = 0; i < columnas; i++) {fila[i]=rs.getObject(i+1);}
                modelo_pro.addRow(fila);
            }
        }catch(SQLException sqlex){
            System.out.println("Problema CargarDatos " + sqlex.getMessage() + "/" + sqlex.getErrorCode() + " Proveedor");
        }finally{
            try{
                cn.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        }
        
        return modelo_pro;
    }
    
}
