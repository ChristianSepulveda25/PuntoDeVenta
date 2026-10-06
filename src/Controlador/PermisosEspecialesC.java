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
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ChristianSepulveda
 */
public class PermisosEspecialesC implements ActionListener, KeyListener {
    private PermisosEspecialesV pev;
    private PermisosEspecialesM pem;
    int fila_s = 0;
    
     public PermisosEspecialesC(PermisosEspecialesM pem, PermisosEspecialesV pev) {
        this.pev = pev;
        this.pem = pem;
        this.pev.btnGuardar.addActionListener(this);
        this.pev.btnCancelar.addActionListener(this);
        this.pev.btnEditar.addActionListener(this);
        this.pev.btnSalir.addActionListener(this);
    }

    public void iniciar() {
        pev.setTitle("Registro Permisos Especiales");
        ImageIcon originalIcon  = new ImageIcon(getClass().getResource("/img/FondoCopyCentro72ppp.png"));
        Image image = originalIcon.getImage();
        Image scaledImage = image.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        this.pev.setFrameIcon(resizedIcon);
        pev.tbllis.getTableHeader().setReorderingAllowed(false) ;
        pev.tbllis.getTableHeader().setResizingAllowed(false);
        pev.txtId.setVisible(false);
        CargarPermisosEspeciales();
        pev.txtNombre.setEnabled(false);
        pev.cmbPermiso.setEnabled(false);
        pev.btnGuardar.setEnabled(false);
        pev.btnCancelar.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == pev.btnGuardar){
            if(pev.txtNombre.getText().isEmpty()){
                JOptionPane.showMessageDialog(pev, "Campo Nombre Vacio");
                pev.txtNombre.requestFocus();
            }else{
                int existe = 0;
                existe = VerificarExistencia(pev.txtNombre.getText(), pev.txtId.getText());
                if(existe == 1){//1 - SI EXISTE, 0 - NO EXISTE
                    JOptionPane.showMessageDialog(pev, "El Nombre ya se encuentra registrado");
                }else{
                    int reg = 0;
                    reg = GuardarPermisosEspeciales();
                    if(reg <= 0){//0 - Error, 1 - Guardado
                        JOptionPane.showMessageDialog(pev, "Error al Guardar, Contacte a Soporte Técnico");
                    }else if (reg == 1){
                        if(pev.txtId.getText().isEmpty()){
                            pev.txtNombre.setText("");
                            pev.cmbPermiso.setSelectedIndex(0);
                            pev.txtId.setText("");
                            pev.txtNombre.requestFocus();
                            CargarPermisosEspeciales();
                            JOptionPane.showMessageDialog(pev, "Registro Guardado");
                        }else{
                            pev.txtNombre.setEnabled(false);
                            pev.cmbPermiso.setEnabled(false);
                            pev.btnGuardar.setEnabled(false);
                            pev.btnCancelar.setEnabled(false);
                            pev.btnEditar.setEnabled(true);
                            pev.btnSalir.setEnabled(true);
                            pev.txtNombre.setText("");
                            pev.cmbPermiso.setSelectedIndex(0);
                            pev.txtId.setText("");
                            CargarPermisosEspeciales();
                            JOptionPane.showMessageDialog(pev, "Registro Actualizado");
                        }
                        
                    }else{
                        JOptionPane.showMessageDialog(pev, "Error al Guardar, Contacte a Soporte Técnico");
                    }
                }
            }
        }else if(e.getSource() == pev.btnCancelar){
            pev.txtNombre.setEnabled(false);
            pev.cmbPermiso.setEnabled(false);
            pev.btnGuardar.setEnabled(false);
            pev.btnCancelar.setEnabled(false);
            pev.btnEditar.setEnabled(true);
            pev.btnSalir.setEnabled(true);
            pev.txtNombre.setText("");
            pev.cmbPermiso.setSelectedIndex(0);
            pev.txtId.setText("");
        }else if(e.getSource() == pev.btnEditar){
            fila_s = pev.tbllis.getSelectedRow();
            if(fila_s < 0){
                JOptionPane.showMessageDialog(pev, "Seleccione un Registro");
            }else{
                pev.txtId.setText(pev.tbllis.getValueAt(fila_s, 0).toString());
                pev.txtNombre.setText(pev.tbllis.getValueAt(fila_s, 1).toString());
                pev.cmbPermiso.setSelectedItem(pev.tbllis.getValueAt(fila_s, 2).toString());
                pev.txtNombre.setEnabled(false);
                pev.cmbPermiso.setEnabled(true);
                pev.btnGuardar.setEnabled(true);
                pev.btnCancelar.setEnabled(true);
                pev.btnEditar.setEnabled(false);
                pev.btnSalir.setEnabled(false);
                pev.txtNombre.requestFocus();
           }
        }else if(e.getSource() == pev.btnSalir){
            pev.dispose();
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
    
    public void CargarPermisosEspeciales(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        pem.setId(-1);
        pem.setNombre(null);
        pem.setPermiso(0);
        modelt = pem.CargarPermisosEspeciales();
        pev.tbllis.setModel(modelt);
        
        pev.tbllis.getColumnModel().getColumn(0).setMaxWidth(0);
        pev.tbllis.getColumnModel().getColumn(0).setMinWidth(0);
        pev.tbllis.getColumnModel().getColumn(0).setPreferredWidth(0);
        /*cpv.tbllis.getColumnModel().getColumn(1).setMaxWidth(100);
        cpv.tbllis.getColumnModel().getColumn(1).setMinWidth(100);
        cpv.tbllis.getColumnModel().getColumn(1).setPreferredWidth(100);*/
        pev.tbllis.getColumnModel().getColumn(2).setMaxWidth(100);
        pev.tbllis.getColumnModel().getColumn(2).setMinWidth(100);
        pev.tbllis.getColumnModel().getColumn(2).setPreferredWidth(100);
    }
    
    public int VerificarExistencia(String codigo, String id){
        int filas = 0;
        filas = pev.tbllis.getRowCount();
        
        for (int i = 0; i < filas; i++) {
            if(!id.equals("")){
                if(fila_s == i){
                    
                }else{
                    if(pev.tbllis.getValueAt(i, 1).toString().equals(codigo)){
                        return 1;
                    }
                }
            }else{
                if(pev.tbllis.getValueAt(i, 1).toString().equals(codigo)){
                    return 1;
                }
            }
        }
        
        return 0;
    }
    
    public int GuardarPermisosEspeciales(){
        int id = 0;
        String cod = null;
        String nom = null;
        int per = 0;
        
        if(!pev.txtId.getText().isEmpty()){
            id = Integer.parseInt(pev.txtId.getText());
        }
        
        cod = "0";
        nom = pev.txtNombre.getText();
        
        if(pev.cmbPermiso.getSelectedIndex() == 0){
            per = 1;
        }else if(pev.cmbPermiso.getSelectedIndex() == 1){
            per = 0;
        }
        
        pem.setId(id);
        pem.setNombre(nom);
        pem.setPermiso(per);
        
        if(pem.GuardarPermisosEspeciales()){
            return 1;
        }
        return 0;
    }
    
}
