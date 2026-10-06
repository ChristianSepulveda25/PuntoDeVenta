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
public class MovimientosAlmacenM {
    public int id = 0;
    public int id_producto = 0;
    public int id_almacen = 0;
    public int id_inventario = 0;
    public int id_almacen_destino = 0;
    public double cantidad = 0;
    public double cantidad_inventario = 0;
    public String fecha = null;
    public String fechaAl = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getId_almacen() {
        return id_almacen;
    }

    public void setId_almacen(int id_almacen) {
        this.id_almacen = id_almacen;
    }

    public int getId_inventario() {
        return id_inventario;
    }

    public void setId_inventario(int id_inventario) {
        this.id_inventario = id_inventario;
    }

    public int getId_almacen_destino() {
        return id_almacen_destino;
    }

    public void setId_almacen_destino(int id_almacen_destino) {
        this.id_almacen_destino = id_almacen_destino;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getCantidad_inventario() {
        return cantidad_inventario;
    }

    public void setCantidad_inventario(double cantidad_inventario) {
        this.cantidad_inventario = cantidad_inventario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFechaAl() {
        return fechaAl;
    }

    public void setFechaAl(String fechaAl) {
        this.fechaAl = fechaAl;
    }
    
    public boolean GuardarMovimientosProductos(){
       
       Conexion cc = new Conexion();
       java.sql.Connection cn = cc.getConexion();
       
       try {
            CallableStatement proc = cn.prepareCall("CALL GuardarMovimientosAlmacen(?,?,?,?,?,?,?,?,?)");
            proc.setInt(1, getId());
            proc.setInt(2, getId_producto());
            proc.setInt(3, getId_almacen());
            proc.setInt(4, getId_inventario());
            proc.setInt(5, getId_almacen_destino());
            proc.setDouble(6, getCantidad());
            proc.setDouble(7, getCantidad_inventario());
            proc.setString(8, getFecha());
            proc.setString(9, getFechaAl());
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
    
    public DefaultTableModel CargarMovimientosProductos(){
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
            CallableStatement proc = cn.prepareCall("CALL GuardarMovimientosAlmacen(?,?,?,?,?,?,?,?,?);");
            proc.setInt(1, getId());
            proc.setInt(2, getId_producto());
            proc.setInt(3, getId_almacen());
            proc.setInt(4, getId_inventario());
            proc.setInt(5, getId_almacen_destino());
            proc.setDouble(6, getCantidad());
            proc.setDouble(7, getCantidad_inventario());
            proc.setString(8, getFecha());
            proc.setString(9, getFechaAl());
            
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
