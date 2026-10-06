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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class DevolucionesC implements ActionListener, KeyListener, MouseListener, PropertyChangeListener {
    private DevolucionesV dv;
    private DevolucionesM dm;
    private VentasM vm = new VentasM();
    private ProductosM pm = new ProductosM();
    DefaultTableModel modeltLisVen;
    DefaultTableModel modeltLisOrd;
    DefaultTableModel c_modeltLisVE;
    DefaultTableModel c_modeltLisOS;
    DefaultTableCellRenderer centrar = new DefaultTableCellRenderer();

    public DevolucionesC(DevolucionesM dm, DevolucionesV dv) {
        this.dv = dv;
        this.dm = dm;
        this.dv.btnbus_ven.addActionListener(this);
        this.dv.btnbus_ord.addActionListener(this);
        this.dv.btnsalir_ven.addActionListener(this);
        this.dv.btnsalir_ord.addActionListener(this);
        this.dv.btnconsultar_ven.addActionListener(this);
        this.dv.btnconsultar_ord.addActionListener(this);
        this.dv.btndevolver_ven.addActionListener(this);
        this.dv.btndevolver_ord.addActionListener(this);
        this.dv.dtc_de_ven.addPropertyChangeListener(this);
        this.dv.dtc_al_ven.addPropertyChangeListener(this);
        this.dv.dtc_de_ord.addPropertyChangeListener(this);
        this.dv.dtc_al_ord.addPropertyChangeListener(this);
        this.dv.pesprincipal.addMouseListener(this);
        this.dv.pesprincipal.setIconAt(0, new ImageIcon("src/img/devoluciones_ven.png"));
        this.dv.pesprincipal.setIconAt(1, new ImageIcon("src/img/devoluciones_ord.png"));
        JTextFieldDateEditor dateEditor_de = (JTextFieldDateEditor)dv.dtc_de_ven.getDateEditor();
        dateEditor_de.setHorizontalAlignment(JTextField.CENTER);
        dateEditor_de.setEnabled(false);
        JTextFieldDateEditor dateEditor_al = (JTextFieldDateEditor)dv.dtc_al_ven.getDateEditor();
        dateEditor_al.setHorizontalAlignment(JTextField.CENTER);
        dateEditor_al.setEnabled(false);
        JTextFieldDateEditor dateEditor_de1 = (JTextFieldDateEditor)dv.dtc_de_ord.getDateEditor();
        dateEditor_de1.setHorizontalAlignment(JTextField.CENTER);
        dateEditor_de1.setEnabled(false);
        JTextFieldDateEditor dateEditor_al1 = (JTextFieldDateEditor)dv.dtc_al_ord.getDateEditor();
        dateEditor_al1.setHorizontalAlignment(JTextField.CENTER);
        dateEditor_al1.setEnabled(false);
        java.util.Date tuFecha = new java.util.Date();
        dv.dtc_de_ven.setDate(tuFecha);
        dv.dtc_al_ven.setDate(tuFecha);
        dv.dtc_de_ord.setDate(tuFecha);
        dv.dtc_al_ord.setDate(tuFecha);
    }

    public void iniciar() {
        dv.setTitle("Devoluciones");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == dv.btnbus_ven){
            CargarVentasDevoluciones();
        }else if(e.getSource() == dv.btnbus_ord){
            CargarOrdenDevoluciones();
        }else if(e.getSource() == dv.btnsalir_ven){
            dv.dispose();
        }else if(e.getSource() == dv.btnsalir_ord){
            dv.dispose();
        }else if(e.getSource() == dv.btnconsultar_ven){
            int fila = 0;
            fila = dv.tbllis_ven.getSelectedRow();
            if(fila < 0){
                JOptionPane.showMessageDialog(dv, "Seleccione un Registro");
            }else{
                dv.dConsultarVE.setSize(800, 550);
                dv.dConsultarVE.setLocationRelativeTo(null);
                dv.dConsultarVE.setVisible(true);
                //vv.dConsultarOS.show();
                
                // LIMPIAR DIALOG
                dv.c_folio_ve.setText("");
                dv.c_fechaventa_ve.setText("");
                dv.c_fechacancelado_ve.setText("");
                dv.c_estatus_ve.setText("");
                dv.c_subtotal_ve.setText("");
                dv.c_descuento_ve.setText("");
                dv.c_total_ve.setText("");
                int c_fila = 0;
                c_fila = dv.c_tbllis_ve.getSelectedRow();
                if(c_fila == -1){
                    
                }else{
                    for (int i = 0; i < fila; i++) {
                        c_modeltLisVE.removeRow(0);
                    }
                }
                
                // ASIGNAR VALORES A DIALOG
                dv.c_folio_ve.setText(dv.tbllis_ven.getValueAt(fila, 1).toString());
                dv.c_fechaventa_ve.setText(dv.tbllis_ven.getValueAt(fila, 12).toString());
                dv.c_estatus_ve.setText(dv.tbllis_ven.getValueAt(fila, 13).toString());
                dv.c_subtotal_ve.setText(dv.tbllis_ven.getValueAt(fila, 4).toString());
                dv.c_descuento_ve.setText(dv.tbllis_ven.getValueAt(fila, 5).toString());
                dv.c_total_ve.setText(dv.tbllis_ven.getValueAt(fila, 2).toString());
                if(dv.tbllis_ven.getValueAt(fila, 15) == null){
                    dv.c_fechacancelado_ve.setText("");
                }else{
                    dv.c_fechacancelado_ve.setText(dv.tbllis_ven.getValueAt(fila, 15).toString());
                }
                CargarConsultaDetalle2(dv.c_folio_ve.getText());
            }
        }else if(e.getSource() == dv.btnconsultar_ord){
            int fila = 0;
            fila = dv.tbllis_ord.getSelectedRow();
            if(fila < 0){
                JOptionPane.showMessageDialog(dv, "Seleccione un Registro");
            }else{
                dv.dConsultarOS.setSize(800, 550);
                dv.dConsultarOS.setLocationRelativeTo(null);
                dv.dConsultarOS.setVisible(true);
                //vv.dConsultarOS.show();
                
                // LIMPIAR DIALOG
                dv.c_folio_os.setText("");
                dv.c_fechaventa_os.setText("");
                dv.c_fechasurtido_os.setText("");
                dv.c_fechacancelado_os.setText("");
                dv.c_cliente_os.setText("");
                dv.c_domicilio_os.setText("");
                dv.c_estatus_os.setText("");
                dv.c_fechaentrega_os.setText("");
                dv.c_subtotal_os.setText("");
                dv.c_descuento_os.setText("");
                dv.c_total_os.setText("");
                int c_fila = 0;
                c_fila = dv.c_tbllis_os.getSelectedRow();
                if(c_fila == -1){
                    
                }else{
                    for (int i = 0; i < fila; i++) {
                        c_modeltLisOS.removeRow(0);
                    }
                }
                
                // ASIGNAR VALORES A DIALOG
                dv.c_folio_os.setText(dv.tbllis_ord.getValueAt(fila, 1).toString());
                dv.c_fechaventa_os.setText(dv.tbllis_ord.getValueAt(fila, 12).toString());
                dv.c_cliente_os.setText(dv.tbllis_ord.getValueAt(fila, 8).toString());
                dv.c_domicilio_os.setText(dv.tbllis_ord.getValueAt(fila, 7).toString());
                dv.c_estatus_os.setText(dv.tbllis_ord.getValueAt(fila, 13).toString());
                dv.c_fechaentrega_os.setText(dv.tbllis_ord.getValueAt(fila, 6).toString());
                dv.c_subtotal_os.setText(dv.tbllis_ord.getValueAt(fila, 4).toString());
                dv.c_descuento_os.setText(dv.tbllis_ord.getValueAt(fila, 5).toString());
                dv.c_total_os.setText(dv.tbllis_ord.getValueAt(fila, 2).toString());
                if(dv.tbllis_ord.getValueAt(fila, 14) == null){
                    dv.c_fechasurtido_os.setText("");
                }else{
                    dv.c_fechasurtido_os.setText(dv.tbllis_ord.getValueAt(fila, 14).toString());
                }
                if(dv.tbllis_ord.getValueAt(fila, 15) == null){
                    dv.c_fechacancelado_os.setText("");
                }else{
                    dv.c_fechacancelado_os.setText(dv.tbllis_ord.getValueAt(fila, 15).toString());
                }
                CargarConsultaDetalle(dv.c_folio_os.getText());
            }
        }else if(e.getSource() == dv.btndevolver_ven){
            int fila = 0;
            fila = dv.tbllis_ven.getSelectedRow();
            if(fila < 0){
                JOptionPane.showMessageDialog(dv, "Selecione un Registro");
            }else{
                String estatus = null;
                estatus = dv.tbllis_ven.getValueAt(fila, 13).toString();
                if(estatus.equals("Cancelada")){
                    JOptionPane.showMessageDialog(dv, "La venta ya fue cancelada!!!");
                }else if(estatus.equals("Devuelta")){
                    JOptionPane.showMessageDialog(dv, "La venta ya fue devuelta!!!");
                }else{
                    int res = 0;
                    res = JOptionPane.showConfirmDialog(null, "¿Devolver la Venta?", "Alerta!!!", JOptionPane.YES_NO_OPTION);
                    if(res == 0){
                        int a = 0;
                        a = ActualizarEstatus_VE(fila);
                        if(a == 0){
                            JOptionPane.showMessageDialog(dv, "Error al CanceDevolverlar Venta, Contacte a Soporte Técnio");
                        }else{
                            RegresarStockVenta(fila);
                            CargarVentasDevoluciones();
                            JOptionPane.showMessageDialog(dv, "Venta Devuelta");
                        }
                    }
                }
            }
        }else if(e.getSource() == dv.btndevolver_ord){
            int fila = 0;
            fila = dv.tbllis_ord.getSelectedRow();
            if(fila < 0){
                JOptionPane.showMessageDialog(dv, "Selecione un Registro");
            }else{
                String estatus = null;
                estatus = dv.tbllis_ord.getValueAt(fila, 13).toString();
                if(estatus.equals("Cancelada")){
                    JOptionPane.showMessageDialog(dv, "La orden ya fue cancelada!!!");
                }else if(estatus.equals("Devuelta")){
                    JOptionPane.showMessageDialog(dv, "La orden ya fue devuelta!!!");
                }else if(estatus.equals("Por Surtir")){
                    JOptionPane.showMessageDialog(dv, "La orden no ha sido surtida!!!\n Ingresar como cancelación");
                }else{
                    int res = 0;
                    res = JOptionPane.showConfirmDialog(null, "¿Devolver la Órden de Servicio?", "Alerta!!!", JOptionPane.YES_NO_OPTION);
                    if(res == 0){
                        int a = 0;
                        a = ActualizarEstatus_COS(fila);
                        if(a == 0){
                            JOptionPane.showMessageDialog(dv, "Error al Devolver Orden de Servicio, Contacte a Soporte Técnio");
                        }else{
                            RegresarStockOrden(fila);
                            CargarOrdenDevoluciones();
                            JOptionPane.showMessageDialog(dv, "Orden de Servicio Devuelta");
                        }
                    }
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
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == DevolucionesV.pesprincipal){
            if(DevolucionesV.pesprincipal.getSelectedIndex() == 0){
                int f1 = 0;
                f1 = dv.tbllis_ven.getRowCount();
                for (int i = 0; i < f1; i++) {
                    modeltLisVen.removeRow(0);
                }
            }else if(DevolucionesV.pesprincipal.getSelectedIndex() == 1){
                int f2 = 0;
                f2 = dv.tbllis_ord.getRowCount();
                for (int i = 0; i < f2; i++) {
                    modeltLisOrd.removeRow(0);
                }
            }
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
        if(e.getSource() == dv.dtc_de_ven){
            dv.dtc_al_ven.getJCalendar().setMinSelectableDate(dv.dtc_de_ven.getDate());
            dv.dtc_al_ven.setDate(dv.dtc_de_ven.getDate());
        }else if(e.getSource() == dv.dtc_de_ord){
            dv.dtc_al_ord.getJCalendar().setMinSelectableDate(dv.dtc_de_ord.getDate());
            dv.dtc_al_ord.setDate(dv.dtc_de_ord.getDate());
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getSource() == dv.dtc_de_ven){
            dv.dtc_al_ven.getJCalendar().setMinSelectableDate(dv.dtc_de_ven.getDate());
            dv.dtc_al_ven.setDate(dv.dtc_de_ven.getDate());
        }else if(evt.getSource() == dv.dtc_de_ord){
            dv.dtc_al_ord.getJCalendar().setMinSelectableDate(dv.dtc_de_ord.getDate());
            dv.dtc_al_ord.setDate(dv.dtc_de_ord.getDate());
        }
    }
    
    public void CargarVentasDevoluciones(){
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
        String fec_ent = null;
        String dir_ent = null;
        String cli_ent = null;
        int met_pag = 0;
        int tip_ven = 0;
        String fec_reg = null;
        int sta_ven = 0;
        String ref_ven = null;
        double subtot_ven = 0;
        
        id_ven = -7;
        tip_ven = 0;
        switch(dv.cmbest_ven.getSelectedIndex()){
            case 0:
                ref_ven = "0,1,-1";
                break;
            case 1:
                ref_ven = "1";
                break;
            case 2:
                ref_ven = "0";
                break;
            case 3:
                ref_ven = "-1";
                break;
            default:
                ref_ven = "0,1,-1";
                break;
        }
        Date fecha_de;
        Date fecha_al;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        fecha_de = dv.dtc_de_ven.getDate();
        fecha_al = dv.dtc_al_ven.getDate();
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
        
        modeltLisVen = vm.CargarOrdenesServicio();
        
        dv.tbllis_ven.setModel(modeltLisVen);
        
        dv.tbllis_ven.getColumnModel().getColumn(0).setMaxWidth(0);
        dv.tbllis_ven.getColumnModel().getColumn(0).setMinWidth(0);
        dv.tbllis_ven.getColumnModel().getColumn(0).setPreferredWidth(0);
        dv.tbllis_ven.getColumnModel().getColumn(1).setMaxWidth(175);
        dv.tbllis_ven.getColumnModel().getColumn(1).setMinWidth(175);
        dv.tbllis_ven.getColumnModel().getColumn(1).setPreferredWidth(175);
        dv.tbllis_ven.getColumnModel().getColumn(2).setMaxWidth(175);
        dv.tbllis_ven.getColumnModel().getColumn(2).setMinWidth(175);
        dv.tbllis_ven.getColumnModel().getColumn(2).setPreferredWidth(175);
        dv.tbllis_ven.getColumnModel().getColumn(3).setMaxWidth(0);
        dv.tbllis_ven.getColumnModel().getColumn(3).setMinWidth(0);
        dv.tbllis_ven.getColumnModel().getColumn(3).setPreferredWidth(0);
        dv.tbllis_ven.getColumnModel().getColumn(4).setMaxWidth(125);
        dv.tbllis_ven.getColumnModel().getColumn(4).setMinWidth(125);
        dv.tbllis_ven.getColumnModel().getColumn(4).setPreferredWidth(125);
        dv.tbllis_ven.getColumnModel().getColumn(5).setMaxWidth(125);
        dv.tbllis_ven.getColumnModel().getColumn(5).setMinWidth(125);
        dv.tbllis_ven.getColumnModel().getColumn(5).setPreferredWidth(125);
        dv.tbllis_ven.getColumnModel().getColumn(6).setMaxWidth(0);
        dv.tbllis_ven.getColumnModel().getColumn(6).setMinWidth(0);
        dv.tbllis_ven.getColumnModel().getColumn(6).setPreferredWidth(0);
        dv.tbllis_ven.getColumnModel().getColumn(7).setMaxWidth(0);
        dv.tbllis_ven.getColumnModel().getColumn(7).setMinWidth(0);
        dv.tbllis_ven.getColumnModel().getColumn(7).setPreferredWidth(0);
        dv.tbllis_ven.getColumnModel().getColumn(8).setMaxWidth(0);
        dv.tbllis_ven.getColumnModel().getColumn(8).setMinWidth(0);
        dv.tbllis_ven.getColumnModel().getColumn(8).setPreferredWidth(0);
        dv.tbllis_ven.getColumnModel().getColumn(9).setMaxWidth(0);
        dv.tbllis_ven.getColumnModel().getColumn(9).setMinWidth(0);
        dv.tbllis_ven.getColumnModel().getColumn(9).setPreferredWidth(0);
        dv.tbllis_ven.getColumnModel().getColumn(10).setMaxWidth(0);
        dv.tbllis_ven.getColumnModel().getColumn(10).setMinWidth(0);
        dv.tbllis_ven.getColumnModel().getColumn(10).setPreferredWidth(0);
        dv.tbllis_ven.getColumnModel().getColumn(11).setMaxWidth(0);
        dv.tbllis_ven.getColumnModel().getColumn(11).setMinWidth(0);
        dv.tbllis_ven.getColumnModel().getColumn(11).setPreferredWidth(0);
        dv.tbllis_ven.getColumnModel().getColumn(12).setMaxWidth(300);
        dv.tbllis_ven.getColumnModel().getColumn(12).setMinWidth(300);
        dv.tbllis_ven.getColumnModel().getColumn(12).setPreferredWidth(300);
        dv.tbllis_ven.getColumnModel().getColumn(13).setMaxWidth(125);
        dv.tbllis_ven.getColumnModel().getColumn(13).setMinWidth(125);
        dv.tbllis_ven.getColumnModel().getColumn(13).setPreferredWidth(125);
        dv.tbllis_ven.getColumnModel().getColumn(14).setMaxWidth(0);
        dv.tbllis_ven.getColumnModel().getColumn(14).setMinWidth(0);
        dv.tbllis_ven.getColumnModel().getColumn(14).setPreferredWidth(0);
        centrar.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
        for (int i = 0; i <= 16; i++) {
            dv.tbllis_ven.getColumnModel().getColumn(i).setCellRenderer(centrar);
        }
        
    }
    
    public void CargarOrdenDevoluciones(){
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
        String fec_ent = null;
        String dir_ent = null;
        String cli_ent = null;
        int met_pag = 0;
        int tip_ven = 0;
        String fec_reg = null;
        int sta_ven = 0;
        String ref_ven = null;
        double subtot_ven = 0;
        
        id_ven = -7;
        tip_ven = 1;
        switch(dv.cmbest_ord.getSelectedIndex()){
            case 0:
                ref_ven = "0,1,-1,2";
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
            case 4:
                ref_ven = "-1";
                break;
            default:
                ref_ven = "0,1,-1,2";
                break;
        }
        Date fecha_de;
        Date fecha_al;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        fecha_de = dv.dtc_de_ord.getDate();
        fecha_al = dv.dtc_al_ord.getDate();
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
        
        modeltLisOrd = vm.CargarOrdenesServicio();
        
        dv.tbllis_ord.setModel(modeltLisOrd);
        
        dv.tbllis_ord.getColumnModel().getColumn(0).setMaxWidth(0);
        dv.tbllis_ord.getColumnModel().getColumn(0).setMinWidth(0);
        dv.tbllis_ord.getColumnModel().getColumn(0).setPreferredWidth(0);
        dv.tbllis_ord.getColumnModel().getColumn(1).setMaxWidth(75);
        dv.tbllis_ord.getColumnModel().getColumn(1).setMinWidth(75);
        dv.tbllis_ord.getColumnModel().getColumn(1).setPreferredWidth(75);
        dv.tbllis_ord.getColumnModel().getColumn(2).setMaxWidth(75);
        dv.tbllis_ord.getColumnModel().getColumn(2).setMinWidth(75);
        dv.tbllis_ord.getColumnModel().getColumn(2).setPreferredWidth(75);
        dv.tbllis_ord.getColumnModel().getColumn(3).setMaxWidth(0);
        dv.tbllis_ord.getColumnModel().getColumn(3).setMinWidth(0);
        dv.tbllis_ord.getColumnModel().getColumn(3).setPreferredWidth(0);
        dv.tbllis_ord.getColumnModel().getColumn(4).setMaxWidth(75);
        dv.tbllis_ord.getColumnModel().getColumn(4).setMinWidth(75);
        dv.tbllis_ord.getColumnModel().getColumn(4).setPreferredWidth(75);
        dv.tbllis_ord.getColumnModel().getColumn(5).setMaxWidth(75);
        dv.tbllis_ord.getColumnModel().getColumn(5).setMinWidth(75);
        dv.tbllis_ord.getColumnModel().getColumn(5).setPreferredWidth(75);
        dv.tbllis_ord.getColumnModel().getColumn(9).setMaxWidth(0);
        dv.tbllis_ord.getColumnModel().getColumn(9).setMinWidth(0);
        dv.tbllis_ord.getColumnModel().getColumn(9).setPreferredWidth(0);
        dv.tbllis_ord.getColumnModel().getColumn(10).setMaxWidth(0);
        dv.tbllis_ord.getColumnModel().getColumn(10).setMinWidth(0);
        dv.tbllis_ord.getColumnModel().getColumn(10).setPreferredWidth(0);
        dv.tbllis_ord.getColumnModel().getColumn(11).setMaxWidth(0);
        dv.tbllis_ord.getColumnModel().getColumn(11).setMinWidth(0);
        dv.tbllis_ord.getColumnModel().getColumn(11).setPreferredWidth(0);
        dv.tbllis_ord.getColumnModel().getColumn(12).setMaxWidth(150);
        dv.tbllis_ord.getColumnModel().getColumn(12).setMinWidth(150);
        dv.tbllis_ord.getColumnModel().getColumn(12).setPreferredWidth(150);
        dv.tbllis_ord.getColumnModel().getColumn(13).setMaxWidth(75);
        dv.tbllis_ord.getColumnModel().getColumn(13).setMinWidth(75);
        dv.tbllis_ord.getColumnModel().getColumn(13).setPreferredWidth(75);
        centrar.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
        for (int i = 0; i <= 16; i++) {
            dv.tbllis_ord.getColumnModel().getColumn(i).setCellRenderer(centrar);
        }
        
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
        
        dv.c_tbllis_os.setModel(c_modeltLisOS);
        
        dv.c_tbllis_os.getColumnModel().getColumn(0).setMaxWidth(130);
        dv.c_tbllis_os.getColumnModel().getColumn(0).setMinWidth(130);
        dv.c_tbllis_os.getColumnModel().getColumn(0).setPreferredWidth(130);
        dv.c_tbllis_os.getColumnModel().getColumn(2).setMaxWidth(100);
        dv.c_tbllis_os.getColumnModel().getColumn(2).setMinWidth(100);
        dv.c_tbllis_os.getColumnModel().getColumn(2).setPreferredWidth(100);
        dv.c_tbllis_os.getColumnModel().getColumn(3).setMaxWidth(100);
        dv.c_tbllis_os.getColumnModel().getColumn(3).setMinWidth(100);
        dv.c_tbllis_os.getColumnModel().getColumn(3).setPreferredWidth(100);
        dv.c_tbllis_os.getColumnModel().getColumn(4).setMaxWidth(100);
        dv.c_tbllis_os.getColumnModel().getColumn(4).setMinWidth(100);
        dv.c_tbllis_os.getColumnModel().getColumn(4).setPreferredWidth(100);
        centrar.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
        for (int i = 0; i <= 4; i++) {
            dv.c_tbllis_os.getColumnModel().getColumn(i).setCellRenderer(centrar);
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
        
        dv.c_tbllis_ve.setModel(c_modeltLisVE);
        
        dv.c_tbllis_ve.getColumnModel().getColumn(0).setMaxWidth(130);
        dv.c_tbllis_ve.getColumnModel().getColumn(0).setMinWidth(130);
        dv.c_tbllis_ve.getColumnModel().getColumn(0).setPreferredWidth(130);
        dv.c_tbllis_ve.getColumnModel().getColumn(2).setMaxWidth(100);
        dv.c_tbllis_ve.getColumnModel().getColumn(2).setMinWidth(100);
        dv.c_tbllis_ve.getColumnModel().getColumn(2).setPreferredWidth(100);
        dv.c_tbllis_ve.getColumnModel().getColumn(3).setMaxWidth(100);
        dv.c_tbllis_ve.getColumnModel().getColumn(3).setMinWidth(100);
        dv.c_tbllis_ve.getColumnModel().getColumn(3).setPreferredWidth(100);
        dv.c_tbllis_ve.getColumnModel().getColumn(4).setMaxWidth(100);
        dv.c_tbllis_ve.getColumnModel().getColumn(4).setMinWidth(100);
        dv.c_tbllis_ve.getColumnModel().getColumn(4).setPreferredWidth(100);
        centrar.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
        for (int i = 0; i <= 4; i++) {
            dv.c_tbllis_ve.getColumnModel().getColumn(i).setCellRenderer(centrar);
        }
        
    }
    
    public int ActualizarEstatus_VE(int filaVenCan){
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
        
        
        id_ven = -8;
        fol_ven = dv.tbllis_ven.getValueAt(filaVenCan, 1).toString();
        sta_ven = -1;
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
    
    public void RegresarStockVenta(int filaVenCan){
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
        
        folio = dv.tbllis_ven.getValueAt(filaVenCan, 1).toString();
        
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
            pm.setEst(0);
            pm.GuardarProductos();
        }
        
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
        
        
        id_ven = -8;
        fol_ven = dv.tbllis_ord.getValueAt(filaOrdCan, 1).toString();
        sta_ven = -1;
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
        
        folio = dv.tbllis_ord.getValueAt(filaOrdCan, 1).toString();
        
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
            pm.setEst(0);
            pm.GuardarProductos();
        }
        
    }
    
}
