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
public class R_EntradaSalidaProductosC implements ActionListener, KeyListener, PropertyChangeListener {
    private R_EntradaSalidaProductosM respm;
    private R_EntradaSalidaProductosV respv;
    private ESProductoM espm = new ESProductoM();
    private VentasM vm = new VentasM();
    DefaultTableModel modeltLisCV;//modelo Para cancelar venta
    DefaultTableCellRenderer centrar = new DefaultTableCellRenderer();
    
    public R_EntradaSalidaProductosC(R_EntradaSalidaProductosM respm, R_EntradaSalidaProductosV respv) {
        this.respm = respm;
        this.respv = respv;
        this.respv.btnsalir.addActionListener(this);
        this.respv.btnbuscar.addActionListener(this);
        this.respv.btnexportar.addActionListener(this);
        this.respv.btnexportarpdf.addActionListener(this);
        this.respv.fechaDe.addPropertyChangeListener(this);
    }
    
    public void iniciar() {
        respv.setTitle("Reporte de entradas y salidas de productos");
        ImageIcon originalIcon  = new ImageIcon(getClass().getResource("/img/FondoCopyCentro72ppp.png"));
        Image image = originalIcon.getImage();
        Image scaledImage = image.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        this.respv.setFrameIcon(resizedIcon);
        java.util.Date tuFecha = new java.util.Date();
        respv.fechaDe.setDate(tuFecha);
        respv.fechaAl.setDate(tuFecha);
        JTextFieldDateEditor dateEditor_de = (JTextFieldDateEditor)respv.fechaDe.getDateEditor();
        dateEditor_de.setHorizontalAlignment(JTextField.CENTER);
        dateEditor_de.setEnabled(false);
        JTextFieldDateEditor dateEditor_al = (JTextFieldDateEditor)respv.fechaAl.getDateEditor();
        dateEditor_al.setHorizontalAlignment(JTextField.CENTER);
        dateEditor_al.setEnabled(false);
        respv.tbllis.getTableHeader().setReorderingAllowed(false);
        // respv.tbllis.getTableHeader().setResizingAllowed(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == respv.btnsalir){
            respv.dispose();
        }else if(e.getSource() == respv.btnbuscar){
            int cmbTipo = 0;
            cmbTipo = respv.cmbtipo.getSelectedIndex();
            if( cmbTipo > 0 ){
                CargarESProductos();
            }else{
                JOptionPane.showMessageDialog(respv, "Seleccione el tipo de movimiento");
            }
        }else if(e.getSource() == respv.btnexportar){
            int filas = respv.tbllis.getRowCount();
            if( filas > 0 ){
                ExportarExcel obj;
                try {
                    obj = new ExportarExcel();
                    obj.exportarExcel(respv.tbllis);
                }catch(IOException ex) {
                    System.out.println("Error: " + ex);
                }
            }else{
                JOptionPane.showMessageDialog(respv, "No tienes datos para exportar");
            }
        }else if(e.getSource() == respv.btnexportarpdf){
            int filas = respv.tbllis.getRowCount();
            if( filas > 0 ){
                int pro = 0;
                int est = 0;
                if( respv.cmbtipo.getSelectedIndex() == 1 ){
                    pro = 0;
                    est = 0;
                }else if( respv.cmbtipo.getSelectedIndex() == 2 ){
                    pro = 1;
                    est = 1;
                }else if( respv.cmbtipo.getSelectedIndex() == 3 ){
                    pro = 0;
                    est = 1;
                }

                String fecha_d = null;
                String fecha_a = null;
                Date fecha_de;
                Date fecha_al;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                fecha_de = respv.fechaDe.getDate();
                fecha_al = respv.fechaAl.getDate();
                fecha_d = sdf.format(fecha_de);
                fecha_a = sdf.format(fecha_al);

                // String url = "http://www.google.com";
                String url = "http://localhost/CopyCentro/Reportes/R_EntradaSalidaProductos.php?a="+fecha_d+"&b="+fecha_a+"&c="+pro+"&d="+est;
                try {
                    Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
                } catch (IOException ex) {
                    Logger.getLogger(R_VentasFechaRealizadoC.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                JOptionPane.showMessageDialog(respv, "No tienes datos para exportar");
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
        if(evt.getSource() == respv.fechaDe){
            respv.fechaAl.getJCalendar().setMinSelectableDate(respv.fechaDe.getDate());
            respv.fechaAl.setDate(respv.fechaDe.getDate());
        }
    }
    
    public void CargarESProductos(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        int id = 0;
        String con = null;
        int can = 0;
        int tip = 0;
        String fec = null;
        int est = 0;
        int pro = 0;
        int tot = 0;
        int can_ant = 0;
        
        id = -1;
        con = "";
        can = 0;
        
        int cambTipoIndex = 0;
        cambTipoIndex = respv.cmbtipo.getSelectedIndex();
        /*if( cambTipoIndex == 1 ){
            tip = 0;
        }else if( cambTipoIndex == 2 ){
            tip = 1;
        }else if( cambTipoIndex == 3 ){
            tip = 0;
        }*/
        
        Date fecha_de;
        Date fecha_al;
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        fecha_de = respv.fechaDe.getDate();
        fecha_al = respv.fechaAl.getDate();
        fec = sdf1.format(fecha_de)+" 00:00:00";
        con = sdf2.format(fecha_al)+" 23:59:59";
        
        // 1 Entrada
        // 2 Salidas
        // 0 Todos
        pro = 0;
        est = 0;
        if( respv.cmbtipo.getSelectedIndex() == 1 ){
            pro = 0;
            est = 0;
        }else if( respv.cmbtipo.getSelectedIndex() == 2 ){
            pro = 1;
            est = 1;
        }else if( respv.cmbtipo.getSelectedIndex() == 3 ){
            pro = 0;
            est = 1;
        }
        
        tot = 0;
        can_ant = 0;
        
        espm.setId(id);
        espm.setCon(con);
        espm.setCan(can);
        espm.setTip(tip);
        espm.setFec(fec);
        espm.setEst(est);
        espm.setId_pro(pro);
        espm.setTot(tot);
        espm.setCant_ant(can_ant);
        espm.setId_alm(0);
        
        modelt = espm.CargarESProducto();
        respv.tbllis.setModel(modelt);
        
        //respv.tbllis.getColumnModel().getColumn(0).setMaxWidth(75);
        //respv.tbllis.getColumnModel().getColumn(0).setMinWidth(75);
        respv.tbllis.getColumnModel().getColumn(0).setPreferredWidth(75);
        //respv.tbllis.getColumnModel().getColumn(1).setMaxWidth(75);
        //respv.tbllis.getColumnModel().getColumn(1).setMinWidth(75);
        respv.tbllis.getColumnModel().getColumn(1).setPreferredWidth(75);
        //respv.tbllis.getColumnModel().getColumn(2).setMaxWidth(75);
        //respv.tbllis.getColumnModel().getColumn(2).setMinWidth(75);
        respv.tbllis.getColumnModel().getColumn(2).setPreferredWidth(75);
        //respv.tbllis.getColumnModel().getColumn(4).setMaxWidth(80);
        //respv.tbllis.getColumnModel().getColumn(4).setMinWidth(80);
        respv.tbllis.getColumnModel().getColumn(4).setPreferredWidth(80);
        //respv.tbllis.getColumnModel().getColumn(5).setMaxWidth(150);
        //respv.tbllis.getColumnModel().getColumn(5).setMinWidth(150);
        respv.tbllis.getColumnModel().getColumn(5).setPreferredWidth(150);
        centrar.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
        for (int i = 0; i <= 5; i++) {
            respv.tbllis.getColumnModel().getColumn(i).setCellRenderer(centrar);
        }
    }
    
}
