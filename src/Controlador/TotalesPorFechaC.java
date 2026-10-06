/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ChristianSepulveda
 */
public class TotalesPorFechaC implements ActionListener, KeyListener {
    private TotalesPorFechaV tpfv;
    private TotalesPorFechaM tpfm;
    DefaultTableCellRenderer centrar = new DefaultTableCellRenderer();
    
    public TotalesPorFechaC(TotalesPorFechaM tpfm, TotalesPorFechaV tpfv) {
        this.tpfv = tpfv;
        this.tpfm = tpfm;
        this.tpfv.btnsalir.addActionListener(this);
        this.tpfv.btnbuscar.addActionListener(this);
    }

    public void iniciar() {
        tpfv.setTitle("Registro Almacenes");
        ImageIcon originalIcon  = new ImageIcon(getClass().getResource("/img/FondoCopyCentro72ppp.png"));
        Image image = originalIcon.getImage();
        Image scaledImage = image.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        this.tpfv.setFrameIcon(resizedIcon);
        
        java.util.Date tuFecha = new java.util.Date();
        tpfv.fechaDe.setDate(tuFecha);
        tpfv.fechaAl.setDate(tuFecha);
        JTextFieldDateEditor dateEditor_de = (JTextFieldDateEditor)tpfv.fechaDe.getDateEditor();
        dateEditor_de.setHorizontalAlignment(JTextField.CENTER);
        dateEditor_de.setEnabled(false);
        JTextFieldDateEditor dateEditor_al = (JTextFieldDateEditor)tpfv.fechaAl.getDateEditor();
        dateEditor_al.setHorizontalAlignment(JTextField.CENTER);
        dateEditor_al.setEnabled(false);
        tpfv.tbllis.getTableHeader().setReorderingAllowed(false);
        // tpfv.tbllis.getTableHeader().setResizingAllowed(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == tpfv.btnsalir){
            tpfv.dispose();
        }else if(e.getSource() == tpfv.btnbuscar){
            BuscarTotales();
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
    
    public void BuscarTotales(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        String fechaDe = null;
        String fechaAl = null;
        Date fecha_de;
        Date fecha_al;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        fecha_de = tpfv.fechaDe.getDate();
        fecha_al = tpfv.fechaAl.getDate();
        fechaDe = sdf.format(fecha_de)+" 00:00:00";
        fechaAl = sdf.format(fecha_al)+" 23:59:59";
        
        tpfm.setId(0);
        tpfm.setFechaDe(fechaDe);
        tpfm.setFechaAl(fechaAl);
        
        modelt = tpfm.CargarTotales();
        
        tpfv.tbllis.setModel(modelt);
        
        /*ruvv.tbllis_ven.getColumnModel().getColumn(0).setMaxWidth(0);
        ruvv.tbllis_ven.getColumnModel().getColumn(0).setMinWidth(0);
        ruvv.tbllis_ven.getColumnModel().getColumn(0).setPreferredWidth(0);*/
        centrar.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
        for (int i = 0; i <= 1; i++) {
            tpfv.tbllis.getColumnModel().getColumn(i).setCellRenderer(centrar);
        }
        
    }
    
}
