/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ProveedoresM;
import Vista.ProveedoresV;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Christian Sepulveda
 */
public class ProveedoresC implements ActionListener, KeyListener {
    private ProveedoresM pm;
    private ProveedoresV pv;
    DefaultTableCellRenderer centrar = new DefaultTableCellRenderer();

    public ProveedoresC(ProveedoresM prm, ProveedoresV prv) {
        this.pm = prm;
        this.pv = prv;
        this.pv.btnnuevo.addActionListener(this);
        this.pv.btnguardar.addActionListener(this);
        this.pv.btncancelar.addActionListener(this);
        this.pv.btneditar.addActionListener(this);
        this.pv.btnsalir.addActionListener(this);
    }

    public void iniciar() {
        pv.setTitle("Catálogo de Proveedores");
        bloquear();
        CargarProveedores();
        pv.tbllis.getTableHeader().setReorderingAllowed(false);
        pv.tbllis.getTableHeader().setResizingAllowed(false);
        pv.txtid.setVisible(false);
        pv.txtbuscar.requestFocus();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == pv.btnnuevo){
            desbloquear();
            pv.txtnombre.requestFocus();
        }else if(e.getSource() == pv.btnguardar){
            if(pv.txtnombre.getText().isEmpty()){
                JOptionPane.showMessageDialog(pv, "Campo Nombre Vacio");
                pv.txtnombre.requestFocus();
            }else{
                int a = 0;
                a = GuardarProveedor();
                if(a == 0){
                    JOptionPane.showMessageDialog(pv, "Error al Guardar, Conctacte a Soporte Técnico!!!");
                }else{
                    CargarProveedores();
                    if(pv.txtid.getText().isEmpty()){
                        limpiar();
                        JOptionPane.showMessageDialog(pv, "Registro Guardado");
                        pv.txtnombre.requestFocus();
                    }else{
                        bloquear();
                        limpiar();
                        JOptionPane.showMessageDialog(pv, "Registro Actualizado");
                        pv.txtbuscar.requestFocus();
                    }
                }
            }
        }else if(e.getSource() == pv.btncancelar){
            bloquear();
            pv.txtbuscar.requestFocus();
        }else if(e.getSource() == pv.btnsalir){
            pv.dispose();
        }else if(e.getSource() == pv.btneditar){
            int fila = 0;
            fila = pv.tbllis.getSelectedRow();
            if(fila < 0){
                JOptionPane.showMessageDialog(pv, "Seleccione una Fila");
            }else{
                pv.txtid.setText(pv.tbllis.getValueAt(fila, 0).toString());
                pv.txtnombre.setText(pv.tbllis.getValueAt(fila, 1).toString());
                if(pv.tbllis.getValueAt(fila, 2)==null){
                    pv.txtcalle.setText("");
                }else{
                    pv.txtcalle.setText(pv.tbllis.getValueAt(fila, 2).toString());
                }
                if(pv.tbllis.getValueAt(fila, 3)==null){
                    pv.txtnumero.setText("");
                }else{
                    pv.txtnumero.setText(pv.tbllis.getValueAt(fila, 3).toString());
                }
                if(pv.tbllis.getValueAt(fila, 4)==null){
                    pv.txtletra.setText("");
                }else{
                    pv.txtletra.setText(pv.tbllis.getValueAt(fila, 4).toString());
                }
                if(pv.tbllis.getValueAt(fila, 5)==null){
                    pv.txtinterior.setText("");
                }else{
                    pv.txtinterior.setText(pv.tbllis.getValueAt(fila, 5).toString());
                }
                if(pv.tbllis.getValueAt(fila, 6)==null){
                    pv.txtcolonia.setText("");
                }else{
                    pv.txtcolonia.setText(pv.tbllis.getValueAt(fila, 6).toString());
                }
                if(pv.tbllis.getValueAt(fila, 7)==null){
                    pv.txtentre.setText("");
                }else{
                    pv.txtentre.setText(pv.tbllis.getValueAt(fila, 7).toString());
                }
                if(pv.tbllis.getValueAt(fila, 8)==null){
                    pv.txtcp.setText("");
                }else{
                    pv.txtcp.setText(pv.tbllis.getValueAt(fila, 8).toString());
                }
                if(pv.tbllis.getValueAt(fila, 9)==null){
                    pv.txttelefono.setText("");
                }else{
                    pv.txttelefono.setText(pv.tbllis.getValueAt(fila, 9).toString());
                }
                if(pv.tbllis.getValueAt(fila, 10)==null){
                    pv.txtcontacto.setText("");
                }else{
                    pv.txtcontacto.setText(pv.tbllis.getValueAt(fila, 10).toString());
                }
                if(pv.tbllis.getValueAt(fila, 11)==null){
                    pv.txttelcontacto.setText("");
                }else{
                    pv.txttelcontacto.setText(pv.tbllis.getValueAt(fila, 11).toString());
                }
                pv.cmbestatus.setSelectedItem(pv.tbllis.getValueAt(fila, 12).toString());
                
                desbloquear();
                pv.txtnombre.requestFocus();
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
        pv.txtnombre.setEnabled(false);
        pv.txtcalle.setEnabled(false);
        pv.txtnumero.setEnabled(false);
        pv.txtletra.setEnabled(false);
        pv.txtinterior.setEnabled(false);
        pv.txtcolonia.setEnabled(false);
        pv.txtentre.setEnabled(false);
        pv.txtcp.setEnabled(false);
        pv.txttelefono.setEnabled(false);
        pv.txtcontacto.setEnabled(false);
        pv.txttelcontacto.setEnabled(false);
        pv.cmbestatus.setEnabled(false);
        pv.txtbuscar.setEnabled(true);
        pv.btnnuevo.setEnabled(true);
        pv.btncancelar.setEnabled(false);
        pv.btnguardar.setEnabled(false);
        pv.btneditar.setEnabled(true);
        pv.btnsalir.setEnabled(true);
        ////////////////////////////////////////
        pv.txtnombre.setText("");
        pv.txtcalle.setText("");
        pv.txtnumero.setText("");
        pv.txtletra.setText("");
        pv.txtinterior.setText("");
        pv.txtcolonia.setText("");
        pv.txtentre.setText("");
        pv.txtcp.setText("");
        pv.txttelefono.setText("");
        pv.txtcontacto.setText("");
        pv.txttelcontacto.setText("");
        pv.txtid.setText("");
        pv.cmbestatus.setSelectedIndex(0);
    }
    
    public void desbloquear(){
        pv.txtnombre.setEnabled(true);
        pv.txtcalle.setEnabled(true);
        pv.txtnumero.setEnabled(true);
        pv.txtletra.setEnabled(true);
        pv.txtinterior.setEnabled(true);
        pv.txtcolonia.setEnabled(true);
        pv.txtentre.setEnabled(true);
        pv.txtcp.setEnabled(true);
        pv.txttelefono.setEnabled(true);
        pv.txtcontacto.setEnabled(true);
        pv.txttelcontacto.setEnabled(true);
        pv.cmbestatus.setEnabled(true);
        pv.txtbuscar.setEnabled(false);
        pv.btnnuevo.setEnabled(false);
        pv.btncancelar.setEnabled(true);
        pv.btnguardar.setEnabled(true);
        pv.btneditar.setEnabled(false);
        pv.btnsalir.setEnabled(false);
    }
    
    public void limpiar(){
        pv.txtnombre.setText("");
        pv.txtcalle.setText("");
        pv.txtnumero.setText("");
        pv.txtletra.setText("");
        pv.txtinterior.setText("");
        pv.txtcolonia.setText("");
        pv.txtentre.setText("");
        pv.txtcp.setText("");
        pv.txttelefono.setText("");
        pv.txtcontacto.setText("");
        pv.txttelcontacto.setText("");
        pv.txtid.setText("");
        pv.cmbestatus.setSelectedIndex(0);
    }
    
    public int GuardarProveedor(){
        int id_pro = 0;
        String nom_pro = null;
        String cal_pro = null;
        int no_pro = 0;
        String let_pro = null;
        int int_pro = 0;
        String col_pro = null;
        String ent_pro = null;
        String cp_pro = null;
        String tel_pro = null;
        String con_pro = null;
        String tel_con_pro = null;
        int sta_pro = 0;
        
        if(pv.txtid.getText().isEmpty()){
            id_pro = 0;
        }else{
            id_pro = Integer.parseInt(pv.txtid.getText());
        }
        nom_pro = pv.txtnombre.getText();
        if(pv.txtcalle.getText().isEmpty()){
            cal_pro = null;
        }else{
            cal_pro = pv.txtcalle.getText();
        }
        if(pv.txtnumero.getText().isEmpty()){
            no_pro = 0;
        }else{
            no_pro = Integer.parseInt(pv.txtnumero.getText());
        }
        if(pv.txtletra.getText().isEmpty()){
            let_pro = null;
        }else{
            let_pro = pv.txtletra.getText();
        }
        if(pv.txtinterior.getText().isEmpty()){
            int_pro = 0;
        }else{
            int_pro = Integer.parseInt(pv.txtinterior.getText());
        }
        if(pv.txtcolonia.getText().isEmpty()){
            col_pro = null;
        }else{
            col_pro = pv.txtcolonia.getText();
        }
        if(pv.txtentre.getText().isEmpty()){
            ent_pro = null;
        }else{
            ent_pro = pv.txtentre.getText();
        }
        if(pv.txtcp.getText().isEmpty()){
            cp_pro = null;
        }else{
            cp_pro = pv.txtcp.getText();
        }
        if(pv.txttelefono.getText().isEmpty()){
            tel_pro = null;
        }else{
            tel_pro = pv.txttelefono.getText();
        }
        if(pv.txtcontacto.getText().isEmpty()){
            con_pro = null;
        }else{
            con_pro = pv.txtcontacto.getText();
        }
        if(pv.txttelcontacto.getText().isEmpty()){
            tel_con_pro = null;
        }else{
            tel_con_pro = pv.txttelcontacto.getText();
        }
        if(pv.cmbestatus.getSelectedIndex() == 0){
            sta_pro = 1;
        }else{
            sta_pro = 0;
        }
        
        pm.setId_pro(id_pro);
        pm.setNom_pro(nom_pro);
        pm.setCal_pro(cal_pro);
        pm.setNo_pro(no_pro);
        pm.setLet_pro(let_pro);
        pm.setInt_pro(int_pro);
        pm.setCol_pro(col_pro);
        pm.setEnt_pro(ent_pro);
        pm.setCp_pro(cp_pro);
        pm.setTel_pro(tel_pro);
        pm.setCon_pro(con_pro);
        pm.setTel_con_pro(tel_con_pro);
        pm.setSta_pro(sta_pro);
        
        if( pm.GuardarProveedor() ){
            return 1;
        }
        return 0;
    }
    
    public void CargarProveedores(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        pm.setId_pro(-1);
        modelt = pm.CargarProveedor();
        pv.tbllis.setModel(modelt);
        
        pv.tbllis.getColumnModel().getColumn(0).setMaxWidth(0);
        pv.tbllis.getColumnModel().getColumn(0).setMinWidth(0);
        pv.tbllis.getColumnModel().getColumn(0).setPreferredWidth(0);
        pv.tbllis.getColumnModel().getColumn(2).setMaxWidth(0);
        pv.tbllis.getColumnModel().getColumn(2).setMinWidth(0);
        pv.tbllis.getColumnModel().getColumn(2).setPreferredWidth(0);
        pv.tbllis.getColumnModel().getColumn(3).setMaxWidth(0);
        pv.tbllis.getColumnModel().getColumn(3).setMinWidth(0);
        pv.tbllis.getColumnModel().getColumn(3).setPreferredWidth(0);
        pv.tbllis.getColumnModel().getColumn(4).setMaxWidth(0);
        pv.tbllis.getColumnModel().getColumn(4).setMinWidth(0);
        pv.tbllis.getColumnModel().getColumn(4).setPreferredWidth(0);
        pv.tbllis.getColumnModel().getColumn(5).setMaxWidth(0);
        pv.tbllis.getColumnModel().getColumn(5).setMinWidth(0);
        pv.tbllis.getColumnModel().getColumn(5).setPreferredWidth(0);
        pv.tbllis.getColumnModel().getColumn(6).setMaxWidth(0);
        pv.tbllis.getColumnModel().getColumn(6).setMinWidth(0);
        pv.tbllis.getColumnModel().getColumn(6).setPreferredWidth(0);
        pv.tbllis.getColumnModel().getColumn(7).setMaxWidth(0);
        pv.tbllis.getColumnModel().getColumn(7).setMinWidth(0);
        pv.tbllis.getColumnModel().getColumn(7).setPreferredWidth(0);
        pv.tbllis.getColumnModel().getColumn(8).setMaxWidth(0);
        pv.tbllis.getColumnModel().getColumn(8).setMinWidth(0);
        pv.tbllis.getColumnModel().getColumn(8).setPreferredWidth(0);
        centrar.setHorizontalAlignment(SwingConstants.CENTER);
        pv.tbllis.getColumnModel().getColumn(1).setCellRenderer(centrar);
        pv.tbllis.getColumnModel().getColumn(9).setCellRenderer(centrar);
        pv.tbllis.getColumnModel().getColumn(10).setCellRenderer(centrar);
        pv.tbllis.getColumnModel().getColumn(11).setCellRenderer(centrar);
        pv.tbllis.getColumnModel().getColumn(12).setCellRenderer(centrar);
    }
    
}
