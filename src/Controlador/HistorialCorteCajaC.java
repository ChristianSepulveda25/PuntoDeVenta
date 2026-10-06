/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.EmpresaM;
import Modelo.ExportarExcel;
import Modelo.HistorialCorteCajaM;
import Vista.HistorialCorteCajaV;
import com.toedter.calendar.JTextFieldDateEditor;
import innotpv.Ticket;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Christian Sepulveda
 */
public class HistorialCorteCajaC implements ActionListener, KeyListener {
    private HistorialCorteCajaV hccv;
    private HistorialCorteCajaM hccm;
    public int fila_s = 0;
    private EmpresaM em = new EmpresaM();
    
    public HistorialCorteCajaC(HistorialCorteCajaM hccm, HistorialCorteCajaV hccv) {
        this.hccv = hccv;
        this.hccm = hccm;
        this.hccv.btnbuscar.addActionListener(this);
        this.hccv.btnbuscartodos.addActionListener(this);
        this.hccv.btnexportar.addActionListener(this);
        this.hccv.btnsalir.addActionListener(this);
        this.hccv.btnimprimir.addActionListener(this);
    }

    public void iniciar() {
        hccv.setTitle("Historial de cortes de caja");
        hccv.tbllis.getTableHeader().setReorderingAllowed(false) ;
        hccv.tbllis.getTableHeader().setResizingAllowed(false);
        java.util.Date tuFecha = new java.util.Date();
        hccv.fechaDe.setDate(tuFecha);
        JTextFieldDateEditor dateEditor_de = (JTextFieldDateEditor)hccv.fechaDe.getDateEditor();
        dateEditor_de.setHorizontalAlignment(JTextField.CENTER);
        dateEditor_de.setEnabled(false);
        CargarHistorialCortes();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == hccv.btnbuscar){
            CargarHistorialCortesFecha();
        }else if(e.getSource() == hccv.btnbuscartodos){
            CargarHistorialCortes();
        }else if(e.getSource() == hccv.btnimprimir){
            int filas = hccv.tbllis.getSelectedRow();
            
            if( filas < 0 ){
                JOptionPane.showMessageDialog(hccv, "Seleccione un registro");
            }else{
                String estatus = null;
                estatus = hccv.tbllis.getValueAt(filas, 8).toString();
                if( estatus.equals("Cerrada") ){
                    try {
                        imprimirTicketCorteCaja(true);
                    } catch (IOException ex) {
                        Logger.getLogger(HistorialCorteCajaC.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    JOptionPane.showMessageDialog(hccv, "Corte de caja Abierto");
                }
            }
        }else if(e.getSource() == hccv.btnexportar){
            int filas = hccv.tbllis.getRowCount();
            if( filas > 0 ){
                ExportarExcel obj;
                try {
                    obj = new ExportarExcel();
                    obj.exportarExcel(hccv.tbllis);
                }catch(IOException ex) {
                    System.out.println("Error: " + ex);
                }
            }else{
                JOptionPane.showMessageDialog(hccv, "No tienes datos para exportar");
            }
        }else if(e.getSource() == hccv.btnsalir){
            hccv.dispose();
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
    
    public void CargarHistorialCortes(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        hccm.setId_cc(-1);
        hccm.setCan_inicio(0);
        hccm.setFec_inicio(null);
        hccm.setCan_cierre(0);
        hccm.setFec_cierre(null);
        hccm.setUsu_inicio(0);
        hccm.setUsu_cierre(0);
        hccm.setVentas_realizadas(0);
        hccm.setVentas_canceladas(0);
        hccm.setMov_entradas(0);
        hccm.setMov_salidas(0);
        hccm.setEstatus(null);
        modelt = hccm.CargarHistorialCortes();
        hccv.tbllis.setModel(modelt);
        
        /*av.tbllis.getColumnModel().getColumn(0).setMaxWidth(0);
        av.tbllis.getColumnModel().getColumn(0).setMinWidth(0);
        av.tbllis.getColumnModel().getColumn(0).setPreferredWidth(0);
        cpv.tbllis.getColumnModel().getColumn(1).setMaxWidth(100);
        cpv.tbllis.getColumnModel().getColumn(1).setMinWidth(100);
        cpv.tbllis.getColumnModel().getColumn(1).setPreferredWidth(100);
        av.tbllis.getColumnModel().getColumn(2).setMaxWidth(100);
        av.tbllis.getColumnModel().getColumn(2).setMinWidth(100);
        av.tbllis.getColumnModel().getColumn(2).setPreferredWidth(100);*/
    }
    
    public void CargarHistorialCortesFecha(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        String fecha_del = null;
        String fecha_al = null;
        Date fecha_de;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        fecha_de = hccv.fechaDe.getDate();
        fecha_del = sdf.format(fecha_de)+" 00:00:00";
        fecha_al = sdf.format(fecha_de)+" 23:59:59";
        
        hccm.setId_cc(-2);
        hccm.setCan_inicio(0);
        hccm.setFec_inicio(fecha_del);
        hccm.setCan_cierre(0);
        hccm.setFec_cierre(fecha_al);
        hccm.setUsu_inicio(0);
        hccm.setUsu_cierre(0);
        hccm.setVentas_realizadas(0);
        hccm.setVentas_canceladas(0);
        hccm.setMov_entradas(0);
        hccm.setMov_salidas(0);
        hccm.setEstatus(null);
        modelt = hccm.CargarHistorialCortes();
        hccv.tbllis.setModel(modelt);
        
        /*av.tbllis.getColumnModel().getColumn(0).setMaxWidth(0);
        av.tbllis.getColumnModel().getColumn(0).setMinWidth(0);
        av.tbllis.getColumnModel().getColumn(0).setPreferredWidth(0);
        cpv.tbllis.getColumnModel().getColumn(1).setMaxWidth(100);
        cpv.tbllis.getColumnModel().getColumn(1).setMinWidth(100);
        cpv.tbllis.getColumnModel().getColumn(1).setPreferredWidth(100);
        av.tbllis.getColumnModel().getColumn(2).setMaxWidth(100);
        av.tbllis.getColumnModel().getColumn(2).setMinWidth(100);
        av.tbllis.getColumnModel().getColumn(2).setPreferredWidth(100);*/
    }
    
    
    public int imprimirTicketCorteCaja(boolean imprimir) throws IOException{
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
            Ticket.AddCabecera(modelt.getValueAt(0, 7).toString() + ", " + modelt.getValueAt(0, 8).toString());
            Ticket.AddCabecera(Ticket.DarEspacio());
        }
        
        int filas = hccv.tbllis.getSelectedRow();
        
        String fechaCorte = null;
        fechaCorte = hccv.tbllis.getValueAt(filas, 3).toString();
        String[] parts = fechaCorte.split(" ");
        String[] parts2 = parts[0].split("-");
        
        
        Ticket.AddCabecera(Ticket.DibujarLinea(29));
        Ticket.AddCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera("Corte de Caja: "+parts2[2]+"/"+parts2[1]+"/"+parts2[0]);
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera(Ticket.DibujarLinea(29));
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        
        Ticket.AddTotal("SALDO INICIAL: $ ", hccv.tbllis.getValueAt(filas, 0).toString() );
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("",Ticket.DarEspacio());
        
        Ticket.AddTotal("VENTAS","");
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("- Realizadas: $ ", hccv.tbllis.getValueAt(filas, 4).toString() );
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("- Canceladas: $ ", hccv.tbllis.getValueAt(filas, 5).toString() );
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("",Ticket.DarEspacio());
        
        Ticket.AddTotal("MOVIMIENTOS","");
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("- Entradas: $ ", hccv.tbllis.getValueAt(filas, 6).toString() );
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("- Salidas: $ ", hccv.tbllis.getValueAt(filas, 7).toString() );
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("",Ticket.DarEspacio());
        
        Ticket.AddTotal("TOTAL: $ ", hccv.tbllis.getValueAt(filas, 2).toString() );
        Ticket.AddTotal("",Ticket.DarEspacio());
        
        Ticket.AddPieLinea(Ticket.DibujarLinea(29));
        Ticket.AddPieLinea(Ticket.DarEspacio());
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
