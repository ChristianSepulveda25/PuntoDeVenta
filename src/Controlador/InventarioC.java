/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.*;
import Vista.*;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Christian Sepulveda
 */
public class InventarioC implements ActionListener, KeyListener {
    private InventarioM im;
    private InventarioV iv; 
    private final CatProM cpm = new CatProM();
    private final AlmacenM am = new AlmacenM();
    private final ProductosM pm = new ProductosM();
    DefaultTableCellRenderer centrar = new DefaultTableCellRenderer();
    
    public InventarioC(InventarioM im, InventarioV iv) {
        this.iv = iv;
        this.im = im;
        this.iv.cmbalm.addActionListener(this);
        this.iv.txtbuscar.addKeyListener(this);
        this.iv.btnexportarexcel.addActionListener(this);
        this.iv.btnexportarpdf.addActionListener(this);
        this.iv.btnsalir.addActionListener(this);
        // this.mav.btnguardar.addActionListener(this);
    }
    
    public void iniciar() {
        iv.setTitle("Inventario por Almacen");
        ImageIcon originalIcon  = new ImageIcon(getClass().getResource("/img/FondoCopyCentro72ppp.png"));
        Image image = originalIcon.getImage();
        Image scaledImage = image.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        this.iv.setFrameIcon(resizedIcon);
        iv.tblinv.getTableHeader().setReorderingAllowed(false) ;
        // iv.tblinv.getTableHeader().setResizingAllowed(false);
        BuscarAlmacenes();
        BuscarCategorias();
        //mav.txtcantidad.setText("1");
        // av.txtid.setVisible(false);
        // CargarAlmacen();
        /*av.txtnom.setEnabled(false);
        av.cmbest.setEnabled(false);
        av.btngua.setEnabled(false);
        av.btncan.setEnabled(false);
        av.btnnue.requestFocus();*/
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if( e.getSource() == iv.cmbalm ){
            if( iv.cmbalm.getSelectedIndex() > 0 ){
                BuscarInventario();
            }
        }else if( e.getSource() == iv.btnexportarexcel ){
            int filas = iv.tblinv.getRowCount();
            if( filas <= 0 ){
                JOptionPane.showMessageDialog(iv, "No tiene filas para exportar");
            }else{
                ExportarExcel obj;
                try {
                    obj = new ExportarExcel();
                    obj.exportarExcel(iv.tblinv);
                }catch(IOException ex) {
                    System.out.println("Error: " + ex);
                }
            }
        }else if( e.getSource() == iv.btnsalir ){
            iv.dispose();
        }else if(e.getSource() == iv.btnexportarpdf){
            int filas = iv.tblinv.getRowCount();
            if( filas <= 0 ){
                JOptionPane.showMessageDialog(iv, "No tiene filas para exportar");
            }else{
                String buscar = "";
                int idAlmacen = 0;
                String[] partAlmacen = null;
                partAlmacen = iv.cmbalm.getSelectedItem().toString().split("'");
                idAlmacen = Integer.parseInt(partAlmacen[1]);

                buscar = "'"+iv.txtbuscar.getText().toString()+"'";
                if( iv.txtbuscar.getText().toString().equals("") ){
                    buscar = "''";
                }

                // String url = "http://www.google.com";
                String url = "http://localhost/CopyCentro/Reportes/R_InventarioActivo.php?a="+idAlmacen+"&b="+buscar;
                try {
                    Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
                } catch (IOException ex) {
                    Logger.getLogger(R_VentasFechaRealizadoC.class.getName()).log(Level.SEVERE, null, ex);
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
        if(e.getSource() == iv.txtbuscar){
            BuscarInventario();
        }
    }
    
    public void BuscarAlmacenes(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        iv.cmbalm.removeAllItems();
        iv.cmbalm.addItem("Seleccione");
        am.setId(-2);
        //cpm.setCon(null);
        //cpm.setNom(null);
        //cpm.setEst(0);
        modelt = am.CargarAlmacen();
        for (int i = 0; i < modelt.getRowCount(); i++) {
            iv.cmbalm.addItem(modelt.getValueAt(i, 1).toString() + " '" + modelt.getValueAt(i, 0).toString());
        }
    }
    
    public void BuscarCategorias(){
        /*DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        iv.cmbcat.removeAllItems();
        iv.cmbcat.addItem("Seleccione");
        cpm.setId(-2);
        //cpm.setCon(null);
        //cpm.setNom(null);
        //cpm.setEst(0);
        modelt = cpm.CargarCatPro();
        for (int i = 0; i < modelt.getRowCount(); i++) {
            iv.cmbcat.addItem(modelt.getValueAt(i, 1).toString() + " '" + modelt.getValueAt(i, 0).toString());
        }*/
    }
    
    public void BuscarInventario(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        String buscar = "";
        int idAlmacen = 0;
        String[] partAlmacen = null;
        partAlmacen = iv.cmbalm.getSelectedItem().toString().split("'");
        idAlmacen = Integer.parseInt(partAlmacen[1]);
        
        buscar = "'"+iv.txtbuscar.getText().toString()+"'";
        if( iv.txtbuscar.getText().toString().equals("") ){
            buscar = "''";
        }
        
        im.setId_inv(-2);
        im.setId_prod(0);
        im.setId_alm(idAlmacen);
        im.setCantidad(0);
        im.setBuscar(buscar);
        modelt = im.CargarInventario();
        iv.tblinv.setModel(modelt);
        
        iv.tblinv.getColumnModel().getColumn(0).setMaxWidth(0);
        iv.tblinv.getColumnModel().getColumn(0).setMinWidth(0);
        iv.tblinv.getColumnModel().getColumn(0).setPreferredWidth(0);//ID
        iv.tblinv.getColumnModel().getColumn(1).setMaxWidth(0);
        iv.tblinv.getColumnModel().getColumn(1).setMinWidth(0);
        iv.tblinv.getColumnModel().getColumn(1).setPreferredWidth(0);//ID
        iv.tblinv.getColumnModel().getColumn(2).setMaxWidth(0);
        iv.tblinv.getColumnModel().getColumn(2).setMinWidth(0);
        iv.tblinv.getColumnModel().getColumn(2).setPreferredWidth(0);//ID
        iv.tblinv.getColumnModel().getColumn(3).setPreferredWidth(250);//ID
        /*iv.tblinv.getColumnModel().getColumn(4).setMaxWidth(90);
        iv.tblinv.getColumnModel().getColumn(4).setMinWidth(90);
        iv.tblinv.getColumnModel().getColumn(4).setPreferredWidth(100);//ID
        iv.tblinv.getColumnModel().getColumn(5).setMaxWidth(90);
        iv.tblinv.getColumnModel().getColumn(5).setMinWidth(90);
        iv.tblinv.getColumnModel().getColumn(5).setPreferredWidth(100);//ID
        iv.tblinv.getColumnModel().getColumn(6).setMaxWidth(90);
        iv.tblinv.getColumnModel().getColumn(6).setMinWidth(90);
        iv.tblinv.getColumnModel().getColumn(6).setPreferredWidth(100);//ID
        iv.tblinv.getColumnModel().getColumn(7).setMaxWidth(90);
        iv.tblinv.getColumnModel().getColumn(7).setMinWidth(90);
        iv.tblinv.getColumnModel().getColumn(7).setPreferredWidth(100);//ID
        iv.tblinv.getColumnModel().getColumn(8).setMaxWidth(90);
        iv.tblinv.getColumnModel().getColumn(8).setMinWidth(90);
        iv.tblinv.getColumnModel().getColumn(8).setPreferredWidth(100);//ID
        iv.tblinv.getColumnModel().getColumn(9).setMaxWidth(90);
        iv.tblinv.getColumnModel().getColumn(9).setMinWidth(90);
        iv.tblinv.getColumnModel().getColumn(9).setPreferredWidth(100);//ID*/
        /*centrar.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
        iv.tblinv.getColumnModel().getColumn(3).setCellRenderer(centrar);
        iv.tblinv.getColumnModel().getColumn(4).setCellRenderer(centrar);*/
    }
    
    
}
