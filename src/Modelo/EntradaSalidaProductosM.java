/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.CallableStatement;
import java.sql.SQLException;

/**
 *
 * @author Christian Sepulveda
 */
public class EntradaSalidaProductosM {
    public int id = 0;
    public String con = null;
    public int can = 0;
    public int tip = 0;
    public String fec = null;
    public int est = 0;
    public int pro = 0;
    public double tot = 0;
    public double cant_ant = 0;
    public int id_almacen = 0;
    public int id_almacen_destino = 0;
    public int id_inventario = 0;

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

    public int getPro() {
        return pro;
    }

    public void setPro(int pro) {
        this.pro = pro;
    }

    public double getTot() {
        return tot;
    }

    public void setTot(double tot) {
        this.tot = tot;
    }

    public double getCant_ant() {
        return cant_ant;
    }

    public void setCant_ant(double cant_ant) {
        this.cant_ant = cant_ant;
    }

    public int getId_almacen() {
        return id_almacen;
    }

    public void setId_almacen(int id_almacen) {
        this.id_almacen = id_almacen;
    }

    public int getId_almacen_destino() {
        return id_almacen_destino;
    }

    public void setId_almacen_destino(int id_almacen_destino) {
        this.id_almacen_destino = id_almacen_destino;
    }

    public int getId_inventario() {
        return id_inventario;
    }

    public void setId_inventario(int id_inventario) {
        this.id_inventario = id_inventario;
    }
    
    public boolean GuardarESProducto(){
       
       Conexion cc = new Conexion();
       java.sql.Connection cn = cc.getConexion();
       
       try {
            CallableStatement proc = cn.prepareCall("CALL GuardarESProducto(?,?,?,?,?,?,?,?,?,?)");
            proc.setInt(1, getId());
            proc.setString(2, getCon());
            proc.setInt(3, getCan());
            proc.setInt(4, getTip());
            proc.setString(5, getFec());
            proc.setInt(6, getEst());
            proc.setInt(7, getPro());
            proc.setDouble(8, getTot());
            proc.setDouble(9, getCant_ant());
            proc.setInt(10, getId_almacen());
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
}
