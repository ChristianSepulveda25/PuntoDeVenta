/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.*;
import Vista.*;
import innotpv.ColorearFilasProductos;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Christian Sepulveda
 */
public class ProductosC implements ActionListener, KeyListener, ItemListener {
    private final ProductosM pm;
    private final ProductosV pv;
    private final CatProM cpm = new CatProM();
    private final AlmacenM am = new AlmacenM();
    private final MedidaM mm = new MedidaM();
    private final InventarioM im = new InventarioM();
    private final MovimientosAlmacenM mam = new MovimientosAlmacenM();
    private final EntradasSalidasPExpressM espem = new EntradasSalidasPExpressM();
    private final PreciosPorcentajesM ppm = new PreciosPorcentajesM();
    DefaultTableCellRenderer centrar = new DefaultTableCellRenderer();
    public int fila_s = 0;
    ColorearFilasProductos colorearFilas = new ColorearFilasProductos();
    TableRowSorter<TableModel> sorter;
    public double costoAnterior = 0.0;
    public double precioAnterior = 0.0;
    public double mayoreoAnterior = 0.0;
    public double menudeoAnterior = 0.0;

    public ProductosC(ProductosM pm, ProductosV pv) {
        this.pm = pm;
        this.pv = pv;
        this.pv.btnnue.addActionListener(this);
        this.pv.btngua.addActionListener(this);
        this.pv.btncan.addActionListener(this);
        this.pv.btnedi.addActionListener(this);
        this.pv.btnsal.addActionListener(this);
        this.pv.cmbcat.addActionListener(this);
        // this.pv.cmbmedidas.addActionListener(this);
        this.pv.txtbus.addKeyListener(this);
        this.pv.txtcodigo.addKeyListener(this);
        this.pv.txtcosto.addKeyListener(this);
        this.pv.btnEntradas.addActionListener(this);
        this.pv.btnSalidas.addActionListener(this);
        this.pv.btnSalirEntSal.addActionListener(this);
        this.pv.btnGuardarEntSal.addActionListener(this);
        this.pv.btnMovimiento.addActionListener(this);
        this.pv.btnGuardarMovimiento.addActionListener(this);
        this.pv.txtCantidadEntSal.addKeyListener(this);
        this.pv.cmbCatBuscar.addItemListener(this);
        this.pv.btnSalirMovimiento.addActionListener(this);
    }

    public void iniciar() {
        pv.setTitle("Registro de Productos");
        ImageIcon originalIcon  = new ImageIcon(getClass().getResource("/img/FondoCopyCentro72ppp.png"));
        Image image = originalIcon.getImage();
        Image scaledImage = image.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        this.pv.setFrameIcon(resizedIcon);
        pv.txtid.setVisible(false);
        pv.txtcon.setVisible(false);
        pv.tbllis.getTableHeader().setReorderingAllowed(false);
        // pv.tbllis.getTableHeader().setResizingAllowed(false);
        pv.tblInventarios.getTableHeader().setReorderingAllowed(false);
        pv.tblInventarios.getTableHeader().setResizingAllowed(false);
        pv.txtTipoEntSal.setVisible(false);
        bloquear();
        CargarCatProBuscar();
        CargarProductos();
        
        // Este código se asegurará de que la tabla ordene por precio cuando se haga clic en el encabezado
        pv.tbllis.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Detectar en qué columna se hizo clic (en este caso columna 3 para precio)
                int column = pv.tbllis.columnAtPoint(evt.getPoint());
                if (column == 3) {  // Solo ordenar cuando se hace clic en la columna de precios
                    sorter.sort();  // Ordenar las filas
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == pv.btnSalirEntSal){
            cerrarModal(pv.dEntradasSalidas);
        }else if(e.getSource() == pv.btnSalirMovimiento){
            cerrarModal(pv.dMovimientos);
        }else if(e.getSource() == pv.btnGuardarEntSal){
            GuardarMovimiento();
        }else if(e.getSource() == pv.btnMovimiento){
            int fila = -1;
            fila = pv.tbllis.getSelectedRow();
            if(fila < 0){
                JOptionPane.showMessageDialog(pv, "Seleccione un producto");
                pv.txtbus.requestFocus();
            }else{
                int idProducto;
                idProducto = Integer.parseInt(pv.tbllis.getValueAt(fila, 0).toString());
                CargarInventario(idProducto);
                pv.txtCantidadMovimiento.setText("1");
                abrirModalMovimientos();
            }
        }else if(e.getSource() == pv.btnGuardarMovimiento){
            double cantidad;
            
            if( pv.txtCantidadMovimiento.getText().equals("") ){
                JOptionPane.showMessageDialog(pv, "Ingrese una cantidad.");
                pv.txtCantidadMovimiento.requestFocus();
                return;
            } else {
                try {
                    cantidad = Double.parseDouble(pv.txtCantidadMovimiento.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(pv, "Ingrese una cantidad válida.");
                    pv.txtCantidadMovimiento.requestFocus();
                    return;
                }
            }
            
            int id_almacen = Integer.parseInt(pv.tblInventarios.getValueAt(pv.tblInventarios.getSelectedRow(), 2).toString());

            AlmacenListM almacenSeleccionadoDestino = (AlmacenListM) pv.cmbAlmacenDestino.getSelectedItem();
            int id_almacen_destino = almacenSeleccionadoDestino.getId();
            
            if( id_almacen == id_almacen_destino ){
                JOptionPane.showMessageDialog(pv, "Seleccione un almacen de destino diferente al almacen de salida.");
                pv.cmbAlmacenDestino.requestFocus();
                return;
            }
            
            int filaSeleccionadaInv = -1;
            filaSeleccionadaInv = pv.tblInventarios.getSelectedRow();
            
            if( filaSeleccionadaInv < 0 ){
                JOptionPane.showMessageDialog(pv, "Seleccione un Almlacen de Salida.");
                return;
            }
            
            int guardar = 0;
            guardar = GuardarMovimientosAlmacen();
            if( guardar == 1 ){
                JOptionPane.showMessageDialog(pv, "Movimiento guardado.");
                CargarAlmacenMovimientos();
                int fila = pv.tbllis.getSelectedRow();
                int idProducto;
                idProducto = Integer.parseInt(pv.tbllis.getValueAt(fila, 0).toString());
                CargarInventario(idProducto);
                pv.txtCantidadMovimiento.setText("1");
            }else{
                JOptionPane.showMessageDialog(pv, "Ocurrio un error al guardar el movimiento.");
            }
            
            /*AlmacenListM almacenSeleccionadoSalida = (AlmacenListM) pv.cmbAlmacenSalida.getSelectedItem();
            int almacenSalida = almacenSeleccionadoSalida.getId();*/
            
            /*AlmacenListM almacenSeleccionadoDestino = (AlmacenListM) pv.cmbAlmacenDestino.getSelectedItem();
            int almacenDestino = almacenSeleccionadoDestino.getId();*/
            
            /*if( almacenSalida == almacenDestino ){
                JOptionPane.showMessageDialog(pv, "Selecciona lamaces de Salida y Destino diferentes.");
                return;
            }*/
            
            // GuardarMovimientoAlmacen(0, almacenDestino);
            // GuardarMovimientoAlmacen(1, almacenSalida);
        }else if(e.getSource() == pv.btnEntradas){
            int fila = -1;
            fila = pv.tbllis.getSelectedRow();
            if(fila < 0){
                JOptionPane.showMessageDialog(pv, "Seleccione un producto");
                pv.txtbus.requestFocus();
            }else{
                abrirModalEntradasSalidas(0);
            }
        }else if(e.getSource() == pv.btnSalidas){
            int fila = -1;
            fila = pv.tbllis.getSelectedRow();
            if(fila < 0){
                JOptionPane.showMessageDialog(pv, "Seleccione un producto");
                pv.txtbus.requestFocus();
            }else{
                abrirModalEntradasSalidas(1);
            }
        }else if(e.getSource() == pv.btnnue){
            desbloquear();
        }else if(e.getSource() == pv.btngua){
            if(pv.txtcodigo.getText().isEmpty()){
                JOptionPane.showMessageDialog(pv, "Campo Código Vacio");
                pv.txtcodigo.requestFocus();
            }else if(pv.txtnom.getText().isEmpty()){
                JOptionPane.showMessageDialog(pv, "Campo Nombre Vacio");
                pv.txtnom.requestFocus();
            }else if(pv.txtsa.getText().isEmpty()){
                JOptionPane.showMessageDialog(pv, "Campo Stock Actual Vacio");
                pv.txtsa.requestFocus();
            }else if(pv.txtsm.getText().isEmpty()){
                JOptionPane.showMessageDialog(pv, "Campo Stock Mínimo Vacio");
                pv.txtsm.requestFocus();
            }else if(pv.txtsx.getText().isEmpty()){
                JOptionPane.showMessageDialog(pv, "Campo Stock Máximo Vacio");
                pv.txtsx.requestFocus();
            }else if(pv.txtpre.getText().isEmpty()){
                JOptionPane.showMessageDialog(pv, "Campo Precio Vacio");
                pv.txtpre.requestFocus();
            }else if(Double.parseDouble(pv.txtpre.getText()) <= 0){
                JOptionPane.showMessageDialog(pv, "Introducir Precio Mayor a Cero");
                pv.txtpre.requestFocus();
            }/*else if(Double.parseDouble(pv.txtcosto.getText()) <= 0){
                JOptionPane.showMessageDialog(pv, "Introducir Costo Mayor a Cero");
                pv.txtcosto.requestFocus();
            }*/else if(pv.cmbcat.getSelectedIndex() <= 0){
                JOptionPane.showMessageDialog(pv, "Seleccione una Categoría");
                pv.cmbcat.requestFocus();
            }else{
                int existe = 0;
                existe = VerificarExistencia(pv.txtcodigo.getText(), pv.txtid.getText());
                if(existe == 1){
                    JOptionPane.showMessageDialog(pv, "El Código Ya Se Encuentra Registrado.");
                }else{
                    
                    // Valida costo anterior
                    Double costoActual = Double.parseDouble(pv.txtcosto.getText());
                    if(costoActual < costoAnterior){
                        Object[] opciones = {"Confirmar", "No"};

                        int respuesta = JOptionPane.showOptionDialog(
                            pv, 
                            "EL Costo Anterior es MAYOR al Costo Actual, ¿Desea Continuar?", 
                            "Confirmación", 
                            JOptionPane.DEFAULT_OPTION, 
                            JOptionPane.QUESTION_MESSAGE, 
                            null, 
                            opciones, 
                            opciones[0] // Establecer "Confirmar" como opción predeterminada
                        );

                        if (respuesta != 0) {
                            return;
                        }
                    }
                    
                    // Valida precio anterior
                    Double precioActual = Double.parseDouble(pv.txtpre.getText());
                    if(precioActual < precioAnterior){
                        Object[] opciones1 = {"Confirmar", "No"};

                        int respuesta1 = JOptionPane.showOptionDialog(
                            pv, 
                            "EL Precio Anterior es MAYOR al Precio Actual, ¿Desea Continuar?", 
                            "Confirmación", 
                            JOptionPane.DEFAULT_OPTION, 
                            JOptionPane.QUESTION_MESSAGE, 
                            null, 
                            opciones1, 
                            opciones1[0] // Establecer "Confirmar" como opción predeterminada
                        );

                        if (respuesta1 != 0) {
                            return;
                        }else{

                        }
                    }
                    
                    // Valida mayoreo anterior
                    Double mayoreoActual = Double.parseDouble(pv.txtmayoreo.getText());
                    if(mayoreoActual < mayoreoAnterior){
                        Object[] opciones2 = {"Confirmar", "No"};

                        int respuesta2 = JOptionPane.showOptionDialog(
                            pv, 
                            "EL Mayoreo Anterior es MAYOR al Mayoreo Actual, ¿Desea Continuar?", 
                            "Confirmación", 
                            JOptionPane.DEFAULT_OPTION, 
                            JOptionPane.QUESTION_MESSAGE, 
                            null, 
                            opciones2, 
                            opciones2[0] // Establecer "Confirmar" como opción predeterminada
                        );

                        if (respuesta2 != 0) {
                            return;
                        }else{

                        }
                    }
                    
                    // Valida menudeo anterior
                    Double menudeoActual = Double.parseDouble(pv.txtmenudeo.getText());
                    if(menudeoActual < menudeoAnterior){
                        Object[] opciones3 = {"Confirmar", "No"};

                        int respuesta3 = JOptionPane.showOptionDialog(
                            pv, 
                            "EL Menudeo Anterior es MAYOR al Menudeo Actual, ¿Desea Continuar?", 
                            "Confirmación", 
                            JOptionPane.DEFAULT_OPTION, 
                            JOptionPane.QUESTION_MESSAGE, 
                            null, 
                            opciones3, 
                            opciones3[0] // Establecer "Confirmar" como opción predeterminada
                        );

                        if (respuesta3 != 0) {
                            return;
                        }
                    }
                    
                    
                    int gua = 0;
                    gua = GuardarProductos();
                    if(gua == 0){
                        JOptionPane.showMessageDialog(pv, "El Código Ya Se Encuentra Registrado.");
                    }else{
                        if(pv.txtid.getText().isEmpty()){
                            CargarProductos();
                            desbloquear();
                            pv.txtcodigo.setText("");
                            pv.txtnom.setText("");
                            pv.txtsa.setText("0");
                            pv.txtsm.setText("5");
                            pv.txtsx.setText("50");
                            pv.txtpre.setText("0");
                            // pv.txtcosto.setText("0");
                            pv.txtmayoreo.setText("0");
                            pv.txtmenudeo.setText("0");
                            pv.txtcosto.setText("0");
                            pv.txtcantidadtipo.setText("1");
                            JOptionPane.showMessageDialog(pv, "Registro Guardado");
                        }else{
                            CargarProductos();
                            bloquear();
                            JOptionPane.showMessageDialog(pv, "Registro Actualizado");
                        }
                    }
                }
            }
        }else if(e.getSource() == pv.btncan){
            bloquear();
        }else if(e.getSource() == pv.btnedi){
            //int fila = 0;
            //fila_s = cpv.tbllis.getSelectedRow();
            fila_s = pv.tbllis.getSelectedRow();
            if(fila_s < 0){
                JOptionPane.showMessageDialog(pv, "Seleccione un Registro");
            }else{
                desbloquear();
                pv.txtid.setText(pv.tbllis.getValueAt(fila_s, 0).toString());
                //String[] part;
                //part = pv.tbllis.getValueAt(fila, 1).toString().split("-");
                //pv.txtcon.setText("-"+part[1]);
                pv.txtcodigo.setText(pv.tbllis.getValueAt(fila_s, 1).toString());
                pv.txtnom.setText(pv.tbllis.getValueAt(fila_s, 2).toString());
                pv.txtsa.setText(pv.tbllis.getValueAt(fila_s, 3).toString());
                pv.txtsm.setText(pv.tbllis.getValueAt(fila_s, 4).toString());
                pv.txtsx.setText(pv.tbllis.getValueAt(fila_s, 5).toString());
                pv.txtcosto.setText(pv.tbllis.getValueAt(fila_s, 6).toString());
                pv.txtpre.setText(pv.tbllis.getValueAt(fila_s, 7).toString());
                pv.txtmayoreo.setText(pv.tbllis.getValueAt(fila_s, 8).toString());
                pv.txtmenudeo.setText(pv.tbllis.getValueAt(fila_s, 9).toString());
                pv.cmbtipo.setSelectedItem(pv.tbllis.getValueAt(fila_s, 10).toString());
                pv.txtcantidadtipo.setText(pv.tbllis.getValueAt(fila_s, 11).toString());
                // pv.txtcosto.setText(pv.tbllis.getValueAt(fila_s, 7).toString());
                pv.cmbcat.setSelectedItem(pv.tbllis.getValueAt(fila_s, 12).toString());
                
                pv.cmbest.setSelectedItem(pv.tbllis.getValueAt(fila_s, 13).toString());
                pv.txtsa.setEnabled(false);
                
                costoAnterior = Double.parseDouble(pv.tbllis.getValueAt(fila_s, 6).toString());
                precioAnterior = Double.parseDouble(pv.tbllis.getValueAt(fila_s, 7).toString());
                mayoreoAnterior = Double.parseDouble(pv.tbllis.getValueAt(fila_s, 8).toString());
                menudeoAnterior = Double.parseDouble(pv.tbllis.getValueAt(fila_s, 9).toString());
                
            }
        }else if(e.getSource() == pv.btnsal){
            pv.dispose();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        /*if( e.getSource() == pv.txtpre ){
            char c=e.getKeyChar();
 
            if(Character.isLetter(c)) {
                pv.txtpre.getToolkit().beep();
                e.consume();
            }
        }*/
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if( e.getSource() == pv.txtCantidadEntSal ){
            if( e.getKeyCode() == KeyEvent.VK_ESCAPE ){
                cerrarModal(pv.dEntradasSalidas);
            }else if( e.getKeyCode() == KeyEvent.VK_ENTER ){
                GuardarMovimiento();
            }
        }else if( e.getSource() == pv.txtcosto ){
            if( e.getKeyCode() == KeyEvent.VK_ENTER ){
                CalcularPrecios();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getSource() == pv.txtbus){
            CargarProductosBuscar(pv.txtbus.getText());
        }else if(e.getSource() == pv.txtcodigo){
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                pv.txtnom.requestFocus();
            }
        }
    }
    
    public void CargarProductosBuscar(String buscar){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
         };
        
        CatProListM catProSeleccionado = (CatProListM) pv.cmbCatBuscar.getSelectedItem();
        int idSeleccionado = catProSeleccionado.getId();
        // System.out.println("ID: " + idSeleccionado);
        
        //System.out.println(""+ buscar);
        String nom = null;
        nom = buscar;
        pm.setId(-2);
        pm.setCod(null);
        pm.setNom(nom);
        pm.setSa(0);
        pm.setSm(0);
        pm.setSx(0);
        pm.setPre(0);
        pm.setCosto(0);
        pm.setCat(String.valueOf(idSeleccionado));
        pm.setEst(0);
        modelt = pm.CargarProductos();
        pv.tbllis.setModel(modelt);
        
        pv.tbllis.getColumnModel().getColumn(0).setMaxWidth(0);
        pv.tbllis.getColumnModel().getColumn(0).setMinWidth(0);
        pv.tbllis.getColumnModel().getColumn(0).setPreferredWidth(0);//ID
        pv.tbllis.getColumnModel().getColumn(2).setPreferredWidth(250);
        /*pv.tbllis.getColumnModel().getColumn(2).setMaxWidth(200);
        pv.tbllis.getColumnModel().getColumn(2).setMinWidth(200);
        pv.tbllis.getColumnModel().getColumn(2).setPreferredWidth(200);//NOM*/
        
        pv.tbllis.setDefaultRenderer(pv.tbllis.getColumnClass(0), colorearFilas);
        
        // Crear el TableRowSorter y asociarlo con el modelo de tabla
        // TableRowSorter<TableModel> sorter = new TableRowSorter<>(modelt);
        sorter = new TableRowSorter<>(modelt);
        pv.tbllis.setRowSorter(sorter);

        // Establecer un comparador para la columna de precios (columna 3)
        sorter.setComparator(3, (Object o1, Object o2) -> {
            // Asegurarse de que los valores sean Double
            Double val1 = (o1 instanceof Double) ? (Double) o1 : Double.valueOf(o1.toString());
            Double val2 = (o2 instanceof Double) ? (Double) o2 : Double.valueOf(o2.toString());
            
            // System.out.println("Comparando");
            
            return val1.compareTo(val2);
        });

        // Establecer un comparador para la columna de precios (columna 3)
        sorter.setComparator(6, (Object o1, Object o2) -> {
            // Asegurarse de que los valores sean Double
            Double val1 = (o1 instanceof Double) ? (Double) o1 : Double.valueOf(o1.toString());
            Double val2 = (o2 instanceof Double) ? (Double) o2 : Double.valueOf(o2.toString());
            
            // System.out.println("Comparando");
            
            return val1.compareTo(val2);
        });
        
    }
    
    public void bloquear(){
        pv.btnnue.setEnabled(true);
        pv.btngua.setEnabled(false);
        pv.btncan.setEnabled(false);
        pv.btnedi.setEnabled(true);
        pv.btnsal.setEnabled(true);
        pv.txtnom.setEnabled(false);
        pv.txtcodigo.setEnabled(false);
        pv.txtsa.setEnabled(false);
        pv.txtsm.setEnabled(false);
        pv.txtsx.setEnabled(false);
        pv.txtpre.setEnabled(false);
        pv.txtmayoreo.setEnabled(false);
        pv.txtmenudeo.setEnabled(false);
        pv.cmbtipo.setEnabled(false);
        pv.txtcantidadtipo.setEnabled(false);
        pv.txtcosto.setEnabled(false);
        // pv.cmbmedidas.setEnabled(false);
        pv.cmbcat.setEnabled(false);
        pv.cmbest.setEnabled(false);
        pv.txtbus.setEnabled(true);
        pv.txtcodigo.setText("");
        pv.txtnom.setText("");
        pv.txtsa.setText("0");
        pv.txtsm.setText("5");
        pv.txtsx.setText("50");
        pv.txtpre.setText("0");
        pv.txtmayoreo.setText("0");
        pv.txtmenudeo.setText("0");
        pv.txtcosto.setText("0");
        pv.cmbtipo.setSelectedIndex(0);
        pv.txtcantidadtipo.setText("1");
        // pv.cmbmedidas.removeAllItems();
        pv.cmbcat.removeAllItems();
        pv.txtid.setText("");
        pv.txtcon.setText("");
        pv.txtbus.setText("");
        pv.txtbus.requestFocus();
    }
    
    public void desbloquear(){
        pv.btnnue.setEnabled(false);
        pv.btngua.setEnabled(true);
        pv.btncan.setEnabled(true);
        pv.btnedi.setEnabled(false);
        pv.btnsal.setEnabled(false);
        pv.txtnom.setEnabled(true);
        pv.txtcodigo.setEnabled(true);
        // pv.txtsa.setEnabled(true);
        pv.txtsm.setEnabled(true);
        pv.txtsx.setEnabled(true);
        pv.txtpre.setEnabled(true);
        pv.txtmayoreo.setEnabled(true);
        pv.txtmenudeo.setEnabled(true);
        pv.txtcosto.setEnabled(true);
        pv.cmbtipo.setSelectedIndex(0);
        pv.cmbtipo.setEnabled(true);
        pv.txtcantidadtipo.setEnabled(true);
        // pv.cmbmedidas.setEnabled(true);
        pv.cmbcat.setEnabled(true);
        pv.cmbest.setEnabled(true);
        pv.txtbus.setEnabled(false);
        pv.txtbus.setText("");
        BuscarCategorias();
        // BuscarMedidas();
        pv.txtcodigo.requestFocus();
    }
    
    public void BuscarCategorias(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        pv.cmbcat.removeAllItems();
        pv.cmbcat.addItem("Seleccione");
        cpm.setId(-2);
        //cpm.setCon(null);
        //cpm.setNom(null);
        //cpm.setEst(0);
        modelt = cpm.CargarCatPro();
        for (int i = 0; i < modelt.getRowCount(); i++) {
            pv.cmbcat.addItem(modelt.getValueAt(i, 1).toString() + " '" + modelt.getValueAt(i, 0).toString());
        }
    }
    
    public void BuscarMedidas(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        // pv.cmbmedidas.removeAllItems();
        // pv.cmbmedidas.addItem("Seleccione");
        mm.setId(-1);
        //cpm.setCon(null);
        //cpm.setNom(null);
        //cpm.setEst(0);
        modelt = mm.CargarMedidas();
        for (int i = 0; i < modelt.getRowCount(); i++) {
            // pv.cmbmedidas.addItem(modelt.getValueAt(i, 1).toString() + " '" + modelt.getValueAt(i, 0).toString());
        }
    }

    public int GuardarProductos(){
        int id = 0;
        String cod = null;
        String nom = null;
        double sa = 0;
        int sm = 0;
        int sx = 0;
        double pre = 0;
        double costo = 0;
        String cat = null;
        String col = null;
        String tam = null;
        String hor = null;
        int est = 0;
        int medida = 0;
        double mayoreo = 0;
        double menudeo = 0;
        String tipo = "";
        int cantidadTipo = 0;
        
        if(!pv.txtid.getText().isEmpty()){
            id = Integer.parseInt(pv.txtid.getText());
        }
        
        cod = pv.txtcodigo.getText();
        nom = pv.txtnom.getText().toUpperCase();
        sa = Double.parseDouble(pv.txtsa.getText());
        sm = Integer.parseInt(pv.txtsm.getText());
        sx = Integer.parseInt(pv.txtsx.getText());
        pre = Double.parseDouble(pv.txtpre.getText());
        // costo = Double.parseDouble(pv.txtcosto.getText());
        String[] p1;
        p1 = pv.cmbcat.getSelectedItem().toString().split("'");
        cat = p1[1];
        if(pv.cmbest.getSelectedIndex() == 0){
            est = 1;
        }else{
            est = 0;
        }
        
        // String[] p2;
        // p2 = pv.cmbmedidas.getSelectedItem().toString().split("'");
        medida = 0;// Integer.parseInt(p2[1]);
        
        // gr_ml = Integer.parseInt(pv.txtgr_ml.getText());
        
        mayoreo = Double.parseDouble(pv.txtmayoreo.getText().toString());
        menudeo = Double.parseDouble(pv.txtmenudeo.getText().toString());
        tipo = pv.cmbtipo.getSelectedItem().toString();
        cantidadTipo = Integer.parseInt(pv.txtcantidadtipo.getText().toString());
        
        costo = Double.parseDouble(pv.txtcosto.getText());
        
        pm.setId(id);
        pm.setCod(cod);
        pm.setNom(nom);
        pm.setSa(sa);
        pm.setSm(sm);
        pm.setSx(sx);
        pm.setPre(pre);
        pm.setCosto(costo);
        pm.setCat(cat);
        pm.setEst(est);
        pm.setGr_ml(medida);
        pm.setMayoreo(mayoreo);
        pm.setMenudeo(menudeo);
        pm.setTipo(tipo);
        pm.setCantidadTipo(cantidadTipo);
        
        if(pm.GuardarProductos()){
            return 1;
        }
        return 0;
    }
    
    public void CargarProductos_old(){
        /*DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        pm.setId(-1);
        pm.setCod(null);
        pm.setNom(null);
        pm.setSa(0);
        pm.setSm(0);
        pm.setSx(0);
        pm.setPre(0);
        pm.setCosto(0);
        pm.setCat(null);
        pm.setEst(0);
        pm.setGr_ml(0);
        modelt = pm.CargarProductos();
        pv.tbllis.setModel(modelt);
        
        pv.tbllis.getColumnModel().getColumn(0).setMaxWidth(0);
        pv.tbllis.getColumnModel().getColumn(0).setMinWidth(0);
        pv.tbllis.getColumnModel().getColumn(0).setPreferredWidth(0);//ID
        pv.tbllis.getColumnModel().getColumn(2).setMaxWidth(200);
        pv.tbllis.getColumnModel().getColumn(2).setMinWidth(200);
        pv.tbllis.getColumnModel().getColumn(2).setPreferredWidth(200);//NOM
        centrar.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
        pv.tbllis.getColumnModel().getColumn(2).setCellRenderer(centrar);
        pv.tbllis.getColumnModel().getColumn(3).setCellRenderer(centrar);
        pv.tbllis.getColumnModel().getColumn(4).setCellRenderer(centrar);
        pv.tbllis.getColumnModel().getColumn(5).setCellRenderer(centrar);*/
        
    }
    
    public void CargarProductos() {
        DefaultTableModel modelt = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Evita la edición de las celdas
            }
        };
        
        CatProListM catProSeleccionado = (CatProListM) pv.cmbCatBuscar.getSelectedItem();
        int idSeleccionado = catProSeleccionado.getId();

        // Establecer valores predeterminados
        pm.setId(-1);
        pm.setCod(null);
        pm.setNom(null);
        pm.setSa(0);
        pm.setSm(0);
        pm.setSx(0);
        pm.setPre(0);
        pm.setCosto(0);
        pm.setCat(String.valueOf(idSeleccionado));
        pm.setEst(0);
        pm.setGr_ml(0);

        // Cargar productos en el modelo
        modelt = pm.CargarProductos();
        pv.tbllis.setModel(modelt);

        // Ajustar el tamaño de las columnas
        pv.tbllis.getColumnModel().getColumn(0).setMaxWidth(0);
        pv.tbllis.getColumnModel().getColumn(0).setMinWidth(0);
        pv.tbllis.getColumnModel().getColumn(0).setPreferredWidth(0); // Columna ID
        pv.tbllis.getColumnModel().getColumn(2).setPreferredWidth(250);
        /*pv.tbllis.getColumnModel().getColumn(2).setMaxWidth(200);
        pv.tbllis.getColumnModel().getColumn(2).setMinWidth(200);
        pv.tbllis.getColumnModel().getColumn(2).setPreferredWidth(200); // Columna NOM*/
        
        pv.tbllis.setDefaultRenderer(pv.tbllis.getColumnClass(0), colorearFilas);
        
        // Crear el TableRowSorter y asociarlo con el modelo de tabla
        // TableRowSorter<TableModel> sorter = new TableRowSorter<>(modelt);
        sorter = new TableRowSorter<>(modelt);
        pv.tbllis.setRowSorter(sorter);

        // Establecer un comparador para la columna de precios (columna 3)
        sorter.setComparator(3, (Object o1, Object o2) -> {
            // Asegurarse de que los valores sean Double
            Double val1 = (o1 instanceof Double) ? (Double) o1 : Double.valueOf(o1.toString());
            Double val2 = (o2 instanceof Double) ? (Double) o2 : Double.valueOf(o2.toString());
            
            // System.out.println("Comparando");
            
            return val1.compareTo(val2);
        });

        // Establecer un comparador para la columna de precios (columna 3)
        sorter.setComparator(6, (Object o1, Object o2) -> {
            // Asegurarse de que los valores sean Double
            Double val1 = (o1 instanceof Double) ? (Double) o1 : Double.valueOf(o1.toString());
            Double val2 = (o2 instanceof Double) ? (Double) o2 : Double.valueOf(o2.toString());
            
            // System.out.println("Comparando");
            
            return val1.compareTo(val2);
        });
        
    }

    public int VerificarExistencia(String codigo, String id){
        int filas = 0;
        filas = pv.tbllis.getRowCount();
        
        for (int i = 0; i < filas; i++) {
            if(!id.equals("")){
                if(fila_s == i){
                    
                }else{
                    if(pv.tbllis.getValueAt(i, 1).toString().equals(codigo)){
                        return 1;
                    }
                }
            }else{
                if(pv.tbllis.getValueAt(i, 1).toString().equals(codigo)){
                    return 1;
                }
            }
        }
        
        return 0;
    }
    
    public void abrirModalEntradasSalidas(int tipo) {
        pv.dEntradasSalidas.setSize(350, 200);
        pv.dEntradasSalidas.setModal(true);
        pv.dEntradasSalidas.setLocationRelativeTo(pv);
        pv.dEntradasSalidas.setResizable(false);
        
        if( tipo == 0 ){
            pv.dEntradasSalidas.setTitle("Entrada de Productos");
            pv.lblTituloEntSal.setText("Entrada de Productos");
        }else if( tipo == 1 ){
            pv.dEntradasSalidas.setTitle("Salida de Productos");
            pv.lblTituloEntSal.setText("Salida de Productos");
        }
        
        CargarAlmacen();
        pv.txtCantidadEntSal.setText("1");
        pv.txtTipoEntSal.setText(""+tipo);
        pv.dEntradasSalidas.setVisible(true);
        // pv.txtCantidadEntSal.requestFocus();
    }
    
    public void abrirModalMovimientos() {
        pv.dMovimientos.setSize(410, 400);
        pv.dMovimientos.setModal(true);
        pv.dMovimientos.setLocationRelativeTo(pv);
        pv.dMovimientos.setResizable(false);
        
        CargarAlmacenMovimientos();
        pv.dMovimientos.setVisible(true);
        // pv.txtCantidadEntSal.requestFocus();
    }
    
    public void CargarAlmacen(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        
        am.setId(-2);
        
        modelt = am.CargarAlmacen();
        
        // Iterar sobre las filas del DefaultTableModel y agregar los usuarios al JComboBox
        for (int i = 0; i < modelt.getRowCount(); i++) {
            // Asumimos que la columna 0 es el 'id' y la columna 1 es el 'nombre'
            int id = (int) modelt.getValueAt(i, 0); // Obtén el 'id'
            String nombre = (String) modelt.getValueAt(i, 1); // Obtén el 'nombre'

            // Crear un nuevo objeto Usuario
            AlmacenListM almacen = new AlmacenListM(id, nombre);

            // Agregar el usuario al JComboBox
            pv.cmbAlmacenEntSal.addItem(almacen);
        }
        
    }
    
    public void CargarAlmacenMovimientos(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        pv.cmbAlmacenDestino.removeAllItems();
        
        am.setId(-2);
        
        modelt = am.CargarAlmacen();
        
        // Iterar sobre las filas del DefaultTableModel y agregar los usuarios al JComboBox
        for (int i = 0; i < modelt.getRowCount(); i++) {
            // Asumimos que la columna 0 es el 'id' y la columna 1 es el 'nombre'
            int id = (int) modelt.getValueAt(i, 0); // Obtén el 'id'
            String nombre = (String) modelt.getValueAt(i, 1); // Obtén el 'nombre'

            // Crear un nuevo objeto Usuario
            AlmacenListM almacen = new AlmacenListM(id, nombre);

            // Agregar el usuario al JComboBox
            // pv.cmbAlmacenSalida.addItem(almacen);
            pv.cmbAlmacenDestino.addItem(almacen);
        }
        
    }
    
    public void GuardarMovimiento(){
        String codigo = null;
        double cantidad = 0;
        
        codigo = pv.tbllis.getValueAt(pv.tbllis.getSelectedRow(), 1).toString();
        
        if(pv.txtCantidadEntSal.getText().toString().equals("")){
            JOptionPane.showMessageDialog(null, "No se ingresó ninguna cantidad.", "Cancelado", JOptionPane.WARNING_MESSAGE);
            pv.txtCantidadEntSal.requestFocus();
            return;
        }
        
        boolean validarDouble = false;
        validarDouble = esDouble(pv.txtCantidadEntSal.getText());
        
        if(validarDouble){
            cantidad = Double.parseDouble(pv.txtCantidadEntSal.getText());
        }else{
            JOptionPane.showMessageDialog(null, "Por favor ingresa un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            pv.txtCantidadEntSal.requestFocus();
            return;
        }

        int guardar = GuardarEntradaSalida(codigo, cantidad);
        if( guardar == 1 ){
            JOptionPane.showMessageDialog(pv, "Movimiento Guardado.");
            cerrarModal(pv.dEntradasSalidas);
            pv.txtCantidadEntSal.setText("");
            
            CargarProductosBuscar(pv.txtbus.getText());
            pv.txtCantidadEntSal.requestFocus();
            
        }else{
            JOptionPane.showMessageDialog(pv, "Ocurrio un error al guardar el movimiento.");
        }
    }
    
    public void GuardarMovimientoAlmacen(int tipo, int almacen){
        String codigo = null;
        double cantidad = 0;
        
        codigo = pv.tbllis.getValueAt(pv.tbllis.getSelectedRow(), 1).toString();
        
        if(pv.txtCantidadMovimiento.getText().toString().equals("")){
            JOptionPane.showMessageDialog(null, "No se ingresó ninguna cantidad.", "Cancelado", JOptionPane.WARNING_MESSAGE);
            pv.txtCantidadMovimiento.requestFocus();
            return;
        }
        
        boolean validarDouble = false;
        validarDouble = esDouble(pv.txtCantidadMovimiento.getText());
        
        if(validarDouble){
            cantidad = Double.parseDouble(pv.txtCantidadMovimiento.getText());
        }else{
            JOptionPane.showMessageDialog(null, "Por favor ingresa un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            pv.txtCantidadMovimiento.requestFocus();
            return;
        }

        int guardar = GuardarEntradaSalidaMovimiento(codigo, cantidad, tipo, almacen);
        if( guardar == 1 ){
            if(tipo == 1){
                JOptionPane.showMessageDialog(pv, "Movimiento Guardado.");
                cerrarModal(pv.dMovimientos);
                pv.txtCantidadMovimiento.setText("");

                CargarProductosBuscar(pv.txtbus.getText());
                pv.txtCantidadMovimiento.requestFocus();
            }
        }else{
            JOptionPane.showMessageDialog(pv, "Ocurrio un error al guardar el movimiento.");
        }
    }
    
    // Método para validar si un String es un número de tipo double
    public static boolean esDouble(String str) {
        try {
            Double.parseDouble(str);  // Intentar convertir el String a un double
            return true;  // Si la conversión fue exitosa
        } catch (NumberFormatException e) {
            return false;  // Si ocurre una excepción, no es un número válido
        }
    }
    
    public int GuardarEntradaSalida(String codigo, double cantidad){
        int tipo = 0;
        
        tipo = Integer.parseInt(pv.txtTipoEntSal.getText());
        
        AlmacenListM almacenSeleccionado = (AlmacenListM) pv.cmbAlmacenEntSal.getSelectedItem();
        int idSeleccionado = almacenSeleccionado.getId();
        
        espem.setParam(1);
        espem.setTipo(tipo);
        espem.setAlmacen(idSeleccionado);
        espem.setCodigo(codigo);
        espem.setCantidad(cantidad);
        
        if(espem.GuardarESProducto()){
            return 1;
        }
        return 0;
    }
    
    public int GuardarEntradaSalidaMovimiento(String codigo, double cantidad, int tipo, int almacen){
        
        espem.setParam(1);
        espem.setTipo(tipo);
        espem.setAlmacen(almacen);
        espem.setCodigo(codigo);
        espem.setCantidad(cantidad);
        
        if(espem.GuardarESProducto()){
            return 1;
        }
        return 0;
    }
    
    public void cerrarModal(JDialog dialog){
        dialog.dispose();
        pv.txtbus.requestFocus();
    }
    
    public void CargarCatProBuscar(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        
        cpm.setId(-2);
        
        modelt = cpm.CargarCatPro();
        
        // Crear un nuevo objeto Usuario
        CatProListM catProList = new CatProListM(0, "Seleccione una Categoría");
        pv.cmbCatBuscar.addItem(catProList);
        
        // Iterar sobre las filas del DefaultTableModel y agregar los usuarios al JComboBox
        for (int i = 0; i < modelt.getRowCount(); i++) {
            // Asumimos que la columna 0 es el 'id' y la columna 1 es el 'nombre'
            int id = (int) modelt.getValueAt(i, 0); // Obtén el 'id'
            String nombre = (String) modelt.getValueAt(i, 1); // Obtén el 'nombre'

            // Crear un nuevo objeto Usuario
            catProList = new CatProListM(id, nombre);

            // Agregar el usuario al JComboBox
            pv.cmbCatBuscar.addItem(catProList);
        }
        
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if( e.getSource() == pv.cmbCatBuscar ){
            if( e.getStateChange() == ItemEvent.SELECTED ){
                CargarProductosBuscar(pv.txtbus.getText());
            }
        }
        
        /*if( e.getStateChange() == ItemEvent.SELECTED ){
            if( e.getSource() == pv.cmbCatBuscar ){
                System.out.println("Se cambió");
            }
        }*/
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
        id_producto = Integer.parseInt(pv.tbllis.getValueAt(pv.tbllis.getSelectedRow(), 0).toString());
        
        id_almacen = Integer.parseInt(pv.tblInventarios.getValueAt(pv.tblInventarios.getSelectedRow(), 2).toString());
        
        id_inventario = Integer.parseInt(pv.tblInventarios.getValueAt(pv.tblInventarios.getSelectedRow(), 0).toString());
        
        AlmacenListM almacenSeleccionadoDestino = (AlmacenListM) pv.cmbAlmacenDestino.getSelectedItem();
        id_almacen_destino = almacenSeleccionadoDestino.getId();
        // id_almacen_destino = Integer.parseInt(partAlmacen[1]);
        cantidad = Double.parseDouble(pv.txtCantidadMovimiento.getText());
        cantidad_inventario = Double.parseDouble(pv.tblInventarios.getValueAt(pv.tblInventarios.getSelectedRow(), 4).toString());
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
        pv.tblInventarios.setModel(modelt);
        
        pv.tblInventarios.getColumnModel().getColumn(0).setMaxWidth(0);
        pv.tblInventarios.getColumnModel().getColumn(0).setMinWidth(0);
        pv.tblInventarios.getColumnModel().getColumn(0).setPreferredWidth(0);//ID
        pv.tblInventarios.getColumnModel().getColumn(1).setMaxWidth(0);
        pv.tblInventarios.getColumnModel().getColumn(1).setMinWidth(0);
        pv.tblInventarios.getColumnModel().getColumn(1).setPreferredWidth(0);//ID
        pv.tblInventarios.getColumnModel().getColumn(2).setMaxWidth(0);
        pv.tblInventarios.getColumnModel().getColumn(2).setMinWidth(0);
        pv.tblInventarios.getColumnModel().getColumn(2).setPreferredWidth(0);//ID
        centrar.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
        pv.tblInventarios.getColumnModel().getColumn(3).setCellRenderer(centrar);
        pv.tblInventarios.getColumnModel().getColumn(4).setCellRenderer(centrar);
    }
    
    public void CalcularPrecios(){
        int indexCategoria = pv.cmbcat.getSelectedIndex();

        if( indexCategoria == 0 ){
            JOptionPane.showMessageDialog(pv, "Seleccione una categoría.");
            return;
        }

        int categoria;
        String[] p1;
        p1 = pv.cmbcat.getSelectedItem().toString().split("'");
        categoria = Integer.parseInt(p1[1]);

        if (!validarCosto(pv.txtcosto)) return;

        // Obtener el costo con seguridad
        double costo = Double.parseDouble(pv.txtcosto.getText().trim());

        // Obtener los porcentajes de la base de datos (precio, mayoreo, menudeo)
        int[] valores = ppm.obtenerPreciosPorCategoria(categoria);

        int porcPrecio = valores[0];
        int porcMayoreo = valores[1];
        int porcMenudeo = valores[2];

        // Aplicar cálculo, validación de porcentaje > 0 y regla de redondeo
        double precioFinal = calcularPrecioConPorcentaje(costo, porcPrecio);
        if (precioFinal == -1) return; // Se detiene si el porcentaje es <= 0

        double mayoreoFinal = calcularPrecioConPorcentaje(costo, porcMayoreo);
        if (mayoreoFinal == -1) return;

        double menudeoFinal = calcularPrecioConPorcentaje(costo, porcMenudeo);
        if (menudeoFinal == -1) return;

        // --- AQUÍ YA PUEDES MOSTRAR LOS RESULTADOS EN TUS CAMPOS O ETIQUETAS ---
        // Ejemplo:
        pv.txtpre.setText(String.valueOf(precioFinal));
        pv.txtmayoreo.setText(String.valueOf(mayoreoFinal));
        pv.txtmenudeo.setText(String.valueOf(menudeoFinal));

        System.out.println("Precio final: " + precioFinal);
        System.out.println("Mayoreo final: " + mayoreoFinal);
        System.out.println("Menudeo final: " + menudeoFinal);
    }
    
    private boolean validarCosto(javax.swing.JTextField campo) {
        String texto = campo.getText().trim();

        // 1. Verificar si está vacío
        if (texto.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(pv, 
                "El campo Costo no puede estar vacío.", 
                "Error de validación", javax.swing.JOptionPane.ERROR_MESSAGE);
            campo.requestFocus();
            return false;
        }

        try {
            // 2. Intentar convertir a número (usamos double o float por si el costo maneja decimales, ej: 15.50)
            double valor = Double.parseDouble(texto);

            // 3. Verificar que sea mayor a cero
            if (valor <= 0) {
                javax.swing.JOptionPane.showMessageDialog(pv, 
                    "El campo Costo debe ser mayor a cero.", 
                    "Error de validación", javax.swing.JOptionPane.ERROR_MESSAGE);
                campo.requestFocus();
                return false;
            }

        } catch (NumberFormatException e) {
            // Si el usuario escribe letras o símbolos inválidos
            javax.swing.JOptionPane.showMessageDialog(pv, 
                "El campo Costo debe contener un número válido.", 
                "Error de validación", javax.swing.JOptionPane.ERROR_MESSAGE);
            campo.requestFocus();
            return false;
        }

        return true; // ¡Pasó todas las validaciones!
    }
    
    private double calcularPrecioConPorcentaje(double costo, double porcentaje) {
        // 1. Validar que el porcentaje sea mayor a cero
        if (porcentaje <= 0) {
            javax.swing.JOptionPane.showMessageDialog(pv, 
                "El porcentaje debe ser mayor a cero.", 
                "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return -1; // Usamos -1 para indicar que hubo un error y detener el proceso
        }

        // 2. Aplicar el aumento de porcentaje al costo (ej. Costo 100 + 15% = 115.0)
        double resultadoConAumento = costo + (costo * (porcentaje / 100.0));

        // 3. Separar la parte entera y la parte decimal
        int parteEntera = (int) resultadoConAumento;
        double parteDecimal = resultadoConAumento - parteEntera;

        // 4. Aplicar tu regla de redondeo personalizada
        // Si la parte decimal es menor o igual a 0.39, baja al entero. Si es >= 0.40, sube.
        double precioFinal;
        if (parteDecimal <= 0.39) {
            precioFinal = parteEntera; // Baja al entero (ej. 30.39 -> 30)
        } else {
            precioFinal = parteEntera + 1; // Sube al siguiente entero (ej. 30.40 -> 31)
        }

        return precioFinal;
    }
    
}
