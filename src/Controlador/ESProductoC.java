/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.*;
import Vista.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Christian Sepulveda
 */
public class ESProductoC implements ActionListener, KeyListener, MouseListener {
    private ESProductoM espm;
    private ESProductoV espv;
    private CatProM cpm = new CatProM();
    private ProductosM pm = new ProductosM();
    DefaultTableModel modeltCat;
    DefaultTableModel modeltPro;
    DefaultTableModel modeltCat1;
    DefaultTableModel modeltPro1;

    public ESProductoC(ESProductoM espm, ESProductoV espv) {
        this.espv = espv;
        this.espm = espm;
        this.espv.tbdpnl_es.addMouseListener(this);
        this.espv.tblcat.addMouseListener(this);
        this.espv.txtconcepto.addKeyListener(this);
        this.espv.txtcan.addKeyListener(this);
        this.espv.btnmen.addActionListener(this);
        this.espv.btnmas.addActionListener(this);
        this.espv.btnsal.addActionListener(this);
        this.espv.btnreg.addActionListener(this);
        this.espv.tblcat1.addMouseListener(this);
        this.espv.txtconcepto1.addKeyListener(this);
        this.espv.txtcan1.addKeyListener(this);
        this.espv.btnmen1.addActionListener(this);
        this.espv.btnmas1.addActionListener(this);
        this.espv.btnsal1.addActionListener(this);
        this.espv.btnreg1.addActionListener(this);
    }

    public void iniciar() {
        espv.setTitle("Entrada y Salida de Productos");
        llenarCat();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == espv.btnmas){
            int can = 0;
            int res = 0;
            can = Integer.parseInt(espv.txtcan.getText());
            can = can + 1;
            espv.txtcan.setText(""+can);
        }else if(e.getSource() == espv.btnmen){
            int can = 0;
            int res = 0;
            can = Integer.parseInt(espv.txtcan.getText());
            if(can == 0){
                
            }else{
                can = can - 1;
                espv.txtcan.setText(""+can);
            }
        }else if(e.getSource() == espv.btnreg){
            if(espv.tblpro.getRowCount() <= 0){
                JOptionPane.showMessageDialog(espv, "Seleccione una Categoría");
            }else if(espv.tblpro.getSelectedRow() < 0){
                JOptionPane.showMessageDialog(espv, "Seleccione un Producto");
            }else if(espv.txtconcepto.getText().length() <= 20){
                JOptionPane.showMessageDialog(espv, "Ingrese el Concepto o Motivo \n Mínimo 20 caracteres.");
                espv.txtcan.requestFocus();
            }else if(Integer.parseInt(espv.txtcan.getText()) == 0){
                JOptionPane.showMessageDialog(espv, "Ingrese una Cantidad Mayor a Cero");
                espv.txtcan.requestFocus();
            }else{
                int gua = 0;
                gua = GuardarEntProducto();
                if(gua <= 0){
                    JOptionPane.showMessageDialog(espv, "Error al Guardar, Contacte a Soporte Técnico");
                }else{
                    llenarCat();
                    DefaultTableModel modeltVaciar = new DefaultTableModel();
                    espv.tblpro.setModel(modeltVaciar);
                    espv.txtconcepto.setText("");
                    espv.txtcan.setText("0");
                    JOptionPane.showMessageDialog(espv, "Registro Guardado");
                    espv.tblcat.requestFocus();
                }
            }
        }else if(e.getSource() == espv.btnsal){
            espv.dispose();
        }else if(e.getSource() == espv.btnmas1){
            int can = 0;
            int res = 0;
            can = Integer.parseInt(espv.txtcan1.getText());
            can = can + 1;
            espv.txtcan1.setText(""+can);
        }else if(e.getSource() == espv.btnmen1){
            int can = 0;
            int res = 0;
            can = Integer.parseInt(espv.txtcan1.getText());
            if(can == 0){
                
            }else{
                can = can - 1;
                espv.txtcan1.setText(""+can);
            }
        }else if(e.getSource() == espv.btnreg1){
            if(espv.tblpro1.getRowCount() <= 0){
                JOptionPane.showMessageDialog(espv, "Seleccione una Categoría");
            }else if(espv.tblpro1.getSelectedRow() < 0){
                JOptionPane.showMessageDialog(espv, "Seleccione un Producto");
            }else if(espv.txtconcepto1.getText().length() <= 20){
                JOptionPane.showMessageDialog(espv, "Ingrese el Concepto o Motivo \n Mínimo 20 caracteres.");
                espv.txtcan1.requestFocus();
            }else if(Integer.parseInt(espv.txtcan1.getText()) == 0){
                JOptionPane.showMessageDialog(espv, "Ingrese una Cantidad Mayor a Cero");
                espv.txtcan1.requestFocus();
            }else if(Integer.parseInt(espv.txtcan1.getText()) > Integer.parseInt(espv.tblpro1.getValueAt(espv.tblpro1.getSelectedRow(), 1).toString())){
                JOptionPane.showMessageDialog(espv, "La Cantidad Para SALIDA es Mayor al Stock Actual");
                espv.txtcan1.requestFocus();
            }else{
                int gua = 0;
                gua = GuardarEntProducto1();
                if(gua <= 0){
                    JOptionPane.showMessageDialog(espv, "Error al Guardar, Contacte a Soporte Técnico");
                }else{
                    llenarCat1();
                    DefaultTableModel modeltVaciar = new DefaultTableModel();
                    espv.tblpro1.setModel(modeltVaciar);
                    espv.txtconcepto1.setText("");
                    espv.txtcan1.setText("0");
                    JOptionPane.showMessageDialog(espv, "Registro Guardado");
                    espv.tblcat1.requestFocus();
                }
            }
        }else if(e.getSource() == espv.btnsal1){
            espv.dispose();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getSource() == espv.txtconcepto){
            if (espv.txtconcepto.getText().length()== 250){
                e.consume();
            }
        }else if(e.getSource() == espv.txtcan){
            if (espv.txtcan.getText().length()== 4){
                e.consume();
            }
        }else if(e.getSource() == espv.txtcan){
            if(e.getSource() == espv.txtcan){
                char caracter = e.getKeyChar();
                if (((caracter < '0') || (caracter > '9'))) {
                    e.consume();
                }
            }
        }else if(e.getSource() == espv.txtconcepto1){
            if (espv.txtconcepto1.getText().length()== 250){
                e.consume();
            }
        }else if(e.getSource() == espv.txtcan1){
            if (espv.txtcan1.getText().length()== 4){
                e.consume();
            }
        }else if(e.getSource() == espv.txtcan1){
            if(e.getSource() == espv.txtcan1){
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
        if(e.getSource() == espv.txtconcepto){
            int car = 0;
            car = espv.txtconcepto.getText().length();
            espv.lblcan.setText(""+ (250 - car));
        }else if(e.getSource() == espv.txtconcepto1){
            int car = 0;
            car = espv.txtconcepto1.getText().length();
            espv.lblcan1.setText(""+ (250 - car));
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == espv.tblcat){
            int fila = 0;
            fila = espv.tblcat.getSelectedRow();
            if(fila < 0){
                JOptionPane.showMessageDialog(espv, "Seleccione un Registro");
            }else{
                llenarPro(espv.tblcat.getValueAt(fila, 0).toString());
            }
        }else if(e.getSource() == espv.tblcat1){
            int fila = 0;
            fila = espv.tblcat1.getSelectedRow();
            if(fila < 0){
                JOptionPane.showMessageDialog(espv, "Seleccione un Registro");
            }else{
                llenarPro1(espv.tblcat1.getValueAt(fila, 0).toString());
            }
        }else if(e.getSource() == espv.tbdpnl_es){
            if(espv.tbdpnl_es.getSelectedIndex() == 0){
                DefaultTableModel modeltVaciar = new DefaultTableModel();
                espv.tblpro.setModel(modeltVaciar);
                espv.txtconcepto.setText("");
                espv.txtcan.setText("0");
                llenarCat();
            }else{
                DefaultTableModel modeltVaciar = new DefaultTableModel();
                espv.tblpro1.setModel(modeltVaciar);
                espv.txtconcepto1.setText("");
                espv.txtcan1.setText("0");
                llenarCat1();
                
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

    public void llenarCat(){
        modeltCat = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        cpm.setId(-1);
        cpm.setCon(null);
        cpm.setNom(null);
        cpm.setEst(0);
        modelt = cpm.CargarCatPro();
        modeltCat.addColumn("Categoría");
        int filas = 0;
        filas = modelt.getRowCount();
        for (int i=0;i<filas;i++){
            modeltCat.addRow(new Object[]{modelt.getValueAt(i, 1).toString() + " '" + modelt.getValueAt(i, 0).toString()});
        }
        espv.tblcat.setModel(modeltCat);
    }
    
    public void llenarCat1(){
        modeltCat1 = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        cpm.setId(-1);
        cpm.setCon(null);
        cpm.setNom(null);
        cpm.setEst(0);
        modelt = cpm.CargarCatPro();
        modeltCat1.addColumn("Categoría");
        int filas = 0;
        filas = modelt.getRowCount();
        for (int i=0;i<filas;i++){
            modeltCat1.addRow(new Object[]{modelt.getValueAt(i, 1).toString() + " '" + modelt.getValueAt(i, 0).toString()});
        }
        espv.tblcat1.setModel(modeltCat1);
    }
    
    public void llenarPro(String producto){
        modeltPro = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        String cod = null;
        String[] partCod;
        partCod = producto.split("'");
        cod = partCod[1];
        
        pm.setId(-3);
        pm.setCod(null);
        pm.setNom(null);
        pm.setSa(0);
        pm.setSm(0);
        pm.setPre(0);
        pm.setCat(cod);
        pm.setEst(0);
        modelt = pm.CargarProductos();
        modeltPro.addColumn("Producto");
        modeltPro.addColumn("StockA");
        int filas = 0;
        filas = modelt.getRowCount();
        for (int i=0;i<filas;i++){
            modeltPro.addRow(new Object[]{modelt.getValueAt(i, 2).toString() + " '" + modelt.getValueAt(i, 1).toString(),modelt.getValueAt(i, 3).toString()});
        }
        espv.tblpro.setModel(modeltPro);
        espv.tblpro.getColumnModel().getColumn(1).setMaxWidth(60);
        espv.tblpro.getColumnModel().getColumn(1).setMinWidth(60);
        espv.tblpro.getColumnModel().getColumn(1).setPreferredWidth(60);
    }
    
    public void llenarPro1(String producto){
        modeltPro1 = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        String cod = null;
        String[] partCod;
        partCod = producto.split("'");
        cod = partCod[1];
        
        pm.setId(-3);
        pm.setCod(null);
        pm.setNom(null);
        pm.setSa(0);
        pm.setSm(0);
        pm.setPre(0);
        pm.setCat(cod);
        pm.setEst(0);
        modelt = pm.CargarProductos();
        modeltPro1.addColumn("Producto");
        modeltPro1.addColumn("StockA");
        int filas = 0;
        filas = modelt.getRowCount();
        for (int i=0;i<filas;i++){
            modeltPro1.addRow(new Object[]{modelt.getValueAt(i, 2).toString() + " '" + modelt.getValueAt(i, 1).toString(),modelt.getValueAt(i, 3).toString()});
        }
        espv.tblpro1.setModel(modeltPro1);
        espv.tblpro1.getColumnModel().getColumn(1).setMaxWidth(60);
        espv.tblpro1.getColumnModel().getColumn(1).setMinWidth(60);
        espv.tblpro1.getColumnModel().getColumn(1).setPreferredWidth(60);
    }
    
    public int GuardarEntProducto(){
        int id = 0;
        String con = null;
        int can = 0;
        int tip = 0;
        String fec = null;
        int est = 0;
        String pro = null;
        int tot = 0;
        int can_ant = 0;
        
        id = 0;
        con = espv.txtconcepto.getText();
        can = Integer.parseInt(espv.txtcan.getText());
        tip = 0;
        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        fec = sdf.format(dt);
        est = 1;
        String[] partPro= null;
        partPro = espv.tblpro.getValueAt(espv.tblpro.getSelectedRow(), 0).toString().split("'");
        pro = partPro[1];
        tot = Integer.parseInt(espv.tblpro.getValueAt(espv.tblpro.getSelectedRow(), 1).toString())+can;
        can_ant = Integer.parseInt(espv.tblpro.getValueAt(espv.tblpro.getSelectedRow(), 1).toString());
        
        espm.setId(id);
        espm.setCon(con);
        espm.setCan(can);
        espm.setTip(tip);
        espm.setFec(fec);
        espm.setEst(est);
        espm.setPro(pro);
        espm.setTot(tot);
        espm.setCant_ant(can_ant);
        
        if(espm.GuardarESProducto()){
            return 1;
        }
        return 0;
    }
    
    public int GuardarEntProducto1(){
        int id = 0;
        String con = null;
        int can = 0;
        int tip = 0;
        String fec = null;
        int est = 0;
        String pro = null;
        int tot = 0;
        int can_ant = 0;
        
        id = 0;
        con = espv.txtconcepto1.getText();
        can = Integer.parseInt(espv.txtcan1.getText());
        tip = 1;
        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        fec = sdf.format(dt);
        est = 1;
        String[] partPro= null;
        partPro = espv.tblpro1.getValueAt(espv.tblpro1.getSelectedRow(), 0).toString().split("'");
        pro = partPro[1];
        tot = Integer.parseInt(espv.tblpro1.getValueAt(espv.tblpro1.getSelectedRow(), 1).toString())-can;
        can_ant = Integer.parseInt(espv.tblpro1.getValueAt(espv.tblpro1.getSelectedRow(), 1).toString());
        
        espm.setId(id);
        espm.setCon(con);
        espm.setCan(can);
        espm.setTip(tip);
        espm.setFec(fec);
        espm.setEst(est);
        espm.setPro(pro);
        espm.setTot(tot);
        espm.setCant_ant(can_ant);
        
        if(espm.GuardarESProducto()){
            return 1;
        }
        return 0;
    }

    
}
