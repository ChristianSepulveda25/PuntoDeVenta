/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ColoresM;
import Vista.ColoresV;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Christian Sepulveda
 */
public class ColoresC implements ActionListener, KeyListener {
    private ColoresV cv;
    private ColoresM cm;
    public int fila_s = 0;

    public ColoresC(ColoresM cm, ColoresV cv) {
        this.cv = cv;
        this.cm = cm;
        this.cv.btnnue.addActionListener(this);
        this.cv.btngua.addActionListener(this);
        this.cv.btncan.addActionListener(this);
        this.cv.btnedi.addActionListener(this);
        this.cv.btnsal.addActionListener(this);
    }

    public void iniciar() {
        cv.setTitle("Registro de Colores");
        cv.tbllis.getTableHeader().setReorderingAllowed(false) ;
        cv.tbllis.getTableHeader().setResizingAllowed(false);
        cv.txtid.setVisible(false);
        CargarColores();
        cv.txtcod.setEnabled(false);
        cv.txtnom.setEnabled(false);
        cv.cmbest.setEnabled(false);
        cv.btngua.setEnabled(false);
        cv.btncan.setEnabled(false);
        cv.btnnue.requestFocus();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == cv.btnnue){
            cv.txtcod.setEnabled(true);
            cv.txtnom.setEnabled(true);
            cv.cmbest.setEnabled(true);
            cv.btngua.setEnabled(true);
            cv.btncan.setEnabled(true);
            cv.btnnue.setEnabled(false);
            cv.btnedi.setEnabled(false);
            cv.btnsal.setEnabled(false);
            cv.txtcod.requestFocus();
        }else if(e.getSource() == cv.btngua){
            if(cv.txtcod.getText().isEmpty()){
                JOptionPane.showMessageDialog(cv, "Campo Código Vacio");
                cv.txtcod.requestFocus();
            }else if(cv.txtnom.getText().isEmpty()){
                JOptionPane.showMessageDialog(cv, "Campo Nombre Vacio");
                cv.txtnom.requestFocus();
            }else{
                int existe = 0;
                existe = VerificarExistencia(cv.txtcod.getText(), cv.txtid.getText());
                if(existe == 1){//1 - SI EXISTE, 0 - NO EXISTE
                    JOptionPane.showMessageDialog(cv, "El Código Ya Se Encuentra Registrado");
                }else{
                    int reg = 0;
                    reg = GuardarColores();
                    if(reg <= 0){//0 - Error, 1 - Guardado
                        JOptionPane.showMessageDialog(cv, "Error al Guardar, Contacte a Soporte Técnico");
                    }else if (reg == 1){
                        if(cv.txtid.getText().isEmpty()){
                            cv.txtcod.setText("");
                            cv.txtnom.setText("");
                            cv.cmbest.setSelectedIndex(0);
                            cv.txtid.setText("");
                            cv.txtcod.requestFocus();
                            CargarColores();
                            JOptionPane.showMessageDialog(cv, "Registro Guardado");
                        }else{
                            cv.txtcod.setEnabled(false);
                            cv.txtnom.setEnabled(false);
                            cv.cmbest.setEnabled(false);
                            cv.btngua.setEnabled(false);
                            cv.btncan.setEnabled(false);
                            cv.btnnue.setEnabled(true);
                            cv.btnedi.setEnabled(true);
                            cv.btnsal.setEnabled(true);
                            cv.txtcod.setText("");
                            cv.txtnom.setText("");
                            cv.cmbest.setSelectedIndex(0);
                            cv.txtid.setText("");
                            cv.btnnue.requestFocus();
                            CargarColores();
                            JOptionPane.showMessageDialog(cv, "Registro Actualizado");
                        }
                        
                    }else{
                        JOptionPane.showMessageDialog(cv, "Error al Guardar, Contacte a Soporte Técnico");
                    }
                }
            }
        }else if(e.getSource() == cv.btncan){
            cv.txtcod.setEnabled(false);
            cv.txtnom.setEnabled(false);
            cv.cmbest.setEnabled(false);
            cv.btngua.setEnabled(false);
            cv.btncan.setEnabled(false);
            cv.btnnue.setEnabled(true);
            cv.btnedi.setEnabled(true);
            cv.btnsal.setEnabled(true);
            cv.txtcod.setText("");
            cv.txtnom.setText("");
            cv.cmbest.setSelectedIndex(0);
            cv.txtid.setText("");
            cv.btnnue.requestFocus();
        }else if(e.getSource() == cv.btnedi){
            fila_s = cv.tbllis.getSelectedRow();
            if(fila_s < 0){
                JOptionPane.showMessageDialog(cv, "Seleccione un Registro");
            }else{
                cv.txtid.setText(cv.tbllis.getValueAt(fila_s, 0).toString());
                cv.txtcod.setText(cv.tbllis.getValueAt(fila_s, 1).toString());
                cv.txtnom.setText(cv.tbllis.getValueAt(fila_s, 2).toString());
                cv.cmbest.setSelectedItem(cv.tbllis.getValueAt(fila_s, 3).toString());
                cv.txtcod.setEnabled(true);
                cv.txtnom.setEnabled(true);
                cv.cmbest.setEnabled(true);
                cv.btngua.setEnabled(true);
                cv.btncan.setEnabled(true);
                cv.btnnue.setEnabled(false);
                cv.btnedi.setEnabled(false);
                cv.btnsal.setEnabled(false);
                cv.txtcod.requestFocus();
           }
        }else if(e.getSource() == cv.btnsal){
            cv.dispose();
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
        filas = cv.tbllis.getRowCount();
        
        for (int i = 0; i < filas; i++) {
            if(!id.equals("")){
                if(fila_s == i){
                    
                }else{
                    if(cv.tbllis.getValueAt(i, 1).toString().equals(codigo)){
                        return 1;
                    }
                }
            }else{
                if(cv.tbllis.getValueAt(i, 1).toString().equals(codigo)){
                    return 1;
                }
            }
        }
        
        return 0;
    }
    
    public int GuardarColores(){
        int id = 0;
        String cod = null;
        String nom = null;
        int est = 0;
        
        if(!cv.txtid.getText().isEmpty()){
            id = Integer.parseInt(cv.txtid.getText());
        }
        
        cod = cv.txtcod.getText();
        nom = cv.txtnom.getText();
        
        if(cv.cmbest.getSelectedIndex() == 0){
            est = 1;
        }else if(cv.cmbest.getSelectedIndex() == 1){
            est = 0;
        }
        
        cm.setId(id);
        cm.setCon(cod);
        cm.setNom(nom);
        cm.setEst(est);
        
        if(cm.GuardarColores()){
            return 1;
        }
        return 0;
    }
    
    public void CargarColores(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        cm.setId(-1);
        cm.setCon(null);
        cm.setNom(null);
        cm.setEst(0);
        modelt = cm.CargarColores();
        cv.tbllis.setModel(modelt);
        
        cv.tbllis.getColumnModel().getColumn(0).setMaxWidth(0);
        cv.tbllis.getColumnModel().getColumn(0).setMinWidth(0);
        cv.tbllis.getColumnModel().getColumn(0).setPreferredWidth(0);
        cv.tbllis.getColumnModel().getColumn(1).setMaxWidth(70);
        cv.tbllis.getColumnModel().getColumn(1).setMinWidth(70);
        cv.tbllis.getColumnModel().getColumn(1).setPreferredWidth(70);
        cv.tbllis.getColumnModel().getColumn(3).setMaxWidth(100);
        cv.tbllis.getColumnModel().getColumn(3).setMinWidth(100);
        cv.tbllis.getColumnModel().getColumn(3).setPreferredWidth(100);
    }
    
}
