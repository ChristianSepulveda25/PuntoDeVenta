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
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ChristianSepulveda
 */
public class CotizacionM {
    public int param = 0;
    public int id = 0;
    public String codigo = null;
    public String nombre = null;
    public double precio = 0.0;
    public double cantidad = 0.0;
    public double subtotal = 0.0;
    public int consecutivo = 0;
    public String nombreCotizacion = null;
    public String fecha = null;

    public int getParam() {
        return param;
    }

    public void setParam(int param) {
        this.param = param;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public int getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(int consecutivo) {
        this.consecutivo = consecutivo;
    }

    public String getNombreCotizacion() {
        return nombreCotizacion;
    }

    public void setNombreCotizacion(String nombreCotizacion) {
        this.nombreCotizacion = nombreCotizacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    public boolean GuardarCotizacion(){
       
       Conexion cc = new Conexion();
       java.sql.Connection cn = cc.getConexion();
       
       try {
            CallableStatement proc = cn.prepareCall("CALL GuardarCotizacion(?,?,?,?,?,?,?,?,?,?)");
            proc.setInt(1, getParam());
            proc.setInt(2, getId());
            proc.setString(3, getCodigo());
            proc.setString(4, getNombre());
            proc.setDouble(5, getPrecio());
            proc.setDouble(6, getCantidad());
            proc.setDouble(7, getSubtotal());
            proc.setInt(8, getConsecutivo());
            proc.setString(9, getNombreCotizacion());
            proc.setString(10, getFecha());
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
    
    public DefaultTableModel CargarCotizacion() throws SQLException {
        Conexion cc = new Conexion();
        Connection cn = cc.getConexion();  
        DefaultTableModel modelocp = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Consulta SQL con parámetros
        String sql = "{CALL GuardarCotizacion(?,?,?,?,?,?,?,?,?,?)}";

        try {
            // Crear PreparedStatement
            CallableStatement stmt = cn.prepareCall(sql);

            // Establecer los valores de los parámetros (índices 1 a 15)
            stmt.setInt(1, getParam());
            stmt.setInt(2, getId());
            stmt.setString(3, getCodigo());
            stmt.setString(4, getNombre());
            stmt.setDouble(5, getPrecio());
            stmt.setDouble(6, getCantidad());
            stmt.setDouble(7, getSubtotal());
            stmt.setInt(8, getConsecutivo());
            stmt.setString(9, getNombreCotizacion());
            stmt.setString(10, getFecha());

            // Ejecutar la consulta
            ResultSet rs = stmt.executeQuery();

            // Obtener los metadatos del resultado
            ResultSetMetaData md = rs.getMetaData();
            int columnas = md.getColumnCount();

            // Añadir las columnas al modelo de la tabla
            for (int i = 1; i <= columnas; i++) {
                modelocp.addColumn(md.getColumnLabel(i));
            }

            // Añadir filas al modelo de la tabla
            while (rs.next()) {
                Object[] fila = new Object[columnas];
                for (int i = 0; i < columnas; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                modelocp.addRow(fila);
            }
        } catch (SQLException sqlex) {
            System.out.println("Problema CargarDatos " + sqlex.getMessage() + "/" + sqlex.getErrorCode() + " Productos");
        } finally {
            cn.close();
        }

        return modelocp;
    }
}
