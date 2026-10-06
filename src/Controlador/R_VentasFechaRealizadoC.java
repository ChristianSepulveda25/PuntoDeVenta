/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.*;
import Vista.*;
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Christian Sepulveda
 */
public class R_VentasFechaRealizadoC implements ActionListener, KeyListener, PropertyChangeListener {
    private R_VentasFechaRealizadoM rvcm;
    private R_VentasFechaRealizadoV rvcv;
    private VentasFrameM vm = new VentasFrameM();
    DefaultTableModel modeltLisCV;//modelo Para cancelar venta
    DefaultTableCellRenderer centrar = new DefaultTableCellRenderer();
    
    public R_VentasFechaRealizadoC(R_VentasFechaRealizadoM rvcm, R_VentasFechaRealizadoV rvcv) {
        this.rvcm = rvcm;
        this.rvcv = rvcv;
        this.rvcv.btnsalir.addActionListener(this);
        this.rvcv.btnbuscar.addActionListener(this);
        this.rvcv.btnexportar.addActionListener(this);
        this.rvcv.btnexportarpdf.addActionListener(this);
        this.rvcv.fechaDe.addPropertyChangeListener(this);
    }
    
    public void iniciar() {
        rvcv.setTitle("Reporte de ventas realizadas");
        ImageIcon originalIcon  = new ImageIcon(getClass().getResource("/img/FondoCopyCentro72ppp.png"));
        Image image = originalIcon.getImage();
        Image scaledImage = image.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        this.rvcv.setFrameIcon(resizedIcon);
        java.util.Date tuFecha = new java.util.Date();
        rvcv.fechaDe.setDate(tuFecha);
        rvcv.fechaAl.setDate(tuFecha);
        JTextFieldDateEditor dateEditor_de = (JTextFieldDateEditor)rvcv.fechaDe.getDateEditor();
        dateEditor_de.setHorizontalAlignment(JTextField.CENTER);
        dateEditor_de.setEnabled(false);
        JTextFieldDateEditor dateEditor_al = (JTextFieldDateEditor)rvcv.fechaAl.getDateEditor();
        dateEditor_al.setHorizontalAlignment(JTextField.CENTER);
        dateEditor_al.setEnabled(false);
        rvcv.tbllis_ven.getTableHeader().setReorderingAllowed(false);
        rvcv.tbllis_ven.getTableHeader().setResizingAllowed(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == rvcv.btnsalir){
            rvcv.dispose();
        }else if(e.getSource() == rvcv.btnbuscar){
            CargarVentasCanceladas();
        }else if(e.getSource() == rvcv.btnexportar){
            int filas = rvcv.tbllis_ven.getRowCount();
            if( filas > 0 ){
                ExportarExcel obj;
                try {
                    obj = new ExportarExcel();
                    obj.exportarExcel(rvcv.tbllis_ven);
                }catch(IOException ex) {
                    System.out.println("Error: " + ex);
                }
            }else{
                JOptionPane.showMessageDialog(rvcv, "No tienes datos para exportar");
            }
        }else if(e.getSource() == rvcv.btnexportarpdf){
            int filas = rvcv.tbllis_ven.getRowCount();
            if( filas > 0 ){
                String fecha_d = null;
                String fecha_a = null;
                Date fecha_de;
                Date fecha_al;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                fecha_de = rvcv.fechaDe.getDate();
                fecha_al = rvcv.fechaAl.getDate();
                fecha_d = sdf.format(fecha_de);
                fecha_a = sdf.format(fecha_al);

                // String url = "http://www.google.com";
                String url = "http://localhost/CopyCentro/Reportes/R_VentasRealizadas.php?a="+fecha_d+"&b="+fecha_a;
                try {
                    Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
                } catch (IOException ex) {
                    Logger.getLogger(R_VentasFechaRealizadoC.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                JOptionPane.showMessageDialog(rvcv, "No tienes datos para exportar");
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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getSource() == rvcv.fechaDe){
            rvcv.fechaAl.getJCalendar().setMinSelectableDate(rvcv.fechaDe.getDate());
            rvcv.fechaAl.setDate(rvcv.fechaDe.getDate());
        }
    }
    
    public void CargarVentasCanceladas(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        int id_ven = 0;
        String fol_ven = null;
        double tot_ven = 0;
        int can_ven = 0;
        int des_ven = 0;
        int met_pag = 0;
        int tip_ven = 0;
        String fec_reg = null;
        int sta_ven = 0;
        String ref_ven = null;
        double subtot_ven = 0;
        String fecha_can = null;
        
        id_ven = -6;
        tip_ven = 0;
        ref_ven = "1";
        Date fecha_de;
        Date fecha_al;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        fecha_de = rvcv.fechaDe.getDate();
        fecha_al = rvcv.fechaAl.getDate();
        fecha_can = sdf.format(fecha_de)+" 00:00:00";
        fec_reg = sdf.format(fecha_al)+" 23:59:59";
        
        
        vm.setId_ven(id_ven);
        vm.setFol_ven(fol_ven);
        vm.setTot_ven(tot_ven);
        vm.setCan_ven(can_ven);
        vm.setDes_ven(des_ven);
        vm.setMet_pag(met_pag);
        vm.setTip_ven(tip_ven);
        vm.setFec_reg(fec_reg);
        vm.setSta_ven(sta_ven);
        vm.setRef_ven(ref_ven);
        vm.setSubtot_ven(subtot_ven);
        vm.setFec_cancela(fecha_can);
        
        modeltLisCV = vm.CargarOrdenesServicio();
        
        rvcv.tbllis_ven.setModel(modeltLisCV);
        
        rvcv.tbllis_ven.getColumnModel().getColumn(0).setMaxWidth(0);
        rvcv.tbllis_ven.getColumnModel().getColumn(0).setMinWidth(0);
        rvcv.tbllis_ven.getColumnModel().getColumn(0).setPreferredWidth(0);
        centrar.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
        for (int i = 0; i <= 8; i++) {
            rvcv.tbllis_ven.getColumnModel().getColumn(i).setCellRenderer(centrar);
        }
        
    }
    
}
