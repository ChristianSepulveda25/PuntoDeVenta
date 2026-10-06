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
public class EquivalenciaM {
    public int id;
    public int idProdPadre;
    public int idProdHijo;
    public double cantidad;
    public int estatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProdPadre() {
        return idProdPadre;
    }

    public void setIdProdPadre(int idProdPadre) {
        this.idProdPadre = idProdPadre;
    }

    public int getIdProdHijo() {
        return idProdHijo;
    }

    public void setIdProdHijo(int idProdHijo) {
        this.idProdHijo = idProdHijo;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }
    
    public boolean GuardarEquivalencia(){
       
       Conexion cc = new Conexion();
       java.sql.Connection cn = cc.getConexion();
       
       try {
            CallableStatement proc = cn.prepareCall("CALL GuardarEquivalencia(?,?,?,?,?)");
            proc.setInt(1, getId());
            proc.setInt(2, getIdProdPadre());
            proc.setInt(3, getIdProdHijo());
            proc.setDouble(4, getCantidad());
            proc.setInt(5, getEstatus());
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
    
    public int ValidarExistenciaProducto(String codigo) {
        Conexion cc = new Conexion();
        java.sql.Connection cn = cc.getConexion();

        try {
            // Preparar el procedimiento almacenado con los parámetros
            CallableStatement proc = cn.prepareCall("{CALL ValidarExistenciaProducto(?, ?)}");

            // Establecer los parámetros de entrada
            proc.setString(1, codigo);

            // Registrar el parámetro de salida
            proc.registerOutParameter(2, java.sql.Types.INTEGER);

            // Ejecutar el procedimiento
            proc.execute();

            // Obtener el valor de salida del procedimiento (índice 2)
            int resultado = proc.getInt(2);  // Cambiar de 1 a 2

            // Retornar el valor obtenido del procedimiento almacenado
            return resultado;

        } catch (SQLException e) {
            // Manejo de excepciones
            System.out.println(e);
            return -1;  // Retornar -1 en caso de error, o puedes retornar otro valor que indique fallo
        } finally {
            try {
                // Cerrar la conexión
                cn.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }
    
    public DefaultTableModel CargarEquivalencias(){
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
            CallableStatement proc = cn.prepareCall("CALL GuardarEquivalencia(?,?,?,?,?);");
            proc.setInt(1, getId());
            proc.setInt(2, getIdProdPadre());
            proc.setInt(3, getIdProdHijo());
            proc.setDouble(4, getCantidad());
            proc.setInt(5, getEstatus());
            
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
            System.out.println("Problema CargarDatos " + sqlex.getMessage() + "/" + sqlex.getErrorCode() + " Equivalencias");
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
