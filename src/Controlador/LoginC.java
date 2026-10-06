/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.GlobalVaribales;
import Modelo.LoginM;
import Modelo.UsuariosM;
import Vista.LoginV;
import Vista.Principal;
import static com.sun.tools.javac.tree.TreeInfo.args;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author ChristianSepulveda
 */
public class LoginC implements ActionListener, KeyListener{
    
    private LoginM modelo;
    private UsuariosM um = new UsuariosM();
    private LoginV vista;

    public LoginC(LoginM modelo, LoginV vista) {
        this.modelo = modelo;
        this.vista = vista;
        this.vista.btnIniciar.addActionListener(this);
        this.vista.btnSalir.addActionListener(this);
        this.vista.txtPass.addKeyListener(this);
        this.vista.txtUser.addKeyListener(this);
        Image icon = new ImageIcon(getClass().getResource("/img/FondoCopyCentro72ppp.png")).getImage();
        this.vista.setIconImage(icon);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if( e.getSource() == vista.btnIniciar ){
            IniciarSesion();
        }else if( e.getSource() == vista.btnSalir ){
            System.exit(0);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if( e.getSource() == vista.txtPass || 
            e.getSource() == vista.txtUser ){
            if( e.getKeyCode() == KeyEvent.VK_ENTER ){
                IniciarSesion();
            }else if( e.getKeyCode() == KeyEvent.VK_ESCAPE ){
                System.exit(0);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
    private String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }
    
    // Método para verificar la contraseña
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
    
    public void IniciarSesion(){
        int iniciarSesion = 0;
        String usuario = null;
        String contrasena = null;

        usuario = vista.txtUser.getText();
        // contrasena = Arrays.toString(vista.txtPass.getPassword());
        char[] password = vista.txtPass.getPassword();
        contrasena = new String(password);

        iniciarSesion = um.validarUsuarioYContrasena(usuario, contrasena);

        switch (iniciarSesion) {
            case 0:
                // System.out.println("Usuario no existe");
                JOptionPane.showMessageDialog(vista, "Usuario no existe");
                break;
            case 1:
                if( checkPassword(contrasena, um.getContrasenaValidation()) ){
                    /*GlobalVaribales.getInstance().usuarioLogueado = usuario;
                    GlobalVaribales.getInstance().nombreLogueado = um.getNomUsuario();

                    JOptionPane.showMessageDialog(
                        vista,
                        "Bienvenidos " + um.getNomUsuario() + " al sistema CopyCentro"
                    );

                    vista.dispose();

                    System.out.println(Principal.class.getName());
                    
                    Principal principal = new Principal();
                    principal.setVisible(true);*/
                    GlobalVaribales.getInstance().usuarioLogueado = usuario;
                    GlobalVaribales.getInstance().nombreLogueado = um.getNomUsuario();
                    JOptionPane.showMessageDialog(vista, "Bienvenidos " + um.getNomUsuario() + " al sistema CopyCentro");
                    vista.dispose();
                    String args[] = null;
                    Principal.main(args);
                }else{
                    // System.out.println("Contraseña incorrecta");
                    JOptionPane.showMessageDialog(vista, "Contraseña Incorrecta");
                }
                break;
            default:
                // System.out.println("Ocurrio un error, contacte a soporte");
                JOptionPane.showMessageDialog(vista, "Ocurrio un error al iniciar sesión, contacte a Soporte Técnico.");
                break;
        }
    }
    
}
