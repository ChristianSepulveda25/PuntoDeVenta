/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.*;
import Vista.*;
import com.toedter.calendar.JTextFieldDateEditor;
import innotpv.Ticket;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ChristianSepulveda
 */
public class CorteCajaUsuarioC implements ActionListener {
    UsuariosM um = new UsuariosM();
    EmpresaM em = new EmpresaM();
    CatProM cpm = new CatProM();
    private CorteCajaUsuarioV ccuv;
    private CorteCajaUsuarioM ccum;

    public CorteCajaUsuarioC(CorteCajaUsuarioM ccum, CorteCajaUsuarioV ccuv) {
        this.ccuv = ccuv;
        this.ccum = ccum;
        this.ccuv.btnGenerarCorte.addActionListener(this);
        this.ccuv.btnSalir.addActionListener(this);
        /*this.ccuv.btnnue.addActionListener(this);
        this.ccuv.btngua.addActionListener(this);
        this.ccuv.btncan.addActionListener(this);
        this.ccuv.btnedi.addActionListener(this);
        this.ccuv.btnsal.addActionListener(this);*/
    }

    public void iniciar() {
        ccuv.setTitle("Corte de Caja por Usuario");
        ImageIcon originalIcon  = new ImageIcon(getClass().getResource("/img/FondoCopyCentro72ppp.png"));
        Image image = originalIcon.getImage();
        Image scaledImage = image.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        this.ccuv.setFrameIcon(resizedIcon);
        CargarUsuarios();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date fecha = sdf.parse("2020-01-01");
            ccuv.fechaCorte.setDate(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JTextFieldDateEditor fechaCorte = (JTextFieldDateEditor)ccuv.fechaCorte.getDateEditor();
        fechaCorte.setHorizontalAlignment(JTextField.CENTER);
        fechaCorte.setEnabled(false);
        /*cpv.tbllis.getTableHeader().setReorderingAllowed(false) ;
        cpv.tbllis.getTableHeader().setResizingAllowed(false);
        cpv.txtid.setVisible(false);
        CargarCatPro();
        cpv.txtnom.setEnabled(false);
        cpv.cmbest.setEnabled(false);
        cpv.btngua.setEnabled(false);
        cpv.btncan.setEnabled(false);
        cpv.btnnue.requestFocus();*/
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if( e.getSource() == ccuv.btnSalir ){
            ccuv.dispose();
        }else if( e.getSource() == ccuv.btnGenerarCorte ){
            Date fechaSeleccionada = ccuv.fechaCorte.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fechaFormateada = sdf.format(fechaSeleccionada);
            
            UsuarioListM usuarioSeleccionado = (UsuarioListM) ccuv.cmbUsuarios.getSelectedItem();
            int idSeleccionado = usuarioSeleccionado.getId();
            String userSeleccionado = usuarioSeleccionado.getUser();
            String nombreSeleccionado = usuarioSeleccionado.getNombre();

            // Generar Corte de Caja Por Partes
            GenerarCorteCaja(fechaFormateada, userSeleccionado, nombreSeleccionado);
        }
    }
    
    public void CargarUsuarios(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        
        um.setId(-2);
        
        modelt = um.CargarUsuarios();
        
        // Iterar sobre las filas del DefaultTableModel y agregar los usuarios al JComboBox
        for (int i = 0; i < modelt.getRowCount(); i++) {
            // Asumimos que la columna 0 es el 'id' y la columna 1 es el 'nombre'
            int id = (int) modelt.getValueAt(i, 0); // Obtén el 'id'
            String nombre = (String) modelt.getValueAt(i, 1); // Obtén el 'nombre'
            String user = (String) modelt.getValueAt(i, 2); // Obtén el 'user'

            // Crear un nuevo objeto Usuario
            UsuarioListM usuario = new UsuarioListM(id, nombre, user);

            // Agregar el usuario al JComboBox
            ccuv.cmbUsuarios.addItem(usuario);
        }
        
    }
    
    public void GenerarCorteCaja(String fecha, String idUsuario, String nombreSeleccionado){
        
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        DefaultTableModel modeltDeptos = new DefaultTableModel(){
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
            Ticket.AddCabecera("WhatsApp: " + modelt.getValueAt(0, 6).toString() );
            Ticket.AddCabecera(Ticket.DarEspacio());
        }
        
        String usuarioCorte = nombreSeleccionado;
        
        Ticket.AddCabecera(Ticket.DarEspacio());
        Ticket.AddCabecera("Usuario: " + usuarioCorte);
        Ticket.AddCabecera(Ticket.DarEspacio());
        
        Ticket.AddCabecera("Fecha del Corte: " + fecha);
        Ticket.AddCabecera(Ticket.DarEspacio());
            
        Ticket.AddSubCabecera(Ticket.DibujarLinea(29));
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        
        Double[] headers = null;
        try {
            headers = ccum.obtenerHeadersCorte(1, fecha, idUsuario);
        } catch (SQLException ex) {
            Logger.getLogger(CorteCajaUsuarioC.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Ticket.AddTotal("Ventas Totales: ","$ " + headers[0]);
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("Ventas Contado: ","$ " + headers[9]);
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("Ventas Credito: ","$ " + headers[10]);
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("",Ticket.DarEspacio());
        
        /*Ticket.AddTotal("Ventas Contado: ","$ " + headers[9]);
        Ticket.AddTotal("",Ticket.DarEspacio());
        
        Ticket.AddTotal("Ventas Crédito: ","$ " + headers[10]);
        Ticket.AddTotal("",Ticket.DarEspacio());*/
        
        Ticket.AddTotal("Entrada Efectivo: ","$ " + headers[1]);
        Ticket.AddTotal("",Ticket.DarEspacio());
        
        Ticket.AddTotal("Salida Efectivo: ","$ " + headers[2]);
        Ticket.AddTotal("",Ticket.DarEspacio());
        
        /*Ticket.AddTotal("Abonos Efectivo: ","$ " + headers[3]);
        Ticket.AddTotal("",Ticket.DarEspacio());
        
        Ticket.AddTotal("Abonos Tajeta: ","$ " + headers[8]);
        Ticket.AddTotal("",Ticket.DarEspacio());*/
        
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("",Ticket.DibujarLinea(29));
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("",Ticket.DarEspacio());
        
        Ticket.AddTotal("Abonos Efectivo: ","$ " + headers[12]);
        Ticket.AddTotal("",Ticket.DarEspacio());
        
        Ticket.AddTotal("Abonos Tarjeta: ","$ " + headers[13]);
        Ticket.AddTotal("",Ticket.DarEspacio());
        
        Ticket.AddTotal("Abonos Transferencia: ","$ " + headers[14]);
        Ticket.AddTotal("",Ticket.DarEspacio());
        
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("",Ticket.DibujarLinea(29));
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("",Ticket.DarEspacio());
        
        Ticket.AddTotal("VENTAS CONTADO","");
        Ticket.AddTotal("",Ticket.DarEspacio());
        
        Ticket.AddTotal("Total Efectivo: ","$ " + headers[4]);
        Ticket.AddTotal("",Ticket.DarEspacio());
        
        Ticket.AddTotal("Total Tarjeta: ","$ " + headers[5]);
        Ticket.AddTotal("",Ticket.DarEspacio());
        
        Ticket.AddTotal("Total Transferencia: ","$ " + headers[11]);
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("",Ticket.DarEspacio());
        
        double totalCaja = 0.0;
        totalCaja = headers[4] + headers[1] - headers[2] + headers[12];
        
        Ticket.AddTotal("TOTAL CAJA: ","$ " + totalCaja);
        Ticket.AddTotal("",Ticket.DarEspacio());
        
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("VENTAS CANCELADAS: ","" + headers[7]);
        Ticket.AddTotal("",Ticket.DarEspacio());
        
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("",Ticket.DibujarLinea(29));
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("",Ticket.DarEspacio());
        
        Ticket.AddTotal("== VENTAR POR DEPTO ==","");
        Ticket.AddTotal("",Ticket.DarEspacio());
        
        cpm.setId(-2);
        modeltDeptos = cpm.CargarCatPro();
        for (int i = 0; i < modeltDeptos.getRowCount(); i++) {
            double totalDepto = 0.0;
            try {
                totalDepto = ccum.obtenerTotalDepto(1, fecha, idUsuario, Integer.parseInt(modeltDeptos.getValueAt(i, 0).toString()));
            } catch (SQLException ex) {
                Logger.getLogger(CorteCajaUsuarioC.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if( totalDepto > 0){
                Ticket.AddTotal(modeltDeptos.getValueAt(i, 1).toString(),"$ "+totalDepto);
                Ticket.AddTotal("",Ticket.DarEspacio());
            }
        }
        
        Ticket.AddPieLinea(Ticket.DarEspacio());
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
        try {
            a = Ticket.ImprimirDocumento(true);
            JOptionPane.showMessageDialog(ccuv, "Corte de Caja Generado Correctamente.");
        } catch (IOException ex) {
            Logger.getLogger(CorteCajaUsuarioC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
