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
import java.sql.Types;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ChristianSepulveda
 */
public class TotalesPorFechaM {
    public int id = 0;
    public String fechaDe = null;
    public String fechaAl = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFechaDe() {
        return fechaDe;
    }

    public void setFechaDe(String fechaDe) {
        this.fechaDe = fechaDe;
    }

    public String getFechaAl() {
        return fechaAl;
    }

    public void setFechaAl(String fechaAl) {
        this.fechaAl = fechaAl;
    }
    
    public DefaultTableModel CargarTotales(){
        Conexion cc = new Conexion();
        Connection cn = cc.getConexion();  
        DefaultTableModel modelo_os = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        }; 
        
        try{
            
            Statement smt = cn.createStatement();
            ResultSet rs = null;
            
            CallableStatement proc = cn.prepareCall("CALL ConsultarTotalesVentas(?,?,?)");
            proc.setInt(1, getId());
            proc.setString(2, getFechaDe());
            proc.setString(3, getFechaAl());
            
            rs = proc.executeQuery();
            ResultSetMetaData md = rs.getMetaData();
            int columnas= md.getColumnCount();
            for (int i = 1; i <= columnas; i++) {
                modelo_os.addColumn(md.getColumnLabel(i));
            }
            
            while(rs.next()){
                Object[] fila = new Object[columnas];
                for (int i = 0; i < columnas; i++) {fila[i]=rs.getObject(i+1);}
                modelo_os.addRow(fila);
            }
        }catch(SQLException sqlex){
            System.out.println("Problema CargarDatos " + sqlex.getMessage() + "/" + sqlex.getErrorCode() + " Cargar Totales");
        }finally{
            try{
                cn.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        }
        
        return modelo_os;
    }
    
}
