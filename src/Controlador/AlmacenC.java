/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.AlmacenM;
import Vista.AlmacenV;
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
public class AlmacenC implements ActionListener, KeyListener {
    private AlmacenV av;
    private AlmacenM am;
    public int fila_s = 0;
    
    public AlmacenC(AlmacenM am, AlmacenV av) {
        this.av = av;
        this.am = am;
        this.av.btnnue.addActionListener(this);
        this.av.btngua.addActionListener(this);
        this.av.btncan.addActionListener(this);
        this.av.btnedi.addActionListener(this);
        this.av.btnsal.addActionListener(this);
    }

    public void iniciar() {
        av.setTitle("Registro Almacenes");
        ImageIcon originalIcon  = new ImageIcon(getClass().getResource("/img/FondoCopyCentro72ppp.png"));
        Image image = originalIcon.getImage();
        Image scaledImage = image.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        this.av.setFrameIcon(resizedIcon);
        av.tbllis.getTableHeader().setReorderingAllowed(false) ;
        av.tbllis.getTableHeader().setResizingAllowed(false);
        av.txtid.setVisible(false);
        CargarAlmacen();
        av.txtnom.setEnabled(false);
        av.cmbest.setEnabled(false);
        av.btngua.setEnabled(false);
        av.btncan.setEnabled(false);
        av.btnnue.requestFocus();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == av.btnnue){
            av.txtnom.setEnabled(true);
            av.cmbest.setEnabled(true);
            av.btngua.setEnabled(true);
            av.btncan.setEnabled(true);
            av.btnnue.setEnabled(false);
            av.btnedi.setEnabled(false);
            av.btnsal.setEnabled(false);
            av.txtnom.requestFocus();
        }else if(e.getSource() == av.btngua){
            if(av.txtnom.getText().isEmpty()){
                JOptionPane.showMessageDialog(av, "Campo Nombre Vacio");
                av.txtnom.requestFocus();
            }else{
                int existe = 0;
                existe = VerificarExistencia(av.txtnom.getText(), av.txtid.getText());
                if(existe == 1){//1 - SI EXISTE, 0 - NO EXISTE
                    JOptionPane.showMessageDialog(av, "El Nombre ya se encuentra registrado");
                }else{
                    int reg = 0;
                    reg = GuardarAlmacen();
                    if(reg <= 0){//0 - Error, 1 - Guardado
                        JOptionPane.showMessageDialog(av, "Error al Guardar, Contacte a Soporte Técnico");
                    }else if (reg == 1){
                        if(av.txtid.getText().isEmpty()){
                            av.txtnom.setText("");
                            av.cmbest.setSelectedIndex(0);
                            av.txtid.setText("");
                            av.txtnom.requestFocus();
                            CargarAlmacen();
                            JOptionPane.showMessageDialog(av, "Registro Guardado");
                        }else{
                            av.txtnom.setEnabled(false);
                            av.cmbest.setEnabled(false);
                            av.btngua.setEnabled(false);
                            av.btncan.setEnabled(false);
                            av.btnnue.setEnabled(true);
                            av.btnedi.setEnabled(true);
                            av.btnsal.setEnabled(true);
                            av.txtnom.setText("");
                            av.cmbest.setSelectedIndex(0);
                            av.txtid.setText("");
                            av.btnnue.requestFocus();
                            CargarAlmacen();
                            JOptionPane.showMessageDialog(av, "Registro Actualizado");
                        }
                        
                    }else{
                        JOptionPane.showMessageDialog(av, "Error al Guardar, Contacte a Soporte Técnico");
                    }
                }
            }
        }else if(e.getSource() == av.btncan){
            av.txtnom.setEnabled(false);
            av.cmbest.setEnabled(false);
            av.btngua.setEnabled(false);
            av.btncan.setEnabled(false);
            av.btnnue.setEnabled(true);
            av.btnedi.setEnabled(true);
            av.btnsal.setEnabled(true);
            av.txtnom.setText("");
            av.cmbest.setSelectedIndex(0);
            av.txtid.setText("");
            av.btnnue.requestFocus();
        }else if(e.getSource() == av.btnedi){
            fila_s = av.tbllis.getSelectedRow();
            if(fila_s < 0){
                JOptionPane.showMessageDialog(av, "Seleccione un Registro");
            }else{
                av.txtid.setText(av.tbllis.getValueAt(fila_s, 0).toString());
                av.txtnom.setText(av.tbllis.getValueAt(fila_s, 1).toString());
                av.cmbest.setSelectedItem(av.tbllis.getValueAt(fila_s, 2).toString());
                av.txtnom.setEnabled(true);
                av.cmbest.setEnabled(true);
                av.btngua.setEnabled(true);
                av.btncan.setEnabled(true);
                av.btnnue.setEnabled(false);
                av.btnedi.setEnabled(false);
                av.btnsal.setEnabled(false);
                av.txtnom.requestFocus();
           }
        }else if(e.getSource() == av.btnsal){
            av.dispose();
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
        filas = av.tbllis.getRowCount();
        
        for (int i = 0; i < filas; i++) {
            if(!id.equals("")){
                if(fila_s == i){
                    
                }else{
                    if(av.tbllis.getValueAt(i, 1).toString().equals(codigo)){
                        return 1;
                    }
                }
            }else{
                if(av.tbllis.getValueAt(i, 1).toString().equals(codigo)){
                    return 1;
                }
            }
        }
        
        return 0;
    }
    
    public int GuardarAlmacen(){
        int id = 0;
        String cod = null;
        String nom = null;
        int est = 0;
        
        if(!av.txtid.getText().isEmpty()){
            id = Integer.parseInt(av.txtid.getText());
        }
        
        cod = "0";
        nom = av.txtnom.getText();
        
        if(av.cmbest.getSelectedIndex() == 0){
            est = 1;
        }else if(av.cmbest.getSelectedIndex() == 1){
            est = 0;
        }
        
        am.setId(id);
        am.setNom(nom);
        am.setEst(est);
        
        if(am.GuardarAlmacen()){
            return 1;
        }
        return 0;
    }
    
    public void CargarAlmacen(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        am.setId(-1);
        am.setNom(null);
        am.setEst(0);
        modelt = am.CargarAlmacen();
        av.tbllis.setModel(modelt);
        
        av.tbllis.getColumnModel().getColumn(0).setMaxWidth(0);
        av.tbllis.getColumnModel().getColumn(0).setMinWidth(0);
        av.tbllis.getColumnModel().getColumn(0).setPreferredWidth(0);
        /*cpv.tbllis.getColumnModel().getColumn(1).setMaxWidth(100);
        cpv.tbllis.getColumnModel().getColumn(1).setMinWidth(100);
        cpv.tbllis.getColumnModel().getColumn(1).setPreferredWidth(100);*/
        av.tbllis.getColumnModel().getColumn(2).setMaxWidth(100);
        av.tbllis.getColumnModel().getColumn(2).setMinWidth(100);
        av.tbllis.getColumnModel().getColumn(2).setPreferredWidth(100);
    }
    
    
}
