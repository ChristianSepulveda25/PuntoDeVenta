/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package innotpv;

/**
 *
 * @author ChristianSepulveda
 */
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BackupMySQL {

    /**
     * Ejecuta el respaldo mostrando un diálogo emergente con barra de progreso
     */
    public static void ejecutarRespaldoYCerrarSesion(JButton btnCerrarSesion, JFrame ventanaActual) {

        // 1. Deshabilitar botón
        btnCerrarSesion.setEnabled(false);

        // 2. Crear ventana emergente de carga (JDialog modal)
        JDialog dialogoCarga = crearDialogoCarga(ventanaActual);

        // 3. Crear e iniciar el hilo de trabajo
        new Thread(new Runnable() {
            @Override
            public void run() {

                // --- CONFIGURACIÓN DE TU BASE DE DATOS ---
                String host = "localhost";
                String puerto = "3306";
                String usuario = "root";
                String password = "root";
                String nombreBaseDatos = "pdv_copycentro";
                String carpetaDestino = "C:/Respaldos_Sistema/";
                String rutaMysqldump = "C:/Program Files/MySQL/MySQL Server 8.0/bin/mysqldump.exe";

                // Ejecutar el respaldo en segundo plano
                final boolean respaldoExitoso = generarBackupMySQL(
                        rutaMysqldump, host, puerto, usuario, password, nombreBaseDatos, carpetaDestino
                );

                // 4. Regresar al hilo de la interfaz para cerrar el diálogo y mostrar resultado
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        // Ocultar y destruir la ventana de carga
                        dialogoCarga.dispose();

                        if (respaldoExitoso) {
                            JOptionPane.showMessageDialog(
                                    ventanaActual,
                                    "Respaldo generado correctamente. Cerrando sesión...",
                                    "Éxito",
                                    JOptionPane.INFORMATION_MESSAGE
                            );
                        } else {
                            int opcion = JOptionPane.showConfirmDialog(
                                    ventanaActual,
                                    "Ocurrió un error al generar el respaldo.\n¿Deseas cerrar sesión de todos modos?",
                                    "Advertencia",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.WARNING_MESSAGE
                            );

                            if (opcion != JOptionPane.YES_OPTION) {
                                btnCerrarSesion.setEnabled(true);
                                return; // Cancela el cierre
                            }
                        }

                        // Cerrar la ventana principal
                        ventanaActual.dispose();
                        // new VentanaLogin().setVisible(true);
                    }
                });
            }
        }).start();

        // 5. Mostrar el diálogo (al ser modal, se queda visible hasta que el hilo lo destruya)
        dialogoCarga.setVisible(true);
    }

    /**
     * Construye un JDialog emergente con la barra de progreso
     */
    private static JDialog crearDialogoCarga(JFrame padre) {
        JDialog dialog = new JDialog(padre, "Procesando", true); // true = modal
        dialog.setUndecorated(true); // Quita marco y botones de minimizar/cerrar
        dialog.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        JLabel lblMensaje = new JLabel("Generando respaldo de la base de datos, por favor espere...", JLabel.CENTER);
        
        // Barra de progreso animada (indeterminada)
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);

        panel.add(lblMensaje, BorderLayout.NORTH);
        panel.add(progressBar, BorderLayout.CENTER);

        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(padre); // Centrar respecto a la ventana principal
        return dialog;
    }

    /**
     * Proceso nativo mysqldump
     */
    private static boolean generarBackupMySQL(String executablePath, String host, String puerto,
                                              String usuario, String password, String dbName, String destino) {
        try {
            File carpeta = new File(destino);
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            String fechaHora = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String nombreArchivo = String.format("respaldo_%s_%s.sql", dbName, fechaHora);
            File archivoSalida = new File(carpeta, nombreArchivo);

            File binario = new File(executablePath);
            String comandoBase = binario.exists() ? executablePath : "mysqldump";

            ProcessBuilder pb = new ProcessBuilder(
                    comandoBase,
                    "-h", host,
                    "-P", puerto,
                    "-u", usuario,
                    "-p" + password,
                    "--opt",
                    dbName
            );

            pb.redirectOutput(archivoSalida);

            Process proceso = pb.start();
            int codigoSalida = proceso.waitFor();

            return codigoSalida == 0;

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
}

