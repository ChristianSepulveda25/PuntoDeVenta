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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Christian Sepulveda
 */
public class MovimientosAlmacenC implements ActionListener, KeyListener, MouseListener {
    private MovimientosAlmacenV mav;
    private MovimientosAlmacenM mam;
    private final CatProM cpm = new CatProM();
    private final AlmacenM am = new AlmacenM();
    private final ProductosM pm = new ProductosM();
    private final InventarioM im = new InventarioM();
    DefaultTableCellRenderer centrar = new DefaultTableCellRenderer();
    
    public MovimientosAlmacenC(MovimientosAlmacenM mam, MovimientosAlmacenV mav) {
        this.mav = mav;
        this.mam = mam;
        this.mav.btnguardar.addActionListener(this);
        this.mav.btnsalir.addActionListener(this);
        this.mav.cmbcat.addActionListener(this);
        this.mav.tbllis.addMouseListener(this);
        this.mav.txtbuscar.addKeyListener(this);
        /*this.av.btnnue.addActionListener(this);
        this.av.btncan.addActionListener(this);
        this.av.btnedi.addActionListener(this);*/
    }

    public void iniciar() {
        mav.setTitle("Registro de movimientos en almacenes");
        ImageIcon originalIcon  = new ImageIcon(getClass().getResource("/img/FondoCopyCentro72ppp.png"));
        Image image = originalIcon.getImage();
        Image scaledImage = image.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        this.mav.setFrameIcon(resizedIcon);
        mav.tbllis.getTableHeader().setReorderingAllowed(false);
        //mav.tbllis.getTableHeader().setResizingAllowed(false);
        mav.tblinv.getTableHeader().setReorderingAllowed(false);
        //mav.tblinv.getTableHeader().setResizingAllowed(false);
        BuscarAlmacenes();
        BuscarCategorias();
        mav.txtcantidad.setText("1");
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
        if(e.getSource() == mav.btnsalir){
            mav.dispose();
        }else if(e.getSource() == mav.btnguardar){
            int filaInvSelect = mav.tblinv.getSelectedRow();
            int filaProdSelect = mav.tbllis.getSelectedRow();
            int idAlmacenDestino = 0;
            int idAlmacenInventario = -1;
            
            double cantidadInventarioSeleccionado = 0;
            if( mav.tblinv.getSelectedRow() >= 0 ){
                cantidadInventarioSeleccionado = Double.parseDouble( mav.tblinv.getValueAt(filaInvSelect, 4).toString() );
            }
            
            if( filaInvSelect >= 0 ){
                int id_producto = Integer.parseInt(mav.tbllis.getValueAt(mav.tbllis.getSelectedRow(), 0).toString());
                CargarInventario(id_producto);
                // Cargar Productos
                String categoria = (String) mav.cmbcat.getSelectedItem();
                String[] partCat;
                partCat = categoria.split("'");
                String cod = partCat[1];
                BuscarProductosCategoria(cod);
                // ===============================
                mav.tblinv.changeSelection(filaInvSelect, filaInvSelect, false, false);
                mav.tbllis.changeSelection(filaProdSelect, filaProdSelect, false, false);
                idAlmacenInventario = Integer.parseInt(mav.tblinv.getValueAt(mav.tblinv.getSelectedRow(), 2).toString());
            }
            
            if( mav.cmbalmdestino.getSelectedIndex() > 0 ){
                String[] partAlmacen = null;
                partAlmacen = mav.cmbalmdestino.getSelectedItem().toString().split("'");
                idAlmacenDestino = Integer.parseInt(partAlmacen[1]);
            }
            
            if( mav.cmbcat.getSelectedIndex() == 0 ){
                JOptionPane.showMessageDialog(mav, "Seleccione una categoría");
                mav.cmbcat.requestFocus();
            }else if( mav.txtcantidad.getText().toString().equals("") ){
                JOptionPane.showMessageDialog(mav, "Ingrese una cantidad mayor a 0");
                mav.txtcantidad.requestFocus();
            }else if( mav.txtcantidad.getText().toString().equals("0") ){
                JOptionPane.showMessageDialog(mav, "Ingrese una cantidad mayor a 0");
                mav.txtcantidad.requestFocus();
            }else if( mav.cmbalmdestino.getSelectedIndex() == 0 ){
                JOptionPane.showMessageDialog(mav, "Seleccione un almacen de destino");
                mav.cmbalmdestino.requestFocus();
            }else if( mav.tbllis.getSelectedRow() < 0 ){
                JOptionPane.showMessageDialog(mav, "Seleccione un producto");
            }else if( mav.tblinv.getSelectedRow() < 0 ){
                JOptionPane.showMessageDialog(mav, "Seleccione un inventario");
            }else if( cantidadInventarioSeleccionado <= 0 ){
                JOptionPane.showMessageDialog(mav, "No dispone de Stock suficiente en el inventario seleccionado");
            }else if( idAlmacenInventario == idAlmacenDestino ){
                JOptionPane.showMessageDialog(mav, "Seleccione un almacen de destino diferente al inventario");
            }else{
                int resGuardar = JOptionPane.showConfirmDialog(null, "Guardar movimiento?", "Guardar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if( resGuardar == 0 ){
                    int guard = GuardarMovimientosAlmacen();
                    if( guard == 0 ){
                        JOptionPane.showMessageDialog(mav, "Error al Guardar, Contacte a Soporte Técnico");
                    }else{
                        BuscarAlmacenes();
                        BuscarCategorias();
                        DefaultTableModel modeltVaciar = new DefaultTableModel();
                        mav.tbllis.setModel(modeltVaciar);
                        mav.tblinv.setModel(modeltVaciar);
                        mav.txtcantidad.setText("1");
                        JOptionPane.showMessageDialog(mav, "Registro Guardado");
                        mav.cmbcat.requestFocus();
                    }
                }
            }
        }else if(e.getSource() == mav.cmbcat){
            int seleccionCategoria = mav.cmbcat.getSelectedIndex();
            if( seleccionCategoria > 0 ){
                String categoria = (String) mav.cmbcat.getSelectedItem();
                String[] partCat;
                partCat = categoria.split("'");
                String cod = partCat[1];
                BuscarProductosCategoria(cod);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getSource() == mav.txtcantidad){
            if(e.getSource() == mav.txtcantidad){
                char caracter = e.getKeyChar();
                if (((caracter < '0') || (caracter > '9'))) {
                    e.consume();
                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if( e.getSource() == mav.txtbuscar ){
            int seleccionCategoria = mav.cmbcat.getSelectedIndex();
            if( seleccionCategoria > 0 ){
                String categoria = (String) mav.cmbcat.getSelectedItem();
                String[] partCat;
                partCat = categoria.split("'");
                String cod = partCat[1];
                BuscarProductosCategoria(cod);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == mav.tbllis){
            int fila = 0;
            fila = mav.tbllis.getSelectedRow();
            if(fila < 0){
                JOptionPane.showMessageDialog(mav, "Seleccione un Producto");
            }else{
                CargarInventario(Integer.parseInt(mav.tbllis.getValueAt(fila, 0).toString()));
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
    }
        
    public void BuscarAlmacenes(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        mav.cmbalmdestino.removeAllItems();
        mav.cmbalmdestino.addItem("Seleccione");
        am.setId(-2);
        //cpm.setCon(null);
        //cpm.setNom(null);
        //cpm.setEst(0);
        modelt = am.CargarAlmacen();
        for (int i = 0; i < modelt.getRowCount(); i++) {
            mav.cmbalmdestino.addItem(modelt.getValueAt(i, 1).toString() + " '" + modelt.getValueAt(i, 0).toString());
        }
    }
    
    public void BuscarCategorias(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        mav.cmbcat.removeAllItems();
        mav.cmbcat.addItem("Seleccione");
        cpm.setId(-2);
        //cpm.setCon(null);
        //cpm.setNom(null);
        //cpm.setEst(0);
        modelt = cpm.CargarCatPro();
        for (int i = 0; i < modelt.getRowCount(); i++) {
            mav.cmbcat.addItem(modelt.getValueAt(i, 1).toString() + " '" + modelt.getValueAt(i, 0).toString());
        }
    }
    
    public void BuscarProductosCategoria(String codigo){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        String buscar;
        buscar = mav.txtbuscar.getText();
        
        pm.setId(-3);
        pm.setCod(null);
        pm.setNom(buscar);
        pm.setSa(0);
        pm.setSm(0);
        pm.setSx(0);
        pm.setPre(0);
        pm.setCat(codigo);
        pm.setEst(0);
        modelt = pm.CargarProductos();
        mav.tbllis.setModel(modelt);
        
        mav.tbllis.getColumnModel().getColumn(0).setMaxWidth(0);
        mav.tbllis.getColumnModel().getColumn(0).setMinWidth(0);
        mav.tbllis.getColumnModel().getColumn(0).setPreferredWidth(0);//ID
        // mav.tbllis.getColumnModel().getColumn(2).setMaxWidth(200);
        // mav.tbllis.getColumnModel().getColumn(2).setMinWidth(200);
        mav.tbllis.getColumnModel().getColumn(2).setPreferredWidth(200);//NOM
        centrar.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
        mav.tbllis.getColumnModel().getColumn(2).setCellRenderer(centrar);
        
    }
    
    public int GuardarMovimientosAlmacen(){
        int id = 0;
        int id_producto = 0;
        int id_almacen = 0;
        int id_inventario = 0;
        int id_almacen_destino = 0;
        double cantidad = 0.0;
        double cantidad_inventario = 0.0;
        String fecha = null;
        
        id = 0;
        id_producto = Integer.parseInt(mav.tbllis.getValueAt(mav.tbllis.getSelectedRow(), 0).toString());
        id_almacen = Integer.parseInt(mav.tblinv.getValueAt(mav.tblinv.getSelectedRow(), 2).toString());
        id_inventario = Integer.parseInt(mav.tblinv.getValueAt(mav.tblinv.getSelectedRow(), 0).toString());
        String[] partAlmacen = null;
        partAlmacen = mav.cmbalmdestino.getSelectedItem().toString().split("'");
        id_almacen_destino = Integer.parseInt(partAlmacen[1]);
        cantidad = Double.parseDouble(mav.txtcantidad.getText());
        cantidad_inventario = Double.parseDouble(mav.tblinv.getValueAt(mav.tblinv.getSelectedRow(), 4).toString());
        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        fecha = sdf.format(dt);
        
        mam.setId(id);
        mam.setId_producto(id_producto);
        mam.setId_almacen(id_almacen);
        mam.setId_inventario(id_inventario);
        mam.setId_almacen_destino(id_almacen_destino);
        mam.setCantidad(cantidad);
        mam.setCantidad_inventario(cantidad_inventario);
        mam.setFecha(fecha);
        
        if(mam.GuardarMovimientosProductos()){
            return 1;
        }
        return 0;
    }
    
    public void CargarInventario(int idProducto){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        im.setId_inv(-1);
        im.setId_prod(idProducto);
        im.setId_alm(0);
        im.setCantidad(0);
        im.setBuscar("''");
        modelt = im.CargarInventario();
        mav.tblinv.setModel(modelt);
        
        mav.tblinv.getColumnModel().getColumn(0).setMaxWidth(0);
        mav.tblinv.getColumnModel().getColumn(0).setMinWidth(0);
        mav.tblinv.getColumnModel().getColumn(0).setPreferredWidth(0);//ID
        mav.tblinv.getColumnModel().getColumn(1).setMaxWidth(0);
        mav.tblinv.getColumnModel().getColumn(1).setMinWidth(0);
        mav.tblinv.getColumnModel().getColumn(1).setPreferredWidth(0);//ID
        mav.tblinv.getColumnModel().getColumn(2).setMaxWidth(0);
        mav.tblinv.getColumnModel().getColumn(2).setMinWidth(0);
        mav.tblinv.getColumnModel().getColumn(2).setPreferredWidth(0);//ID
        centrar.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
        mav.tblinv.getColumnModel().getColumn(3).setCellRenderer(centrar);
        mav.tblinv.getColumnModel().getColumn(4).setCellRenderer(centrar);
    }
    
}
