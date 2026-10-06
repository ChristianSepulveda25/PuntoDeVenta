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
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Christian Sepulveda
 */
public class EquivalenciaC implements ActionListener, KeyListener {
    private EquivalenciaV eqv;
    private EquivalenciaM eqm;
    private final CatProM cpm = new CatProM();
    private final MedidaM mdm = new MedidaM();
    private final InventarioM im = new InventarioM();
    private final ProductosM pm = new ProductosM();
    DefaultTableCellRenderer centrar = new DefaultTableCellRenderer();
    
    public EquivalenciaC(EquivalenciaM eqm, EquivalenciaV eqv) {
        this.eqv = eqv;
        this.eqm = eqm;
        this.eqv.btnguardar.addActionListener(this);
        this.eqv.btnsalir.addActionListener(this);
        this.eqv.cmbcat.addActionListener(this);
        this.eqv.txtBuscarPaquetes.addKeyListener(this);
        this.eqv.txtBuscarProductos.addKeyListener(this);
        this.eqv.btnAplicar.addActionListener(this);
        this.eqv.btnEditarCantidad.addActionListener(this);
    }

    public void iniciar() {
        eqv.setTitle("Registro de Equivalencias");
        ImageIcon originalIcon  = new ImageIcon(getClass().getResource("/img/FondoCopyCentro72ppp.png"));
        Image image = originalIcon.getImage();
        Image scaledImage = image.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        this.eqv.setFrameIcon(resizedIcon);
        BuscarCategorias();
        BuscarPaquetes();
        BuscarProductos();
        CargarEquivalencias();
        /*eqv.tbllis.getTableHeader().setReorderingAllowed(false) ;
        eqv.tbllis.getTableHeader().setResizingAllowed(false);
        eqv.txtid.setVisible(false);
        CargarAlmacen();
        av.txtnom.setEnabled(false);
        av.cmbest.setEnabled(false);
        av.btngua.setEnabled(false);
        av.btncan.setEnabled(false);
        av.btnnue.requestFocus();*/
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if( e.getSource() == eqv.btnAplicar ){
            int filaEquivalencia = -1;
            filaEquivalencia = eqv.tbllisEquivalencias.getSelectedRow();
            if( filaEquivalencia < 0 ){
                JOptionPane.showMessageDialog(eqv, "Seleccione una Equivalencia");
                // eqv.txtBuscarProductos.requestFocus();
                return;
            }
            
            String paquete;
            String producto;
            double cantidad;
            int idPaquete;
            int idProducto;
            paquete = eqv.tbllisEquivalencias.getValueAt(filaEquivalencia, 1).toString();
            producto = eqv.tbllisEquivalencias.getValueAt(filaEquivalencia, 2).toString();
            cantidad = Double.parseDouble(eqv.tbllisEquivalencias.getValueAt(filaEquivalencia, 3).toString());
            idPaquete = Integer.parseInt(eqv.tbllisEquivalencias.getValueAt(filaEquivalencia, 4).toString());
            idProducto = Integer.parseInt(eqv.tbllisEquivalencias.getValueAt(filaEquivalencia, 5).toString());
            
            // Crear un arreglo de botones personalizados
            Object[] opciones = {"Aplicar", "No"};

            // Mostrar el mensaje de confirmación con los botones personalizados
            int respuesta = JOptionPane.showOptionDialog(
                eqv, 
                "¿Aplicar Equivalencia? \nEste proceso le restará la cantidada de 1 (uno) al paquete "+paquete+", \ny le sumará la cantidad de "+cantidad+" al producto "+producto+".", 
                "Aplicar", 
                JOptionPane.DEFAULT_OPTION, 
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                opciones, 
                opciones[0] // Establecer "Confirmar" como opción predeterminada
            );

            // Procesar la respuesta
            if (respuesta == 0) {  // El índice de "Confirmar" es 0
                
                int alpicar = AplicarEquivalencia(idPaquete, idProducto, cantidad);
                if( alpicar == 1 ){
                    JOptionPane.showMessageDialog(eqv, "Equivalencia aplicada correctamente.");
                }else{
                    JOptionPane.showMessageDialog(eqv, "Ocurrio un error al aplicar la equivalencia.");
                }
            }
            
        }else if( e.getSource() == eqv.btnEditarCantidad ){
            int filaEquivalencia = -1;
            filaEquivalencia = eqv.tbllisEquivalencias.getSelectedRow();
            if( filaEquivalencia < 0 ){
                JOptionPane.showMessageDialog(eqv, "Seleccione una Equivalencia");
                // eqv.txtBuscarProductos.requestFocus();
                return;
            }
            
            String input = JOptionPane.showInputDialog(null, "Por favor, ingresa la nueva cantidad:", "Equivalencia de productos", JOptionPane.PLAIN_MESSAGE);

            // Verificar si el usuario no canceló el cuadro de diálogo
            if (input != null && !input.trim().isEmpty()) {
                try {
                    // Convertir el valor ingresado a un número entero
                    double cantidad;
                    cantidad = Integer.parseInt(input);
                    
                    int idEquivalencia;
                    
                    idEquivalencia = Integer.parseInt(eqv.tbllisEquivalencias.getValueAt(filaEquivalencia, 0).toString());

                    int guardar = ActualizarEquivalencias(idEquivalencia, cantidad);
                    if( guardar == 1 ){
                        JOptionPane.showMessageDialog(eqv, "Equivalencia Guardada.");
                        CargarEquivalencias();
                    }else{
                        JOptionPane.showMessageDialog(eqv, "Ocurrio un error al guardar ña equivalencia.");
                    }

                    // Mostrar la cantidad ingresada
                    // JOptionPane.showMessageDialog(null, "La cantidad ingresada es: " + cantidad, "Cantidad", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    // Si la entrada no es un número válido, mostrar un mensaje de error
                    JOptionPane.showMessageDialog(null, "Por favor ingresa un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Si el usuario canceló o no ingresó nada
                JOptionPane.showMessageDialog(null, "No se ingresó ninguna cantidad.", "Cancelado", JOptionPane.WARNING_MESSAGE);
            }
        }else if(e.getSource() == eqv.btnguardar){
            
            int filaPaquete = -1;
            filaPaquete = eqv.tbllisPaquetes.getSelectedRow();
            if( filaPaquete < 0 ){
                JOptionPane.showMessageDialog(eqv, "Seleccione un Paquete");
                eqv.txtBuscarPaquetes.requestFocus();
                return;
            }
            
            int filaProducto = -1;
            filaProducto = eqv.tbllisProductos.getSelectedRow();
            if( filaProducto < 0 ){
                JOptionPane.showMessageDialog(eqv, "Seleccione un Producto");
                eqv.txtBuscarProductos.requestFocus();
                return;
            }
            
            String input = JOptionPane.showInputDialog(null, "Por favor, ingresa la cantidad:", "Equivalencia de productos", JOptionPane.PLAIN_MESSAGE);

            // Verificar si el usuario no canceló el cuadro de diálogo
            if (input != null && !input.trim().isEmpty()) {
                try {
                    // Convertir el valor ingresado a un número entero
                    double cantidad;
                    cantidad = Integer.parseInt(input);
                    
                    int idProdPadre;
                    int idProdHijo;
                    
                    idProdPadre = Integer.parseInt(eqv.tbllisPaquetes.getValueAt(filaPaquete, 0).toString());
                    idProdHijo = Integer.parseInt(eqv.tbllisProductos.getValueAt(filaProducto, 0).toString());

                    int guardar = GuardarEquivalencias(idProdPadre, idProdHijo, cantidad);
                    if( guardar == 1 ){
                        JOptionPane.showMessageDialog(eqv, "Equivalencia Guardada.");
                        CargarEquivalencias();
                    }else{
                        JOptionPane.showMessageDialog(eqv, "Ocurrio un error al guardar ña equivalencia.");
                    }

                    // Mostrar la cantidad ingresada
                    // JOptionPane.showMessageDialog(null, "La cantidad ingresada es: " + cantidad, "Cantidad", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    // Si la entrada no es un número válido, mostrar un mensaje de error
                    JOptionPane.showMessageDialog(null, "Por favor ingresa un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Si el usuario canceló o no ingresó nada
                JOptionPane.showMessageDialog(null, "No se ingresó ninguna cantidad.", "Cancelado", JOptionPane.WARNING_MESSAGE);
            }
            
        }else if(e.getSource() == eqv.btnsalir){
            eqv.dispose();
        }else if(e.getSource() == eqv.cmbcat){
            BuscarProductos();
            /*int filaMedida = eqv.cmbcat.getSelectedIndex();
            if( filaMedida == 0){
                
            }else{
                JOptionPane.showMessageDialog(eqv, "La fila es " + filaMedida);
                String categoria = (String) eqv.cmbcat.getSelectedItem();
                String[] partCat;
                partCat = categoria.split("'");
                String cod = partCat[1];
                BuscarProductosCategoria(cod);
            }*/
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
        if( e.getSource() == eqv.txtBuscarPaquetes ){
            BuscarPaquetes();
        }else if( e.getSource() == eqv.txtBuscarProductos ){
            // System.out.println("Teclea buscar productos");
            BuscarProductos();
        }
    }
    
    public void BuscarCategorias(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        
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
            eqv.cmbcat.addItem(catProList);
        }
    }
    
    public void BuscarPaquetes(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        String codigo;
        codigo = eqv.txtBuscarPaquetes.getText();
        
        pm.setId(-12);
        pm.setCod(null);
        pm.setNom(codigo);
        pm.setSa(0);
        pm.setSm(0);
        pm.setSx(0);
        pm.setPre(0);
        pm.setCat(null);
        pm.setEst(0);
        modelt = pm.CargarProductos();
        eqv.tbllisPaquetes.setModel(modelt);
        
        eqv.tbllisPaquetes.getColumnModel().getColumn(0).setMaxWidth(0);
        eqv.tbllisPaquetes.getColumnModel().getColumn(0).setMinWidth(0);
        eqv.tbllisPaquetes.getColumnModel().getColumn(0).setPreferredWidth(0);//ID
        eqv.tbllisPaquetes.getColumnModel().getColumn(2).setMaxWidth(200);
        eqv.tbllisPaquetes.getColumnModel().getColumn(2).setMinWidth(200);
        eqv.tbllisPaquetes.getColumnModel().getColumn(2).setPreferredWidth(200);//NOM
        eqv.tbllisPaquetes.getColumnModel().getColumn(3).setMaxWidth(0);
        eqv.tbllisPaquetes.getColumnModel().getColumn(3).setMinWidth(0);
        eqv.tbllisPaquetes.getColumnModel().getColumn(3).setPreferredWidth(0);//ID
        // centrar.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
        // eqv.tbllisPaquete.getColumnModel().getColumn(2).setCellRenderer(centrar);
        
    }
    
    public void BuscarProductos(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        String codigo;
        codigo = eqv.txtBuscarProductos.getText();
        
        CatProListM catProSeleccionado = (CatProListM) eqv.cmbcat.getSelectedItem();
        int idCategoria = catProSeleccionado.getId();
        
        // System.out.println("Codigo: " + codigo);
        // System.out.println("Id Cat: " + idCategoria);
        
        pm.setId(-13);
        pm.setCod(null);
        pm.setNom(codigo);
        pm.setSa(0);
        pm.setSm(0);
        pm.setSx(0);
        pm.setPre(0);
        pm.setCat(""+idCategoria);
        pm.setEst(0);
        modelt = pm.CargarProductos();
        eqv.tbllisProductos.setModel(modelt);
        
        eqv.tbllisProductos.getColumnModel().getColumn(0).setMaxWidth(0);
        eqv.tbllisProductos.getColumnModel().getColumn(0).setMinWidth(0);
        eqv.tbllisProductos.getColumnModel().getColumn(0).setPreferredWidth(0);//ID
        eqv.tbllisProductos.getColumnModel().getColumn(2).setMaxWidth(200);
        eqv.tbllisProductos.getColumnModel().getColumn(2).setMinWidth(200);
        eqv.tbllisProductos.getColumnModel().getColumn(2).setPreferredWidth(200);//NOM
        eqv.tbllisProductos.getColumnModel().getColumn(3).setMaxWidth(0);
        eqv.tbllisProductos.getColumnModel().getColumn(3).setMinWidth(0);
        eqv.tbllisProductos.getColumnModel().getColumn(3).setPreferredWidth(0);//ID
        // centrar.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
        // eqv.tbllisProductos.getColumnModel().getColumn(2).setCellRenderer(centrar);
        
    }
    
    public int GuardarEquivalencias(int idProdPadre, int idProdHijo, double cantidad){
        
        eqm.setId(0);
        eqm.setIdProdPadre(idProdPadre);
        eqm.setIdProdHijo(idProdHijo);
        eqm.setCantidad(cantidad);
        eqm.setEstatus(0);
        
        if(eqm.GuardarEquivalencia()){
            return 1;
        }
        return 0;
    }
    
    public int ActualizarEquivalencias(int idEquivalencia, double cantidad){
        
        eqm.setId(-2);
        eqm.setIdProdPadre(idEquivalencia);
        eqm.setIdProdHijo(0);
        eqm.setCantidad(cantidad);
        eqm.setEstatus(0);
        
        if(eqm.GuardarEquivalencia()){
            return 1;
        }
        return 0;
    }
    
    public int AplicarEquivalencia(int idPaquete, int idProducto, double cantidad){
        
        eqm.setId(-3);
        eqm.setIdProdPadre(idPaquete);
        eqm.setIdProdHijo(idProducto);
        eqm.setCantidad(cantidad);
        eqm.setEstatus(0);
        
        if(eqm.GuardarEquivalencia()){
            return 1;
        }
        return 0;
    }
    
    public void CargarEquivalencias(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        eqm.setId(-1);
        modelt = eqm.CargarEquivalencias();
        eqv.tbllisEquivalencias.setModel(modelt);
        
        eqv.tbllisEquivalencias.getColumnModel().getColumn(0).setMaxWidth(0);
        eqv.tbllisEquivalencias.getColumnModel().getColumn(0).setMinWidth(0);
        eqv.tbllisEquivalencias.getColumnModel().getColumn(0).setPreferredWidth(0);//ID
        eqv.tbllisEquivalencias.getColumnModel().getColumn(3).setMaxWidth(100);
        eqv.tbllisEquivalencias.getColumnModel().getColumn(3).setMinWidth(100);
        eqv.tbllisEquivalencias.getColumnModel().getColumn(3).setPreferredWidth(100);//ID
        eqv.tbllisEquivalencias.getColumnModel().getColumn(4).setMaxWidth(0);
        eqv.tbllisEquivalencias.getColumnModel().getColumn(4).setMinWidth(0);
        eqv.tbllisEquivalencias.getColumnModel().getColumn(4).setPreferredWidth(0);//ID
        eqv.tbllisEquivalencias.getColumnModel().getColumn(5).setMaxWidth(0);
        eqv.tbllisEquivalencias.getColumnModel().getColumn(5).setMinWidth(0);
        eqv.tbllisEquivalencias.getColumnModel().getColumn(5).setPreferredWidth(0);//ID
        /*eqv.tbllisPaquete.getColumnModel().getColumn(2).setMaxWidth(200);
        eqv.tbllisPaquete.getColumnModel().getColumn(2).setMinWidth(200);
        eqv.tbllisPaquete.getColumnModel().getColumn(2).setPreferredWidth(200);//NOM
        eqv.tbllisPaquete.getColumnModel().getColumn(3).setMaxWidth(0);
        eqv.tbllisPaquete.getColumnModel().getColumn(3).setMinWidth(0);
        eqv.tbllisPaquete.getColumnModel().getColumn(3).setPreferredWidth(0);//ID*/
        // centrar.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
        // eqv.tbllisPaquete.getColumnModel().getColumn(2).setCellRenderer(centrar);
        
    }
    
}
