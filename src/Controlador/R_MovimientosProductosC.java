/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.*;
import Vista.*;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Christian Sepulveda
 */
public class R_MovimientosProductosC implements ActionListener, KeyListener, PropertyChangeListener {
    private R_MovimientosProductosM rmpm;
    private R_MovimientosProductosV rmpv;
    private MovimientosAlmacenM mam = new MovimientosAlmacenM();
    DefaultTableModel modeltLisCV;//modelo Para cancelar venta
    DefaultTableCellRenderer centrar = new DefaultTableCellRenderer();
    
    public R_MovimientosProductosC(R_MovimientosProductosM rmpm, R_MovimientosProductosV rmpv) {
        this.rmpm = rmpm;
        this.rmpv = rmpv;
        this.rmpv.btnsalir.addActionListener(this);
        this.rmpv.btnbuscar.addActionListener(this);
        this.rmpv.btnexportar.addActionListener(this);
        this.rmpv.btnexportarpdf.addActionListener(this);
        this.rmpv.fechaDe.addPropertyChangeListener(this);
    }
    
    public void iniciar() {
        rmpv.setTitle("Reporte de movimientos de productos");
        java.util.Date tuFecha = new java.util.Date();
        rmpv.fechaDe.setDate(tuFecha);
        rmpv.fechaAl.setDate(tuFecha);
        JTextFieldDateEditor dateEditor_de = (JTextFieldDateEditor)rmpv.fechaDe.getDateEditor();
        dateEditor_de.setHorizontalAlignment(JTextField.CENTER);
        dateEditor_de.setEnabled(false);
        JTextFieldDateEditor dateEditor_al = (JTextFieldDateEditor)rmpv.fechaAl.getDateEditor();
        dateEditor_al.setHorizontalAlignment(JTextField.CENTER);
        dateEditor_al.setEnabled(false);
        rmpv.tbllis.getTableHeader().setReorderingAllowed(false);
        rmpv.tbllis.getTableHeader().setResizingAllowed(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == rmpv.btnsalir){
            rmpv.dispose();
        }else if(e.getSource() == rmpv.btnbuscar){
            CargarMovimientosProductos();
        }else if(e.getSource() == rmpv.btnexportar){
            int filas = rmpv.tbllis.getRowCount();
            if( filas > 0 ){
                ExportarExcel obj;
                try {
                    obj = new ExportarExcel();
                    obj.exportarExcel(rmpv.tbllis);
                }catch(IOException ex) {
                    System.out.println("Error: " + ex);
                }
            }else{
                JOptionPane.showMessageDialog(rmpv, "No tienes datos para exportar");
            }
        }else if(e.getSource() == rmpv.btnexportarpdf){
            int filas = rmpv.tbllis.getRowCount();
            if( filas > 0 ){
                String fecha_d = null;
                String fecha_a = null;
                Date fecha_de;
                Date fecha_al;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                fecha_de = rmpv.fechaDe.getDate();
                fecha_al = rmpv.fechaAl.getDate();
                fecha_d = sdf.format(fecha_de);
                fecha_a = sdf.format(fecha_al);

                // String url = "http://www.google.com";
                String url = "http://localhost/ReportesPDV_Papeleria/R_MovimientosProductos.php?a="+fecha_d+"&b="+fecha_a;
                try {
                    Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
                } catch (IOException ex) {
                    Logger.getLogger(R_VentasFechaRealizadoC.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                JOptionPane.showMessageDialog(rmpv, "No tienes datos para exportar");
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
        if(evt.getSource() == rmpv.fechaDe){
            rmpv.fechaAl.getJCalendar().setMinSelectableDate(rmpv.fechaDe.getDate());
            rmpv.fechaAl.setDate(rmpv.fechaDe.getDate());
        }
    }
    
    public void CargarMovimientosProductos(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        int id = -1;
        int id_producto = 0;
        int id_almacen = 0;
        int id_inventario = 0;
        int id_almacen_destino = 0;
        int cantidad = 0;
        int cantidad_inventario = 0;
        String fechaDe = null;
        String fechaAl = null;
        
        Date fecha_de;
        Date fecha_al;
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        fecha_de = rmpv.fechaDe.getDate();
        fecha_al = rmpv.fechaAl.getDate();
        fechaDe = sdf1.format(fecha_de)+" 00:00:00";
        fechaAl = sdf2.format(fecha_al)+" 23:59:59";
        
        mam.setId(id);
        mam.setId_producto(id_producto);
        mam.setId_almacen(id_almacen);
        mam.setId_inventario(id_inventario);
        mam.setId_almacen_destino(id_almacen_destino);
        mam.setCantidad(cantidad);
        mam.setCantidad_inventario(cantidad_inventario);
        mam.setFecha(fechaDe);
        mam.setFechaAl(fechaAl);
        
        modelt = mam.CargarMovimientosProductos();
        rmpv.tbllis.setModel(modelt);
        
    }
    
}
