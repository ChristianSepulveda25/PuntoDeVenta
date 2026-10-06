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
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author ChristianSepulveda
 */
public class CambiarContrasenaC implements ActionListener, KeyListener {
    private CambiarContrasenaV v;
    private CambiarContrasenaM m;
    public int fila_s = 0;
    
    public CambiarContrasenaC(CambiarContrasenaM m, CambiarContrasenaV v) {
        this.v = v;
        this.m = m;
        this.v.btnCambiar.addActionListener(this);
        this.v.btnSalir.addActionListener(this);
        this.v.btnGuardar_M.addActionListener(this);
        this.v.btnSalir_M.addActionListener(this);
    }

    public void iniciar() {
        v.setTitle("Cambiar contraseña");
        ImageIcon originalIcon  = new ImageIcon(getClass().getResource("/img/FondoCopyCentro72ppp.png"));
        Image image = originalIcon.getImage();
        Image scaledImage = image.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        this.v.setFrameIcon(resizedIcon);
        v.tbllis.getTableHeader().setReorderingAllowed(false) ;
        // v.tbllis.getTableHeader().setResizingAllowed(false);
        
        v.txtId_M.setVisible(false);
        v.txtUser_M.setEnabled(false);
        
        try {
            CargarUsuarios();
        } catch (SQLException ex) {
            Logger.getLogger(CambiarContrasenaC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(v.btnSalir == e.getSource()){
            v.dispose();
        }else if(v.btnCambiar == e.getSource()){
            int fila = -1;
            fila = v.tbllis.getSelectedRow();
            if(fila < 0){
                JOptionPane.showMessageDialog(v, "Seleccione un registro.");
            }else{
                String user = null;
                String id = null;
                
                id = v.tbllis.getValueAt(fila, 0).toString();
                user = v.tbllis.getValueAt(fila, 2).toString();
                
                abrirModalCambiarPass(user,id);
            }
        }else if(v.btnSalir_M == e.getSource()){
            cerrarModal(v.dCambiarPass);
        }else if(v.btnGuardar_M == e.getSource()){
            String newPass = "";
            newPass = v.txtPass_M.getText();
            
            if(newPass.equals("")){
                JOptionPane.showMessageDialog(v, "Ingrese una contraseña.");
            }else{
                int res = ActualizarContrasena();
                if(res == 1){
                    JOptionPane.showMessageDialog(v, "Contraseña Actualizada Correctamente.");
                    v.txtId_M.setText("");
                    v.txtPass_M.setText("");
                    v.txtUser_M.setText("");
                    cerrarModal(v.dCambiarPass);
                    try {
                        CargarUsuarios();
                    } catch (SQLException ex) {
                        Logger.getLogger(CambiarContrasenaC.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void keyPressed(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public void abrirModalCambiarPass(String user, String id) {
        v.dCambiarPass.setSize(275, 265);
        v.dCambiarPass.setModal(true);
        v.dCambiarPass.setLocationRelativeTo(v);
        v.dCambiarPass.setTitle("Cambiar Contraseña");
        v.dCambiarPass.setResizable(false);
        
        v.txtId_M.setText(id);
        v.txtUser_M.setText(user);
        
        v.dCambiarPass.setVisible(true);
    }
    
    public void CargarUsuarios() throws SQLException{
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        String user = null;
        user = GlobalVaribales.getInstance().usuarioLogueado;
        
        //System.out.println(""+ buscar);
        m.setId(-1);
        m.setUser(user.toUpperCase());
        modelt = m.CargarUsuarios();
        v.tbllis.setModel(modelt);
        
        v.tbllis.getColumnModel().getColumn(0).setMaxWidth(0);
        v.tbllis.getColumnModel().getColumn(0).setMinWidth(0);
        v.tbllis.getColumnModel().getColumn(0).setPreferredWidth(0);
        
        // centrar.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
        // v.tbllis.getColumnModel().getColumn(0).setCellRenderer(centrar);
    }
    
    public int ActualizarContrasena(){
        int id = 0;
        String contrasena = null;
        
        id = Integer.parseInt(v.txtId_M.getText());
        contrasena = hashPassword(v.txtPass_M.getText().toString());
        
        m.setId(id);
        m.setPass(contrasena);
        
        if(m.ActualizarContrasena()){
            return 1;
        }
        return 0;
    }
    
    private String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }
    
    public void cerrarModal(JDialog dialog){
        dialog.dispose();
        // vv.txtbus.requestFocus();
    }
    
}

    