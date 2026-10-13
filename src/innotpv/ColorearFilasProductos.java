/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package innotpv;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author ChristianSepulveda
 */
public class ColorearFilasProductos extends DefaultTableCellRenderer {
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        String estatus = (String) table.getValueAt(row, 13);
        
        // Cambiar el color de fondo de la fila según el estatus
        if ("Inactivo".equals(estatus)) {
            setBackground(Color.decode("#d9534f")); // Rojo para "Inactivo"
        }else {
            setBackground(new Color(242, 242, 242)); // Blanco por defecto si no es ni "Activo" ni "Inactivo"
        }
        
        // Cambiar el color del texto si la fila está seleccionada
        if (isSelected && "Inactivo".equals(estatus)) {
            setForeground(Color.WHITE); // Texto blanco cuando está seleccionado
        }else if (isSelected && "Activo".equals(estatus)) {
            setBackground(Color.decode("#f0ad4e"));
        }else{
            setForeground(Color.BLACK);
        }
        
        // setBackground(Color.cyan);

        // Usa el renderer predeterminado para pintar las celdas normalmente
        /*Component c = table.getDefaultRenderer(Object.class).getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Accede al valor de la columna 'estatus' (asumiendo que es la columna 6 o índice 5)
        // Asegúrate de que el índice de la columna esté correctamente asignado (en este caso índice 6 para estatus)
        String estatus = (String) table.getValueAt(row, 6); // Cambia el índice si es necesario

        // Cambiar el color de fondo de la fila según el estatus
        if ("Activo".equals(estatus)) {
            c.setBackground(Color.GREEN); // Verde para "Activo"
        } else if ("Inactivo".equals(estatus)) {
            c.setBackground(Color.RED); // Rojo para "Inactivo"
        } else {
            c.setBackground(Color.WHITE); // Blanco por defecto si no es ni "Activo" ni "Inactivo"
        }

        // Cambiar el color del texto si la fila está seleccionada
        if (isSelected) {
            c.setForeground(Color.WHITE); // Texto blanco cuando está seleccionado
        } else {
            c.setForeground(Color.BLACK); // Texto negro por defecto
        }*/

        return this;
    }
    
}
