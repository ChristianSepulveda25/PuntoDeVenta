/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.*;
import Vista.*;
import innotpv.NumericDocumentFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;

/**
 *
 * @author ChristianSepulveda
 */
public class ClientesC implements ActionListener, KeyListener {
    private ClientesV cv;
    private ClientesM cm;
    public int fila_s = 0;

    public ClientesC(ClientesM cm, ClientesV cv) {
        this.cv = cv;
        this.cm = cm;
        this.cv.btnnue.addActionListener(this);
        this.cv.btngua.addActionListener(this);
        this.cv.btncan.addActionListener(this);
        this.cv.btnedi.addActionListener(this);
        this.cv.btnsal.addActionListener(this);
        this.cv.txtbuscar.addKeyListener(this);
    }

    public void iniciar() {
        cv.setTitle("Registro de Clientes");
        cv.tbllis.getTableHeader().setReorderingAllowed(false) ;
        cv.tbllis.getTableHeader().setResizingAllowed(false);
        cv.txtid.setVisible(false);
        bloquear();
        CargarUsuarios();
        
        AbstractDocument doc = (AbstractDocument) cv.txttelefono.getDocument();
        doc.setDocumentFilter(new NumericDocumentFilter(10));
        
        cv.txtbuscar.requestFocus();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == cv.btnnue){
            desbloquear();
        }else if(e.getSource() == cv.btncan){
            bloquear();
        }else if(e.getSource() == cv.btnsal){
            cv.dispose();
        }else if(e.getSource() == cv.btngua){
            int id = 0;
            String nombre;
            String telefono;
            String correo;
            int estatus = 0;
            
            id = Integer.parseInt(cv.txtid.getText());
            nombre = cv.txtnombre.getText();
            telefono = cv.txttelefono.getText();
            correo = cv.txtcorreo.getText();
            if( "Activo".equals(cv.cmbest.getSelectedItem()) ){
                estatus = 1;
            }
            
            if(nombre.equals("")){
                JOptionPane.showMessageDialog(cv, "Ingrese el nombre del cliente.");
                cv.txtnombre.requestFocus();
                return;
            }
            
            int guardar = GuardarCliente(id,nombre,telefono,correo,estatus);
            if ( guardar == 1 ){
                JOptionPane.showMessageDialog(cv, "Registro Guardado.");
                
                if(id == 0)
                    desbloquear();
                else
                    bloquear();
                
                CargarUsuarios();
            }else{
                JOptionPane.showMessageDialog(cv, "Ocurrió un error al guardar al cliente.");
            }
        }else if(e.getSource() == cv.btnedi){
            int fila = -1;
            fila = cv.tbllis.getSelectedRow();
            if( fila < 0 ){
                JOptionPane.showMessageDialog(cv, "Seleccione un registro.");
            }else{
                desbloquear();
                cv.txtid.setText(cv.tbllis.getValueAt(fila, 0).toString());
                cv.txtnombre.setText(cv.tbllis.getValueAt(fila, 1).toString());
                cv.txttelefono.setText(cv.tbllis.getValueAt(fila, 2).toString());
                cv.txtcorreo.setText(cv.tbllis.getValueAt(fila, 3).toString());
                cv.cmbest.setSelectedItem( cv.tbllis.getValueAt(fila, 4).toString() );
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
        if(e.getSource() == cv.txtbuscar){
            CargarUsuarios();
        }
    }
    
    public void bloquear(){
        cv.txtid.setText("0");
        cv.txtnombre.setText("");
        cv.txttelefono.setText("");
        cv.txtcorreo.setText("");
        cv.cmbest.setSelectedIndex(0);
        
        cv.txtnombre.setEnabled(false);
        cv.txttelefono.setEnabled(false);
        cv.txtcorreo.setEnabled(false);
        cv.cmbest.setEnabled(false);
        
        cv.btngua.setEnabled(false);
        cv.btncan.setEnabled(false);
        cv.btnnue.setEnabled(true);
        cv.btnedi.setEnabled(true);
        cv.btnsal.setEnabled(true);
        
        cv.txtbuscar.setEnabled(true);
        cv.txtbuscar.requestFocus();
    }
    
    public void desbloquear(){
        cv.txtid.setText("0");
        cv.txtnombre.setText("");
        cv.txttelefono.setText("");
        cv.txtcorreo.setText("");
        cv.cmbest.setSelectedIndex(0);
        
        cv.txtnombre.setEnabled(true);
        cv.txttelefono.setEnabled(true);
        cv.txtcorreo.setEnabled(true);
        cv.cmbest.setEnabled(true);
        
        cv.btngua.setEnabled(true);
        cv.btncan.setEnabled(true);
        cv.btnnue.setEnabled(false);
        cv.btnedi.setEnabled(false);
        cv.btnsal.setEnabled(false);
        
        cv.txtbuscar.setEnabled(false);
        cv.txtnombre.requestFocus();
    }
    
    public int GuardarCliente(int id,String nombre,String telefono,String correo,int estatus){
        
        cm.setId(id);
        cm.setNombre(nombre.toUpperCase());
        cm.setTelefono(telefono);
        cm.setCorreo(correo.toLowerCase());
        cm.setEstatus(estatus);
        
        if( cm.GuardarCliente() ){
            return 1;
        }
        
        return 0;
    }
    
    public void CargarUsuarios(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        String nombre;
        nombre = cv.txtbuscar.getText();
        
        cm.setId(-1);
        cm.setNombre(nombre);
        
        modelt = cm.CargarClientes();
        cv.tbllis.setModel(modelt);
        
        cv.tbllis.getColumnModel().getColumn(0).setMaxWidth(0);
        cv.tbllis.getColumnModel().getColumn(0).setMinWidth(0);
        cv.tbllis.getColumnModel().getColumn(0).setPreferredWidth(0);
        cv.tbllis.getColumnModel().getColumn(4).setMaxWidth(100);
        cv.tbllis.getColumnModel().getColumn(4).setMinWidth(100);
        cv.tbllis.getColumnModel().getColumn(4).setPreferredWidth(100);
        /*uv.tbllis.getColumnModel().getColumn(1).setMaxWidth(300);
        uv.tbllis.getColumnModel().getColumn(1).setMinWidth(300);
        uv.tbllis.getColumnModel().getColumn(1).setPreferredWidth(300);*/
    }
    
}
