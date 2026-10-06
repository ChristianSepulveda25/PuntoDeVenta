/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.*;
import Vista.*;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author ChristianSepulveda
 */
public class UsuariosC implements ActionListener, KeyListener {
    private UsuariosV uv;
    private UsuariosM um;
    public int fila_s = 0;
    
    public UsuariosC(UsuariosM um, UsuariosV uv) {
        this.uv = uv;
        this.um = um;
        this.uv.btnnue.addActionListener(this);
        this.uv.btngua.addActionListener(this);
        this.uv.btncan.addActionListener(this);
        this.uv.btnedi.addActionListener(this);
        this.uv.btnsal.addActionListener(this);
    }

    public void iniciar() {
        uv.setTitle("Registro de Usuarios");
        ImageIcon originalIcon  = new ImageIcon(getClass().getResource("/img/FondoCopyCentro72ppp.png"));
        Image image = originalIcon.getImage();
        Image scaledImage = image.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        this.uv.setFrameIcon(resizedIcon);
        uv.tbllis.getTableHeader().setReorderingAllowed(false) ;
        uv.tbllis.getTableHeader().setResizingAllowed(false);
        uv.txtid.setVisible(false);
        // CargarCatPro();
        CargarUsuarios();
        bloquear();
        // uv.btnnue.requestFocus();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == uv.btnsal){
            uv.dispose();
        }else if(e.getSource() == uv.btnnue){
            desbloquear();
        }else if(e.getSource() == uv.btngua){
            if(uv.txtnombre.getText().isEmpty()){
                JOptionPane.showMessageDialog(uv, "Campo Nombre Vacio");
                uv.txtnombre.requestFocus();
            }else if(uv.txtusuario.getText().isEmpty()){
                JOptionPane.showMessageDialog(uv, "Campo Usuario Vacio");
                uv.txtusuario.requestFocus();
            }else if(uv.txtcontrasena.getText().isEmpty()){
                JOptionPane.showMessageDialog(uv, "Campo Contraseña Vacio");
                uv.txtcontrasena.requestFocus();
            }else{
                int existe = 0;
                existe = VerificarExistencia(uv.txtusuario.getText().toString());
                if(existe == 1){//1 - SI EXISTE, 0 - NO EXISTE
                    JOptionPane.showMessageDialog(uv, "El Nombre ya se encuentra registrado");
                }else{
                    int reg = 0;
                    reg = GuardarUsuario();
                    if(reg <= 0){//0 - Error, 1 - Guardado
                        JOptionPane.showMessageDialog(uv, "Error al Guardar, Contacte a Soporte Técnico");
                    }else if (reg == 1){
                        if(uv.txtid.getText().isEmpty()){
                            desbloquear();
                            JOptionPane.showMessageDialog(uv, "Registro Guardado");
                            CargarUsuarios();
                        }else{
                            bloquear();
                            /*cpv.txtnom.setEnabled(false);
                            cpv.cmbest.setEnabled(false);
                            cpv.btngua.setEnabled(false);
                            cpv.btncan.setEnabled(false);
                            cpv.btnnue.setEnabled(true);
                            cpv.btnedi.setEnabled(true);
                            cpv.btnsal.setEnabled(true);
                            cpv.txtnom.setText("");
                            cpv.cmbest.setSelectedIndex(0);
                            cpv.txtid.setText("");
                            cpv.btnnue.requestFocus();*/
                            // CargarCatPro();
                            JOptionPane.showMessageDialog(uv, "Registro Actualizado");
                            CargarUsuarios();
                        }
                        
                    }else{
                        JOptionPane.showMessageDialog(uv, "Error al Guardar, Contacte a Soporte Técnico");
                    }
                }
            }
        }else if(e.getSource() == uv.btncan){
            bloquear();
        }else if(e.getSource() == uv.btnedi){
            fila_s = uv.tbllis.getSelectedRow();
            if(fila_s < 0){
                JOptionPane.showMessageDialog(uv, "Seleccione un Registro");
            }else{
                desbloquear();
                uv.txtid.setText(uv.tbllis.getValueAt(fila_s, 0).toString());
                uv.txtnombre.setText(uv.tbllis.getValueAt(fila_s, 1).toString());
                uv.txtusuario.setText(uv.tbllis.getValueAt(fila_s, 2).toString());
                uv.cmbtipo.setSelectedItem(uv.tbllis.getValueAt(fila_s, 3).toString());
                uv.cmbest.setSelectedItem(uv.tbllis.getValueAt(fila_s, 4).toString());
                uv.txtusuario.setEnabled(false);
                uv.txtcontrasena.setEnabled(false);
                uv.txtcontrasena.setText("**********");
                uv.txtnombre.requestFocus();
           }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
    public void bloquear(){
        uv.txtnombre.setEnabled(false);
        uv.txtusuario.setEnabled(false);
        uv.txtcontrasena.setEnabled(false);
        uv.cmbtipo.setEnabled(false);
        uv.cmbest.setEnabled(false);
        uv.btngua.setEnabled(false);
        uv.btncan.setEnabled(false);
        uv.btnnue.setEnabled(true);
        uv.btnedi.setEnabled(true);
        uv.btnsal.setEnabled(true);
        limpiar();
        uv.txtbuscar.requestFocus();
    }
    
    public void desbloquear(){
        uv.txtnombre.setEnabled(true);
        uv.txtusuario.setEnabled(true);
        uv.txtcontrasena.setEnabled(true);
        uv.cmbtipo.setEnabled(true);
        uv.cmbest.setEnabled(true);
        uv.btngua.setEnabled(true);
        uv.btncan.setEnabled(true);
        uv.btnnue.setEnabled(false);
        uv.btnedi.setEnabled(false);
        uv.btnsal.setEnabled(false);
        limpiar();
        uv.txtnombre.requestFocus();
    }
    
    public void limpiar(){
        uv.txtnombre.setText("");
        uv.txtusuario.setText("");
        uv.txtcontrasena.setText("");
        uv.cmbtipo.setSelectedIndex(0);
        uv.cmbest.setSelectedIndex(0);
        uv.txtbuscar.setText("");
        uv.txtid.setText("");
    }
    
    public int VerificarExistencia(String usuario){
        return 0;
    }
    
    public int GuardarUsuario(){
        int id = 0;
        String nombre = null;
        String usuario = null;
        String contrasena = null;
        int tipo = 0;
        int estatus = 0;
        
        if(!uv.txtid.getText().isEmpty()){
            id = Integer.parseInt(uv.txtid.getText());
        }
        
        nombre = uv.txtnombre.getText().toString();
        usuario = uv.txtusuario.getText().toString();
        contrasena = hashPassword(uv.txtcontrasena.getText().toString());
        // contrasena = encrypt(uv.txtcontrasena.getText().toString());
        tipo = uv.cmbtipo.getSelectedIndex();
        if(uv.cmbest.getSelectedIndex() == 0){
            estatus = 1;
        }else if(uv.cmbest.getSelectedIndex() == 1){
            estatus = 0;
        }
        
        um.setId(id);
        um.setNombre(nombre);
        um.setUsuario(usuario);
        um.setContrasena(contrasena);
        um.setTipo(tipo);
        um.setEstatus(estatus);
        
        if(um.GuardarUsuarios()){
            return 1;
        }
        return 0;
    }
    
    public void CargarUsuarios(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        
        um.setId(-1);
        um.setNombre("");
        um.setUsuario("");
        um.setContrasena("");
        um.setTipo(0);
        um.setEstatus(0);
        
        modelt = um.CargarUsuarios();
        uv.tbllis.setModel(modelt);
        
        uv.tbllis.getColumnModel().getColumn(0).setMaxWidth(0);
        uv.tbllis.getColumnModel().getColumn(0).setMinWidth(0);
        uv.tbllis.getColumnModel().getColumn(0).setPreferredWidth(0);
        uv.tbllis.getColumnModel().getColumn(1).setMaxWidth(300);
        uv.tbllis.getColumnModel().getColumn(1).setMinWidth(300);
        uv.tbllis.getColumnModel().getColumn(1).setPreferredWidth(300);
        /*cpv.tbllis.getColumnModel().getColumn(2).setMaxWidth(100);
        cpv.tbllis.getColumnModel().getColumn(2).setMinWidth(100);
        cpv.tbllis.getColumnModel().getColumn(2).setPreferredWidth(100);*/
    }
    
    private String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }
    
}
