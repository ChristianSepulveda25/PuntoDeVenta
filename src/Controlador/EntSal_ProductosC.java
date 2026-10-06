/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.*;
import Vista.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ChristianSepulveda
 */
public class EntSal_ProductosC implements ActionListener, KeyListener, MouseListener, ItemListener {
    private final EntSal_ProductosV espv;
    private final EntSal_ProductosM espm;
    private final AlmacenM am = new AlmacenM();
    private final ProductosM pm = new ProductosM();
    public DefaultTableModel modelESP;
    
    public EntSal_ProductosC(EntSal_ProductosM espm, EntSal_ProductosV espv) {
        this.espv = espv;
        this.espm = espm;
        this.espv.btnguardar.addActionListener(this);
        this.espv.btnsalir.addActionListener(this);
        this.espv.cmbmovimiento.addActionListener(this);
        this.espv.txtCodigoProducto.addKeyListener(this);
        this.espv.btnsumar.addActionListener(this);
        this.espv.btnrestar.addActionListener(this);
        this.espv.btneliminar.addActionListener(this);
        this.espv.btnpreciocosto.addActionListener(this);
        // this.espv.tblEntSal.addMouseListener(this);
        /*this.av.btnnue.addActionListener(this);
        this.av.btncan.addActionListener(this);
        this.av.btnedi.addActionListener(this);*/
    }

    public void iniciar() {
        espv.setTitle("Registro de entradas y salidas de productos");
        espv.tblEntSal.getTableHeader().setReorderingAllowed(false);
        espv.tblEntSal.getTableHeader().setResizingAllowed(false);
        espv.tblEntSal.getTableHeader().setReorderingAllowed(false);
        espv.tblEntSal.getTableHeader().setResizingAllowed(false);
        BuscarAlmacenes();
        espv.txtCodigoProducto.requestFocus();
        
        /*modelESP = (DefaultTableModel) espv.tblEntSal.getModel();
        modelESP.addColumn("ID");
        modelESP.addColumn("NOMBRE");
        modelESP.addColumn("PRECIO");
        modelESP.addColumn("COSTO");
        modelESP.addColumn("CANTIDAD");*/
        
        // Inicializar el modelo de tabla
        modelESP = new DefaultTableModel(new Object[]{"ID", "NOMBRE", "PRECIO", "COSTO", "CANTIDAD"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Deshabilitar la edición de todas las celdas
            }
        };

        // Asignar el modelo a la JTable
        espv.tblEntSal.setModel(modelESP);
        
        // Deshabilitar la edición de todas las celdas
        /*modelESP.addTableModelListener(e -> {
            // Esta línea previene la edición de las celdas
            espv.tblEntSal.setCellSelectionEnabled(false);
            espv.tblEntSal.setRowSelectionAllowed(false);
        });*/
        /*BuscarCategorias();
        espv.txtcantidad.setText("1");*/
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
        }else if( e.getSource() == espv.cmbmovimiento ){
            espv.txtCodigoProducto.requestFocus();
        }else if( e.getSource() == espv.btnsumar ){
            int fila = 0;
            fila = espv.tblEntSal.getSelectedRow();
            if(fila < 0){
                JOptionPane.showMessageDialog(espv, "Seleccione un Registro");
                espv.txtCodigoProducto.requestFocus();
            }else{
                double cantidadActual = 0;
                cantidadActual = Double.parseDouble(modelESP.getValueAt(fila, 4).toString());
                
                modelESP.setValueAt(cantidadActual+1, fila, 4);
                espv.txtCodigoProducto.requestFocus();
            }
        }else if( e.getSource() == espv.btnrestar ){
            int fila = 0;
            fila = espv.tblEntSal.getSelectedRow();
            if(fila < 0){
                JOptionPane.showMessageDialog(espv, "Seleccione un Registro");
                espv.txtCodigoProducto.requestFocus();
            }else{
                double cantidadActual = 0;
                cantidadActual = Double.parseDouble(modelESP.getValueAt(fila, 4).toString());
                
                if( cantidadActual > 1 ){
                    modelESP.setValueAt(cantidadActual-1, fila, 4);
                }
                espv.txtCodigoProducto.requestFocus();
            }
        }else if( e.getSource() == espv.btneliminar ){
            int fila = 0;
            fila = espv.tblEntSal.getSelectedRow();
            if(fila < 0){
                JOptionPane.showMessageDialog(espv, "Seleccione un Registro");
                espv.txtCodigoProducto.requestFocus();
            }else{
                modelESP.removeRow(fila);
                espv.txtCodigoProducto.requestFocus();
            }
        }else if( e.getSource() == espv.btnpreciocosto ){
            int fila = 0;
            fila = espv.tblEntSal.getSelectedRow();
            if(fila < 0){
                JOptionPane.showMessageDialog(espv, "Seleccione un Registro");
            }else{
                Double precio = null;
                precio = Double.parseDouble(modelESP.getValueAt(fila, 2).toString());
                String input1 = JOptionPane.showInputDialog(null, "Confirmar el precio de venta:", "Confirmar Precio de Venta", JOptionPane.QUESTION_MESSAGE, null, null, precio).toString();
                if (input1 == null) { // Usuario cancela el cuadro de diálogo
                    System.out.println("Cancelar");
                }else{
                    try {
                        Double.valueOf(input1); // Intenta convertir la entrada a un número
                        modelESP.setValueAt(input1, fila, 2);
                        Double costo = null;
                        costo = Double.parseDouble(modelESP.getValueAt(fila, 3).toString());
                        String input2 = JOptionPane.showInputDialog(null, "Confirmar el costo:", "Confirmar Costo", JOptionPane.QUESTION_MESSAGE, null, null, costo).toString();
                        if (input2 == null) { // Usuario cancela el cuadro de diálogo
                            System.out.println("Cancelar");
                        }else{
                            try {
                                Double.valueOf(input2); // Intenta convertir la entrada a un número
                                /*Object[] newRow = new Object[]{partsResult[0],partsResult[1],input1,input2,input};
                                addOrUpdateRow(modelESP, newRow);*/
                                
                                modelESP.setValueAt(input2, fila, 3);
                            } catch (NumberFormatException ex) {
                                // Si la conversión falla, muestra un mensaje de error y pide la entrada nuevamente
                                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.","Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } catch (NumberFormatException ex) {
                        // Si la conversión falla, muestra un mensaje de error y pide la entrada nuevamente
                        JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.","Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }else if( e.getSource() == espv.btnguardar ){
            int filas = 0;
            filas = espv.tblEntSal.getRowCount();
            if(filas <= 0){
                JOptionPane.showMessageDialog(espv, "Ingrese al menos un producto.");
                espv.txtCodigoProducto.requestFocus();
            }else{
                System.out.println("Guardar");
                String movimiento = null;
                int almacen = 0;
                
                movimiento = espv.cmbmovimiento.getSelectedItem().toString();
                String[] partsAlmacen = null;
                partsAlmacen = espv.cmbalm.getSelectedItem().toString().split("'");
                almacen = Integer.parseInt(partsAlmacen[1]);
                
                System.out.println(movimiento);
                System.out.println(almacen);
                
                Object[] options = {"Si", "No"};
                int confirm = JOptionPane.showOptionDialog(
                    espv,
                    "Confirmar " + movimiento + " de Productos.",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[1]
                );

                // Si el usuario confirma, eliminar la fila
                if (confirm == 0) {
                    System.out.println("Guardar Confirmado");
                    GuardarCompraProductos();
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getSource() == espv.txtCodigoProducto){
            if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                String codigoBuscar = "";
                codigoBuscar = espv.txtCodigoProducto.getText().toString();
                if( !(codigoBuscar.isEmpty()) ){
                    String[] partsCodigoBuscar = null;
                    String resultadoBuscarProducto = null;
                    resultadoBuscarProducto = BuscarProducto(codigoBuscar);
                    if( !resultadoBuscarProducto.isEmpty() ){
                        String[] partsResult = null;
                        partsResult = resultadoBuscarProducto.split("\\|");
                        /*System.out.println(partsResult[0]);
                        System.out.println(partsResult[1]);
                        System.out.println(partsResult[2]);
                        System.out.println(partsResult[3]);*/
                        
                        String input = JOptionPane.showInputDialog(null, "Ingrese cantidad de productos:", "Confirmar Cantidad de Productos", JOptionPane.QUESTION_MESSAGE, null, null, 1).toString();
                        
                        if (input == null) { // Usuario cancela el cuadro de diálogo
                            // System.out.println("Cancelar");
                        }else{
                            try {
                                Double.valueOf(input); // Intenta convertir la entrada a un número
                                String input1 = JOptionPane.showInputDialog(null, "Confirmar el precio de venta:", "Confirmar Precio de Venta", JOptionPane.QUESTION_MESSAGE, null, null, partsResult[2]).toString();
                                if (input1 == null) { // Usuario cancela el cuadro de diálogo
                                    System.out.println("Cancelar");
                                }else{
                                    try {
                                        Double.valueOf(input1); // Intenta convertir la entrada a un número
                                        String input2 = JOptionPane.showInputDialog(null, "Confirmar el costo:", "Confirmar Costo", JOptionPane.QUESTION_MESSAGE, null, null, partsResult[3]).toString();
                                        if (input2 == null) { // Usuario cancela el cuadro de diálogo
                                            System.out.println("Cancelar");
                                        }else{
                                            try {
                                                Double.valueOf(input2); // Intenta convertir la entrada a un número
                                                Object[] newRow = new Object[]{partsResult[0],partsResult[1],input1,input2,input};
                                                addOrUpdateRow(modelESP, newRow);
                                            } catch (NumberFormatException ex) {
                                                // Si la conversión falla, muestra un mensaje de error y pide la entrada nuevamente
                                                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.","Error", JOptionPane.ERROR_MESSAGE);
                                            }
                                        }
                                    } catch (NumberFormatException ex) {
                                        // Si la conversión falla, muestra un mensaje de error y pide la entrada nuevamente
                                        JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.","Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                            } catch (NumberFormatException ex) {
                                // Si la conversión falla, muestra un mensaje de error y pide la entrada nuevamente
                                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.","Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                    espv.txtCodigoProducto.setText("");
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
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

    @Override
    public void itemStateChanged(ItemEvent e) {
    }
    
    public void BuscarAlmacenes(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        espv.cmbalm.removeAllItems();
        // espv.cmbalm.addItem("Seleccione");
        am.setId(-2);
        //cpm.setCon(null);
        //cpm.setNom(null);
        //cpm.setEst(0);
        modelt = am.CargarAlmacen();
        for (int i = 0; i < modelt.getRowCount(); i++) {
            espv.cmbalm.addItem(modelt.getValueAt(i, 1).toString() + " '" + modelt.getValueAt(i, 0).toString());
        }
    }
    
    public String BuscarProducto(String codigo){
        pm.setId(-9);
        pm.setCod(codigo);
        pm.setNom("");
        pm.setSa(0);
        pm.setSm(0);
        pm.setSx(0);
        pm.setPre(0);
        pm.setCat("");
        pm.setEst(0);
        pm.setGr_ml(0);
        pm.setCosto(0);
        
        return pm.ObtenerProductoCodigo();
    }
    
    public static boolean rowExists(DefaultTableModel model, Object[] rowData) {
        int columnCount = model.getColumnCount();
        for (int i = 0; i < model.getRowCount(); i++) {
            boolean match = true;
            for (int j = 0; j < columnCount; j++) {
                if (!model.getValueAt(i, j).equals(rowData[j])) {
                    match = false;
                    break;
                }
            }
            if (match) {
                return true;
            }
        }
        return false;
    }
    
    public static void addOrUpdateRow(DefaultTableModel model, Object[] rowData) {
        int idColumnIndex = 0; // Índice de la columna ID
        int quantityColumnIndex = 4; // Índice de la columna Cantidad
        boolean rowUpdated = false;

        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, idColumnIndex).equals(rowData[idColumnIndex])) {
                // La fila con el mismo ID ya existe, actualiza la cantidad
                double currentQuantity = Double.parseDouble(model.getValueAt(i, quantityColumnIndex).toString());
                model.setValueAt(currentQuantity + Double.parseDouble(rowData[quantityColumnIndex].toString()), i, quantityColumnIndex);
                rowUpdated = true;
                break;
            }
        }

        if (!rowUpdated) {
            // Si la fila no existe, agregar la nueva fila
            model.addRow(rowData);
        }
    }
    
    public void GuardarCompraProductos(){
        int filas = 0;
        filas = espv.tblEntSal.getRowCount();
        
        for (int i = 0; i < filas ; i++) {
            System.out.println(espv.tblEntSal.getValueAt(i, 0).toString());
            System.out.println(espv.tblEntSal.getValueAt(i, 1).toString());
            System.out.println(espv.tblEntSal.getValueAt(i, 2).toString());
            System.out.println(espv.tblEntSal.getValueAt(i, 3).toString());
            System.out.println(espv.tblEntSal.getValueAt(i, 4).toString());
            
        }
        
    }
    
}
