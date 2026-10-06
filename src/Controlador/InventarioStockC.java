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
public class InventarioStockC implements ActionListener, KeyListener {
    private InventarioStockM im;
    private InventarioStockV iv; 
    private final CatProM cpm = new CatProM();
    private final AlmacenM am = new AlmacenM();
    private final ProductosM pm = new ProductosM();
    DefaultTableCellRenderer centrar = new DefaultTableCellRenderer();
    
    public InventarioStockC(InventarioStockM im, InventarioStockV iv) {
        this.iv = iv;
        this.im = im;
        this.iv.cmbalm.addActionListener(this);
        this.iv.cmbstock.addActionListener(this);
        this.iv.btnexportarexcel.addActionListener(this);
        this.iv.btnexportarpdf.addActionListener(this);
        this.iv.btnsalir.addActionListener(this);
        this.iv.btnbuscar.addActionListener(this);
        // this.mav.btnguardar.addActionListener(this);
    }
    
    public void iniciar() {
        iv.setTitle("Inventario por Stock");
        ImageIcon originalIcon  = new ImageIcon(getClass().getResource("/img/FondoCopyCentro72ppp.png"));
        Image image = originalIcon.getImage();
        Image scaledImage = image.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        this.iv.setFrameIcon(resizedIcon);
        // this.iv.btnbuscar.setVisible(false);
        iv.tblinv.getTableHeader().setReorderingAllowed(false) ;
        // iv.tblinv.getTableHeader().setResizingAllowed(false);
        BuscarAlmacenes();
        BuscarCategorias();
        CargarCatPro();
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
        if( e.getSource() == iv.btnbuscar ){
            if( iv.cmbstock.getSelectedIndex() > 0 ){
                BuscarInventario();
            }else{
                JOptionPane.showMessageDialog(iv, "Seleccione stock a buscar");
            }
        }else if( e.getSource() == iv.cmbstock ){
            /*if( iv.cmbstock.getSelectedIndex() > 0 ){
                BuscarInventario();
            }else{
                JOptionPane.showMessageDialog(iv, "Seleccione stock a buscar");
            }*/
        }else if( e.getSource() == iv.cmbalm ){
            /*if( iv.cmbstock.getSelectedIndex() > 0 ){
                BuscarInventario();
            }else{
                JOptionPane.showMessageDialog(iv, "Seleccione stock a buscar");
            }*/
        }/*else if( e.getSource() == iv.btnbuscar ){
            if( iv.cmbstock.getSelectedIndex() > 0 ){
                BuscarInventario();
            }else{
                JOptionPane.showMessageDialog(iv, "Seleccione stock a buscar");
            }
        }*/else if( e.getSource() == iv.btnexportarexcel ){
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
                int id = 0;
                int id_almacen = -1;
                int estatus = 1;
                String tipoStock = null;

                if( iv.cmbstock.getSelectedIndex() == 1 && iv.cmbalm.getSelectedIndex() == 0 ){
                    id = -1;
                }else if( iv.cmbstock.getSelectedIndex() == 1 && iv.cmbalm.getSelectedIndex() != 0 ){
                    id = -3;
                    String[] partAlmacen = null;
                    partAlmacen = iv.cmbalm.getSelectedItem().toString().split("'");
                    id_almacen = Integer.parseInt(partAlmacen[1]);
                }if( iv.cmbstock.getSelectedIndex() == 2 && iv.cmbalm.getSelectedIndex() == 0 ){
                    id = -2;
                }if( iv.cmbstock.getSelectedIndex() == 2 && iv.cmbalm.getSelectedIndex() != 0 ){
                    id = -4;
                    String[] partAlmacen = null;
                    partAlmacen = iv.cmbalm.getSelectedItem().toString().split("'");
                    id_almacen = Integer.parseInt(partAlmacen[1]);
                }
                
                tipoStock = iv.cmbstock.getSelectedItem().toString();
                
                // String url = "http://www.google.com";
                String url = "http://localhost/CopyCentro/Reportes/R_InvestarioPorStock.php?a="+id+"&b="+id_almacen+"&c="+estatus+"&d="+tipoStock;
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
    }
    
    public void CargarCatPro(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        iv.cmbCat.removeAllItems();
        cpm.setId(-2);
        
        modelt = cpm.CargarCatPro();
        
        // Iterar sobre las filas del DefaultTableModel y agregar los usuarios al JComboBox
        for (int i = 0; i < modelt.getRowCount(); i++) {
            // Asumimos que la columna 0 es el 'id' y la columna 1 es el 'nombre'
            int id = (int) modelt.getValueAt(i, 0); // Obtén el 'id'
            String nombre = (String) modelt.getValueAt(i, 1); // Obtén el 'nombre'

            // Crear un nuevo objeto Usuario
            CatProListM catProList = new CatProListM(id, nombre);

            // Agregar el usuario al JComboBox
            iv.cmbCat.addItem(catProList);
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
        iv.cmbalm.addItem("General");
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
        
        int id = 0;
        int id_almacen = 0;
        int estatus = 1;
        
        if( iv.cmbstock.getSelectedIndex() == 1 && iv.cmbalm.getSelectedIndex() == 0 ){
            id = -1;
        }else if( iv.cmbstock.getSelectedIndex() == 1 && iv.cmbalm.getSelectedIndex() != 0 ){
            id = -3;
            String[] partAlmacen = null;
            partAlmacen = iv.cmbalm.getSelectedItem().toString().split("'");
            id_almacen = Integer.parseInt(partAlmacen[1]);
        }if( iv.cmbstock.getSelectedIndex() == 2 && iv.cmbalm.getSelectedIndex() == 0 ){
            id = -2;
        }if( iv.cmbstock.getSelectedIndex() == 2 && iv.cmbalm.getSelectedIndex() != 0 ){
            id = -4;
            String[] partAlmacen = null;
            partAlmacen = iv.cmbalm.getSelectedItem().toString().split("'");
            id_almacen = Integer.parseInt(partAlmacen[1]);
        }
        
        CatProListM catProSeleccionado = (CatProListM) iv.cmbCat.getSelectedItem();
        int idSeleccionadoCat = catProSeleccionado.getId();
        String txtBuscar = "";
        txtBuscar = iv.txtBuscar.getText();

        im.setId(id);
        im.setId_alm(id_almacen);
        im.setEstatus(estatus);
        im.setId_cat(idSeleccionadoCat);
        im.setNombreBuscar(txtBuscar);
        modelt = im.CargarInventarioStock();
        iv.tblinv.setModel(modelt);

        if( iv.cmbalm.getSelectedIndex() == 0 ){
            iv.tblinv.getColumnModel().getColumn(1).setPreferredWidth(250);
            /*iv.tblinv.getColumnModel().getColumn(1).setMaxWidth(100);
            iv.tblinv.getColumnModel().getColumn(1).setMinWidth(100);
            iv.tblinv.getColumnModel().getColumn(1).setPreferredWidth(100);//ID
            iv.tblinv.getColumnModel().getColumn(2).setMaxWidth(100);
            iv.tblinv.getColumnModel().getColumn(2).setMinWidth(100);
            iv.tblinv.getColumnModel().getColumn(2).setPreferredWidth(100);//ID
            iv.tblinv.getColumnModel().getColumn(3).setMaxWidth(100);
            iv.tblinv.getColumnModel().getColumn(3).setMinWidth(100);
            iv.tblinv.getColumnModel().getColumn(3).setPreferredWidth(100);//ID*/
        }else{
            iv.tblinv.getColumnModel().getColumn(1).setPreferredWidth(250);
            /*iv.tblinv.getColumnModel().getColumn(1).setMaxWidth(100);
            iv.tblinv.getColumnModel().getColumn(1).setMinWidth(100);
            iv.tblinv.getColumnModel().getColumn(1).setPreferredWidth(100);//ID
            iv.tblinv.getColumnModel().getColumn(2).setMaxWidth(100);
            iv.tblinv.getColumnModel().getColumn(2).setMinWidth(100);
            iv.tblinv.getColumnModel().getColumn(2).setPreferredWidth(100);//ID
            iv.tblinv.getColumnModel().getColumn(3).setMaxWidth(100);
            iv.tblinv.getColumnModel().getColumn(3).setMinWidth(100);
            iv.tblinv.getColumnModel().getColumn(3).setPreferredWidth(100);//ID
            iv.tblinv.getColumnModel().getColumn(4).setMaxWidth(100);
            iv.tblinv.getColumnModel().getColumn(4).setMinWidth(100);
            iv.tblinv.getColumnModel().getColumn(4).setPreferredWidth(100);//ID*/
        }
        
        /*centrar.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
        iv.tblinv.getColumnModel().getColumn(3).setCellRenderer(centrar);
        iv.tblinv.getColumnModel().getColumn(4).setCellRenderer(centrar);*/
    }
    
    
}
