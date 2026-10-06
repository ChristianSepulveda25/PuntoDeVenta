
package innotpv;

import java.awt.Image;
import javax.print.*;
import java.awt.print.*;
import java.io.*;
import java.nio.file.*;
import javax.swing.ImageIcon;

public class PrinterSelectionWithSave {

    private static final String CONFIG_FILE = "selected_printer.txt"; // Archivo de configuración

    public static void main(String[] args) {
        // Primero intentamos recuperar la impresora predeterminada guardada
        PrintService selectedPrinter = getSavedPrinter();

        if (selectedPrinter == null) {
            // System.out.println("No se encontró una impresora guardada. Se utilizará la predeterminada.");
            selectedPrinter = PrintServiceLookup.lookupDefaultPrintService();
        }

        // Mostrar la impresora que se está utilizando
        if (selectedPrinter != null) {
            // System.out.println("Impresora seleccionada: " + selectedPrinter.getName());
        } else {
            // System.out.println("No se ha encontrado ninguna impresora.");
            return;
        }

        // Aquí podrías configurar un PrintJob para imprimir en la impresora seleccionada
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        try {
            printerJob.setPrintService(selectedPrinter);
            printerJob.print();
            // System.out.println("El documento está siendo enviado a la impresora.");
        } catch (PrinterException e) {
            // System.out.println("Error al imprimir: " + e.getMessage());
        }

        // Para simular la selección de una nueva impresora, mostramos el diálogo
        if (printerJob.printDialog()) {
            PrintService newSelectedPrinter = printerJob.getPrintService();
            // System.out.println("Nueva impresora seleccionada: " + newSelectedPrinter.getName());

            // Guardar la impresora seleccionada
            savePrinter(newSelectedPrinter);
        }
    }

    // Método para guardar la impresora seleccionada
    public static void savePrinter(PrintService printer) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(CONFIG_FILE))) {
            writer.write(printer.getName());
            // System.out.println("Impresora guardada: " + printer.getName());
        } catch (IOException e) {
            // System.out.println("Error al guardar la impresora: " + e.getMessage());
        }
    }

    // Método para obtener la impresora guardada
    public static PrintService getSavedPrinter() {
        try {
            // Leer el nombre de la impresora del archivo de configuración
            String savedPrinterName = new String(Files.readAllBytes(Paths.get(CONFIG_FILE)));
            // System.out.println("Impresora guardada en archivo: " + savedPrinterName);

            // Obtener todas las impresoras disponibles
            PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
            for (PrintService service : printServices) {
                if (service.getName().equals(savedPrinterName)) {
                    return service; // Retorna la impresora guardada
                }
            }

        } catch (IOException e) {
            // System.out.println("No se pudo leer la impresora guardada: " + e.getMessage());
        }
        return null;
    }
}
