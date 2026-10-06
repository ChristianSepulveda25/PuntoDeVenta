/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.CatProM;
import Vista.CatProV;
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
 * @author Christian Sepulveda
 */
public class CatProC implements ActionListener, KeyListener {
    private CatProV cpv;
    private CatProM cpm;
    public int fila_s = 0;

    public CatProC(CatProM cpm, CatProV cpv) {
        this.cpv = cpv;
        this.cpm = cpm;
        this.cpv.btnnue.addActionListener(this);
        this.cpv.btngua.addActionListener(this);
        this.cpv.btncan.addActionListener(this);
        this.cpv.btnedi.addActionListener(this);
        this.cpv.btnsal.addActionListener(this);
    }

    public void iniciar() {
        cpv.setTitle("Registro Categoría de Productos");
        ImageIcon originalIcon  = new ImageIcon(getClass().getResource("/img/FondoCopyCentro72ppp.png"));
        Image image = originalIcon.getImage();
        Image scaledImage = image.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        this.cpv.setFrameIcon(resizedIcon);
        cpv.tbllis.getTableHeader().setReorderingAllowed(false) ;
        cpv.tbllis.getTableHeader().setResizingAllowed(false);
        cpv.txtid.setVisible(false);
        CargarCatPro();
        cpv.txtnom.setEnabled(false);
        cpv.cmbest.setEnabled(false);
        cpv.btngua.setEnabled(false);
        cpv.btncan.setEnabled(false);
        cpv.btnnue.requestFocus();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == cpv.btnnue){
            cpv.txtnom.setEnabled(true);
            cpv.cmbest.setEnabled(true);
            cpv.btngua.setEnabled(true);
            cpv.btncan.setEnabled(true);
            cpv.btnnue.setEnabled(false);
            cpv.btnedi.setEnabled(false);
            cpv.btnsal.setEnabled(false);
            cpv.txtnom.requestFocus();
        }else if(e.getSource() == cpv.btngua){
            if(cpv.txtnom.getText().isEmpty()){
                JOptionPane.showMessageDialog(cpv, "Campo Nombre Vacio");
                cpv.txtnom.requestFocus();
            }else{
                int existe = 0;
                existe = VerificarExistencia(cpv.txtnom.getText(), cpv.txtid.getText());
                if(existe == 1){//1 - SI EXISTE, 0 - NO EXISTE
                    JOptionPane.showMessageDialog(cpv, "El Nombre ya se encuentra registrado");
                }else{
                    int reg = 0;
                    reg = GuardarCatPro();
                    if(reg <= 0){//0 - Error, 1 - Guardado
                        JOptionPane.showMessageDialog(cpv, "Error al Guardar, Contacte a Soporte Técnico");
                    }else if (reg == 1){
                        if(cpv.txtid.getText().isEmpty()){
                            cpv.txtnom.setText("");
                            cpv.cmbest.setSelectedIndex(0);
                            cpv.txtid.setText("");
                            cpv.txtnom.requestFocus();
                            CargarCatPro();
                            JOptionPane.showMessageDialog(cpv, "Registro Guardado");
                        }else{
                            cpv.txtnom.setEnabled(false);
                            cpv.cmbest.setEnabled(false);
                            cpv.btngua.setEnabled(false);
                            cpv.btncan.setEnabled(false);
                            cpv.btnnue.setEnabled(true);
                            cpv.btnedi.setEnabled(true);
                            cpv.btnsal.setEnabled(true);
                            cpv.txtnom.setText("");
                            cpv.cmbest.setSelectedIndex(0);
                            cpv.txtid.setText("");
                            cpv.btnnue.requestFocus();
                            CargarCatPro();
                            JOptionPane.showMessageDialog(cpv, "Registro Actualizado");
                        }
                        
                    }else{
                        JOptionPane.showMessageDialog(cpv, "Error al Guardar, Contacte a Soporte Técnico");
                    }
                }
            }
        }else if(e.getSource() == cpv.btncan){
            cpv.txtnom.setEnabled(false);
            cpv.cmbest.setEnabled(false);
            cpv.btngua.setEnabled(false);
            cpv.btncan.setEnabled(false);
            cpv.btnnue.setEnabled(true);
            cpv.btnedi.setEnabled(true);
            cpv.btnsal.setEnabled(true);
            cpv.txtnom.setText("");
            cpv.cmbest.setSelectedIndex(0);
            cpv.txtid.setText("");
            cpv.btnnue.requestFocus();
        }else if(e.getSource() == cpv.btnedi){
            fila_s = cpv.tbllis.getSelectedRow();
            if(fila_s < 0){
                JOptionPane.showMessageDialog(cpv, "Seleccione un Registro");
            }else{
                cpv.txtid.setText(cpv.tbllis.getValueAt(fila_s, 0).toString());
                cpv.txtnom.setText(cpv.tbllis.getValueAt(fila_s, 1).toString());
                cpv.cmbest.setSelectedItem(cpv.tbllis.getValueAt(fila_s, 2).toString());
                cpv.txtnom.setEnabled(true);
                cpv.cmbest.setEnabled(true);
                cpv.btngua.setEnabled(true);
                cpv.btncan.setEnabled(true);
                cpv.btnnue.setEnabled(false);
                cpv.btnedi.setEnabled(false);
                cpv.btnsal.setEnabled(false);
                cpv.txtnom.requestFocus();
           }
        }else if(e.getSource() == cpv.btnsal){
            cpv.dispose();
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
    
    public int VerificarExistencia(String codigo, String id){
        int filas = 0;
        filas = cpv.tbllis.getRowCount();
        
        for (int i = 0; i < filas; i++) {
            if(!id.equals("")){
                if(fila_s == i){
                    
                }else{
                    if(cpv.tbllis.getValueAt(i, 1).toString().equals(codigo)){
                        return 1;
                    }
                }
            }else{
                if(cpv.tbllis.getValueAt(i, 1).toString().equals(codigo)){
                    return 1;
                }
            }
        }
        
        return 0;
    }
    
    public int GuardarCatPro(){
        int id = 0;
        String cod = null;
        String nom = null;
        int est = 0;
        
        if(!cpv.txtid.getText().isEmpty()){
            id = Integer.parseInt(cpv.txtid.getText());
        }
        
        cod = "0";
        nom = cpv.txtnom.getText().toUpperCase();
        
        if(cpv.cmbest.getSelectedIndex() == 0){
            est = 1;
        }else if(cpv.cmbest.getSelectedIndex() == 1){
            est = 0;
        }
        
        cpm.setId(id);
        cpm.setCon(cod);
        cpm.setNom(nom);
        cpm.setEst(est);
        
        if(cpm.GuardarCatPro()){
            return 1;
        }
        return 0;
    }
    
    public void CargarCatPro(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        cpm.setId(-1);
        cpm.setCon(null);
        cpm.setNom(null);
        cpm.setEst(0);
        modelt = cpm.CargarCatPro();
        cpv.tbllis.setModel(modelt);
        
        cpv.tbllis.getColumnModel().getColumn(0).setMaxWidth(0);
        cpv.tbllis.getColumnModel().getColumn(0).setMinWidth(0);
        cpv.tbllis.getColumnModel().getColumn(0).setPreferredWidth(0);
        /*cpv.tbllis.getColumnModel().getColumn(1).setMaxWidth(100);
        cpv.tbllis.getColumnModel().getColumn(1).setMinWidth(100);
        cpv.tbllis.getColumnModel().getColumn(1).setPreferredWidth(100);*/
        cpv.tbllis.getColumnModel().getColumn(2).setMaxWidth(100);
        cpv.tbllis.getColumnModel().getColumn(2).setMinWidth(100);
        cpv.tbllis.getColumnModel().getColumn(2).setPreferredWidth(100);
    }
    
}
