/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ChristianSepulveda
 */
public class EntradasSalidasPExpressC implements ActionListener, KeyListener {
    private EntradasSalidasPExpressM modelo;
    private EntradasSalidasPExpressV vista;
    private AlmacenM am = new AlmacenM();

    public EntradasSalidasPExpressC(EntradasSalidasPExpressM modelo, EntradasSalidasPExpressV vista) {
        this.modelo = modelo;
        this.vista = vista;
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnSalir.addActionListener(this);
    }
    
    public void iniciar() {
        this.vista.setTitle("Entradas y Salidas de Productos");
        ImageIcon originalIcon  = new ImageIcon(getClass().getResource("/img/FondoCopyCentro72ppp.png"));
        Image image = originalIcon.getImage();
        Image scaledImage = image.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        this.vista.setFrameIcon(resizedIcon);
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnSalir.addActionListener(this);
        this.vista.txtCodigo.addKeyListener(this);
        CargarAlmacen();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if( e.getSource() == vista.btnSalir ){
            vista.dispose();
        }else if( e.getSource() == vista.btnGuardar ){
            GuardarMovimiento();
        }
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
            vista.cmbAlmacen.addItem(almacen);
        }
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
    
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getSource() == vista.txtCodigo){
            if( e.getKeyCode() == KeyEvent.VK_ESCAPE ){
                vista.dispose();
            }else if( e.getKeyCode() == KeyEvent.VK_ENTER ){
                GuardarMovimiento();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    
    }
    
    public void GuardarMovimiento(){
        String codigo = null;
        codigo = vista.txtCodigo.getText().toString();

        if( codigo.equals("") ){
            JOptionPane.showMessageDialog(vista, "Ingrese el código del producto.");
            vista.txtCodigo.requestFocus();
            return;
        }
        
        int verificarCodigo = 0;
        verificarCodigo = modelo.ValidarExistenciaProducto(codigo);
        
        if( verificarCodigo <= 0){
            JOptionPane.showMessageDialog(vista, "El código no existe.");
            vista.txtCodigo.requestFocus();
            return;
        }
        
        int cantidad = 0;

        // Mostrar un cuadro de diálogo de entrada para solicitar la cantidad
        String input = JOptionPane.showInputDialog(null, "Por favor, ingresa la cantidad:", "Entrada de cantidad", JOptionPane.PLAIN_MESSAGE);

        // Verificar si el usuario no canceló el cuadro de diálogo
        if (input != null && !input.trim().isEmpty()) {
            try {
                // Convertir el valor ingresado a un número entero
                cantidad = Integer.parseInt(input);

                int guardar = GuardarEntradaSalida(codigo, cantidad);
                if( guardar == 1 ){
                    JOptionPane.showMessageDialog(vista, "Movimiento Guardado.");
                    vista.txtCodigo.setText("");
                    vista.txtCodigo.requestFocus();
                }else{
                    JOptionPane.showMessageDialog(vista, "Ocurrio un error al guardar el movimiento.");
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
    }
    
    public int GuardarEntradaSalida(String codigo, double cantidad){
        int tipo = 0;
        
        tipo = vista.cmbTipo.getSelectedIndex();
        
        AlmacenListM almacenSeleccionado = (AlmacenListM) vista.cmbAlmacen.getSelectedItem();
        int idSeleccionado = almacenSeleccionado.getId();
        
        String usuarioLogeado = GlobalVaribales.getInstance().usuarioLogueado;
        
        modelo.setParam(1);
        modelo.setTipo(tipo);
        modelo.setAlmacen(idSeleccionado);
        modelo.setCodigo(codigo);
        modelo.setCantidad(cantidad);
        modelo.setUsuarioRegistra(usuarioLogeado);
        
        if(modelo.GuardarESProducto()){
            return 1;
        }
        return 0;
    }
    
}
