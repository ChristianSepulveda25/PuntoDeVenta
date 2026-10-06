/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Christian Sepulveda
 */
public class VentasM {
    public int id_ven = 0;
    public String fol_ven = null;
    public double tot_ven = 0;
    public double can_ven = 0;
    public int des_ven = 0;
    public String fec_ent = null;
    public String dir_ent = null;
    public String cli_ent = null;
    public int met_pag = 0;
    public int tip_ven = 0;
    public String fec_reg = null;
    public int sta_ven = 0;
    public String ref_ven = null;
    public double subtot_ven = 0;
    public String fec_surtido = null;
    public String fec_cancela = null;
    public String usuarioRegistra = null;
    ///// DETALLE VENTA
    public int id = 0;
    public double tot = 0;
    public double can = 0;
    public int id_pro = 0;
    public String fol_dven = null;
    public String codigoProducto = null;

    public int getId_ven() {
        return id_ven;
    }

    public void setId_ven(int id_ven) {
        this.id_ven = id_ven;
    }

    public String getFol_ven() {
        return fol_ven;
    }

    public void setFol_ven(String fol_ven) {
        this.fol_ven = fol_ven;
    }

    public double getTot_ven() {
        return tot_ven;
    }

    public void setTot_ven(double tot_ven) {
        this.tot_ven = tot_ven;
    }

    public double getCan_ven() {
        return can_ven;
    }

    public void setCan_ven(double can_ven) {
        this.can_ven = can_ven;
    }

    public int getDes_ven() {
        return des_ven;
    }

    public void setDes_ven(int des_ven) {
        this.des_ven = des_ven;
    }

    public String getFec_ent() {
        return fec_ent;
    }

    public void setFec_ent(String fec_ent) {
        this.fec_ent = fec_ent;
    }

    public String getDir_ent() {
        return dir_ent;
    }

    public void setDir_ent(String dir_ent) {
        this.dir_ent = dir_ent;
    }

    public String getCli_ent() {
        return cli_ent;
    }

    public void setCli_ent(String cli_ent) {
        this.cli_ent = cli_ent;
    }

    public int getMet_pag() {
        return met_pag;
    }

    public void setMet_pag(int met_pag) {
        this.met_pag = met_pag;
    }

    public int getTip_ven() {
        return tip_ven;
    }

    public void setTip_ven(int tip_ven) {
        this.tip_ven = tip_ven;
    }

    public String getFec_reg() {
        return fec_reg;
    }

    public void setFec_reg(String fec_reg) {
        this.fec_reg = fec_reg;
    }

    public int getSta_ven() {
        return sta_ven;
    }

    public void setSta_ven(int sta_ven) {
        this.sta_ven = sta_ven;
    }

    public String getRef_ven() {
        return ref_ven;
    }

    public void setRef_ven(String ref_ven) {
        this.ref_ven = ref_ven;
    }

    public double getSubtot_ven() {
        return subtot_ven;
    }

    public void setSubtot_ven(double subtot_ven) {
        this.subtot_ven = subtot_ven;
    }

    public String getFec_surtido() {
        return fec_surtido;
    }

    public void setFec_surtido(String fec_surtido) {
        this.fec_surtido = fec_surtido;
    }

    public String getFec_cancela() {
        return fec_cancela;
    }

    public void setFec_cancela(String fec_cancela) {
        this.fec_cancela = fec_cancela;
    }

    public String getUsuarioRegistra() {
        return usuarioRegistra;
    }

    public void setUsuarioRegistra(String usuarioRegistra) {
        this.usuarioRegistra = usuarioRegistra;
    }
    
    ///////// DETALLE VENTA

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTot() {
        return tot;
    }

    public void setTot(double tot) {
        this.tot = tot;
    }

    public double getCan() {
        return can;
    }

    public void setCan(double can) {
        this.can = can;
    }

    public int getId_pro() {
        return id_pro;
    }

    public void setId_pro(int id_pro) {
        this.id_pro = id_pro;
    }

    public String getFol_dven() {
        return fol_dven;
    }

    public void setFol_dven(String fol_dven) {
        this.fol_dven = fol_dven;
    }
    
    public boolean GuardarVentaOrden(){
       
       Conexion cc = new Conexion();
       java.sql.Connection cn = cc.getConexion();
       
       try {
            CallableStatement proc = cn.prepareCall("CALL GuardarVentaOrden(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            proc.setInt(1, getId_ven());
            proc.setString(2, getFol_ven());
            proc.setDouble(3, getTot_ven());
            proc.setDouble(4, getCan_ven());
            proc.setInt(5, getDes_ven());
            proc.setInt(6, getMet_pag());
            proc.setInt(7, getTip_ven());
            proc.setString(8, getFec_reg());
            proc.setInt(9, getSta_ven());
            proc.registerOutParameter(10, Types.VARCHAR); 
            proc.setString(11, getRef_ven());
            proc.setDouble(12, getSubtot_ven());
            proc.setString(13, getFec_cancela());
            proc.setString(14, GlobalVaribales.getInstance().usuarioLogueado);
            
            proc.execute();
            
            String outputValue = proc.getString("folio");
            setFol_dven(outputValue);
            return true;
        }catch (SQLException e) {                  
            System.out.println(e);
            return false;
        }finally{
            try{
                cn.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        }
    }
    
    public boolean GuardarDetalleVenta(){
       
       Conexion cc = new Conexion();
       java.sql.Connection cn = cc.getConexion();
       
       try {
            CallableStatement proc = cn.prepareCall("CALL GuardarDetalleVenta(?,?,?,?,?)");
            proc.setInt(1, getId());
            proc.setDouble(2, getTot());
            proc.setDouble(3, getCan());
            proc.setInt(4, getId_pro());
            proc.setString(5, getFol_dven()); 
            
            proc.execute();
            
            return true;
        }catch (SQLException e) {                  
            System.out.println(e);
            return false;
        }finally{
            try{
                cn.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        }
    }
    
    public DefaultTableModel CargarDetalleVenta(){
        Conexion cc = new Conexion();
        Connection cn = cc.getConexion();  
        DefaultTableModel modelo_os = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        }; 
        
        try{
            
            Statement smt = cn.createStatement();
            ResultSet rs = null;
            rs = smt.executeQuery("CALL GuardarDetalleVenta("+getId()+","+getTot()+
                    ","+getCan()+","+getId_pro()+",'"+getFol_dven()+"')");
            
            ResultSetMetaData md = rs.getMetaData();
            int columnas= md.getColumnCount();
            for (int i = 1; i <= columnas; i++) {
                modelo_os.addColumn(md.getColumnLabel(i));
            }
            
            while(rs.next()){
                Object[] fila = new Object[columnas];
                for (int i = 0; i < columnas; i++) {fila[i]=rs.getObject(i+1);}
                modelo_os.addRow(fila);
            }
        }catch(SQLException sqlex){
            System.out.println("Problema CargarDatos " + sqlex.getMessage() + "/" + sqlex.getErrorCode() + " OrdenesServicio");
        }finally{
            try{
                cn.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        }
        
        return modelo_os;
    }
    
    public DefaultTableModel CargarOrdenesServicio(){
        Conexion cc = new Conexion();
        Connection cn = cc.getConexion();  
        DefaultTableModel modelo_os = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        }; 
        
        try{
            
            Statement smt = cn.createStatement();
            ResultSet rs = null;
            
            CallableStatement proc = cn.prepareCall("CALL GuardarVentaOrden(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            proc.setInt(1, getId_ven());
            proc.setString(2, getFol_ven());
            proc.setDouble(3, getTot_ven());
            proc.setDouble(4, getCan_ven());
            proc.setInt(5, getDes_ven());
            proc.setInt(6, getMet_pag());
            proc.setInt(7, getTip_ven());
            proc.setString(8, getFec_reg());
            proc.setInt(9, getSta_ven());
            proc.registerOutParameter(10, Types.VARCHAR); 
            proc.setString(11, getRef_ven());
            proc.setDouble(12, getSubtot_ven());
            proc.setString(13, getFec_cancela());
            proc.setString(14, GlobalVaribales.getInstance().usuarioLogueado);
            
            rs = proc.executeQuery();
            ResultSetMetaData md = rs.getMetaData();
            int columnas= md.getColumnCount();
            for (int i = 1; i <= columnas; i++) {
                modelo_os.addColumn(md.getColumnLabel(i));
            }
            
            while(rs.next()){
                Object[] fila = new Object[columnas];
                for (int i = 0; i < columnas; i++) {fila[i]=rs.getObject(i+1);}
                modelo_os.addRow(fila);
            }
        }catch(SQLException sqlex){
            System.out.println("Problema CargarDatos " + sqlex.getMessage() + "/" + sqlex.getErrorCode() + " OrdenesServicio");
        }finally{
            try{
                cn.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        }
        
        return modelo_os;
    }
    
}
