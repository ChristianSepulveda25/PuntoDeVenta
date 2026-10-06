package innotpv;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class ConfiguracionDB {

    private static final String CONFIG_FILE = "db_config.txt"; // Archivo de configuración
    private static final String SECRET_KEY = "1234567890123456"; // Clave secreta para encriptación (debe ser de 16 caracteres para AES-128)

    // Método para encriptar la contraseña usando AES
    public static String encriptarContraseña(String contraseña) throws Exception {
        SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = cipher.doFinal(contraseña.getBytes());
        return Base64.getEncoder().encodeToString(encrypted); // Convertimos el byte[] a String para guardarlo
    }

    // Método para desencriptar la contraseña usando AES
    public static String desencriptarContraseña(String contraseñaEncriptada) throws Exception {
        SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decoded = Base64.getDecoder().decode(contraseñaEncriptada);
        byte[] decrypted = cipher.doFinal(decoded);
        return new String(decrypted);
    }

    // Método para guardar la configuración en un archivo de texto
    public static void guardarConfiguracion(String ip, String usuario, String contraseña) throws Exception {
        // String contraseñaEncriptada = encriptarContraseña(contraseña);

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(CONFIG_FILE))) {
            writer.write("IP=" + ip);
            writer.newLine();
            writer.write("USUARIO=" + usuario);
            writer.newLine();
            writer.write("CONTRASEÑA=" + contraseña);
        }
    }

    // Método para leer la configuración desde el archivo de texto
    public static String[] leerConfiguracion() throws IOException, Exception {
        String ip = "";
        String usuario = "";
        String contraseñaEncriptada = "";

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(CONFIG_FILE))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.startsWith("IP=")) {
                    ip = linea.substring(3);
                } else if (linea.startsWith("USUARIO=")) {
                    usuario = linea.substring(8);
                } else if (linea.startsWith("CONTRASEÑA=")) {
                    contraseñaEncriptada = linea.substring(11);
                }
            }
        }

        // Desencriptar la contraseña antes de retornarla
        // String contraseña = desencriptarContraseña(contraseñaEncriptada);
        return new String[]{ip, usuario, contraseñaEncriptada};
    }
}
