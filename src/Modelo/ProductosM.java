/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Christian Sepulveda
 */
public class ProductosM {
    public int id = 0;
    public String cod = null;
    public String nom = null;
    public double sa = 0;
    public int sm = 0;
    public int sx = 0;
    public double pre = 0;
    public double costo = 0;
    public String cat = null;
    public int est = 0;
    public int gr_ml = 0;
    public double mayoreo = 0;
    public double menudeo = 0;
    public String tipo = null;
    public int cantidadTipo = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getSa() {
        return sa;
    }

    public void setSa(double sa) {
        this.sa = sa;
    }

    public int getSm() {
        return sm;
    }

    public void setSm(int sm) {
        this.sm = sm;
    }

    public int getSx() {
        return sx;
    }

    public void setSx(int sx) {
        this.sx = sx;
    }

    public double getPre() {
        return pre;
    }

    public void setPre(double pre) {
        this.pre = pre;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public int getEst() {
        return est;
    }

    public void setEst(int est) {
        this.est = est;
    }

    public int getGr_ml() {
        return gr_ml;
    }

    public void setGr_ml(int gr_ml) {
        this.gr_ml = gr_ml;
    }

    public double getMayoreo() {
        return mayoreo;
    }

    public void setMayoreo(double mayoreo) {
        this.mayoreo = mayoreo;
    }

    public double getMenudeo() {
        return menudeo;
    }

    public void setMenudeo(double menudeo) {
        this.menudeo = menudeo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCantidadTipo() {
        return cantidadTipo;
    }

    public void setCantidadTipo(int cantidadTipo) {
        this.cantidadTipo = cantidadTipo;
    }
    
    public boolean GuardarProductos(){
       
       Conexion cc = new Conexion();
       java.sql.Connection cn = cc.getConexion();
       
       try {
            CallableStatement proc = cn.prepareCall("CALL GuardarProductos(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            proc.setInt(1, getId());
            proc.setString(2, getCod());
            proc.setString(3, getNom());
            proc.setDouble(4, getSa());
            proc.setInt(5, getSm());
            proc.setInt(6, getSx());
            proc.setDouble(7, getPre());
            proc.setString(8, getCat());
            proc.setInt(9, getEst());
            proc.setInt(10, getGr_ml());
            proc.setDouble(11, getCosto());
            proc.setDouble(12, getMayoreo());
            proc.setDouble(13, getMenudeo());
            proc.setString(14, getTipo());
            proc.setInt(15, getCantidadTipo());
            
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
    
    public DefaultTableModel CargarProductos(){
        Conexion cc = new Conexion();
        Connection cn = cc.getConexion();  
        DefaultTableModel modelocp = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        }; 
        
        try{
            Statement smt = cn.createStatement();
            ResultSet rs = null;
            rs = smt.executeQuery("CALL GuardarProductos("+getId()+",'"+getCod()+"','"+getNom()+"',"+getSa()+","+getSm()+","+getSx()+
                    ","+getPre()+",'"+getCat()+"',"+getEst()+","+getGr_ml()+","+getCosto()+",'"+getMayoreo()+"','"+getMenudeo()+
                    "','"+getTipo()+"',"+getCantidadTipo()+")");
            
            ResultSetMetaData md = rs.getMetaData();
            int columnas= md.getColumnCount();
            for (int i = 1; i <= columnas; i++) {
                modelocp.addColumn(md.getColumnLabel(i));
            }
            
            while(rs.next()){
                Object[] fila = new Object[columnas];
                for (int i = 0; i < columnas; i++) {fila[i]=rs.getObject(i+1);}
                modelocp.addRow(fila);
            }
        }catch(SQLException sqlex){
            System.out.println("Problema CargarDatos " + sqlex.getMessage() + "/" + sqlex.getErrorCode() + " Productos");
        }finally{
            try{
                cn.close();
            }catch(SQLException e){
                System.out.println(e);
            }
        }
        
        return modelocp;
    }
    
    public String ObtenerProductoCodigo(){
        Conexion cc = new Conexion();
        Connection cn = cc.getConexion();
        CallableStatement stmt = null;
        ResultSet rs = null;
        
        try {

            // Preparar la llamada al procedimiento almacenado
            String sql = "{CALL GuardarProductos(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            stmt = cn.prepareCall(sql);
            stmt.setInt(1, getId());
            stmt.setString(2, getCod());
            stmt.setString(3, getNom());
            stmt.setDouble(4, getSa());
            stmt.setInt(5, getSm());
            stmt.setInt(6, getSx());
            stmt.setDouble(7, getPre());
            stmt.setString(8, getCat());
            stmt.setInt(9, getEst());
            stmt.setInt(10, getGr_ml());
            stmt.setDouble(11, getCosto());
            stmt.setDouble(12, getMayoreo());
            stmt.setDouble(13, getMenudeo());
            stmt.setString(14, getTipo());
            stmt.setInt(15, getCantidadTipo());

            // Ejecutar el procedimiento almacenado y obtener el ResultSet
            rs = stmt.executeQuery();

            // Procesar el ResultSet
            StringBuilder result = new StringBuilder();
            while (rs.next()) {
                // Supongamos que tienes columnas 'id' y 'name' en tu tabla 'users'
                int id = rs.getInt("ID");
                String nombre = rs.getString("Nombre");
                Double precio = rs.getDouble("Precio");
                Double costo = rs.getDouble("Costo");
                result.append(id)
                        .append("|")
                        .append(nombre)
                        .append("|")
                        .append(precio)
                        .append("|")
                        .append(costo)
                        .append("|");
            }

            // Mostrar los resultados en un mensaje
            return result.toString();
        } catch (SQLException ex) {
            ex.printStackTrace();
            // JOptionPane.showMessageDialog(null, "Error al ejecutar el procedimiento: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            // Cerrar recursos
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (cn != null) cn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        
        return null;
    }
    
    public DefaultTableModel CargarProductosConParametros(int id, String cod, String nom, double sa, double sm, double sx, 
                                              double pre, String cat, int est, double grMl, double costo, 
                                              double mayoreo, double menudeo, String tipo, int cantidadTipo) {
        Conexion cc = new Conexion();
        Connection cn = cc.getConexion();  
        DefaultTableModel modelocp = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Las celdas no son editables
            }
        };

        try {
            // Crear la consulta SQL con los parámetros
            Statement smt = cn.createStatement();
            ResultSet rs = null;
            String query = String.format("CALL GuardarProductos(%d, '%s', '%s', %f, %f, %f, %f, '%s', %d, %f, %f, '%s', '%s', '%s', %d)",
                    id, cod, nom, sa, sm, sx, pre, cat, est, grMl, costo, mayoreo, menudeo, tipo, cantidadTipo);
            
            rs = smt.executeQuery(query);

            // Obtener los metadatos de las columnas
            ResultSetMetaData md = rs.getMetaData();
            int columnas = md.getColumnCount();

            // Definir las columnas explícitamente en función de los tipos
            for (int i = 1; i <= columnas; i++) {
                modelocp.addColumn(md.getColumnLabel(i)); // Agregar nombre de la columna
            }

            // Recorrer las filas del ResultSet y añadirlas al modelo
            while (rs.next()) {
                Object[] fila = new Object[columnas];
                for (int i = 0; i < columnas; i++) {
                    // Aquí puedes verificar el tipo de columna antes de agregar los valores
                    // Por ejemplo, convertir cadenas numéricas a Double, Integer, etc.
                    Object value = rs.getObject(i + 1);

                    // Definir tipos de datos esperados para cada columna
                    switch (i) {
                        case 0: // Columna 14: Integer (ID)
                            fila[i] = value != null ? Integer.parseInt(value.toString()) : 0;
                            break;
                        case 1: // Columna 1: String (Código de producto)
                            fila[i] = value != null ? value.toString() : "";
                            break;
                        case 2: // Columna 2: String (Nombre de producto)
                            fila[i] = value != null ? value.toString() : "";
                            break;
                        case 3: // Columna 3: Double (Stock actual)
                            fila[i] = value != null ? Double.parseDouble(value.toString()) : 0.0;
                            break;
                        case 4: // Columna 4: Double (Precio)
                            fila[i] = value != null ? Double.parseDouble(value.toString()) : 0.0;
                            break;
                        default:
                            fila[i] = value;
                            break;
                    }
                }
                modelocp.addRow(fila);
            }
        } catch (SQLException sqlex) {
            System.out.println("Problema CargarDatos " + sqlex.getMessage() + "/" + sqlex.getErrorCode() + " Productos");
        } finally {
            try {
                cn.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }

        return modelocp;
    }
    
}
