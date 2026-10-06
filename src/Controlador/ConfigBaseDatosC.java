/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.*;
import Vista.*;
import innotpv.*;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author ChristianSepulveda
 */
public class ConfigBaseDatosC implements ActionListener {
    private ConfigBaseDatosM cbdm;
    private ConfigBaseDatosV cbdv;

    public ConfigBaseDatosC(ConfigBaseDatosM cbdm, ConfigBaseDatosV cbdv) {
        this.cbdm = cbdm;
        this.cbdv = cbdv;
        this.cbdv.btnSalir.addActionListener(this);
        this.cbdv.btnSalir.addActionListener(this);
    }

    public void iniciar() {
        this.cbdv.setTitle("Configuración de la Base de Datos");
        ImageIcon originalIcon  = new ImageIcon(getClass().getResource("/img/FondoCopyCentro72ppp.png"));
        Image image = originalIcon.getImage();
        Image scaledImage = image.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        this.cbdv.setFrameIcon(resizedIcon);
        this.cbdv.btnGuardar.addActionListener(this);
        this.cbdv.btnSalir.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if( e.getSource() == cbdv.btnSalir ){
            cbdv.dispose();
        }else if( e.getSource() == cbdv.btnGuardar ){
            String ip = null;
            String user = null;
            String pass = null;
            
            ip = cbdv.txtIp.getText().toString();
            user = cbdv.txtUsuario.getText().toString();
            pass = cbdv.txtContrasena.getText().toString();
            
            if( ip.equals("") ){
                JOptionPane.showMessageDialog(cbdv, "Ingrese la IP del Servidor.");
            }else if( user.equals("") ){
                JOptionPane.showMessageDialog(cbdv, "Ingrese el Usuario.");
            }else if( pass.equals("") ){
                JOptionPane.showMessageDialog(cbdv, "Ingrese la Contraseña.");
            }
            
            try {
                // Ruta relativa al archivo de configuración
                String configFilePath = "db_config.txt";

                // Verificar la ruta absoluta
                File file = new File(configFilePath);
                // System.out.println("El archivo se guardará en: " + file.getAbsolutePath());
                
                ConfiguracionDB.guardarConfiguracion(ip, user, pass);
            } catch (Exception ex) {
                Logger.getLogger(ConfigBaseDatosC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
