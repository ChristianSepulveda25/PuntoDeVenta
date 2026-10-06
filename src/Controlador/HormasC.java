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
public class HormasC implements ActionListener, KeyListener {
    private HormasV hv;
    private HormasM hm;
    public int fila_s = 0;

    public HormasC(HormasM hm, HormasV hv) {
        this.hv = hv;
        this.hm = hm;
        this.hv.btnnue.addActionListener(this);
        this.hv.btngua.addActionListener(this);
        this.hv.btncan.addActionListener(this);
        this.hv.btnedi.addActionListener(this);
        this.hv.btnsal.addActionListener(this);
    }

    public void iniciar() {
        hv.setTitle("Registro de Hormas");
        hv.tbllis.getTableHeader().setReorderingAllowed(false) ;
        hv.tbllis.getTableHeader().setResizingAllowed(false);
        hv.txtid.setVisible(false);
        CargarHormas();
        hv.txtcod.setEnabled(false);
        hv.txtnom.setEnabled(false);
        hv.cmbest.setEnabled(false);
        hv.btngua.setEnabled(false);
        hv.btncan.setEnabled(false);
        hv.btnnue.requestFocus();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == hv.btnnue){
            hv.txtcod.setEnabled(true);
            hv.txtnom.setEnabled(true);
            hv.cmbest.setEnabled(true);
            hv.btngua.setEnabled(true);
            hv.btncan.setEnabled(true);
            hv.btnnue.setEnabled(false);
            hv.btnedi.setEnabled(false);
            hv.btnsal.setEnabled(false);
            hv.txtcod.requestFocus();
        }else if(e.getSource() == hv.btngua){
            if(hv.txtcod.getText().isEmpty()){
                JOptionPane.showMessageDialog(hv, "Campo Código Vacio");
                hv.txtcod.requestFocus();
            }else if(hv.txtnom.getText().isEmpty()){
                JOptionPane.showMessageDialog(hv, "Campo Nombre Vacio");
                hv.txtnom.requestFocus();
            }else{
                int existe = 0;
                existe = VerificarExistencia(hv.txtcod.getText(), hv.txtid.getText());
                if(existe == 1){//1 - SI EXISTE, 0 - NO EXISTE
                    JOptionPane.showMessageDialog(hv, "El Código Ya Se Encuentra Registrado");
                }else{
                    int reg = 0;
                    reg = GuardarHormas();
                    if(reg <= 0){//0 - Error, 1 - Guardado
                        JOptionPane.showMessageDialog(hv, "Error al Guardar, Contacte a Soporte Técnico");
                    }else if (reg == 1){
                        if(hv.txtid.getText().isEmpty()){
                            hv.txtcod.setText("");
                            hv.txtnom.setText("");
                            hv.cmbest.setSelectedIndex(0);
                            hv.txtid.setText("");
                            hv.txtcod.requestFocus();
                            CargarHormas();
                            JOptionPane.showMessageDialog(hv, "Registro Guardado");
                        }else{
                            hv.txtcod.setEnabled(false);
                            hv.txtnom.setEnabled(false);
                            hv.cmbest.setEnabled(false);
                            hv.btngua.setEnabled(false);
                            hv.btncan.setEnabled(false);
                            hv.btnnue.setEnabled(true);
                            hv.btnedi.setEnabled(true);
                            hv.btnsal.setEnabled(true);
                            hv.txtcod.setText("");
                            hv.txtnom.setText("");
                            hv.cmbest.setSelectedIndex(0);
                            hv.txtid.setText("");
                            hv.btnnue.requestFocus();
                            CargarHormas();
                            JOptionPane.showMessageDialog(hv, "Registro Actualizado");
                        }
                        
                    }else{
                        JOptionPane.showMessageDialog(hv, "Error al Guardar, Contacte a Soporte Técnico");
                    }
                }
            }
        }else if(e.getSource() == hv.btncan){
            hv.txtcod.setEnabled(false);
            hv.txtnom.setEnabled(false);
            hv.cmbest.setEnabled(false);
            hv.btngua.setEnabled(false);
            hv.btncan.setEnabled(false);
            hv.btnnue.setEnabled(true);
            hv.btnedi.setEnabled(true);
            hv.btnsal.setEnabled(true);
            hv.txtcod.setText("");
            hv.txtnom.setText("");
            hv.cmbest.setSelectedIndex(0);
            hv.txtid.setText("");
            hv.btnnue.requestFocus();
        }else if(e.getSource() == hv.btnedi){
            fila_s = hv.tbllis.getSelectedRow();
            if(fila_s < 0){
                JOptionPane.showMessageDialog(hv, "Seleccione un Registro");
            }else{
                hv.txtid.setText(hv.tbllis.getValueAt(fila_s, 0).toString());
                hv.txtcod.setText(hv.tbllis.getValueAt(fila_s, 1).toString());
                hv.txtnom.setText(hv.tbllis.getValueAt(fila_s, 2).toString());
                hv.cmbest.setSelectedItem(hv.tbllis.getValueAt(fila_s, 3).toString());
                hv.txtcod.setEnabled(true);
                hv.txtnom.setEnabled(true);
                hv.cmbest.setEnabled(true);
                hv.btngua.setEnabled(true);
                hv.btncan.setEnabled(true);
                hv.btnnue.setEnabled(false);
                hv.btnedi.setEnabled(false);
                hv.btnsal.setEnabled(false);
                hv.txtcod.requestFocus();
           }
        }else if(e.getSource() == hv.btnsal){
            hv.dispose();
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
        filas = hv.tbllis.getRowCount();
        
        for (int i = 0; i < filas; i++) {
            if(!id.equals("")){
                if(fila_s == i){
                    
                }else{
                    if(hv.tbllis.getValueAt(i, 1).toString().equals(codigo)){
                        return 1;
                    }
                }
            }else{
                if(hv.tbllis.getValueAt(i, 1).toString().equals(codigo)){
                    return 1;
                }
            }
        }
        
        return 0;
    }
    
    public int GuardarHormas(){
        int id = 0;
        String cod = null;
        String nom = null;
        int est = 0;
        
        if(!hv.txtid.getText().isEmpty()){
            id = Integer.parseInt(hv.txtid.getText());
        }
        
        cod = hv.txtcod.getText();
        nom = hv.txtnom.getText();
        
        if(hv.cmbest.getSelectedIndex() == 0){
            est = 1;
        }else if(hv.cmbest.getSelectedIndex() == 1){
            est = 0;
        }
        
        hm.setId(id);
        hm.setCon(cod);
        hm.setNom(nom);
        hm.setEst(est);
        
        if(hm.GuardarHormas()){
            return 1;
        }
        return 0;
    }
    
    public void CargarHormas(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        hm.setId(-1);
        hm.setCon(null);
        hm.setNom(null);
        hm.setEst(0);
        modelt = hm.CargarHormas();
        hv.tbllis.setModel(modelt);
        
        hv.tbllis.getColumnModel().getColumn(0).setMaxWidth(0);
        hv.tbllis.getColumnModel().getColumn(0).setMinWidth(0);
        hv.tbllis.getColumnModel().getColumn(0).setPreferredWidth(0);
        hv.tbllis.getColumnModel().getColumn(1).setMaxWidth(70);
        hv.tbllis.getColumnModel().getColumn(1).setMinWidth(70);
        hv.tbllis.getColumnModel().getColumn(1).setPreferredWidth(70);
        hv.tbllis.getColumnModel().getColumn(3).setMaxWidth(100);
        hv.tbllis.getColumnModel().getColumn(3).setMinWidth(100);
        hv.tbllis.getColumnModel().getColumn(3).setPreferredWidth(100);
    }
    
}
