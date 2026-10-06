/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import static Controlador.LoginC.checkPassword;
import Modelo.*;
import Vista.*;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author ChristianSepulveda
 */
public class CambioUsuarioC implements ActionListener, KeyListener{
    private CambioUsuarioM modelo;
    private CambioUsuarioV vista;
    private UsuariosM um = new UsuariosM();

    public CambioUsuarioC(CambioUsuarioM modelo, CambioUsuarioV vista) {
        this.modelo = modelo;
        this.vista = vista;
        this.vista.btnCambiar.addActionListener(this);
        this.vista.btnCancelar.addActionListener(this);
        this.vista.txtUsuario.addKeyListener(this);
        this.vista.psfContrasena.addKeyListener(this);
    }
    
    public void iniciar() {
        vista.setTitle("Cambiar de Usuario");
        ImageIcon originalIcon  = new ImageIcon(getClass().getResource("/img/FondoCopyCentro72ppp.png"));
        Image image = originalIcon.getImage();
        Image scaledImage = image.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        this.vista.setFrameIcon(resizedIcon);
        vista.txtUsuario.requestFocus();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vista.btnCancelar){
            vista.dispose();
        }else if(e.getSource() == vista.btnCambiar){
            IniciarSesion();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if( e.getSource() == vista.txtUsuario || e.getSource() == vista.psfContrasena ){
            if( e.getKeyCode() == KeyEvent.VK_ENTER ){
                IniciarSesion();
            }else if( e.getKeyCode() == KeyEvent.VK_ESCAPE ){
                vista.dispose();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
    public void IniciarSesion(){
        int iniciarSesion = 0;
        String usuario = null;
        String contrasena = null;

        usuario = vista.txtUsuario.getText();
        // contrasena = Arrays.toString(vista.txtPass.getPassword());
        char[] password = vista.psfContrasena.getPassword();
        contrasena = new String(password);
        
        if( usuario.equals("") ){
            JOptionPane.showMessageDialog(vista, "Ingrese el usuario.");
            vista.txtUsuario.requestFocus();
            return;
        }
        
        if( contrasena.equals("") ){
            JOptionPane.showMessageDialog(vista, "Ingrese la contraseña.");
            vista.psfContrasena.requestFocus();
            return;
        }
        
        String usuarioLogeado = null;
        usuarioLogeado = GlobalVaribales.getInstance().usuarioLogueado;
        
        if( usuarioLogeado.equals(usuario) ){
            JOptionPane.showMessageDialog(vista, "El usuario " + usuario + " ya se encuentra logueado.");
            return;
        }

        iniciarSesion = um.validarUsuarioYContrasena(usuario, contrasena);

        switch (iniciarSesion) {
            case 0:
                // System.out.println("Usuario no existe");
                JOptionPane.showMessageDialog(vista, "Usuario no existe");
                vista.txtUsuario.requestFocus();
                break;
            case 1:
                if( checkPassword(contrasena, um.getContrasenaValidation()) ){
                    GlobalVaribales.getInstance().usuarioLogueado = usuario;
                    GlobalVaribales.getInstance().nombreLogueado = um.getNomUsuario();
                    JOptionPane.showMessageDialog(vista, "Bienvenidos " + um.getNomUsuario() + " al sistema CopyCentro");
                    
                    Principal parentFrame = (Principal) SwingUtilities.getWindowAncestor(vista);
                    parentFrame.setTitle("Bienvenido al Punto de Venta - Usuario: " + um.getNomUsuario() + " ( " + usuario + " ).");
                    
                    vista.dispose();
                }else{
                    // System.out.println("Contraseña incorrecta");
                    JOptionPane.showMessageDialog(vista, "Contraseña Incorrecta");
                    vista.psfContrasena.requestFocus();
                }
                break;
            default:
                // System.out.println("Ocurrio un error, contacte a soporte");
                JOptionPane.showMessageDialog(vista, "Ocurrio un error al iniciar sesión, contacte a Soporte Técnico.");
                break;
        }
    }
    
}
