/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.*;
import Vista.*;
import com.toedter.calendar.JTextFieldDateEditor;
import innotpv.Ticket;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.text.ParseException;
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
public class ESEfectivoC implements ActionListener, KeyListener {
    private ESEfectivoV esev;
    private ESEfectivoM esem;
    public ProveedoresM pm = new ProveedoresM();
    public EmpresaM em = new EmpresaM();
    DefaultTableCellRenderer centrar = new DefaultTableCellRenderer();

    public ESEfectivoC(ESEfectivoM esem, ESEfectivoV esev) {
        this.esem = esem;
        this.esev = esev;
        this.esev.btnsalir.addActionListener(this);
        this.esev.btncancelar.addActionListener(this);
        this.esev.btnnuevo.addActionListener(this);
        this.esev.btnguardar.addActionListener(this);
        this.esev.btnbuscar.addActionListener(this);
        this.esev.btnexportar.addActionListener(this);
        this.esev.btnReimprimir.addActionListener(this);
    }

    public void iniciar() {
        esev.setTitle("Entrada y Salida de Efectivo");
        ImageIcon originalIcon  = new ImageIcon(getClass().getResource("/img/FondoCopyCentro72ppp.png"));
        Image image = originalIcon.getImage();
        Image scaledImage = image.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        this.esev.setFrameIcon(resizedIcon);
        esev.cmbtipo.setEnabled(false);
        esev.txtcantidad.setEnabled(false);
        esev.txtmotivo.setEnabled(false);
        esev.btnguardar.setEnabled(false);
        esev.btncancelar.setEnabled(false);
        java.util.Date tuFecha = new java.util.Date();
        esev.dtc_decha_de.setDate(tuFecha);
        esev.dtc_decha_al.setDate(tuFecha);
        JTextFieldDateEditor dateEditor_de = (JTextFieldDateEditor)esev.dtc_decha_de.getDateEditor();
        dateEditor_de.setHorizontalAlignment(JTextField.CENTER);
        dateEditor_de.setEnabled(false);
        JTextFieldDateEditor dateEditor_al = (JTextFieldDateEditor)esev.dtc_decha_al.getDateEditor();
        dateEditor_al.setHorizontalAlignment(JTextField.CENTER);
        dateEditor_al.setEnabled(false);
        esev.tbllis.getTableHeader().setReorderingAllowed(false) ;
        esev.tbllis.getTableHeader().setResizingAllowed(false); 
        CargarESEfectivo();
        esev.btnnuevo.requestFocus();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == esev.btnsalir){
            esev.dispose();
        }else if(e.getSource() == esev.btnnuevo){
            esev.cmbtipo.setEnabled(true);
            esev.txtcantidad.setEnabled(true);
            esev.txtmotivo.setEnabled(true);
            esev.btnguardar.setEnabled(true);
            esev.btncancelar.setEnabled(true);
            esev.btnnuevo.setEnabled(false);
            esev.dtc_decha_al.setEnabled(false);
            esev.dtc_decha_de.setEnabled(false);
            esev.btnbuscar.setEnabled(false);
            esev.btnsalir.setEnabled(false);
            // CargarPrveedores();
            esev.cmbtipo.requestFocus();
        }else if(e.getSource() == esev.btncancelar){
            esev.cmbtipo.setEnabled(false);
            esev.txtcantidad.setEnabled(false);
            esev.txtmotivo.setEnabled(false);
            esev.btnguardar.setEnabled(false);
            esev.btncancelar.setEnabled(false);
            esev.btnnuevo.setEnabled(true);
            esev.dtc_decha_al.setEnabled(true);
            esev.dtc_decha_de.setEnabled(true);
            JTextFieldDateEditor dateEditor_de = (JTextFieldDateEditor)esev.dtc_decha_de.getDateEditor();
            dateEditor_de.setHorizontalAlignment(JTextField.CENTER);
            dateEditor_de.setEnabled(false);
            JTextFieldDateEditor dateEditor_al = (JTextFieldDateEditor)esev.dtc_decha_al.getDateEditor();
            dateEditor_al.setHorizontalAlignment(JTextField.CENTER);
            dateEditor_al.setEnabled(false);
            esev.btnbuscar.setEnabled(true);
            esev.btnsalir.setEnabled(true);
            esev.cmbtipo.setSelectedIndex(0);
            esev.txtcantidad.setText("");
            esev.txtmotivo.setText("");
            esev.btnnuevo.requestFocus();
        }else if(e.getSource() == esev.btnguardar){
            if(esev.cmbtipo.getSelectedIndex() == 0){
                JOptionPane.showMessageDialog(esev, "Seleccione un Movimiento");
                esev.cmbtipo.requestFocus();
            }else if(esev.txtcantidad.getText().isEmpty()){
                JOptionPane.showMessageDialog(esev, "Ingrese un Cantidad");
                esev.txtcantidad.requestFocus();
            }else if(esev.txtmotivo.getText().isEmpty()){
                JOptionPane.showMessageDialog(esev, "Ingrese un Motivo");
                esev.txtmotivo.requestFocus();
            }else{
                int a = 0;
                a =  GuardarESEfectivo();
                if(a == 0){
                    JOptionPane.showMessageDialog(esev, "Error al Guardar Entrada y Salida de Efectivo, Contacte a Soporte Técnico!!!");
                }else{
                    boolean isSelected = false;
                    isSelected = esev.chbImpComprobante.isSelected();
                    if( isSelected ){
                        try {
                            // System.out.println("Imprimir Ticket.");
                            imprimirEntradaSalida(true, esev.cmbtipo.getSelectedItem().toString(), Double.parseDouble(esev.txtcantidad.getText()), esev.txtmotivo.getText());
                        } catch (IOException ex) {
                            Logger.getLogger(ESEfectivoC.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
                    esev.cmbtipo.setSelectedIndex(0);
                    esev.txtcantidad.setText("");
                    esev.txtmotivo.setText("");
                    esev.btnnuevo.requestFocus();
                    CargarESEfectivo();
                    // CargarPrveedores();
                    JOptionPane.showMessageDialog(esev, "Registro Guardado");
                }
            }
        }else if(e.getSource() == esev.btnbuscar){
            CargarESEfectivo();
        }else if(e.getSource() == esev.btnexportar){
            int filas = esev.tbllis.getRowCount();
            if( filas > 0 ){
                ExportarExcel obj;
                try {
                    obj = new ExportarExcel();
                    obj.exportarExcel(esev.tbllis);
                }catch(IOException ex) {
                    System.out.println("Error: " + ex);
                }
            }else{
                JOptionPane.showMessageDialog(esev, "No tienes datos para exportar");
            }
        }else if ( e.getSource() == esev.btnReimprimir ){
            int fila = esev.tbllis.getSelectedRow();
            if( fila < 0 ){
                JOptionPane.showMessageDialog(esev, "Seleccione un registro.");
            }else{
                String tipo = null;
                double cantidad = 0.0;
                String motivo = null;
                String partsCantidad [];
                String fecha = null;
                String usuario = null;
                
                partsCantidad = esev.tbllis.getValueAt(fila, 2).toString().split(" ");
                tipo = esev.tbllis.getValueAt(fila, 3).toString();
                cantidad = Double.parseDouble( partsCantidad[1] );
                motivo = esev.tbllis.getValueAt(fila, 1).toString();
                fecha = esev.tbllis.getValueAt(fila, 4).toString();
                usuario = esev.tbllis.getValueAt(fila, 6).toString();
                
                try {
                    reImprimirEntradaSalida(true, tipo, cantidad, motivo, fecha, usuario);
                } catch (IOException ex) {
                    Logger.getLogger(ESEfectivoC.class.getName()).log(Level.SEVERE, null, ex);
                }
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
    
    public void CargarESEfectivo(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        int id_ese = 0;
        String con_ese = null;
        Double can_ese = 0.0;
        int tip_ese = 0;
        String fec_ese = null;
        int sta_ese = 0;
        int id_pro = 0;
        
        id_ese = -1;
        
        Date fecha_de;
        Date fecha_al;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        fecha_de = esev.dtc_decha_de.getDate();
        fecha_al = esev.dtc_decha_al.getDate();
        con_ese = sdf.format(fecha_de)+" 00:00:00";
        fec_ese = sdf.format(fecha_al)+" 23:59:59";
        /*System.out.println("Fecha 1");
        System.out.println(con_ese);
        System.out.println("Fecha 2");
        System.out.println(fec_ese);*/
        
        tip_ese = 0;
        sta_ese = 0;
        id_pro = 0;
        
        esem.setId_ese(id_ese);
        esem.setCon_ese(con_ese);
        esem.setTip_ese(tip_ese);
        esem.setFec_ese(fec_ese);
        esem.setSta_ese(sta_ese);
        esem.setId_pro(id_pro);
        
        modelt = esem.CargarESEfectivo();
        esev.tbllis.setModel(modelt);
        
        esev.tbllis.getColumnModel().getColumn(0).setMaxWidth(0);
        esev.tbllis.getColumnModel().getColumn(0).setMinWidth(0);
        esev.tbllis.getColumnModel().getColumn(0).setPreferredWidth(0);
        /*esev.tbllis.getColumnModel().getColumn(3).setMaxWidth(60);
        esev.tbllis.getColumnModel().getColumn(3).setMinWidth(60);
        esev.tbllis.getColumnModel().getColumn(3).setPreferredWidth(60);
        esev.tbllis.getColumnModel().getColumn(4).setMaxWidth(125);
        esev.tbllis.getColumnModel().getColumn(4).setMinWidth(125);
        esev.tbllis.getColumnModel().getColumn(4).setPreferredWidth(125);*/
        esev.tbllis.getColumnModel().getColumn(2).setMaxWidth(100);
        esev.tbllis.getColumnModel().getColumn(2).setMinWidth(100);
        esev.tbllis.getColumnModel().getColumn(2).setPreferredWidth(100);
        esev.tbllis.getColumnModel().getColumn(3).setMaxWidth(60);
        esev.tbllis.getColumnModel().getColumn(3).setMinWidth(60);
        esev.tbllis.getColumnModel().getColumn(3).setPreferredWidth(60);
        esev.tbllis.getColumnModel().getColumn(4).setMaxWidth(125);
        esev.tbllis.getColumnModel().getColumn(4).setMinWidth(125);
        esev.tbllis.getColumnModel().getColumn(4).setPreferredWidth(125);
        esev.tbllis.getColumnModel().getColumn(5).setMaxWidth(60);
        esev.tbllis.getColumnModel().getColumn(5).setMinWidth(60);
        esev.tbllis.getColumnModel().getColumn(5).setPreferredWidth(60);
        centrar.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
        for (int i = 0; i <= 6; i++) {
            esev.tbllis.getColumnModel().getColumn(i).setCellRenderer(centrar);
        }
    }
    
    public void CargarPrveedores(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        pm.setId_pro(-2);
        
        modelt = pm.CargarProveedor();
        
        int filas = 0;
        filas = modelt.getRowCount();
        
    }
    
    public int GuardarESEfectivo(){
        int id_ese = 0;
        String con_ese = null;
        Double can_ese = 0.0;
        int tip_ese = 0;
        String fec_ese = null;
        int sta_ese = 0;
        int id_pro = 0;
        
        id_ese = 0;
        con_ese = esev.txtmotivo.getText();
        can_ese = Double.parseDouble(esev.txtcantidad.getText());
        if(esev.cmbtipo.getSelectedIndex() == 1){
            tip_ese = 0;
        }else{
            tip_ese = 1;
        }
        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        fec_ese = sdf.format(dt);
        sta_ese = 1;
        
        esem.setId_ese(id_ese);
        esem.setCon_ese(con_ese);
        esem.setCan_ese(can_ese);
        esem.setTip_ese(tip_ese);
        esem.setFec_ese(fec_ese);
        esem.setSta_ese(sta_ese);
        esem.setId_pro(id_pro);
        
        if(esem.GuardarESEfectivo()){
            return 1;
        }
        return 0;
    }
    
    public int imprimirEntradaSalida(boolean imprimir, String tipo, double monto, String comentarios) throws IOException{
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        em.setId(-1);
        em.setNom(null);
        em.setCal(null);
        em.setNum(null);
        em.setCol(null);
        em.setTel(null);
        em.setCel(null);
        em.setMun(null);
        em.setEst(null);
        em.setTex(null);
        modelt = em.CargarEmpresa();
        
        Date fechaActual = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        String fechaFormateada = formato.format(fechaActual);
        
        Ticket ticket = new Ticket();
        
        if(modelt.getRowCount()<=0){
            
        }else{
            Ticket.AddCabecera(modelt.getValueAt(0, 1).toString());
            Ticket.AddCabecera(Ticket.DarEspacio());
            Ticket.AddCabecera(modelt.getValueAt(0, 9).toString());
            Ticket.AddCabecera(Ticket.DarEspacio());
            Ticket.AddCabecera(modelt.getValueAt(0, 2).toString() + " #"+modelt.getValueAt(0, 3).toString() + " " + modelt.getValueAt(0, 4).toString());
            Ticket.AddCabecera(Ticket.DarEspacio());
            if(modelt.getValueAt(0, 5) == null){
                
            }else{
            }
            Ticket.AddCabecera(modelt.getValueAt(0, 7).toString() + ", " + modelt.getValueAt(0, 8).toString());
            Ticket.AddCabecera(Ticket.DarEspacio());
            Ticket.AddCabecera("WhatsApp: " + modelt.getValueAt(0, 6).toString() );
            Ticket.AddCabecera(Ticket.DarEspacio());
        }
        Ticket.AddCabecera(Ticket.DibujarLinea(29));
        Ticket.AddCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera("Caja UNICA");
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera("Cajero: " + GlobalVaribales.getInstance().nombreLogueado);
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera(fechaFormateada);
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera(""/*+vv.c_fechaventa_ve.getText()*/);
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera(Ticket.DibujarLinea(29));
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        
        Ticket.AddSubCabecera("Monto de " + tipo + ": " +String.valueOf(monto));
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera("Comentarios:");
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera(comentarios);
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        
        if(modelt.getRowCount() <= 0){
            
        }else{
            Ticket.AddPieLinea(Ticket.DarEspacio());
            Ticket.AddPieLinea(Ticket.DarEspacio());
            Ticket.AddPieLinea(Ticket.DarEspacio());
            Ticket.AddPieLinea(Ticket.DarEspacio());
        }
        
        int a = 0;
        a = Ticket.ImprimirDocumento(imprimir);
        if(a == 1){
            return 1;
        }
        return 0;
    
    }
    
    public int reImprimirEntradaSalida(boolean imprimir, String tipo, double monto, String comentarios, String fecha, String usuario) throws IOException{
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        em.setId(-1);
        em.setNom(null);
        em.setCal(null);
        em.setNum(null);
        em.setCol(null);
        em.setTel(null);
        em.setCel(null);
        em.setMun(null);
        em.setEst(null);
        em.setTex(null);
        modelt = em.CargarEmpresa();
        
        if (fecha.contains(".")) {
            fecha = fecha.substring(0, fecha.indexOf("."));
        }

        /*SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fechaFormateada = null;
        try {
            System.out.println("Fecha: " + fecha);
            fechaFormateada = formato.parse(fecha);
        } catch (ParseException ex) {
            Logger.getLogger(ESEfectivoC.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String fechaFormateada2 = formato.format(fechaFormateada);*/
        
        String fechaFormateada2 = fecha;
        
        Ticket ticket = new Ticket();
        
        if(modelt.getRowCount()<=0){
            
        }else{
            Ticket.AddCabecera(modelt.getValueAt(0, 1).toString());
            Ticket.AddCabecera(Ticket.DarEspacio());
            Ticket.AddCabecera(modelt.getValueAt(0, 9).toString());
            Ticket.AddCabecera(Ticket.DarEspacio());
            Ticket.AddCabecera(modelt.getValueAt(0, 2).toString() + " #"+modelt.getValueAt(0, 3).toString() + " " + modelt.getValueAt(0, 4).toString());
            Ticket.AddCabecera(Ticket.DarEspacio());
            if(modelt.getValueAt(0, 5) == null){
                
            }else{
            }
            Ticket.AddCabecera(modelt.getValueAt(0, 7).toString() + ", " + modelt.getValueAt(0, 8).toString());
            Ticket.AddCabecera(Ticket.DarEspacio());
            Ticket.AddCabecera("WhatsApp: " + modelt.getValueAt(0, 6).toString() );
            Ticket.AddCabecera(Ticket.DarEspacio());
        }
        Ticket.AddCabecera(Ticket.DibujarLinea(29));
        Ticket.AddCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera("Caja UNICA");
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera("Cajero: " + usuario);
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera(fechaFormateada2);
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera(""/*+vv.c_fechaventa_ve.getText()*/);
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera(Ticket.DibujarLinea(29));
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        
        Ticket.AddSubCabecera("Monto de " + tipo + ": " +String.valueOf(monto));
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera("Comentarios:");
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera(comentarios);
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        
        if(modelt.getRowCount() <= 0){
            
        }else{
            Ticket.AddPieLinea(Ticket.DarEspacio());
            Ticket.AddPieLinea(Ticket.DarEspacio());
            Ticket.AddPieLinea(Ticket.DarEspacio());
            Ticket.AddPieLinea(Ticket.DarEspacio());
        }
        
        int a = 0;
        a = Ticket.ImprimirDocumento(imprimir);
        if(a == 1){
            return 1;
        }
        return 0;
    
    }
    
}
