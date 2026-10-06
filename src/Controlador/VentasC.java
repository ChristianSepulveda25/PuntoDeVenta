/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.*;
import Vista.*;
import static Vista.Principal.estacerrado;
import static Vista.Principal.inicio;
import com.mxrck.autocompleter.TextAutoCompleter;
import com.toedter.calendar.JTextFieldDateEditor;
import innotpv.Ticket;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListDataListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.*;
// import br.com.adilson.util.PrinterMatrix;
// import br.com.adilson.util.Extenso;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.TimeZone;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.ListSelectionModel;
import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import org.jdesktop.swingx.JXDialog;

/**
 *
 * @author Christian Sepulveda
 */
public class VentasC implements ActionListener, KeyListener, MouseListener, PropertyChangeListener{
    private VentasM vm;
    private VentasV vv;
    private Principal mainV;
    private ProductosM pm = new ProductosM();
    private EmpresaM em = new EmpresaM();
    private ESEfectivoM esem = new ESEfectivoM();
    TextAutoCompleter textAutoCompleter;
    TextAutoCompleter textAutoCompleter1;
    DefaultTableModel modeltLis;
    DefaultTableModel modeltLis1;
    DefaultTableModel modeltLis2;
    DefaultTableModel modeltLisCV;//modelo Para cancelar venta
    DefaultTableModel modeltLisCO;//modelo Para cancelar Orden
    DefaultTableModel c_modeltLisOS;//modelo Para Consulta Orden de Servicio
    DefaultTableModel c_modeltLisVE;//modelo Para Consulta Venta
    public int filaReem = 0;
    public String fech_reg_Tick = null;
    DefaultTableCellRenderer centrar = new DefaultTableCellRenderer();

    public VentasC(VentasM vm, VentasV vv) {
        this.vm = vm;
        this.vv = vv;
        this.vv.btnsalir.addActionListener(this);
        this.vv.txtbus.addKeyListener(this);
        this.vv.btnmas.addActionListener(this);
        this.vv.btnmen.addActionListener(this);
        this.vv.btneli.addActionListener(this);
        this.vv.btnact.addActionListener(this);
        this.vv.btncobrar.addActionListener(this);
        this.vv.txtdescuento.addKeyListener(this);
        this.vv.txtpago.addKeyListener(this);
        //this.vv.txtdescuento1.addKeyListener(this);
        //this.vv.txtpago1.addKeyListener(this);
        this.vv.btncobrarsinticket.addActionListener(this);
        //this.vv.btncobrarsinticket1.addActionListener(this);
        this.vv.btncobrarconticket.addActionListener(this);
        this.vv.btn_reimprimir_ticket.addActionListener(this);
        //this.vv.btncobrarconticket1.addActionListener(this);
        //this.vv.rbtnorden.addActionListener(this);
        //this.vv.rbtnorden1.addActionListener(this);
        //this.vv.rbtnventa.addActionListener(this);
        //this.vv.rbtnventa1.addActionListener(this);
        this.vv.pescobrar.addMouseListener(this);
        this.vv.btnbus_ven.addActionListener(this);
        this.vv.btnexportar.addActionListener(this);
        this.vv.btnsalir_ven.addActionListener(this);
        this.vv.btnconsultar_ven.addActionListener(this);
        this.vv.btncancelar_ven.addActionListener(this);
        this.vv.pesprincipal.addMouseListener(this);
        this.vv.fechaDe.addPropertyChangeListener(this);
        this.vv.fechaDe.addPropertyChangeListener(this);
        // MODAL ENTRADAS SALIDAS EFECTIVO
        this.vv.btnGuardar_ESE.addActionListener(this);
        this.vv.btnCancelar_ESE.addActionListener(this);
        this.vv.dEntSalEfe.addKeyListener(this);
        this.vv.cmbTipo_ESE.addKeyListener(this);
        // this.vv.spinMonto_ESE.addKeyListener(this);
        this.vv.txtComentarios_ESE.addKeyListener(this);
        this.vv.chbImpComprobante_ESE.addKeyListener(this);
        this.vv.btnCancelar_ESE.addKeyListener(this);
        this.vv.btnGuardar_ESE.addKeyListener(this);
        this.vv.btnEntSalEfectivo.addActionListener(this);
        this.vv.jrbPrecio1.addKeyListener(this);
        this.vv.jrbPrecio2.addKeyListener(this);
        this.vv.jrbPrecio3.addKeyListener(this);
        this.vv.btnAplicarPrecio.addActionListener(this);
        this.vv.btnCancelarPrecio.addActionListener(this);
        this.vv.btnPreciosF8.addActionListener(this);
    }

    public void iniciar() {
        // vv.setTitle("Ventas");
        vv.txtbus.setFocusable(true);
        vv.pnlcobro.setVisible(false);
        vv.btncobrarconticket.setVisible(true);
        //AutoCompleteDecorator.decorate(vv.cmbbus);
        CargarProductos();
        CargarLista();
        vv.tbllis.getTableHeader().setReorderingAllowed(false) ;
        vv.tbllis.getTableHeader().setResizingAllowed(false); 
        vv.tbllis_ven.getTableHeader().setReorderingAllowed(false) ;
        vv.tbllis_ven.getTableHeader().setResizingAllowed(false);
        vv.tbllis.getTableHeader().setFont(new Font("Arial", 1, 14));
        vv.pescobrar.setIconAt(0, new ImageIcon("src/img/efectivo.png"));
        //vv.pescobrar.setIconAt(1, new ImageIcon("src/img/tarjeta.png"));
        vv.pesprincipal.setIconAt(0, new ImageIcon("src/img/ventas.png"));
        vv.pesprincipal.setIconAt(1, new ImageIcon("src/img/CancelarVenta.png"));
        java.util.Date tuFecha = new java.util.Date();
        vv.fechaDe.setDate(tuFecha);
        vv.fechaAl.setDate(tuFecha);
        JTextFieldDateEditor dateEditor_de = (JTextFieldDateEditor)vv.fechaDe.getDateEditor();
        dateEditor_de.setHorizontalAlignment(JTextField.CENTER);
        dateEditor_de.setEnabled(false);
        JTextFieldDateEditor dateEditor_al = (JTextFieldDateEditor)vv.fechaAl.getDateEditor();
        dateEditor_al.setHorizontalAlignment(JTextField.CENTER);
        dateEditor_al.setEnabled(false);
        vv.txtbus.requestFocus();
        //vv.pesprincipal.setIconAt(2, new ImageIcon("src/img/CancelarVenta.png"));
        //vv.pesprincipal.setIconAt(3, new ImageIcon("src/img/CancelarOrden.png"));
        /*vv.rbtnventa.setSelected(true);
        vv.rbtnventa1.setSelected(true);
        vv.txtcliente.setEnabled(false);
        vv.txtcliente1.setEnabled(false);
        vv.spnfechor.setEnabled(false);
        vv.spnfechor1.setEnabled(false);
        vv.txtdomicilio.setEnabled(false);
        vv.txtdomicilio1.setEnabled(false);*/
        //ORDEN DE SERVICIO
        /*java.util.Date tuFecha = new java.util.Date();
        vv.dtc_de.setDate(tuFecha);
        vv.dtc_al.setDate(tuFecha);
        JTextFieldDateEditor dateEditor_de = (JTextFieldDateEditor)vv.dtc_de.getDateEditor();
        dateEditor_de.setHorizontalAlignment(JTextField.CENTER);
        dateEditor_de.setEnabled(false);
        JTextFieldDateEditor dateEditor_al = (JTextFieldDateEditor)vv.dtc_al.getDateEditor();
        dateEditor_al.setHorizontalAlignment(JTextField.CENTER);
        dateEditor_al.setEnabled(false);*/
        // CANCELACIONES
        //vv.txtbus.requestFocus();
        /*java.util.Date tuFecha1 = new java.util.Date();
        vv.dtc_de_ord.setDate(tuFecha1);
        vv.dtc_al_ord.setDate(tuFecha1);
        JTextFieldDateEditor dateEditor_de1 = (JTextFieldDateEditor)vv.dtc_de_ord.getDateEditor();
        dateEditor_de1.setHorizontalAlignment(JTextField.CENTER);
        dateEditor_de1.setEnabled(false);
        JTextFieldDateEditor dateEditor_al1 = (JTextFieldDateEditor)vv.dtc_al_ord.getDateEditor();
        dateEditor_al1.setHorizontalAlignment(JTextField.CENTER);
        dateEditor_al1.setEnabled(false);*/
        
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) vv.spinMonto_ESE.getEditor();
        editor.getTextField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Verificar si se presionó la tecla Enter
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    cerrarModal(vv.dEntSalEfe);
                }else if ( e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_F12 ) {
                    try {
                        guardarEntradasSalidasEfectivo();
                    } catch (IOException ex) {
                        Logger.getLogger(VentasC.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vv.btnsalir){
            if(vv.btnsalir.getText().equals("Salir")){
                vv.dispose();
            }else{
                vv.pnlcajarapida.setVisible(true);
                vv.pnlcobro.setVisible(false);
                vv.btnsalir.setText("Salir");
                vv.btncobrar.setEnabled(true);
            }
        }else if(e.getSource() == vv.btnPreciosF8){
            int fila = 0;
            fila = vv.tbllis.getSelectedRow();
            if(fila < 0){
                JOptionPane.showMessageDialog(vv, "Seleccione un Registro");
            }else{
                int id = 0;
                id = Integer.parseInt(vv.tbllis.getValueAt(fila, 0).toString());
                abrirModalPrecios(id);
            }
        }else if(e.getSource() == vv.btnAplicarPrecio){
            seleccionarListaPrecios();
        }else if(e.getSource() == vv.btnCancelarPrecio){
            cerrarModal(vv.dEntSalEfe);
        }else if(e.getSource() == vv.btnmas){
            int fila = 0;
            fila = vv.tbllis.getSelectedRow();
            if(fila < 0){
                JOptionPane.showMessageDialog(vv, "Seleccione un Producto");
            }else{
                double cant = 0;
                cant = Double.parseDouble(vv.tbllis.getValueAt(fila, 4).toString());
                cant = cant + 1;
                vv.tbllis.setValueAt(cant, fila, 4);
                CalcularTotales();
            }
            vv.txtbus.requestFocus();
        }else if(e.getSource() == vv.btnmen){
            int fila = 0;
            fila = vv.tbllis.getSelectedRow();
            if(fila < 0){
                JOptionPane.showMessageDialog(vv, "Seleccione un Producto");
            }else{
                double cant = 0;
                cant = Double.parseDouble(vv.tbllis.getValueAt(fila, 4).toString());
                if(cant <= 1){
                    
                }else{
                    cant = cant - 1;
                    vv.tbllis.setValueAt(cant, fila, 4);
                    CalcularTotales();
                }
            }
            vv.txtbus.requestFocus();
        }else if(e.getSource() == vv.btneli){
            int fila = 0;
            fila = vv.tbllis.getSelectedRow();
            if(fila < 0){
                JOptionPane.showMessageDialog(vv, "Seleccione un Producto");
            }else{
                int resEliminar = JOptionPane.showConfirmDialog(null, "Desea eliminar el producto de la lista?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if( resEliminar == 0 ){
                    modeltLis.removeRow(fila);
                    CalcularTotales();
                }
            }
            vv.txtbus.requestFocus();
        }else if(e.getSource() == vv.btnact){
            int resEliminar = JOptionPane.showConfirmDialog(null, "Desea recargar la ventana?", "Confirmar recarga", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if( resEliminar == 0 ){
                this.vv.dispose();
                VentasM vm = new VentasM();
                if(estacerrado(vv)){
                    vv = new VentasV();
                    inicio.add(vv);
                    VentasC vc = new VentasC(vm, vv);
                    vv.show();
                    try {
                        vv.setMaximum(true);
                    } catch (PropertyVetoException ex) {
                        Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    vc.iniciar();
                }else{
                    JOptionPane.showMessageDialog(vv, "La ventana ya se encuentra abierta!!!");
                }
            }
        }else if(e.getSource() == vv.btncobrar){
            int filas = 0;
            filas = vv.tbllis.getRowCount();
            if(filas <= 0){
                vv.txtbus.requestFocus();
            }else{
                vv.pnlcajarapida.setVisible(false);
                vv.pnlcobro.setVisible(true);
                vv.btnsalir.setText("Regresar");
                vv.btncobrar.setEnabled(false);
                //Obtener Subtotal y Total
                String[] partSubTot;
                partSubTot = vv.lbltot.getText().split(" ");
                vv.txtsubtotal.setText(partSubTot[1]);
                vv.txttotal.setText(partSubTot[1]);
                vv.txtsubtotal.setEnabled(false);
                vv.txttotal.setEnabled(false);
                //Descuento
                vv.txtdescuento.setText("0");
                //Paga Con
                vv.txtpago.setText(partSubTot[1]);
                //Cambio
                vv.txtcambio.setText("0");
                vv.txtcambio.setEnabled(false);
                // Referencia
                // Tipo de Venta
                vv.pescobrar.setSelectedIndex(0);
                vv.txtpago.requestFocus();
            }
        }else if(e.getSource() == vv.btncobrarconticket){// Cobrar en Efectivo y Ticket
            double total = 0;
            double pago = 0;
            total = Double.parseDouble(vv.txttotal.getText());
            if(vv.txtpago.getText().isEmpty()){
                pago = 0;
            }else{
                pago = Double.parseDouble(vv.txtpago.getText());
            }
            if(pago < total){
                JOptionPane.showMessageDialog(vv, "Introducir una cantidad en el Campo \"Pago\"\nIgual o Mayor al Total");
            }else{
                int guaVen=0;
                guaVen = GuardarVenta();
                if(guaVen <= 0){
                    JOptionPane.showMessageDialog(vv, "Error al Guarar la venta, Contacte a Soporte Técnico!!!");
                }else{
                    int guaDet = 0;
                    guaDet = GuardarDetalleVenta();
                    if(guaDet > 0){
                        JOptionPane.showMessageDialog(vv, "Error al Guardar los Productos de la Venta, Contacte a Soporte Técnico");
                    }else{
                        // Espacio para Imprimir Ticket Antes de Regresar al Inicio
                        int a = 0;
                        try {
                            a = imprimirTicket(true);
                        } catch (IOException ex) {
                            Logger.getLogger(VentasC.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if(a == 1){
                            
                        }else{
                            JOptionPane.showMessageDialog(vv, "El Ticket no se Imprimió, Contacte a Soporte Técnico");
                        }
                        // Fin de Imprimir Ticket
                        vv.pnlcajarapida.setVisible(true);
                        vv.pnlcobro.setVisible(false);
                        vv.btnsalir.setText("Salir");
                        vv.btncobrar.setEnabled(true);
                        //Obtener Subtotal y Total
                        vv.txtsubtotal.setText("");
                        vv.txttotal.setText("");
                        //Descuento
                        vv.txtdescuento.setText("");
                        //Paga Con
                        vv.txtpago.setText("");
                        //Cambio
                        vv.txtcambio.setText("");
                        int filas = vv.tbllis.getRowCount();
                        for (int i = 0; i < filas; i++) {
                            modeltLis.removeRow(0);
                        }
                        CalcularTotales();
                        JOptionPane.showMessageDialog(vv, "Venta Finalizada");
                        vv.txtbus.requestFocus();
                    }
                }
            }
        }else if(e.getSource() == vv.btncobrarsinticket){// Cobro en Efectivo Sin Ticket
            double total = 0;
            double pago = 0;
            total = Double.parseDouble(vv.txttotal.getText());
            if(vv.txtpago.getText().isEmpty()){
                pago = 0;
            }else{
                pago = Double.parseDouble(vv.txtpago.getText());
            }
            if(pago < total){
                JOptionPane.showMessageDialog(vv, "Introducir una cantidad en el Campo \"Pago\"\nIgual o Mayor al Total");
            }else{
                int guaVen=0;
                guaVen = GuardarVenta();
                if(guaVen <= 0){
                    JOptionPane.showMessageDialog(vv, "Error al Guarar la venta, Contacte a Soporte Técnico!!!");
                }else{
                    int guaDet = 0;
                    guaDet = GuardarDetalleVenta();
                    if(guaDet > 0){
                        JOptionPane.showMessageDialog(vv, "Error al Guardar los Productos de la Venta, Contacte a Soporte Técnico");
                    }else{
                        int a = 0;
                        try {
                            a = imprimirTicket(false);
                        } catch (IOException ex) {
                            Logger.getLogger(VentasC.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if(a == 1){
                            
                        }else{
                            JOptionPane.showMessageDialog(vv, "El Ticket no se Imprimió, Contacte a Soporte Técnico");
                        }
                        vv.pnlcajarapida.setVisible(true);
                        vv.pnlcobro.setVisible(false);
                        vv.btnsalir.setText("Salir");
                        vv.btncobrar.setEnabled(true);
                        //Obtener Subtotal y Total
                        vv.txtsubtotal.setText("");
                        vv.txttotal.setText("");
                        //Descuento
                        vv.txtdescuento.setText("");
                        //Paga Con
                        vv.txtpago.setText("");
                        //Cambio
                        vv.txtcambio.setText("");
                        int filas = vv.tbllis.getRowCount();
                        for (int i = 0; i < filas; i++) {
                            modeltLis.removeRow(0);
                        }
                        CalcularTotales();
                        JOptionPane.showMessageDialog(vv, "Venta Finalizada");
                        vv.txtbus.requestFocus();
                    }
                }
            }
        }else if(e.getSource() == vv.btnbus_ven){
            CargarVentasCancelar();
        }else if(e.getSource() == vv.btnsalir_ven){
            vv.dispose();
        }else if(e.getSource() == vv.btncancelar_ven){
            int fila = 0;
            fila = vv.tbllis_ven.getSelectedRow();
            if(fila < 0){
                JOptionPane.showMessageDialog(vv, "Selecione un Registro");
            }else{
                String estatus = null;
                estatus = vv.tbllis_ven.getValueAt(fila, 7).toString();
                if(estatus.equals("Cancelada")){
                    JOptionPane.showMessageDialog(vv, "La venta ya fue cancelada!!!");
                }else{
                    Object[] options = {"Si", "No"};
                    int resGuardar = JOptionPane.showOptionDialog(null, "Desea cancelar la venta?", "Guardar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null, options, options[0]);
                    if( resGuardar == 0 ){
                        int a = 0;
                        a = ActualizarEstatus_VE(fila);
                        if(a == 0){
                            JOptionPane.showMessageDialog(vv, "Error al Cancelar Venta, Contacte a Soporte Técnio");
                        }else{
                            RegresarStockVenta(fila);
                            CargarVentasCancelar();
                            JOptionPane.showMessageDialog(vv, "Venta Cancelada");
                        }
                    }
                }
            }
        }else if(e.getSource() == vv.btnconsultar_ven){
            int fila = 0;
            fila = vv.tbllis_ven.getSelectedRow();
            if(fila < 0){
                JOptionPane.showMessageDialog(vv, "Seleccione un Registro");
            }else{
                vv.dConsultarVE.setSize(800, 550);
                vv.dConsultarVE.setLocationRelativeTo(null);
                vv.dConsultarVE.setVisible(true);
                //vv.dConsultarOS.show();
                
                // LIMPIAR DIALOG
                vv.c_folio_ve.setText("");
                vv.c_fechaventa_ve.setText("");
                vv.c_fechacancelado_ve.setText("");
                vv.c_estatus_ve.setText("");
                vv.c_subtotal_ve.setText("");
                vv.c_descuento_ve.setText("");
                vv.c_total_ve.setText("");
                int c_fila = 0;
                c_fila = vv.c_tbllis_ve.getSelectedRow();
                if(c_fila == -1){
                    
                }else{
                    for (int i = 0; i < fila; i++) {
                        c_modeltLisVE.removeRow(0);
                    }
                }
                
                // ASIGNAR VALORES A DIALOG
                vv.c_folio_ve.setText(vv.tbllis_ven.getValueAt(fila, 1).toString());
                vv.c_fechaventa_ve.setText(vv.tbllis_ven.getValueAt(fila, 6).toString());
                vv.c_estatus_ve.setText(vv.tbllis_ven.getValueAt(fila, 7).toString());
                vv.c_subtotal_ve.setText(vv.tbllis_ven.getValueAt(fila, 4).toString());
                vv.c_descuento_ve.setText(vv.tbllis_ven.getValueAt(fila, 5).toString());
                vv.c_total_ve.setText(vv.tbllis_ven.getValueAt(fila, 2).toString());
                if(vv.tbllis_ven.getValueAt(fila, 8) == null){
                    vv.c_fechacancelado_ve.setText("");
                }else{
                    vv.c_fechacancelado_ve.setText(vv.tbllis_ven.getValueAt(fila, 8).toString());
                }
                CargarConsultaDetalle2(vv.c_folio_ve.getText());
                
                if(vv.tbllis_ven.getValueAt(fila, 7).toString().equals("Cancelada")){
                    vv.btn_reimprimir_ticket.setEnabled(false);
                }else{
                    vv.btn_reimprimir_ticket.setEnabled(true);
                }
            }
        }else if(e.getSource() == vv.btnexportar){
            int filas = vv.tbllis_ven.getRowCount();
            if( filas > 0 ){
                ExportarExcel obj;
                try {
                    obj = new ExportarExcel();
                    obj.exportarExcel(vv.tbllis_ven);
                }catch(IOException ex) {
                    System.out.println("Error: " + ex);
                }
            }else{
                JOptionPane.showMessageDialog(vv, "No tienes datos para exportar");
            }
        }else if(e.getSource() == vv.btn_reimprimir_ticket){
            int a = 0;
            try {
                a = reImprimirTicket(true);
            } catch (IOException ex) {
                Logger.getLogger(VentasC.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(a == 1){

            }else{
                JOptionPane.showMessageDialog(vv, "El Ticket no se Imprimió, Contacte a Soporte Técnico");
            }
        }else if(e.getSource() == vv.btnCancelar_ESE){
            cerrarModal(vv.dEntSalEfe);
        }else if(e.getSource() == vv.btnGuardar_ESE){
            try {
                guardarEntradasSalidasEfectivo();
            } catch (IOException ex) {
                Logger.getLogger(VentasC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(e.getSource() == vv.btnEntSalEfectivo){
            abrirModalEntradasSalidasEfe();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getSource()== vv.txtbus){
            /*
            if(vv.tbllis.getRowCount() > 0)
                vv.tbllis.setRowSelectionInterval(0, 0);
            */
            
            if(/*e.getKeyCode() == KeyEvent.VK_PLUS ||*/ e.getKeyCode() == KeyEvent.VK_ADD) {
                int fila = 0;
                fila = vv.tbllis.getSelectedRow();
                if(fila < 0){
                    JOptionPane.showMessageDialog(vv, "Seleccione un Producto");
                }else{
                    double cant = 0;
                    cant = Double.parseDouble(vv.tbllis.getValueAt(fila, 4).toString());
                    cant = cant + 1;
                    vv.tbllis.setValueAt(cant, fila, 4);
                    CalcularTotales();
                }
                
                vv.txtbus.requestFocus();
            }else if(/*e.getKeyCode() == KeyEvent.VK_MINUS ||*/ e.getKeyCode() == KeyEvent.VK_SUBTRACT) {
                int fila = 0;
                fila = vv.tbllis.getSelectedRow();
                if(fila < 0){
                    JOptionPane.showMessageDialog(vv, "Seleccione un Producto");
                }else{
                    double cant = 0;
                    cant = Double.parseDouble(vv.tbllis.getValueAt(fila, 4).toString());
                    if(cant <= 1){

                    }else{
                        cant = cant - 1;
                        vv.tbllis.setValueAt(cant, fila, 4);
                        CalcularTotales();
                    }
                }
                vv.txtbus.requestFocus();
            }else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                if( vv.txtbus.getText().toString().length() < 1 ){
                    int filasDownCount = vv.tbllis.getRowCount();
                    int filasDownSelected = vv.tbllis.getSelectedRow();
                    if(filasDownCount > 0){
                        // vv.tbllis.setRowSelectionInterval(0, 0);
                        if(filasDownCount > filasDownSelected+1){
                            vv.tbllis.setRowSelectionInterval(filasDownSelected+1, filasDownSelected+1);
                        }
                    }
                }
            }else if(e.getKeyCode() == KeyEvent.VK_UP) {
                if( vv.txtbus.getText().toString().length() < 1 ){
                    int filasUpCount = vv.tbllis.getRowCount();
                    int filasUpSelected = vv.tbllis.getSelectedRow();
                    if(filasUpCount > 0){
                        // vv.tbllis.setRowSelectionInterval(0, 0);
                        if( filasUpSelected > 0 ){
                            vv.tbllis.setRowSelectionInterval(filasUpSelected-1, filasUpSelected-1);
                        }
                    }
                }
            }else if(e.getKeyCode() == KeyEvent.VK_DELETE) {
                int fila = 0;
                fila = vv.tbllis.getSelectedRow();
                if(fila < 0){
                    JOptionPane.showMessageDialog(vv, "Seleccione un Producto");
                }else{
                    int resEliminar = JOptionPane.showConfirmDialog(null, "Desea eliminar el producto de la lista?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if( resEliminar == 0 ){
                        modeltLis.removeRow(fila);
                        CalcularTotales();
                    }
                }
                
                if(vv.tbllis.getRowCount() > 0){
                    if(fila == 0){
                        vv.tbllis.setRowSelectionInterval(0, 0);
                    }else{
                        vv.tbllis.setRowSelectionInterval(fila-1, fila-1);
                    }
                }
                vv.txtbus.requestFocus();
            }else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                String[] partPro;
                partPro = vv.txtbus.getText().split("'");
                //JOptionPane.showMessageDialog(vv, "Length en texto: " + partPro.length + " | Texto: " + vv.txtbus.getText());
                
                if( partPro.length <= 1 ){
                    String[] partPro1;
                    partPro1 = vv.txtbus.getText().split("\\*");
                    
                    if( partPro1.length <= 1 ){
                    
                        String str = vv.txtbus.getText();
                        boolean isNumeric =  str.matches("[+-]?\\d*(\\.\\d+)?");

                        // if( isNumeric ){
                        ///////////////////////// AGREGAR PRODUCTO CON ENTER //////////////////////////////////
                        double existe = 0;
                        existe = VerificarExisteTabla(vv.txtbus.getText());
                        if(existe <= 0){
                            String datos = null;
                            // textAutoCompleter.setCaseSensitive(true);
                            datos = CargarProducto(vv.txtbus.getText());
                            if(datos.equals("NO")){
                                int indicador = 0;
                                for (int i = 0; i < textAutoCompleter.getItems().length; i++) {
                                    //System.out.println(textAutoCompleter.getItems()[i]);
                                    String[] partProAuto;
                                    partProAuto = textAutoCompleter.getItems()[i].toString().split("'");
                                    int strLength = 0;
                                    strLength = str.length();
                                    String subcadena = "";
                                    subcadena = partProAuto[0].substring(0, strLength);
                                    if (subcadena.contains(str.toUpperCase())) {
                                        indicador = 1;
                                        break;
                                    }/* else {
                                        System.out.println("La cadena no contiene la subcadena.");
                                    }*/
                                }
                                
                                if(indicador == 0){
                                    vv.txtbus.setText("");
                                    JOptionPane.showMessageDialog(vv, "Producto No Existe");
                                }
                                
                                // System.out.println(textAutoCompleter.getItems()[0]);
                                // JOptionPane.showMessageDialog(vv, "Producto No Existe");
                            }else{
                                String[] datosBD;
                                datosBD = datos.split("_");
                                modeltLis.addRow(new Object[]{datosBD[0],vv.txtbus.getText(),datosBD[3],datosBD[1],1,""});
                                vv.txtbus.setText("");
                            }
                        }else{
                            String datos = null;
                            datos = CargarProducto(vv.txtbus.getText());
                            if(datos.equals("NO")){
                                JOptionPane.showMessageDialog(vv, "Producto No Existe");
                            }else{
                                String[] datosBD;
                                datosBD = datos.split("_");
                                if(Double.parseDouble(datosBD[2]) == existe+1){
                                    //JOptionPane.showMessageDialog(vv, "Stock Agotado"); -- Se le retiró para agregar sin Stock
                                    vv.tbllis.setValueAt(existe+1, filaReem, 4);
                                    vv.txtbus.setText("");
                                }else{
                                    vv.tbllis.setValueAt(existe+1, filaReem, 4);
                                    vv.txtbus.setText("");
                                }
                            }
                        }
                        CalcularTotales();
                        // vv.txtbus.setText("");
                        
                        if(vv.tbllis.getRowCount() > 0)
                            vv.tbllis.setRowSelectionInterval(0, 0);
                        
                        vv.txtbus.requestFocus();
                        //////////////////////////////////////////////////////////////////////////////////
                        // }
                    }else{
                        String str = partPro1[0];
                        boolean isNumeric =  str.matches("[+-]?\\d*(\\.\\d+)?");
                        if( !isNumeric ){
                            JOptionPane.showMessageDialog(vv, "Cantidad inválida. Ingrese otra cantidad.");
                            return;
                        }
                        ///////////////////////// AGREGAR PRODUCTO CON ENTER //////////////////////////////////
                        double existe = 0;
                        existe = VerificarExisteTabla(partPro1[1]);
                        if(existe <= 0){
                            String datos = null;
                            datos = CargarProducto(partPro1[1]);
                            if(datos.equals("NO")){
                                JOptionPane.showMessageDialog(vv, "Producto No Existe");
                            }else{
                                String[] datosBD;
                                datosBD = datos.split("_");
                                modeltLis.addRow(new Object[]{datosBD[0],partPro1[1],datosBD[3],datosBD[1],partPro1[0],""});
                                vv.txtbus.setText("");
                            }
                        }else{
                            String datos = null;
                            datos = CargarProducto(partPro1[1]);
                            if(datos.equals("NO")){
                                JOptionPane.showMessageDialog(vv, "Producto No Existe");
                            }else{
                                String[] datosBD;
                                datosBD = datos.split("_");
                                if(Double.parseDouble(datosBD[2]) == existe+Double.parseDouble(partPro1[0])){
                                    //JOptionPane.showMessageDialog(vv, "Stock Agotado"); -- Se le retiró para agregar sin Stock
                                    vv.tbllis.setValueAt(existe+Double.parseDouble(partPro1[0]), filaReem, 4);
                                    vv.txtbus.setText("");
                                }else{
                                    vv.tbllis.setValueAt(existe+Double.parseDouble(partPro1[0]), filaReem, 4);
                                    vv.txtbus.setText("");
                                }
                            }
                        }
                        CalcularTotales();
                        vv.txtbus.setText("");
                        
                        if(vv.tbllis.getRowCount() > 0)
                            vv.tbllis.setRowSelectionInterval(0, 0);
                        
                        vv.txtbus.requestFocus();
                        //////////////////////////////////////////////////////////////////////////////////
                    }
                }else{
                    double existe = 0;
                    existe = VerificarExisteTabla(partPro[1]);
                    if(existe < 1){
                        String datos = null;
                        datos = CargarProducto(partPro[1]);
                        if(datos.equals("NO")){
                            JOptionPane.showMessageDialog(vv, "Producto No Existe");
                        }else{
                            String[] datosBD;
                            datosBD = datos.split("_");
                            modeltLis.addRow(new Object[]{datosBD[0],partPro[1],datosBD[3],datosBD[1],1,""});
                        }
                    }else{
                        String datos = null;
                        datos = CargarProducto(partPro[1]);
                        if(datos.equals("NO")){
                            JOptionPane.showMessageDialog(vv, "Producto No Existe");
                        }else{
                            String[] datosBD;
                            datosBD = datos.split("_");
                            if(Double.parseDouble(datosBD[2]) == existe+1){
                                //JOptionPane.showMessageDialog(vv, "Stock Agotado"); -- Se le retiró para agregar sin Stock
                                vv.tbllis.setValueAt(existe+1, filaReem, 4);
                            }else{
                                vv.tbllis.setValueAt(existe+1, filaReem, 4);
                            }
                        }
                    }
                    CalcularTotales();
                    vv.txtbus.setText("");
                    
                    if(vv.tbllis.getRowCount() > 0)
                        vv.tbllis.setRowSelectionInterval(0, 0);
                    
                    vv.txtbus.requestFocus();
                    
                }
                
                /*if(partPro.length < 1){
                    String[] partPro1;
                    partPro1 = vv.txtbus.getText().split("-");
                    if(partPro1.length <= 1){
                        
                    }else{
                        //JOptionPane.showMessageDialog(vv, partPro1[0] + "-" +partPro1[1]);
                        
                    }
                    //vv.txtbus.setText("");
                    
                }else{
                    
                }*/
            }else if(e.getKeyCode() == KeyEvent.VK_F12) {
                //JOptionPane.showMessageDialog(vv, "Predionó F10");
                int filas = 0;
                filas = vv.tbllis.getRowCount();
                if(filas <= 0){
                    vv.txtbus.requestFocus();
                }else{
                    vv.pnlcajarapida.setVisible(false);
                    vv.pnlcobro.setVisible(true);
                    vv.btnsalir.setText("Regresar");
                    vv.btncobrar.setEnabled(false);
                    //Obtener Subtotal y Total
                    String[] partSubTot;
                    partSubTot = vv.lbltot.getText().split(" ");
                    vv.txtsubtotal.setText(partSubTot[1]);
                    vv.txttotal.setText(partSubTot[1]);
                    vv.txtsubtotal.setEnabled(false);
                    vv.txttotal.setEnabled(false);
                    //Descuento
                    vv.txtdescuento.setText("0");
                    //Paga Con
                    vv.txtpago.setText(partSubTot[1]);
                    //Cambio
                    vv.txtcambio.setText("0");
                    vv.txtcambio.setEnabled(false);
                    // Referencia
                    // Tipo de Venta
                    vv.pescobrar.setSelectedIndex(0);
                    vv.txtpago.requestFocus();
                }
            }else if(e.getKeyCode() == KeyEvent.VK_F6) {
                abrirModalEntradasSalidasEfe();
            }else if(e.getKeyCode() == KeyEvent.VK_F8) {
                int fila = 0;
                fila = vv.tbllis.getSelectedRow();
                if(fila < 0){
                    JOptionPane.showMessageDialog(vv, "Seleccione un Registro");
                }else{
                    int id = 0;
                    id = Integer.parseInt(vv.tbllis.getValueAt(fila, 0).toString());
                    abrirModalPrecios(id);
                }
            }else if(e.getKeyCode() == KeyEvent.VK_F10) {
                JOptionPane.showMessageDialog(vv, "Abrir buscar");
            }
        }else if(e.getSource()== vv.txtpago){
            if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                cobrarConEnter();
            }else if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                vv.pnlcajarapida.setVisible(true);
                vv.pnlcobro.setVisible(false);
                vv.btnsalir.setText("Salir");
                vv.btncobrar.setEnabled(true);
                vv.txtbus.requestFocus();
            }
        }else if(e.getSource()== vv.txtdescuento){
            if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                cobrarConEnter();
            }else if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                vv.pnlcajarapida.setVisible(true);
                vv.pnlcobro.setVisible(false);
                vv.btnsalir.setText("Salir");
                vv.btncobrar.setEnabled(true);
                vv.txtbus.requestFocus();
            }
        }else if( e.getSource() == vv.txtComentarios_ESE || 
                e.getSource() == vv.chbImpComprobante_ESE || 
                e.getSource() == vv.btnCancelar_ESE || 
                e.getSource() == vv.btnGuardar_ESE ){
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                cerrarModal(vv.dEntSalEfe);
            }else if ( e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_F12 ) {
                try {
                    guardarEntradasSalidasEfectivo();
                } catch (IOException ex) {
                    Logger.getLogger(VentasC.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else if( e.getSource() == vv.cmbTipo_ESE ){
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                cerrarModal(vv.dEntSalEfe);
            }
        }else if( e.getSource() == vv.jrbPrecio1 || e.getSource() == vv.jrbPrecio2 || e.getSource() == vv.jrbPrecio3 ){
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                cerrarModal(vv.dPrecios);
            }else if ( e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_F12 ) {
                seleccionarListaPrecios();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getSource()== vv.txtbus){
            if(/*e.getKeyCode() == KeyEvent.VK_PLUS ||*/ e.getKeyCode() == KeyEvent.VK_ADD) {
                vv.txtbus.setText("");
            }else if(/*e.getKeyCode() == KeyEvent.VK_MINUS ||*/ e.getKeyCode() == KeyEvent.VK_SUBTRACT) {
                vv.txtbus.setText("");
            }
        }else if(e.getSource() == vv.txtdescuento){
            if (vv.txtdescuento.getText().length()== 3){
                e.consume();
            }
            double des = 0;
            double can = 0;
            double tot = 0;
            if(vv.txtdescuento.getText().isEmpty()){
                can = Double.parseDouble(vv.txtsubtotal.getText());
                vv.txttotal.setText(""+can);
                vv.txtpago.setText(""+can);
                vv.txtcambio.setText("0");
            }else{
                des = Double.parseDouble(vv.txtdescuento.getText());
                if(des > 100){
                    des = 100;
                    vv.txtdescuento.setText("100");
                }
                can = Double.parseDouble(vv.txtsubtotal.getText());
                tot = can * (des/100);
                tot = can - tot;
                vv.txttotal.setText(""+tot);
                vv.txtpago.setText(""+tot);
                // vv.txttotal.setText(""+Math.ceil(tot));
                // vv.txtpago.setText(""+Math.ceil(tot));
                vv.txtcambio.setText("0");
            }
        }else if(e.getSource() == vv.txtpago){
            char caracter = e.getKeyChar();
            if (((caracter < '0') || (caracter > '9'))) {
                e.consume();
            }
            double tot = 0;
            double pag = 0;
            double cam = 0;
            if(vv.txtpago.getText().isEmpty()){
                vv.txtcambio.setText("-" + vv.txttotal.getText());
            }else{
                tot = Double.parseDouble(vv.txttotal.getText());
                pag = Double.parseDouble(vv.txtpago.getText());
                cam = pag - tot;
                vv.txtcambio.setText("" + cam);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == VentasV.pescobrar){
            if(VentasV.pescobrar.getSelectedIndex() == 0){
                vv.txtpago.requestFocus();
            }else if(VentasV.pescobrar.getSelectedIndex() == 1){
                //vv.txtreferencia1.requestFocus();
            }
        }else if(e.getSource() == VentasV.pesprincipal){
            /*if(VentasV.pesprincipal.getSelectedIndex() == 1){
                int f1 = 0;
                f1 = vv.tbllis_os.getRowCount();
                for (int i = 0; i < f1; i++) {
                    modeltLis1.removeRow(0);
                }
            }else */if(VentasV.pesprincipal.getSelectedIndex() == 2){
                int f2 = 0;
                f2 = vv.tbllis_ven.getRowCount();
                for (int i = 0; i < f2; i++) {
                    modeltLisCV.removeRow(0);
                }
            }/*else if(VentasV.pesprincipal.getSelectedIndex() == 3){
                int f3 = 0;
                f3 = vv.tbllis_ord.getRowCount();
                for (int i = 0; i < f3; i++) {
                    modeltLisCO.removeRow(0);
                }
            }*/
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getSource() == vv.fechaDe){
            vv.fechaAl.getJCalendar().setMinSelectableDate(vv.fechaDe.getDate());
            vv.fechaAl.setDate(vv.fechaDe.getDate());
        }
    }
    
    public void CargarProductos(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        pm.setId(-4);
        pm.setCod(null);
        pm.setNom(null);
        pm.setSa(0);
        pm.setSm(0);
        pm.setPre(0);
        pm.setCat(null);
        pm.setEst(0);
        modelt = pm.CargarProductos();
        // TEXT AUTO COMPLETER
        textAutoCompleter = new TextAutoCompleter(vv.txtbus);
        textAutoCompleter.setCaseSensitive(false);
        for (int i = 0; i < modelt.getRowCount(); i++) {
            textAutoCompleter.addItem(modelt.getValueAt(i, 2) + " '" + modelt.getValueAt(i, 1));
        }
        //textAutoCompleter.
        
        // Remover
        /*vv.cmbbus.removeAllItems();
        vv.cmbbus.addItem("Escriba el Producto");
        for (int i = 0; i < modelt.getRowCount(); i++) {
            vv.cmbbus.addItem(modelt.getValueAt(i, 2) + " '" + modelt.getValueAt(i, 1));
        }*/
        //System.out.println("Filas: " + modelt.getRowCount());
        //System.out.println("Columnas: " + modelt.getColumnCount());
    }
    
    public void CargarLista(){
        modeltLis = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        modeltLis.addColumn("ID");//////// 0
        modeltLis.addColumn("Código");//// 1
        modeltLis.addColumn("Nombre");//// 2
        modeltLis.addColumn("Precio");//// 3
        modeltLis.addColumn("Cantidad");// 4
        modeltLis.addColumn("Subtotal");// 5
        vv.tbllis.setModel(modeltLis);
        vv.tbllis.getColumnModel().getColumn(0).setMaxWidth(0);
        vv.tbllis.getColumnModel().getColumn(0).setMinWidth(0);
        vv.tbllis.getColumnModel().getColumn(0).setPreferredWidth(0);
        vv.tbllis.getColumnModel().getColumn(1).setMaxWidth(150);
        vv.tbllis.getColumnModel().getColumn(1).setMinWidth(150);
        vv.tbllis.getColumnModel().getColumn(1).setPreferredWidth(150);
        vv.tbllis.getColumnModel().getColumn(3).setMaxWidth(100);
        vv.tbllis.getColumnModel().getColumn(3).setMinWidth(100);
        vv.tbllis.getColumnModel().getColumn(3).setPreferredWidth(100);
        vv.tbllis.getColumnModel().getColumn(4).setMaxWidth(100);
        vv.tbllis.getColumnModel().getColumn(4).setMinWidth(100);
        vv.tbllis.getColumnModel().getColumn(4).setPreferredWidth(100);
        vv.tbllis.getColumnModel().getColumn(5).setMaxWidth(100);
        vv.tbllis.getColumnModel().getColumn(5).setMinWidth(100);
        vv.tbllis.getColumnModel().getColumn(5).setPreferredWidth(100);
    }
    
    public String CargarProducto(String cod){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        pm.setId(-5);
        pm.setCod(cod);
        pm.setNom(null);
        pm.setSa(0);
        pm.setSm(0);
        pm.setPre(0);
        pm.setCat(null);
        pm.setEst(0);
        modelt = pm.CargarProductos();
        
        if(modelt.getRowCount()<=0){
            return "NO";
        }
        
        return modelt.getValueAt(0, 0).toString() + "_" + modelt.getValueAt(0, 6).toString() + "_" + modelt.getValueAt(0, 3).toString() + "_" + modelt.getValueAt(0, 2).toString();
        
    }
    
    public double VerificarExisteTabla(String cod){
        int filas = 0;
        filas = vv.tbllis.getRowCount();
        if(filas <= 0){
            return 0;
        }else{
            for (int i = 0; i < filas; i++) {
                if(cod.equals(vv.tbllis.getValueAt(i, 1).toString())){
                    filaReem = 0;
                    filaReem = i;
                    return Double.parseDouble(vv.tbllis.getValueAt(i, 4).toString());
                }
            }
        }
        return 0;
    }
    
    public void CalcularTotales(){
        double tot_efe = 0;
        double tot_pro = 0;
        double sub_tot = 0;
        double can_pro = 0;
        int filas = 0;
        filas = vv.tbllis.getRowCount();
        vv.lblcan.setText("0");
        vv.lbltot.setText("$ 0.0");
        for (int i = 0; i < filas; i++) {
            can_pro = 0;
            sub_tot = 0;
            can_pro = Double.parseDouble(vv.tbllis.getValueAt(i, 4).toString());
            sub_tot = Double.parseDouble(vv.tbllis.getValueAt(i, 3).toString());
            sub_tot = sub_tot * can_pro;
            vv.tbllis.setValueAt(sub_tot, i, 5);
            tot_efe = tot_efe + Double.parseDouble(vv.tbllis.getValueAt(i, 5).toString());
            tot_pro = tot_pro + can_pro;
            vv.lbltot.setText("$ "+tot_efe);
            vv.lblcan.setText(""+tot_pro);
        }
    }
    
    public int imprimirTicket(boolean imprimir) throws IOException{
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
                // Ticket.AddCabecera("Telefono: " + modelt.getValueAt(0, 5).toString());
                // Ticket.AddCabecera(Ticket.DarEspacio());
            }
            // Ticket.AddCabecera("Celular: " + modelt.getValueAt(0, 6).toString());
            // Ticket.AddCabecera(Ticket.DarEspacio());
            Ticket.AddCabecera(modelt.getValueAt(0, 7).toString() + ", " + modelt.getValueAt(0, 8).toString());
            Ticket.AddCabecera(Ticket.DarEspacio());
            //ev.txttexto.setText(modelt.getValueAt(0, 9).toString());
        }
        //Ticket.AddCabecera("BOUTIQUE GAMBOA");
        //Ticket.AddCabecera(Ticket.DarEspacio());
        //Ticket.AddCabecera("Av. De la Cultura # 79 Colonia Cd. del Valle");
        //Ticket.AddCabecera(Ticket.DarEspacio());
        //Ticket.AddCabecera("Teléfono: 311-372-93-61");
        //Ticket.AddCabecera(Ticket.DarEspacio());
        //Ticket.AddCabecera("Tepic, Nayarit");
        //Ticket.AddCabecera(Ticket.DarEspacio());
        Ticket.AddCabecera(Ticket.DibujarLinea(29));
        Ticket.AddCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera("Caja UNICA - Ticket # "+vm.getFol_dven());
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera("Le Atiende: " + GlobalVaribales.getInstance().nombreLogueado);
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera(""+fech_reg_Tick);
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera(Ticket.DibujarLinea(29));
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        /*Ticket.AddItem("1","Articulo Prueba","15.00");
        Ticket.AddItem("","",Ticket.DarEspacio());
        Ticket.AddItem("2","Articulo Prueba","25.00");
        Ticket.AddItem("","",Ticket.DarEspacio());*/
        
        int filas = 0;
        filas = vv.tbllis.getRowCount();
        for (int i = 0; i < filas; i++) {
            Ticket.AddItem(vv.tbllis.getValueAt(i, 4).toString(),vv.tbllis.getValueAt(i,2).toString(),"$ "+vv.tbllis.getValueAt(i, 5).toString());
            Ticket.AddItem("","",Ticket.DarEspacio());
        }
        
        Ticket.AddTotal("",Ticket.DibujarLinea(29));
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("No. Productos",vv.lblcan.getText());
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("Subtotal","$ "+vv.txtsubtotal.getText());
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("Descuento",vv.txtdescuento.getText()+" %");
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("",Ticket.DarEspacio());
        /*Ticket.AddTotal("IVA","5.25");
        Ticket.AddTotal("",Ticket.DarEspacio());*/
        Ticket.AddTotal("TOTAL","$ "+vv.txttotal.getText());
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("Recibido","$ "+vv.txtpago.getText());
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("Cambio","$ "+vv.txtcambio.getText());
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddPieLinea(Ticket.DibujarLinea(29));
        Ticket.AddPieLinea(Ticket.DarEspacio());
        if(modelt.getRowCount() <= 0){
            
        }else{
            // Ticket.AddPieLinea(modelt.getValueAt(0, 9).toString());
            // Ticket.AddPieLinea(Ticket.DarEspacio());
            // Ticket.AddPieLinea(Ticket.DarEspacio());
            Ticket.AddPieLinea(Ticket.DarEspacio());
            Ticket.AddPieLinea(Ticket.DarEspacio());
            Ticket.AddPieLinea(Ticket.DarEspacio());
            Ticket.AddPieLinea(Ticket.DarEspacio());
        }
        /*Ticket.AddPieLinea("Contamos con Sistema de Apartado y Envíos a Domicilio");
        Ticket.AddPieLinea(Ticket.DarEspacio());
        Ticket.AddPieLinea("Horario: L-V 11:00 am - 7:30 pm");
        Ticket.AddPieLinea(Ticket.DarEspacio());
        Ticket.AddPieLinea("Sábados 11:00 am - 4:00 pm");
        Ticket.AddPieLinea(Ticket.DarEspacio());
        Ticket.AddPieLinea("Gracias por su compra!!!");
        Ticket.AddPieLinea(Ticket.DarEspacio());*/
        
        int a = 0;
        a = Ticket.ImprimirDocumento(imprimir);
        if(a == 1){
            return 1;
        }
        return 0;
    
    }
    
    public int reImprimirTicket(boolean imprimir) throws IOException{
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
                // Ticket.AddCabecera("Telefono: " + modelt.getValueAt(0, 5).toString());
                // Ticket.AddCabecera(Ticket.DarEspacio());
            }
            // Ticket.AddCabecera("Celular: " + modelt.getValueAt(0, 6).toString());
            // Ticket.AddCabecera(Ticket.DarEspacio());
            Ticket.AddCabecera(modelt.getValueAt(0, 7).toString() + ", " + modelt.getValueAt(0, 8).toString());
            Ticket.AddCabecera(Ticket.DarEspacio());
            //ev.txttexto.setText(modelt.getValueAt(0, 9).toString());
        }
        //Ticket.AddCabecera("BOUTIQUE GAMBOA");
        //Ticket.AddCabecera(Ticket.DarEspacio());
        //Ticket.AddCabecera("Av. De la Cultura # 79 Colonia Cd. del Valle");
        //Ticket.AddCabecera(Ticket.DarEspacio());
        //Ticket.AddCabecera("Teléfono: 311-372-93-61");
        //Ticket.AddCabecera(Ticket.DarEspacio());
        //Ticket.AddCabecera("Tepic, Nayarit");
        //Ticket.AddCabecera(Ticket.DarEspacio());
        Ticket.AddCabecera(Ticket.DibujarLinea(29));
        Ticket.AddCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera("Caja UNICA - Ticket # "+vv.c_folio_ve.getText());
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera(""+vv.c_fechaventa_ve.getText());
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera(Ticket.DibujarLinea(29));
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        /*Ticket.AddItem("1","Articulo Prueba","15.00");
        Ticket.AddItem("","",Ticket.DarEspacio());
        Ticket.AddItem("2","Articulo Prueba","25.00");
        Ticket.AddItem("","",Ticket.DarEspacio());*/
        
        double totalProductos = 0;
        int filas = 0;
        filas = vv.c_tbllis_ve.getRowCount();
        for (int i = 0; i < filas; i++) {
            //Ticket.AddItem(vv.c_tbllis_ve.getValueAt(i, 4).toString(),vv.tbllis.getValueAt(i,2).toString(),"$ "+vv.tbllis.getValueAt(i, 5).toString());
            Ticket.AddItem(vv.c_tbllis_ve.getValueAt(i, 3).toString(),vv.c_tbllis_ve.getValueAt(i,1).toString(),"$ "+vv.c_tbllis_ve.getValueAt(i, 4).toString());
            Ticket.AddItem("","",Ticket.DarEspacio());
            totalProductos = totalProductos + Double.parseDouble(vv.c_tbllis_ve.getValueAt(i, 3).toString());
        }
        
        Ticket.AddTotal("",Ticket.DibujarLinea(29));
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("No. Productos", String.valueOf(totalProductos) );
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("Subtotal",vv.c_subtotal_ve.getText());
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("Descuento",vv.c_descuento_ve.getText());
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("TOTAL",vv.c_total_ve.getText());
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("Recibido",vv.c_total_ve.getText());
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("Cambio","$ 0");
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddPieLinea(Ticket.DibujarLinea(29));
        Ticket.AddPieLinea(Ticket.DarEspacio());
        if(modelt.getRowCount() <= 0){
            
        }else{
            //Ticket.AddPieLinea(modelt.getValueAt(0, 9).toString());
            // Ticket.AddPieLinea(Ticket.DarEspacio());
            //Ticket.AddPieLinea(Ticket.DarEspacio());
            Ticket.AddPieLinea(Ticket.DarEspacio());
            Ticket.AddPieLinea(Ticket.DarEspacio());
            Ticket.AddPieLinea(Ticket.DarEspacio());
            Ticket.AddPieLinea(Ticket.DarEspacio());
        }
        
        /*Ticket.AddPieLinea("Contamos con Sistema de Apartado y Envíos a Domicilio");
        Ticket.AddPieLinea(Ticket.DarEspacio());
        Ticket.AddPieLinea("Horario: L-V 11:00 am - 7:30 pm");
        Ticket.AddPieLinea(Ticket.DarEspacio());
        Ticket.AddPieLinea("Sábados 11:00 am - 4:00 pm");
        Ticket.AddPieLinea(Ticket.DarEspacio());
        Ticket.AddPieLinea("Gracias por su compra!!!");
        Ticket.AddPieLinea(Ticket.DarEspacio());*/
        
        int a = 0;
        a = Ticket.ImprimirDocumento(imprimir);
        if(a == 1){
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
                // Ticket.AddCabecera("Telefono: " + modelt.getValueAt(0, 5).toString());
                // Ticket.AddCabecera(Ticket.DarEspacio());
            }
            // Ticket.AddCabecera("Celular: " + modelt.getValueAt(0, 6).toString());
            // Ticket.AddCabecera(Ticket.DarEspacio());
            Ticket.AddCabecera(modelt.getValueAt(0, 7).toString() + ", " + modelt.getValueAt(0, 8).toString());
            Ticket.AddCabecera(Ticket.DarEspacio());
            //ev.txttexto.setText(modelt.getValueAt(0, 9).toString());
        }
        //Ticket.AddCabecera("BOUTIQUE GAMBOA");
        //Ticket.AddCabecera(Ticket.DarEspacio());
        //Ticket.AddCabecera("Av. De la Cultura # 79 Colonia Cd. del Valle");
        //Ticket.AddCabecera(Ticket.DarEspacio());
        //Ticket.AddCabecera("Teléfono: 311-372-93-61");
        //Ticket.AddCabecera(Ticket.DarEspacio());
        //Ticket.AddCabecera("Tepic, Nayarit");
        //Ticket.AddCabecera(Ticket.DarEspacio());
        Ticket.AddCabecera(Ticket.DibujarLinea(29));
        Ticket.AddCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera("Caja UNICA");
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera("Cajero: " + GlobalVaribales.getInstance().nombreLogueado);
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera(fechaFormateada);
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera(""+vv.c_fechaventa_ve.getText());
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera(Ticket.DibujarLinea(29));
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        
        Ticket.AddSubCabecera("Monto de " + tipo + ": " +String.valueOf(monto));
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera("Comentarios:");
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera(comentarios);
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        
        /* double totalProductos = 0;
        int filas = 0;
        filas = vv.c_tbllis_ve.getRowCount();
        for (int i = 0; i < filas; i++) {
            //Ticket.AddItem(vv.c_tbllis_ve.getValueAt(i, 4).toString(),vv.tbllis.getValueAt(i,2).toString(),"$ "+vv.tbllis.getValueAt(i, 5).toString());
            Ticket.AddItem(vv.c_tbllis_ve.getValueAt(i, 3).toString(),vv.c_tbllis_ve.getValueAt(i,1).toString(),"$ "+vv.c_tbllis_ve.getValueAt(i, 4).toString());
            Ticket.AddItem("","",Ticket.DarEspacio());
            totalProductos = totalProductos + Double.parseDouble(vv.c_tbllis_ve.getValueAt(i, 3).toString());
        } */
        
        /*Ticket.AddTotal("",Ticket.DibujarLinea(29));
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("Monto de " + tipo + ":", String.valueOf(monto) );
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("Comentarios:","");
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("",comentarios);
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("TOTAL",vv.c_total_ve.getText());
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("Recibido",vv.c_total_ve.getText());
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("Cambio","$ 0");
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddPieLinea(Ticket.DibujarLinea(29));
        Ticket.AddPieLinea(Ticket.DarEspacio());*/
        if(modelt.getRowCount() <= 0){
            
        }else{
            //Ticket.AddPieLinea(modelt.getValueAt(0, 9).toString());
            // Ticket.AddPieLinea(Ticket.DarEspacio());
            //Ticket.AddPieLinea(Ticket.DarEspacio());
            Ticket.AddPieLinea(Ticket.DarEspacio());
            Ticket.AddPieLinea(Ticket.DarEspacio());
            Ticket.AddPieLinea(Ticket.DarEspacio());
            Ticket.AddPieLinea(Ticket.DarEspacio());
        }
        
        /*Ticket.AddPieLinea("Contamos con Sistema de Apartado y Envíos a Domicilio");
        Ticket.AddPieLinea(Ticket.DarEspacio());
        Ticket.AddPieLinea("Horario: L-V 11:00 am - 7:30 pm");
        Ticket.AddPieLinea(Ticket.DarEspacio());
        Ticket.AddPieLinea("Sábados 11:00 am - 4:00 pm");
        Ticket.AddPieLinea(Ticket.DarEspacio());
        Ticket.AddPieLinea("Gracias por su compra!!!");
        Ticket.AddPieLinea(Ticket.DarEspacio());*/
        
        int a = 0;
        a = Ticket.ImprimirDocumento(imprimir);
        if(a == 1){
            return 1;
        }
        return 0;
    
    }
    
    public void imprimirTicketNoSeUsa(JTable tbllis, String subTotal, String total, String dineroR, String devolucion){
        /*try {

            PrinterMatrix printer = new PrinterMatrix();
            Extenso e = new Extenso();

            e.setNumber(20.30);
            //Definir el tamanho del papel para la impresion de dinamico y 32 columnas
            int filas = tbllis.getRowCount();
            int tamaño = filas+40;
            printer.setOutSize(tamaño, 80);

            //Imprimir = 1ra linea de la columa de 1 a 32
            printer.printTextWrap(0, 1, 5, 80, "===================================================================");
            printer.printTextWrap(1, 1, 35, 80, "CARNIVOROS"); //Nombre establecimiento
            printer.printTextWrap(3, 1, 31, 80, "Barrio Bellavista"); //Barrio
            printer.printTextWrap(4, 1, 37, 80, "Clle 47"); //Direccion
            printer.printTextWrap(5, 1, 38, 80, "12345"); //Codigo Postal
            printer.printTextWrap(6, 1, 10, 41, "Fecha"); //Aqui va la fecha de recibo
            printer.printTextWrap(6, 1, 38, 80, "Hora"); //Aqui va la hora de recibo
            printer.printTextWrap(7, 1, 3, 80, "Numero"); //Numero del recibo - FACTURA O PEDIDO
            printer.printTextWrap(8, 1, 3, 80, "Atiende"); //Nombre Cajero
            printer.printTextWrap(9, 1, 3, 80, "Cliente");//Nombre del Cliente
            printer.printTextWrap(10,1, 5, 80, "——————————–——————————–——————————–——————————–——–———–——–—–———–——–———");
            printer.printTextWrap(11,1, 7, 80, "ID   DESCRIPCION                        CANT           PRECIO");
            printer.printTextWrap(12,1, 0, 80, " ");

            for (int i = 0; i < filas; i++) {
                int p = 13+i; //Fila

                printer.printTextWrap(p , 1, 7, 19 , tbllis.getValueAt(i,0).toString());
                printer.printTextWrap(p , 1, 12, 42 , tbllis.getValueAt(i,1).toString());
                printer.printTextWrap(p , 1, 47, 49, tbllis.getValueAt(i,2).toString());

                String pre= printer.alinharADireita(10, tbllis.getValueAt(i,3).toString());
                printer.printTextWrap(p , 1, 57, 80, pre);

                //String inp= printer.alinharADireita(7,punto_Venta.jtbl_venta.getValueAt(i,6).toString());
                //printer.printTextWrap(p , 1, 25, 32, inp);
            }
            DecimalFormat formateador = new DecimalFormat("#.###");

            String sub= printer.alinharADireita(10, subTotal);
            printer.printTextWrap(filas+17, 1, 5, 80, "Subtotal: ");
            printer.printTextWrap(filas+17, 1, 20, 80, "$"+sub);

            String tot= printer.alinharADireita(10, total);
            printer.printTextWrap(filas+18, 1, 5, 80, "Total a pagar: ");
            printer.printTextWrap(filas+18, 1, 20, 80, "$"+tot);

            String efe= printer.alinharADireita(10,dineroR);
            printer.printTextWrap(filas+19, 1, 5, 80, "Efectivo : ");
            printer.printTextWrap(filas+19, 1, 20, 80, "$"+efe);

            String cam= printer.alinharADireita(10,devolucion);
            printer.printTextWrap(filas+20, 1, 5, 80, "Cambio : ");
            printer.printTextWrap(filas+20, 1, 20, 80, "$"+ cam);

            printer.printTextWrap(filas+21, 1, 5, 80, "——————————–——————————–——————————–——————————–——–———–——–—–———–——–———");
            printer.printTextWrap(filas+22, 1, 26,80, "!Gracias por su preferencia!");
            printer.printTextWrap(filas+23, 1, 30, 80, "WorkIt App - v1.0.0");
            printer.printTextWrap(filas+24, 1, 31, 80, "Software a Medida");
            printer.printTextWrap(filas+25, 1, 25, 80, "Contacto: workitapp@gmail.com");
            printer.printTextWrap(filas+26, 1, 5,80, "===================================================================");


            ///CREAR ARCHIVO EN CARPETA DEL PROYECTO PARA PEDIDOS
            printer.toFile("C:\\tmp\\impresion.txt");
            FileInputStream inputStream = null;

            try {
                inputStream = new FileInputStream("C:\\tmp\\impresion.txt");
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al guardar");
            }
            if (inputStream == null) {
                return;
            }

            DocFlavor docFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
            Doc document = new SimpleDoc(inputStream, docFormat, null);
            PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();
            PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();

            if (defaultPrintService != null) {
                DocPrintJob printJob = defaultPrintService.createPrintJob();
                try {
                    printJob.print(document, attributeSet);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                System.err.println("No existen impresoras instaladas");
            }

            inputStream.close();
            //imprimirFin(subTotal, total, dineroR, devolucion); //ESTE METODO NO SE UTILIZARA

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al imprimir "+e);
        }*/
    }
    
    public int ActualizarEstatus_OS(int filaOS){
        int id_ven = 0;
        String fol_ven = null;
        double tot_ven = 0;
        int can_ven = 0;
        int des_ven = 0;
        String fec_ent = null;
        String dir_ent = null;
        String cli_ent = null;
        int met_pag = 0;
        int tip_ven = 0;
        String fec_reg = null;
        int sta_ven = 0;
        String ref_ven = null;
        String fec_surtido = null;
        String fec_cancela = null;
        
        id_ven = -4;
        //fol_ven = vv.tbllis_os.getValueAt(filaOS, 1).toString();
        sta_ven = 2;
        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        fec_surtido = sdf.format(dt);
                
        vm.setId_ven(id_ven);
        vm.setFol_ven(fol_ven);
        vm.setTot_ven(tot_ven);
        vm.setCan_ven(can_ven);
        vm.setDes_ven(des_ven);
        vm.setFec_ent(fec_ent);
        vm.setDir_ent(dir_ent);
        vm.setCli_ent(cli_ent);
        vm.setMet_pag(met_pag);
        vm.setTip_ven(tip_ven);
        vm.setFec_reg(fec_reg);
        vm.setSta_ven(sta_ven);
        vm.setRef_ven(ref_ven);
        vm.setFec_surtido(fec_surtido);
        vm.setFec_cancela(fec_cancela);
        
        if(vm.GuardarVentaOrden()){
            DefaultTableModel modeltDetalle = new DefaultTableModel();
            int id = 0;
            double tot = 0;
            int can = 0;
            int idpro = 0;
            String fol_dven = null;
            id = -2;
            tot = 0;
            can = 0;
            idpro = 0;
            fol_dven = fol_ven;
            vm.setId(id);
            vm.setTot(tot);
            vm.setCan(can);
            vm.setId_pro(idpro);
            vm.setFol_dven(fol_dven);
            modeltDetalle = vm.CargarDetalleVenta();
            int contador = 0;
            
            for (int i = 0; i < modeltDetalle.getRowCount(); i++) {
                int id_Pro = 0;
                int cant = 0;
                id_Pro = Integer.parseInt(modeltDetalle.getValueAt(i, 3).toString());
                cant = Integer.parseInt(modeltDetalle.getValueAt(i, 2).toString());
                pm.setId(-6);
                pm.setCod(null);
                pm.setNom(null);
                pm.setSa(cant);
                pm.setSm(id_Pro);
                pm.setSx(0);
                pm.setPre(0);
                pm.setCat(null);
                pm.setEst(0);
                if(pm.GuardarProductos()){
                    
                }else{
                    contador = contador + 1;
                }
            }
            if(contador == 0){
                return 1;
            }else{
                return 0;
            }
        }
        return 0;
    }
    
    /*SI se usa*/ public int GuardarVenta(){
        int id_ven = 0;
        String fol_ven = null;
        double tot_ven = 0;
        double can_ven = 0;
        int des_ven = 0;
        int met_pag = 0;
        int tip_ven = 0;
        String fec_reg = null;
        int sta_ven = 0;
        String ref_ven = null;
        double subtot_ven = 0;
        
        id_ven = 0;
        fol_ven = null;
        can_ven = Double.parseDouble(vv.lblcan.getText());
        
        met_pag = vv.pescobrar.getSelectedIndex();
        //tip_ven = vv.pesprincipal.getSelectedIndex();
        tip_ven = 0;
        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.text.SimpleDateFormat sdf_T = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        fec_reg = sdf.format(dt);
        fech_reg_Tick = sdf_T.format(dt);
        sta_ven = 1;
        ref_ven = null;
        subtot_ven = Double.parseDouble(vv.txtsubtotal.getText());
        tot_ven = Double.parseDouble(vv.txttotal.getText());
        des_ven = Integer.parseInt(vv.txtdescuento.getText());
        
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
        
        if(vm.GuardarVentaOrden()){
            return 1;
        }
        return 0;
    }
    
    /*SI se usa*/ public int GuardarDetalleVenta(){
        int filasDet = 0;
        int id = 0;
        double tot = 0;
        double can = 0;
        int idpro = 0;
        int contador = 0;
        filasDet = vv.tbllis.getRowCount();
        for (int i = 0; i < filasDet; i++) {
            id = 0;
            tot = 0;
            can = 0;
            idpro = 0;
            
            id = 0;
            tot = Double.parseDouble(vv.tbllis.getValueAt(i, 5).toString());
            can = Double.parseDouble(vv.tbllis.getValueAt(i, 4).toString());
            idpro = Integer.parseInt(vv.tbllis.getValueAt(i, 0).toString());
            
            vm.setId(id);
            vm.setTot(tot);
            vm.setCan(can);
            vm.setId_pro(idpro);
            
            if(!vm.GuardarDetalleVenta()){
                contador = contador + 1;
            }            
        }
        if(contador > 0){
            return contador;
        }
        return 0;
    }
    
    public void CargarOrdenesServicio(){
        /*DefaultTableModel modelt = new DefaultTableModel(){
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
        String fec_ent = null;
        String dir_ent = null;
        String cli_ent = null;
        int met_pag = 0;
        int tip_ven = 0;
        String fec_reg = null;
        int sta_ven = 0;
        String ref_ven = null;
        
        id_ven = -1;
        tip_ven = 1;
        Date fecha_de;
        Date fecha_al;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        fecha_de = vv.dtc_de.getDate();
        fecha_al = vv.dtc_al.getDate();
        fec_ent = sdf.format(fecha_de)+" 00:00:00";
        fec_reg = sdf.format(fecha_al)+" 23:59:59";
        switch (vv.cmbest_os.getSelectedIndex()) {
            case 0:
                ref_ven = "1,2";
                break;
            case 1:
                ref_ven = "1";
                break;
            case 2:
                ref_ven = "2";
                break;
            default:
                break;
        }
        
        vm.setId_ven(id_ven);
        vm.setFol_ven(fol_ven);
        vm.setTot_ven(tot_ven);
        vm.setCan_ven(can_ven);
        vm.setDes_ven(des_ven);
        vm.setFec_ent(fec_ent);
        vm.setDir_ent(dir_ent);
        vm.setCli_ent(cli_ent);
        vm.setMet_pag(met_pag);
        vm.setTip_ven(tip_ven);
        vm.setFec_reg(fec_reg);
        vm.setSta_ven(sta_ven);
        vm.setRef_ven(ref_ven);
        
        modeltLis1 = vm.CargarOrdenesServicio();
        
        vv.tbllis_os.setModel(modeltLis1);
        
        vv.tbllis_os.getColumnModel().getColumn(0).setMaxWidth(0);
        vv.tbllis_os.getColumnModel().getColumn(0).setMinWidth(0);
        vv.tbllis_os.getColumnModel().getColumn(0).setPreferredWidth(0);
        vv.tbllis_os.getColumnModel().getColumn(1).setMaxWidth(75);
        vv.tbllis_os.getColumnModel().getColumn(1).setMinWidth(75);
        vv.tbllis_os.getColumnModel().getColumn(1).setPreferredWidth(75);
        vv.tbllis_os.getColumnModel().getColumn(2).setMaxWidth(75);
        vv.tbllis_os.getColumnModel().getColumn(2).setMinWidth(75);
        vv.tbllis_os.getColumnModel().getColumn(2).setPreferredWidth(75);
        vv.tbllis_os.getColumnModel().getColumn(3).setMaxWidth(0);
        vv.tbllis_os.getColumnModel().getColumn(3).setMinWidth(0);
        vv.tbllis_os.getColumnModel().getColumn(3).setPreferredWidth(0);
        vv.tbllis_os.getColumnModel().getColumn(4).setMaxWidth(75);
        vv.tbllis_os.getColumnModel().getColumn(4).setMinWidth(75);
        vv.tbllis_os.getColumnModel().getColumn(4).setPreferredWidth(75);
        vv.tbllis_os.getColumnModel().getColumn(5).setMaxWidth(75);
        vv.tbllis_os.getColumnModel().getColumn(5).setMinWidth(75);
        vv.tbllis_os.getColumnModel().getColumn(5).setPreferredWidth(75);
        vv.tbllis_os.getColumnModel().getColumn(6).setMaxWidth(150);
        vv.tbllis_os.getColumnModel().getColumn(6).setMinWidth(150);
        vv.tbllis_os.getColumnModel().getColumn(6).setPreferredWidth(150);
        vv.tbllis_os.getColumnModel().getColumn(9).setMaxWidth(0);
        vv.tbllis_os.getColumnModel().getColumn(9).setMinWidth(0);
        vv.tbllis_os.getColumnModel().getColumn(9).setPreferredWidth(0);
        vv.tbllis_os.getColumnModel().getColumn(10).setMaxWidth(0);
        vv.tbllis_os.getColumnModel().getColumn(10).setMinWidth(0);
        vv.tbllis_os.getColumnModel().getColumn(10).setPreferredWidth(0);
        vv.tbllis_os.getColumnModel().getColumn(11).setMaxWidth(0);
        vv.tbllis_os.getColumnModel().getColumn(11).setMinWidth(0);
        vv.tbllis_os.getColumnModel().getColumn(11).setPreferredWidth(0);
        vv.tbllis_os.getColumnModel().getColumn(12).setMaxWidth(150);
        vv.tbllis_os.getColumnModel().getColumn(12).setMinWidth(150);
        vv.tbllis_os.getColumnModel().getColumn(12).setPreferredWidth(150);
        vv.tbllis_os.getColumnModel().getColumn(13).setMaxWidth(75);
        vv.tbllis_os.getColumnModel().getColumn(13).setMinWidth(75);
        vv.tbllis_os.getColumnModel().getColumn(13).setPreferredWidth(75);
        vv.tbllis_os.getColumnModel().getColumn(14).setMaxWidth(0);
        vv.tbllis_os.getColumnModel().getColumn(14).setMinWidth(0);
        vv.tbllis_os.getColumnModel().getColumn(14).setPreferredWidth(0);
        vv.tbllis_os.getColumnModel().getColumn(15).setMaxWidth(0);
        vv.tbllis_os.getColumnModel().getColumn(15).setMinWidth(0);
        vv.tbllis_os.getColumnModel().getColumn(15).setPreferredWidth(0);
        centrar.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
        for (int i = 0; i <= 14; i++) {
            vv.tbllis_os.getColumnModel().getColumn(i).setCellRenderer(centrar);
        }*/
    }
    
    /*SI se usa*/ public void CargarVentasCancelar(){
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
        switch(vv.cmbest_ven.getSelectedIndex()){
            case 0:
                ref_ven = "0,1";
                break;
            case 1:
                ref_ven = "1";
                break;
            case 2:
                ref_ven = "0";
                break;
            default:
                ref_ven = "0,1";
                break;
        }
        Date fecha_de;
        Date fecha_al;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        fecha_de = vv.fechaDe.getDate();
        fecha_al = vv.fechaAl.getDate();
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
        
        vv.tbllis_ven.setModel(modeltLisCV);
        
        vv.tbllis_ven.getColumnModel().getColumn(0).setMaxWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(0).setMinWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(0).setPreferredWidth(0);
        centrar.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
        for (int i = 0; i <= 8; i++) {
            vv.tbllis_ven.getColumnModel().getColumn(i).setCellRenderer(centrar);
        }
        /*vv.tbllis_ven.getColumnModel().getColumn(1).setMaxWidth(175);
        vv.tbllis_ven.getColumnModel().getColumn(1).setMinWidth(175);
        vv.tbllis_ven.getColumnModel().getColumn(1).setPreferredWidth(175);
        vv.tbllis_ven.getColumnModel().getColumn(2).setMaxWidth(175);
        vv.tbllis_ven.getColumnModel().getColumn(2).setMinWidth(175);
        vv.tbllis_ven.getColumnModel().getColumn(2).setPreferredWidth(175);
        vv.tbllis_ven.getColumnModel().getColumn(3).setMaxWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(3).setMinWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(3).setPreferredWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(4).setMaxWidth(125);
        vv.tbllis_ven.getColumnModel().getColumn(4).setMinWidth(125);
        vv.tbllis_ven.getColumnModel().getColumn(4).setPreferredWidth(125);
        vv.tbllis_ven.getColumnModel().getColumn(5).setMaxWidth(125);
        vv.tbllis_ven.getColumnModel().getColumn(5).setMinWidth(125);
        vv.tbllis_ven.getColumnModel().getColumn(5).setPreferredWidth(125);
        vv.tbllis_ven.getColumnModel().getColumn(6).setMaxWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(6).setMinWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(6).setPreferredWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(7).setMaxWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(7).setMinWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(7).setPreferredWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(8).setMaxWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(8).setMinWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(8).setPreferredWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(9).setMaxWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(9).setMinWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(9).setPreferredWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(10).setMaxWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(10).setMinWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(10).setPreferredWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(11).setMaxWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(11).setMinWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(11).setPreferredWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(12).setMaxWidth(300);
        vv.tbllis_ven.getColumnModel().getColumn(12).setMinWidth(300);
        vv.tbllis_ven.getColumnModel().getColumn(12).setPreferredWidth(300);
        vv.tbllis_ven.getColumnModel().getColumn(13).setMaxWidth(125);
        vv.tbllis_ven.getColumnModel().getColumn(13).setMinWidth(125);
        vv.tbllis_ven.getColumnModel().getColumn(13).setPreferredWidth(125);
        vv.tbllis_ven.getColumnModel().getColumn(14).setMaxWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(14).setMinWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(14).setPreferredWidth(0);*/
        /*centrar.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
        for (int i = 0; i <= 15; i++) {
            vv.tbllis_ven.getColumnModel().getColumn(i).setCellRenderer(centrar);
        }*/
        
    }
    
    /*SI se usa*/ public int ActualizarEstatus_VE(int filaVenCan){
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
        String fec_cancela = null;
        
        id_ven = -5;
        fol_ven = vv.tbllis_ven.getValueAt(filaVenCan, 1).toString();
        sta_ven = 0;
        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        fec_cancela = sdf.format(dt);
        
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
        vm.setFec_cancela(fec_cancela);
        
        if(vm.GuardarVentaOrden()){
            return 1;
        }
        return 0;
    }
    
    /*SI se usa*/ public void RegresarStockVenta(int filaVenCan){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        int id = -2;
        double tot = 0;
        int cant = 0;
        int id_pro = 0;
        String folio = null;
        
        folio = vv.tbllis_ven.getValueAt(filaVenCan, 1).toString();
        
        vm.setId(id);
        vm.setTot(tot);
        vm.setCan(cant);
        vm.setId_pro(id_pro);
        vm.setFol_dven(folio);
        
        modelt = vm.CargarDetalleVenta();
        
        int filasModel = 0;
        filasModel  = modelt.getRowCount();
        for (int i = 0; i < filasModel; i++) {
            //System.out.println("Cantidad: " + modelt.getValueAt(i, 2).toString());
            //System.out.println("Producto: " + modelt.getValueAt(i, 3).toString());
            int id_Pro = 0;
            double cantD = 0;
            id_Pro = Integer.parseInt(modelt.getValueAt(i, 3).toString());
            cantD = Double.parseDouble(modelt.getValueAt(i, 2).toString());
            pm.setId(-7);
            pm.setCod(null);
            pm.setNom(null);
            pm.setSa(cantD);
            pm.setSm(id_Pro);
            pm.setSx(0);
            pm.setPre(0);
            pm.setCat(null);
            pm.setEst(0);
            pm.GuardarProductos();
        }
        
    }
    
    public void CargarOrdenCancelar(){
        /*DefaultTableModel modelt = new DefaultTableModel(){
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
        String fec_ent = null;
        String dir_ent = null;
        String cli_ent = null;
        int met_pag = 0;
        int tip_ven = 0;
        String fec_reg = null;
        int sta_ven = 0;
        String ref_ven = null;
        double subtot_ven = 0;
        
        id_ven = -6;
        tip_ven = 1;
        switch(vv.cmbest_ord.getSelectedIndex()){
            case 0:
                ref_ven = "0,1,2";
                break;
            case 1:
                ref_ven = "1";
                break;
            case 2:
                ref_ven = "2";
                break;
            case 3:
                ref_ven = "0";
                break;
            default:
                ref_ven = "0,1,2";
                break;
        }
        Date fecha_de;
        Date fecha_al;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        fecha_de = vv.dtc_de_ord.getDate();
        fecha_al = vv.dtc_al_ord.getDate();
        fec_ent = sdf.format(fecha_de)+" 00:00:00";
        fec_reg = sdf.format(fecha_al)+" 23:59:59";
        
        
        vm.setId_ven(id_ven);
        vm.setFol_ven(fol_ven);
        vm.setTot_ven(tot_ven);
        vm.setCan_ven(can_ven);
        vm.setDes_ven(des_ven);
        vm.setFec_ent(fec_ent);
        vm.setDir_ent(dir_ent);
        vm.setCli_ent(cli_ent);
        vm.setMet_pag(met_pag);
        vm.setTip_ven(tip_ven);
        vm.setFec_reg(fec_reg);
        vm.setSta_ven(sta_ven);
        vm.setRef_ven(ref_ven);
        vm.setSubtot_ven(subtot_ven);
        
        modeltLisCO = vm.CargarOrdenesServicio();
        
        vv.tbllis_ord.setModel(modeltLisCO);
        
        vv.tbllis_ord.getColumnModel().getColumn(0).setMaxWidth(0);
        vv.tbllis_ord.getColumnModel().getColumn(0).setMinWidth(0);
        vv.tbllis_ord.getColumnModel().getColumn(0).setPreferredWidth(0);
        vv.tbllis_ord.getColumnModel().getColumn(1).setMaxWidth(75);
        vv.tbllis_ord.getColumnModel().getColumn(1).setMinWidth(75);
        vv.tbllis_ord.getColumnModel().getColumn(1).setPreferredWidth(75);
        vv.tbllis_ord.getColumnModel().getColumn(2).setMaxWidth(75);
        vv.tbllis_ord.getColumnModel().getColumn(2).setMinWidth(75);
        vv.tbllis_ord.getColumnModel().getColumn(2).setPreferredWidth(75);
        vv.tbllis_ord.getColumnModel().getColumn(3).setMaxWidth(0);
        vv.tbllis_ord.getColumnModel().getColumn(3).setMinWidth(0);
        vv.tbllis_ord.getColumnModel().getColumn(3).setPreferredWidth(0);
        vv.tbllis_ord.getColumnModel().getColumn(4).setMaxWidth(75);
        vv.tbllis_ord.getColumnModel().getColumn(4).setMinWidth(75);
        vv.tbllis_ord.getColumnModel().getColumn(4).setPreferredWidth(75);
        vv.tbllis_ord.getColumnModel().getColumn(5).setMaxWidth(75);
        vv.tbllis_ord.getColumnModel().getColumn(5).setMinWidth(75);
        vv.tbllis_ord.getColumnModel().getColumn(5).setPreferredWidth(75);
        vv.tbllis_ord.getColumnModel().getColumn(9).setMaxWidth(0);
        vv.tbllis_ord.getColumnModel().getColumn(9).setMinWidth(0);
        vv.tbllis_ord.getColumnModel().getColumn(9).setPreferredWidth(0);
        vv.tbllis_ord.getColumnModel().getColumn(10).setMaxWidth(0);
        vv.tbllis_ord.getColumnModel().getColumn(10).setMinWidth(0);
        vv.tbllis_ord.getColumnModel().getColumn(10).setPreferredWidth(0);
        vv.tbllis_ord.getColumnModel().getColumn(11).setMaxWidth(0);
        vv.tbllis_ord.getColumnModel().getColumn(11).setMinWidth(0);
        vv.tbllis_ord.getColumnModel().getColumn(11).setPreferredWidth(0);
        vv.tbllis_ord.getColumnModel().getColumn(12).setMaxWidth(150);
        vv.tbllis_ord.getColumnModel().getColumn(12).setMinWidth(150);
        vv.tbllis_ord.getColumnModel().getColumn(12).setPreferredWidth(150);
        vv.tbllis_ord.getColumnModel().getColumn(13).setMaxWidth(75);
        vv.tbllis_ord.getColumnModel().getColumn(13).setMinWidth(75);
        vv.tbllis_ord.getColumnModel().getColumn(13).setPreferredWidth(75);
        centrar.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
        for (int i = 0; i <= 15; i++) {
            vv.tbllis_ord.getColumnModel().getColumn(i).setCellRenderer(centrar);
        }*/
    }
    
    public int ActualizarEstatus_COS(int filaOrdCan){
        int id_ven = 0;
        String fol_ven = null;
        double tot_ven = 0;
        int can_ven = 0;
        int des_ven = 0;
        String fec_ent = null;
        String dir_ent = null;
        String cli_ent = null;
        int met_pag = 0;
        int tip_ven = 0;
        String fec_reg = null;
        int sta_ven = 0;
        String ref_ven = null;
        double subtot_ven = 0;
        String fec_surtido = null;
        String fec_cancela = null;
        
        
        id_ven = -5;
        //fol_ven = vv.tbllis_ord.getValueAt(filaOrdCan, 1).toString();
        sta_ven = 0;
        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        fec_cancela = sdf.format(dt);
        
        vm.setId_ven(id_ven);
        vm.setFol_ven(fol_ven);
        vm.setTot_ven(tot_ven);
        vm.setCan_ven(can_ven);
        vm.setDes_ven(des_ven);
        vm.setFec_ent(fec_ent);
        vm.setDir_ent(dir_ent);
        vm.setCli_ent(cli_ent);
        vm.setMet_pag(met_pag);
        vm.setTip_ven(tip_ven);
        vm.setFec_reg(fec_reg);
        vm.setSta_ven(sta_ven);
        vm.setRef_ven(ref_ven);
        vm.setSubtot_ven(subtot_ven);
        vm.setFec_cancela(fec_cancela);
        vm.setFec_surtido(fec_surtido);
        
        if(vm.GuardarVentaOrden()){
            return 1;
        }
        return 0;
    }
    
    public void RegresarStockOrden(int filaOrdCan){
        /*DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        int id = -2;
        double tot = 0;
        int cant = 0;
        int id_pro = 0;
        String folio = null;
        
        folio = vv.tbllis_ord.getValueAt(filaOrdCan, 1).toString();
        
        vm.setId(id);
        vm.setTot(tot);
        vm.setCan(cant);
        vm.setId_pro(id_pro);
        vm.setFol_dven(folio);
        
        modelt = vm.CargarDetalleVenta();
        
        int filasModel = 0;
        filasModel  = modelt.getRowCount();
        for (int i = 0; i < filasModel; i++) {
            //System.out.println("Cantidad: " + modelt.getValueAt(i, 2).toString());
            //System.out.println("Producto: " + modelt.getValueAt(i, 3).toString());
            int id_Pro = 0;
            int cantD = 0;
            id_Pro = Integer.parseInt(modelt.getValueAt(i, 3).toString());
            cantD = Integer.parseInt(modelt.getValueAt(i, 2).toString());
            pm.setId(-7);
            pm.setCod(null);
            pm.setNom(null);
            pm.setSa(cantD);
            pm.setSm(id_Pro);
            pm.setSx(0);
            pm.setPre(0);
            pm.setCat(null);
            pm.setCol(null);
            pm.setTam(null);
            pm.setHor(null);
            pm.setEst(0);
            pm.GuardarProductos();
        }
        */
    }
    
    public void CargarConsultaDetalle(String Folio){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        int id = -3;
        double tot = 0;
        int can = 0;
        int idpro = 0;
        String folVen = Folio;
        
        vm.setId(id);
        vm.setTot(tot);
        vm.setCan(can);
        vm.setId_pro(idpro);
        vm.setFol_dven(folVen);
        
        c_modeltLisOS = vm.CargarDetalleVenta();
        
        vv.c_tbllis_os.setModel(c_modeltLisOS);
        
        vv.c_tbllis_os.getColumnModel().getColumn(0).setMaxWidth(130);
        vv.c_tbllis_os.getColumnModel().getColumn(0).setMinWidth(130);
        vv.c_tbllis_os.getColumnModel().getColumn(0).setPreferredWidth(130);
        vv.c_tbllis_os.getColumnModel().getColumn(2).setMaxWidth(100);
        vv.c_tbllis_os.getColumnModel().getColumn(2).setMinWidth(100);
        vv.c_tbllis_os.getColumnModel().getColumn(2).setPreferredWidth(100);
        vv.c_tbllis_os.getColumnModel().getColumn(3).setMaxWidth(100);
        vv.c_tbllis_os.getColumnModel().getColumn(3).setMinWidth(100);
        vv.c_tbllis_os.getColumnModel().getColumn(3).setPreferredWidth(100);
        vv.c_tbllis_os.getColumnModel().getColumn(4).setMaxWidth(100);
        vv.c_tbllis_os.getColumnModel().getColumn(4).setMinWidth(100);
        vv.c_tbllis_os.getColumnModel().getColumn(4).setPreferredWidth(100);
        centrar.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
        for (int i = 0; i <= 4; i++) {
            vv.c_tbllis_os.getColumnModel().getColumn(i).setCellRenderer(centrar);
        }
        
    }
    
    public void CargarConsultaDetalle2(String Folio){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        int id = -3;
        double tot = 0;
        int can = 0;
        int idpro = 0;
        String folVen = Folio;
        
        vm.setId(id);
        vm.setTot(tot);
        vm.setCan(can);
        vm.setId_pro(idpro);
        vm.setFol_dven(folVen);
        
        c_modeltLisVE = vm.CargarDetalleVenta();
        
        vv.c_tbllis_ve.setModel(c_modeltLisVE);
        
        vv.c_tbllis_ve.getColumnModel().getColumn(0).setMaxWidth(130);
        vv.c_tbllis_ve.getColumnModel().getColumn(0).setMinWidth(130);
        vv.c_tbllis_ve.getColumnModel().getColumn(0).setPreferredWidth(130);
        vv.c_tbllis_ve.getColumnModel().getColumn(2).setMaxWidth(100);
        vv.c_tbllis_ve.getColumnModel().getColumn(2).setMinWidth(100);
        vv.c_tbllis_ve.getColumnModel().getColumn(2).setPreferredWidth(100);
        vv.c_tbllis_ve.getColumnModel().getColumn(3).setMaxWidth(100);
        vv.c_tbllis_ve.getColumnModel().getColumn(3).setMinWidth(100);
        vv.c_tbllis_ve.getColumnModel().getColumn(3).setPreferredWidth(100);
        vv.c_tbllis_ve.getColumnModel().getColumn(4).setMaxWidth(100);
        vv.c_tbllis_ve.getColumnModel().getColumn(4).setMinWidth(100);
        vv.c_tbllis_ve.getColumnModel().getColumn(4).setPreferredWidth(100);
        centrar.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
        for (int i = 0; i <= 4; i++) {
            vv.c_tbllis_ve.getColumnModel().getColumn(i).setCellRenderer(centrar);
        }
        
    }
    
    public void cobrarConEnter(){
        double total = 0;
        double pago = 0;
        total = Double.parseDouble(vv.txttotal.getText());
        if(vv.txtpago.getText().isEmpty()){
            pago = 0;
        }else{
            pago = Double.parseDouble(vv.txtpago.getText());
        }
        if(pago < total){
            JOptionPane.showMessageDialog(vv, "Introducir una cantidad en el Campo \"Pago\"\nIgual o Mayor al Total");
        }else{
            int guaVen=0;
            guaVen = GuardarVenta();
            if(guaVen <= 0){
                JOptionPane.showMessageDialog(vv, "Error al Guarar la venta, Contacte a Soporte Técnico!!!");
            }else{
                int guaDet = 0;
                guaDet = GuardarDetalleVenta();
                if(guaDet > 0){
                    JOptionPane.showMessageDialog(vv, "Error al Guardar los Productos de la Venta, Contacte a Soporte Técnico");
                }else{
                    // Espacio para Imprimir Ticket Antes de Regresar al Inicio
                    int a = 0;
                    try {
                        a = imprimirTicket(true);
                    } catch (IOException ex) {
                        Logger.getLogger(VentasC.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if(a == 1){

                    }else{
                        JOptionPane.showMessageDialog(vv, "El Ticket no se Imprimió, Contacte a Soporte Técnico");
                    }
                    // Fin de Imprimir Ticket
                    vv.pnlcajarapida.setVisible(true);
                    vv.pnlcobro.setVisible(false);
                    vv.btnsalir.setText("Salir");
                    vv.btncobrar.setEnabled(true);
                    //Obtener Subtotal y Total
                    vv.txtsubtotal.setText("");
                    vv.txttotal.setText("");
                    //Descuento
                    vv.txtdescuento.setText("");
                    //Paga Con
                    vv.txtpago.setText("");
                    //Cambio
                    vv.txtcambio.setText("");
                    int filas = vv.tbllis.getRowCount();
                    for (int i = 0; i < filas; i++) {
                        modeltLis.removeRow(0);
                    }
                    CalcularTotales();
                    JOptionPane.showMessageDialog(vv, "Venta Finalizada");
                    vv.txtbus.requestFocus();
                }
            }
        }
    }
    
    public void abrirModalEntradasSalidasEfe() {
        vv.dEntSalEfe.setSize(350, 335);
        vv.dEntSalEfe.setModal(true);
        vv.dEntSalEfe.setLocationRelativeTo(vv);
        vv.dEntSalEfe.setVisible(true);
        
        vv.cmbTipo_ESE.setSelectedIndex(0);
        vv.spinMonto_ESE.setValue(1.0);
        vv.txtComentarios_ESE.setText("");
        vv.chbImpComprobante_ESE.setSelected(false);
    }
    
    public void abrirModalPrecios(int id) {
        vv.dPrecios.setSize(300, 255);
        vv.dPrecios.setModal(true);
        vv.dPrecios.setLocationRelativeTo(vv);
        vv.dPrecios.setTitle("Seleccione Precio");
        String precios = null; 
        precios = obtenerListaPrecios(id);
        String[] arrayPrecios;
        arrayPrecios = precios.split("\\|");
        vv.txtPrecio1.setText(arrayPrecios[0]);
        if(arrayPrecios[1].equals(""))
            vv.txtPrecio2.setText("0.0");
        else
            vv.txtPrecio2.setText(arrayPrecios[1]);
        
        if(arrayPrecios[2].equals(""))
            vv.txtPrecio3.setText("0.0");
        else
            vv.txtPrecio3.setText(arrayPrecios[2]);
        
        vv.dPrecios.setVisible(true);
    }
    
    public void cerrarModal(JDialog dialog){
        dialog.dispose();
        vv.txtbus.requestFocus();
    }
    
    public static boolean isNumeric(String str) {
        return str != null && str.matches("-?\\d+(\\.\\d+)?"); // Para números enteros y decimales
        // return str != null && str.matches("-?\\d{1,3}(,\\d{3})*(\\.\\d+)?");
    }
    
    public void guardarEntradasSalidasEfectivo() throws IOException{
        
        String tipo = "";
        double monto = 0.0;
        String comentario = "";
        boolean comprobante = false;
        
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) vv.spinMonto_ESE.getEditor();
        JTextField textField = editor.getTextField(); // Obtener el campo de texto del editor

        // Obtener el texto del campo de texto
        String texto = textField.getText();
        String textoSinComas = texto.replace(",", "");
        if( texto.equals("") ){
            JOptionPane.showMessageDialog(vv, "El campo Monto está vacio.");
            return;
        }
        
        if( !isNumeric( textoSinComas ) ){
            JOptionPane.showMessageDialog(vv, "Ingrese un Monto Válido.");
            return;
        }
        
        Object[] options = {"Si", "No"};
        int resGuardar = JOptionPane.showOptionDialog(
            null, 
            "¿Confirmar "+vv.cmbTipo_ESE.getSelectedItem()+" de Efectivo?", 
            "Confirmar", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE,
            null, 
            options, 
            options[0]
        );
        if( resGuardar == 0 ){
            if( GuardarESEfectivo() == 1 ){
                boolean isSelected = false;
                isSelected = vv.chbImpComprobante_ESE.isSelected();
                if( isSelected ){
                    // System.out.println("Imprimir Ticket.");
                    imprimirEntradaSalida(true, vv.cmbTipo_ESE.getSelectedItem().toString(), Double.parseDouble(textoSinComas), vv.txtComentarios_ESE.getText().toString());
                }
                JOptionPane.showMessageDialog(vv, "Registro Guardado.");
                cerrarModal(vv.dEntSalEfe);
            }else{
                JOptionPane.showMessageDialog(vv, "Error a Guardar " + vv.cmbTipo_ESE.getSelectedItem() + " de Efectivo. Contacte a Soporte Técnico.");
            }
        }
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
        con_ese = vv.txtComentarios_ESE.getText();
        
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) vv.spinMonto_ESE.getEditor();
        JTextField textField = editor.getTextField(); // Obtener el campo de texto del editor

        // Obtener el texto del campo de texto
        String texto = textField.getText();
        String textoSinComas = texto.replace(",", "");
        can_ese = Double.parseDouble(textoSinComas);
        
        if( vv.cmbTipo_ESE.getSelectedIndex() == 0 ){
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
    
    public String obtenerListaPrecios(int id){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        pm.setId(-10);
        pm.setSm(id);
        modelt = pm.CargarProductos();
        
        if(modelt.getRowCount() <= 0){
            return "NO";
        }
        
        String precio1;
        String precio2;
        String precio3;
        Object valor1 = modelt.getValueAt(0, 0);
        Object valor2 = modelt.getValueAt(0, 1);
        Object valor3 = modelt.getValueAt(0, 2);
        
        // Verificar si el valor es nulo o vacío
        if (valor1 == null || valor1.toString().trim().isEmpty()) {
            precio1 = "0.0";
        } else {
            precio1 = modelt.getValueAt(0, 0).toString();
        }
        
        // Verificar si el valor es nulo o vacío
        if (valor2 == null || valor2.toString().trim().isEmpty()) {
            precio2 = "0.0";
        } else {
            precio2 = modelt.getValueAt(0, 1).toString();
        }
        
        // Verificar si el valor es nulo o vacío
        if (valor3 == null || valor3.toString().trim().isEmpty()) {
            precio3 = "0.0";
        } else {
            precio3 = modelt.getValueAt(0, 2).toString();
        }
        
        return precio1 + "|" + precio2 + "|" + precio3;
        
        /*return modelt.getValueAt(0, 0).toString() + "|" + 
                modelt.getValueAt(0, 1).toString() + "|" + 
                modelt.getValueAt(0, 2).toString();*/
    }
    
    public void seleccionarListaPrecios(){
        AbstractButton selectedButton = null;
        for (AbstractButton button : new AbstractButton[] { vv.jrbPrecio1, vv.jrbPrecio2, vv.jrbPrecio3 }) {
            if (button.isSelected()) {
                selectedButton = button;
                break;
            }
        }
        
        double precioSeleccionado;
        switch (selectedButton.getText()) {
            case "Precio : $":
                // System.out.println("Selecciona precio");
                precioSeleccionado = Double.parseDouble( vv.txtPrecio1.getText() );
                break;
            case "Mayoreo: $":
                // System.out.println("Selecciona Mayoreo");
                precioSeleccionado = Double.parseDouble(vv.txtPrecio2.getText() );
                break;
            case "Menudeo: $":
                // System.out.println("Selecciona Menudeo");
                precioSeleccionado = Double.parseDouble( vv.txtPrecio3.getText() );
                break;
            default:
                precioSeleccionado = 1.0;
                break;
        }
        
        // System.out.println("Precio Seleccionado: " + precioSeleccionado);
        
        if(precioSeleccionado <= 0){
            JOptionPane.showMessageDialog(vv, "No se puede seleccionar un precio en ceros.");
            return;
        }
        
        int row = 0;
        row = vv.tbllis.getSelectedRow();
        
        vv.tbllis.setValueAt(precioSeleccionado, row, 3);
        CalcularTotales();
        
        JOptionPane.showMessageDialog(vv, "Precio Asignado.");
        cerrarModal(vv.dPrecios);
        
        // Mostrar el resultado
        /*if (selectedButton != null) {
        JOptionPane.showMessageDialog(vv, "Seleccionado: " + selectedButton.getText());
        } else {
        JOptionPane.showMessageDialog(vv, "Ninguna opción seleccionada.");
        }*/
    }
    
}