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
public class CorteCajaUsuarioM {
    
    public double obtenerTotalDepto(int param, String fecha, String usuario, int depto) throws SQLException {
        CallableStatement callableStatement = null;
        ResultSet rs = null;
        double totalDepto = 0.0;
        Conexion cc = new Conexion();
        Connection cn = cc.getConexion(); 
        
        try {
            // Llamamos al procedimiento almacenado
            String query = "{CALL GenerarCCDetDeptoUsuario(?, ?, ?, ?, ?, ?, ?)}";
            callableStatement = cn.prepareCall(query);
            
            // Establecemos los parámetros de entrada
            callableStatement.setInt(1, param); // PARAM
            callableStatement.setString(2, fecha); // FECHA
            callableStatement.setString(3, usuario); // USUARIO
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
    
    public Double[] obtenerHeadersCorte(int param, String fecha, String usuario) throws SQLException {
        CallableStatement callableStatement = null;
        Double[] headers = new Double[15];
        Conexion cc = new Conexion();
        Connection cn = cc.getConexion(); 

        try {
            // Llamamos al procedimiento almacenado
            String query = "{CALL GenerarCCHeaderUsuario(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            callableStatement = cn.prepareCall(query);

            // Establecemos los parámetros de entrada
            callableStatement.setInt(1, param); // PARAM
            callableStatement.setString(2, fecha); // FECHA, usar NULL si es necesario
            callableStatement.setString(3, usuario); // USUARIO

            // Establecemos el parámetro de salida (solo uno, por ejemplo, el usuarioVenta)
            callableStatement.registerOutParameter(4, Types.VARCHAR); 
            callableStatement.registerOutParameter(5, Types.VARCHAR); 
            callableStatement.registerOutParameter(6, Types.VARCHAR); 
            callableStatement.registerOutParameter(7, Types.VARCHAR); 
            callableStatement.registerOutParameter(8, Types.VARCHAR); 
            callableStatement.registerOutParameter(9, Types.VARCHAR); 
            callableStatement.registerOutParameter(10, Types.VARCHAR); 
            callableStatement.registerOutParameter(11, Types.VARCHAR); 
            callableStatement.registerOutParameter(12, Types.VARCHAR); 
            callableStatement.registerOutParameter(13, Types.VARCHAR); 
            callableStatement.registerOutParameter(14, Types.VARCHAR); 
            callableStatement.registerOutParameter(15, Types.VARCHAR); 
            callableStatement.registerOutParameter(16, Types.VARCHAR); 
            callableStatement.registerOutParameter(17, Types.VARCHAR); 
            callableStatement.registerOutParameter(18, Types.VARCHAR); 

            // Ejecutamos el procedimiento
            callableStatement.execute();

            // Obtenemos los valores de los parámetros de salida y los almacenamos en el array
            for (int i = 4; i <= 18; i++) {
                // Usamos getDouble para obtener los valores como Double
                headers[i - 4] = callableStatement.getDouble(i); 
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al ejecutar el procedimiento almacenado", e);
        } finally {
            if (callableStatement != null) {
                callableStatement.close();
            }
        }

        return headers;
    }
    
}
