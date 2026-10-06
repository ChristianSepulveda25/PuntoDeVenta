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
public class ESProductoM {
    public int id = 0;
    public String con = null;
    public int can = 0;
    public int tip = 0;
    public String fec = null;
    public int est = 0;
    public String pro = null;
    public int tot = 0;
    public int cant_ant = 0;
    public int id_alm = 0;
    public int id_pro = 0;
    public String usuarioRegistra = null;

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

    public int getCan() {
        return can;
    }

    public void setCan(int can) {
        this.can = can;
    }

    public int getTip() {
        return tip;
    }

    public void setTip(int tip) {
        this.tip = tip;
    }

    public String getFec() {
        return fec;
    }

    public void setFec(String fec) {
        this.fec = fec;
    }

    public int getEst() {
        return est;
    }

    public void setEst(int est) {
        this.est = est;
    }

    public String getPro() {
        return pro;
    }

    public void setPro(String pro) {
        this.pro = pro;
    }

    public int getTot() {
        return tot;
    }

    public void setTot(int tot) {
        this.tot = tot;
    }

    public int getCant_ant() {
        return cant_ant;
    }

    public void setCant_ant(int cant_ant) {
        this.cant_ant = cant_ant;
    }

    public int getId_alm() {
        return id_alm;
    }

    public void setId_alm(int id_alm) {
        this.id_alm = id_alm;
    }

    public int getId_pro() {
        return id_pro;
    }

    public void setId_pro(int id_pro) {
        this.id_pro = id_pro;
    }

    public String getUsuarioRegistra() {
        return usuarioRegistra;
    }

    public void setUsuarioRegistra(String usuarioRegistra) {
        this.usuarioRegistra = usuarioRegistra;
    }
    
    
    
    public boolean GuardarESProducto(){
       
       Conexion cc = new Conexion();
       java.sql.Connection cn = cc.getConexion();
       
       try {
            CallableStatement proc = cn.prepareCall("CALL GuardarESProducto(?,?,?,?,?,?,?,?,?)");
            proc.setInt(1, getId());
            proc.setString(2, getCon());
            proc.setInt(3, getCan());
            proc.setInt(4, getTip());
            proc.setString(5, getFec());
            proc.setInt(6, getEst());
            proc.setString(7, getPro());
            proc.setInt(8, getTot());
            proc.setInt(9, getCant_ant());
            
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
    
    public DefaultTableModel CargarESProducto(){
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
            // System.out.println("CALL GuardarESProducto("+getId()+","+getCon()+","+getCan()+","+getTip()+","+getFec()+","+getEst()+","+getId_pro()+","+getTot()+","+getCant_ant()+","+getId_alm()+");");
            CallableStatement proc = cn.prepareCall("CALL GuardarESProducto(?,?,?,?,?,?,?,?,?,?,?);");
            proc.setInt(1, getId());
            proc.setString(2, getCon());
            proc.setInt(3, getCan());
            proc.setInt(4, getTip());
            proc.setString(5, getFec());
            proc.setInt(6, getEst());
            proc.setInt(7, getId_pro());
            proc.setInt(8, getTot());
            proc.setInt(9, getCant_ant());
            proc.setInt(10, getId_alm());
            proc.setString(11, getUsuarioRegistra());
            
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
    
}
