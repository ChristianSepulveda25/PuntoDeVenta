/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.CallableStatement;
import java.sql.SQLException;

/**
 *
 * @author ChristianSepulveda
 */
public class EntradasSalidasPExpressM {
    
    public int param = 0;
    public int tipo = 0;
    public int almacen = 0;
    public String codigo = null;
    public double cantidad = 0;
    public String usuarioRegistra = null;

    public int getAlmacen() {
        return almacen;
    }

    public void setAlmacen(int almacen) {
        this.almacen = almacen;
    }

    public int getParam() {
        return param;
    }

    public void setParam(int param) {
        this.param = param;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
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
            CallableStatement proc = cn.prepareCall("CALL GuardarESPExpress(?,?,?,?,?,?)");
            proc.setInt(1, getParam());
            proc.setInt(2, getTipo());
            proc.setInt(3, getAlmacen());
            proc.setString(4, getCodigo());
            proc.setDouble(5, getCantidad());
            proc.setString(6, getUsuarioRegistra());
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
    
}
