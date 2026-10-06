/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import java.awt.Dimension;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import Modelo.*;
import Controlador.*;
import innotpv.*;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import static javax.swing.UIManager.setLookAndFeel;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import org.jvnet.substance.SubstanceLookAndFeel;

/**
 *
 * @author Christian Sepulveda
 */
public class Principal extends javax.swing.JFrame {
    public VentasV vv;
    public VentasFrameV vfv;
    public CorteCajaV ccv;
    public CorteCajaUsuarioV ccuv;
    public CorteCajaGeneralV ccgv;
    public HistorialCorteCajaV hccv;
    public CatProV cpv;
    public ClientesV cv;
    public TamanosV tv;
    public HormasV hv;
    public ProductosV pv;
    // public ESProductoV espv;
    public EntradaSalidaProductosV espv;
    public EntSal_ProductosV espv_c;
    public ESEfectivoV esev;
    public EmpresaV ev;
    public DevolucionesV dv;
    public ProveedoresV prv;
    public AlmacenV av;
    public MovimientosAlmacenV mav;
    public InventarioV iv;
    public InventarioStockV isv;
    public R_VentasFechaCancelacionV rvcv;
    public R_VentasFechaRealizadoV rvrv;
    public R_ProductosMasVendidosV rpvv;
    public R_EntradaSalidaProductosV respv;
    public R_MovimientosProductosV rmpv;
    public R_UtilidadesVentasV ruvv;
    public EquivalenciaV eqv;
    public UsuariosV uv;
    public EntradasSalidasPExpressV espev;
    public ConfigBaseDatosV cbdv;
    public CambioUsuarioV cuv;
    public TotalesPorFechaV tpfv;
    public CambiarContrasenaV ccov;
    public PermisosEspecialesV pev;
    public String usuarioLogeado;
    public String nombreLogeado;
    /**
     * Creates new form Principal
     */
    public Principal() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setExtendedState(MAXIMIZED_BOTH);
        usuarioLogeado = GlobalVaribales.getInstance().usuarioLogueado;
        nombreLogeado = GlobalVaribales.getInstance().nombreLogueado;
        this.setTitle("Bienvenido al Punto de Venta - Usuario: " + nombreLogeado + " ( " + usuarioLogeado + " ).");
        getIconImage();
        inicio.setBorder(new ImagenFondo());
        // this.setIconImage(new ImageIcon("/img/LogoHelesa_Sistema.ico").getImage());
        // Image icon = new ImageIcon(getClass().getResource("/img/FondoPuntoVenta72ppp.png")).getImage();
        Image icon = new ImageIcon(getClass().getResource("/img/FondoCopyCentro72ppp.png")).getImage();
        setIconImage(icon);
        TimeZone.setDefault(TimeZone.getTimeZone("America/Mazatlan"));
    }

    public String getUsuarioLogeado() {
        return usuarioLogeado;
    }

    public void setUsuarioLogeado(String usuarioLogeado) {
        this.usuarioLogeado = usuarioLogeado;
    }
    
    public int VerificarCorteCaja(int corte, String fecha){
        Conexion cc = new Conexion();
        com.mysql.jdbc.Connection cn = cc.getConexion();
        int cant_reg = 0;
                    
        try{
            Statement ps = cn.createStatement();
            ResultSet rs;
            if(corte == 0){
                rs = ps.executeQuery("SELECT COUNT(id_cc) FROM corte_caja WHERE fec_inicio LIKE '%" + fecha + "%';");
            }else{
                rs = ps.executeQuery("SELECT COUNT(id_cc) FROM corte_caja WHERE fec_cierre LIKE '%" + fecha + "%';");
            }
            if(rs.next()){
                cant_reg = rs.getInt(1);
                return cant_reg;
            }
            return cant_reg;
        }catch(SQLException e){
            System.out.println(e);
            return -1;
        }finally{
            try{
                cn.close();
                //return cant_reg;
            }catch(SQLException e){
                System.out.println(e);
                return -1;
            }
        }
    }
    
    public static boolean estacerrado(Object obj){
        JInternalFrame[] activos;
        activos = inicio.getAllFrames();
        boolean cerrado=true;
        int i=0;
        while (i<activos.length && cerrado){
            if(activos[i]==obj){
                cerrado=false;
            }
            i++;
        }
        return cerrado;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        inicio = new javax.swing.JDesktopPane();
        jPanel1 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem22 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem17 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem27 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenu8 = new javax.swing.JMenu();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem24 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem23 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem25 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem26 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        inicio.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Accesos rápidos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 18))); // NOI18N
        jPanel1.setForeground(new java.awt.Color(204, 204, 204));

        jButton3.setBackground(new java.awt.Color(164, 200, 193));
        jButton3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton3.setText("Ventas");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(164, 200, 193));
        jButton4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton4.setText("Inventario");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        inicio.setLayer(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout inicioLayout = new javax.swing.GroupLayout(inicio);
        inicio.setLayout(inicioLayout);
        inicioLayout.setHorizontalGroup(
            inicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inicioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(653, Short.MAX_VALUE))
        );
        inicioLayout.setVerticalGroup(
            inicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inicioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(288, Short.MAX_VALUE))
        );

        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/catalogos.png"))); // NOI18N
        jMenu3.setText("Catálogos");
        jMenu3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jMenu3.setMargin(new java.awt.Insets(5, 0, 5, 0));

        jMenuItem2.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Vineta.png"))); // NOI18N
        jMenuItem2.setText("Productos");
        jMenuItem2.setMargin(new java.awt.Insets(5, 0, 5, 0));
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem2);

        jMenuItem1.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Vineta.png"))); // NOI18N
        jMenuItem1.setText("Almacén");
        jMenuItem1.setMargin(new java.awt.Insets(5, 0, 5, 0));
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem1);

        jMenuItem22.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jMenuItem22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Vineta.png"))); // NOI18N
        jMenuItem22.setText("Usuarios");
        jMenuItem22.setMargin(new java.awt.Insets(5, 0, 5, 0));
        jMenuItem22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem22ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem22);

        jMenuItem16.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jMenuItem16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Vineta.png"))); // NOI18N
        jMenuItem16.setText("Clientes");
        jMenuItem16.setMargin(new java.awt.Insets(5, 0, 5, 0));
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem16);

        jMenuBar1.add(jMenu3);

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/almacen.png"))); // NOI18N
        jMenu1.setText("Inventario");
        jMenu1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        jMenuItem6.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jMenuItem6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Vineta.png"))); // NOI18N
        jMenuItem6.setText("General");
        jMenuItem6.setMargin(new java.awt.Insets(5, 0, 5, 0));
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem6);

        jMenuItem7.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jMenuItem7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Vineta.png"))); // NOI18N
        jMenuItem7.setText("Stock min y max");
        jMenuItem7.setMargin(new java.awt.Insets(5, 0, 5, 0));
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem7);

        jMenuItem17.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jMenuItem17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Vineta.png"))); // NOI18N
        jMenuItem17.setText("Equivalencias");
        jMenuItem17.setMargin(new java.awt.Insets(5, 0, 5, 0));
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem17ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem17);

        jMenuBar1.add(jMenu1);

        jMenu4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/categorias.png"))); // NOI18N
        jMenu4.setText("Movimientos");
        jMenu4.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jMenu4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu4ActionPerformed(evt);
            }
        });

        jMenuItem8.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jMenuItem8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Vineta.png"))); // NOI18N
        jMenuItem8.setText("Efectivo");
        jMenuItem8.setMargin(new java.awt.Insets(5, 0, 5, 0));
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem8);

        jMenuItem9.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jMenuItem9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Vineta.png"))); // NOI18N
        jMenuItem9.setText("Productos");
        jMenuItem9.setMargin(new java.awt.Insets(5, 0, 5, 0));
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem9);

        jMenuItem27.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jMenuItem27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Vineta.png"))); // NOI18N
        jMenuItem27.setText("Movimientos Almacen");
        jMenuItem27.setMargin(new java.awt.Insets(5, 0, 5, 0));
        jMenuItem27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem27ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem27);

        jMenuBar1.add(jMenu4);

        jMenu5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/reportes.png"))); // NOI18N
        jMenu5.setText("Reportes");
        jMenu5.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N

        jMenu6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Vineta.png"))); // NOI18N
        jMenu6.setText("Ventas");
        jMenu6.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jMenu6.setMargin(new java.awt.Insets(5, 0, 5, 0));

        jMenuItem11.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jMenuItem11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Vineta.png"))); // NOI18N
        jMenuItem11.setText("Por fecha de venta");
        jMenuItem11.setMargin(new java.awt.Insets(5, 0, 5, 0));
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem11);

        jMenu5.add(jMenu6);

        jMenu7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Vineta.png"))); // NOI18N
        jMenu7.setText("Productos");
        jMenu7.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jMenu7.setMargin(new java.awt.Insets(5, 0, 5, 0));

        jMenuItem4.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Vineta.png"))); // NOI18N
        jMenuItem4.setText("Entradas y salidas de productos");
        jMenuItem4.setMargin(new java.awt.Insets(5, 0, 5, 0));
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem4);

        jMenu5.add(jMenu7);

        jMenuBar1.add(jMenu5);

        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/configuracion.png"))); // NOI18N
        jMenu2.setText("Configuración");
        jMenu2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jMenu2.setMargin(new java.awt.Insets(5, 0, 5, 0));
        jMenu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu2ActionPerformed(evt);
            }
        });

        jMenu8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Vineta.png"))); // NOI18N
        jMenu8.setText("Corte de Caja");
        jMenu8.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jMenu8.setMargin(new java.awt.Insets(5, 0, 5, 0));

        jMenuItem14.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jMenuItem14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Vineta.png"))); // NOI18N
        jMenuItem14.setText("General");
        jMenuItem14.setMargin(new java.awt.Insets(5, 0, 5, 0));
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem14);

        jMenuItem24.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jMenuItem24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Vineta.png"))); // NOI18N
        jMenuItem24.setText("Por Usuario");
        jMenuItem24.setMargin(new java.awt.Insets(5, 0, 5, 0));
        jMenuItem24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem24ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem24);

        jMenuItem12.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jMenuItem12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Vineta.png"))); // NOI18N
        jMenuItem12.setText("Totales por Fecha");
        jMenuItem12.setMargin(new java.awt.Insets(5, 0, 5, 0));
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem12);

        jMenu2.add(jMenu8);

        jMenuItem3.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Vineta.png"))); // NOI18N
        jMenuItem3.setText("Categorías de Productos");
        jMenuItem3.setInheritsPopupMenu(true);
        jMenuItem3.setMargin(new java.awt.Insets(5, 0, 5, 0));
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuItem23.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jMenuItem23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Vineta.png"))); // NOI18N
        jMenuItem23.setText("Seleccionar Impresora");
        jMenuItem23.setMargin(new java.awt.Insets(5, 0, 5, 0));
        jMenuItem23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem23ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem23);
        jMenu2.add(jSeparator2);

        jMenuItem10.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jMenuItem10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Vineta.png"))); // NOI18N
        jMenuItem10.setText("Datos de Empresa");
        jMenuItem10.setMargin(new java.awt.Insets(5, 0, 5, 0));
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem10);

        jMenuItem25.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jMenuItem25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Vineta.png"))); // NOI18N
        jMenuItem25.setText("Configuración BD");
        jMenuItem25.setMargin(new java.awt.Insets(5, 0, 5, 0));
        jMenuItem25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem25ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem25);

        jMenuItem15.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jMenuItem15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Vineta.png"))); // NOI18N
        jMenuItem15.setText("Permisos Especiales");
        jMenuItem15.setMargin(new java.awt.Insets(5, 0, 5, 0));
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem15);
        jMenu2.add(jSeparator1);

        jMenuItem26.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jMenuItem26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Vineta.png"))); // NOI18N
        jMenuItem26.setText("Cambiar Usuario");
        jMenuItem26.setMargin(new java.awt.Insets(5, 0, 5, 0));
        jMenuItem26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem26ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem26);

        jMenuItem13.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jMenuItem13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Vineta.png"))); // NOI18N
        jMenuItem13.setText("Cambiar Contraseña");
        jMenuItem13.setMargin(new java.awt.Insets(5, 0, 5, 0));
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem13);

        jMenuItem5.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Vineta.png"))); // NOI18N
        jMenuItem5.setText("Cerrar Sesión");
        jMenuItem5.setMargin(new java.awt.Insets(5, 0, 5, 0));
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem5);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(inicio)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(inicio)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        CatProM cpm = new CatProM();
        if(estacerrado(cpv)){
            cpv = new CatProV();
            inicio.add(cpv);
            CatProC cpc = new CatProC(cpm, cpv);
            Dimension desktopSize = inicio.getSize();
            Dimension FrameSize = cpv.getSize();
            cpv.setLocation((desktopSize.width - FrameSize.width)/2, (desktopSize.height - FrameSize.height)/2);
            cpv.show();
            cpc.iniciar();
        }else{
            JOptionPane.showMessageDialog(this, "La ventana ya se encuentra abierta!!!");
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        ProductosM pm = new ProductosM();
        if(estacerrado(pv)){
            pv = new ProductosV();
            inicio.add(pv);
            ProductosC pc = new ProductosC(pm, pv);
            Dimension desktopSize = inicio.getSize();
            Dimension FrameSize = pv.getSize();
            pv.setLocation((desktopSize.width - FrameSize.width)/2, (desktopSize.height - FrameSize.height)/2);
            pv.show();
            pc.iniciar();
        }else{
            JOptionPane.showMessageDialog(this, "La ventana ya se encuentra abierta!!!");
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        // TODO add your handling code here:
        EntradasSalidasPExpressM espem = new EntradasSalidasPExpressM();
        if(estacerrado(espev)){
            espev = new EntradasSalidasPExpressV();
            inicio.add(espev);
            EntradasSalidasPExpressC espec = new EntradasSalidasPExpressC(espem, espev);
            Dimension desktopSize = inicio.getSize();
            Dimension FrameSize = espev.getSize();
            espev.setLocation((desktopSize.width - FrameSize.width)/2, (desktopSize.height - FrameSize.height)/2);
            espev.show();
            espec.iniciar();
        }else{
            JOptionPane.showMessageDialog(this, "La ventana ya se encuentra abierta!!!");
        }
        
        /*EntradaSalidaProductosM espm = new EntradaSalidaProductosM();
        if(estacerrado(espv)){
            espv = new EntradaSalidaProductosV();
            inicio.add(espv);
            EntradaSalidaProductosC espc = new EntradaSalidaProductosC(espm, espv);
            Dimension desktopSize = inicio.getSize();
            Dimension FrameSize = espv.getSize();
            espv.setLocation((desktopSize.width - FrameSize.width)/2, (desktopSize.height - FrameSize.height)/2);
            espv.show();
            espc.iniciar();
        }else{
            JOptionPane.showMessageDialog(this, "La ventana ya se encuentra abierta!!!");
        }*/
        
        /*ESProductoM espm = new ESProductoM();
        if(estacerrado(espv)){
            espv = new ESProductoV();
            inicio.add(espv);
            ESProductoC espc = new ESProductoC(espm, espv);
            espv.show();
            try {
                espv.setMaximum(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
            espc.iniciar();
        }else{
            JOptionPane.showMessageDialog(this, "La ventana ya se encuentra abierta!!!");
        }*/
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenu4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu4ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        // TODO add your handling code here:
        EmpresaM em = new EmpresaM();
        if(estacerrado(ev)){
            ev = new EmpresaV();
            inicio.add(ev);
            EmpresaC ec = new EmpresaC(em, ev);
            Dimension desktopSize = inicio.getSize();
            Dimension FrameSize = ev.getSize();
            ev.setLocation((desktopSize.width - FrameSize.width)/2, (desktopSize.height - FrameSize.height)/2);
            ev.show();
            ec.iniciar();
        }else{
            JOptionPane.showMessageDialog(this, "La ventana ya se encuentra abierta!!!");
        }
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
        
        ESEfectivoM esem = new ESEfectivoM();
        if(estacerrado(esev)){
            esev = new ESEfectivoV();
            inicio.add(esev);
            ESEfectivoC esec = new ESEfectivoC(esem, esev);
            Dimension desktopSize = inicio.getSize();
            Dimension FrameSize = esev.getSize();
            esev.setLocation((desktopSize.width - FrameSize.width)/2, (desktopSize.height - FrameSize.height)/2);
            esev.show();
            esec.iniciar();
        }else{
            JOptionPane.showMessageDialog(this, "La ventana ya se encuentra abierta!!!");
        }
        
        /*int cant = 0;
        Date hoy= new Date();
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        String fecha = sdf.format(hoy);
        cant = VerificarCorteCaja(0, fecha);
        if(cant >= 1){
            cant = VerificarCorteCaja(1, fecha);
            if(cant >= 1){
                JOptionPane.showMessageDialog(this, "La Caja Ya Fue CERRADA!!!");
            }else{
                ESEfectivoM esem = new ESEfectivoM();
                if(estacerrado(esev)){
                    esev = new ESEfectivoV();
                    inicio.add(esev);
                    ESEfectivoC esec = new ESEfectivoC(esem, esev);
                    Dimension desktopSize = inicio.getSize();
                    Dimension FrameSize = esev.getSize();
                    esev.setLocation((desktopSize.width - FrameSize.width)/2, (desktopSize.height - FrameSize.height)/2);
                    esev.show();
                    esec.iniciar();
                }else{
                    JOptionPane.showMessageDialog(this, "La ventana ya se encuentra abierta!!!");
                }
            }
        }else{
            JOptionPane.showMessageDialog(this, "No Hay Un Corte De Caja ABIERTA!!!");
        }*/
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        // JOptionPane.showMessageDialog(vv, "Módulo en Desarrollo.");
        AlmacenM am = new AlmacenM();
        if(estacerrado(av)){
            av = new AlmacenV();
            inicio.add(av);
            AlmacenC ac = new AlmacenC(am, av);
            Dimension desktopSize = inicio.getSize();
            Dimension FrameSize = av.getSize();
            av.setLocation((desktopSize.width - FrameSize.width)/2, (desktopSize.height - FrameSize.height)/2);
            av.show();
            ac.iniciar();
        }else{
            JOptionPane.showMessageDialog(this, "La ventana ya se encuentra abierta!!!");
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        
        // Crear y mostrar el JFrame independiente
        /*VentasPruebasV frameIndependiente;
        frameIndependiente = new VentasPruebasV();
        frameIndependiente.setVisible(true); // Hacerlo visible*/
        
        // Establece el Look and Feel antes de crear cualquier componente Swing
        /*try {
            // Aquí puedes elegir el Look and Feel que desees. Ejemplo con Nimbus:
             // SubstanceLookAndFeel.setSkin("org.jvnet.substance.skin.CremeCoffeeSkin");
            
            // Si usas FlatLaf o cualquier otro Look and Feel, asegúrate de tener la librería correspondiente:
            // UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            // UIManager.setLookAndFeel("org.jvnet.substance.skin.CremeCoffeeSkin");
            // SubstanceLookAndFeel.setSkin("org.jvnet.substance.skin.CremeCoffeeSkin");  // Ejemplo de skin CremeCoffee
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        
        SwingUtilities.invokeLater(() -> {
            VentasFrameV vista = new VentasFrameV();
            VentasFrameM modelo = new VentasFrameM();
            VentasFrameC controlador = new VentasFrameC(modelo, vista); // Modelo inicial vacío
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            vista.setSize(screenSize);  // Establecer el tamaño de la ventana antes de maximizarla
            vista.setExtendedState(vfv.MAXIMIZED_BOTH);
            vista.setResizable(false);
            controlador.iniciar();
            vista.setVisible(true);
            
            /*LoginV vista = new LoginV();
            LoginM modelo = new LoginM();
            LoginC controlador = new LoginC(modelo, vista); // Modelo inicial vacío
            vista.setVisible(true);*/
        });
        
        this.dispose();
        
        /*String args[] = null;
        LoginV.main(args);
        this.dispose();*/
        
        /*vfv = new VentasFrameV();
        vfv.setVisible(true);*/
        // vfv.setUndecorated(true);
        // vfv.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        
        /*VentasFrameM vfm = new VentasFrameM();
        vfv = new VentasFrameV();
        vfv.setUndecorated(true);
        // inicio.add(vfv);
        VentasFrameC vfc = new VentasFrameC(vfm, vfv);
        // Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        vfv.setSize(screenSize);  // Establecer el tamaño de la ventana antes de maximizarla
        vfv.setExtendedState(vfv.MAXIMIZED_BOTH);
        // vfv.pack();
        vfv.setVisible(true);
        
        vfc.iniciar();
        
        this.dispose();*/
        
        /*
        VentasFrameM vfm = new VentasFrameM();
        vfv = new VentasFrameV();
        // inicio.add(vfv);
        VentasFrameC vfc = new VentasFrameC(vfm, vfv);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize(); 
        vfv.setSize(screenSize);
        vfv.setExtendedState(vfv.MAXIMIZED_BOTH);
        vfv.setVisible(true);
        
        vfc.iniciar();
        
        this.dispose();*/
        
        /*int cant = 0;
        Date hoy= new Date();
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        String fecha = sdf.format(hoy);
        cant = VerificarCorteCaja(0, fecha);
        if(cant >= 1){
            cant = VerificarCorteCaja(1, fecha);
            if(cant >= 1){
                JOptionPane.showMessageDialog(this, "La Caja Ya Fue CERRADA!!!");
            }else{
                VentasM vm = new VentasM();
                if(estacerrado(vv)){
                    vv = new VentasV();
                    inicio.add(vv);
                    VentasC vc = new VentasC(vm, vv);
                    vv.show();
                    try {
                        vv.setMaximum(true);
                    } catch (PropertyVetoException ex) {
                        Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    vc.iniciar();
                }else{
                    JOptionPane.showMessageDialog(this, "La ventana ya se encuentra abierta!!!");
                }
            }
        }else{
            JOptionPane.showMessageDialog(this, "No Hay Un Corte De Caja ABIERTA!!!");
        }*/
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        // JOptionPane.showMessageDialog(vv, "Módulo en Desarrollo.");
        
        InventarioM im = new InventarioM();
        if(estacerrado(iv)){
            iv = new InventarioV();
            inicio.add(iv);
            InventarioC ic = new InventarioC(im, iv);
            Dimension desktopSize = inicio.getSize();
            Dimension FrameSize = iv.getSize();
            iv.setLocation((desktopSize.width - FrameSize.width)/2, (desktopSize.height - FrameSize.height)/2);
            iv.show();
            ic.iniciar();
        }else{
            JOptionPane.showMessageDialog(this, "La ventana ya se encuentra abierta!!!");
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
        // JOptionPane.showMessageDialog(vv, "Módulo en Desarrollo.");
        
        InventarioM im = new InventarioM();
        if(estacerrado(iv)){
            iv = new InventarioV();
            inicio.add(iv);
            InventarioC ic = new InventarioC(im, iv);
            Dimension desktopSize = inicio.getSize();
            Dimension FrameSize = iv.getSize();
            iv.setLocation((desktopSize.width - FrameSize.width)/2, (desktopSize.height - FrameSize.height)/2);
            iv.show();
            ic.iniciar();
        }else{
            JOptionPane.showMessageDialog(this, "La ventana ya se encuentra abierta!!!");
        }
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
        // JOptionPane.showMessageDialog(vv, "Módulo en Desarrollo.");
        
        InventarioStockM ism = new InventarioStockM();
        if(estacerrado(isv)){
            isv = new InventarioStockV();
            inicio.add(isv);
            InventarioStockC isc = new InventarioStockC(ism, isv);
            Dimension desktopSize = inicio.getSize();
            Dimension FrameSize = isv.getSize();
            isv.setLocation((desktopSize.width - FrameSize.width)/2, (desktopSize.height - FrameSize.height)/2);
            isv.show();
            isc.iniciar();
        }else{
            JOptionPane.showMessageDialog(this, "La ventana ya se encuentra abierta!!!");
        }
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        // TODO add your handling code here:
        // JOptionPane.showMessageDialog(vv, "Módulo en Desarrollo.");
        
        R_VentasFechaRealizadoM rvrm = new R_VentasFechaRealizadoM();
        if(estacerrado(rvrv)){
            rvrv = new R_VentasFechaRealizadoV();
            inicio.add(rvrv);
            R_VentasFechaRealizadoC rvrc = new R_VentasFechaRealizadoC(rvrm, rvrv);
            Dimension desktopSize = inicio.getSize();
            Dimension FrameSize = rvrv.getSize();
            rvrv.setLocation((desktopSize.width - FrameSize.width)/2, (desktopSize.height - FrameSize.height)/2);
            rvrv.show();
            rvrc.iniciar();
        }else{
            JOptionPane.showMessageDialog(this, "La ventana ya se encuentra abierta!!!");
        }
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        // TODO add your handling code here:
        CorteCajaGeneralM ccgm = new CorteCajaGeneralM();
        if(estacerrado(ccgv)){
            ccgv = new CorteCajaGeneralV();
            inicio.add(ccgv);
            CorteCajaGeneralC ccgc = new CorteCajaGeneralC(ccgm, ccgv);
            Dimension desktopSize = inicio.getSize();
            Dimension FrameSize = ccgv.getSize();
            ccgv.setLocation((desktopSize.width - FrameSize.width)/2, (desktopSize.height - FrameSize.height)/2);
            ccgv.show();
            ccgc.iniciar();
        }else{
            JOptionPane.showMessageDialog(this, "La ventana ya se encuentra abierta!!!");
        }
        
        /* CORTE DE CAJA ANTERIOR
        CorteCajaM ccm = new CorteCajaM();
        if(estacerrado(ccv)){
            ccv = new CorteCajaV();
            inicio.add(ccv);
            CorteCajaC ccc = new CorteCajaC(ccm, ccv);
            Dimension desktopSize = inicio.getSize();
            Dimension FrameSize = ccv.getSize();
            ccv.setLocation((desktopSize.width - FrameSize.width)/2, (desktopSize.height - FrameSize.height)/2);
            ccv.show();
            ccc.iniciar();
        }else{
            JOptionPane.showMessageDialog(this, "La ventana ya se encuentra abierta!!!");
        }*/
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        // JOptionPane.showMessageDialog(vv, "Módulo en Desarrollo.");
        
        R_EntradaSalidaProductosM respm = new R_EntradaSalidaProductosM();
        if(estacerrado(respv)){
            respv = new R_EntradaSalidaProductosV();
            inicio.add(respv);
            R_EntradaSalidaProductosC respc = new R_EntradaSalidaProductosC(respm, respv);
            Dimension desktopSize = inicio.getSize();
            Dimension FrameSize = respv.getSize();
            respv.setLocation((desktopSize.width - FrameSize.width)/2, (desktopSize.height - FrameSize.height)/2);
            respv.show();
            respc.iniciar();
        }else{
            JOptionPane.showMessageDialog(this, "La ventana ya se encuentra abierta!!!");
        }
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu2ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        // System.exit(0);
        
        BackupMySQL.ejecutarRespaldoYCerrarSesion(jButton3, this);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem22ActionPerformed
        // TODO add your handling code here:
        UsuariosM um = new UsuariosM();
        if(estacerrado(uv)){
            uv = new UsuariosV();
            inicio.add(uv);
            UsuariosC cc = new UsuariosC(um, uv);
            Dimension desktopSize = inicio.getSize();
            Dimension FrameSize = uv.getSize();
            uv.setLocation((desktopSize.width - FrameSize.width)/2, (desktopSize.height - FrameSize.height)/2);
            uv.show();
            cc.iniciar();
        }else{
            JOptionPane.showMessageDialog(this, "La ventana ya se encuentra abierta!!!");
        }
    }//GEN-LAST:event_jMenuItem22ActionPerformed

    private void jMenuItem23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem23ActionPerformed
        // TODO add your handling code here:
        PrinterSelectionWithSave.main(null);
    }//GEN-LAST:event_jMenuItem23ActionPerformed

    private void jMenuItem24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem24ActionPerformed
        // TODO add your handling code here:
        CorteCajaUsuarioM ccum = new CorteCajaUsuarioM();
        if(estacerrado(ccuv)){
            ccuv = new CorteCajaUsuarioV();
            inicio.add(ccuv);
            CorteCajaUsuarioC ccuc = new CorteCajaUsuarioC(ccum, ccuv);
            Dimension desktopSize = inicio.getSize();
            Dimension FrameSize = ccuv.getSize();
            ccuv.setLocation((desktopSize.width - FrameSize.width)/2, (desktopSize.height - FrameSize.height)/2);
            ccuv.show();
            ccuc.iniciar();
        }else{
            JOptionPane.showMessageDialog(this, "La ventana ya se encuentra abierta!!!");
        }
    }//GEN-LAST:event_jMenuItem24ActionPerformed

    private void jMenuItem25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem25ActionPerformed
        // TODO add your handling code here:
        ConfigBaseDatosM cbdm = new ConfigBaseDatosM();
        if(estacerrado(cbdv)){
            cbdv = new ConfigBaseDatosV();
            inicio.add(cbdv);
            ConfigBaseDatosC cbdc = new ConfigBaseDatosC(cbdm, cbdv);
            Dimension desktopSize = inicio.getSize();
            Dimension FrameSize = cbdv.getSize();
            cbdv.setLocation((desktopSize.width - FrameSize.width)/2, (desktopSize.height - FrameSize.height)/2);
            cbdv.show();
            cbdc.iniciar();
        }else{
            JOptionPane.showMessageDialog(this, "La ventana ya se encuentra abierta!!!");
        }
    }//GEN-LAST:event_jMenuItem25ActionPerformed

    private void jMenuItem26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem26ActionPerformed
        // TODO add your handling code here:
        // JOptionPane.showMessageDialog(vv, "Módulo en Desarrollo.");
        CambioUsuarioM cum = new CambioUsuarioM();
        if(estacerrado(cuv)){
            cuv = new CambioUsuarioV();
            inicio.add(cuv);
            CambioUsuarioC cuc = new CambioUsuarioC(cum, cuv);
            Dimension desktopSize = inicio.getSize();
            Dimension FrameSize = cuv.getSize();
            cuv.setLocation((desktopSize.width - FrameSize.width)/2, (desktopSize.height - FrameSize.height)/2);
            cuv.show();
            cuc.iniciar();
        }else{
            JOptionPane.showMessageDialog(this, "La ventana ya se encuentra abierta!!!");
        }
    }//GEN-LAST:event_jMenuItem26ActionPerformed

    private void jMenuItem27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem27ActionPerformed
        // TODO add your handling code here:
        MovimientosAlmacenM mam = new MovimientosAlmacenM();
        if(estacerrado(mav)){
            mav = new MovimientosAlmacenV();
            inicio.add(mav);
            MovimientosAlmacenC mac = new MovimientosAlmacenC(mam, mav);
            Dimension desktopSize = inicio.getSize();
            Dimension FrameSize = mav.getSize();
            mav.setLocation((desktopSize.width - FrameSize.width)/2, (desktopSize.height - FrameSize.height)/2);
            mav.show();
            mac.iniciar();
        }else{
            JOptionPane.showMessageDialog(this, "La ventana ya se encuentra abierta!!!");
        }
    }//GEN-LAST:event_jMenuItem27ActionPerformed

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed
        // TODO add your handling code here:
        /*EquivalenciaM eqm = new EquivalenciaM();
        if(estacerrado(eqv)){
            eqv = new EquivalenciaV();
            inicio.add(eqv);
            EquivalenciaC eqc = new EquivalenciaC(eqm, eqv);
            Dimension desktopSize = inicio.getSize();
            Dimension FrameSize = eqv.getSize();
            eqv.setLocation((desktopSize.width - FrameSize.width)/2, (desktopSize.height - FrameSize.height)/2);
            eqv.show();
            eqc.iniciar();
        }else{
            JOptionPane.showMessageDialog(this, "La ventana ya se encuentra abierta!!!");
        }*/
    }//GEN-LAST:event_jMenu1ActionPerformed

    private void jMenuItem17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem17ActionPerformed
        // TODO add your handling code here:
        EquivalenciaM eqm = new EquivalenciaM();
        if(estacerrado(eqv)){
            eqv = new EquivalenciaV();
            inicio.add(eqv);
            EquivalenciaC eqc = new EquivalenciaC(eqm, eqv);
            Dimension desktopSize = inicio.getSize();
            Dimension FrameSize = eqv.getSize();
            eqv.setLocation((desktopSize.width - FrameSize.width)/2, (desktopSize.height - FrameSize.height)/2);
            eqv.show();
            eqc.iniciar();
        }else{
            JOptionPane.showMessageDialog(this, "La ventana ya se encuentra abierta!!!");
        }
    }//GEN-LAST:event_jMenuItem17ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        // TODO add your handling code here:
        TotalesPorFechaM tpfm = new TotalesPorFechaM();
        if(estacerrado(tpfv)){
            tpfv = new TotalesPorFechaV();
            inicio.add(tpfv);
            TotalesPorFechaC tpfc = new TotalesPorFechaC(tpfm, tpfv);
            Dimension desktopSize = inicio.getSize();
            Dimension FrameSize = tpfv.getSize();
            tpfv.setLocation((desktopSize.width - FrameSize.width)/2, (desktopSize.height - FrameSize.height)/2);
            tpfv.show();
            tpfc.iniciar();
        }else{
            JOptionPane.showMessageDialog(this, "La ventana ya se encuentra abierta!!!");
        }
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        // TODO add your handling code here:
        CambiarContrasenaM ccom = new CambiarContrasenaM();
        if(estacerrado(ccov)){
            ccov = new CambiarContrasenaV();
            inicio.add(ccov);
            CambiarContrasenaC ccoc = new CambiarContrasenaC(ccom, ccov);
            Dimension desktopSize = inicio.getSize();
            Dimension FrameSize = ccov.getSize();
            ccov.setLocation((desktopSize.width - FrameSize.width)/2, (desktopSize.height - FrameSize.height)/2);
            ccov.show();
            ccoc.iniciar();
        }else{
            JOptionPane.showMessageDialog(this, "La ventana ya se encuentra abierta!!!");
        }
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        // TODO add your handling code here:
        PermisosEspecialesM pem = new PermisosEspecialesM();
        if(estacerrado(pev)){
            pev = new PermisosEspecialesV();
            inicio.add(pev);
            PermisosEspecialesC pec = new PermisosEspecialesC(pem, pev);
            Dimension desktopSize = inicio.getSize();
            Dimension FrameSize = pev.getSize();
            pev.setLocation((desktopSize.width - FrameSize.width)/2, (desktopSize.height - FrameSize.height)/2);
            pev.show();
            pec.iniciar();
        }else{
            JOptionPane.showMessageDialog(this, "La ventana ya se encuentra abierta!!!");
        }
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
        // TODO add your handling code here:
        ClientesM cm = new ClientesM();
        if(estacerrado(cv)){
            cv = new ClientesV();
            inicio.add(cv);
            ClientesC cc = new ClientesC(cm, cv);
            Dimension desktopSize = inicio.getSize();
            Dimension FrameSize = cv.getSize();
            cv.setLocation((desktopSize.width - FrameSize.width)/2, (desktopSize.height - FrameSize.height)/2);
            cv.show();
            cc.iniciar();
        }else{
            JOptionPane.showMessageDialog(this, "La ventana ya se encuentra abierta!!!");
        }
    }//GEN-LAST:event_jMenuItem16ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Principal.setDefaultLookAndFeelDecorated(true);
                SubstanceLookAndFeel.setSkin("org.jvnet.substance.skin.CremeCoffeeSkin");
                //SubstanceLookAndFeel.setSkin("org.jvnet.substance.skin.CremeSkin");
                //SubstanceLookAndFeel.setCurrentTheme("org.jvnet.substance.theme.BusinessSkin");
                new Principal().setVisible(true);
                /*
                – org.jvnet.substance.skin.AutumnSkin
– org.jvnet.substance.skin.BusinessBlackSteelSkin
– org.jvnet.substance.skin.BusinessBlueSteelSkin
– org.jvnet.substance.skin.BusinessSkin
– org.jvnet.substance.skin.CremeCoffeeSkin
– org.jvnet.substance.skin.CremeSkin
– org.jvnet.substance.skin.EmeraldDuskSkin
– org.jvnet.substance.skin.FieldOfWheatSkin
– org.jvnet.substance.skin.FindingNemoSkin
– org.jvnet.substance.skin.GreenMagicSkin
– org.jvnet.substance.skin.MagmaSkin
– org.jvnet.substance.skin.MangoSkin
– org.jvnet.substance.skin.MistAquaSkin
– org.jvnet.substance.skin.ModerateSkin
– org.jvnet.substance.skin.NebulaBrickWallSkin
– org.jvnet.substance.skin.NebulaSkin
– org.jvnet.substance.skin.OfficeBlue2007Skin
– org.jvnet.substance.skin.OfficeSilver2007Skin
– org.jvnet.substance.skin.RavenGraphiteGlassSkin
– org.jvnet.substance.skin.RavenGraphiteSkin
– org.jvnet.substance.skin.RavenSkin
– org.jvnet.substance.skin.SaharaSkin
                
                =============================================================
                
                – org.jvnet.substance.theme.SubstanceAquaTheme
– org.jvnet.substance.theme.SubstanceBarbyPinkTheme
– org.jvnet.substance.theme.SubstanceBottleGreenTheme
– org.jvnet.substance.theme.SubstanceBrownTheme
– org.jvnet.substance.theme.SubstanceCharcoalTheme
– org.jvnet.substance.theme.SubstanceCremeTheme
– org.jvnet.substance.theme.SubstanceDarkVioletTheme
– org.jvnet.substance.theme.SubstanceDesertSandTheme
– org.jvnet.substance.theme.SubstanceEbonyTheme
– org.jvnet.substance.theme.SubstanceJadeForestTheme
– org.jvnet.substance.theme.SubstanceLightAquaTheme
– org.jvnet.substance.theme.SubstanceLimeGreenTheme
– org.jvnet.substance.theme.SubstanceNegatedTheme
– org.jvnet.substance.theme.SubstanceOliveTheme
– org.jvnet.substance.theme.SubstanceOrangeTheme
– org.jvnet.substance.theme.SubstancePurpleTheme
– org.jvnet.substance.theme.SubstanceRaspberryTheme
– org.jvnet.substance.theme.SubstanceSaturatedTheme
– org.jvnet.substance.theme.SubstanceSepiaTheme
– org.jvnet.substance.theme.SubstanceSteelBlueTheme
– org.jvnet.substance.theme.SubstanceSunGlareTheme
– org.jvnet.substance.theme.SubstanceSunsetTheme
– org.jvnet.substance.theme.SubstanceTerracottaTheme
                */
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JDesktopPane inicio;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem22;
    private javax.swing.JMenuItem jMenuItem23;
    private javax.swing.JMenuItem jMenuItem24;
    private javax.swing.JMenuItem jMenuItem25;
    private javax.swing.JMenuItem jMenuItem26;
    private javax.swing.JMenuItem jMenuItem27;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    // End of variables declaration//GEN-END:variables
}

