/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.EmpresaM;
import Vista.EmpresaV;
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
 * @author Christian Sepulveda
 */
public class EmpresaC implements ActionListener, KeyListener {
    private EmpresaV ev;
    private EmpresaM em;

    public EmpresaC(EmpresaM em, EmpresaV ev) {
        this.em = em;
        this.ev = ev;
        this.ev.btnguardar.addActionListener(this);
        this.ev.btnsalir.addActionListener(this);
        this.ev.txttexto.addKeyListener(this);
    }

    public void iniciar() {
        ev.setTitle("Datos de la Empresa");
        ImageIcon originalIcon  = new ImageIcon(getClass().getResource("/img/FondoCopyCentro72ppp.png"));
        Image image = originalIcon.getImage();
        Image scaledImage = image.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        this.ev.setFrameIcon(resizedIcon);
        ev.lblcar.setText("0");
        ev.txtid.setVisible(false);
        CargarEmpresa();
        ev.txtnom.requestFocus();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == ev.btnsalir){
            ev.dispose();
        }else if(e.getSource() == ev.btnguardar){
            if(ev.txtnom.getText().isEmpty()){
                JOptionPane.showMessageDialog(ev, "Campo Nombre Vacio");
                ev.txtnom.requestFocus();
            }else if(ev.txtcal.getText().isEmpty()){
                JOptionPane.showMessageDialog(ev, "Campo Calle Vacio");
                ev.txtcal.requestFocus();
            }else if(ev.txtnum.getText().isEmpty()){
                JOptionPane.showMessageDialog(ev, "Campo Número Vacio");
                ev.txtnum.requestFocus();
            }else if(ev.txtcol.getText().isEmpty()){
                JOptionPane.showMessageDialog(ev, "Campo Colonia Vacio");
                ev.txtcol.requestFocus();
            }else if(ev.txtcel.getText().isEmpty()){
                JOptionPane.showMessageDialog(ev, "Campo Celular Vacio");
                ev.txtcel.requestFocus();
            }else if(ev.txtest.getText().isEmpty()){
                JOptionPane.showMessageDialog(ev, "Campo Estado Vacio");
                ev.txtest.requestFocus();
            }else if(ev.txtmun.getText().isEmpty()){
                JOptionPane.showMessageDialog(ev, "Campo Municipio Vacio");
                ev.txtmun.requestFocus();
            }else if(ev.txttexto.getText().isEmpty()){
                JOptionPane.showMessageDialog(ev, "Campo Texto Vacio");
                ev.txttexto.requestFocus();
            }else if(ev.txttexto.getText().length() < 20){
                JOptionPane.showMessageDialog(ev, "Debe introducir al menos 20 caracteres en el Campo Texto Final para Ticket");
                ev.txttexto.requestFocus();
            }else{
                int a = 0;
                a = GuardarEmpresa();
                if(a <= 0){
                    JOptionPane.showMessageDialog(ev, "Registro Guardado");
                }else{
                    CargarEmpresa();
                    JOptionPane.showMessageDialog(ev, "Registro Guardado");
                }
            }
                
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getSource() == ev.txttexto){
            if (ev.txttexto.getText().length()== 250){
                e.consume();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getSource() == ev.txttexto){
            int car = 0;
            car = ev.txttexto.getText().length();
            ev.lblcar.setText(""+ (250 - car));
        }
    }
    
    public void CargarEmpresa(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        em.setId(-1);
        em.setNom(null);
        em.setCal(null);
        em.setNum(null);
        em.setCol(null);
        em.setTel(null);
        em.setCel(null);
        em.setMun(null);
        em.setEst(null);
        em.setTex(null);
        modelt = em.CargarEmpresa();
        if(modelt.getRowCount()<=0){
            
        }else{
            ev.txtid.setText(modelt.getValueAt(0, 0).toString());
            ev.txtnom.setText(modelt.getValueAt(0, 1).toString());
            ev.txtcal.setText(modelt.getValueAt(0, 2).toString());
            ev.txtnum.setText(modelt.getValueAt(0, 3).toString());
            ev.txtcol.setText(modelt.getValueAt(0, 4).toString());
            if(modelt.getValueAt(0, 5) == null){
                ev.txttel.setText("");
            }else{
                ev.txttel.setText(modelt.getValueAt(0, 5).toString());
            }
            ev.txtcel.setText(modelt.getValueAt(0, 6).toString());
            ev.txtmun.setText(modelt.getValueAt(0, 7).toString());
            ev.txtest.setText(modelt.getValueAt(0, 8).toString());
            ev.txttexto.setText(modelt.getValueAt(0, 9).toString());
            ContarCaracteres();
        }
    }
    
    public int GuardarEmpresa(){
        int id = 0;
        String nom = null;
        String cal = null;
        String num = null;
        String col = null;
        String tel = null;
        String cel = null;
        String mun = null;
        String est = null;
        String tex = null;
        
        if(ev.txtid.getText().isEmpty()){
            id = 0;
        }else{
            id = Integer.parseInt(ev.txtid.getText());
        }
        nom = ev.txtnom.getText();
        cal = ev.txtcal.getText();
        num = ev.txtnum.getText();
        col = ev.txtcol.getText();
        if(ev.txttel.getText().isEmpty()){
            tel = null;
        }else{
            tel = ev.txttel.getText();
        }
        cel = ev.txtcel.getText();
        mun = ev.txtmun.getText();
        est = ev.txtest.getText();
        tex = ev.txttexto.getText();
        
        em.setId(id);
        em.setNom(nom);
        em.setCal(cal);
        em.setNum(num);
        em.setCol(col);
        em.setTel(tel);
        em.setCel(cel);
        em.setMun(mun);
        em.setEst(est);
        em.setTex(tex);
        
        if(em.GuardarEmpresa()){
            return 1;
        }
        return 0;
    }
    
    public void ContarCaracteres(){
        int a = 0;
        int b = 0;
        a = ev.txttexto.getText().length();
        b = 250 - a;
        ev.lblcar.setText(""+b);
    }
    
}
