/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package innotpv;

import Controlador.*;
import Modelo.*;
import Vista.*;
import javax.swing.SwingUtilities;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import javax.swing.UIManager;
import org.jvnet.substance.SubstanceLookAndFeel;

/**
 *
 * @author Christian Sepulveda
 */
public class InnoTPV {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        
        SwingUtilities.invokeLater(() -> {
            LoginV vista = new LoginV();
            LoginM modelo = new LoginM();
            LoginC controlador = new LoginC(modelo, vista); // Modelo inicial vacío
            /*Principal.setDefaultLookAndFeelDecorated(true);
            SubstanceLookAndFeel.setSkin("org.jvnet.substance.skin.OfficeSilver2007Skin");*/
            vista.setVisible(true);
        });
    }
    
}
