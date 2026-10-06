/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import com.mysql.jdbc.Connection;
import java.sql.CallableStatement;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Christian Sepulveda
 */
public class CorteCajaM {
    public int id_cc;
    public double cantidad;
    public String fecha;
    public int estatus;
    
    public int _ID = 0;
    public String _FECHADE = null;
    public String _FECHAAL = null;
    public String _CAN_INICIAL = null;
    public String _CAN_CIERRE = null;
    public String _VENTAS_REALIZADAS = null;
    public String _VENTAS_CANCELADAS = null;
    public String _MOV_ENTRADAS = null;
    public String _MOV_SALIDAS = null;
    public String _EST = null;

    public int getId_cc() {
        return id_cc;
    }

    public void setId_cc(int id_cc) {
        this.id_cc = id_cc;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public int getID() {
        return _ID;
    }

    public void setID(int _ID) {
        this._ID = _ID;
    }

    public String getFECHADE() {
        return _FECHADE;
    }

    public void setFECHADE(String _FECHADE) {
        this._FECHADE = _FECHADE;
    }

    public String getFECHAAL() {
        return _FECHAAL;
    }

    public void setFECHAAL(String _FECHAAL) {
        this._FECHAAL = _FECHAAL;
    }

    public String getCAN_INICIAL() {
        return _CAN_INICIAL;
    }

    public void setCAN_INICIAL(String _CAN_INICIAL) {
        this._CAN_INICIAL = _CAN_INICIAL;
    }

    public String getCAN_CIERRE() {
        return _CAN_CIERRE;
    }

    public void setCAN_CIERRE(String _CAN_CIERRE) {
        this._CAN_CIERRE = _CAN_CIERRE;
    }

    public String getVENTAS_REALIZADAS() {
        return _VENTAS_REALIZADAS;
    }

    public void setVENTAS_REALIZADAS(String _VENTAS_REALIZADAS) {
        this._VENTAS_REALIZADAS = _VENTAS_REALIZADAS;
    }

    public String getVENTAS_CANCELADAS() {
        return _VENTAS_CANCELADAS;
    }

    public void setVENTAS_CANCELADAS(String _VENTAS_CANCELADAS) {
        this._VENTAS_CANCELADAS = _VENTAS_CANCELADAS;
    }

    public String getMOV_ENTRADAS() {
        return _MOV_ENTRADAS;
    }

    public void setMOV_ENTRADAS(String _MOV_ENTRADAS) {
        this._MOV_ENTRADAS = _MOV_ENTRADAS;
    }

    public String getMOV_SALIDAS() {
        return _MOV_SALIDAS;
    }

    public void setMOV_SALIDAS(String _MOV_SALIDAS) {
        this._MOV_SALIDAS = _MOV_SALIDAS;
    }

    public String getEST() {
        return _EST;
    }

    public void setEST(String _EST) {
        this._EST = _EST;
    }
    
    
    
    public boolean GuardarCorteCaja(){
       
       Conexion cc = new Conexion();
       java.sql.Connection cn = cc.getConexion();
       
       try {
            CallableStatement proc = cn.prepareCall("CALL GuardarCorteCaja(?,?,?,?,?,?,?,?,?)");
            proc.setDouble(1, getCantidad());
            proc.setString(2, getFecha());
            proc.setInt(3, getEstatus());
            proc.setInt(4, getId_cc());
            proc.setString(5, getVENTAS_REALIZADAS());
            proc.setString(6, getVENTAS_CANCELADAS());
            proc.setString(7, getMOV_ENTRADAS());
            proc.setString(8, getMOV_SALIDAS());
            proc.setString(9, getEST());
            //System.out.println(proc);
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
    
    public boolean GenerarCorteCaja(){
       
       Conexion cc = new Conexion();
       java.sql.Connection cn = cc.getConexion();
       
       try {
            CallableStatement proc = cn.prepareCall("CALL ConsultarCorteCaja(?,?,?,?,?,?,?,?,?)");
            proc.setInt(1, getID());
            proc.setString(2, getFECHADE());
            proc.setString(3, getFECHAAL());
            proc.registerOutParameter(4, Types.VARCHAR);
            proc.registerOutParameter(5, Types.VARCHAR);
            proc.registerOutParameter(6, Types.VARCHAR);
            proc.registerOutParameter(7, Types.VARCHAR);
            proc.registerOutParameter(8, Types.VARCHAR);
            proc.registerOutParameter(9, Types.VARCHAR);
            proc.execute();
            
            String canInicial = proc.getString("_CAN_INICIAL");
            String canCierre = proc.getString("_CAN_CIERRE");
            String ventasRealizadas = proc.getString("_VENTAS_REALIZADAS");
            String ventasCanceladas = proc.getString("_VENTAS_CANCELADAS");
            String movEntradas = proc.getString("_MOV_ENTRADAS");
            String movSalidas = proc.getString("_MOV_SALIDAS");
            
            /*System.out.println("Cantidad Inicial: " + canInicial);
            System.out.println("Cantidad Cierre: " + canCierre);
            System.out.println("Ventas Realizadas: " + ventasRealizadas);
            System.out.println("Ventas Canceladas: " + ventasCanceladas);
            System.out.println("Movimientos Entradas: " + movEntradas);
            System.out.println("Movimientos Salidas: " + movSalidas);*/
            
            setCAN_INICIAL(""+canInicial);
            setCAN_CIERRE(""+canCierre);
            setVENTAS_REALIZADAS(""+ventasRealizadas);
            setVENTAS_CANCELADAS(""+ventasCanceladas);
            setMOV_ENTRADAS(""+movEntradas);
            setMOV_SALIDAS(""+movSalidas);
            
            /*System.out.println("Cantidad Inicial: " + getCAN_INICIAL());
            System.out.println("Cantidad Cierre: " + getCAN_CIERRE());
            System.out.println("Ventas Realizadas: " + getVENTAS_REALIZADAS());
            System.out.println("Ventas Canceladas: " + getVENTAS_CANCELADAS());
            System.out.println("Movimientos Entradas: " + getMOV_ENTRADAS());
            System.out.println("Movimientos Salidas: " + getMOV_SALIDAS());*/
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
