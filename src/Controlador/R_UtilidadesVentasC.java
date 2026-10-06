/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.*;
import Modelo.*;
import Vista.*;
import Vista.R_VentasFechaRealizadoV;
import com.toedter.calendar.JTextFieldDateEditor;
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
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ChristianSepulveda
 */
public class R_UtilidadesVentasC implements ActionListener, KeyListener, PropertyChangeListener {
    private R_UtilidadesVentasM ruvm;
    private R_UtilidadesVentasV ruvv;
    private VentasM vm = new VentasM();
    DefaultTableModel modeltLisCV;//modelo Para cancelar venta
    DefaultTableCellRenderer centrar = new DefaultTableCellRenderer();
    
    public R_UtilidadesVentasC(R_UtilidadesVentasM ruvm, R_UtilidadesVentasV ruvv) {
        this.ruvm = ruvm;
        this.ruvv = ruvv;
        this.ruvv.btnsalir.addActionListener(this);
        this.ruvv.btnbuscar.addActionListener(this);
        this.ruvv.btnexportar.addActionListener(this);
        this.ruvv.btnexportarpdf.addActionListener(this);
        this.ruvv.fechaDe.addPropertyChangeListener(this);
    }
    
    public void iniciar() {
        ruvv.setTitle("Reporte de utilidades de ventas");
        java.util.Date tuFecha = new java.util.Date();
        ruvv.fechaDe.setDate(tuFecha);
        ruvv.fechaAl.setDate(tuFecha);
        JTextFieldDateEditor dateEditor_de = (JTextFieldDateEditor)ruvv.fechaDe.getDateEditor();
        dateEditor_de.setHorizontalAlignment(JTextField.CENTER);
        dateEditor_de.setEnabled(false);
        JTextFieldDateEditor dateEditor_al = (JTextFieldDateEditor)ruvv.fechaAl.getDateEditor();
        dateEditor_al.setHorizontalAlignment(JTextField.CENTER);
        dateEditor_al.setEnabled(false);
        ruvv.tbllis_ven.getTableHeader().setReorderingAllowed(false);
        ruvv.tbllis_ven.getTableHeader().setResizingAllowed(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == ruvv.btnsalir){
            ruvv.dispose();
        }else if(e.getSource() == ruvv.btnbuscar){
            CargarDetalleVentas();
        }else if(e.getSource() == ruvv.btnexportar){
            int filas = ruvv.tbllis_ven.getRowCount();
            if( filas > 0 ){
                ExportarExcel obj;
                try {
                    obj = new ExportarExcel();
                    obj.exportarExcel(ruvv.tbllis_ven);
                }catch(IOException ex) {
                    System.out.println("Error: " + ex);
                }
            }else{
                JOptionPane.showMessageDialog(ruvv, "No tienes datos para exportar");
            }
        }else if(e.getSource() == ruvv.btnexportarpdf){
            int filas = ruvv.tbllis_ven.getRowCount();
            if( filas > 0 ){
                String fecha_d = null;
                String fecha_a = null;
                Date fecha_de;
                Date fecha_al;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                fecha_de = ruvv.fechaDe.getDate();
                fecha_al = ruvv.fechaAl.getDate();
                fecha_d = sdf.format(fecha_de);
                fecha_a = sdf.format(fecha_al);

                // String url = "http://www.google.com";
                String url = "http://localhost/ReportesPDV_Papeleria/R_UtilidadesVentas.php?a="+fecha_d+"&b="+fecha_a;
                try {
                    Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
                } catch (IOException ex) {
                    Logger.getLogger(R_VentasFechaRealizadoC.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                JOptionPane.showMessageDialog(ruvv, "No tienes datos para exportar");
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
        if(evt.getSource() == ruvv.fechaDe){
            ruvv.fechaAl.getJCalendar().setMinSelectableDate(ruvv.fechaDe.getDate());
            ruvv.fechaAl.setDate(ruvv.fechaDe.getDate());
        }
    }
    
    public void CargarDetalleVentas(){
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
        
        id_ven = -11;
        tip_ven = 0;
        ref_ven = "1";
        Date fecha_de;
        Date fecha_al;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        fecha_de = ruvv.fechaDe.getDate();
        fecha_al = ruvv.fechaAl.getDate();
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
        
        ruvv.tbllis_ven.setModel(modeltLisCV);
        
        /*ruvv.tbllis_ven.getColumnModel().getColumn(0).setMaxWidth(0);
        ruvv.tbllis_ven.getColumnModel().getColumn(0).setMinWidth(0);
        ruvv.tbllis_ven.getColumnModel().getColumn(0).setPreferredWidth(0);*/
        centrar.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
        for (int i = 0; i <= 10; i++) {
            ruvv.tbllis_ven.getColumnModel().getColumn(i).setCellRenderer(centrar);
        }
        
    }
    
}
