/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import com.mysql.jdbc.Connection;
import innotpv.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author INFORMATICA-05
 */
public class Conexion {
    private final String base = "pdv_copycentro?allowPublicKeyRetrieval=true&useSSL=false";
    // private final String base = "pdv_detodounpoco?allowPublicKeyRetrieval=true&useSSL=false";
    private final String user = "root";
    // private final String pass = "root";
    private final String pass = "6x4uc6qY7zGn";
    private final String url = "jdbc:mysql://localhost:3306/"+base;
    private Connection con = null;
    
    public Connection getConexion(){
        // Leer configuración
        String[] config = null;
        try {
            config = ConfiguracionDB.leerConfiguracion();
        } catch (Exception ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        String ip = "jdbc:mysql://"+config[0]+":3306/"+base;
        // System.out.println("Ip: " + ip);
        String usuario = config[1];
        // System.out.println("User: " + usuario);
        String contraseña = config[2];
        // System.out.println("Contraseña: " + contraseña);
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(ip, usuario, contraseña);
            // con = (Connection) DriverManager.getConnection(this.url, this.user, this.pass);
        }catch(SQLException e){
            System.err.println(e);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Modelo.Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
}
