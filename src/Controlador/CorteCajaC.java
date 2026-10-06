/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Conexion;
import Modelo.CorteCajaM;
import Modelo.EmpresaM;
import Vista.CorteCajaV;
import innotpv.Ticket;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Christian Sepulveda
 */
public class CorteCajaC implements ActionListener, KeyListener  {
    private CorteCajaM ccm;
    private CorteCajaV ccv;
    public double corte_inicial = 0;
    public double corte_cierre = 0;
    public int id_cc = 0;
    private EmpresaM em = new EmpresaM();

    public CorteCajaC(CorteCajaM ccm, CorteCajaV ccv) {
        this.ccm = ccm;
        this.ccv = ccv;
        this.ccv.btnsalir.addActionListener(this);
        this.ccv.btnguardar.addActionListener(this);
        this.ccv.btngenerar.addActionListener(this);
    }

    public void iniciar() {
        ccv.setTitle("Corte de Caja");
        Date hoy = new Date();
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1= new SimpleDateFormat("dd/MM/yyyy");
        String fecha = sdf.format(hoy);
        String fecha1 = sdf1.format(hoy);
        ccv.lblfecha.setText(fecha1);
        int cant = ConsultarCorteCaja(0, fecha);
        switch (cant) {
            case -1:
                ccv.lblestatus.setText("Error");
                // System.out.println("Problemas");
                break;
            case 0:
                ccv.lblestatus.setText("Por Abrir");
                // System.out.println("Abrir");
                ccv.btngenerar.setEnabled(false);
                ccv.btnguardar.setEnabled(true);
                ccv.btnguardar.setText("Abrir");
                break;
            case 1:
                int cant1 = ConsultarCorteCaja(1, fecha);
                switch(cant1){
                    case -1:
                        ccv.lblestatus.setText("Error");
                        // System.out.println("Problemas");
                        break;
                    case 0:
                        ccv.lblestatus.setText("Abierta");
                        // System.out.println("Abierta");
                        ccv.btngenerar.setEnabled(true);
                        ccv.btnguardar.setEnabled(false);
                        ccv.btnguardar.setText("Cerrar");
                        break;
                    case 1:
                        ccv.lblestatus.setText("Cerrada");
                        // System.out.println("Cerrada");
                        ccv.btngenerar.setEnabled(false);
                        ccv.btnguardar.setEnabled(false);
                        ccv.btnguardar.setText("Cerrar");
                        CargarCorteCerrado();
                        break;
                    default:
                        break;
                }
                        
                break;
            default:
                break;
        }
        if(ccv.lblestatus.getText().equals("Por Abrir")){
            ccv.txtsaldo.setText("0.0");
            ccv.txtsaldo.setEnabled(true);
        }else if(ccv.lblestatus.getText().equals("Abierta")){
            ccv.txtsaldo.setEnabled(false);
            ccv.txtsaldo.setText(""+corte_inicial);
        }else if(ccv.lblestatus.getText().equals("Cerrada")){
            ccv.txtsaldo.setEnabled(false);
            ccv.btnguardar.setEnabled(false);
            ccv.txtsaldo.setText(""+corte_inicial);
            ccv.lbltotal.setText(""+corte_cierre);
        }
        SumarCortes();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == ccv.btnsalir){
            ccv.dispose();
        }else if(e.getSource() == ccv.btnguardar){
            if(ccv.lblestatus.getText().equals("Por Abrir")){
                if(ccv.txtsaldo.getText().isEmpty()){
                    JOptionPane.showMessageDialog(ccv, "Introduce una Cantidad Mayor a 0 !!!");
                }else{
                    double saldo = 0;
                    saldo = Double.parseDouble(ccv.txtsaldo.getText());
                    if(saldo <= 0){
                        JOptionPane.showMessageDialog(ccv, "Introduce una Cantidad Mayor a 0 !!!");
                        ccv.txtsaldo.requestFocus();
                    }else{
                        int res = 0;
                        
                        Object[] options = {"Si", "No"};
                        res = JOptionPane.showOptionDialog(ccv, "¿Desea Abrir la Caja?", "Confirmar!!!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                        
                        if(res == 0){
                            GuardarCorteCaja();
                        }else{
                            ccv.txtsaldo.requestFocus();
                        }
                    }
                }
            }else if(ccv.lblestatus.getText().equals("Abierta")){
                int res = 0;
                //res = JOptionPane.showConfirmDialog(ccv, "¿Desea Cerrar la Caja?", "Confirmar!!!", JOptionPane.YES_NO_OPTION);
                
                Object[] options = {"Si", "No"};
                res = JOptionPane.showOptionDialog(ccv, "¿Desea Cerrar la Caja?", "Confirmar!!!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                
                if(res == 0){
                    try {
                        //JOptionPane.showMessageDialog(ccv, "Cerrar");
                        ActualizarCorteCaja();
                    } catch (IOException ex) {
                        Logger.getLogger(CorteCajaC.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }else if(ccv.lblestatus.getText().equals("Cerrada")){
                JOptionPane.showMessageDialog(ccv, "Caja Cerrada");
            }
        }else if(e.getSource() == ccv.btngenerar){
            int res = 0;
            // res = JOptionPane.showConfirmDialog(ccv, "¿Desea generar el Corte de Caja?", "Confirmar!!!",  JOptionPane.YES_NO_OPTION);
            
            Object[] options = {"Si", "No"};
            res = JOptionPane.showOptionDialog(ccv, "¿Desea generar el Corte de Caja?", "Confirmar!!!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            
            if(res == 0){
                //JOptionPane.showMessageDialog(ccv, "Es hora de generar");
                GenerarCorte();
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
    
    public void GuardarCorteCaja(){
        double can_inicio = 0;
        java.util.Date dt = new java.util.Date();
        
        can_inicio = Double.parseDouble(ccv.txtsaldo.getText());
        
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fec_inicio = sdf.format(dt);
        
        ccm.setCantidad(can_inicio);
        ccm.setFecha(fec_inicio);
        ccm.setEstatus(0);
        ccm.setId_cc(0);
        ccm.setEST("Abierta");
        
        if(ccm.GuardarCorteCaja()){
            ccv.lblestatus.setText("Abierta");
            JOptionPane.showMessageDialog(ccv, "Caja Abierta");
            ccv.txtsaldo.setEnabled(false);
            ccv.btnguardar.setEnabled(false);
            ccv.btngenerar.setEnabled(true);
            SumarCortes();
        }else{
            JOptionPane.showMessageDialog(ccv, "Ocurrio un Problema al Abrir la Caja, Contacte a Soporte Técnico");
        }
        
    }
    
    public void ActualizarCorteCaja() throws IOException{
        double can_cierre = 0;
        java.util.Date dt = new java.util.Date();
        
        can_cierre = Double.parseDouble(ccv.lbltotal.getText());
        
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fec_cierre = sdf.format(dt);
        
        String a1 = null;
        String a2 = null;
        String a3 = null;
        String a4 = null;
        a1 = ccv.lblventas.getText().toString();
        a2 = ccv.lblventascan.getText().toString();
        a3 = ccv.lblentradas.getText().toString();
        a4 = ccv.lblpagos.getText().toString();
        
        ccm.setCantidad(can_cierre);
        ccm.setFecha(fec_cierre);
        ccm.setEstatus(1);
        ccm.setId_cc(id_cc);
        ccm.setVENTAS_REALIZADAS(a1);
        ccm.setVENTAS_CANCELADAS(a2);
        ccm.setMOV_ENTRADAS(a3);
        ccm.setMOV_SALIDAS(a4);
        ccm.setEST("Cerrada");
        
        if(ccm.GuardarCorteCaja()){
            imprimirTicketCorteCaja(true);
            ccv.lblestatus.setText("Cerrada");
            JOptionPane.showMessageDialog(ccv, "Caja Cerrada");
            ccv.txtsaldo.setEnabled(false);
            ccv.btnguardar.setEnabled(false);
            ccv.btngenerar.setEnabled(false);
        }else{
            JOptionPane.showMessageDialog(ccv, "Ocurrio un Problema al Cerrar la Caja, Contacte a Soporte Técnico");
        }
        
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
            if(modelt.getValueAt(0, 5) == null){
                
            }else{
                // Ticket.AddCabecera("Telefono: " + modelt.getValueAt(0, 5).toString());
                // Ticket.AddCabecera(Ticket.DarEspacio());*/
            }
            // Ticket.AddCabecera("Celular: " + modelt.getValueAt(0, 6).toString());
            // Ticket.AddCabecera(Ticket.DarEspacio());
            Ticket.AddCabecera(modelt.getValueAt(0, 7).toString() + ", " + modelt.getValueAt(0, 8).toString());
            Ticket.AddCabecera(Ticket.DarEspacio());
        }
        Ticket.AddCabecera(Ticket.DibujarLinea(29));
        Ticket.AddCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera("Corte de Caja: "+ccv.lblfecha.getText().toString());
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera(Ticket.DibujarLinea(29));
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        
        Ticket.AddTotal("SALDO INICIAL: $ ", ccv.txtsaldo.getText().toString() );
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("",Ticket.DarEspacio());
        
        Ticket.AddTotal("VENTAS","");
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("- Realizadas: $ ",ccv.lblventas.getText().toString());
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("- Canceladas: $ ",ccv.lblventascan.getText().toString());
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("",Ticket.DarEspacio());
        
        Ticket.AddTotal("MOVIMIENTOS","");
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("- Entradas: $ ",ccv.lblentradas.getText().toString());
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("- Salidas: $ ",ccv.lblpagos.getText().toString());
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("",Ticket.DarEspacio());
        
        Ticket.AddTotal("TOTAL: $ ",ccv.lbltotal.getText().toString());
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
        
        int a = 0;
        a = Ticket.ImprimirDocumento(imprimir);
        if(a == 1){
            return 1;
        }
        return 0;
    
    }
    
    public int ConsultarCorteCaja(int corte, String fecha){
        Conexion cc = new Conexion();
        com.mysql.jdbc.Connection cn = cc.getConexion();
        int cant_reg = 0;
                    
        try{
            Statement ps = cn.createStatement();
            ResultSet rs;
            if(corte == 0){
                rs = ps.executeQuery("SELECT COUNT(id_cc), can_inicio, id_cc FROM corte_caja WHERE fec_inicio LIKE '%" + fecha + "%';");
            }else{
                rs = ps.executeQuery("SELECT COUNT(id_cc), can_inicio, can_cierre, id_cc FROM corte_caja WHERE fec_cierre LIKE '%" + fecha + "%';");
            }
            if(rs.next()){
                cant_reg = rs.getInt(1);
                if(cant_reg >=1){
                    corte_inicial = rs.getDouble(2);
                    id_cc = rs.getInt(3);
                    if(corte > 0){
                        corte_cierre = rs.getDouble(3);
                        id_cc = rs.getInt(4);
                    }
                }
            }
            return 1;
        }catch(SQLException e){
            System.out.println(e);
            return -1;
        }finally{
            try{
                cn.close();
                return cant_reg;
            }catch(SQLException e){
                System.out.println(e);
                return -1;
            }
        }
    }
    
    public void SumarCortes(){
        double si = 0;
        double ve = 0;
        double sd = 0;
        double en = 0;
        double pa = 0;
        double to = 0;
        
        si = Double.parseDouble(ccv.txtsaldo.getText());
        ve = Double.parseDouble(ccv.lblventas.getText());
        //sd = Double.parseDouble(ccv.lblsd.getText());
        en = Double.parseDouble(ccv.lblentradas.getText());
        pa = Double.parseDouble(ccv.lblpagos.getText());
        
        to = si + ve + sd + en - pa;
        
        ccv.lbltotal.setText("" + to);
        
    }
    
    public void GenerarCorte(){
        int id = -1;
        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String fec_cierre = sdf.format(dt);
        String fechaDe = fec_cierre + " 00:00:00";
        String fechaAl = fec_cierre + " 23:59:59";
        
        //System.out.println("Fecha de: " + fechaDe);
        //System.out.println("Fecha al: " + fechaAl);
        
        ccm.setID(id);
        ccm.setFECHADE(fechaDe);
        ccm.setFECHAAL(fechaAl);
        
        ccm.GenerarCorteCaja();
        
        /*System.out.println(ccm.getVENTAS_REALIZADAS());
        System.out.println(ccm.getVENTAS_CANCELADAS());
        System.out.println(ccm.getMOV_ENTRADAS());
        System.out.println(ccm.getMOV_SALIDAS());
        System.out.println(ccm.getCAN_CIERRE());
        System.out.println(ccm.getCAN_INICIAL());*/
        
        ccv.lblventas.setText( ccm.getVENTAS_REALIZADAS() );
        ccv.lblventascan.setText( ccm.getVENTAS_CANCELADAS() );
        ccv.lblentradas.setText( ccm.getMOV_ENTRADAS() );
        ccv.lblpagos.setText( ccm.getMOV_SALIDAS() );
        ccv.lbltotal.setText( ccm.getCAN_CIERRE() );
        ccv.txtsaldo.setText( ccm.getCAN_INICIAL() );
        
        ccv.btngenerar.setEnabled(true);
        ccv.btnguardar.setEnabled(true);
        ccv.btnguardar.setText("Cerrar");
        
    }
    
    public void CargarCorteCerrado(){
        int id = -2;
        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String fec_cierre = sdf.format(dt);
        String fechaDe = fec_cierre + " 00:00:00";
        String fechaAl = fec_cierre + " 23:59:59";
        
        ccm.setID(id);
        ccm.setFECHADE(fechaDe);
        ccm.setFECHAAL(fechaAl);
        
        ccm.GenerarCorteCaja();
        
        /*System.out.println(ccm.getVENTAS_REALIZADAS());
        System.out.println(ccm.getVENTAS_CANCELADAS());
        System.out.println(ccm.getMOV_ENTRADAS());
        System.out.println(ccm.getMOV_SALIDAS());
        System.out.println(ccm.getCAN_CIERRE());
        System.out.println(ccm.getCAN_INICIAL());*/
        
        ccv.lblventas.setText( ccm.getVENTAS_REALIZADAS() );
        ccv.lblventascan.setText( ccm.getVENTAS_CANCELADAS() );
        ccv.lblentradas.setText( ccm.getMOV_ENTRADAS() );
        ccv.lblpagos.setText( ccm.getMOV_SALIDAS() );
        ccv.lbltotal.setText( ccm.getCAN_CIERRE() );
        ccv.txtsaldo.setText( ccm.getCAN_INICIAL() );
        //SumarCortes();
        
    }
    
}
