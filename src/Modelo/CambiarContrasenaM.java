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
public class CambiarContrasenaM {
    public int id = 0;
    public String nombre = null;
    public String user = null;
    public String pass = null;
    public int tipo = 0;
    public int estatus = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }
    
    public boolean ActualizarContrasena(){
       
       Conexion cc = new Conexion();
       java.sql.Connection cn = cc.getConexion();
       
       try {
            CallableStatement proc = cn.prepareCall("CALL CambiarContrasena(?,?,?,?,?,?);");
            proc.setInt(1, getId());
            proc.setString(2, getNombre());
            proc.setString(3, getUser());
            proc.setString(4, getPass());
            proc.setInt(5, getTipo());
            proc.setInt(6, getEstatus());
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
    
    public DefaultTableModel CargarUsuarios(){
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
            CallableStatement proc = cn.prepareCall("CALL CambiarContrasena(?,?,?,?,?,?);");
            proc.setInt(1, getId());
            proc.setString(2, getNombre());
            proc.setString(3, getUser());
            proc.setString(4, getPass());
            proc.setInt(5, getTipo());
            proc.setInt(6, getEstatus());
            
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
