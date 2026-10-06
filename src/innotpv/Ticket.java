/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innotpv;

/**
 *
 * @author Christian Sepulveda
 */
// import br.com.adilson.util.PrinterMatrix;
import static com.sun.management.VMOption.Origin.CONFIG_FILE;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.event.PrintJobListener;
import javax.swing.JOptionPane;
import javax.print.*;
import java.awt.print.*;
import java.io.*;
import java.nio.file.*;

public class Ticket{
    static ArrayList<String> CabezaLineas=new ArrayList<String>();
    static ArrayList<String> subCabezaLineas=new ArrayList<String>();
    static ArrayList<String> items=new ArrayList<String>();
    static ArrayList<String> totales=new ArrayList<String>();
    static ArrayList<String> LineasPie=new ArrayList<String>();
    private static final String CONFIG_FILE = "selected_printer.txt"; // Archivo de configuración
    
    public static void AddCabecera(String line){
        CabezaLineas.add(line);
    }
    
    public static void AddSubCabecera(String line){
        subCabezaLineas.add(line);
    }
    
    public static void AddItem(String cantidad,String item,String price){
        OrderItem newItem = new OrderItem(' ');
        items.add(newItem.GeneraItem(cantidad,item, price));
    }
    
    public static void AddTotal(String name,String price){
        OrderTotal newTotal = new OrderTotal(' ');
        totales.add(newTotal.GeneraTotal(name, price));
    }
    
    public static void AddPieLinea(String line){
        LineasPie.add(line);
    }
    
    public static String DibujarLinea(int valor){
        String raya="";for(int x=0;x<valor;x++){raya+="=";}return raya;
    }
    
    public static String DarEspacio(){
        return "\n";
    }
    
    public static int ImprimirDocumento(boolean imprimir) throws IOException{
        String cadena=""+(char)27+(char)112+(char)0+(char)10+(char)100;
        if(imprimir){
            for(int cabecera=0;cabecera<CabezaLineas.size();cabecera++ ){
                cadena+=CabezaLineas.get(cabecera);
            }
            for(int subcabecera=0; subcabecera<subCabezaLineas.size(); subcabecera++){
                cadena+=subCabezaLineas.get(subcabecera);
            }
            for(int ITEM=0;ITEM<items.size();ITEM++){
                cadena+=items.get (ITEM);
            }
            for(int total=0;total<totales.size();total++){
                cadena+=totales.get(total);
            }
            for(int pie=0;pie<LineasPie.size();pie++){
                cadena+=LineasPie.get(pie);
            }
        }
        
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        // PrintService service = PrintServiceLookup.lookupDefaultPrintService();
        PrintService service = getSavedPrinter();
        DocPrintJob pj = service.createPrintJob();
        byte[]bytes = cadena.getBytes();
        Doc doc = new SimpleDoc(bytes, flavor,null);
        try{
            System.out.println(cadena);
            // pj.print(doc,null);
            
            return 1;
        }catch(Exception e){ 
            System.out.println("Error Imprimir: " + e);
            return 0;
        }finally{
            cadena = null;
            CabezaLineas.clear();
            subCabezaLineas.clear();
            items.clear();
            totales.clear();
            LineasPie.clear();
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
            System.out.println("No se pudo leer la impresora guardada: " + e.getMessage());
        }
        return null;
    }
    
}