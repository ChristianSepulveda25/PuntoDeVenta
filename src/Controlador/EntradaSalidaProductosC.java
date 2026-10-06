/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.*;
import Vista.AlmacenV;
import Vista.EntradaSalidaProductosV;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Christian Sepulveda
 */
public class EntradaSalidaProductosC implements ActionListener, KeyListener, MouseListener {
    private EntradaSalidaProductosV espv;
    private EntradaSalidaProductosM espm;
    private final CatProM cpm = new CatProM();
    private final AlmacenM am = new AlmacenM();
    private final ProductosM pm = new ProductosM();
    private final InventarioM im = new InventarioM();
    DefaultTableCellRenderer centrar = new DefaultTableCellRenderer();
    
    public EntradaSalidaProductosC(EntradaSalidaProductosM espm, EntradaSalidaProductosV espv) {
        this.espv = espv;
        this.espm = espm;
        this.espv.btnguardar.addActionListener(this);
        this.espv.btnsalir.addActionListener(this);
        this.espv.cmbcat.addActionListener(this);
        this.espv.cmbmovimiento.addActionListener(this);
        this.espv.tbllis.addMouseListener(this);
        this.espv.txtbuscar.addKeyListener(this);
        /*this.av.btnnue.addActionListener(this);
        this.av.btncan.addActionListener(this);
        this.av.btnedi.addActionListener(this);*/
    }

    public void iniciar() {
        espv.setTitle("Registro de entradas y salidas de productos");
        espv.tbllis.getTableHeader().setReorderingAllowed(false);
        espv.tbllis.getTableHeader().setResizingAllowed(false);
        espv.tblinv.getTableHeader().setReorderingAllowed(false);
        espv.tblinv.getTableHeader().setResizingAllowed(false);
        BuscarAlmacenes();
        BuscarCategorias();
        espv.txtcantidad.setText("1");
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
        if(e.getSource() == espv.btnsalir){
            espv.dispose();
        }else if(e.getSource() == espv.btnguardar){
            
            double totalInventarioAlmacen = 0;
            boolean existeAlmacen = true;
            int filasInventario = espv.tblinv.getRowCount();
            
            if( espv.cmbmovimiento.getSelectedIndex() == 0 ){
                JOptionPane.showMessageDialog(espv, "Seleccione un movimiento");
                espv.cmbmovimiento.requestFocus();
                return;
            // }else if( espv.cmbmovimiento.getSelectedIndex() == 3 && espv.cmbalm.getSelectedIndex() == 0 ){
            }else if( espv.cmbalm.getSelectedIndex() == 0 ){
                JOptionPane.showMessageDialog(espv, "Seleccione un almacen");
                espv.cmbalm.requestFocus();
                return;
            }
            
            //JOptionPane.showMessageDialog(espv, "Row Selected: " + espv.tbllis.getSelectedRow());
            
            if( espv.tbllis.getSelectedRow() >= 0 ){
                int codigoProducto = Integer.parseInt(espv.tbllis.getValueAt(espv.tbllis.getSelectedRow(), 0).toString());
                String[] partAlmacen = null;
                partAlmacen = espv.cmbalm.getSelectedItem().toString().split("'");
                int idAlmacen = Integer.parseInt(partAlmacen[1]);
                for( int i = 0 ; i < filasInventario ; i++ ){
                    /*JOptionPane.showMessageDialog(espv, "Código Producto: " + codigoProducto + 
                    " | Código Producto Tabla: " + Integer.parseInt(espv.tblinv.getValueAt(i, 1).toString()) );*/
                    if( codigoProducto == Integer.parseInt(espv.tblinv.getValueAt(i, 1).toString()) &&
                        idAlmacen == Integer.parseInt(espv.tblinv.getValueAt(i, 2).toString()) ){
                        totalInventarioAlmacen = Double.parseDouble(espv.tblinv.getValueAt(i, 4).toString());
                        // JOptionPane.showMessageDialog(espv, "Existe Inventario: " + totalInventarioAlmacen);
                        
                        existeAlmacen = false;
                        // JOptionPane.showMessageDialog(espv, "Existe Almacen: " + existeAlmacen);
                        break;
                    }
                }
            }
            
            /*JOptionPane.showMessageDialog(espv, "Total Inevntario Almacen: " + totalInventarioAlmacen);
            JOptionPane.showMessageDialog(espv, "Existe Almacen: " + existeAlmacen);
            JOptionPane.showMessageDialog(espv, "Filas Inventario: " + filasInventario);*/
            
            if( espv.cmbcat.getSelectedIndex() == 0 ){
                JOptionPane.showMessageDialog(espv, "Seleccione una categoría");
                espv.cmbcat.requestFocus();
            }else if( espv.txtcantidad.getText().toString().equals("") ){
                JOptionPane.showMessageDialog(espv, "Ingrese una cantidad mayor a 0");
                espv.txtcantidad.requestFocus();
            }else if( espv.txtcantidad.getText().toString().equals("0") ){
                JOptionPane.showMessageDialog(espv, "Ingrese una cantidad mayor a 0");
                espv.txtcantidad.requestFocus();
            }else if( Integer.parseInt(espv.txtcantidad.getText().toString()) < 1 ){
                JOptionPane.showMessageDialog(espv, "Ingrese una cantidad mayor a 0");
                espv.txtcantidad.requestFocus();
            }else if( espv.cmbmovimiento.getSelectedIndex() == 0 ){
                JOptionPane.showMessageDialog(espv, "Seleccione un movimiento");
                espv.cmbmovimiento.requestFocus();
            // }else if( espv.cmbmovimiento.getSelectedIndex() == 3 && espv.cmbalm.getSelectedIndex() == 0 ){
            }else if( espv.cmbalm.getSelectedIndex() == 0 ){
                JOptionPane.showMessageDialog(espv, "Seleccione un almacen");
                espv.cmbalm.requestFocus();
            }else if( espv.tbllis.getSelectedRow() < 0 ){
                JOptionPane.showMessageDialog(espv, "Seleccione un producto");
            }else if( espv.cmbmovimiento.getSelectedItem().toString() == "Salida" && existeAlmacen ){
                JOptionPane.showMessageDialog(espv, "No tienes productos para hacer la salida en el almacen seleccionado");
            }else if( espv.cmbmovimiento.getSelectedItem().toString() == "Salida" && totalInventarioAlmacen == 0 ){
                JOptionPane.showMessageDialog(espv, "No tienes productos para hacer la salida en el almacen seleccionado");
            }else if( espv.cmbmovimiento.getSelectedItem().toString() == "Salida" && filasInventario == 0 ){
                JOptionPane.showMessageDialog(espv, "No tienes productos para hacer la salida");
            }else if( espv.cmbmovimiento.getSelectedItem().toString() == "Salida" && totalInventarioAlmacen < Integer.parseInt(espv.txtcantidad.getText()) ){
                JOptionPane.showMessageDialog(espv, "Introduce una cantidad menor a los productos en el almacen seleccionado");
            }else{
                int resGuardar = JOptionPane.showConfirmDialog(null, "Guardar movimiento?", "Guardar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if( resGuardar == 0 ){
                    int guard = GuardarEntProducto();
                    if( guard == 0 ){
                        JOptionPane.showMessageDialog(espv, "Error al Guardar, Contacte a Soporte Técnico");
                    }else{
                        BuscarAlmacenes();
                        BuscarCategorias();
                        DefaultTableModel modeltVaciar = new DefaultTableModel();
                        espv.tbllis.setModel(modeltVaciar);
                        espv.tblinv.setModel(modeltVaciar);
                        espv.txtcomentario.setText("");
                        espv.txtbuscar.setText("");
                        espv.txtcantidad.setText("1");
                        espv.cmbmovimiento.setSelectedIndex(0);
                        JOptionPane.showMessageDialog(espv, "Registro Guardado");
                        espv.cmbcat.requestFocus();
                    }
                }
            }
        }else if(e.getSource() == espv.cmbcat){
            int seleccionCategoria = espv.cmbcat.getSelectedIndex();
            if( seleccionCategoria > 0 ){
                String categoria = (String) espv.cmbcat.getSelectedItem();
                String[] partCat;
                partCat = categoria.split("'");
                String cod = partCat[1];
                BuscarProductosCategoria(cod);
                DefaultTableModel modelt = new DefaultTableModel(){
                    @Override
                    public boolean isCellEditable(int row, int column) {
                       return false;
                    }
                };
                espv.tblinv.setModel(modelt);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getSource() == espv.txtcantidad){
            if(e.getSource() == espv.txtcantidad){
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
        if(e.getSource() == espv.txtbuscar){
            int seleccionCategoria = espv.cmbcat.getSelectedIndex();
            if( seleccionCategoria > 0 ){
                String categoria = (String) espv.cmbcat.getSelectedItem();
                String[] partCat;
                partCat = categoria.split("'");
                String cod = partCat[1];
                CargarProductosBuscar(espv.txtbuscar.getText(),cod);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == espv.tbllis){
            int fila = 0;
            fila = espv.tbllis.getSelectedRow();
            if(fila < 0){
                JOptionPane.showMessageDialog(espv, "Seleccione un Producto");
            }else{
                CargarInventario(Integer.parseInt(espv.tbllis.getValueAt(fila, 0).toString()));
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
    
    public void CargarProductosBuscar(String buscar, String categoria){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
         };
        
        //System.out.println(""+ buscar);
        String nom = null;
        nom = buscar;
        pm.setId(-8);
        pm.setCod(null);
        pm.setNom(nom);
        pm.setSa(0);
        pm.setSm(0);
        pm.setSx(0);
        pm.setPre(0);
        pm.setCat(categoria);
        pm.setEst(0);
        modelt = pm.CargarProductos();
        espv.tbllis.setModel(modelt);
        
        espv.tbllis.getColumnModel().getColumn(0).setMaxWidth(0);
        espv.tbllis.getColumnModel().getColumn(0).setMinWidth(0);
        espv.tbllis.getColumnModel().getColumn(0).setPreferredWidth(0);//ID
        espv.tbllis.getColumnModel().getColumn(2).setMaxWidth(200);
        espv.tbllis.getColumnModel().getColumn(2).setMinWidth(200);
        espv.tbllis.getColumnModel().getColumn(2).setPreferredWidth(200);//NOM
        centrar.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
        espv.tbllis.getColumnModel().getColumn(2).setCellRenderer(centrar);
    }
        
    public void BuscarAlmacenes(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        espv.cmbalm.removeAllItems();
        espv.cmbalm.addItem("Seleccione");
        am.setId(-2);
        //cpm.setCon(null);
        //cpm.setNom(null);
        //cpm.setEst(0);
        modelt = am.CargarAlmacen();
        for (int i = 0; i < modelt.getRowCount(); i++) {
            espv.cmbalm.addItem(modelt.getValueAt(i, 1).toString() + " '" + modelt.getValueAt(i, 0).toString());
        }
    }
    
    public void BuscarCategorias(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        espv.cmbcat.removeAllItems();
        espv.cmbcat.addItem("Seleccione");
        cpm.setId(-2);
        //cpm.setCon(null);
        //cpm.setNom(null);
        //cpm.setEst(0);
        modelt = cpm.CargarCatPro();
        for (int i = 0; i < modelt.getRowCount(); i++) {
            espv.cmbcat.addItem(modelt.getValueAt(i, 1).toString() + " '" + modelt.getValueAt(i, 0).toString());
        }
    }
    
    public void BuscarProductosCategoria(String codigo){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        pm.setId(-3);
        pm.setCod(null);
        pm.setNom(null);
        pm.setSa(0);
        pm.setSm(0);
        pm.setSx(0);
        pm.setPre(0);
        pm.setCat(codigo);
        pm.setEst(0);
        modelt = pm.CargarProductos();
        espv.tbllis.setModel(modelt);
        
        espv.tbllis.getColumnModel().getColumn(0).setMaxWidth(0);
        espv.tbllis.getColumnModel().getColumn(0).setMinWidth(0);
        espv.tbllis.getColumnModel().getColumn(0).setPreferredWidth(0);//ID
        espv.tbllis.getColumnModel().getColumn(2).setMaxWidth(200);
        espv.tbllis.getColumnModel().getColumn(2).setMinWidth(200);
        espv.tbllis.getColumnModel().getColumn(2).setPreferredWidth(200);//NOM
        centrar.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
        espv.tbllis.getColumnModel().getColumn(2).setCellRenderer(centrar);
        /*espv.tbllis.getColumnModel().getColumn(3).setCellRenderer(centrar);
        espv.tbllis.getColumnModel().getColumn(4).setCellRenderer(centrar);
        espv.tbllis.getColumnModel().getColumn(5).setCellRenderer(centrar);*/
        //pv.tbllis.getColumnModel().getColumn(6).setCellRenderer(centrar);
        
    }
    
    public int GuardarEntProducto(){
        int id = 0;
        String con = null;
        int can = 0;
        int tip = 0;
        String fec = null;
        int est = 0;
        int pro = 0;
        double tot = 0;
        double can_ant = 0;
        int id_almacen = 0;
        int id_almacen_destino = 0;
        int id_inventario = 0;
        
        id = 0;
        con = espv.txtcomentario.getText();
        can = Integer.parseInt(espv.txtcantidad.getText());
        
        String tipoMovimiento = (String) espv.cmbmovimiento.getSelectedItem();
        if( tipoMovimiento.equals("Entrada") ){
            tip = 0;
        }else if( tipoMovimiento.equals("Salida") ){
            tip = 1;
        }
        
        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        fec = sdf.format(dt);
        est = 1;
        pro = Integer.parseInt(espv.tbllis.getValueAt(espv.tbllis.getSelectedRow(), 0).toString());
        tot = Double.parseDouble(espv.tbllis.getValueAt(espv.tbllis.getSelectedRow(), 3).toString())+can;
        can_ant = Double.parseDouble(espv.tbllis.getValueAt(espv.tbllis.getSelectedRow(), 3).toString());
        String[] partAlmacen = null;
        partAlmacen = espv.cmbalm.getSelectedItem().toString().split("'");
        id_almacen = Integer.parseInt(partAlmacen[1]);
        
        /*System.out.println("ID: " + id);
        System.out.println("Concepto: " + con);
        System.out.println("Cantidad: " + can);
        System.out.println("Tipo: " + tip);
        System.out.println("Fecha: " + fec);
        System.out.println("Estatus: " + est);
        System.out.println("Producto: " + pro);
        System.out.println("Total: " + tot);
        System.out.println("Cantidad Anterior: " + can_ant);
        System.out.println("Id Almacen: " + id_almacen);
        System.out.println("======================================");*/
        
        espm.setId(id);
        espm.setCon(con);
        espm.setCan(can);
        espm.setTip(tip);
        espm.setFec(fec);
        espm.setEst(est);
        espm.setPro(pro);
        espm.setTot(tot);
        espm.setCant_ant(can_ant);
        espm.setId_almacen(id_almacen);
        
        if(espm.GuardarESProducto()){
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
        espv.tblinv.setModel(modelt);
        
        espv.tblinv.getColumnModel().getColumn(0).setMaxWidth(0);
        espv.tblinv.getColumnModel().getColumn(0).setMinWidth(0);
        espv.tblinv.getColumnModel().getColumn(0).setPreferredWidth(0);//ID
        espv.tblinv.getColumnModel().getColumn(1).setMaxWidth(0);
        espv.tblinv.getColumnModel().getColumn(1).setMinWidth(0);
        espv.tblinv.getColumnModel().getColumn(1).setPreferredWidth(0);//ID
        espv.tblinv.getColumnModel().getColumn(2).setMaxWidth(0);
        espv.tblinv.getColumnModel().getColumn(2).setMinWidth(0);
        espv.tblinv.getColumnModel().getColumn(2).setPreferredWidth(0);//ID
        centrar.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
        espv.tblinv.getColumnModel().getColumn(3).setCellRenderer(centrar);
        espv.tblinv.getColumnModel().getColumn(4).setCellRenderer(centrar);
    }
    
}
