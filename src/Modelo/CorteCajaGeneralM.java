/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 *
 * @author ChristianSepulveda
 */
public class CorteCajaGeneralM {
    
    public double obtenerTotalDepto(int param, String fecha, int usuario, int depto) throws SQLException {
        CallableStatement callableStatement = null;
        ResultSet rs = null;
        double totalDepto = 0.0;
        Conexion cc = new Conexion();
        Connection cn = cc.getConexion(); 
        
        try {
            // Llamamos al procedimiento almacenado
            String query = "{CALL GenerarCorteCajaUsuario(?, ?, ?, ?, ?, ?, ?)}";
            callableStatement = cn.prepareCall(query);
            
            // Establecemos los parámetros de entrada
            callableStatement.setInt(1, param); // PARAM
            callableStatement.setString(2, fecha); // FECHA
            callableStatement.setInt(3, usuario); // USUARIO
            callableStatement.setInt(4, depto); // DEPTO
            
            // Establecemos el parámetro de salida
            callableStatement.registerOutParameter(5, Types.DOUBLE); // TOT_DEPTO
            callableStatement.registerOutParameter(6, Types.VARCHAR); // TOT_DEPTO
            callableStatement.registerOutParameter(7, Types.VARCHAR); // TOT_DEPTO
            
            // Ejecutamos el procedimiento
            callableStatement.execute();
            
            // Obtenemos el valor del parámetro de salida (TOT_DEPTO)
            totalDepto = callableStatement.getDouble(5);
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al ejecutar el procedimiento almacenado", e);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (callableStatement != null) {
                callableStatement.close();
            }
        }
        
        return totalDepto;
    }
    
    public String obtenerUsuario(int param, int usuario) throws SQLException {
        CallableStatement callableStatement = null;
        String usuarioVenta = null;
        Conexion cc = new Conexion();
        Connection cn = cc.getConexion(); 

        try {
            // Llamamos al procedimiento almacenado
            String query = "{CALL GenerarCorteCajaUsuario(?, ?, ?, ?, ?, ?, ?)}";
            callableStatement = cn.prepareCall(query);

            // Establecemos los parámetros de entrada
            callableStatement.setInt(1, param); // PARAM
            callableStatement.setString(2, null); // FECHA, usar NULL si es necesario
            callableStatement.setInt(3, usuario); // USUARIO
            callableStatement.setInt(4, 0); // DEPTO

            // Establecemos el parámetro de salida (solo uno, por ejemplo, el usuarioVenta)
            callableStatement.registerOutParameter(7, Types.VARCHAR); // usuarioVenta (o el valor que esperas)

            // Ejecutamos el procedimiento
            callableStatement.execute();

            // Obtenemos el valor del parámetro de salida (usuarioVenta)
            usuarioVenta = callableStatement.getString(7);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al ejecutar el procedimiento almacenado", e);
        } finally {
            if (callableStatement != null) {
                callableStatement.close();
            }
        }

        return usuarioVenta;
    }
    
}
