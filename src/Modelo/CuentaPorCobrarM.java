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
public class CuentaPorCobrarM {
    public int ID = 0;
    public String FOLIO_VENTA = null;
    public int ID_CLIENTE = 0;
    public double TOTAL = 0;
    public int ESTATUS = 0;
    public String FECHA = null;
    public String FECHADE = null;
    public String FECHAAL = null;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFOLIO_VENTA() {
        return FOLIO_VENTA;
    }

    public void setFOLIO_VENTA(String FOLIO_VENTA) {
        this.FOLIO_VENTA = FOLIO_VENTA;
    }

    public int getID_CLIENTE() {
        return ID_CLIENTE;
    }

    public void setID_CLIENTE(int ID_CLIENTE) {
        this.ID_CLIENTE = ID_CLIENTE;
    }

    public double getTOTAL() {
        return TOTAL;
    }

    public void setTOTAL(double TOTAL) {
        this.TOTAL = TOTAL;
    }

    public int getESTATUS() {
        return ESTATUS;
    }

    public void setESTATUS(int ESTATUS) {
        this.ESTATUS = ESTATUS;
    }

    public String getFECHA() {
        return FECHA;
    }

    public void setFECHA(String FECHA) {
        this.FECHA = FECHA;
    }

    public String getFECHADE() {
        return FECHADE;
    }

    public void setFECHADE(String FECHADE) {
        this.FECHADE = FECHADE;
    }

    public String getFECHAAL() {
        return FECHAAL;
    }

    public void setFECHAAL(String FECHAAL) {
        this.FECHAAL = FECHAAL;
    }
    
    
    
    public DefaultTableModel CargarCuentasPorCobrar(){
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
            CallableStatement proc = cn.prepareCall("CALL GuardarCuentasPorCobrar(?,?,?,?,?,?,?,?);");
            proc.setInt(1, getID());
            proc.setString(2, getFOLIO_VENTA());
            proc.setInt(3, getID_CLIENTE());
            proc.setDouble(4, getTOTAL());
            proc.setInt(5, getESTATUS());
            proc.setString(6, getFECHA());
            proc.setString(7, getFECHADE());
            proc.setString(8, getFECHAAL());
            
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
            System.out.println("Problema CargarDatos " + sqlex.getMessage() + "/" + sqlex.getErrorCode() + " Clientes");
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
