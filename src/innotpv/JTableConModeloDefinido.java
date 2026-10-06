/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package innotpv;

import javax.swing.*;
import javax.swing.table.*;
import java.util.Comparator;

public class JTableConModeloDefinido {

    public static void main(String[] args) {
        // Crear el modelo de tabla
        DefaultTableModel modelt = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Las celdas no son editables
            }
        };
        
        // Añadir las columnas y las filas al modelo
        modelt.addColumn("ID");
        modelt.addColumn("Producto");
        modelt.addColumn("Descripción");
        modelt.addColumn("Precio");
        
        modelt.addRow(new Object[]{1, "Producto A", "Descripción A", 9.99});
        modelt.addRow(new Object[]{2, "Producto B", "Descripción B", 86.50});
        modelt.addRow(new Object[]{3, "Producto C", "Descripción C", 92.1});
        modelt.addRow(new Object[]{4, "Producto D", "Descripción D", 10.5});

        // Crear la tabla y asignar el modelo
        JTable tblBuscarProductos = new JTable(modelt);
        
        // Crear el TableRowSorter y asociarlo con el modelo de tabla
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(modelt);
        tblBuscarProductos.setRowSorter(sorter);

        // Agregar el comparador a la columna de precio (índice 3)
        sorter.setComparator(3, new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                // Asegurarse de que los valores sean Double
                if (o1 == null && o2 == null) return 0;
                if (o1 == null) return -1;
                if (o2 == null) return 1;

                Double val1 = (o1 instanceof Double) ? (Double) o1 : Double.parseDouble(o1.toString());
                Double val2 = (o2 instanceof Double) ? (Double) o2 : Double.parseDouble(o2.toString());

                // Imprimir para depuración
                System.out.println("Comparando: " + val1 + " vs " + val2);

                return val1.compareTo(val2);
            }
        });

        // Crear un JScrollPane para mostrar la tabla en un panel
        JScrollPane scrollPane = new JScrollPane(tblBuscarProductos);
        
        // Crear un JFrame para mostrar la tabla
        JFrame frame = new JFrame("Ordenar JTable por Precio");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(scrollPane);
        frame.setSize(500, 300);
        frame.setVisible(true);
    }
}
