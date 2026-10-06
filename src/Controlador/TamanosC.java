/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.*;
import Vista.*;
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
public class TamanosC implements ActionListener, KeyListener {
    private TamanosV tv;
    private TamanosM tm;
    public int fila_s = 0;

    public TamanosC(TamanosM tm, TamanosV tv) {
        this.tv = tv;
        this.tm = tm;
        this.tv.btnnue.addActionListener(this);
        this.tv.btngua.addActionListener(this);
        this.tv.btncan.addActionListener(this);
        this.tv.btnedi.addActionListener(this);
        this.tv.btnsal.addActionListener(this);
    }

    public void iniciar() {
        tv.setTitle("Registro de Tamaños");
        tv.tbllis.getTableHeader().setReorderingAllowed(false) ;
        tv.tbllis.getTableHeader().setResizingAllowed(false);
        tv.txtid.setVisible(false);
        CargarTamanos();
        tv.txtcod.setEnabled(false);
        tv.txtnom.setEnabled(false);
        tv.cmbest.setEnabled(false);
        tv.btngua.setEnabled(false);
        tv.btncan.setEnabled(false);
        tv.btnnue.requestFocus();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == tv.btnnue){
            tv.txtcod.setEnabled(true);
            tv.txtnom.setEnabled(true);
            tv.cmbest.setEnabled(true);
            tv.btngua.setEnabled(true);
            tv.btncan.setEnabled(true);
            tv.btnnue.setEnabled(false);
            tv.btnedi.setEnabled(false);
            tv.btnsal.setEnabled(false);
            tv.txtcod.requestFocus();
        }else if(e.getSource() == tv.btngua){
            if(tv.txtcod.getText().isEmpty()){
                JOptionPane.showMessageDialog(tv, "Campo Código Vacio");
                tv.txtcod.requestFocus();
            }else if(tv.txtnom.getText().isEmpty()){
                JOptionPane.showMessageDialog(tv, "Campo Nombre Vacio");
                tv.txtnom.requestFocus();
            }else{
                int existe = 0;
                existe = VerificarExistencia(tv.txtcod.getText(), tv.txtid.getText());
                if(existe == 1){//1 - SI EXISTE, 0 - NO EXISTE
                    JOptionPane.showMessageDialog(tv, "El Código Ya Se Encuentra Registrado");
                }else{
                    int reg = 0;
                    reg = GuardarTamanos();
                    if(reg <= 0){//0 - Error, 1 - Guardado
                        JOptionPane.showMessageDialog(tv, "Error al Guardar, Contacte a Soporte Técnico");
                    }else if (reg == 1){
                        if(tv.txtid.getText().isEmpty()){
                            tv.txtcod.setText("");
                            tv.txtnom.setText("");
                            tv.cmbest.setSelectedIndex(0);
                            tv.txtid.setText("");
                            tv.txtcod.requestFocus();
                            CargarTamanos();
                            JOptionPane.showMessageDialog(tv, "Registro Guardado");
                        }else{
                            tv.txtcod.setEnabled(false);
                            tv.txtnom.setEnabled(false);
                            tv.cmbest.setEnabled(false);
                            tv.btngua.setEnabled(false);
                            tv.btncan.setEnabled(false);
                            tv.btnnue.setEnabled(true);
                            tv.btnedi.setEnabled(true);
                            tv.btnsal.setEnabled(true);
                            tv.txtcod.setText("");
                            tv.txtnom.setText("");
                            tv.cmbest.setSelectedIndex(0);
                            tv.txtid.setText("");
                            tv.btnnue.requestFocus();
                            CargarTamanos();
                            JOptionPane.showMessageDialog(tv, "Registro Actualizado");
                        }
                        
                    }else{
                        JOptionPane.showMessageDialog(tv, "Error al Guardar, Contacte a Soporte Técnico");
                    }
                }
            }
        }else if(e.getSource() == tv.btncan){
            tv.txtcod.setEnabled(false);
            tv.txtnom.setEnabled(false);
            tv.cmbest.setEnabled(false);
            tv.btngua.setEnabled(false);
            tv.btncan.setEnabled(false);
            tv.btnnue.setEnabled(true);
            tv.btnedi.setEnabled(true);
            tv.btnsal.setEnabled(true);
            tv.txtcod.setText("");
            tv.txtnom.setText("");
            tv.cmbest.setSelectedIndex(0);
            tv.txtid.setText("");
            tv.btnnue.requestFocus();
        }else if(e.getSource() == tv.btnedi){
            fila_s = tv.tbllis.getSelectedRow();
            if(fila_s < 0){
                JOptionPane.showMessageDialog(tv, "Seleccione un Registro");
            }else{
                tv.txtid.setText(tv.tbllis.getValueAt(fila_s, 0).toString());
                tv.txtcod.setText(tv.tbllis.getValueAt(fila_s, 1).toString());
                tv.txtnom.setText(tv.tbllis.getValueAt(fila_s, 2).toString());
                tv.cmbest.setSelectedItem(tv.tbllis.getValueAt(fila_s, 3).toString());
                tv.txtcod.setEnabled(true);
                tv.txtnom.setEnabled(true);
                tv.cmbest.setEnabled(true);
                tv.btngua.setEnabled(true);
                tv.btncan.setEnabled(true);
                tv.btnnue.setEnabled(false);
                tv.btnedi.setEnabled(false);
                tv.btnsal.setEnabled(false);
                tv.txtcod.requestFocus();
           }
        }else if(e.getSource() == tv.btnsal){
            tv.dispose();
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
        filas = tv.tbllis.getRowCount();
        
        for (int i = 0; i < filas; i++) {
            if(!id.equals("")){
                if(fila_s == i){
                    
                }else{
                    if(tv.tbllis.getValueAt(i, 1).toString().equals(codigo)){
                        return 1;
                    }
                }
            }else{
                if(tv.tbllis.getValueAt(i, 1).toString().equals(codigo)){
                    return 1;
                }
            }
        }
        
        return 0;
    }
    
    public int GuardarTamanos(){
        int id = 0;
        String cod = null;
        String nom = null;
        int est = 0;
        
        if(!tv.txtid.getText().isEmpty()){
            id = Integer.parseInt(tv.txtid.getText());
        }
        
        cod = tv.txtcod.getText();
        nom = tv.txtnom.getText();
        
        if(tv.cmbest.getSelectedIndex() == 0){
            est = 1;
        }else if(tv.cmbest.getSelectedIndex() == 1){
            est = 0;
        }
        
        tm.setId(id);
        tm.setCon(cod);
        tm.setNom(nom);
        tm.setEst(est);
        
        if(tm.GuardarTamanos()){
            return 1;
        }
        return 0;
    }
    
    public void CargarTamanos(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        tm.setId(-1);
        modelt = tm.CargarTamanos();
        tv.tbllis.setModel(modelt);
        
        tv.tbllis.getColumnModel().getColumn(0).setMaxWidth(0);
        tv.tbllis.getColumnModel().getColumn(0).setMinWidth(0);
        tv.tbllis.getColumnModel().getColumn(0).setPreferredWidth(0);
        tv.tbllis.getColumnModel().getColumn(1).setMaxWidth(70);
        tv.tbllis.getColumnModel().getColumn(1).setMinWidth(70);
        tv.tbllis.getColumnModel().getColumn(1).setPreferredWidth(70);
        tv.tbllis.getColumnModel().getColumn(3).setMaxWidth(100);
        tv.tbllis.getColumnModel().getColumn(3).setMinWidth(100);
        tv.tbllis.getColumnModel().getColumn(3).setPreferredWidth(100);
    }
    
}
