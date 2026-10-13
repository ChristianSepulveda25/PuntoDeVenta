/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.*;
import Vista.*;
import innotpv.IntegerRangeDocumentFilter;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;

/**
 *
 * @author ChristianSepulveda
 */
public class PreciosPorcentajesC implements ActionListener, KeyListener {
    private PreciosPorcentajesV pv;
    private PreciosPorcentajesM pm;
    private CatProM cpm = new CatProM();
    public int fila_s = 0;
    
    public PreciosPorcentajesC(PreciosPorcentajesM pm, PreciosPorcentajesV pv) {
        this.pv = pv;
        this.pm = pm;
        this.pv.btnnue.addActionListener(this);
        this.pv.btngua.addActionListener(this);
        this.pv.btncan.addActionListener(this);
        this.pv.btnedi.addActionListener(this);
        this.pv.btnsal.addActionListener(this);
    }

    public void iniciar() {
        pv.setTitle("Registro de Precios y Porcentajes");
        ImageIcon originalIcon  = new ImageIcon(getClass().getResource("/img/FondoCopyCentro72ppp.png"));
        Image image = originalIcon.getImage();
        Image scaledImage = image.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        this.pv.setFrameIcon(resizedIcon);
        pv.tbllis.getTableHeader().setReorderingAllowed(false) ;
        pv.tbllis.getTableHeader().setResizingAllowed(false);
        pv.txtid.setVisible(false);
        
        // Suponiendo que tus JTextField se llaman txtCampo1, txtCampo2 y txtCampo3
        ((AbstractDocument) pv.txtprecio.getDocument()).setDocumentFilter(new IntegerRangeDocumentFilter(100));
        ((AbstractDocument) pv.txtmayoreo.getDocument()).setDocumentFilter(new IntegerRangeDocumentFilter(100));
        ((AbstractDocument) pv.txtmenudeo.getDocument()).setDocumentFilter(new IntegerRangeDocumentFilter(100));
        
        CargarPrecioPorcentaje();
        
        //CargarAlmacen();
        pv.cmbcategoria.setEnabled(false);
        pv.txtprecio.setEnabled(false);
        pv.txtmayoreo.setEnabled(false);
        pv.txtmenudeo.setEnabled(false);
        pv.btngua.setEnabled(false);
        pv.btncan.setEnabled(false);
        pv.btnedi.setVisible(false);
        pv.btnnue.requestFocus();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void keyPressed(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == pv.btnnue){
            limpiar();
            desbloquear();
        }else if(e.getSource() == pv.btnedi){
            
        }else if(e.getSource() == pv.btncan){
            limpiar();
            bloquear();
        }if(e.getSource() == pv.btngua){
            
            String precio = pv.txtprecio.getText().trim();
            String mayoreo = pv.txtmayoreo.getText().trim();
            String menudeo = pv.txtmenudeo.getText().trim();

            // 1. Verificar si precio está vacío
            if (precio.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(pv, "El campo Precio no puede estar vacío.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                pv.txtprecio.requestFocus();
                return; // Detiene el proceso
            }else if (Integer.parseInt(precio) <= 0) {
                javax.swing.JOptionPane.showMessageDialog(pv, "El campo Precio no puede ser igual a cero.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                pv.txtprecio.requestFocus();
                return; // Detiene el proceso
            }

            // 1. Verificar si mayoreo está vacío
            if (mayoreo.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(pv, "El campo Mayoreo no puede estar vacío.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                pv.txtmayoreo.requestFocus();
                return; // Detiene el proceso
            }else if (Integer.parseInt(mayoreo) <= 0) {
                javax.swing.JOptionPane.showMessageDialog(pv, "El campo Mayoreo no puede ser igual a cero.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                pv.txtmayoreo.requestFocus();
                return; // Detiene el proceso
            }

            // 1. Verificar si menudeo está vacío
            if (menudeo.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(pv, "El campo Menudeo no puede estar vacío.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                pv.txtmenudeo.requestFocus();
                return; // Detiene el proceso
            }else if (Integer.parseInt(menudeo) <= 0) {
                javax.swing.JOptionPane.showMessageDialog(pv, "El campo Menudeo no puede ser igual a cero.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                pv.txtmenudeo.requestFocus();
                return; // Detiene el proceso
            }
            
            int reg = 0;
            reg = GuardarPrecioPorcentaje();
            if(reg <= 0){//0 - Error, 1 - Guardado
                JOptionPane.showMessageDialog(pv, "Error al Guardar, Contacte a Soporte Técnico");
            }else if (reg == 1){
                limpiar();
                CargarCatPro();
                CargarPrecioPorcentaje();
                JOptionPane.showMessageDialog(pv, "Porcentajes Guardados.");
            }
            
        }if(e.getSource() == pv.btnsal){
            pv.dispose();
        }
    }
    
    public void limpiar(){
        pv.txtid.setText("0");
        CargarCatPro();
        pv.txtprecio.setText("0");
        pv.txtmayoreo.setText("0");
        pv.txtmenudeo.setText("0");
    }
    
    public void bloquear(){
        // Campos
        pv.txtid.setEnabled(false);
        pv.cmbcategoria.setEnabled(false);
        pv.txtprecio.setEnabled(false);
        pv.txtmayoreo.setEnabled(false);
        pv.txtmenudeo.setEnabled(false);
        // Botones
        pv.btnnue.setEnabled(true);
        pv.btnedi.setEnabled(true);
        pv.btncan.setEnabled(false);
        pv.btngua.setEnabled(false);
        pv.btnsal.setEnabled(true);
    }
    
    public void desbloquear(){
        // Camos
        pv.txtid.setEnabled(true);
        pv.cmbcategoria.setEnabled(true);
        pv.txtprecio.setEnabled(true);
        pv.txtmayoreo.setEnabled(true);
        pv.txtmenudeo.setEnabled(true);
        // Botones
        pv.btnnue.setEnabled(false);
        pv.btnedi.setEnabled(false);
        pv.btncan.setEnabled(true);
        pv.btngua.setEnabled(true);
        pv.btnsal.setEnabled(false);
    }
    
    public void CargarCatPro(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        pv.cmbcategoria.removeAllItems();
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
            pv.cmbcategoria.addItem(catProList);
        }
        
    }
    
    public int GuardarPrecioPorcentaje(){
        int id = 0;
        int idCat = 0;
        int precio = 0;
        int mayoreo = 0;
        int menudeo = 0;
        
        id = Integer.parseInt(pv.txtid.getText());
        
        CatProListM catProSeleccionado = (CatProListM) pv.cmbcategoria.getSelectedItem();
        idCat = catProSeleccionado.getId();
        
        precio = Integer.parseInt(pv.txtprecio.getText());
        mayoreo = Integer.parseInt(pv.txtmayoreo.getText());
        menudeo = Integer.parseInt(pv.txtmenudeo.getText());
        
        pm.setId(id);
        pm.setId_cat(idCat);
        pm.setPrecio(precio);
        pm.setMayoreo(mayoreo);
        pm.setMenudeo(menudeo);
        
        if(pm.GuardarPreciosPorcentajes()){
            return 1;
        }
        return 0;
    }
    
    public void CargarPrecioPorcentaje(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        pm.setId(-1);
        modelt = pm.CargarPreciosPorcentajes();
        pv.tbllis.setModel(modelt);
        
        pv.tbllis.getColumnModel().getColumn(0).setMaxWidth(200);
        pv.tbllis.getColumnModel().getColumn(0).setMinWidth(200);
        pv.tbllis.getColumnModel().getColumn(0).setPreferredWidth(200);
        
        // --- CENTRAR LAS COLUMNAS 2, 3 y 4 (Índices 1, 2 y 3) ---
        javax.swing.table.DefaultTableCellRenderer centrar = new javax.swing.table.DefaultTableCellRenderer();
        centrar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        
        
        javax.swing.table.DefaultTableCellRenderer left = new javax.swing.table.DefaultTableCellRenderer();
        left.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        // Aplicar el renderizador a las columnas deseadas
        pv.tbllis.getColumnModel().getColumn(1).setCellRenderer(centrar); // Columna 2
        pv.tbllis.getColumnModel().getColumn(2).setCellRenderer(centrar); // Columna 3
        pv.tbllis.getColumnModel().getColumn(3).setCellRenderer(centrar); // Columna 4
        
        pv.tbllis.getColumnModel().getColumn(0).setCellRenderer(left); // Columna 4
    }
    
}
