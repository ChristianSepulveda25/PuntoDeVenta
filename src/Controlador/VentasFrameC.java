/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.FormasPagoM;
import Modelo.*;
import Vista.*;
import static Vista.Principal.estacerrado;
import static Vista.Principal.inicio;
import com.mxrck.autocompleter.TextAutoCompleter;
import com.toedter.calendar.JTextFieldDateEditor;
import innotpv.Ticket;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListDataListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.*;
// import br.com.adilson.util.PrinterMatrix;
// import br.com.adilson.util.Extenso;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.ListSelectionModel;
import javax.swing.*;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import org.jdesktop.swingx.JXDialog;

/**
 *
 * @author ChristianSepulveda
 */
public class VentasFrameC implements ActionListener, KeyListener, MouseListener, PropertyChangeListener, RowSorterListener{
    private VentasFrameM vm;
    private VentasFrameV vv;
    private Principal mainV;
    private ProductosM pm = new ProductosM();
    private EmpresaM em = new EmpresaM();
    private ESEfectivoM esem = new ESEfectivoM();
    private PendientesM pem = new PendientesM();
    private CotizacionM com = new CotizacionM();
    private CatProM cpm = new CatProM();
    private ClientesM cm = new ClientesM();
    private UsuariosM um = new UsuariosM();
    private CuentaPorCobrarM cpcm = new CuentaPorCobrarM();
    private PermisosEspecialesM pesm = new PermisosEspecialesM();
    private ClientesAbonosM cam = new ClientesAbonosM();
    TextAutoCompleter textAutoCompleter;
    TextAutoCompleter textAutoCompleter1;
    DefaultTableModel modeltLis;
    DefaultTableModel modeltLis1;
    DefaultTableModel modeltLis2;
    DefaultTableModel modeltLisCV;//modelo Para cancelar venta
    DefaultTableModel modeltLisCO;//modelo Para cancelar Orden
    DefaultTableModel c_modeltLisOS;//modelo Para Consulta Orden de Servicio
    DefaultTableModel c_modeltLisVE;//modelo Para Consulta Venta
    DefaultTableModel modeltTicketsActivos;//modelo Para Tickets Activos
    public int filaReem = 0;
    public String fech_reg_Tick = null;
    DefaultTableCellRenderer centrar = new DefaultTableCellRenderer();
    public String usuarioLogeado;
    public String nombreLogeado;
    DefaultTableModel modeltBuscarProductos = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Las celdas no son editables
            }
        };
    TableRowSorter<TableModel> sorter;
    public static final String PERMISO_IMPRIMIR_TICKET = "PREGUNTAR SI IMPRIME TICKET";
    public int estatusVenta = 1;
    public String nombreCliente_Pen = "";
    public String telefonoCliente_Pen = "";

    public VentasFrameC(VentasFrameM vm, VentasFrameV vv) {
        this.vm = vm;
        this.vv = vv;
        this.vv.btnsalir.addActionListener(this);
        this.vv.txtbus.addKeyListener(this);
        this.vv.btnmas.addActionListener(this);
        this.vv.btnmen.addActionListener(this);
        this.vv.btneli.addActionListener(this);
        this.vv.btnact.addActionListener(this);
        this.vv.btncobrar.addActionListener(this);
        this.vv.btnCobrarCredito.addActionListener(this);
        this.vv.txtdescuento.addKeyListener(this);
        this.vv.txtpago.addKeyListener(this);
        this.vv.cmbFormaPago.addKeyListener(this);
        //this.vv.txtdescuento1.addKeyListener(this);
        //this.vv.txtpago1.addKeyListener(this);
        //this.vv.btncobrarsinticket.addActionListener(this);
        //this.vv.btncobrarsinticket1.addActionListener(this);
        this.vv.btncobrarconticket.addActionListener(this);
        this.vv.btn_reimprimir_ticket.addActionListener(this);
        this.vv.btncobrarpendiente.addActionListener(this);
        //this.vv.btncobrarconticket1.addActionListener(this);
        //this.vv.rbtnorden.addActionListener(this);
        //this.vv.rbtnorden1.addActionListener(this);
        //this.vv.rbtnventa.addActionListener(this);
        //this.vv.rbtnventa1.addActionListener(this);
        this.vv.pescobrar.addMouseListener(this);
        this.vv.btnbus_ven.addActionListener(this);
        this.vv.btnexportar.addActionListener(this);
        this.vv.btnsalir_ven.addActionListener(this);
        this.vv.btnconsultar_ven.addActionListener(this);
        this.vv.btncancelar_ven.addActionListener(this);
        this.vv.pesprincipal.addMouseListener(this);
        this.vv.fechaDe.addPropertyChangeListener(this);
        this.vv.fechaDe.addPropertyChangeListener(this);
        // MODAL ENTRADAS SALIDAS EFECTIVO
        this.vv.btnGuardar_ESE.addActionListener(this);
        this.vv.btnCancelar_ESE.addActionListener(this);
        this.vv.dEntSalEfe.addKeyListener(this);
        this.vv.cmbTipo_ESE.addKeyListener(this);
        // this.vv.spinMonto_ESE.addKeyListener(this);
        this.vv.txtComentarios_ESE.addKeyListener(this);
        this.vv.chbImpComprobante_ESE.addKeyListener(this);
        this.vv.btnCancelar_ESE.addKeyListener(this);
        this.vv.btnGuardar_ESE.addKeyListener(this);
        this.vv.btnEntSalEfectivo.addActionListener(this);
        this.vv.jrbPrecio1.addKeyListener(this);
        this.vv.jrbPrecio2.addKeyListener(this);
        this.vv.jrbPrecio3.addKeyListener(this);
        this.vv.btnAplicarPrecio.addActionListener(this);
        this.vv.btnCancelarPrecio.addActionListener(this);
        this.vv.btnSalirBuscarP.addActionListener(this);
        this.vv.txtBuscarProductos.addKeyListener(this);
        this.vv.btnSeleccionarBuscarP.addActionListener(this);
        this.vv.btnSiImprimirTicket.addActionListener(this);
        this.vv.btnNoImprimirTicket.addActionListener(this);
        this.vv.btnSiImprimirTicket.addKeyListener(this);
        this.vv.btnNoImprimirTicket.addKeyListener(this);
        this.vv.btnDescuento.addActionListener(this);
        this.vv.btnAplicarDescuento.addActionListener(this);
        this.vv.btnCancelarAplicar.addActionListener(this);
        this.vv.pesAplicarDescuento.addMouseListener(this);
        this.vv.btnPendientes.addActionListener(this);
        this.vv.btnGuardarPendiente.addActionListener(this);
        this.vv.btnCancelarPendiente.addActionListener(this);
        this.vv.btnAbiertos.addActionListener(this);
        this.vv.btnSeleccionarAbiertos.addActionListener(this);
        this.vv.btnCancelarAbiertos.addActionListener(this);
        this.vv.btnListaCot.addActionListener(this);
        this.vv.btnSeleccionarListaCot.addActionListener(this);
        this.vv.btnCancelarListaCot.addActionListener(this);
        this.vv.tblAbiertos.addKeyListener(this);
        this.vv.tblListaCot.addKeyListener(this);
        this.vv.txtNombrePendiente.addKeyListener(this);
        this.vv.txtPorcentajeAplicar.addKeyListener(this);
        this.vv.txtImporteAplicar.addKeyListener(this);
        this.vv.btnCotizacion.addActionListener(this);
        this.vv.btnGuardarCotizacion.addActionListener(this);
        this.vv.btnCancelarCotizacion.addActionListener(this);
        this.vv.btnPreciosF8.addActionListener(this);
        this.vv.btnBuscarF10.addActionListener(this);
        this.vv.btnDevolverProducto.addActionListener(this);
        this.vv.btnSalirDetalleVenta.addActionListener(this);
        this.vv.btnBuscarDetalle.addActionListener(this);
        this.vv.btnSalirDetalle.addActionListener(this);
        this.vv.btnDevolverDetalle.addActionListener(this);
        this.vv.btnExportarDetalle.addActionListener(this);
        this.vv.txtdescuentoCredito.addKeyListener(this);
        this.vv.txtpagoCredito.addKeyListener(this);
        this.vv.btnBuscarCxC.addActionListener(this);
        this.vv.btnExportarCxC.addActionListener(this);
        this.vv.txtTextoBusqueda.addKeyListener(this);
        this.vv.btnCerrarCot.addActionListener(this);
        this.vv.txtNombreCotView.addMouseListener(this);
        this.vv.btnImprimirCotTicket.addActionListener(this);
        this.vv.btnImprimirCotPdf.addActionListener(this);
        this.vv.btnEliminarListaCot.addActionListener(this);
        this.vv.txtNombreCotizacion.addKeyListener(this);
        this.vv.btnAsignarCliente_ven.addActionListener(this);
        this.vv.cmb_nuevoCliente_AC.addKeyListener(this);
        this.vv.btn_guardar_AC.addActionListener(this);
        this.vv.btn_cancelar_AC.addActionListener(this);
        this.vv.btnRegistroAbonos.addActionListener(this);
        this.vv.btn_guardar_RA.addActionListener(this);
        this.vv.btn_cancelar_RA.addActionListener(this);
        this.vv.txt_cantidadAbono_RA.addKeyListener(this);
        this.vv.txt_comentarios_RA.addKeyListener(this);
        this.vv.btnSalirCreditos.addActionListener(this);
        this.vv.btnBuscarCreditos.addActionListener(this);
        this.vv.btnCancelarAbono.addActionListener(this);
        this.vv.btnImprimirAbono.addActionListener(this);
        this.vv.btnbus_pen.addActionListener(this);
        this.vv.btnexportar_pen.addActionListener(this);
        this.vv.btnsalir_pen.addActionListener(this);
        this.vv.btnconsultar_pen.addActionListener(this);
        this.vv.btnCompletarVenta_pen.addActionListener(this);
        this.vv.btncancelar_pen.addActionListener(this);
        this.vv.cmbFormaPago.addActionListener(this);
        
        vv.dAbonosCreditos.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                vv.txt_cantidadAbono_RA.requestFocusInWindow();
                vv.txt_cantidadAbono_RA.selectAll();
            }
        });
        Image icon = new ImageIcon(getClass().getResource("/img/FondoCopyCentro72ppp.png")).getImage();
        this.vv.setIconImage(icon);
        //vv.pescobrar.setIconAt(1, new ImageIcon("src/img/tarjeta.png"));
        // vv.pesprincipal.setIconAt(0, new ImageIcon("src/img/ventas.png"));
        ImageIcon iconTab0 = new ImageIcon(getClass().getResource("/img/ventas.png"));
        vv.pesprincipal.setIconAt(0, iconTab0);
        // vv.pesprincipal.setIconAt(1, new ImageIcon("src/img/CancelarVenta.png"));
        ImageIcon iconTab1 = new ImageIcon(getClass().getResource("/img/CancelarVenta.png"));
        vv.pesprincipal.setIconAt(1, iconTab1);
        // vv.pesprincipal.setIconAt(2, getClass().getResource("/img/CancelarVenta.png"));
        ImageIcon iconTab2 = new ImageIcon(getClass().getResource("/img/cancelaciones.png"));
        vv.pesprincipal.setIconAt(2, iconTab2);
        
        ImageIcon iconTabCreditos = new ImageIcon(getClass().getResource("/img/tarjeta.png"));
        vv.pesprincipal.setIconAt(4, iconTabCreditos);
        
        ImageIcon iconTabPendientes = new ImageIcon(getClass().getResource("/img/regoistro_personal.png"));
        vv.pesprincipal.setIconAt(5, iconTabPendientes);
        // vv.pesprincipal.setIconAt(1, new ImageIcon("src/img/CancelarVenta.png"));getClass().getResource("/img/CancelarVenta.png")
        // vv.pesprincipal.setIconAt(2, new ImageIcon("src/img/CancelarVenta.png"));
        // vv.pesAplicarDescuento.setIconAt(0, new ImageIcon("src/img/promociones.png"));
        ImageIcon iconTab3 = new ImageIcon(getClass().getResource("/img/promociones.png"));
        vv.pesAplicarDescuento.setIconAt(0, iconTab3);
        // vv.pesAplicarDescuento.setIconAt(1, new ImageIcon("src/img/regoistro_personal.png"));
        ImageIcon iconTab4 = new ImageIcon(getClass().getResource("/img/regoistro_personal.png"));
        vv.pesAplicarDescuento.setIconAt(1, iconTab4);
        // vv.pescobrar.setIconAt(0, new ImageIcon("src/img/efectivo.png"));
        ImageIcon iconTab5 = new ImageIcon(getClass().getResource("/img/efectivo.png"));
        vv.pescobrar.setIconAt(0, iconTab5);
    }

    public void iniciar() {
        usuarioLogeado = GlobalVaribales.getInstance().usuarioLogueado;
        nombreLogeado = GlobalVaribales.getInstance().nombreLogueado;
        vv.setTitle("Bienvenido al Punto de Venta - Usuario: " + nombreLogeado + " ( " + usuarioLogeado + " ).");
        
        vv.pesprincipal.removeTabAt(3);
        vv.pescobrar.removeTabAt(1);
        
        // vv.setUndecorated(true);
        // vv.setVisible(true);
        vv.txtbus.setFocusable(true);
        vv.pnlcobro.setVisible(false);
        vv.btncobrarconticket.setVisible(true);
        //AutoCompleteDecorator.decorate(vv.cmbbus);
        CargarProductos();
        CargarLista();
        CargarCatPro();
        CargarUsuarios();
        vv.tbllis.getTableHeader().setReorderingAllowed(false) ;
        vv.tbllis.getTableHeader().setResizingAllowed(false); 
        vv.tbllis_ven.getTableHeader().setReorderingAllowed(false) ;
        // vv.tbllis_ven.getTableHeader().setResizingAllowed(false);
        vv.c_tbllis_ve.getTableHeader().setReorderingAllowed(false) ;
        vv.tbllis.getTableHeader().setFont(new Font("Arial", 1, 14));
        
        /*Ocultas botones pendientes*/
        vv.btnAbiertos.setVisible(false);
        vv.btnPendientes.setVisible(false);
        /*vv.pescobrar.setIconAt(0, new ImageIcon("src/img/efectivo.png"));
        //vv.pescobrar.setIconAt(1, new ImageIcon("src/img/tarjeta.png"));
        vv.pesprincipal.setIconAt(0, new ImageIcon("src/img/ventas.png"));
        vv.pesprincipal.setIconAt(1, new ImageIcon("src/img/CancelarVenta.png"));
        vv.pesAplicarDescuento.setIconAt(0, new ImageIcon("src/img/promociones.png"));
        vv.pesAplicarDescuento.setIconAt(1, new ImageIcon("src/img/regoistro_personal.png"));*/
        java.util.Date tuFecha = new java.util.Date();
        java.util.Date primerDiaAnio = java.util.Date.from(
            java.time.LocalDate.now()
                .withDayOfYear(1)
                .atStartOfDay(java.time.ZoneId.systemDefault())
                .toInstant()
        );
        vv.fechaDe.setDate(tuFecha);
        vv.fechaAl.setDate(tuFecha);
        vv.fechaDeDetalle.setDate(tuFecha);
        vv.fechaAlDetalle.setDate(tuFecha);
        vv.fechaDeCxC.setDate(tuFecha);
        vv.fechaAlCxC.setDate(tuFecha);
        vv.fechaDeCreditos.setDate(tuFecha);
        vv.fechaAlCreditos.setDate(tuFecha);
        vv.fechaDe_pen.setDate(primerDiaAnio);
        vv.fechaAl_pen.setDate(tuFecha);
        JTextFieldDateEditor dateEditor_de = (JTextFieldDateEditor)vv.fechaDe.getDateEditor();
        dateEditor_de.setHorizontalAlignment(JTextField.CENTER);
        dateEditor_de.setEnabled(false);
        JTextFieldDateEditor dateEditor_al = (JTextFieldDateEditor)vv.fechaAl.getDateEditor();
        dateEditor_al.setHorizontalAlignment(JTextField.CENTER);
        dateEditor_al.setEnabled(false);
        // DETALLE
        JTextFieldDateEditor dateEditor_deDetalle = (JTextFieldDateEditor)vv.fechaDeDetalle.getDateEditor();
        dateEditor_deDetalle.setHorizontalAlignment(JTextField.CENTER);
        dateEditor_deDetalle.setEnabled(false);
        JTextFieldDateEditor dateEditor_alDetalle = (JTextFieldDateEditor)vv.fechaAlDetalle.getDateEditor();
        dateEditor_alDetalle.setHorizontalAlignment(JTextField.CENTER);
        dateEditor_alDetalle.setEnabled(false);
        // CxC
        JTextFieldDateEditor dateEditor_deCxC = (JTextFieldDateEditor)vv.fechaDeCxC.getDateEditor();
        dateEditor_deCxC.setHorizontalAlignment(JTextField.CENTER);
        dateEditor_deCxC.setEnabled(false);
        JTextFieldDateEditor dateEditor_alCxC = (JTextFieldDateEditor)vv.fechaAlCxC.getDateEditor();
        dateEditor_alCxC.setHorizontalAlignment(JTextField.CENTER);
        dateEditor_alCxC.setEnabled(false);
        // Creditos
        JTextFieldDateEditor fechaDeCreditos = (JTextFieldDateEditor)vv.fechaDeCreditos.getDateEditor();
        fechaDeCreditos.setHorizontalAlignment(JTextField.CENTER);
        fechaDeCreditos.setEnabled(false);
        JTextFieldDateEditor fechaAlCreditos = (JTextFieldDateEditor)vv.fechaAlCreditos.getDateEditor();
        fechaAlCreditos.setHorizontalAlignment(JTextField.CENTER);
        fechaAlCreditos.setEnabled(false);
        // Pendientes
        JTextFieldDateEditor fechaDePendientes = (JTextFieldDateEditor)vv.fechaDe_pen.getDateEditor();
        fechaDePendientes.setHorizontalAlignment(JTextField.CENTER);
        fechaDePendientes.setEnabled(false);
        JTextFieldDateEditor fechaAlPendientes = (JTextFieldDateEditor)vv.fechaAl_pen.getDateEditor();
        fechaAlPendientes.setHorizontalAlignment(JTextField.CENTER);
        fechaAlPendientes.setEnabled(false);
        
        vv.txt_idVenta_AC.setVisible(false);
        vv.txt_idCliente_AC.setVisible(false);
        vv.txt_idCliente_RA.setVisible(false);
        
        // Inicializar CMD Forma de Pago Busqueda
        vv.cmbfiltro_metodopago1.addItem(new FormasPagoM(0, "Todos"));
        vv.cmbfiltro_metodopago1.addItem(new FormasPagoM(1, "Efectivo"));
        vv.cmbfiltro_metodopago1.addItem(new FormasPagoM(2, "Tarjeta"));
        vv.cmbfiltro_metodopago1.addItem(new FormasPagoM(3, "Transferencia"));
        
        // Placeholder
        vv.txtTextoBus_Cancelar.putClientProperty("JTextField.placeholderText", "Folio");
        
        /*
        FormaPago formaPago = (FormaPago) cmbFormaPago.getSelectedItem();
        int idFormaPago = formaPago.getId();
        */
        
        //vv.btnact.setVisible(false);
        vv.txtbus.requestFocus();
        //vv.pesprincipal.setIconAt(2, new ImageIcon("src/img/CancelarVenta.png"));
        //vv.pesprincipal.setIconAt(3, new ImageIcon("src/img/CancelarOrden.png"));
        /*vv.rbtnventa.setSelected(true);
        vv.rbtnventa1.setSelected(true);
        vv.txtcliente.setEnabled(false);
        vv.txtcliente1.setEnabled(false);
        vv.spnfechor.setEnabled(false);
        vv.spnfechor1.setEnabled(false);
        vv.txtdomicilio.setEnabled(false);
        vv.txtdomicilio1.setEnabled(false);*/
        //ORDEN DE SERVICIO
        /*java.util.Date tuFecha = new java.util.Date();
        vv.dtc_de.setDate(tuFecha);
        vv.dtc_al.setDate(tuFecha);
        JTextFieldDateEditor dateEditor_de = (JTextFieldDateEditor)vv.dtc_de.getDateEditor();
        dateEditor_de.setHorizontalAlignment(JTextField.CENTER);
        dateEditor_de.setEnabled(false);
        JTextFieldDateEditor dateEditor_al = (JTextFieldDateEditor)vv.dtc_al.getDateEditor();
        dateEditor_al.setHorizontalAlignment(JTextField.CENTER);
        dateEditor_al.setEnabled(false);*/
        // CANCELACIONES
        //vv.txtbus.requestFocus();
        /*java.util.Date tuFecha1 = new java.util.Date();
        vv.dtc_de_ord.setDate(tuFecha1);
        vv.dtc_al_ord.setDate(tuFecha1);
        JTextFieldDateEditor dateEditor_de1 = (JTextFieldDateEditor)vv.dtc_de_ord.getDateEditor();
        dateEditor_de1.setHorizontalAlignment(JTextField.CENTER);
        dateEditor_de1.setEnabled(false);
        JTextFieldDateEditor dateEditor_al1 = (JTextFieldDateEditor)vv.dtc_al_ord.getDateEditor();
        dateEditor_al1.setHorizontalAlignment(JTextField.CENTER);
        dateEditor_al1.setEnabled(false);*/
        
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) vv.spinMonto_ESE.getEditor();
        editor.getTextField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Verificar si se presionó la tecla Enter
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    cerrarModal(vv.dEntSalEfe);
                }else if ( e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_F12 ) {
                    try {
                        guardarEntradasSalidasEfectivo();
                    } catch (IOException ex) {
                        Logger.getLogger(VentasC.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
        // Este código se asegurará de que la tabla ordene por precio cuando se haga clic en el encabezado
        vv.tblBuscarProductos.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Detectar en qué columna se hizo clic (en este caso columna 3 para precio)
                int column = vv.tblBuscarProductos.columnAtPoint(evt.getPoint());
                if (column == 3) {  // Solo ordenar cuando se hace clic en la columna de precios
                    sorter.sort();  // Ordenar las filas
                }
            }
        });
        
        // 
        vv.tbllisClientesCreditos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Detectar en qué columna se hizo clic (en este caso columna 3 para precio)
                int fila = vv.tbllisClientesCreditos.getSelectedRow();
                // System.out.println("Punto: " + fila);
                int idCliente = 0;
                idCliente = Integer.parseInt(vv.tbllisClientesCreditos.getValueAt(fila, 0).toString());
                CargarMovimientosCreditos(idCliente);
            }
        });
        
        // Configuración de la tabla
        /*vv.tblBuscarProductos.getTableHeader().setReorderingAllowed(false);  // Deshabilitar mover columnas
        
        // Configuración de la tabla para escuchar clics en el encabezado
        vv.tblBuscarProductos.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 1) {
                    System.out.println("Clic en encabezado");
                    // Ordenar por la columna de precio cuando se haga clic en el encabezado
                    ordenarPorPrecio();
                }
            }
        });*/
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if( e.getSource() == vv.btnCompletarVenta_pen ){
            int fila = 0;
            fila = vv.tbllis_pen.getSelectedRow();
            if(fila < 0){
                JOptionPane.showMessageDialog(vv, "Selecione un Registro");
            }else{
                // Crear un arreglo de botones personalizados
                Object[] opciones = {"Confirmar", "No"};

                // Mostrar el mensaje de confirmación con los botones personalizados
                int respuesta = JOptionPane.showOptionDialog(
                    vv, 
                    "¿Completar venta pendiente?", 
                    "Confirmación", 
                    JOptionPane.DEFAULT_OPTION, 
                    JOptionPane.QUESTION_MESSAGE, 
                    null, 
                    opciones, 
                    opciones[0] // Establecer "Confirmar" como opción predeterminada
                );

                // Procesar la respuesta
                if (respuesta == 0) {
                    int res = 0;
                    res = CompletarVentaPendiente(fila);
                    if(res == 1){
                        CargarVentasPendientes();
                        JOptionPane.showMessageDialog(vv, "Venta Pendiente Completada");
                    }else{
                        JOptionPane.showMessageDialog(vv, "Error al Cancelar Venta, Contacte a Soporte Técnio");
                    }
                }
            }
        }else if( e.getSource() == vv.btncancelar_pen ){
            int fila = 0;
            fila = vv.tbllis_pen.getSelectedRow();
            if(fila < 0){
                JOptionPane.showMessageDialog(vv, "Selecione un Registro");
            }else{
                String estatus = null;
                estatus = vv.tbllis_pen.getValueAt(fila, 7).toString();
                if(estatus.equals("Cancelada")){
                    JOptionPane.showMessageDialog(vv, "La venta ya fue cancelada!!!");
                }else{
                    Object[] options = {"Si", "No"};
                    int resGuardar = JOptionPane.showOptionDialog(null, "Desea cancelar la venta pendiente?", "Guardar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null, options, options[0]);
                    if( resGuardar == 0 ){
                        int a = 0;
                        a = ActualizarEstatus_PEN(fila);
                        if(a == 0){
                            JOptionPane.showMessageDialog(vv, "Error al Cancelar Venta, Contacte a Soporte Técnio");
                        }else{
                            RegresarStockVenta_PEN(fila);
                            CargarVentasPendientes();
                            JOptionPane.showMessageDialog(vv, "Venta Pendiente Cancelada");
                        }
                    }
                }
            }
        }else if( e.getSource() == vv.btnbus_pen ){
            CargarVentasPendientes();
        }else if( e.getSource() == vv.btnexportar_pen ){
            int filas = vv.tbllis_pen.getRowCount();
            if( filas > 0 ){
                ExportarExcel obj;
                try {
                    obj = new ExportarExcel();
                    obj.exportarExcel(vv.tbllis_pen);
                }catch(IOException ex) {
                    System.out.println("Error: " + ex);
                }
            }else{
                JOptionPane.showMessageDialog(vv, "No tienes datos para exportar");
            }
        }else if( e.getSource() == vv.btnsalir_pen ){
            vv.dispose();
            String args[] = null;
            Principal.main(args);
        }else if( e.getSource() == vv.btnconsultar_pen ){
                CargarModalDetalleVenta_Pen();
        }else if( e.getSource() == vv.btnImprimirAbono ){
            int fila = vv.tbllisCreditosMovimientos.getSelectedRow();
            if( fila < 0 ){
                JOptionPane.showMessageDialog(vv, "Seleccione un movimiento");
                return;
            }
            
            Object valor = vv.tbllisCreditosMovimientos.getValueAt(fila, 0);
            if (valor == null) {
                // System.out.println("No se puede imprimir una fila de totales");
                return;
            }
            
            int idVenta = 0;
            idVenta = Integer.parseInt(vv.tbllisCreditosMovimientos.getValueAt(fila, 1).toString());
            if( idVenta > 0 ){
                JOptionPane.showMessageDialog(vv, "El movimiento no puede ser imprimir ya que viene de una venta.");
                return;
            }
            
            String nombreCliente;
            double abono;
            String formaPago;
            String comentarios;
            
            int filaCliente = vv.tbllisClientesCreditos.getSelectedRow();
            nombreCliente = vv.tbllisClientesCreditos.getValueAt(filaCliente, 1).toString();
            abono = Double.parseDouble(vv.tbllisCreditosMovimientos.getValueAt(fila,4).toString());
            formaPago = vv.tbllisCreditosMovimientos.getValueAt(fila,7).toString();
            comentarios = vv.tbllisCreditosMovimientos.getValueAt(fila,6).toString();
            
            try {
                imprimirAbono(nombreCliente, abono, formaPago, comentarios);
            } catch (IOException ex) {
                Logger.getLogger(VentasFrameC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if( e.getSource() == vv.btnCancelarAbono ){
            int fila = vv.tbllisCreditosMovimientos.getSelectedRow();
            if( fila < 0 ){
                JOptionPane.showMessageDialog(vv, "Seleccione un movimiento");
                return;
            }
            
            int idVenta = 0;
            idVenta = Integer.parseInt(vv.tbllisCreditosMovimientos.getValueAt(fila, 1).toString());
            if( idVenta > 0 ){
                JOptionPane.showMessageDialog(vv, "El movimiento no puede ser eliminado ya que viene de una venta.");
                return;
            }
            
            // Crear un arreglo de botones personalizados
            Object[] opciones = {"Confirmar", "No"};

            // Mostrar el mensaje de confirmación con los botones personalizados
            int respuesta = JOptionPane.showOptionDialog(
                vv, 
                "¿Cancelar el abono?", 
                "Confirmación", 
                JOptionPane.DEFAULT_OPTION, 
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                opciones, 
                opciones[0] // Establecer "Confirmar" como opción predeterminada
            );

            // Procesar la respuesta
            if (respuesta == 0) {
                CancelarMovimientoAbonoCargo(fila);
            }
            
        }else if( e.getSource() == vv.btnBuscarCreditos ){
            int fila = vv.tbllisClientesCreditos.getSelectedRow();
            if( fila < 0 ){
                JOptionPane.showMessageDialog(vv, "Seleccione un cliente");
                return;
            }
            int idCliente = 0;
            idCliente = Integer.parseInt(vv.tbllisClientesCreditos.getValueAt(fila, 0).toString());
            CargarMovimientosCreditos(idCliente);
        }else if( e.getSource() == vv.btn_guardar_RA ){
            GuardarAbonoCredito();
        }else if( e.getSource() == vv.btn_cancelar_RA ){
            cerrarModal(vv.dAbonosCreditos);
        }else if( e.getSource() == vv.btnRegistroAbonos ){
            
            int fila = 0;
            fila = vv.tbllisClientesCreditos.getSelectedRow();
            if(fila < 0){
                JOptionPane.showMessageDialog(vv, "Seleccione un Cliente");
            }else{
                int idCliente = 0;
                String nombreCliente = null;
                
                idCliente = Integer.parseInt(vv.tbllisClientesCreditos.getValueAt(fila, 0).toString());
                nombreCliente = vv.tbllisClientesCreditos.getValueAt(fila, 1).toString();
                
                abrirModalAbonosCreditos(idCliente, nombreCliente);
            }
        }else if( e.getSource() == vv.btnSalirCreditos ){
            vv.dispose();
            String args[] = null;
            Principal.main(args);
        }else if( e.getSource() == vv.btn_guardar_AC ){
            GuardarAsignarCliente();
        }else if( e.getSource() == vv.btn_cancelar_AC ){
            cerrarModal(vv.dAsignarCliente);
        }else if( e.getSource() == vv.btnAsignarCliente_ven ){
            // AsignarClienteVenta(int idCliente, int idVenta)
            int fila = 0;
            fila = vv.tbllis_ven.getSelectedRow();
            if(fila < 0){
                JOptionPane.showMessageDialog(vv, "Seleccione un Registro");
            }else{
                int idCliente = 0;
                int idVenta = 0;
                String nombreCliente = null;
                
                idCliente = Integer.parseInt(vv.tbllis_ven.getValueAt(fila, 11).toString());
                idVenta = Integer.parseInt(vv.tbllis_ven.getValueAt(fila, 0).toString());
                nombreCliente = vv.tbllis_ven.getValueAt(fila, 10).toString();
                
                AsignarClienteVenta(idCliente, idVenta, nombreCliente);
            }
        }else if( e.getSource() == vv.btnEliminarListaCot ){
            int fila = -1;
            fila = vv.tblListaCot.getSelectedRow();
            if( fila >= 0 ){
                String nombreCotizacion = null;
                nombreCotizacion = vv.tblListaCot.getValueAt(fila, 0).toString();
                
                // Crear un arreglo de botones personalizados
                Object[] opciones = {"Confirmar", "No"};

                // Mostrar el mensaje de confirmación con los botones personalizados
                int respuesta = JOptionPane.showOptionDialog(
                    vv, 
                    "¿Eliminar Cotización "+nombreCotizacion+"?", 
                    "Confirmación", 
                    JOptionPane.DEFAULT_OPTION, 
                    JOptionPane.QUESTION_MESSAGE, 
                    null, 
                    opciones, 
                    opciones[0] // Establecer "Confirmar" como opción predeterminada
                );

                // Procesar la respuesta
                if (respuesta == 0) {
                    CancelarListaCot(nombreCotizacion);
                    try {
                        CargarListaCot();
                        
                        if(vv.tblListaCot.getRowCount() > 0)
                            vv.tblListaCot.setRowSelectionInterval(0, 0);
                        
                        vv.tblListaCot.requestFocus();
                        
                    } catch (SQLException ex) {
                        Logger.getLogger(VentasFrameC.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }else if( e.getSource() == vv.btnImprimirCotTicket ){
            String nombreCotizacion = null;
            nombreCotizacion = vv.txtNombreCotView.getText();
            if( !nombreCotizacion.equals("")){
                // FinalizarVenta(0);
                String nombrePendiente = null;
                nombrePendiente = vv.txtNombreCotView.getText();
                
                int errores = 0;
                errores = GuardarCotizacion(nombrePendiente);
                if(errores == 0){
                    
                    try {
                        imprimirTicketCotizacion(true);
                    } catch (IOException ex) {
                        Logger.getLogger(VentasFrameC.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
            
            /*try {
                imprimirTicketCotizacion(true);
            } catch (IOException ex) {
                Logger.getLogger(VentasFrameC.class.getName()).log(Level.SEVERE, null, ex);
            }*/
        }else if( e.getSource() == vv.btnImprimirCotPdf ){
            String nombreCotizacion = null;
            nombreCotizacion = vv.txtNombreCotView.getText();
            if( !nombreCotizacion.equals("")){
                // FinalizarVenta(0);
                String nombrePendiente = null;
                nombrePendiente = vv.txtNombreCotView.getText();
                
                int errores = 0;
                errores = GuardarCotizacion(nombrePendiente);
                if(errores == 0){
                    
                    String url = "http://localhost/CopyCentro/Reportes/R_FormatoCotización.php?a="+nombrePendiente;
                    try {
                        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
                    } catch (IOException ex) {
                        Logger.getLogger(R_VentasFechaRealizadoC.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        }else if( e.getSource() == vv.btnCerrarCot ){
            String nombreCotizacion = null;
            nombreCotizacion = vv.txtNombreCotView.getText();
            if( !nombreCotizacion.equals("")){
                // FinalizarVenta(0);
                String nombrePendiente = null;
                nombrePendiente = vv.txtNombreCotView.getText();
                
                int errores = 0;
                errores = GuardarCotizacion(nombrePendiente);
                if(errores == 0){
                    JOptionPane.showMessageDialog(vv, "Cotización Guardada Correctamente.");
                    cerrarModal(vv.dCotizaciones);

                    FinalizarVenta(0);

                }
            }
        }else if(e.getSource() == vv.btnSalirDetalle){
            vv.dispose();
            String args[] = null;
            Principal.main(args);
        }else if(e.getSource() == vv.btnDevolverDetalle){
            int fila = -1;
            fila = vv.tbllisDetalle.getSelectedRow();
            if(fila < 0){
                JOptionPane.showMessageDialog(vv, "Seleccione un producto del detalle.");
            }else{
                String codigo = null;
                String nombre = null;
                String folio = null;
                String estatus = null;
                double cantidad = 0.0;
                
                codigo = vv.tbllisDetalle.getValueAt(fila, 2).toString();
                nombre = vv.tbllisDetalle.getValueAt(fila, 3).toString();
                folio = vv.tbllisDetalle.getValueAt(fila, 0).toString();
                estatus = vv.tbllisDetalle.getValueAt(fila, 10).toString();
                cantidad = Double.parseDouble(vv.tbllisDetalle.getValueAt(fila, 4).toString());
                
                if( estatus.equals("Devuelto") ){
                    JOptionPane.showMessageDialog(vv, "El producto ya fue devuelto.");
                    return;
                }
                
                // Crear un arreglo de botones personalizados
                Object[] opciones = {"Confirmar", "No"};

                // Mostrar el mensaje de confirmación con los botones personalizados
                int respuesta = JOptionPane.showOptionDialog(
                    vv, 
                    "¿Confirmar la devolucion del producto "+nombre+"?", 
                    "Confirmación", 
                    JOptionPane.DEFAULT_OPTION, 
                    JOptionPane.QUESTION_MESSAGE, 
                    null, 
                    opciones, 
                    opciones[0] // Establecer "Confirmar" como opción predeterminada
                );

                // Procesar la respuesta
                if (respuesta == 0) {  // El índice de "Confirmar" es 0
                    
                    // Mostrar un cuadro de entrada para que el usuario ingrese una cantidad
                    String input = (String) JOptionPane.showInputDialog(vv, "Cantidad a devolver:", "Solicitar Cantidad", JOptionPane.QUESTION_MESSAGE,null,null,cantidad);

                    // Verificar si el usuario canceló (input será null si se cancela)
                    if (input != null) {
                        try {
                            // Intentar convertir la entrada a un número
                            double cantidadIngresada = Double.parseDouble(input);

                            // Verificar si la cantidad es válida (puedes poner condiciones adicionales)
                            if (cantidadIngresada > 0) {
                                // JOptionPane.showMessageDialog(null, "Has ingresado la cantidad: " + cantidadIngresada);
                                
                                if( cantidadIngresada > cantidad ){
                                    JOptionPane.showMessageDialog(null, "La cantidad a devolver debe ser igual o menor a " + cantidad);
                                    return;
                                }
                                
                                int respuestaDevolver = 0; 
                                respuestaDevolver = devolverDetalleProducto(codigo, folio, cantidadIngresada);

                                if(respuestaDevolver == 1){
                                    JOptionPane.showMessageDialog(vv, "Producto Devuelto Correctamente.");
                                    // CargarConsultaDetalle2(folio);
                                    CargarDetalleVentas();
                                    /*cerrarModal(vv.dConsultarVE);
                                    CargarModalDetalleVenta();*/
                                }else{
                                    JOptionPane.showMessageDialog(vv, "Ocurrió un error al devolver el producto.");
                                }
                                
                            } else {
                                JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor que cero.");
                            }
                        } catch (NumberFormatException ex) {
                            // Si no se puede convertir la entrada a número, mostrar un error
                            JOptionPane.showMessageDialog(null, "Por favor, ingresa un número válido.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Operación cancelada.");
                    }
                }
                
            }
        }else if(e.getSource() == vv.btnBuscarDetalle){
            CargarDetalleVentas();
        }else if(e.getSource() == vv.btnBuscarCxC){
            CargarCuentasPorCobrar();
        }else if(e.getSource() == vv.btnBuscarF10){
            abrirModalBuscarProductos();
        }else if(e.getSource() == vv.btnSalirDetalleVenta){
            cerrarModal(vv.dConsultarVE);
            CargarVentasCancelar();
        }else if( e.getSource() == vv.btnDevolverProducto ){
            int fila = -1;
            fila = vv.c_tbllis_ve.getSelectedRow();
            if(fila < 0){
                JOptionPane.showMessageDialog(vv, "Seleccione un producto de la venta.");
            }else{
                String codigo = null;
                String nombre = null;
                String folio = null;
                String estatus = null;
                double cantidad = 0.0;
                
                codigo = vv.c_tbllis_ve.getValueAt(fila, 0).toString();
                nombre = vv.c_tbllis_ve.getValueAt(fila, 1).toString();
                folio = vv.c_folio_ve.getText();
                estatus = vv.c_tbllis_ve.getValueAt(fila, 5).toString();
                cantidad = Double.parseDouble(vv.c_tbllis_ve.getValueAt(fila, 3).toString());
                
                if( estatus.equals("Devuelto") ){
                    JOptionPane.showMessageDialog(vv, "El producto ya fue devuelto.");
                    return;
                }
                
                // Crear un arreglo de botones personalizados
                Object[] opciones = {"Confirmar", "No"};

                // Mostrar el mensaje de confirmación con los botones personalizados
                int respuesta = JOptionPane.showOptionDialog(
                    vv, 
                    "¿Confirmar la devolucion del producto "+nombre+"?", 
                    "Confirmación", 
                    JOptionPane.DEFAULT_OPTION, 
                    JOptionPane.QUESTION_MESSAGE, 
                    null, 
                    opciones, 
                    opciones[0] // Establecer "Confirmar" como opción predeterminada
                );

                // Procesar la respuesta
                if (respuesta == 0) {  // El índice de "Confirmar" es 0
                    
                    // Mostrar un cuadro de entrada para que el usuario ingrese una cantidad
                    String input = (String) JOptionPane.showInputDialog(vv, "Cantidad a devolver:", "Solicitar Cantidad", JOptionPane.QUESTION_MESSAGE,null,null,cantidad);

                    // Verificar si el usuario canceló (input será null si se cancela)
                    if (input != null) {
                        try {
                            // Intentar convertir la entrada a un número
                            double cantidadIngresada = Double.parseDouble(input);

                            // Verificar si la cantidad es válida (puedes poner condiciones adicionales)
                            if (cantidadIngresada > 0) {
                                // JOptionPane.showMessageDialog(null, "Has ingresado la cantidad: " + cantidadIngresada);
                                
                                if( cantidadIngresada > cantidad ){
                                    JOptionPane.showMessageDialog(null, "La cantidad a devolver debe ser igual o menor a " + cantidad);
                                    return;
                                }
                                
                                int respuestaDevolver = 0; 
                                respuestaDevolver = devolverDetalleProducto(codigo, folio, cantidadIngresada);

                                if(respuestaDevolver == 1){
                                    JOptionPane.showMessageDialog(vv, "Producto Devuelto Correctamente.");
                                    // CargarConsultaDetalle2(folio);
                                    cerrarModal(vv.dConsultarVE);
                                    CargarModalDetalleVenta();
                                }else{
                                    JOptionPane.showMessageDialog(vv, "Ocurrió un error al devolver el producto.");
                                }
                                
                            } else {
                                JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor que cero.");
                            }
                        } catch (NumberFormatException ex) {
                            // Si no se puede convertir la entrada a número, mostrar un error
                            JOptionPane.showMessageDialog(null, "Por favor, ingresa un número válido.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Operación cancelada.");
                    }
                }
                
            }
        }else if(e.getSource() == vv.btnCotizacion){
            /*JOptionPane.showMessageDialog(vv, "Módulo en Desarrollo.");
            vv.txtbus.requestFocus();*/
            
            int filas = 0;
            filas = vv.tbllis.getRowCount();
            if(filas <= 0){
                JOptionPane.showMessageDialog(vv, "Ingrese al menos un producto.");
                vv.txtbus.requestFocus();
            }else{
                abrirModalCotizacion();
            }
        }else if(e.getSource() == vv.btnPreciosF8){
            int fila = 0;
            fila = vv.tbllis.getSelectedRow();
            if(fila < 0){
                JOptionPane.showMessageDialog(vv, "Seleccione un Registro");
            }else{
                int id = 0;
                id = Integer.parseInt(vv.tbllis.getValueAt(fila, 0).toString());
                abrirModalPrecios(id);
            }
        }else if(e.getSource() == vv.btnSeleccionarAbiertos){
            String nombrePendiente = null;
            int fila = 0;
            fila = vv.tblAbiertos.getSelectedRow();
            nombrePendiente = vv.tblAbiertos.getValueAt(fila, 0).toString();
            CargarDatosTicketActivo(nombrePendiente);
        }else if(e.getSource() == vv.btnCancelarAbiertos){
            cerrarModal(vv.dAbiertos);
        }else if(e.getSource() == vv.btnAbiertos){
            abrirModalAbiertos();
        }else if(e.getSource() == vv.btnSeleccionarListaCot){
            String nombreCotizacion = null;
            int fila = 0;
            fila = vv.tblListaCot.getSelectedRow();
            nombreCotizacion = vv.tblListaCot.getValueAt(fila, 0).toString();
            CargarDatosListaCot(nombreCotizacion);
        }else if(e.getSource() == vv.btnCancelarListaCot){
            cerrarModal(vv.dListaCot);
        }else if(e.getSource() == vv.btnListaCot){
            /* JOptionPane.showMessageDialog(vv, "Módulo en Desarrollo.");
            vv.txtbus.requestFocus();*/
            abrirModalListaCot();
        }else if(e.getSource() == vv.btnCancelarPendiente){
            cerrarModal(vv.dPendientes);
        }else if(e.getSource() == vv.btnCancelarCotizacion){
            cerrarModal(vv.dCotizaciones);
        }else if(e.getSource() == vv.btnGuardarPendiente){
            String nombrePendiente = null;
            nombrePendiente = vv.txtNombrePendiente.getText();
            
            if( nombrePendiente.equals("") ){
                JOptionPane.showMessageDialog(vv, "Ingrese el nombre del ticket pendiente");
                vv.txtNombrePendiente.requestFocus();
            }else{
                int errores = 0;
                errores = GuardarTicketPendiente(nombrePendiente);
                if(errores == 0){
                    JOptionPane.showMessageDialog(vv, "Ticket Pendiente Guardado Correctamente.");
                    cerrarModal(vv.dPendientes);
                    
                    FinalizarVenta(0);
                    
                }
            }
        }else if(e.getSource() == vv.btnGuardarCotizacion){
            String nombrePendiente = null;
            nombrePendiente = vv.txtNombreCotizacion.getText();
            
            if( nombrePendiente.equals("") ){
                JOptionPane.showMessageDialog(vv, "Ingrese el nombre de la cotización");
                vv.txtNombreCotizacion.requestFocus();
            }else{
                // Validar Nombre Corización
                int validarCountCotizacion = 0;
                validarCountCotizacion = ValidarCountCotizacion(nombrePendiente);
                
                System.out.println("Total: " + validarCountCotizacion);
                
                if( validarCountCotizacion > 0 ){
                    JOptionPane.showMessageDialog(vv, "La cotización ya existe. \n Utilice otro nombre para la cotización.");
                    vv.txtNombreCotizacion.requestFocus();
                    return;
                }
                
                int errores = 0;
                errores = GuardarCotizacion(nombrePendiente);
                if(errores == 0){
                    JOptionPane.showMessageDialog(vv, "Cotización Guardada Correctamente.");
                    cerrarModal(vv.dCotizaciones);
                    
                    FinalizarVenta(0);
                    
                }else{
                    JOptionPane.showMessageDialog(vv, "Utilice otro nombre para la cotización.");
                    vv.txtNombreCotizacion.requestFocus();
                }
            }
        }else if(e.getSource() == vv.btnPendientes){
            int filas = 0;
            filas = vv.tbllis.getRowCount();
            if(filas <= 0){
                JOptionPane.showMessageDialog(vv, "Ingrese al menos un producto.");
                vv.txtbus.requestFocus();
            }else{
                abrirModalPendientes();
            }
        }else if(e.getSource() == vv.btnCancelarAplicar){
            cerrarModal(vv.dDescuentos);
        }else if(e.getSource() == vv.btnAplicarDescuento){
            AplicarDescuentos();
            
        }else if(e.getSource() == vv.btnDescuento){
            int fila = 0;
            fila = vv.tbllis.getSelectedRow();
            if(fila < 0){
                JOptionPane.showMessageDialog(vv, "Seleccione un Producto");
                vv.txtbus.requestFocus();
            }else{
                String nombre = null;
                String precio = null;
                nombre = vv.tbllis.getValueAt(fila, 2).toString();
                precio = vv.tbllis.getValueAt(fila, 3).toString();
                abrirModalDescuentos(nombre, precio);
            }
        }else if(e.getSource() == vv.btnSalirBuscarP){
            try {
                cerrarModalBuscarP(vv.dBuscarProductos);
            } catch (AWTException ex) {
                Logger.getLogger(VentasFrameC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(e.getSource() == vv.btnSiImprimirTicket){
            FinalizarVenta(1);
        }else if(e.getSource() == vv.btnNoImprimirTicket){
            FinalizarVenta(0);
        }else if(e.getSource() == vv.btnSeleccionarBuscarP){
            SeleccionarBuscarProductoB();
        }else if(e.getSource() == vv.btnsalir){
            if(vv.btnsalir.getText().equals("Salir")){
                // vv.dispose();
                vv.dispose();
                String args[] = null;
                Principal.main(args);
            }else{
                vv.pnlcajarapida.setVisible(true);
                vv.pnlcobro.setVisible(false);
                vv.btnsalir.setText("Salir");
                vv.btncobrar.setEnabled(true);
            }
        }else if(e.getSource() == vv.btnAplicarPrecio){
            seleccionarListaPrecios();
        }else if(e.getSource() == vv.btnCancelarPrecio){
            cerrarModal(vv.dEntSalEfe);
        }else if(e.getSource() == vv.btnmas){
            int fila = 0;
            fila = vv.tbllis.getSelectedRow();
            if(fila < 0){
                JOptionPane.showMessageDialog(vv, "Seleccione un Producto");
            }else{
                double cant = 0;
                cant = Double.parseDouble(vv.tbllis.getValueAt(fila, 4).toString());
                cant = cant + 1;
                vv.tbllis.setValueAt(cant, fila, 4);
                CalcularTotales();
            }
            vv.txtbus.requestFocus();
        }else if(e.getSource() == vv.btnmen){
            int fila = 0;
            fila = vv.tbllis.getSelectedRow();
            if(fila < 0){
                JOptionPane.showMessageDialog(vv, "Seleccione un Producto");
            }else{
                double cant = 0;
                cant = Double.parseDouble(vv.tbllis.getValueAt(fila, 4).toString());
                if(cant <= 1){
                    
                }else{
                    cant = cant - 1;
                    vv.tbllis.setValueAt(cant, fila, 4);
                    CalcularTotales();
                }
            }
            vv.txtbus.requestFocus();
        }else if(e.getSource() == vv.btneli){
            int fila = 0;
            fila = vv.tbllis.getSelectedRow();
            if(fila < 0){
                JOptionPane.showMessageDialog(vv, "Seleccione un Producto");
            }else{
                int resEliminar = JOptionPane.showConfirmDialog(null, "Desea eliminar el producto de la lista?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if( resEliminar == 0 ){
                    modeltLis.removeRow(fila);
                    CalcularTotales();
                }
            }
            vv.txtbus.requestFocus();
        }else if(e.getSource() == vv.btnact){
            FinalizarVenta(0);
            /*int resEliminar = JOptionPane.showConfirmDialog(null, "Desea recargar la ventana?", "Confirmar recarga", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if( resEliminar == 0 ){
                this.vv.dispose();
                VentasFrameM vm = new VentasFrameM();
                if(estacerrado(vv)){
                    vv = new VentasFrameV();
                    inicio.add(vv);
                    VentasFrameC vc = new VentasFrameC(vm, vv);
                    vv.show();
                    /*try {
                        vv.setMaximum(true);
                    } catch (PropertyVetoException ex) {
                        Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    vc.iniciar();
                }else{
                    JOptionPane.showMessageDialog(vv, "La ventana ya se encuentra abierta!!!");
                }
            }*/
        }else if(e.getSource() == vv.btncobrar){
            int filas = 0;
            filas = vv.tbllis.getRowCount();
            if(filas <= 0){
                vv.txtbus.requestFocus();
            }else{
                int fPago = 0;
                fPago = vv.cmbFormaPago.getSelectedIndex();
                // Justo antes de mostrar el resumen o abrir la ventana de cobro: 
                // String.valueOf(totalConDescuentosAplicados)
                double totalConDescuentosAplicados = calcularTotalConDescuento(fPago);
                
                BuscarClientesCredito();
                vv.pnlcajarapida.setVisible(false);
                vv.pnlcobro.setVisible(true);
                vv.btnsalir.setText("Regresar");
                vv.btncobrar.setEnabled(false);
                
                //Obtener Subtotal y Total
                String[] partSubTot;
                partSubTot = vv.lbltot.getText().split(" ");
                vv.txtsubtotal.setText(String.valueOf(totalConDescuentosAplicados)); //partSubTot[1]
                vv.txttotal.setText(String.valueOf(totalConDescuentosAplicados)); // partSubTot[1]
                vv.txtsubtotal.setEnabled(false);
                vv.txttotal.setEnabled(false);
                //Descuento
                vv.txtdescuento.setText("0");
                //Paga Con
                vv.txtpago.setText(String.valueOf(totalConDescuentosAplicados)); // partSubTot[1]
                //Cambio
                vv.txtcambio.setText("0");
                vv.txtcambio.setEnabled(false);
                    
                //Obtener Subtotal y Total en btn cobrar crédito
                vv.txtsubtotalCredito.setText(String.valueOf(totalConDescuentosAplicados)); // partSubTot[1]
                vv.txttotalCredito.setText(String.valueOf(totalConDescuentosAplicados)); // partSubTot[1]
                vv.txtsubtotalCredito.setEnabled(false);
                vv.txttotalCredito.setEnabled(false);
                //Descuento
                vv.txtdescuentoCredito.setText("0");
                //Paga Con
                vv.txtpagoCredito.setText(String.valueOf(totalConDescuentosAplicados)); // partSubTot[1]
                //Cambio
                vv.txtcambioCredito.setText("0");
                
                // Tipo de Venta
                vv.pescobrar.setSelectedIndex(0);
                vv.txtpago.requestFocus();
            }
        }else if(e.getSource() == vv.btncobrarconticket){// Cobrar en Efectivo y Ticket
            double total = 0;
            double pago = 0;
            total = Double.parseDouble(vv.txttotal.getText());
            if(vv.txtpago.getText().isEmpty()){
                pago = 0;
            }else{
                pago = Double.parseDouble(vv.txtpago.getText());
            }
            if(pago < total){
                JOptionPane.showMessageDialog(vv, "Introducir una cantidad en el Campo \"Pago\"\nIgual o Mayor al Total");
            }else{
                int guaVen=0;
                guaVen = GuardarVenta();
                if(guaVen <= 0){
                    JOptionPane.showMessageDialog(vv, "Error al Guarar la venta, Contacte a Soporte Técnico!!!");
                }else{
                    int guaDet = 0;
                    guaDet = GuardarDetalleVenta();
                    if(guaDet > 0){
                        JOptionPane.showMessageDialog(vv, "Error al Guardar los Productos de la Venta, Contacte a Soporte Técnico");
                    }else{
                        // ACTUALIZAR ESTATUS COTIZACION
                        String nombreCotizacion = null;
                        nombreCotizacion = vv.txtNombreCotView.getText();
                        
                        if( !nombreCotizacion.equals("")){
                            FinalizaListaCot(nombreCotizacion);
                        }
                        
                        int permiso = pesm.obtenerPermisoEspecial(PERMISO_IMPRIMIR_TICKET);
                        // System.out.println("Permiso: " + permiso);
                        if( permiso == 0 ){
                            FinalizarVenta(1);
                        }else{
                            vv.dConfirmarCobro.setSize(292, 105);
                            vv.dConfirmarCobro.setModal(true);
                            vv.dConfirmarCobro.setLocationRelativeTo(vv);
                            // Deshabilitar el botón de maximizar
                            vv.dConfirmarCobro.setResizable(false);
                            vv.dConfirmarCobro.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                            vv.dConfirmarCobro.setTitle("Imprimir Ticket");
                            ImageIcon icon = new ImageIcon("src/img/pagos.png"); // Asegúrate de que la imagen esté en la ruta correcta
                            vv.dConfirmarCobro.setIconImage(icon.getImage());
                            vv.dConfirmarCobro.setVisible(true);
                            vv.btnSiImprimirTicket.requestFocus();
                        }
                        // Espacio para Imprimir Ticket Antes de Regresar al Inicio
                        /*int a = 0;
                        try {
                            a = imprimirTicket(true);
                        } catch (IOException ex) {
                            Logger.getLogger(VentasC.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if(a == 1){
                            
                        }else{
                            JOptionPane.showMessageDialog(vv, "El Ticket no se Imprimió, Contacte a Soporte Técnico");
                        }
                        // Fin de Imprimir Ticket
                        vv.pnlcajarapida.setVisible(true);
                        vv.pnlcobro.setVisible(false);
                        vv.btnsalir.setText("Salir");
                        vv.btncobrar.setEnabled(true);
                        //Obtener Subtotal y Total
                        vv.txtsubtotal.setText("");
                        vv.txttotal.setText("");
                        //Descuento
                        vv.txtdescuento.setText("");
                        //Paga Con
                        vv.txtpago.setText("");
                        //Cambio
                        vv.txtcambio.setText("");
                        int filas = vv.tbllis.getRowCount();
                        for (int i = 0; i < filas; i++) {
                            modeltLis.removeRow(0);
                        }
                        CalcularTotales();
                        JOptionPane.showMessageDialog(vv, "Venta Finalizada");
                        vv.txtbus.requestFocus();*/
                    }
                }
            }
        }else if(e.getSource() == vv.btncobrarpendiente){// Cobrar en Efectivo y Ticket
            // 1. Validar primero que el nombre y el teléfono no estén vacíos antes de cobrar
            javax.swing.JTextField txtNombreCliente = new javax.swing.JTextField(20);
            javax.swing.JTextField txtTelefonoCliente = new javax.swing.JTextField(15);

            javax.swing.JPanel panelCliente = new javax.swing.JPanel(new java.awt.GridLayout(0, 1, 5, 5));
            panelCliente.add(new javax.swing.JLabel("Nombre del cliente (Obligatorio):"));
            panelCliente.add(txtNombreCliente);
            panelCliente.add(new javax.swing.JLabel("Teléfono (Obligatorio):"));
            panelCliente.add(txtTelefonoCliente);

            // 2. Definir los textos personalizados para los botones
            Object[] opciones = {"Guardar", "Cancelar"};

            // 3. Usar showOptionDialog para controlar los textos y la opción predeterminada
            int resultadoCliente = JOptionPane.showOptionDialog(
                vv, 
                panelCliente, 
                "Datos del Cliente", 
                JOptionPane.YES_NO_OPTION, // Opciones estándar sí/no para mapear a los botones
                JOptionPane.PLAIN_MESSAGE, 
                null, 
                opciones,       // Arreglo con tus textos personalizados
                opciones[0]     // Botón predeterminado ("Guardar")
            );

            // Si cancela o cierra la ventana, detenemos el proceso
            if (resultadoCliente != JOptionPane.OK_OPTION) {
                return;
            }

            String nombreCliente = txtNombreCliente.getText().trim();
            String telefonoCliente = txtTelefonoCliente.getText().trim();

            if (nombreCliente.isEmpty() || telefonoCliente.isEmpty()) {
                JOptionPane.showMessageDialog(vv, "El nombre y el teléfono son obligatorios para completar la venta.", "Error de validación", JOptionPane.WARNING_MESSAGE);
                return; // Detiene el proceso si están vacíos
            }

            // 2. Tu lógica original de validación de pago y guardado
            estatusVenta = 2;
            double total = 0;
            double pago = 0;
            total = Double.parseDouble(vv.txttotal.getText());

            if(vv.txtpago.getText().isEmpty()){
                pago = 0;
            }else{
                pago = Double.parseDouble(vv.txtpago.getText());
            }

            if(pago < total){
                JOptionPane.showMessageDialog(vv, "Introducir una cantidad en el Campo \"Pago\"\nIgual o Mayor al Total");
            }else{
                int guaVen = 0;

                // NOTA: Asegúrate de pasar 'nombreCliente' y 'telefonoCliente' a tu método GuardarVenta() 
                // o guardarlos en variables globales de tu clase para usarlos al registrar en la BD.
                
                nombreCliente_Pen = nombreCliente; // nombreCliente;
                telefonoCliente_Pen = telefonoCliente; // telefonoCliente;
                
                guaVen = GuardarVenta(); // Si tu método acepta parámetros, puedes poner: GuardarVenta(nombreCliente, telefonoCliente);

                if(guaVen <= 0){
                    JOptionPane.showMessageDialog(vv, "Error al Guardar la venta, Contacte a Soporte Técnico!!!");
                }else{
                    int guaDet = 0;
                    guaDet = GuardarDetalleVenta();
                    if(guaDet > 0){
                        JOptionPane.showMessageDialog(vv, "Error al Guardar los Productos de la Venta, Contacte a Soporte Técnico");
                    }else{
                        // ACTUALIZAR ESTATUS COTIZACION
                        String nombreCotizacion = null;
                        nombreCotizacion = vv.txtNombreCotView.getText();

                        if( !nombreCotizacion.equals("")){
                            FinalizaListaCot(nombreCotizacion);
                        }

                        int permiso = pesm.obtenerPermisoEspecial(PERMISO_IMPRIMIR_TICKET);
                        if( permiso == 0 ){
                            FinalizarVenta(1);
                        }else{
                            vv.dConfirmarCobro.setSize(292, 105);
                            vv.dConfirmarCobro.setModal(true);
                            vv.dConfirmarCobro.setLocationRelativeTo(vv);
                            vv.dConfirmarCobro.setResizable(false);
                            vv.dConfirmarCobro.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                            vv.dConfirmarCobro.setTitle("Imprimir Ticket");
                            ImageIcon icon = new ImageIcon("src/img/pagos.png"); 
                            vv.dConfirmarCobro.setIconImage(icon.getImage());
                            vv.dConfirmarCobro.setVisible(true);
                            vv.btnSiImprimirTicket.requestFocus();
                        }
                    }
                }
            }
            estatusVenta = 1;
            
            nombreCliente_Pen = "";
            telefonoCliente_Pen = "";
            
            /* COMENTADO
            
            estatusVenta = 2;
            double total = 0;
            double pago = 0;
            total = Double.parseDouble(vv.txttotal.getText());
            if(vv.txtpago.getText().isEmpty()){
                pago = 0;
            }else{
                pago = Double.parseDouble(vv.txtpago.getText());
            }
            if(pago < total){
                JOptionPane.showMessageDialog(vv, "Introducir una cantidad en el Campo \"Pago\"\nIgual o Mayor al Total");
            }else{
                int guaVen=0;
                guaVen = GuardarVenta();
                if(guaVen <= 0){
                    JOptionPane.showMessageDialog(vv, "Error al Guarar la venta, Contacte a Soporte Técnico!!!");
                }else{
                    int guaDet = 0;
                    guaDet = GuardarDetalleVenta();
                    if(guaDet > 0){
                        JOptionPane.showMessageDialog(vv, "Error al Guardar los Productos de la Venta, Contacte a Soporte Técnico");
                    }else{
                        // ACTUALIZAR ESTATUS COTIZACION
                        String nombreCotizacion = null;
                        nombreCotizacion = vv.txtNombreCotView.getText();
                        
                        if( !nombreCotizacion.equals("")){
                            FinalizaListaCot(nombreCotizacion);
                        }
                        
                        int permiso = pesm.obtenerPermisoEspecial(PERMISO_IMPRIMIR_TICKET);
                        // System.out.println("Permiso: " + permiso);
                        if( permiso == 0 ){
                            FinalizarVenta(1);
                        }else{
                            vv.dConfirmarCobro.setSize(292, 105);
                            vv.dConfirmarCobro.setModal(true);
                            vv.dConfirmarCobro.setLocationRelativeTo(vv);
                            // Deshabilitar el botón de maximizar
                            vv.dConfirmarCobro.setResizable(false);
                            vv.dConfirmarCobro.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                            vv.dConfirmarCobro.setTitle("Imprimir Ticket");
                            ImageIcon icon = new ImageIcon("src/img/pagos.png"); // Asegúrate de que la imagen esté en la ruta correcta
                            vv.dConfirmarCobro.setIconImage(icon.getImage());
                            vv.dConfirmarCobro.setVisible(true);
                            vv.btnSiImprimirTicket.requestFocus();
                        }
                    }
                }
            }
            estatusVenta = 1;*/
        }else if(e.getSource() == vv.btnCobrarCredito){// Cobrar en Efectivo y Ticket
            double total = 0;
            double pago = 0;
            double pagoInicial = 0;
            total = Double.parseDouble(vv.txttotalCredito.getText());
            if(vv.txtpagoCredito.getText().isEmpty()){
                pago = 0;
            }else{
                pago = Double.parseDouble(vv.txtpagoCredito.getText());
            }
            
            if(vv.txtPagoInicialCredito.getText().isEmpty()){
                pagoInicial = 0;
            }else{
                pagoInicial = Double.parseDouble(vv.txtPagoInicialCredito.getText());
            }
            
            if( pagoInicial >= total ){
                JOptionPane.showMessageDialog(vv, "El pago inicial debe ser menor al Total de la Venta.");
                return;
            }
            
            if(pago < total){
                JOptionPane.showMessageDialog(vv, "Introducir una cantidad en el Campo \"Pago\"\nIgual o Mayor al Total");
            }else{
                int guaVen=0;
                guaVen = GuardarVenta();
                if(guaVen <= 0){
                    JOptionPane.showMessageDialog(vv, "Error al Guarar la venta, Contacte a Soporte Técnico!!!");
                }else{
                    int guaDet = 0;
                    guaDet = GuardarDetalleVenta();
                    if(guaDet > 0){
                        JOptionPane.showMessageDialog(vv, "Error al Guardar los Productos de la Venta, Contacte a Soporte Técnico");
                    }else{
                        // ACTUALIZAR ESTATUS COTIZACION
                        String nombreCotizacion = null;
                        nombreCotizacion = vv.txtNombreCotView.getText();
                        
                        if( !nombreCotizacion.equals("")){
                            FinalizaListaCot(nombreCotizacion);
                        }
                        
                        int permiso = pesm.obtenerPermisoEspecial(PERMISO_IMPRIMIR_TICKET);
                        if( permiso == 0 ){
                            FinalizarVenta(1);
                        }else{
                            vv.dConfirmarCobro.setSize(292, 105);
                            vv.dConfirmarCobro.setModal(true);
                            vv.dConfirmarCobro.setLocationRelativeTo(vv);
                            // Deshabilitar el botón de maximizar
                            vv.dConfirmarCobro.setResizable(false);
                            vv.dConfirmarCobro.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                            vv.dConfirmarCobro.setTitle("Imprimir Ticket");
                            ImageIcon icon = new ImageIcon("src/img/pagos.png"); // Asegúrate de que la imagen esté en la ruta correcta
                            vv.dConfirmarCobro.setIconImage(icon.getImage());
                            vv.dConfirmarCobro.setVisible(true);
                            vv.btnSiImprimirTicket.requestFocus();
                        }
                    }
                }
            }
        }/*else if(e.getSource() == vv.btncobrarsinticket){// Cobro en Efectivo Sin Ticket
            double total = 0;
            double pago = 0;
            total = Double.parseDouble(vv.txttotal.getText());
            if(vv.txtpago.getText().isEmpty()){
                pago = 0;
            }else{
                pago = Double.parseDouble(vv.txtpago.getText());
            }
            if(pago < total){
                JOptionPane.showMessageDialog(vv, "Introducir una cantidad en el Campo \"Pago\"\nIgual o Mayor al Total");
            }else{
                int guaVen=0;
                guaVen = GuardarVenta();
                if(guaVen <= 0){
                    JOptionPane.showMessageDialog(vv, "Error al Guarar la venta, Contacte a Soporte Técnico!!!");
                }else{
                    int guaDet = 0;
                    guaDet = GuardarDetalleVenta();
                    if(guaDet > 0){
                        JOptionPane.showMessageDialog(vv, "Error al Guardar los Productos de la Venta, Contacte a Soporte Técnico");
                    }else{
                        vv.dConfirmarCobro.setSize(292, 105);
                        vv.dConfirmarCobro.setModal(true);
                        vv.dConfirmarCobro.setLocationRelativeTo(vv);
                        // Deshabilitar el botón de maximizar
                        vv.dConfirmarCobro.setResizable(false);
                        vv.dConfirmarCobro.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                        vv.dConfirmarCobro.setTitle("Imprimir Ticket");
                        ImageIcon icon = new ImageIcon("src/img/pagos.png"); // Asegúrate de que la imagen esté en la ruta correcta
                        vv.dConfirmarCobro.setIconImage(icon.getImage());
                        vv.dConfirmarCobro.setVisible(true);
                        vv.btnSiImprimirTicket.requestFocus();
                    }
                }
            }
        }*/else if(e.getSource() == vv.btnbus_ven){
            CargarVentasCancelar();
        }else if(e.getSource() == vv.btnsalir_ven){
            vv.dispose();
            String args[] = null;
            Principal.main(args);
        }else if(e.getSource() == vv.btncancelar_ven){
            int fila = 0;
            fila = vv.tbllis_ven.getSelectedRow();
            if(fila < 0){
                JOptionPane.showMessageDialog(vv, "Selecione un Registro");
            }else{
                String estatus = null;
                estatus = vv.tbllis_ven.getValueAt(fila, 7).toString();
                if(estatus.equals("Cancelada")){
                    JOptionPane.showMessageDialog(vv, "La venta ya fue cancelada!!!");
                }else{
                    Object[] options = {"Si", "No"};
                    int resGuardar = JOptionPane.showOptionDialog(null, "Desea cancelar la venta?", "Guardar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null, options, options[0]);
                    if( resGuardar == 0 ){
                        int a = 0;
                        a = ActualizarEstatus_VE(fila);
                        if(a == 0){
                            JOptionPane.showMessageDialog(vv, "Error al Cancelar Venta, Contacte a Soporte Técnio");
                        }else{
                            RegresarStockVenta(fila);
                            CargarVentasCancelar();
                            JOptionPane.showMessageDialog(vv, "Venta Cancelada");
                        }
                    }
                }
            }
        }else if(e.getSource() == vv.btnconsultar_ven){
            CargarModalDetalleVenta();
            
            /*int fila = 0;
            fila = vv.tbllis_ven.getSelectedRow();
            if(fila < 0){
                JOptionPane.showMessageDialog(vv, "Seleccione un Registro");
            }else{
                
                // LIMPIAR DIALOG
                vv.c_folio_ve.setText("");
                vv.c_fechaventa_ve.setText("");
                vv.c_fechacancelado_ve.setText("");
                vv.c_estatus_ve.setText("");
                vv.c_subtotal_ve.setText("");
                vv.c_descuento_ve.setText("");
                vv.c_total_ve.setText("");
                int c_fila = 0;
                c_fila = vv.c_tbllis_ve.getSelectedRow();
                if(c_fila == -1){
                    
                }else{
                    for (int i = 0; i < fila; i++) {
                        c_modeltLisVE.removeRow(0);
                    }
                }
                
                // ASIGNAR VALORES A DIALOG
                vv.c_folio_ve.setText(vv.tbllis_ven.getValueAt(fila, 1).toString());
                vv.c_fechaventa_ve.setText(vv.tbllis_ven.getValueAt(fila, 6).toString());
                vv.c_estatus_ve.setText(vv.tbllis_ven.getValueAt(fila, 7).toString());
                vv.c_subtotal_ve.setText(vv.tbllis_ven.getValueAt(fila, 4).toString());
                vv.c_descuento_ve.setText(vv.tbllis_ven.getValueAt(fila, 5).toString());
                vv.c_total_ve.setText(vv.tbllis_ven.getValueAt(fila, 2).toString());
                if(vv.tbllis_ven.getValueAt(fila, 8) == null){
                    vv.c_fechacancelado_ve.setText("");
                }else{
                    vv.c_fechacancelado_ve.setText(vv.tbllis_ven.getValueAt(fila, 8).toString());
                }
                CargarConsultaDetalle2(vv.c_folio_ve.getText());
                
                if(vv.tbllis_ven.getValueAt(fila, 7).toString().equals("Cancelada")){
                    vv.btn_reimprimir_ticket.setEnabled(false);
                    vv.btnDevolverProducto.setEnabled(false);
                }else{
                    vv.btn_reimprimir_ticket.setEnabled(true);
                    vv.btnDevolverProducto.setEnabled(true);
                }
                abrirModalDetalleVenta();
            }*/
        }else if(e.getSource() == vv.btnexportar){
            int filas = vv.tbllis_ven.getRowCount();
            if( filas > 0 ){
                ExportarExcel obj;
                try {
                    obj = new ExportarExcel();
                    obj.exportarExcel(vv.tbllis_ven);
                }catch(IOException ex) {
                    System.out.println("Error: " + ex);
                }
            }else{
                JOptionPane.showMessageDialog(vv, "No tienes datos para exportar");
            }
        }else if(e.getSource() == vv.btnExportarDetalle){
            int filas = vv.tbllisDetalle.getRowCount();
            if( filas > 0 ){
                ExportarExcel obj;
                try {
                    obj = new ExportarExcel();
                    obj.exportarExcel(vv.tbllisDetalle);
                }catch(IOException ex) {
                    System.out.println("Error: " + ex);
                }
            }else{
                JOptionPane.showMessageDialog(vv, "No tienes datos para exportar");
            }
        }else if(e.getSource() == vv.btn_reimprimir_ticket){
            int a = 0;
            try {
                a = reImprimirTicket(true);
            } catch (IOException ex) {
                Logger.getLogger(VentasC.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(a == 1){

            }else{
                JOptionPane.showMessageDialog(vv, "El Ticket no se Imprimió, Contacte a Soporte Técnico");
            }
        }else if(e.getSource() == vv.btnCancelar_ESE){
            cerrarModal(vv.dEntSalEfe);
        }else if(e.getSource() == vv.btnGuardar_ESE){
            try {
                guardarEntradasSalidasEfectivo();
            } catch (IOException ex) {
                Logger.getLogger(VentasC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(e.getSource() == vv.btnEntSalEfectivo){
            abrirModalEntradasSalidasEfe();
        }else if(e.getSource()== vv.cmbFormaPago){
            int fPago = 0;
            fPago = vv.cmbFormaPago.getSelectedIndex();
            // Justo antes de mostrar el resumen o abrir la ventana de cobro: 
            // String.valueOf(totalConDescuentosAplicados)
            double totalConDescuentosAplicados = calcularTotalConDescuento(fPago);

            vv.txtsubtotal.setText(String.valueOf(totalConDescuentosAplicados)); // partSubTot[1]
            vv.txttotal.setText(String.valueOf(totalConDescuentosAplicados)); // partSubTot[1]
            //Descuento
            vv.txtdescuento.setText("0");
            //Paga Con
            vv.txtpago.setText(String.valueOf(totalConDescuentosAplicados)); // partSubTot[1]
            //Cambio
            vv.txtcambio.setText("0");

            //Obtener Subtotal y Total en btn cobrar crédito
            vv.txtsubtotalCredito.setText(String.valueOf(totalConDescuentosAplicados)); // partSubTot[1]
            vv.txttotalCredito.setText(String.valueOf(totalConDescuentosAplicados)); // partSubTot[1]
            //Descuento
            vv.txtdescuentoCredito.setText("0");
            //Paga Con
            vv.txtpagoCredito.setText(String.valueOf(totalConDescuentosAplicados)); // partSubTot[1]
            //Cambio
            vv.txtcambioCredito.setText("0");
            vv.txtpago.requestFocus();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getSource()== vv.txtbus){
            /*
            if(vv.tbllis.getRowCount() > 0)
                vv.tbllis.setRowSelectionInterval(0, 0);
            */
            
            if(/*e.getKeyCode() == KeyEvent.VK_PLUS ||*/ e.getKeyCode() == KeyEvent.VK_ADD) {
                int fila = 0;
                fila = vv.tbllis.getSelectedRow();
                if(fila < 0){
                    JOptionPane.showMessageDialog(vv, "Seleccione un Producto");
                }else{
                    double cant = 0;
                    cant = Double.parseDouble(vv.tbllis.getValueAt(fila, 4).toString());
                    cant = cant + 1;
                    vv.tbllis.setValueAt(cant, fila, 4);
                    CalcularTotales();
                }
                
                vv.txtbus.requestFocus();
            }else if(/*e.getKeyCode() == KeyEvent.VK_MINUS ||*/ e.getKeyCode() == KeyEvent.VK_SUBTRACT) {
                int fila = 0;
                fila = vv.tbllis.getSelectedRow();
                if(fila < 0){
                    JOptionPane.showMessageDialog(vv, "Seleccione un Producto");
                }else{
                    double cant = 0;
                    cant = Double.parseDouble(vv.tbllis.getValueAt(fila, 4).toString());
                    if(cant <= 1){

                    }else{
                        cant = cant - 1;
                        vv.tbllis.setValueAt(cant, fila, 4);
                        CalcularTotales();
                    }
                }
                vv.txtbus.requestFocus();
            }else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                if( vv.txtbus.getText().toString().length() < 1 ){
                    int filasDownCount = vv.tbllis.getRowCount();
                    int filasDownSelected = vv.tbllis.getSelectedRow();
                    if(filasDownCount > 0){
                        // vv.tbllis.setRowSelectionInterval(0, 0);
                        if(filasDownCount > filasDownSelected+1){
                            vv.tbllis.setRowSelectionInterval(filasDownSelected+1, filasDownSelected+1);
                        }
                    }
                }
            }else if(e.getKeyCode() == KeyEvent.VK_UP) {
                if( vv.txtbus.getText().toString().length() < 1 ){
                    int filasUpCount = vv.tbllis.getRowCount();
                    int filasUpSelected = vv.tbllis.getSelectedRow();
                    if(filasUpCount > 0){
                        // vv.tbllis.setRowSelectionInterval(0, 0);
                        if( filasUpSelected > 0 ){
                            vv.tbllis.setRowSelectionInterval(filasUpSelected-1, filasUpSelected-1);
                        }
                    }
                }
            }else if(e.getKeyCode() == KeyEvent.VK_DELETE) {
                int fila = 0;
                fila = vv.tbllis.getSelectedRow();
                if(fila < 0){
                    JOptionPane.showMessageDialog(vv, "Seleccione un Producto");
                }else{
                    int resEliminar = JOptionPane.showConfirmDialog(null, "Desea eliminar el producto de la lista?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if( resEliminar == 0 ){
                        modeltLis.removeRow(fila);
                        CalcularTotales();
                    }
                }
                
                if(vv.tbllis.getRowCount() > 0){
                    if(fila == 0){
                        vv.tbllis.setRowSelectionInterval(0, 0);
                    }else{
                        vv.tbllis.setRowSelectionInterval(fila-1, fila-1);
                    }
                }
                vv.txtbus.requestFocus();
            }else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                String[] partPro;
                partPro = vv.txtbus.getText().split("'");
                // JOptionPane.showMessageDialog(vv, "Length en texto: " + partPro.length + " | Texto: " + vv.txtbus.getText());
                
                if( partPro.length <= 1 ){
                    String[] partPro1;
                    partPro1 = vv.txtbus.getText().split("\\*");
                    
                    if( partPro1.length <= 1 ){
                    
                        String str = vv.txtbus.getText();
                        boolean isNumeric =  str.matches("[+-]?\\d*(\\.\\d+)?");

                        // if( isNumeric ){
                        ///////////////////////// AGREGAR PRODUCTO CON ENTER //////////////////////////////////
                        double existe = 0;
                        existe = VerificarExisteTabla(vv.txtbus.getText());
                        if(existe <= 0){
                            String datos = null;
                            // textAutoCompleter.setCaseSensitive(true);
                            datos = CargarProducto(vv.txtbus.getText());
                            if(datos.equals("NO")){
                                int indicador = 0;
                                for (int i = 0; i < textAutoCompleter.getItems().length; i++) {
                                    //System.out.println(textAutoCompleter.getItems()[i]);
                                    String[] partProAuto;
                                    partProAuto = textAutoCompleter.getItems()[i].toString().split("'");
                                    int strLength = 0;
                                    strLength = str.length();
                                    if( strLength > partProAuto[0].length()){
                                        continue;
                                    }
                                    String subcadena = "";
                                    subcadena = partProAuto[0].substring(0, strLength);
                                    // System.out.println("Ulitmo: " + subcadena);
                                    // if (subcadena.contains(str.toUpperCase())) {
                                    if (subcadena.toUpperCase().contains(str.toUpperCase())) {
                                        indicador = 1;
                                        break;
                                    }/* else {
                                        System.out.println("La cadena no contiene la subcadena.");
                                    }*/
                                }
                                
                                if(indicador == 0){
                                    vv.txtbus.setText("");
                                    JOptionPane.showMessageDialog(vv, "Producto No Existe");
                                }
                                
                                // System.out.println(textAutoCompleter.getItems()[0]);
                                // JOptionPane.showMessageDialog(vv, "Producto No Existe");
                            }else{
                                String[] datosBD;
                                datosBD = datos.split("_");
                                if( Double.parseDouble(datosBD[2]) < 0){
                                    JOptionPane.showMessageDialog(vv, "<html>El producto <b>" + datosBD[3] + "</b> no cuenta con stock.</html>");
                                }
                                modeltLis.addRow(new Object[]{datosBD[0],vv.txtbus.getText(),datosBD[3],datosBD[1],1,"",datosBD[4]});
                                vv.txtbus.setText("");
                            }
                        }else{
                            String datos = null;
                            datos = CargarProducto(vv.txtbus.getText());
                            if(datos.equals("NO")){
                                JOptionPane.showMessageDialog(vv, "Producto No Existe");
                            }else{
                                String[] datosBD;
                                datosBD = datos.split("_");
                                if(Double.parseDouble(datosBD[2]) < existe+1){
                                    //JOptionPane.showMessageDialog(vv, "Stock Agotado"); -- Se le retiró para agregar sin Stock
                                    JOptionPane.showMessageDialog(vv, "<html>El producto <b>" + datosBD[3] + "</b> no cuenta con stock.</html>");
                                    vv.tbllis.setValueAt(existe+1, filaReem, 4);
                                    vv.txtbus.setText("");
                                }else{
                                    vv.tbllis.setValueAt(existe+1, filaReem, 4);
                                    vv.txtbus.setText("");
                                }
                            }
                        }
                        CalcularTotales();
                        // vv.txtbus.setText("");
                        
                        if(vv.tbllis.getRowCount() > 0)
                            vv.tbllis.setRowSelectionInterval(0, 0);
                        
                        vv.txtbus.requestFocus();
                        //////////////////////////////////////////////////////////////////////////////////
                        // }
                    }else{
                        String str = partPro1[0];
                        boolean isNumeric =  str.matches("[+-]?\\d*(\\.\\d+)?");
                        if( !isNumeric ){
                            JOptionPane.showMessageDialog(vv, "Cantidad inválida. Ingrese otra cantidad.");
                            return;
                        }
                        ///////////////////////// AGREGAR PRODUCTO CON ENTER //////////////////////////////////
                        double existe = 0;
                        existe = VerificarExisteTabla(partPro1[1]);
                        if(existe <= 0){
                            String datos = null;
                            datos = CargarProducto(partPro1[1]);
                            if(datos.equals("NO")){
                                JOptionPane.showMessageDialog(vv, "Producto No Existe");
                            }else{
                                String[] datosBD;
                                datosBD = datos.split("_");
                                if( Double.parseDouble(datosBD[2]) < Double.parseDouble(partPro1[0])){
                                    JOptionPane.showMessageDialog(vv, "<html>El producto <b>" + datosBD[3] + "</b> no cuenta con stock.</html>");
                                }
                                modeltLis.addRow(new Object[]{datosBD[0],partPro1[1],datosBD[3],datosBD[1],partPro1[0],"",datosBD[4]});
                                vv.txtbus.setText("");
                            }
                        }else{
                            String datos = null;
                            datos = CargarProducto(partPro1[1]);
                            if(datos.equals("NO")){
                                JOptionPane.showMessageDialog(vv, "Producto No Existe");
                            }else{
                                String[] datosBD;
                                datosBD = datos.split("_");
                                if(Double.parseDouble(datosBD[2]) < existe+Double.parseDouble(partPro1[0])){
                                    //JOptionPane.showMessageDialog(vv, "Stock Agotado"); -- Se le retiró para agregar sin Stock
                                    JOptionPane.showMessageDialog(vv, "<html>El producto <b>" + datosBD[3] + "</b> no cuenta con stock.</html>");
                                    vv.tbllis.setValueAt(existe+Double.parseDouble(partPro1[0]), filaReem, 4);
                                    vv.txtbus.setText("");
                                }else{
                                    vv.tbllis.setValueAt(existe+Double.parseDouble(partPro1[0]), filaReem, 4);
                                    vv.txtbus.setText("");
                                }
                            }
                        }
                        CalcularTotales();
                        vv.txtbus.setText("");
                        
                        if(vv.tbllis.getRowCount() > 0)
                            vv.tbllis.setRowSelectionInterval(0, 0);
                        
                        vv.txtbus.requestFocus();
                        //////////////////////////////////////////////////////////////////////////////////
                    }
                }else{
                    double existe = 0;
                    existe = VerificarExisteTabla(partPro[1]);
                    if(existe < 1){
                        String datos = null;
                        datos = CargarProducto(partPro[1]);
                        if(datos.equals("NO")){
                            JOptionPane.showMessageDialog(vv, "Producto No Existe");
                        }else{
                            String[] datosBD;
                            datosBD = datos.split("_");
                            if( Double.parseDouble(datosBD[2]) < 0){
                                JOptionPane.showMessageDialog(vv, "<html>El producto <b>" + datosBD[3] + "</b> no cuenta con stock.</html>");
                            }
                            modeltLis.addRow(new Object[]{datosBD[0],partPro[1],datosBD[3],datosBD[1],1,"",datosBD[4]});
                        }
                    }else{
                        String datos = null;
                        datos = CargarProducto(partPro[1]);
                        if(datos.equals("NO")){
                            JOptionPane.showMessageDialog(vv, "Producto No Existe");
                        }else{
                            String[] datosBD;
                            datosBD = datos.split("_");
                            if(Double.parseDouble(datosBD[2]) < existe+1){
                                //JOptionPane.showMessageDialog(vv, "Stock Agotado"); -- Se le retiró para agregar sin Stock
                                JOptionPane.showMessageDialog(vv, "<html>El producto <b>" + datosBD[3] + "</b> no cuenta con stock.</html>");
                                vv.tbllis.setValueAt(existe+1, filaReem, 4);
                            }else{
                                vv.tbllis.setValueAt(existe+1, filaReem, 4);
                            }
                        }
                    }
                    CalcularTotales();
                    vv.txtbus.setText("");
                    
                    if(vv.tbllis.getRowCount() > 0)
                        vv.tbllis.setRowSelectionInterval(0, 0);
                    
                    vv.txtbus.requestFocus();
                    
                }
                
                /*if(partPro.length < 1){
                    String[] partPro1;
                    partPro1 = vv.txtbus.getText().split("-");
                    if(partPro1.length <= 1){
                        
                    }else{
                        //JOptionPane.showMessageDialog(vv, partPro1[0] + "-" +partPro1[1]);
                        
                    }
                    //vv.txtbus.setText("");
                    
                }else{
                    
                }*/
            }else if(e.getKeyCode() == KeyEvent.VK_F12) {
                //JOptionPane.showMessageDialog(vv, "Predionó F10");
                int filas = 0;
                filas = vv.tbllis.getRowCount();
                if(filas <= 0){
                    vv.txtbus.requestFocus();
                }else{
                    int fPago = 0;
                    fPago = vv.cmbFormaPago.getSelectedIndex();
                    // Justo antes de mostrar el resumen o abrir la ventana de cobro: 
                    // String.valueOf(totalConDescuentosAplicados)
                    double totalConDescuentosAplicados = calcularTotalConDescuento(fPago);
                    
                    BuscarClientesCredito();
                    vv.pnlcajarapida.setVisible(false);
                    vv.pnlcobro.setVisible(true);
                    vv.btnsalir.setText("Regresar");
                    vv.btncobrar.setEnabled(false);
                    //Obtener Subtotal y Total en btn cobrar contado
                    String[] partSubTot;
                    partSubTot = vv.lbltot.getText().split(" ");
                    vv.txtsubtotal.setText(String.valueOf(totalConDescuentosAplicados)); // partSubTot[1]
                    vv.txttotal.setText(String.valueOf(totalConDescuentosAplicados)); // partSubTot[1]
                    vv.txtsubtotal.setEnabled(false);
                    vv.txttotal.setEnabled(false);
                    //Descuento
                    vv.txtdescuento.setText("0");
                    //Paga Con
                    vv.txtpago.setText(String.valueOf(totalConDescuentosAplicados)); // partSubTot[1]
                    //Cambio
                    vv.txtcambio.setText("0");
                    vv.txtcambio.setEnabled(false);
                    
                    //Obtener Subtotal y Total en btn cobrar crédito
                    vv.txtsubtotalCredito.setText(String.valueOf(totalConDescuentosAplicados)); // partSubTot[1]
                    vv.txttotalCredito.setText(String.valueOf(totalConDescuentosAplicados)); // partSubTot[1]
                    vv.txtsubtotalCredito.setEnabled(false);
                    vv.txttotalCredito.setEnabled(false);
                    //Descuento
                    vv.txtdescuentoCredito.setText("0");
                    //Paga Con
                    vv.txtpagoCredito.setText(String.valueOf(totalConDescuentosAplicados)); // partSubTot[1]
                    //Cambio
                    vv.txtcambioCredito.setText("0");
                    
                    // Tipo de Venta
                    vv.pescobrar.setSelectedIndex(0);
                    vv.txtpago.requestFocus();
                }
            }else if(e.getKeyCode() == KeyEvent.VK_F6) {
                abrirModalEntradasSalidasEfe();
            }else if(e.getKeyCode() == KeyEvent.VK_F8) {
                int fila = 0;
                fila = vv.tbllis.getSelectedRow();
                if(fila < 0){
                    JOptionPane.showMessageDialog(vv, "Seleccione un Registro");
                    vv.txtbus.requestFocus();
                }else{
                    int id = 0;
                    id = Integer.parseInt(vv.tbllis.getValueAt(fila, 0).toString());
                    abrirModalPrecios(id);
                }
            }else if(e.getKeyCode() == KeyEvent.VK_F10) {
                // JOptionPane.showMessageDialog(vv, "Abrir buscar");
                abrirModalBuscarProductos();
            }else if(e.getKeyCode() == KeyEvent.VK_F11) {
                // JOptionPane.showMessageDialog(vv, "Abrir buscar");
                FinalizarVenta(0);
            }
        }else if(e.getSource()== vv.cmbFormaPago){
            if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                cobrarConEnter();
            }else if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                vv.pnlcajarapida.setVisible(true);
                vv.pnlcobro.setVisible(false);
                vv.btnsalir.setText("Salir");
                vv.btncobrar.setEnabled(true);
                vv.txtbus.requestFocus();
            }
        }else if(e.getSource()== vv.txtpago){
            if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                cobrarConEnter();
            }else if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                vv.pnlcajarapida.setVisible(true);
                vv.pnlcobro.setVisible(false);
                vv.btnsalir.setText("Salir");
                vv.btncobrar.setEnabled(true);
                vv.txtbus.requestFocus();
            }
        }else if(e.getSource()== vv.txtdescuento){
            if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                cobrarConEnter();
            }else if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                vv.pnlcajarapida.setVisible(true);
                vv.pnlcobro.setVisible(false);
                vv.btnsalir.setText("Salir");
                vv.btncobrar.setEnabled(true);
                vv.txtbus.requestFocus();
            }
        }else if( e.getSource() == vv.txtComentarios_ESE || 
                e.getSource() == vv.chbImpComprobante_ESE || 
                e.getSource() == vv.btnCancelar_ESE || 
                e.getSource() == vv.btnGuardar_ESE ){
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                cerrarModal(vv.dEntSalEfe);
            }else if ( e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_F12 ) {
                try {
                    guardarEntradasSalidasEfectivo();
                } catch (IOException ex) {
                    Logger.getLogger(VentasC.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else if( e.getSource() == vv.cmbTipo_ESE ){
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                cerrarModal(vv.dEntSalEfe);
            }
        }else if( e.getSource() == vv.jrbPrecio1 || e.getSource() == vv.jrbPrecio2 || e.getSource() == vv.jrbPrecio3 ){
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                cerrarModal(vv.dPrecios);
            }else if ( e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_F12 ) {
                seleccionarListaPrecios();
            }
        }else if(e.getSource() == vv.txtBuscarProductos){
            if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                // if( vv.txtBuscarProductos.getText().toString().length() < 1 ){
                    int filasDownCount = vv.tblBuscarProductos.getRowCount();
                    int filasDownSelected = vv.tblBuscarProductos.getSelectedRow();
                    if(filasDownCount > 0){
                        if(filasDownCount > filasDownSelected+1){
                            vv.tblBuscarProductos.setRowSelectionInterval(filasDownSelected+1, filasDownSelected+1);
                        }
                    }
                // }
            }else if(e.getKeyCode() == KeyEvent.VK_UP) {
                // if( vv.txtBuscarProductos.getText().toString().length() < 1 ){
                    int filasUpCount = vv.tblBuscarProductos.getRowCount();
                    int filasUpSelected = vv.tblBuscarProductos.getSelectedRow();
                    if(filasUpCount > 0){
                        if( filasUpSelected > 0 ){
                            vv.tblBuscarProductos.setRowSelectionInterval(filasUpSelected-1, filasUpSelected-1);
                        }
                    }
                // }
            }else if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                try {
                    cerrarModalBuscarP(vv.dBuscarProductos);
                } catch (AWTException ex) {
                    Logger.getLogger(VentasFrameC.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else if( e.getKeyCode() == KeyEvent.VK_F12 || e.getKeyCode() == KeyEvent.VK_ENTER ) {
                SeleccionarBuscarProductoB();
                
                /*double existe = 0;
                existe = VerificarExisteTabla(vv.txtbus.getText());
                if(existe <= 0){
                    
                }
                
                modeltLis.addRow(new Object[]{datosBD[0],vv.txtbus.getText(),datosBD[3],datosBD[1],1,""});
                vv.txtbus.setText("");*/
            }
        }else if(e.getSource() == vv.btnSiImprimirTicket){
            if( e.getKeyCode() == KeyEvent.VK_RIGHT ) {
                vv.btnNoImprimirTicket.requestFocus();
            }/*else if( e.getKeyCode() == KeyEvent.VK_ENTER ) {
                FinalizarVenta(1);
            }*/
        }else if(e.getSource() == vv.btnNoImprimirTicket){
            if( e.getKeyCode() == KeyEvent.VK_LEFT ) {
                vv.btnSiImprimirTicket.requestFocus();
            }/*else if( e.getKeyCode() == KeyEvent.VK_ENTER ) {
                FinalizarVenta(0);
            }*/
        }else if( e.getSource() == vv.tblAbiertos ){
            if( e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_F12 ){
                String nombrePendiente = null;
                int fila = 0;
                fila = vv.tblAbiertos.getSelectedRow();
                nombrePendiente = vv.tblAbiertos.getValueAt(fila, 0).toString();
                CargarDatosTicketActivo(nombrePendiente);
            }else if( e.getKeyCode() == KeyEvent.VK_ESCAPE ){
                cerrarModal(vv.dAbiertos);
            }
        }else if( e.getSource() == vv.tblListaCot ){
            if( e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_F12 ){
                String nombreCotizacion = null;
                int fila = 0;
                fila = vv.tblListaCot.getSelectedRow();
                nombreCotizacion = vv.tblListaCot.getValueAt(fila, 0).toString();
                CargarDatosListaCot(nombreCotizacion);
            }else if( e.getKeyCode() == KeyEvent.VK_ESCAPE ){
                cerrarModal(vv.dListaCot);
            }else if( e.getKeyCode() == KeyEvent.VK_F9 ){
                int fila = -1;
                fila = vv.tblListaCot.getSelectedRow();
                if( fila >= 0 ){
                    String nombreCotizacion = null;
                    nombreCotizacion = vv.tblListaCot.getValueAt(fila, 0).toString();

                    // Crear un arreglo de botones personalizados
                    Object[] opciones = {"Confirmar", "No"};

                    // Mostrar el mensaje de confirmación con los botones personalizados
                    int respuesta = JOptionPane.showOptionDialog(
                        vv, 
                        "¿Eliminar Cotización "+nombreCotizacion+"?", 
                        "Confirmación", 
                        JOptionPane.DEFAULT_OPTION, 
                        JOptionPane.QUESTION_MESSAGE, 
                        null, 
                        opciones, 
                        opciones[0] // Establecer "Confirmar" como opción predeterminada
                    );

                    // Procesar la respuesta
                    if (respuesta == 0) {
                        CancelarListaCot(nombreCotizacion);
                        try {
                            CargarListaCot();

                            if(vv.tblListaCot.getRowCount() > 0)
                                vv.tblListaCot.setRowSelectionInterval(0, 0);

                            vv.tblListaCot.requestFocus();

                        } catch (SQLException ex) {
                            Logger.getLogger(VentasFrameC.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }else if( e.getSource() == vv.txtNombrePendiente ){
            if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_F12 ){
                String nombrePendiente = null;
                nombrePendiente = vv.txtNombrePendiente.getText();

                if( nombrePendiente.equals("") ){
                    JOptionPane.showMessageDialog(vv, "Ingrese el nombre del ticket pendiente");
                    vv.txtNombrePendiente.requestFocus();
                }else{
                    int errores = 0;
                    errores = GuardarTicketPendiente(nombrePendiente);
                    if(errores == 0){
                        JOptionPane.showMessageDialog(vv, "Ticket Pendiente Guardado Correctamente.");
                        cerrarModal(vv.dPendientes);

                        FinalizarVenta(0);

                    }
                }
            }else if( e.getKeyCode() == KeyEvent.VK_ESCAPE ){
                cerrarModal(vv.dPendientes);
            }
        }else if( e.getSource() == vv.txtNombreCotizacion ){
            if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_F12 ){
                String nombrePendiente = null;
                nombrePendiente = vv.txtNombreCotizacion.getText();

                if( nombrePendiente.equals("") ){
                    JOptionPane.showMessageDialog(vv, "Ingrese el nombre de la cotización");
                    vv.txtNombreCotizacion.requestFocus();
                }else{
                    // Validar Nombre Corización
                    int validarCountCotizacion = 0;
                    validarCountCotizacion = ValidarCountCotizacion(nombrePendiente);

                    // System.out.println("Total: " + validarCountCotizacion);

                    if( validarCountCotizacion > 0 ){
                        JOptionPane.showMessageDialog(vv, "La cotización ya existe. \n Utilice otro nombre para la cotización.");
                        vv.txtNombreCotizacion.requestFocus();
                        return;
                    }

                    int errores = 0;
                    errores = GuardarCotizacion(nombrePendiente);
                    if(errores == 0){
                        JOptionPane.showMessageDialog(vv, "Cotización Guardada Correctamente.");
                        cerrarModal(vv.dCotizaciones);

                        FinalizarVenta(0);

                    }else{
                        JOptionPane.showMessageDialog(vv, "Utilice otro nombre para la cotización.");
                        vv.txtNombreCotizacion.requestFocus();
                    }
                }
                /////////////////////////////////////////////////////////////////////////////////
                /*String nombreCotizacion = null;
                nombreCotizacion = vv.txtNombreCotizacion.getText();

                if( nombreCotizacion.equals("") ){
                    JOptionPane.showMessageDialog(vv, "Ingrese el nombre de la cotización.");
                    vv.txtNombreCotizacion.requestFocus();
                }else{
                    int errores = 0;
                    errores = GuardarCotizacion(nombreCotizacion);
                    if(errores == 0){
                        JOptionPane.showMessageDialog(vv, "Cotización Guardada Correctamente.");
                        cerrarModal(vv.dCotizaciones);

                        FinalizarVenta(0);

                    }else{
                    JOptionPane.showMessageDialog(vv, "Utilice otro nombre para la cotización.");
                    vv.txtNombreCotizacion.requestFocus();
                }
                }*/
            }else if( e.getKeyCode() == KeyEvent.VK_ESCAPE ){
                cerrarModal(vv.dCotizaciones);
            }
        }else if( e.getSource() == vv.txtPorcentajeAplicar ){
            if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_F12 ){
                AplicarDescuentos();
            }else if( e.getKeyCode() == KeyEvent.VK_ESCAPE ){
                cerrarModal(vv.dDescuentos);
            }
        }else if( e.getSource() == vv.txtImporteAplicar ){
            if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_F12 ){
                AplicarDescuentos();
            }else if( e.getKeyCode() == KeyEvent.VK_ESCAPE ){
                cerrarModal(vv.dDescuentos);
            }
        }else if( e.getSource() == vv.txtTextoBusqueda ){
            if( e.getKeyCode() == KeyEvent.VK_ENTER ){
                CargarDetalleVentas();
            }
        }else if( e.getSource() == vv.cmb_nuevoCliente_AC ){
            if( e.getKeyCode() == KeyEvent.VK_F12 ){
                GuardarAsignarCliente();
            }else if( e.getKeyCode() == KeyEvent.VK_ESCAPE ){
                cerrarModal(vv.dAsignarCliente);
            }
        }else if( e.getSource() == vv.txt_cantidadAbono_RA || e.getSource() == vv.txt_comentarios_RA ){
            if( e.getKeyCode() == KeyEvent.VK_F12 || e.getKeyCode() == KeyEvent.VK_ENTER ){
                GuardarAbonoCredito();
            }else if( e.getKeyCode() == KeyEvent.VK_ESCAPE ){
                cerrarModal(vv.dAbonosCreditos);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getSource()== vv.txtbus){
            if(/*e.getKeyCode() == KeyEvent.VK_PLUS ||*/ e.getKeyCode() == KeyEvent.VK_ADD) {
                vv.txtbus.setText("");
            }else if(/*e.getKeyCode() == KeyEvent.VK_MINUS ||*/ e.getKeyCode() == KeyEvent.VK_SUBTRACT) {
                vv.txtbus.setText("");
            }
        }else if(e.getSource() == vv.txtdescuento){
            if (vv.txtdescuento.getText().length()== 3){
                e.consume();
            }
            double des = 0;
            double can = 0;
            double tot = 0;
            if(vv.txtdescuento.getText().isEmpty()){
                can = Double.parseDouble(vv.txtsubtotal.getText());
                vv.txttotal.setText(""+can);
                vv.txtpago.setText(""+can);
                vv.txtcambio.setText("0");
            }else{
                des = Double.parseDouble(vv.txtdescuento.getText());
                if(des > 100){
                    des = 100;
                    vv.txtdescuento.setText("100");
                }
                can = Double.parseDouble(vv.txtsubtotal.getText());
                tot = can * (des/100);
                tot = can - tot;
                vv.txttotal.setText(""+tot);
                vv.txtpago.setText(""+tot);
                // vv.txttotal.setText(""+Math.ceil(tot));
                // vv.txtpago.setText(""+Math.ceil(tot));
                vv.txtcambio.setText("0");
            }
        }else if(e.getSource() == vv.txtdescuentoCredito){
            if (vv.txtdescuentoCredito.getText().length()== 3){
                e.consume();
            }
            double des = 0;
            double can = 0;
            double tot = 0;
            if(vv.txtdescuentoCredito.getText().isEmpty()){
                can = Double.parseDouble(vv.txtsubtotalCredito.getText());
                vv.txttotalCredito.setText(""+can);
                vv.txtpagoCredito.setText(""+can);
                vv.txtcambioCredito.setText("0");
            }else{
                des = Double.parseDouble(vv.txtdescuentoCredito.getText());
                if(des > 100){
                    des = 100;
                    vv.txtdescuentoCredito.setText("100");
                }
                can = Double.parseDouble(vv.txtsubtotalCredito.getText());
                tot = can * (des/100);
                tot = can - tot;
                vv.txttotalCredito.setText(""+tot);
                vv.txtpagoCredito.setText(""+tot);
                // vv.txttotal.setText(""+Math.ceil(tot));
                // vv.txtpago.setText(""+Math.ceil(tot));
                vv.txtcambioCredito.setText("0");
            }
        }else if(e.getSource() == vv.txtpago){
            char caracter = e.getKeyChar();
            if (((caracter < '0') || (caracter > '9'))) {
                e.consume();
            }
            double tot = 0;
            double pag = 0;
            double cam = 0;
            if(vv.txtpago.getText().isEmpty()){
                vv.txtcambio.setText("-" + vv.txttotal.getText());
            }else{
                tot = Double.parseDouble(vv.txttotal.getText());
                pag = Double.parseDouble(vv.txtpago.getText());
                cam = pag - tot;
                vv.txtcambio.setText("" + cam);
            }
        }else if(e.getSource() == vv.txtpagoCredito){
            char caracter = e.getKeyChar();
            if (((caracter < '0') || (caracter > '9'))) {
                e.consume();
            }
            double tot = 0;
            double pag = 0;
            double cam = 0;
            if(vv.txtpagoCredito.getText().isEmpty()){
                vv.txtcambioCredito.setText("-" + vv.txttotalCredito.getText());
            }else{
                tot = Double.parseDouble(vv.txttotalCredito.getText());
                pag = Double.parseDouble(vv.txtpagoCredito.getText());
                cam = pag - tot;
                vv.txtcambioCredito.setText("" + cam);
            }
        }else if(e.getSource() == vv.txtpagoCredito){
            char caracter = e.getKeyChar();
            if (((caracter < '0') || (caracter > '9'))) {
                e.consume();
            }
            double tot = 0;
            double pag = 0;
            double cam = 0;
            if(vv.txtpagoCredito.getText().isEmpty()){
                vv.txtcambioCredito.setText("-" + vv.txttotalCredito.getText());
            }else{
                tot = Double.parseDouble(vv.txttotalCredito.getText());
                pag = Double.parseDouble(vv.txtpagoCredito.getText());
                cam = pag - tot;
                vv.txtcambioCredito.setText("" + cam);
            }
        }else if(e.getSource() == vv.txtBuscarProductos){
            
            
            if( e.getKeyCode() == KeyEvent.VK_UP ){
                // System.out.println("Se presionó tecla alta");
            } else if( e.getKeyCode() == KeyEvent.VK_DOWN ){
                // System.out.println("Se presionó tecla baja");
            } else {
                // System.out.println("Entró a seleccionar primer fila");
                CargarProductosBuscar(vv.txtBuscarProductos.getText());
                
                if(vv.tblBuscarProductos.getRowCount() > 0)
                    vv.tblBuscarProductos.setRowSelectionInterval(0, 0);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if( e.getSource() == vv.txtNombreCotView ){
            String nombreCotizacion = null;
            nombreCotizacion = vv.txtNombreCotView.getText();
            
            if( !nombreCotizacion.equals("") ){
                // JOptionPane.showMessageDialog(vv, "Imprimir Cotización");
                abrirModalImprimirCot();
            }
        }else if(e.getSource() == vv.pescobrar){
            if(vv.pescobrar.getSelectedIndex() == 0){
                vv.txtpago.requestFocus();
            }else if(vv.pescobrar.getSelectedIndex() == 1){
                //vv.txtreferencia1.requestFocus();
                //System.out.println("Se presiono el pes cobrar 1");
                BuscarClientesCredito();
                vv.txtPagoInicialCredito.setText("0");
                vv.txtPagoInicialCredito.requestFocus();
            }
        }else if(e.getSource() == vv.pesprincipal){
            /*if(VentasV.pesprincipal.getSelectedIndex() == 1){
                int f1 = 0;
                f1 = vv.tbllis_os.getRowCount();
                for (int i = 0; i < f1; i++) {
                    modeltLis1.removeRow(0);
                }
            }else */if(vv.pesprincipal.getSelectedIndex() == 2){
                // CargarCatPro();
                /*int f2 = 0;
                f2 = vv.tbllis_ven.getRowCount();
                for (int i = 0; i < f2; i++) {
                    modeltLisCV.removeRow(0);
                }*/
            }/*else if(VentasV.pesprincipal.getSelectedIndex() == 3){
                System.out.println("Entró a detalle de venta");
                CargarCatPro();
            }/*else if(VentasV.pesprincipal.getSelectedIndex() == 3){
                int f3 = 0;
                f3 = vv.tbllis_ord.getRowCount();
                for (int i = 0; i < f3; i++) {
                    modeltLisCO.removeRow(0);
                }
            }*/
            if(vv.pesprincipal.getSelectedIndex() == 3){
                // JOptionPane.showMessageDialog(vv, "Se presionó tab créditos");
                CargarClientesSaldos();
            }
        }else if(e.getSource() == vv.pesAplicarDescuento){
            // System.out.println("Clic en pestaña descuento");
            if(vv.pesAplicarDescuento.getSelectedIndex() == 0){
                // System.out.println("Clic en pestaña porcentaje");
                vv.txtPorcentajeAplicar.setText("");
                vv.txtPorcentajeAplicar.setText("0.0");
                vv.chbAplicarDesTodProd.setSelected(false);
                vv.txtPorcentajeAplicar.requestFocus();
            }else if(vv.pesAplicarDescuento.getSelectedIndex() == 1){
                // System.out.println("Clic en pestaña importe");
                // vv.txtImporteAplicar.setText("0.0");
                vv.txtImporteAplicar.requestFocus();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getSource() == vv.fechaDe){
            vv.fechaAl.getJCalendar().setMinSelectableDate(vv.fechaDe.getDate());
            vv.fechaAl.setDate(vv.fechaDe.getDate());
        }
    }
    
    public void CargarProductos(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        pm.setId(-4);
        pm.setCod(null);
        pm.setNom(null);
        pm.setSa(0);
        pm.setSm(0);
        pm.setPre(0);
        pm.setCat(null);
        pm.setEst(0);
        modelt = pm.CargarProductos();
        // TEXT AUTO COMPLETER
        textAutoCompleter = new TextAutoCompleter(vv.txtbus);
        textAutoCompleter.setCaseSensitive(false);
        for (int i = 0; i < modelt.getRowCount(); i++) {
            textAutoCompleter.addItem(modelt.getValueAt(i, 2) + " '" + modelt.getValueAt(i, 1));
        }
        //textAutoCompleter.
        
        // Remover
        /*vv.cmbbus.removeAllItems();
        vv.cmbbus.addItem("Escriba el Producto");
        for (int i = 0; i < modelt.getRowCount(); i++) {
            vv.cmbbus.addItem(modelt.getValueAt(i, 2) + " '" + modelt.getValueAt(i, 1));
        }*/
        //System.out.println("Filas: " + modelt.getRowCount());
        //System.out.println("Columnas: " + modelt.getColumnCount());
    }
    
    public void CargarLista(){
        modeltLis = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        modeltLis.addColumn("ID");//////// 0
        modeltLis.addColumn("Código");//// 1
        modeltLis.addColumn("Nombre");//// 2
        modeltLis.addColumn("Precio");//// 3
        modeltLis.addColumn("Cantidad");// 4
        modeltLis.addColumn("Subtotal");// 5
        modeltLis.addColumn("Id Cat");// 6
        vv.tbllis.setModel(modeltLis);
        vv.tbllis.getColumnModel().getColumn(0).setMaxWidth(0);
        vv.tbllis.getColumnModel().getColumn(0).setMinWidth(0);
        vv.tbllis.getColumnModel().getColumn(0).setPreferredWidth(0);
        vv.tbllis.getColumnModel().getColumn(1).setMaxWidth(150);
        vv.tbllis.getColumnModel().getColumn(1).setMinWidth(150);
        vv.tbllis.getColumnModel().getColumn(1).setPreferredWidth(150);
        vv.tbllis.getColumnModel().getColumn(3).setMaxWidth(100);
        vv.tbllis.getColumnModel().getColumn(3).setMinWidth(100);
        vv.tbllis.getColumnModel().getColumn(3).setPreferredWidth(100);
        vv.tbllis.getColumnModel().getColumn(4).setMaxWidth(100);
        vv.tbllis.getColumnModel().getColumn(4).setMinWidth(100);
        vv.tbllis.getColumnModel().getColumn(4).setPreferredWidth(100);
        vv.tbllis.getColumnModel().getColumn(5).setMaxWidth(100);
        vv.tbllis.getColumnModel().getColumn(5).setMinWidth(100);
        vv.tbllis.getColumnModel().getColumn(5).setPreferredWidth(100);
        vv.tbllis.getColumnModel().getColumn(6).setMaxWidth(0);
        vv.tbllis.getColumnModel().getColumn(6).setMinWidth(0);
        vv.tbllis.getColumnModel().getColumn(6).setPreferredWidth(0);
    }
    
    public String CargarProducto(String cod){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        pm.setId(-5);
        pm.setCod(cod);
        pm.setNom(null);
        pm.setSa(0);
        pm.setSm(0);
        pm.setPre(0);
        pm.setCat(null);
        pm.setEst(0);
        modelt = pm.CargarProductos();
        
        if(modelt.getRowCount()<=0){
            return "NO";
        }
        
        return modelt.getValueAt(0, 0).toString() + "_" + modelt.getValueAt(0, 6).toString() + "_" + modelt.getValueAt(0, 3).toString() + "_" + modelt.getValueAt(0, 2).toString() + "_" + modelt.getValueAt(0, 9).toString();
        
    }
    
    public double VerificarExisteTabla(String cod){
        int filas = 0;
        filas = vv.tbllis.getRowCount();
        if(filas <= 0){
            return 0;
        }else{
            for (int i = 0; i < filas; i++) {
                if(cod.equals(vv.tbllis.getValueAt(i, 1).toString())){
                    filaReem = 0;
                    filaReem = i;
                    return Double.parseDouble(vv.tbllis.getValueAt(i, 4).toString());
                }
            }
        }
        return 0;
    }
    
    public void CalcularTotales(){
        double tot_efe = 0;
        double tot_pro = 0;
        double sub_tot = 0;
        double can_pro = 0;
        int filas = 0;
        filas = vv.tbllis.getRowCount();
        vv.lblcan.setText("0");
        vv.lbltot.setText("$ 0.0");
        for (int i = 0; i < filas; i++) {
            can_pro = 0;
            sub_tot = 0;
            can_pro = Double.parseDouble(vv.tbllis.getValueAt(i, 4).toString());
            sub_tot = Double.parseDouble(vv.tbllis.getValueAt(i, 3).toString());
            sub_tot = sub_tot * can_pro;
            vv.tbllis.setValueAt(sub_tot, i, 5);
            tot_efe = tot_efe + Double.parseDouble(vv.tbllis.getValueAt(i, 5).toString());
            tot_pro = tot_pro + can_pro;
            vv.lbltot.setText("$ "+tot_efe);
            vv.lblcan.setText(""+tot_pro);
        }
    }
    
    public int imprimirTicket(boolean imprimir) throws IOException{
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        em.setId(-1);
        em.setNom(null);
        em.setCal(null);
        em.setNum(null);
        em.setCol(null);
        em.setTel(null);
        em.setCel(null);
        em.setMun(null);
        em.setEst(null);
        em.setTex(null);
        modelt = em.CargarEmpresa();
        
        Ticket ticket = new Ticket();
        
        if(modelt.getRowCount()<=0){
            
        }else{
            Ticket.AddCabecera(modelt.getValueAt(0, 1).toString());
            Ticket.AddCabecera(Ticket.DarEspacio());
            Ticket.AddCabecera(modelt.getValueAt(0, 9).toString());
            Ticket.AddCabecera(Ticket.DarEspacio());
            Ticket.AddCabecera(modelt.getValueAt(0, 2).toString() + " #"+modelt.getValueAt(0, 3).toString() + " " + modelt.getValueAt(0, 4).toString());
            Ticket.AddCabecera(Ticket.DarEspacio());
            Ticket.AddCabecera(modelt.getValueAt(0, 7).toString() + ", " + modelt.getValueAt(0, 8).toString());
            Ticket.AddCabecera(Ticket.DarEspacio());
            Ticket.AddCabecera("WhatsApp: " + modelt.getValueAt(0, 6).toString() );
            Ticket.AddCabecera(Ticket.DarEspacio());
        }
        Ticket.AddCabecera(Ticket.DibujarLinea(29));
        Ticket.AddCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera("Caja UNICA - Ticket # "+vm.getFol_dven());
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera("Le Atiende: " + GlobalVaribales.getInstance().nombreLogueado);
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        ClientesListM clienteSeleccionado = (ClientesListM) vv.cmbCliente.getSelectedItem();
        int idClienteSeleccionado = clienteSeleccionado.getId();
        if( idClienteSeleccionado == 0 )
            Ticket.AddSubCabecera("Tipo de Venta: CONTADO");
        else 
            Ticket.AddSubCabecera("Tipo de Venta: CREDITO");
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera("Cliente: " + clienteSeleccionado.getNombre());
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        if( vv.pescobrar.getSelectedIndex() == 0 )
            Ticket.AddSubCabecera("Metodo de Pago: " + vv.cmbFormaPago.getSelectedItem().toString().toUpperCase());
        else if( vv.pescobrar.getSelectedIndex() == 1 )
            Ticket.AddSubCabecera("Método de Pago: " + vv.cmbFormaPagoCredito.getSelectedItem().toString().toUpperCase());
        else if( vv.pescobrar.getSelectedIndex() == 2 )
            Ticket.AddSubCabecera("Método de Pago: " + vv.cmbFormaPagoCredito.getSelectedItem().toString().toUpperCase());
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera(""+fech_reg_Tick);
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera(Ticket.DibujarLinea(29));
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        
        if( !nombreCliente_Pen.equals("") && !telefonoCliente_Pen.equals("") ){
            Ticket.AddSubCabecera("VENTA PENDIENTE");
            Ticket.AddSubCabecera(Ticket.DarEspacio());
            Ticket.AddSubCabecera("Cliente: "+nombreCliente_Pen);
            Ticket.AddSubCabecera(Ticket.DarEspacio());
            Ticket.AddSubCabecera("Telefono: "+telefonoCliente_Pen);
            Ticket.AddSubCabecera(Ticket.DarEspacio());
            Ticket.AddSubCabecera(Ticket.DibujarLinea(29));
            Ticket.AddSubCabecera(Ticket.DarEspacio());
        }
        
        int filas = 0;
        filas = vv.tbllis.getRowCount();
        for (int i = 0; i < filas; i++) {
            Ticket.AddItem(vv.tbllis.getValueAt(i, 4).toString(),vv.tbllis.getValueAt(i,2).toString(),"$ "+vv.tbllis.getValueAt(i, 5).toString());
            Ticket.AddItem("","",Ticket.DarEspacio());
        }
        if( vv.pescobrar.getSelectedIndex() == 0 ){
            Ticket.AddTotal("",Ticket.DibujarLinea(29));
            Ticket.AddTotal("",Ticket.DarEspacio());
            Ticket.AddTotal("No. Productos",vv.lblcan.getText());
            Ticket.AddTotal("",Ticket.DarEspacio());
            Ticket.AddTotal("Subtotal","$ "+vv.txtsubtotal.getText());
            Ticket.AddTotal("",Ticket.DarEspacio());
            Ticket.AddTotal("Descuento",vv.txtdescuento.getText()+" %");
            Ticket.AddTotal("",Ticket.DarEspacio());
            Ticket.AddTotal("",Ticket.DarEspacio());
            Ticket.AddTotal("TOTAL","$ "+vv.txttotal.getText());
            Ticket.AddTotal("",Ticket.DarEspacio());
            Ticket.AddTotal("Recibido","$ "+vv.txtpago.getText());
            Ticket.AddTotal("",Ticket.DarEspacio());
            Ticket.AddTotal("Cambio","$ "+vv.txtcambio.getText());
            Ticket.AddTotal("",Ticket.DarEspacio());
            Ticket.AddPieLinea(Ticket.DibujarLinea(29));
            Ticket.AddPieLinea(Ticket.DarEspacio());
        }else if( vv.pescobrar.getSelectedIndex() == 1 ){
            Ticket.AddTotal("",Ticket.DibujarLinea(29));
            Ticket.AddTotal("",Ticket.DarEspacio());
            Ticket.AddTotal("No. Productos",vv.lblcan.getText());
            Ticket.AddTotal("",Ticket.DarEspacio());
            Ticket.AddTotal("Subtotal","$ "+vv.txtsubtotalCredito.getText());
            Ticket.AddTotal("",Ticket.DarEspacio());
            Ticket.AddTotal("Descuento",vv.txtdescuentoCredito.getText()+" %");
            Ticket.AddTotal("",Ticket.DarEspacio());
            Ticket.AddTotal("",Ticket.DarEspacio());
            Ticket.AddTotal("TOTAL","$ "+vv.txttotalCredito.getText());
            Ticket.AddTotal("",Ticket.DarEspacio());
            Ticket.AddTotal("Recibido","$ "+vv.txtPagoInicialCredito.getText());
            Ticket.AddTotal("",Ticket.DarEspacio());
            Ticket.AddTotal("Cambio","$ "+vv.txtcambioCredito.getText());
            Ticket.AddTotal("",Ticket.DarEspacio());
            Ticket.AddPieLinea(Ticket.DibujarLinea(29));
            Ticket.AddPieLinea(Ticket.DarEspacio());
        }
        
        if(modelt.getRowCount() <= 0){
            
        }else{
            Ticket.AddPieLinea(Ticket.DarEspacio());
            Ticket.AddPieLinea(Ticket.DarEspacio());
            Ticket.AddPieLinea(Ticket.DarEspacio());
            Ticket.AddPieLinea(Ticket.DarEspacio());
        }
        
        int a = 0;
        a = Ticket.ImprimirDocumento(imprimir);
        if(a == 1){
            return 1;
        }
        return 0;
    
    }
    
    public int reImprimirTicket(boolean imprimir) throws IOException{
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        em.setId(-1);
        em.setNom(null);
        em.setCal(null);
        em.setNum(null);
        em.setCol(null);
        em.setTel(null);
        em.setCel(null);
        em.setMun(null);
        em.setEst(null);
        em.setTex(null);
        modelt = em.CargarEmpresa();
        
        Ticket ticket = new Ticket();
        
        if(modelt.getRowCount()<=0){
            
        }else{
            Ticket.AddCabecera(modelt.getValueAt(0, 1).toString());
            Ticket.AddCabecera(Ticket.DarEspacio());
            Ticket.AddCabecera(modelt.getValueAt(0, 9).toString());
            Ticket.AddCabecera(Ticket.DarEspacio());
            Ticket.AddCabecera(modelt.getValueAt(0, 2).toString() + " #"+modelt.getValueAt(0, 3).toString() + " " + modelt.getValueAt(0, 4).toString());
            Ticket.AddCabecera(Ticket.DarEspacio());
            Ticket.AddCabecera(modelt.getValueAt(0, 7).toString() + ", " + modelt.getValueAt(0, 8).toString());
            Ticket.AddCabecera(Ticket.DarEspacio());
            Ticket.AddCabecera("WhatsApp: " + modelt.getValueAt(0, 6).toString() );
            Ticket.AddCabecera(Ticket.DarEspacio());
        }
        Ticket.AddCabecera(Ticket.DibujarLinea(29));
        Ticket.AddCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera("Caja UNICA - Ticket # "+vv.c_folio_ve.getText());
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera("Le Atiende: " + vv.c_usuarioregistra_ve.getText());
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera("Tipo de Venta: " + vv.c_tipoventa_ve.getText());
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera("Cliente: " + vv.c_cliente_ve.getText());
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera("Metodo de Pago: " + vv.c_metodopago_ve.getText());
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        
        String fechaOriginal = vv.c_fechaventa_ve.getText();
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        Date fecha = null;
        try {
            fecha = formatoEntrada.parse(fechaOriginal);
        } catch (ParseException ex) {
            Logger.getLogger(VentasFrameC.class.getName()).log(Level.SEVERE, null, ex);
        }
        SimpleDateFormat formatoSalida = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fechaFormateada = formatoSalida.format(fecha);
        
        Ticket.AddSubCabecera("" + fechaFormateada);
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera(Ticket.DibujarLinea(29));
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        
        double totalProductos = 0;
        int filas = 0;
        filas = vv.c_tbllis_ve.getRowCount();
        for (int i = 0; i < filas; i++) {
            //Ticket.AddItem(vv.c_tbllis_ve.getValueAt(i, 4).toString(),vv.tbllis.getValueAt(i,2).toString(),"$ "+vv.tbllis.getValueAt(i, 5).toString());
            if(vv.c_tbllis_ve.getValueAt(i, 5).toString().equals("Activo")){
                Ticket.AddItem(vv.c_tbllis_ve.getValueAt(i, 3).toString(),vv.c_tbllis_ve.getValueAt(i,1).toString(),"$ "+vv.c_tbllis_ve.getValueAt(i, 4).toString());
                Ticket.AddItem("","",Ticket.DarEspacio());
                totalProductos = totalProductos + Double.parseDouble(vv.c_tbllis_ve.getValueAt(i, 3).toString());
            }
        }
        
        Ticket.AddTotal("",Ticket.DibujarLinea(29));
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("No. Productos", String.valueOf(totalProductos) );
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("Subtotal",vv.c_subtotal_ve.getText());
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("Descuento",vv.c_descuento_ve.getText());
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("TOTAL",vv.c_total_ve.getText());
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("Recibido",vv.c_total_ve.getText());
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("Cambio","$ 0");
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddPieLinea(Ticket.DibujarLinea(29));
        Ticket.AddPieLinea(Ticket.DarEspacio());
        if(modelt.getRowCount() <= 0){
            
        }else{
            //Ticket.AddPieLinea(modelt.getValueAt(0, 9).toString());
            // Ticket.AddPieLinea(Ticket.DarEspacio());
            //Ticket.AddPieLinea(Ticket.DarEspacio());
            Ticket.AddPieLinea(Ticket.DarEspacio());
            Ticket.AddPieLinea(Ticket.DarEspacio());
            Ticket.AddPieLinea(Ticket.DarEspacio());
            Ticket.AddPieLinea(Ticket.DarEspacio());
        }
        
        /*Ticket.AddPieLinea("Contamos con Sistema de Apartado y Envíos a Domicilio");
        Ticket.AddPieLinea(Ticket.DarEspacio());
        Ticket.AddPieLinea("Horario: L-V 11:00 am - 7:30 pm");
        Ticket.AddPieLinea(Ticket.DarEspacio());
        Ticket.AddPieLinea("Sábados 11:00 am - 4:00 pm");
        Ticket.AddPieLinea(Ticket.DarEspacio());
        Ticket.AddPieLinea("Gracias por su compra!!!");
        Ticket.AddPieLinea(Ticket.DarEspacio());*/
        
        int a = 0;
        a = Ticket.ImprimirDocumento(imprimir);
        if(a == 1){
            return 1;
        }
        return 0;
    
    }
    
    public int imprimirEntradaSalida(boolean imprimir, String tipo, double monto, String comentarios) throws IOException{
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        em.setId(-1);
        em.setNom(null);
        em.setCal(null);
        em.setNum(null);
        em.setCol(null);
        em.setTel(null);
        em.setCel(null);
        em.setMun(null);
        em.setEst(null);
        em.setTex(null);
        modelt = em.CargarEmpresa();
        
        Date fechaActual = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        String fechaFormateada = formato.format(fechaActual);
        
        Ticket ticket = new Ticket();
        
        if(modelt.getRowCount()<=0){
            
        }else{
            Ticket.AddCabecera(modelt.getValueAt(0, 1).toString());
            Ticket.AddCabecera(Ticket.DarEspacio());
            Ticket.AddCabecera(modelt.getValueAt(0, 9).toString());
            Ticket.AddCabecera(Ticket.DarEspacio());
            Ticket.AddCabecera(modelt.getValueAt(0, 2).toString() + " #"+modelt.getValueAt(0, 3).toString() + " " + modelt.getValueAt(0, 4).toString());
            Ticket.AddCabecera(Ticket.DarEspacio());
            if(modelt.getValueAt(0, 5) == null){
                
            }else{
                // Ticket.AddCabecera("Telefono: " + modelt.getValueAt(0, 5).toString());
                // Ticket.AddCabecera(Ticket.DarEspacio());
            }
            // Ticket.AddCabecera("Celular: " + modelt.getValueAt(0, 6).toString());
            // Ticket.AddCabecera(Ticket.DarEspacio());
            Ticket.AddCabecera(modelt.getValueAt(0, 7).toString() + ", " + modelt.getValueAt(0, 8).toString());
            Ticket.AddCabecera(Ticket.DarEspacio());
            Ticket.AddCabecera("WhatsApp: " + modelt.getValueAt(0, 6).toString() );
            Ticket.AddCabecera(Ticket.DarEspacio());
            //ev.txttexto.setText(modelt.getValueAt(0, 9).toString());
        }
        //Ticket.AddCabecera("BOUTIQUE GAMBOA");
        //Ticket.AddCabecera(Ticket.DarEspacio());
        //Ticket.AddCabecera("Av. De la Cultura # 79 Colonia Cd. del Valle");
        //Ticket.AddCabecera(Ticket.DarEspacio());
        //Ticket.AddCabecera("Teléfono: 311-372-93-61");
        //Ticket.AddCabecera(Ticket.DarEspacio());
        //Ticket.AddCabecera("Tepic, Nayarit");
        //Ticket.AddCabecera(Ticket.DarEspacio());
        Ticket.AddCabecera(Ticket.DibujarLinea(29));
        Ticket.AddCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera("Caja UNICA");
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera("Cajero: " + GlobalVaribales.getInstance().nombreLogueado);
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera(fechaFormateada);
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera("");
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera(Ticket.DibujarLinea(29));
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        
        Ticket.AddSubCabecera("Monto de " + tipo + ": " +String.valueOf(monto));
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera("Comentarios:");
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera(comentarios);
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        
        /* double totalProductos = 0;
        int filas = 0;
        filas = vv.c_tbllis_ve.getRowCount();
        for (int i = 0; i < filas; i++) {
            //Ticket.AddItem(vv.c_tbllis_ve.getValueAt(i, 4).toString(),vv.tbllis.getValueAt(i,2).toString(),"$ "+vv.tbllis.getValueAt(i, 5).toString());
            Ticket.AddItem(vv.c_tbllis_ve.getValueAt(i, 3).toString(),vv.c_tbllis_ve.getValueAt(i,1).toString(),"$ "+vv.c_tbllis_ve.getValueAt(i, 4).toString());
            Ticket.AddItem("","",Ticket.DarEspacio());
            totalProductos = totalProductos + Double.parseDouble(vv.c_tbllis_ve.getValueAt(i, 3).toString());
        } */
        
        /*Ticket.AddTotal("",Ticket.DibujarLinea(29));
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("Monto de " + tipo + ":", String.valueOf(monto) );
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("Comentarios:","");
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("",comentarios);
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("TOTAL",vv.c_total_ve.getText());
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("Recibido",vv.c_total_ve.getText());
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("Cambio","$ 0");
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddPieLinea(Ticket.DibujarLinea(29));
        Ticket.AddPieLinea(Ticket.DarEspacio());*/
        if(modelt.getRowCount() <= 0){
            
        }else{
            //Ticket.AddPieLinea(modelt.getValueAt(0, 9).toString());
            // Ticket.AddPieLinea(Ticket.DarEspacio());
            //Ticket.AddPieLinea(Ticket.DarEspacio());
            Ticket.AddPieLinea(Ticket.DarEspacio());
            Ticket.AddPieLinea(Ticket.DarEspacio());
            Ticket.AddPieLinea(Ticket.DarEspacio());
            Ticket.AddPieLinea(Ticket.DarEspacio());
        }
        
        /*Ticket.AddPieLinea("Contamos con Sistema de Apartado y Envíos a Domicilio");
        Ticket.AddPieLinea(Ticket.DarEspacio());
        Ticket.AddPieLinea("Horario: L-V 11:00 am - 7:30 pm");
        Ticket.AddPieLinea(Ticket.DarEspacio());
        Ticket.AddPieLinea("Sábados 11:00 am - 4:00 pm");
        Ticket.AddPieLinea(Ticket.DarEspacio());
        Ticket.AddPieLinea("Gracias por su compra!!!");
        Ticket.AddPieLinea(Ticket.DarEspacio());*/
        
        int a = 0;
        a = Ticket.ImprimirDocumento(imprimir);
        if(a == 1){
            return 1;
        }
        return 0;
    
    }
    
    public int imprimirTicketCotizacion(boolean imprimir) throws IOException{
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        DefaultTableModel modelt1 = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        em.setId(-1);
        em.setNom(null);
        em.setCal(null);
        em.setNum(null);
        em.setCol(null);
        em.setTel(null);
        em.setCel(null);
        em.setMun(null);
        em.setEst(null);
        em.setTex(null);
        modelt = em.CargarEmpresa();
        
        Ticket ticket = new Ticket();
        
        if(modelt.getRowCount()<=0){
            
        }else{
            Ticket.AddCabecera(modelt.getValueAt(0, 1).toString());
            Ticket.AddCabecera(Ticket.DarEspacio());
            Ticket.AddCabecera(modelt.getValueAt(0, 9).toString());
            Ticket.AddCabecera(Ticket.DarEspacio());
            Ticket.AddCabecera(modelt.getValueAt(0, 2).toString() + " #"+modelt.getValueAt(0, 3).toString() + " " + modelt.getValueAt(0, 4).toString());
            Ticket.AddCabecera(Ticket.DarEspacio());
            Ticket.AddCabecera(modelt.getValueAt(0, 7).toString() + ", " + modelt.getValueAt(0, 8).toString());
            Ticket.AddCabecera(Ticket.DarEspacio());
            Ticket.AddCabecera("WhatsApp: " + modelt.getValueAt(0, 6).toString() );
            Ticket.AddCabecera(Ticket.DarEspacio());
        }
        Ticket.AddCabecera(Ticket.DibujarLinea(29));
        Ticket.AddCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera("Le Atiende: " + GlobalVaribales.getInstance().nombreLogueado);
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera("======== COTIZACION ========");
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera("Nombre: " + vv.txtNombreCotView.getText());
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateTime = dateFormat.format(currentDate);
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera(""+currentDateTime);
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera(Ticket.DibujarLinea(29));
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        
        com.setParam(2);
        com.setNombreCotizacion(vv.txtNombreCotView.getText());
        try {
            modelt1 = com.CargarCotizacion();
        } catch (SQLException ex) {
            Logger.getLogger(VentasFrameC.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int filasModel1 = 0;
        filasModel1 = modelt1.getRowCount();
        double noProductos = 0;
        double subTotal = 0;
        for (int i = 0; i < filasModel1; i++) {
            Ticket.AddItem(modelt1.getValueAt(i, 4).toString(),modelt1.getValueAt(i,2).toString(),"$ "+modelt1.getValueAt(i, 5).toString());
            Ticket.AddItem("","",Ticket.DarEspacio());
            noProductos = noProductos + Double.parseDouble(modelt1.getValueAt(i, 4).toString());
            subTotal = subTotal + Double.parseDouble(modelt1.getValueAt(i, 5).toString());
        }
        
        /*for (int i = 0; i < filas; i++) {
            Ticket.AddItem(vv.tbllis.getValueAt(i, 4).toString(),vv.tbllis.getValueAt(i,2).toString(),"$ "+vv.tbllis.getValueAt(i, 5).toString());
            Ticket.AddItem("","",Ticket.DarEspacio());
        }*/
        
        Ticket.AddTotal("",Ticket.DibujarLinea(29));
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("No. Productos",String.valueOf(noProductos));
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("Subtotal","$ "+String.valueOf(subTotal));
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddTotal("TOTAL","$ "+String.valueOf(subTotal));
        Ticket.AddTotal("",Ticket.DarEspacio());
        Ticket.AddPieLinea(Ticket.DibujarLinea(29));
        Ticket.AddPieLinea(Ticket.DarEspacio());
        
        if(modelt.getRowCount() <= 0){
            
        }else{
            Ticket.AddPieLinea(Ticket.DarEspacio());
            Ticket.AddPieLinea(Ticket.DarEspacio());
            Ticket.AddPieLinea(Ticket.DarEspacio());
            Ticket.AddPieLinea(Ticket.DarEspacio());
        }
        
        int a = 0;
        a = Ticket.ImprimirDocumento(imprimir);
        if(a == 1){
            return 1;
        }
        return 0;
    
    }
    
    public void imprimirTicketNoSeUsa(JTable tbllis, String subTotal, String total, String dineroR, String devolucion){
        /*try {

            PrinterMatrix printer = new PrinterMatrix();
            Extenso e = new Extenso();

            e.setNumber(20.30);
            //Definir el tamanho del papel para la impresion de dinamico y 32 columnas
            int filas = tbllis.getRowCount();
            int tamaño = filas+40;
            printer.setOutSize(tamaño, 80);

            //Imprimir = 1ra linea de la columa de 1 a 32
            printer.printTextWrap(0, 1, 5, 80, "===================================================================");
            printer.printTextWrap(1, 1, 35, 80, "CARNIVOROS"); //Nombre establecimiento
            printer.printTextWrap(3, 1, 31, 80, "Barrio Bellavista"); //Barrio
            printer.printTextWrap(4, 1, 37, 80, "Clle 47"); //Direccion
            printer.printTextWrap(5, 1, 38, 80, "12345"); //Codigo Postal
            printer.printTextWrap(6, 1, 10, 41, "Fecha"); //Aqui va la fecha de recibo
            printer.printTextWrap(6, 1, 38, 80, "Hora"); //Aqui va la hora de recibo
            printer.printTextWrap(7, 1, 3, 80, "Numero"); //Numero del recibo - FACTURA O PEDIDO
            printer.printTextWrap(8, 1, 3, 80, "Atiende"); //Nombre Cajero
            printer.printTextWrap(9, 1, 3, 80, "Cliente");//Nombre del Cliente
            printer.printTextWrap(10,1, 5, 80, "——————————–——————————–——————————–——————————–——–———–——–—–———–——–———");
            printer.printTextWrap(11,1, 7, 80, "ID   DESCRIPCION                        CANT           PRECIO");
            printer.printTextWrap(12,1, 0, 80, " ");

            for (int i = 0; i < filas; i++) {
                int p = 13+i; //Fila

                printer.printTextWrap(p , 1, 7, 19 , tbllis.getValueAt(i,0).toString());
                printer.printTextWrap(p , 1, 12, 42 , tbllis.getValueAt(i,1).toString());
                printer.printTextWrap(p , 1, 47, 49, tbllis.getValueAt(i,2).toString());

                String pre= printer.alinharADireita(10, tbllis.getValueAt(i,3).toString());
                printer.printTextWrap(p , 1, 57, 80, pre);

                //String inp= printer.alinharADireita(7,punto_Venta.jtbl_venta.getValueAt(i,6).toString());
                //printer.printTextWrap(p , 1, 25, 32, inp);
            }
            DecimalFormat formateador = new DecimalFormat("#.###");

            String sub= printer.alinharADireita(10, subTotal);
            printer.printTextWrap(filas+17, 1, 5, 80, "Subtotal: ");
            printer.printTextWrap(filas+17, 1, 20, 80, "$"+sub);

            String tot= printer.alinharADireita(10, total);
            printer.printTextWrap(filas+18, 1, 5, 80, "Total a pagar: ");
            printer.printTextWrap(filas+18, 1, 20, 80, "$"+tot);

            String efe= printer.alinharADireita(10,dineroR);
            printer.printTextWrap(filas+19, 1, 5, 80, "Efectivo : ");
            printer.printTextWrap(filas+19, 1, 20, 80, "$"+efe);

            String cam= printer.alinharADireita(10,devolucion);
            printer.printTextWrap(filas+20, 1, 5, 80, "Cambio : ");
            printer.printTextWrap(filas+20, 1, 20, 80, "$"+ cam);

            printer.printTextWrap(filas+21, 1, 5, 80, "——————————–——————————–——————————–——————————–——–———–——–—–———–——–———");
            printer.printTextWrap(filas+22, 1, 26,80, "!Gracias por su preferencia!");
            printer.printTextWrap(filas+23, 1, 30, 80, "WorkIt App - v1.0.0");
            printer.printTextWrap(filas+24, 1, 31, 80, "Software a Medida");
            printer.printTextWrap(filas+25, 1, 25, 80, "Contacto: workitapp@gmail.com");
            printer.printTextWrap(filas+26, 1, 5,80, "===================================================================");


            ///CREAR ARCHIVO EN CARPETA DEL PROYECTO PARA PEDIDOS
            printer.toFile("C:\\tmp\\impresion.txt");
            FileInputStream inputStream = null;

            try {
                inputStream = new FileInputStream("C:\\tmp\\impresion.txt");
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al guardar");
            }
            if (inputStream == null) {
                return;
            }

            DocFlavor docFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
            Doc document = new SimpleDoc(inputStream, docFormat, null);
            PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();
            PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();

            if (defaultPrintService != null) {
                DocPrintJob printJob = defaultPrintService.createPrintJob();
                try {
                    printJob.print(document, attributeSet);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                System.err.println("No existen impresoras instaladas");
            }

            inputStream.close();
            //imprimirFin(subTotal, total, dineroR, devolucion); //ESTE METODO NO SE UTILIZARA

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al imprimir "+e);
        }*/
    }
    
    public int ActualizarEstatus_OS(int filaOS){
        int id_ven = 0;
        String fol_ven = null;
        double tot_ven = 0;
        int can_ven = 0;
        int des_ven = 0;
        String fec_ent = null;
        String dir_ent = null;
        String cli_ent = null;
        int met_pag = 0;
        int tip_ven = 0;
        String fec_reg = null;
        int sta_ven = 0;
        String ref_ven = null;
        String fec_surtido = null;
        String fec_cancela = null;
        
        id_ven = -4;
        //fol_ven = vv.tbllis_os.getValueAt(filaOS, 1).toString();
        sta_ven = 2;
        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        fec_surtido = sdf.format(dt);
                
        vm.setId_ven(id_ven);
        vm.setFol_ven(fol_ven);
        vm.setTot_ven(tot_ven);
        vm.setCan_ven(can_ven);
        vm.setDes_ven(des_ven);
        vm.setFec_ent(fec_ent);
        vm.setDir_ent(dir_ent);
        vm.setCli_ent(cli_ent);
        vm.setMet_pag(met_pag);
        vm.setTip_ven(tip_ven);
        vm.setFec_reg(fec_reg);
        vm.setSta_ven(sta_ven);
        vm.setRef_ven(ref_ven);
        vm.setFec_surtido(fec_surtido);
        vm.setFec_cancela(fec_cancela);
        
        if(vm.GuardarVentaOrden()){
            DefaultTableModel modeltDetalle = new DefaultTableModel();
            int id = 0;
            double tot = 0;
            int can = 0;
            int idpro = 0;
            String fol_dven = null;
            id = -2;
            tot = 0;
            can = 0;
            idpro = 0;
            fol_dven = fol_ven;
            vm.setId(id);
            vm.setTot(tot);
            vm.setCan(can);
            vm.setId_pro(idpro);
            vm.setFol_dven(fol_dven);
            modeltDetalle = vm.CargarDetalleVenta();
            int contador = 0;
            
            for (int i = 0; i < modeltDetalle.getRowCount(); i++) {
                int id_Pro = 0;
                int cant = 0;
                id_Pro = Integer.parseInt(modeltDetalle.getValueAt(i, 3).toString());
                cant = Integer.parseInt(modeltDetalle.getValueAt(i, 2).toString());
                pm.setId(-6);
                pm.setCod(null);
                pm.setNom(null);
                pm.setSa(cant);
                pm.setSm(id_Pro);
                pm.setSx(0);
                pm.setPre(0);
                pm.setCat(null);
                pm.setEst(0);
                if(pm.GuardarProductos()){
                    
                }else{
                    contador = contador + 1;
                }
            }
            if(contador == 0){
                return 1;
            }else{
                return 0;
            }
        }
        return 0;
    }
    
    /*SI se usa*/ public int GuardarVenta(){
        int id_ven = 0;
        String fol_ven = null;
        double tot_ven = 0;
        double can_ven = 0;
        double des_ven = 0;
        int met_pag = 0;
        int tip_ven = 0;
        String fec_reg = null;
        int sta_ven = 0;
        String ref_ven = null;
        double subtot_ven = 0;
        double pagoInicialCredito = 0;
        int idClienteCredito = 0;
        int idCliente = 0;
        
        id_ven = 0;
        fol_ven = null;
        can_ven = Double.parseDouble(vv.lblcan.getText());
        
        met_pag = vv.cmbFormaPago.getSelectedIndex();
        //tip_ven = vv.pesprincipal.getSelectedIndex();
        tip_ven = vv.pescobrar.getSelectedIndex();
        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.text.SimpleDateFormat sdf_T = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        fec_reg = sdf.format(dt);
        fech_reg_Tick = sdf_T.format(dt);
        sta_ven = estatusVenta;
        ref_ven = null;
        
        if( tip_ven == 0 ){
            subtot_ven = Double.parseDouble(vv.txtsubtotal.getText());
            tot_ven = Double.parseDouble(vv.txttotal.getText());
            des_ven = Double.parseDouble(vv.txtdescuento.getText());
        }else if( tip_ven == 1 ){
            subtot_ven = Double.parseDouble(vv.txtsubtotalCredito.getText());
            tot_ven = Double.parseDouble(vv.txttotalCredito.getText());
            des_ven = Double.parseDouble(vv.txtdescuentoCredito.getText());
            // pagoInicialCredito = Double.parseDouble(vv.txtPagoInicialCredito.getText());
            
            if(vv.txtPagoInicialCredito.getText().isEmpty()){
                pagoInicialCredito = 0;
            }else{
                pagoInicialCredito = Double.parseDouble(vv.txtPagoInicialCredito.getText());
            }
            
            /*if( pagoInicialCredito >= tot_ven  ){
                JOptionPane.showMessageDialog(vv, "El pago inicial debe ser menor al total.");
                return 2;
            }*/
            
            ClientesListM clienteSeleccionado = (ClientesListM) vv.cmbClienteCredito.getSelectedItem();
            idClienteCredito = clienteSeleccionado.getId();
        }
        
        ClientesListM clienteSeleccionado = (ClientesListM) vv.cmbCliente.getSelectedItem();
        idCliente = clienteSeleccionado.getId();
        
        if(idCliente == 0)
            tip_ven = 0;
        else
            tip_ven = 1;
        
        vm.setId_ven(id_ven);
        vm.setFol_ven(fol_ven);
        vm.setTot_ven(tot_ven);
        vm.setCan_ven(can_ven);
        vm.setDes_ven(des_ven);
        vm.setMet_pag(met_pag);
        vm.setTip_ven(tip_ven);
        vm.setFec_reg(fec_reg);
        vm.setSta_ven(sta_ven);
        vm.setRef_ven(ref_ven);
        vm.setSubtot_ven(subtot_ven);
        vm.setPagoInicialCredito(pagoInicialCredito);
        vm.setIdCliente(idCliente);
        vm.setNombreCliente_P(nombreCliente_Pen);
        vm.setTelefonoCliente_P(telefonoCliente_Pen);
        
        if(vm.GuardarVentaOrden()){
            return 1;
        }
        return 0;
    }
    
    /*SI se usa*/ public int GuardarDetalleVenta(){
        int filasDet = 0;
        int id = 0;
        double tot = 0;
        double can = 0;
        int idpro = 0;
        int contador = 0;
        filasDet = vv.tbllis.getRowCount();
        for (int i = 0; i < filasDet; i++) {
            id = 0;
            tot = 0;
            can = 0;
            idpro = 0;
            
            id = 0;
            tot = Double.parseDouble(vv.tbllis.getValueAt(i, 5).toString());
            can = Double.parseDouble(vv.tbllis.getValueAt(i, 4).toString());
            idpro = Integer.parseInt(vv.tbllis.getValueAt(i, 0).toString());
            
            vm.setId(id);
            vm.setTot(tot);
            vm.setCan(can);
            vm.setId_pro(idpro);
            
            if(!vm.GuardarDetalleVenta()){
                contador = contador + 1;
            }            
        }
        if(contador > 0){
            return contador;
        }
        return 0;
    }
    
    public void CargarOrdenesServicio(){
        /*DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        int id_ven = 0;
        String fol_ven = null;
        double tot_ven = 0;
        int can_ven = 0;
        int des_ven = 0;
        String fec_ent = null;
        String dir_ent = null;
        String cli_ent = null;
        int met_pag = 0;
        int tip_ven = 0;
        String fec_reg = null;
        int sta_ven = 0;
        String ref_ven = null;
        
        id_ven = -1;
        tip_ven = 1;
        Date fecha_de;
        Date fecha_al;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        fecha_de = vv.dtc_de.getDate();
        fecha_al = vv.dtc_al.getDate();
        fec_ent = sdf.format(fecha_de)+" 00:00:00";
        fec_reg = sdf.format(fecha_al)+" 23:59:59";
        switch (vv.cmbest_os.getSelectedIndex()) {
            case 0:
                ref_ven = "1,2";
                break;
            case 1:
                ref_ven = "1";
                break;
            case 2:
                ref_ven = "2";
                break;
            default:
                break;
        }
        
        vm.setId_ven(id_ven);
        vm.setFol_ven(fol_ven);
        vm.setTot_ven(tot_ven);
        vm.setCan_ven(can_ven);
        vm.setDes_ven(des_ven);
        vm.setFec_ent(fec_ent);
        vm.setDir_ent(dir_ent);
        vm.setCli_ent(cli_ent);
        vm.setMet_pag(met_pag);
        vm.setTip_ven(tip_ven);
        vm.setFec_reg(fec_reg);
        vm.setSta_ven(sta_ven);
        vm.setRef_ven(ref_ven);
        
        modeltLis1 = vm.CargarOrdenesServicio();
        
        vv.tbllis_os.setModel(modeltLis1);
        
        vv.tbllis_os.getColumnModel().getColumn(0).setMaxWidth(0);
        vv.tbllis_os.getColumnModel().getColumn(0).setMinWidth(0);
        vv.tbllis_os.getColumnModel().getColumn(0).setPreferredWidth(0);
        vv.tbllis_os.getColumnModel().getColumn(1).setMaxWidth(75);
        vv.tbllis_os.getColumnModel().getColumn(1).setMinWidth(75);
        vv.tbllis_os.getColumnModel().getColumn(1).setPreferredWidth(75);
        vv.tbllis_os.getColumnModel().getColumn(2).setMaxWidth(75);
        vv.tbllis_os.getColumnModel().getColumn(2).setMinWidth(75);
        vv.tbllis_os.getColumnModel().getColumn(2).setPreferredWidth(75);
        vv.tbllis_os.getColumnModel().getColumn(3).setMaxWidth(0);
        vv.tbllis_os.getColumnModel().getColumn(3).setMinWidth(0);
        vv.tbllis_os.getColumnModel().getColumn(3).setPreferredWidth(0);
        vv.tbllis_os.getColumnModel().getColumn(4).setMaxWidth(75);
        vv.tbllis_os.getColumnModel().getColumn(4).setMinWidth(75);
        vv.tbllis_os.getColumnModel().getColumn(4).setPreferredWidth(75);
        vv.tbllis_os.getColumnModel().getColumn(5).setMaxWidth(75);
        vv.tbllis_os.getColumnModel().getColumn(5).setMinWidth(75);
        vv.tbllis_os.getColumnModel().getColumn(5).setPreferredWidth(75);
        vv.tbllis_os.getColumnModel().getColumn(6).setMaxWidth(150);
        vv.tbllis_os.getColumnModel().getColumn(6).setMinWidth(150);
        vv.tbllis_os.getColumnModel().getColumn(6).setPreferredWidth(150);
        vv.tbllis_os.getColumnModel().getColumn(9).setMaxWidth(0);
        vv.tbllis_os.getColumnModel().getColumn(9).setMinWidth(0);
        vv.tbllis_os.getColumnModel().getColumn(9).setPreferredWidth(0);
        vv.tbllis_os.getColumnModel().getColumn(10).setMaxWidth(0);
        vv.tbllis_os.getColumnModel().getColumn(10).setMinWidth(0);
        vv.tbllis_os.getColumnModel().getColumn(10).setPreferredWidth(0);
        vv.tbllis_os.getColumnModel().getColumn(11).setMaxWidth(0);
        vv.tbllis_os.getColumnModel().getColumn(11).setMinWidth(0);
        vv.tbllis_os.getColumnModel().getColumn(11).setPreferredWidth(0);
        vv.tbllis_os.getColumnModel().getColumn(12).setMaxWidth(150);
        vv.tbllis_os.getColumnModel().getColumn(12).setMinWidth(150);
        vv.tbllis_os.getColumnModel().getColumn(12).setPreferredWidth(150);
        vv.tbllis_os.getColumnModel().getColumn(13).setMaxWidth(75);
        vv.tbllis_os.getColumnModel().getColumn(13).setMinWidth(75);
        vv.tbllis_os.getColumnModel().getColumn(13).setPreferredWidth(75);
        vv.tbllis_os.getColumnModel().getColumn(14).setMaxWidth(0);
        vv.tbllis_os.getColumnModel().getColumn(14).setMinWidth(0);
        vv.tbllis_os.getColumnModel().getColumn(14).setPreferredWidth(0);
        vv.tbllis_os.getColumnModel().getColumn(15).setMaxWidth(0);
        vv.tbllis_os.getColumnModel().getColumn(15).setMinWidth(0);
        vv.tbllis_os.getColumnModel().getColumn(15).setPreferredWidth(0);
        centrar.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
        for (int i = 0; i <= 14; i++) {
            vv.tbllis_os.getColumnModel().getColumn(i).setCellRenderer(centrar);
        }*/
    }
    
    /*SI se usa*/ public void CargarVentasCancelar(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        String folioBusqueda;
        folioBusqueda = vv.txtTextoBus_Cancelar.getText().toString();
        
        int id_ven = 0;
        String fol_ven = folioBusqueda;
        double tot_ven = 0;
        int can_ven = 0;
        int des_ven = 0;
        
        int met_pag = 0;
        FormasPagoM formaPago = (FormasPagoM) vv.cmbfiltro_metodopago1.getSelectedItem();
        met_pag = formaPago.getId()-1;
        
        int tip_ven = 0;
        String fec_reg = null;
        int sta_ven = 0;
        String ref_ven = null;
        double subtot_ven = 0;
        String fecha_can = null;
        
        id_ven = -6;
        tip_ven = 0;
        switch(vv.cmbest_ven.getSelectedIndex()){
            case 0:
                ref_ven = "0,1";
                break;
            case 1:
                ref_ven = "1";
                break;
            case 2:
                ref_ven = "0";
                break;
            default:
                ref_ven = "0,1";
                break;
        }
        Date fecha_de;
        Date fecha_al;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        fecha_de = vv.fechaDe.getDate();
        fecha_al = vv.fechaAl.getDate();
        fecha_can = sdf.format(fecha_de)+" 00:00:00";
        fec_reg = sdf.format(fecha_al)+" 23:59:59";
        
        
        vm.setId_ven(id_ven);
        vm.setFol_ven(fol_ven);
        vm.setTot_ven(tot_ven);
        vm.setCan_ven(can_ven);
        vm.setDes_ven(des_ven);
        vm.setMet_pag(met_pag);
        vm.setTip_ven(tip_ven);
        vm.setFec_reg(fec_reg);
        vm.setSta_ven(sta_ven);
        vm.setRef_ven(ref_ven);
        vm.setSubtot_ven(subtot_ven);
        vm.setFec_cancela(fecha_can);
        
        modeltLisCV = vm.CargarOrdenesServicio();
        
        BigDecimal efectivo = vm.getTotalEfectivo();
        BigDecimal tarjeta = vm.getTotalTarjeta();
        BigDecimal transferencia = vm.getTotalTransferencia();
        
        vv.lblTot_Efectivo.setText("$ " + efectivo.toString());
        vv.lblTot_Tarjeta.setText("$ " + tarjeta.toString());
        vv.lblTot_Transferencia.setText("$ " + transferencia.toString());
        
        vv.tbllis_ven.setModel(modeltLisCV);
        
        vv.tbllis_ven.getColumnModel().getColumn(0).setMaxWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(0).setMinWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(0).setPreferredWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(11).setMaxWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(11).setMinWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(11).setPreferredWidth(0);
        centrar.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
        for (int i = 0; i <= 10; i++) {
            vv.tbllis_ven.getColumnModel().getColumn(i).setCellRenderer(centrar);
        }
        /*vv.tbllis_ven.getColumnModel().getColumn(1).setMaxWidth(175);
        vv.tbllis_ven.getColumnModel().getColumn(1).setMinWidth(175);
        vv.tbllis_ven.getColumnModel().getColumn(1).setPreferredWidth(175);
        vv.tbllis_ven.getColumnModel().getColumn(2).setMaxWidth(175);
        vv.tbllis_ven.getColumnModel().getColumn(2).setMinWidth(175);
        vv.tbllis_ven.getColumnModel().getColumn(2).setPreferredWidth(175);
        vv.tbllis_ven.getColumnModel().getColumn(3).setMaxWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(3).setMinWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(3).setPreferredWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(4).setMaxWidth(125);
        vv.tbllis_ven.getColumnModel().getColumn(4).setMinWidth(125);
        vv.tbllis_ven.getColumnModel().getColumn(4).setPreferredWidth(125);
        vv.tbllis_ven.getColumnModel().getColumn(5).setMaxWidth(125);
        vv.tbllis_ven.getColumnModel().getColumn(5).setMinWidth(125);
        vv.tbllis_ven.getColumnModel().getColumn(5).setPreferredWidth(125);
        vv.tbllis_ven.getColumnModel().getColumn(6).setMaxWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(6).setMinWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(6).setPreferredWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(7).setMaxWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(7).setMinWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(7).setPreferredWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(8).setMaxWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(8).setMinWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(8).setPreferredWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(9).setMaxWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(9).setMinWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(9).setPreferredWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(10).setMaxWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(10).setMinWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(10).setPreferredWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(11).setMaxWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(11).setMinWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(11).setPreferredWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(12).setMaxWidth(300);
        vv.tbllis_ven.getColumnModel().getColumn(12).setMinWidth(300);
        vv.tbllis_ven.getColumnModel().getColumn(12).setPreferredWidth(300);
        vv.tbllis_ven.getColumnModel().getColumn(13).setMaxWidth(125);
        vv.tbllis_ven.getColumnModel().getColumn(13).setMinWidth(125);
        vv.tbllis_ven.getColumnModel().getColumn(13).setPreferredWidth(125);
        vv.tbllis_ven.getColumnModel().getColumn(14).setMaxWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(14).setMinWidth(0);
        vv.tbllis_ven.getColumnModel().getColumn(14).setPreferredWidth(0);*/
        /*centrar.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
        for (int i = 0; i <= 15; i++) {
            vv.tbllis_ven.getColumnModel().getColumn(i).setCellRenderer(centrar);
        }*/
        
    }
    
    public void CargarVentasPendientes(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        String folioBusqueda;
        folioBusqueda = "";
        
        int id_ven = 0;
        String fol_ven = folioBusqueda;
        double tot_ven = 0;
        int can_ven = 0;
        int des_ven = 0;
        
        int met_pag = 0;
        
        int tip_ven = 0;
        String fec_reg = null;
        int sta_ven = 0;
        String ref_ven = null;
        double subtot_ven = 0;
        String fecha_can = null;
        
        id_ven = -13;
        tip_ven = 0;
        ref_ven = "2";
        Date fecha_de;
        Date fecha_al;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        fecha_de = vv.fechaDe_pen.getDate();
        fecha_al = vv.fechaAl_pen.getDate();
        fecha_can = sdf.format(fecha_de)+" 00:00:00";
        fec_reg = sdf.format(fecha_al)+" 23:59:59";
        
        
        vm.setId_ven(id_ven);
        vm.setFol_ven(fol_ven);
        vm.setTot_ven(tot_ven);
        vm.setCan_ven(can_ven);
        vm.setDes_ven(des_ven);
        vm.setMet_pag(met_pag);
        vm.setTip_ven(tip_ven);
        vm.setFec_reg(fec_reg);
        vm.setSta_ven(sta_ven);
        vm.setRef_ven(ref_ven);
        vm.setSubtot_ven(subtot_ven);
        vm.setFec_cancela(fecha_can);
        
        modelt = vm.CargarOrdenesServicio();
        
        /*BigDecimal efectivo = vm.getTotalEfectivo();
        BigDecimal tarjeta = vm.getTotalTarjeta();
        BigDecimal transferencia = vm.getTotalTransferencia();
        
        vv.lblTot_Efectivo.setText("$ " + efectivo.toString());
        vv.lblTot_Tarjeta.setText("$ " + tarjeta.toString());
        vv.lblTot_Transferencia.setText("$ " + transferencia.toString());*/
        
        vv.tbllis_pen.setModel(modelt);
        
        vv.tbllis_pen.getColumnModel().getColumn(0).setMaxWidth(0);
        vv.tbllis_pen.getColumnModel().getColumn(0).setMinWidth(0);
        vv.tbllis_pen.getColumnModel().getColumn(0).setPreferredWidth(0);
        /*vv.tbllis_pen.getColumnModel().getColumn(11).setMaxWidth(0);
        vv.tbllis_pen.getColumnModel().getColumn(11).setMinWidth(0);
        vv.tbllis_pen.getColumnModel().getColumn(11).setPreferredWidth(0);*/
        centrar.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
        for (int i = 0; i <= 10; i++) {
            vv.tbllis_pen.getColumnModel().getColumn(i).setCellRenderer(centrar);
        }
    }
    
    /*SI se usa*/ public int ActualizarEstatus_VE(int filaVenCan){
        int id_ven = 0;
        String fol_ven = null;
        double tot_ven = 0;
        int can_ven = 0;
        int des_ven = 0;
        int met_pag = 0;
        int tip_ven = 0;
        String fec_reg = null;
        int sta_ven = 0;
        String ref_ven = null;
        double subtot_ven = 0;
        String fec_cancela = null;
        
        id_ven = -5;
        fol_ven = vv.tbllis_ven.getValueAt(filaVenCan, 1).toString();
        can_ven = Integer.parseInt(vv.tbllis_ven.getValueAt(filaVenCan, 0).toString());
        sta_ven = 0;
        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        fec_cancela = sdf.format(dt);
        
        vm.setId_ven(id_ven);
        vm.setFol_ven(fol_ven);
        vm.setTot_ven(tot_ven);
        vm.setCan_ven(can_ven);
        vm.setDes_ven(des_ven);
        vm.setMet_pag(met_pag);
        vm.setTip_ven(tip_ven);
        vm.setFec_reg(fec_reg);
        vm.setSta_ven(sta_ven);
        vm.setRef_ven(ref_ven);
        vm.setSubtot_ven(subtot_ven);
        vm.setFec_cancela(fec_cancela);
        vm.setUsuarioRegistra(usuarioLogeado);
        
        if(vm.GuardarVentaOrden()){
            return 1;
        }
        return 0;
    }
    
    /*SI se usa*/ public int ActualizarEstatus_PEN(int filaVenCan){
        int id_ven = 0;
        String fol_ven = null;
        double tot_ven = 0;
        int can_ven = 0;
        int des_ven = 0;
        int met_pag = 0;
        int tip_ven = 0;
        String fec_reg = null;
        int sta_ven = 0;
        String ref_ven = null;
        double subtot_ven = 0;
        String fec_cancela = null;
        
        id_ven = -5;
        fol_ven = vv.tbllis_pen.getValueAt(filaVenCan, 1).toString();
        can_ven = Integer.parseInt(vv.tbllis_pen.getValueAt(filaVenCan, 0).toString());
        sta_ven = 0;
        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        fec_cancela = sdf.format(dt);
        
        vm.setId_ven(id_ven);
        vm.setFol_ven(fol_ven);
        vm.setTot_ven(tot_ven);
        vm.setCan_ven(can_ven);
        vm.setDes_ven(des_ven);
        vm.setMet_pag(met_pag);
        vm.setTip_ven(tip_ven);
        vm.setFec_reg(fec_reg);
        vm.setSta_ven(sta_ven);
        vm.setRef_ven(ref_ven);
        vm.setSubtot_ven(subtot_ven);
        vm.setFec_cancela(fec_cancela);
        vm.setUsuarioRegistra(usuarioLogeado);
        
        if(vm.GuardarVentaOrden()){
            return 1;
        }
        return 0;
    }
    
    public int CompletarVentaPendiente(int filaVenCan){
        int id_ven = 0;
        String fol_ven = null;
        
        id_ven = -12;
        fol_ven = vv.tbllis_pen.getValueAt(filaVenCan, 1).toString();
        
        vm.setId_ven(id_ven);
        vm.setFol_ven(fol_ven);
        
        if(vm.GuardarVentaOrden()){
            return 1;
        }
        return 0;
    }
    
    /*SI se usa*/ public void RegresarStockVenta(int filaVenCan){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        int id = -2;
        double tot = 0;
        int cant = 0;
        int id_pro = 0;
        String folio = null;
        
        folio = vv.tbllis_ven.getValueAt(filaVenCan, 1).toString();
        
        System.out.println("Folio: " + folio);
        
        vm.setId(id);
        vm.setTot(tot);
        vm.setCan(cant);
        vm.setId_pro(id_pro);
        vm.setFol_dven(folio);
        
        modelt = vm.CargarDetalleVenta();
        
        int filasModel = 0;
        filasModel  = modelt.getRowCount();
        System.out.println("Filas: "+filasModel);
        for (int i = 0; i < filasModel; i++) {
            // System.out.println("===================================");
            // System.out.println("Id DVen: " + modelt.getValueAt(i, 0).toString());
            // System.out.println("Cantidad: " + modelt.getValueAt(i, 2).toString());
            // System.out.println("Producto: " + modelt.getValueAt(i, 3).toString());
            int id_dven = 0;
            int id_Pro = 0;
            double cantD = 0;
            id_dven = Integer.parseInt(modelt.getValueAt(i, 0).toString());
            id_Pro = Integer.parseInt(modelt.getValueAt(i, 3).toString());
            cantD = Double.parseDouble(modelt.getValueAt(i, 2).toString());
            pm.setId(-7);
            pm.setCod(null);
            pm.setNom(null);
            pm.setSa(cantD);
            pm.setSm(id_Pro);
            pm.setSx(id_dven);
            pm.setPre(0);
            pm.setCat(null);
            pm.setEst(0);
            pm.GuardarProductos();
        }
        
    }
    
    public void RegresarStockVenta_PEN(int filaVenCan){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        int id = -2;
        double tot = 0;
        int cant = 0;
        int id_pro = 0;
        String folio = null;
        
        folio = vv.tbllis_pen.getValueAt(filaVenCan, 1).toString();
        
        System.out.println("Folio: " + folio);
        
        vm.setId(id);
        vm.setTot(tot);
        vm.setCan(cant);
        vm.setId_pro(id_pro);
        vm.setFol_dven(folio);
        
        modelt = vm.CargarDetalleVenta();
        
        int filasModel = 0;
        filasModel  = modelt.getRowCount();
        System.out.println("Filas: "+filasModel);
        for (int i = 0; i < filasModel; i++) {
            int id_dven = 0;
            int id_Pro = 0;
            double cantD = 0;
            id_dven = Integer.parseInt(modelt.getValueAt(i, 0).toString());
            id_Pro = Integer.parseInt(modelt.getValueAt(i, 3).toString());
            cantD = Double.parseDouble(modelt.getValueAt(i, 2).toString());
            pm.setId(-7);
            pm.setCod(null);
            pm.setNom(null);
            pm.setSa(cantD);
            pm.setSm(id_Pro);
            pm.setSx(id_dven);
            pm.setPre(0);
            pm.setCat(null);
            pm.setEst(0);
            pm.GuardarProductos();
        }
    }
    
    public void CargarOrdenCancelar(){
        /*DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        int id_ven = 0;
        String fol_ven = null;
        double tot_ven = 0;
        int can_ven = 0;
        int des_ven = 0;
        String fec_ent = null;
        String dir_ent = null;
        String cli_ent = null;
        int met_pag = 0;
        int tip_ven = 0;
        String fec_reg = null;
        int sta_ven = 0;
        String ref_ven = null;
        double subtot_ven = 0;
        
        id_ven = -6;
        tip_ven = 1;
        switch(vv.cmbest_ord.getSelectedIndex()){
            case 0:
                ref_ven = "0,1,2";
                break;
            case 1:
                ref_ven = "1";
                break;
            case 2:
                ref_ven = "2";
                break;
            case 3:
                ref_ven = "0";
                break;
            default:
                ref_ven = "0,1,2";
                break;
        }
        Date fecha_de;
        Date fecha_al;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        fecha_de = vv.dtc_de_ord.getDate();
        fecha_al = vv.dtc_al_ord.getDate();
        fec_ent = sdf.format(fecha_de)+" 00:00:00";
        fec_reg = sdf.format(fecha_al)+" 23:59:59";
        
        
        vm.setId_ven(id_ven);
        vm.setFol_ven(fol_ven);
        vm.setTot_ven(tot_ven);
        vm.setCan_ven(can_ven);
        vm.setDes_ven(des_ven);
        vm.setFec_ent(fec_ent);
        vm.setDir_ent(dir_ent);
        vm.setCli_ent(cli_ent);
        vm.setMet_pag(met_pag);
        vm.setTip_ven(tip_ven);
        vm.setFec_reg(fec_reg);
        vm.setSta_ven(sta_ven);
        vm.setRef_ven(ref_ven);
        vm.setSubtot_ven(subtot_ven);
        
        modeltLisCO = vm.CargarOrdenesServicio();
        
        vv.tbllis_ord.setModel(modeltLisCO);
        
        vv.tbllis_ord.getColumnModel().getColumn(0).setMaxWidth(0);
        vv.tbllis_ord.getColumnModel().getColumn(0).setMinWidth(0);
        vv.tbllis_ord.getColumnModel().getColumn(0).setPreferredWidth(0);
        vv.tbllis_ord.getColumnModel().getColumn(1).setMaxWidth(75);
        vv.tbllis_ord.getColumnModel().getColumn(1).setMinWidth(75);
        vv.tbllis_ord.getColumnModel().getColumn(1).setPreferredWidth(75);
        vv.tbllis_ord.getColumnModel().getColumn(2).setMaxWidth(75);
        vv.tbllis_ord.getColumnModel().getColumn(2).setMinWidth(75);
        vv.tbllis_ord.getColumnModel().getColumn(2).setPreferredWidth(75);
        vv.tbllis_ord.getColumnModel().getColumn(3).setMaxWidth(0);
        vv.tbllis_ord.getColumnModel().getColumn(3).setMinWidth(0);
        vv.tbllis_ord.getColumnModel().getColumn(3).setPreferredWidth(0);
        vv.tbllis_ord.getColumnModel().getColumn(4).setMaxWidth(75);
        vv.tbllis_ord.getColumnModel().getColumn(4).setMinWidth(75);
        vv.tbllis_ord.getColumnModel().getColumn(4).setPreferredWidth(75);
        vv.tbllis_ord.getColumnModel().getColumn(5).setMaxWidth(75);
        vv.tbllis_ord.getColumnModel().getColumn(5).setMinWidth(75);
        vv.tbllis_ord.getColumnModel().getColumn(5).setPreferredWidth(75);
        vv.tbllis_ord.getColumnModel().getColumn(9).setMaxWidth(0);
        vv.tbllis_ord.getColumnModel().getColumn(9).setMinWidth(0);
        vv.tbllis_ord.getColumnModel().getColumn(9).setPreferredWidth(0);
        vv.tbllis_ord.getColumnModel().getColumn(10).setMaxWidth(0);
        vv.tbllis_ord.getColumnModel().getColumn(10).setMinWidth(0);
        vv.tbllis_ord.getColumnModel().getColumn(10).setPreferredWidth(0);
        vv.tbllis_ord.getColumnModel().getColumn(11).setMaxWidth(0);
        vv.tbllis_ord.getColumnModel().getColumn(11).setMinWidth(0);
        vv.tbllis_ord.getColumnModel().getColumn(11).setPreferredWidth(0);
        vv.tbllis_ord.getColumnModel().getColumn(12).setMaxWidth(150);
        vv.tbllis_ord.getColumnModel().getColumn(12).setMinWidth(150);
        vv.tbllis_ord.getColumnModel().getColumn(12).setPreferredWidth(150);
        vv.tbllis_ord.getColumnModel().getColumn(13).setMaxWidth(75);
        vv.tbllis_ord.getColumnModel().getColumn(13).setMinWidth(75);
        vv.tbllis_ord.getColumnModel().getColumn(13).setPreferredWidth(75);
        centrar.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
        for (int i = 0; i <= 15; i++) {
            vv.tbllis_ord.getColumnModel().getColumn(i).setCellRenderer(centrar);
        }*/
    }
    
    public int ActualizarEstatus_COS(int filaOrdCan){
        int id_ven = 0;
        String fol_ven = null;
        double tot_ven = 0;
        int can_ven = 0;
        int des_ven = 0;
        String fec_ent = null;
        String dir_ent = null;
        String cli_ent = null;
        int met_pag = 0;
        int tip_ven = 0;
        String fec_reg = null;
        int sta_ven = 0;
        String ref_ven = null;
        double subtot_ven = 0;
        String fec_surtido = null;
        String fec_cancela = null;
        
        
        id_ven = -5;
        //fol_ven = vv.tbllis_ord.getValueAt(filaOrdCan, 1).toString();
        sta_ven = 0;
        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        fec_cancela = sdf.format(dt);
        
        vm.setId_ven(id_ven);
        vm.setFol_ven(fol_ven);
        vm.setTot_ven(tot_ven);
        vm.setCan_ven(can_ven);
        vm.setDes_ven(des_ven);
        vm.setFec_ent(fec_ent);
        vm.setDir_ent(dir_ent);
        vm.setCli_ent(cli_ent);
        vm.setMet_pag(met_pag);
        vm.setTip_ven(tip_ven);
        vm.setFec_reg(fec_reg);
        vm.setSta_ven(sta_ven);
        vm.setRef_ven(ref_ven);
        vm.setSubtot_ven(subtot_ven);
        vm.setFec_cancela(fec_cancela);
        vm.setFec_surtido(fec_surtido);
        
        if(vm.GuardarVentaOrden()){
            return 1;
        }
        return 0;
    }
    
    public void RegresarStockOrden(int filaOrdCan){
        /*DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        int id = -2;
        double tot = 0;
        int cant = 0;
        int id_pro = 0;
        String folio = null;
        
        folio = vv.tbllis_ord.getValueAt(filaOrdCan, 1).toString();
        
        vm.setId(id);
        vm.setTot(tot);
        vm.setCan(cant);
        vm.setId_pro(id_pro);
        vm.setFol_dven(folio);
        
        modelt = vm.CargarDetalleVenta();
        
        int filasModel = 0;
        filasModel  = modelt.getRowCount();
        for (int i = 0; i < filasModel; i++) {
            //System.out.println("Cantidad: " + modelt.getValueAt(i, 2).toString());
            //System.out.println("Producto: " + modelt.getValueAt(i, 3).toString());
            int id_Pro = 0;
            int cantD = 0;
            id_Pro = Integer.parseInt(modelt.getValueAt(i, 3).toString());
            cantD = Integer.parseInt(modelt.getValueAt(i, 2).toString());
            pm.setId(-7);
            pm.setCod(null);
            pm.setNom(null);
            pm.setSa(cantD);
            pm.setSm(id_Pro);
            pm.setSx(0);
            pm.setPre(0);
            pm.setCat(null);
            pm.setCol(null);
            pm.setTam(null);
            pm.setHor(null);
            pm.setEst(0);
            pm.GuardarProductos();
        }
        */
    }
    
    public void CargarConsultaDetalle(String Folio){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        int id = -3;
        double tot = 0;
        int can = 0;
        int idpro = 0;
        String folVen = Folio;
        
        vm.setId(id);
        vm.setTot(tot);
        vm.setCan(can);
        vm.setId_pro(idpro);
        vm.setFol_dven(folVen);
        
        c_modeltLisOS = vm.CargarDetalleVenta();
        
        vv.c_tbllis_os.setModel(c_modeltLisOS);
        
        vv.c_tbllis_os.getColumnModel().getColumn(0).setMaxWidth(130);
        vv.c_tbllis_os.getColumnModel().getColumn(0).setMinWidth(130);
        vv.c_tbllis_os.getColumnModel().getColumn(0).setPreferredWidth(130);
        vv.c_tbllis_os.getColumnModel().getColumn(2).setMaxWidth(100);
        vv.c_tbllis_os.getColumnModel().getColumn(2).setMinWidth(100);
        vv.c_tbllis_os.getColumnModel().getColumn(2).setPreferredWidth(100);
        vv.c_tbllis_os.getColumnModel().getColumn(3).setMaxWidth(100);
        vv.c_tbllis_os.getColumnModel().getColumn(3).setMinWidth(100);
        vv.c_tbllis_os.getColumnModel().getColumn(3).setPreferredWidth(100);
        vv.c_tbllis_os.getColumnModel().getColumn(4).setMaxWidth(100);
        vv.c_tbllis_os.getColumnModel().getColumn(4).setMinWidth(100);
        vv.c_tbllis_os.getColumnModel().getColumn(4).setPreferredWidth(100);
        centrar.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
        for (int i = 0; i <= 4; i++) {
            vv.c_tbllis_os.getColumnModel().getColumn(i).setCellRenderer(centrar);
        }
        
    }
    
    public void CargarConsultaDetalle2(String Folio){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        int id = -3;
        double tot = 0;
        int can = 0;
        int idpro = 0;
        String folVen = Folio;
        
        vm.setId(id);
        vm.setTot(tot);
        vm.setCan(can);
        vm.setId_pro(idpro);
        vm.setFol_dven(folVen);
        
        c_modeltLisVE = vm.CargarDetalleVenta();
        
        vv.c_tbllis_ve.setModel(c_modeltLisVE);
        
        vv.c_tbllis_ve.getColumnModel().getColumn(1).setPreferredWidth(250);
        
        /*vv.c_tbllis_ve.getColumnModel().getColumn(0).setMaxWidth(130);
        vv.c_tbllis_ve.getColumnModel().getColumn(0).setMinWidth(130);
        vv.c_tbllis_ve.getColumnModel().getColumn(0).setPreferredWidth(130);
        vv.c_tbllis_ve.getColumnModel().getColumn(2).setMaxWidth(100);
        vv.c_tbllis_ve.getColumnModel().getColumn(2).setMinWidth(100);
        vv.c_tbllis_ve.getColumnModel().getColumn(2).setPreferredWidth(100);
        vv.c_tbllis_ve.getColumnModel().getColumn(3).setMaxWidth(100);
        vv.c_tbllis_ve.getColumnModel().getColumn(3).setMinWidth(100);
        vv.c_tbllis_ve.getColumnModel().getColumn(3).setPreferredWidth(100);
        vv.c_tbllis_ve.getColumnModel().getColumn(4).setMaxWidth(100);
        vv.c_tbllis_ve.getColumnModel().getColumn(4).setMinWidth(100);
        vv.c_tbllis_ve.getColumnModel().getColumn(4).setPreferredWidth(100);
        vv.c_tbllis_ve.getColumnModel().getColumn(5).setMaxWidth(100);
        vv.c_tbllis_ve.getColumnModel().getColumn(5).setMinWidth(100);
        vv.c_tbllis_ve.getColumnModel().getColumn(5).setPreferredWidth(100);*/
        centrar.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
        for (int i = 0; i <= 5; i++) {
            vv.c_tbllis_ve.getColumnModel().getColumn(i).setCellRenderer(centrar);
        }
        
    }
    
    public void cobrarConEnter(){
        double total = 0;
        double pago = 0;
        total = Double.parseDouble(vv.txttotal.getText());
        if(vv.txtpago.getText().isEmpty()){
            pago = 0;
        }else{
            pago = Double.parseDouble(vv.txtpago.getText());
        }
        if(pago < total){
            JOptionPane.showMessageDialog(vv, "Introducir una cantidad en el Campo \"Pago\"\nIgual o Mayor al Total");
        }else{
            int guaVen=0;
            guaVen = GuardarVenta();
            if(guaVen <= 0){
                JOptionPane.showMessageDialog(vv, "Error al Guarar la venta, Contacte a Soporte Técnico!!!");
            }else{
                int guaDet = 0;
                guaDet = GuardarDetalleVenta();
                if(guaDet > 0){
                    JOptionPane.showMessageDialog(vv, "Error al Guardar los Productos de la Venta, Contacte a Soporte Técnico");
                }else{
                    
                    // ACTUALIZAR ESTATUS COTIZACION
                    String nombreCotizacion = null;
                    nombreCotizacion = vv.txtNombreCotView.getText();

                    if( !nombreCotizacion.equals("")){
                        FinalizaListaCot(nombreCotizacion);
                    }
                    
                    
                    // Mostrar el diálogo con las opciones de "Sí" y "No"
                    // showOptionDialogWithArrowKeys();
                    
                    /*Object[] options = {"Si", "No"};
                    int resGuardar = JOptionPane.showOptionDialog(null, "Desea Imprimir el Ticket?", "Imprimir", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null, options, options[0]);
                    if( resGuardar == 0 ){
                        // Espacio para Imprimir Ticket Antes de Regresar al Inicio
                        int a = 0;
                        try {
                            a = imprimirTicket(true);
                        } catch (IOException ex) {
                            Logger.getLogger(VentasC.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if(a == 1){

                        }else{
                            JOptionPane.showMessageDialog(vv, "El Ticket no se Imprimió, Contacte a Soporte Técnico");
                        }
                        // Fin de Imprimir Ticket
                    }*/
                    
                    // Fin de Imprimir Ticket
                    
                    int permiso = pesm.obtenerPermisoEspecial(PERMISO_IMPRIMIR_TICKET);
                    if( permiso == 0 ){
                        FinalizarVenta(1);
                    }else{
                        vv.dConfirmarCobro.setSize(292, 105);
                        vv.dConfirmarCobro.setModal(true);
                        vv.dConfirmarCobro.setLocationRelativeTo(vv);
                        // Deshabilitar el botón de maximizar
                        vv.dConfirmarCobro.setResizable(false);
                        vv.dConfirmarCobro.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                        vv.dConfirmarCobro.setTitle("Imprimir Ticket");
                        ImageIcon icon = new ImageIcon("src/img/pagos.png"); // Asegúrate de que la imagen esté en la ruta correcta
                        vv.dConfirmarCobro.setIconImage(icon.getImage());
                        vv.dConfirmarCobro.setVisible(true);
                        vv.btnSiImprimirTicket.requestFocus();
                    }
                    
                    /*vv.pnlcajarapida.setVisible(true);
                    vv.pnlcobro.setVisible(false);
                    vv.btnsalir.setText("Salir");
                    vv.btncobrar.setEnabled(true);
                    //Obtener Subtotal y Total
                    vv.txtsubtotal.setText("");
                    vv.txttotal.setText("");
                    //Descuento
                    vv.txtdescuento.setText("");
                    //Paga Con
                    vv.txtpago.setText("");
                    //Cambio
                    vv.txtcambio.setText("");
                    int filas = vv.tbllis.getRowCount();
                    for (int i = 0; i < filas; i++) {
                        modeltLis.removeRow(0);
                    }
                    CalcularTotales();
                    
                    vv.txtbus.requestFocus();*/
                }
            }
        }
    }
    
    public void abrirModalEntradasSalidasEfe() {
        vv.dEntSalEfe.setSize(350, 335);
        vv.dEntSalEfe.setModal(true);
        vv.dEntSalEfe.setLocationRelativeTo(vv);
        vv.dEntSalEfe.setResizable(false);
        vv.dEntSalEfe.setVisible(true);
        
        vv.cmbTipo_ESE.setSelectedIndex(0);
        vv.spinMonto_ESE.setValue(1.0);
        vv.txtComentarios_ESE.setText("");
        vv.chbImpComprobante_ESE.setSelected(false);
    }
    
    public void abrirModalPrecios(int id) {
        vv.dPrecios.setSize(300, 255);
        vv.dPrecios.setModal(true);
        vv.dPrecios.setLocationRelativeTo(vv);
        vv.dPrecios.setTitle("Seleccione Precio");
        vv.dPrecios.setResizable(false);
        String precios = null; 
        precios = obtenerListaPrecios(id);
        String[] arrayPrecios;
        arrayPrecios = precios.split("\\|");
        vv.txtPrecio1.setText(arrayPrecios[0]);
        if(arrayPrecios[1].equals(""))
            vv.txtPrecio2.setText("0.0");
        else
            vv.txtPrecio2.setText(arrayPrecios[1]);
        
        if(arrayPrecios[2].equals(""))
            vv.txtPrecio3.setText("0.0");
        else
            vv.txtPrecio3.setText(arrayPrecios[2]);
        
        vv.dPrecios.setVisible(true);
    }
    
    public void abrirModalBuscarProductos() {
        vv.dBuscarProductos.setSize(600, 435);
        vv.dBuscarProductos.setModal(true);
        vv.dBuscarProductos.setLocationRelativeTo(vv);
        vv.dBuscarProductos.setTitle("Seleccione Producto");
        vv.dBuscarProductos.setResizable(false);
        vv.txtBuscarProductos.setText("");
        CargarProductosBuscar("");
        vv.dBuscarProductos.setVisible(true);
    }
    
    public void abrirModalDescuentos(String producto, String precio) {
        vv.dDescuentos.setSize(320, 350);
        vv.dDescuentos.setModal(true);
        vv.dDescuentos.setLocationRelativeTo(vv);
        vv.dDescuentos.setTitle("Aplicar Descuentos");
        vv.dDescuentos.setResizable(false);
        vv.lblNombreProductoDescuento.setText(producto);
        vv.txtImporteAplicar.setText(precio);
        vv.txtPorcentajeAplicar.setText("");
        vv.txtPorcentajeAplicar.setText("0.0");
        vv.chbAplicarDesTodProd.setSelected(false);
        vv.txtPorcentajeAplicar.requestFocus();
        vv.dDescuentos.setVisible(true);
    }
    
    public void abrirModalPendientes() {
        vv.dPendientes.setSize(286, 190);
        vv.dPendientes.setModal(true);
        vv.dPendientes.setLocationRelativeTo(vv);
        vv.dPendientes.setTitle("Ticket Pendiente");
        vv.dPendientes.setResizable(false);
        vv.txtNombrePendiente.setText("");
        vv.txtNombrePendiente.requestFocus();
        vv.dPendientes.setVisible(true);
    }
    
    public void abrirModalCotizacion() {
        vv.dCotizaciones.setSize(286, 190);
        vv.dCotizaciones.setModal(true);
        vv.dCotizaciones.setLocationRelativeTo(vv);
        vv.dCotizaciones.setTitle("Cotizaciones");
        vv.dCotizaciones.setResizable(false);
        vv.txtNombreCotizacion.setText("");
        vv.txtNombreCotizacion.requestFocus();
        vv.dCotizaciones.setVisible(true);
    }
    
    public void abrirModalAbiertos() {
        vv.dAbiertos.setSize(300, 315);
        vv.dAbiertos.setModal(true);
        vv.dAbiertos.setLocationRelativeTo(vv);
        vv.dAbiertos.setTitle("Tickets Abiertos");
        vv.dAbiertos.setResizable(false);
        try {
            CargarTicketsAbiertos();
        } catch (SQLException ex) {
            Logger.getLogger(VentasFrameC.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(vv.tblAbiertos.getRowCount() > 0)
            vv.tblAbiertos.setRowSelectionInterval(0, 0);
        
        vv.dAbiertos.setVisible(true);
    }
    
    public void abrirModalListaCot() {
        vv.dListaCot.setSize(530, 315);
        vv.dListaCot.setModal(true);
        vv.dListaCot.setLocationRelativeTo(vv);
        vv.dListaCot.setTitle("Lista de Cotizaciones");
        vv.dListaCot.setResizable(false);
        try {
            CargarListaCot();
        } catch (SQLException ex) {
            Logger.getLogger(VentasFrameC.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(vv.tblListaCot.getRowCount() > 0)
            vv.tblListaCot.setRowSelectionInterval(0, 0);
        
        vv.dListaCot.setVisible(true);
    }
    
    public void abrirModalDetalleVenta() {
        vv.dConsultarVE.setSize(800, 565);
        vv.dConsultarVE.setModal(true);
        vv.dConsultarVE.setLocationRelativeTo(vv);
        vv.dConsultarVE.setResizable(false);
        // vv.dConsultarVE.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        vv.dConsultarVE.setVisible(true);
        
        /*vv.dAbiertos.setSize(300, 315);
        vv.dAbiertos.setModal(true);
        vv.dAbiertos.setLocationRelativeTo(vv);
        vv.dAbiertos.setTitle("Tickets Abiertos");
        try {
            CargarTicketsAbiertos();
        } catch (SQLException ex) {
            Logger.getLogger(VentasFrameC.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(vv.tblAbiertos.getRowCount() > 0)
            vv.tblAbiertos.setRowSelectionInterval(0, 0);
        
        vv.dAbiertos.setVisible(true);*/
    }
    
    public void abrirModalImprimirCot() {
        vv.dImprimirCot.setTitle("Imprimir Cotizacion");
        vv.dImprimirCot.setSize(292, 105);
        vv.dImprimirCot.setModal(true);
        vv.dImprimirCot.setLocationRelativeTo(vv);
        vv.dImprimirCot.setResizable(false);
        vv.dImprimirCot.setVisible(true);
    }
    
    public void abrirModalAsignarCliente(int idCliente, int idVenta, String nombreCliente) {
        vv.dAsignarCliente.setTitle("Asignar cliente");
        vv.dAsignarCliente.setSize(450,200);
        vv.dAsignarCliente.setModal(true);
        vv.dAsignarCliente.setLocationRelativeTo(vv);
        vv.dAsignarCliente.setResizable(false);
        
        vv.txt_clienteActual_AC.setText(nombreCliente);
        vv.txt_idVenta_AC.setText(""+idVenta);
        vv.txt_idCliente_AC.setText(""+idCliente);
        BuscarClientesAsignar();
        
        vv.dAsignarCliente.setVisible(true);
        
        vv.cmb_nuevoCliente_AC.requestFocus();
    }
    
    public void abrirModalAbonosCreditos(int idCliente, String nombreCliente) {
        vv.dAbonosCreditos.setTitle("Abonos a créditos");
        vv.dAbonosCreditos.setSize(450,255);
        vv.dAbonosCreditos.setModal(true);
        vv.dAbonosCreditos.setLocationRelativeTo(vv);
        vv.dAbonosCreditos.setResizable(false);
        
        vv.txt_clienteActual_RA.setText(nombreCliente);
        vv.txt_idCliente_RA.setText(""+idCliente);
        vv.txt_cantidadAbono_RA.setText("0");
        vv.txt_comentarios_RA.setText("");
        
        vv.dAbonosCreditos.setVisible(true);
    }
    
    public void cerrarModal(JDialog dialog){
        dialog.dispose();
        vv.txtbus.requestFocus();
    }
    
    public void cerrarModalBuscarP(JDialog dialog) throws AWTException{
        dialog.dispose();
        // Crear una instancia de Robot
        Robot robot = new Robot();

        // Presionar la tecla
        robot.keyPress(KeyEvent.VK_ESCAPE);
        vv.txtbus.requestFocus();
    }
    
    public static boolean isNumeric(String str) {
        return str != null && str.matches("-?\\d+(\\.\\d+)?"); // Para números enteros y decimales
        // return str != null && str.matches("-?\\d{1,3}(,\\d{3})*(\\.\\d+)?");
    }
    
    public void guardarEntradasSalidasEfectivo() throws IOException{
        
        String tipo = "";
        double monto = 0.0;
        String comentario = "";
        boolean comprobante = false;
        
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) vv.spinMonto_ESE.getEditor();
        JTextField textField = editor.getTextField(); // Obtener el campo de texto del editor

        // Obtener el texto del campo de texto
        String texto = textField.getText();
        String textoSinComas = texto.replace(",", "");
        if( texto.equals("") ){
            JOptionPane.showMessageDialog(vv, "El campo Monto está vacio.");
            return;
        }
        
        if( !isNumeric( textoSinComas ) ){
            JOptionPane.showMessageDialog(vv, "Ingrese un Monto Válido.");
            return;
        }
        
        Object[] options = {"Si", "No"};
        int resGuardar = JOptionPane.showOptionDialog(
            null, 
            "¿Confirmar "+vv.cmbTipo_ESE.getSelectedItem()+" de Efectivo?", 
            "Confirmar", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE,
            null, 
            options, 
            options[0]
        );
        if( resGuardar == 0 ){
            if( GuardarESEfectivo() == 1 ){
                boolean isSelected = false;
                isSelected = vv.chbImpComprobante_ESE.isSelected();
                if( isSelected ){
                    // System.out.println("Imprimir Ticket.");
                    imprimirEntradaSalida(true, vv.cmbTipo_ESE.getSelectedItem().toString(), Double.parseDouble(textoSinComas), vv.txtComentarios_ESE.getText().toString());
                }
                JOptionPane.showMessageDialog(vv, "Registro Guardado.");
                cerrarModal(vv.dEntSalEfe);
            }else{
                JOptionPane.showMessageDialog(vv, "Error a Guardar " + vv.cmbTipo_ESE.getSelectedItem() + " de Efectivo. Contacte a Soporte Técnico.");
            }
        }
    }
    
    public int GuardarESEfectivo(){
        int id_ese = 0;
        String con_ese = null;
        Double can_ese = 0.0;
        int tip_ese = 0;
        String fec_ese = null;
        int sta_ese = 0;
        int id_pro = 0;
        
        id_ese = 0;
        con_ese = vv.txtComentarios_ESE.getText();
        
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) vv.spinMonto_ESE.getEditor();
        JTextField textField = editor.getTextField(); // Obtener el campo de texto del editor

        // Obtener el texto del campo de texto
        String texto = textField.getText();
        String textoSinComas = texto.replace(",", "");
        can_ese = Double.parseDouble(textoSinComas);
        
        if( vv.cmbTipo_ESE.getSelectedIndex() == 0 ){
            tip_ese = 0;
        }else{
            tip_ese = 1;
        }
        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        fec_ese = sdf.format(dt);
        sta_ese = 1;
        
        esem.setId_ese(id_ese);
        esem.setCon_ese(con_ese);
        esem.setCan_ese(can_ese);
        esem.setTip_ese(tip_ese);
        esem.setFec_ese(fec_ese);
        esem.setSta_ese(sta_ese);
        esem.setId_pro(id_pro);
        
        if(esem.GuardarESEfectivo()){
            return 1;
        }
        return 0;
    }
    
    public String obtenerListaPrecios(int id){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        pm.setId(-10);
        pm.setSm(id);
        modelt = pm.CargarProductos();
        
        if(modelt.getRowCount() <= 0){
            return "NO";
        }
        
        String precio1;
        String precio2;
        String precio3;
        Object valor1 = modelt.getValueAt(0, 0);
        Object valor2 = modelt.getValueAt(0, 1);
        Object valor3 = modelt.getValueAt(0, 2);
        
        // Verificar si el valor es nulo o vacío
        if (valor1 == null || valor1.toString().trim().isEmpty()) {
            precio1 = "0.0";
        } else {
            precio1 = modelt.getValueAt(0, 0).toString();
        }
        
        // Verificar si el valor es nulo o vacío
        if (valor2 == null || valor2.toString().trim().isEmpty()) {
            precio2 = "0.0";
        } else {
            precio2 = modelt.getValueAt(0, 1).toString();
        }
        
        // Verificar si el valor es nulo o vacío
        if (valor3 == null || valor3.toString().trim().isEmpty()) {
            precio3 = "0.0";
        } else {
            precio3 = modelt.getValueAt(0, 2).toString();
        }
        
        return precio1 + "|" + precio2 + "|" + precio3;
        
        /*return modelt.getValueAt(0, 0).toString() + "|" + 
                modelt.getValueAt(0, 1).toString() + "|" + 
                modelt.getValueAt(0, 2).toString();*/
    }
    
    public void seleccionarListaPrecios(){
        AbstractButton selectedButton = null;
        for (AbstractButton button : new AbstractButton[] { vv.jrbPrecio1, vv.jrbPrecio2, vv.jrbPrecio3 }) {
            if (button.isSelected()) {
                selectedButton = button;
                break;
            }
        }
        
        double precioSeleccionado;
        switch (selectedButton.getText()) {
            case "Precio : $":
                // System.out.println("Selecciona precio");
                precioSeleccionado = Double.parseDouble( vv.txtPrecio1.getText() );
                break;
            case "Mayoreo: $":
                // System.out.println("Selecciona Mayoreo");
                precioSeleccionado = Double.parseDouble(vv.txtPrecio2.getText() );
                break;
            case "Menudeo: $":
                // System.out.println("Selecciona Menudeo");
                precioSeleccionado = Double.parseDouble( vv.txtPrecio3.getText() );
                break;
            default:
                precioSeleccionado = 1.0;
                break;
        }
        
        // System.out.println("Precio Seleccionado: " + precioSeleccionado);
        
        if(precioSeleccionado <= 0){
            JOptionPane.showMessageDialog(vv, "No se puede seleccionar un precio en ceros.");
            return;
        }
        
        int row = 0;
        row = vv.tbllis.getSelectedRow();
        
        vv.tbllis.setValueAt(precioSeleccionado, row, 3);
        CalcularTotales();
        
        JOptionPane.showMessageDialog(vv, "Precio Asignado.");
        cerrarModal(vv.dPrecios);
        
        // Mostrar el resultado
        /*if (selectedButton != null) {
        JOptionPane.showMessageDialog(vv, "Seleccionado: " + selectedButton.getText());
        } else {
        JOptionPane.showMessageDialog(vv, "Ninguna opción seleccionada.");
        }*/
    }
    
    public void CargarProductosBuscar(String buscar){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
         };
        
        String nom = null;
        nom = buscar;
        pm.setId(-11);
        pm.setCod(null);
        pm.setNom(nom);
        pm.setSa(0);
        pm.setSm(0);
        pm.setSx(0);
        pm.setPre(0);
        pm.setCosto(0);
        pm.setCat(null);
        pm.setEst(0);
        modelt = pm.CargarProductos();
        // modeltBuscarProductos = pm.CargarProductosConParametros(-11,null,nom,0,0,0,0,null,0,0,0,0,0,null,0);
        vv.tblBuscarProductos.setModel(modelt);
        
        vv.tblBuscarProductos.getColumnModel().getColumn(0).setMaxWidth(0);
        vv.tblBuscarProductos.getColumnModel().getColumn(0).setMinWidth(0);
        vv.tblBuscarProductos.getColumnModel().getColumn(0).setPreferredWidth(0);//ID
        
        vv.tblBuscarProductos.getColumnModel().getColumn(5).setMaxWidth(0);
        vv.tblBuscarProductos.getColumnModel().getColumn(5).setMinWidth(0);
        vv.tblBuscarProductos.getColumnModel().getColumn(5).setPreferredWidth(0);//ID CAT
        /*vv.tblBuscarProductos.getColumnModel().getColumn(2).setMaxWidth(200);
        vv.tblBuscarProductos.getColumnModel().getColumn(2).setMinWidth(200);
        vv.tblBuscarProductos.getColumnModel().getColumn(2).setPreferredWidth(200);//NOM*/
        centrar.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
        vv.tblBuscarProductos.getColumnModel().getColumn(2).setCellRenderer(centrar);
        vv.tblBuscarProductos.getColumnModel().getColumn(3).setCellRenderer(centrar);
        vv.tblBuscarProductos.getColumnModel().getColumn(4).setCellRenderer(centrar);
        
        vv.tblBuscarProductos.getTableHeader().setReorderingAllowed(false);
        vv.tblBuscarProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Crear el TableRowSorter y asociarlo con el modelo de tabla
        // TableRowSorter<TableModel> sorter = new TableRowSorter<>(modelt);
        sorter = new TableRowSorter<>(modelt);
        vv.tblBuscarProductos.setRowSorter(sorter);

        // Establecer un comparador para la columna de precios (columna 3)
        sorter.setComparator(4, (Object o1, Object o2) -> {
            // Asegurarse de que los valores sean Double
            Double val1 = (o1 instanceof Double) ? (Double) o1 : Double.valueOf(o1.toString());
            Double val2 = (o2 instanceof Double) ? (Double) o2 : Double.valueOf(o2.toString());
            
            return val1.compareTo(val2);
        });

        // Establecer un comparador para la columna de precios (columna 3)
        sorter.setComparator(3, (Object o1, Object o2) -> {
            // Asegurarse de que los valores sean Double
            Double val1 = (o1 instanceof Double) ? (Double) o1 : Double.valueOf(o1.toString());
            Double val2 = (o2 instanceof Double) ? (Double) o2 : Double.valueOf(o2.toString());
            
            // System.out.println("Comparando");
            
            return val1.compareTo(val2);
        });

        // Hacer que la tabla sea interactiva al hacer clic en los encabezados
        // vv.tblBuscarProductos.setAutoCreateRowSorter(true);
        
    }
    
    public void SeleccionarBuscarProductoB(){
        int filaSeleccionada = 0;
        filaSeleccionada = vv.tblBuscarProductos.getSelectedRow();
        if( filaSeleccionada < 0 ){
            JOptionPane.showMessageDialog(vv, "Seleccionar Producto.");
        }else{
            int id = 0;
            String codigo = null;
            double existe = 0;
            String nombre = null;
            String precio = null;
            double sActual = 0;
            int idCat = 0;

            // Producto
            id = Integer.parseInt(vv.tblBuscarProductos.getValueAt(filaSeleccionada, 0).toString());
            codigo = vv.tblBuscarProductos.getValueAt(filaSeleccionada, 1).toString();
            nombre = vv.tblBuscarProductos.getValueAt(filaSeleccionada, 2).toString();
            precio = vv.tblBuscarProductos.getValueAt(filaSeleccionada, 4).toString();
            sActual = Double.parseDouble(vv.tblBuscarProductos.getValueAt(filaSeleccionada, 3).toString());
            idCat = Integer.parseInt(vv.tblBuscarProductos.getValueAt(filaSeleccionada, 5).toString());

            existe = VerificarExisteTabla(codigo);

            if(existe <= 0){
                if( sActual < 0){
                    JOptionPane.showMessageDialog(vv, "<html>El producto <b>" + nombre + "</b> no cuenta con stock.</html>");
                }
                modeltLis.addRow(new Object[]{id,codigo,nombre,precio,1,"",idCat});
                vv.txtbus.setText("");
            }else{
                if( sActual < existe+1){
                    JOptionPane.showMessageDialog(vv, "<html>El producto <b>" + nombre + "</b> no cuenta con stock.</html>");
                }
                vv.tbllis.setValueAt(existe+1, filaReem, 4);
                vv.txtbus.setText("");
            }

            CalcularTotales();
            try {
                cerrarModalBuscarP(vv.dBuscarProductos);
            } catch (AWTException ex) {
                Logger.getLogger(VentasFrameC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private static void showOptionDialogWithArrowKeys() {
        // Opciones que queremos mostrar
        Object[] options = {"Sí", "No"};

        // Crear el JOptionPane con las opciones "Sí" y "No"
        JOptionPane optionPane = new JOptionPane(
            "¿Deseas continuar?", 
            JOptionPane.QUESTION_MESSAGE, 
            JOptionPane.YES_NO_OPTION, 
            null, 
            options, 
            options[0] // Valor predeterminado: "Sí"
        );

        // Crear un JDialog para personalizarlo
        JDialog dialog = optionPane.createDialog("Confirmar acción");

        // Añadir un KeyListener para las teclas de dirección
        dialog.addKeyListener(new KeyAdapter() {
            int selectedIndex = 0; // Índice inicial de la opción seleccionada

            @Override
            public void keyPressed(KeyEvent e) {
                // Detectar las teclas de flecha derecha o izquierda
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    // Mover a la opción siguiente (de "Sí" a "No")
                    // System.out.println("Se presiona la derecha");
                    selectedIndex = (selectedIndex + 1) % options.length;
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    // Mover a la opción anterior (de "No" a "Sí")
                    selectedIndex = (selectedIndex - 1 + options.length) % options.length;
                }

                // Establecer la opción seleccionada
                optionPane.setValue(options[selectedIndex]);
            }
        });

        // Mostrar el cuadro de diálogo
        dialog.setVisible(true);

        // Esperar a que el usuario seleccione una opción
        Object selectedValue = optionPane.getValue();

        // Mostrar el valor seleccionado (Sí o No)
        if (selectedValue != null) {
            System.out.println("Seleccionado: " + selectedValue);
        }
    }
    
    public void FinalizarVenta(int imprimir){
        if(imprimir == 1){
            // Espacio para Imprimir Ticket Antes de Regresar al Inicio
            int a = 0;
            try {
                a = imprimirTicket(true);
            } catch (IOException ex) {
                Logger.getLogger(VentasC.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(a == 1){

            }else{
                JOptionPane.showMessageDialog(vv, "El Ticket no se Imprimió, Contacte a Soporte Técnico");
            }
        }
        
        vv.pnlcajarapida.setVisible(true);
        vv.pnlcobro.setVisible(false);
        vv.btnsalir.setText("Salir");
        vv.btncobrar.setEnabled(true);
        //Obtener Subtotal y Total
        vv.txtsubtotal.setText("");
        vv.txttotal.setText("");
        //Descuento
        vv.txtdescuento.setText("");
        //Paga Con
        vv.txtpago.setText("");
        //Cambio
        vv.txtcambio.setText("");
        vv.cmbFormaPago.setSelectedIndex(0);
        int filas = vv.tbllis.getRowCount();
        for (int i = 0; i < filas; i++) {
            modeltLis.removeRow(0);
        }
        CalcularTotales();
        cerrarModal(vv.dConfirmarCobro);
        
        // Cotizacion
        vv.txtNombreCotView.setText("");

        vv.txtbus.requestFocus();
    }
    
    public int GuardarTicketPendiente(String nombrePendiente){
        int filas = 0;
        filas = vv.tbllis.getRowCount();
        int sumaErrores = 0;
        
        for (int i = 0; i < filas; i++) {
            pem.setParam(1);
            pem.setId(Integer.parseInt(vv.tbllis.getValueAt(i, 0).toString()));
            pem.setCodigo(vv.tbllis.getValueAt(i, 1).toString());
            pem.setNombre(vv.tbllis.getValueAt(i, 2).toString());
            pem.setPrecio(Double.parseDouble(vv.tbllis.getValueAt(i, 3).toString()));
            pem.setCantidad(Double.parseDouble(vv.tbllis.getValueAt(i, 4).toString()));
            pem.setSubtotal(Double.parseDouble(vv.tbllis.getValueAt(i, 5).toString()));
            pem.setConsecutivo(i+1);
            pem.setNombrePendiente(nombrePendiente);
            
            if(!pem.GuardarPendientes())
                sumaErrores++;
        }
        
        return sumaErrores;
        
    }
    
    public int GuardarCotizacion(String nombreCotizacion){
        com.setParam(-1);
        com.setNombreCotizacion(nombreCotizacion);
        com.GuardarCotizacion();
        
        int filas = 0;
        filas = vv.tbllis.getRowCount();
        int sumaErrores = 0;
        
        for (int i = 0; i < filas; i++) {
            com.setParam(1);
            com.setId(Integer.parseInt(vv.tbllis.getValueAt(i, 0).toString()));
            com.setCodigo(vv.tbllis.getValueAt(i, 1).toString());
            com.setNombre(vv.tbllis.getValueAt(i, 2).toString());
            com.setPrecio(Double.parseDouble(vv.tbllis.getValueAt(i, 3).toString()));
            com.setCantidad(Double.parseDouble(vv.tbllis.getValueAt(i, 4).toString()));
            com.setSubtotal(Double.parseDouble(vv.tbllis.getValueAt(i, 5).toString()));
            com.setId_cot(Integer.parseInt(vv.tbllis.getValueAt(i, 6).toString()));
            com.setConsecutivo(i+1);
            com.setNombreCotizacion(nombreCotizacion);
            
            if(!com.GuardarCotizacion())
                sumaErrores++;
        }
        
        return sumaErrores;
        
    }
    
    public int ValidarCountCotizacion(String nombrePendiente){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        com.setParam(-2);
        com.setNombreCotizacion(nombrePendiente);
        try {
            modelt = com.CargarCotizacion();
        } catch (SQLException ex) {
            Logger.getLogger(VentasFrameC.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int total = 0;
        for (int i = 0; i < modelt.getRowCount(); i++) {
            total = Integer.parseInt(modelt.getValueAt(0, 0).toString());
        }
        
        return total;
    }
    
    public void CargarTicketsAbiertos() throws SQLException{
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
         };
        
        //System.out.println(""+ buscar);
        pem.setParam(3);
        modelt = pem.CargarPendientes();
        vv.tblAbiertos.setModel(modelt);
        
        centrar.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
        vv.tblAbiertos.getColumnModel().getColumn(0).setCellRenderer(centrar);
    }
    
    public void CargarListaCot() throws SQLException{
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
         };
        
        //System.out.println(""+ buscar);
        com.setParam(3);
        modelt = com.CargarCotizacion();
        vv.tblListaCot.setModel(modelt);
        
        centrar.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
        vv.tblListaCot.getColumnModel().getColumn(0).setCellRenderer(centrar);
    }
    
    public void CargarDatosTicketActivo(String nombrePendiente){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        pem.setParam(2);
        pem.setNombrePendiente(nombrePendiente);
        try {
            modelt = pem.CargarPendientes();
        } catch (SQLException ex) {
            Logger.getLogger(VentasFrameC.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        FinalizarVenta(0);
        
        int filasModel = 0;
        filasModel = modelt.getRowCount();
        for (int i = 0; i < filasModel; i++) {
            modeltLis.addRow(new Object[]{
                modelt.getValueAt(i, 0),
                modelt.getValueAt(i, 1),
                modelt.getValueAt(i, 2),
                modelt.getValueAt(i, 3),
                modelt.getValueAt(i, 4),
                ""
            });
        }
        
        CalcularTotales();
        
        EliminarTicketActivo(nombrePendiente);
        
        cerrarModal(vv.dAbiertos);
        
        vv.tbllis.setRowSelectionInterval(0, 0);
        
        /* centrar.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
        vv.tblAbiertos.getColumnModel().getColumn(0).setCellRenderer(centrar);*/
    }
    
    public void CargarDatosListaCot(String nombreCotizacion){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        com.setParam(2);
        com.setNombreCotizacion(nombreCotizacion);
        try {
            modelt = com.CargarCotizacion();
        } catch (SQLException ex) {
            Logger.getLogger(VentasFrameC.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        FinalizarVenta(0);
        
        vv.txtNombreCotView.setText(nombreCotizacion);
        
        int filasModel = 0;
        filasModel = modelt.getRowCount();
        for (int i = 0; i < filasModel; i++) {
            modeltLis.addRow(new Object[]{
                modelt.getValueAt(i, 0),
                modelt.getValueAt(i, 1),
                modelt.getValueAt(i, 2),
                modelt.getValueAt(i, 3),
                modelt.getValueAt(i, 4),
                "",
                modelt.getValueAt(i, 10),
            });
        }
        
        CalcularTotales();
        
        // EliminarListaCot(nombreCotizacion);
                
        cerrarModal(vv.dListaCot);
        
        vv.tbllis.setRowSelectionInterval(0, 0);
        
        /* centrar.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
        vv.tblAbiertos.getColumnModel().getColumn(0).setCellRenderer(centrar);*/
    }
    
    public void EliminarTicketActivo(String nombrePendiente){
        pem.setParam(-1);
        pem.setNombrePendiente(nombrePendiente);

        pem.GuardarPendientes();
    }
    
    public void EliminarListaCot(String nombreCotizacion){
        com.setParam(-1);
        com.setNombreCotizacion(nombreCotizacion);

        com.GuardarCotizacion();
    }
    
    public void CancelarListaCot(String nombreCotizacion){
        com.setParam(-4);
        com.setNombreCotizacion(nombreCotizacion);

        com.GuardarCotizacion();
    }
    
    public void FinalizaListaCot(String nombreCotizacion){
        com.setParam(-3);
        com.setNombreCotizacion(nombreCotizacion);

        com.GuardarCotizacion();
    }
    
    public void AplicarDescuentos(){
        // Validar si es importe o descuento
        int tabSeleccionado = 0;
        tabSeleccionado = vv.pesAplicarDescuento.getSelectedIndex();

        if(tabSeleccionado == 0){ // ES PORCENTAJE
            // VALIDAR SI EL PORCENTAJE ES VALIDO
            String regex = "^-?\\d+(\\.\\d{1,2})?$";
            String text = vv.txtPorcentajeAplicar.getText();
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(text);

            // validar campo vacio y en ceros
            if(text.equals("")){
                JOptionPane.showMessageDialog(vv, "El campo Descuento no puede quedar vacio.");
                vv.txtPorcentajeAplicar.requestFocus();
            }else if (!Pattern.matches(regex,text)) {
                JOptionPane.showMessageDialog(vv, "Introduzca un descuento válido.");
                vv.txtPorcentajeAplicar.requestFocus();
            }else if( Double.parseDouble(text) <= 0 ){
                JOptionPane.showMessageDialog(vv, "Introduzca una Descuento mayor a 0 (cero).");
                vv.txtPorcentajeAplicar.requestFocus();
            }else if( Double.parseDouble(text) > 100 ){
                JOptionPane.showMessageDialog(vv, "Introduzca una Descuento menor a 100 (cien).");
                vv.txtPorcentajeAplicar.requestFocus();
            }else {
                if( !vv.chbAplicarDesTodProd.isSelected() ){
                    double precio = 0.0;
                    double precioPorcentaje = 0.0;
                    double totalPorcentaje = 0.0;
                    precio = Double.parseDouble(vv.tbllis.getValueAt(vv.tbllis.getSelectedRow(), 3).toString());

                    precioPorcentaje = ( precio * Double.parseDouble(text) ) / 100;
                    totalPorcentaje = precio - precioPorcentaje;

                    // Redondear el número a 2 decimales usando BigDecimal
                    BigDecimal bd = new BigDecimal(totalPorcentaje);
                    bd = bd.setScale(2, RoundingMode.HALF_UP); 

                    vv.tbllis.setValueAt(bd, vv.tbllis.getSelectedRow(), 3);

                    CalcularTotales();
                    cerrarModal(vv.dDescuentos);
                }else{
                    int filas = vv.tbllis.getRowCount();
                    for (int i = 0; i < filas; i++) {
                        double precio = 0.0;
                        double precioPorcentaje = 0.0;
                        double totalPorcentaje = 0.0;
                        precio = Double.parseDouble(vv.tbllis.getValueAt(i, 3).toString());

                        precioPorcentaje = ( precio * Double.parseDouble(text) ) / 100;
                        totalPorcentaje = precio - precioPorcentaje;

                        // Redondear el número a 2 decimales usando BigDecimal
                        BigDecimal bd = new BigDecimal(totalPorcentaje);
                        bd = bd.setScale(2, RoundingMode.HALF_UP); 

                        vv.tbllis.setValueAt(bd, i, 3);
                    }

                    CalcularTotales();
                    cerrarModal(vv.dDescuentos);
                }

            }
        }else if(tabSeleccionado == 1){ // ES IMPORTE
            // 
            // VALIDAR SI EL PORCENTAJE ES VALIDO
            String regex = "^-?\\d+(\\.\\d{1,2})?$";
            String text = vv.txtImporteAplicar.getText();
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(text);

            // validar campo vacio y en ceros
            if(text.equals("")){
                JOptionPane.showMessageDialog(vv, "El campo Descuento no puede quedar vacio.");
                vv.txtImporteAplicar.requestFocus();
            }else if (!Pattern.matches(regex,text)) {
                JOptionPane.showMessageDialog(vv, "Introduzca un descuento válido.");
                vv.txtImporteAplicar.requestFocus();
            }else if( Double.parseDouble(text) <= 0 ){
                JOptionPane.showMessageDialog(vv, "Introduzca una Descuento mayor a 0 (cero).");
                vv.txtImporteAplicar.requestFocus();
            }/*else if( Double.parseDouble(text) > 100 ){
                JOptionPane.showMessageDialog(vv, "Introduzca una Descuento menor a 100 (cien).");
                vv.txtImporteAplicar.requestFocus();
            }*/else {
                double totalPrecio = 0.0;
                totalPrecio = Double.parseDouble(text);

                // Redondear el número a 2 decimales usando BigDecimal
                BigDecimal bd = new BigDecimal(totalPrecio);
                bd = bd.setScale(2, RoundingMode.HALF_UP); 

                vv.tbllis.setValueAt(bd, vv.tbllis.getSelectedRow(), 3);

                CalcularTotales();
                cerrarModal(vv.dDescuentos);

            }
        }
    }
    
    public int devolverDetalleProducto(String codigo, String folio, double cantidad){
        // public String fol_dven = null;
        // public String codigoProducto = null;
        
        usuarioLogeado = GlobalVaribales.getInstance().usuarioLogueado;
        
        if(vm.DevolverDetalleProducto(codigo,folio,cantidad,usuarioLogeado)){
            return 1;
        }
        return 0;
        
    }
    
    public void AsignarClienteVenta(int idCliente, int idVenta, String nombreCliente){
        if(idCliente > 0){
            // Crear un arreglo de botones personalizados
            Object[] opciones = {"Confirmar", "No"};

            // Mostrar el mensaje de confirmación con los botones personalizados
            int respuesta = JOptionPane.showOptionDialog(
                vv, 
                "¿La venta ya tiene un cliente asignado, desea actualizarlo?", 
                "Confirmación", 
                JOptionPane.DEFAULT_OPTION, 
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                opciones, 
                opciones[0] // Establecer "Confirmar" como opción predeterminada
            );

            // Procesar la respuesta
            if (respuesta == 0) {
                // JOptionPane.showMessageDialog(vv, "Actualizar Cliente");
                abrirModalAsignarCliente(idCliente, idVenta, nombreCliente);
            }
        }else{
            // JOptionPane.showMessageDialog(vv, "Agregar Cliente");
            abrirModalAsignarCliente(idCliente, idVenta, nombreCliente);
        }
    }
    
    public void CargarModalDetalleVenta(){
        int fila = 0;
        fila = vv.tbllis_ven.getSelectedRow();
        if(fila < 0){
            JOptionPane.showMessageDialog(vv, "Seleccione un Registro");
        }else{

            // LIMPIAR DIALOG
            vv.c_folio_ve.setText("");
            vv.c_fechaventa_ve.setText("");
            vv.c_fechacancelado_ve.setText("");
            vv.c_estatus_ve.setText("");
            vv.c_subtotal_ve.setText("");
            vv.c_descuento_ve.setText("");
            vv.c_total_ve.setText("");
            vv.c_metodopago_ve.setText("");
            vv.c_usuarioregistra_ve.setText("");
            vv.c_tipoventa_ve.setText("");
            int c_fila = 0;
            c_fila = vv.c_tbllis_ve.getSelectedRow();
            if(c_fila == -1){

            }else{
                for (int i = 0; i < fila; i++) {
                    //c_modeltLisVE.removeRow(0);
                }
            }

            DefaultTableModel modelo = vm.ConsultarVentaFolio(vv.tbllis_ven.getValueAt(fila, 1).toString());
            
            // Si deseas obtener todas las filas y columnas de la tabla:
            for (int i = 0; i < modelo.getRowCount(); i++) {
                vv.c_folio_ve.setText(modelo.getValueAt(i, 1).toString());
                vv.c_fechaventa_ve.setText(modelo.getValueAt(i, 8).toString());
                if(modelo.getValueAt(i, 9).toString().equals("1"))
                    vv.c_estatus_ve.setText("Realizada");
                else
                    vv.c_estatus_ve.setText("Cancelada");
                vv.c_subtotal_ve.setText(modelo.getValueAt(i, 10).toString());
                vv.c_descuento_ve.setText(modelo.getValueAt(i, 4).toString());
                vv.c_total_ve.setText(modelo.getValueAt(i, 2).toString());
                if(modelo.getValueAt(i, 11) == null){
                    vv.c_fechacancelado_ve.setText("");
                }else{
                    vv.c_fechacancelado_ve.setText(modelo.getValueAt(i, 11).toString());
                }
                
                if( "0".equals(modelo.getValueAt(i, 5).toString()) ){
                    vv.c_metodopago_ve.setText("EFECTIVO");
                }else if( "1".equals(modelo.getValueAt(i, 5).toString()) ){
                    vv.c_metodopago_ve.setText("TARJETA");
                }else if( "2".equals(modelo.getValueAt(i, 5).toString()) ){
                    vv.c_metodopago_ve.setText("TRANSFERENCIA");
                }
                
                /*if( "0".equals(modelo.getValueAt(i, 7).toString()) ){
                    vv.c_tipoventa_ve.setText("CONTADO");
                }else if( "1".equals(modelo.getValueAt(i, 7).toString()) ){
                    vv.c_tipoventa_ve.setText("CREDITO");
                }*/
                
                if( "0".equals(modelo.getValueAt(i, 14).toString()) ){
                    vv.c_tipoventa_ve.setText("CONTADO");
                // }else if( "1".equals(modelo.getValueAt(i, 14).toString()) ){
                }else{
                    vv.c_tipoventa_ve.setText("CREDITO");
                }
                
                vv.c_cliente_ve.setText( modelo.getValueAt(i, 18).toString() );
                
                vv.c_usuarioregistra_ve.setText( modelo.getValueAt(i, 17).toString() );
                
                CargarConsultaDetalle2(vv.c_folio_ve.getText());

                if(modelo.getValueAt(i, 9).toString().equals("0")){
                    vv.btn_reimprimir_ticket.setEnabled(false);
                    vv.btnDevolverProducto.setEnabled(false);
                }else{
                    vv.btn_reimprimir_ticket.setEnabled(true);
                    vv.btnDevolverProducto.setEnabled(true);
                }
            }
            
            // ASIGNAR VALORES A DIALOG
            /*vv.c_folio_ve.setText(vv.tbllis_ven.getValueAt(fila, 1).toString());
            vv.c_fechaventa_ve.setText(vv.tbllis_ven.getValueAt(fila, 6).toString());
            vv.c_estatus_ve.setText(vv.tbllis_ven.getValueAt(fila, 7).toString());
            vv.c_subtotal_ve.setText(vv.tbllis_ven.getValueAt(fila, 4).toString());
            vv.c_descuento_ve.setText(vv.tbllis_ven.getValueAt(fila, 5).toString());
            vv.c_total_ve.setText(vv.tbllis_ven.getValueAt(fila, 2).toString());
            if(vv.tbllis_ven.getValueAt(fila, 8) == null){
                vv.c_fechacancelado_ve.setText("");
            }else{
                vv.c_fechacancelado_ve.setText(vv.tbllis_ven.getValueAt(fila, 8).toString());
            }
            CargarConsultaDetalle2(vv.c_folio_ve.getText());

            if(vv.tbllis_ven.getValueAt(fila, 7).toString().equals("Cancelada")){
                vv.btn_reimprimir_ticket.setEnabled(false);
                vv.btnDevolverProducto.setEnabled(false);
            }else{
                vv.btn_reimprimir_ticket.setEnabled(true);
                vv.btnDevolverProducto.setEnabled(true);
            }*/
            abrirModalDetalleVenta();
        }
    }
    
    public void CargarModalDetalleVenta_Pen(){
        int fila = 0;
        fila = vv.tbllis_pen.getSelectedRow();
        if(fila < 0){
            JOptionPane.showMessageDialog(vv, "Seleccione un Registro");
        }else{

            // LIMPIAR DIALOG
            vv.c_folio_ve.setText("");
            vv.c_fechaventa_ve.setText("");
            vv.c_fechacancelado_ve.setText("");
            vv.c_estatus_ve.setText("");
            vv.c_subtotal_ve.setText("");
            vv.c_descuento_ve.setText("");
            vv.c_total_ve.setText("");
            vv.c_metodopago_ve.setText("");
            vv.c_usuarioregistra_ve.setText("");
            vv.c_tipoventa_ve.setText("");
            int c_fila = 0;
            c_fila = vv.c_tbllis_ve.getSelectedRow();
            if(c_fila == -1){

            }else{
                for (int i = 0; i < fila; i++) {
                    //c_modeltLisVE.removeRow(0);
                }
            }

            DefaultTableModel modelo = vm.ConsultarVentaFolio(vv.tbllis_pen.getValueAt(fila, 1).toString());
            
            // Si deseas obtener todas las filas y columnas de la tabla:
            for (int i = 0; i < modelo.getRowCount(); i++) {
                vv.c_folio_ve.setText(modelo.getValueAt(i, 1).toString());
                vv.c_fechaventa_ve.setText(modelo.getValueAt(i, 8).toString());
                if(modelo.getValueAt(i, 9).toString().equals("1")){
                    vv.c_estatus_ve.setText("Realizada");
                }else if(modelo.getValueAt(i, 9).toString().equals("2")){
                    vv.c_estatus_ve.setText("Pendiente");
                }else{
                    vv.c_estatus_ve.setText("Cancelada");
                }
                vv.c_subtotal_ve.setText(modelo.getValueAt(i, 10).toString());
                vv.c_descuento_ve.setText(modelo.getValueAt(i, 4).toString());
                vv.c_total_ve.setText(modelo.getValueAt(i, 2).toString());
                if(modelo.getValueAt(i, 11) == null){
                    vv.c_fechacancelado_ve.setText("");
                }else{
                    vv.c_fechacancelado_ve.setText(modelo.getValueAt(i, 11).toString());
                }
                
                if( "0".equals(modelo.getValueAt(i, 5).toString()) ){
                    vv.c_metodopago_ve.setText("EFECTIVO");
                }else if( "1".equals(modelo.getValueAt(i, 5).toString()) ){
                    vv.c_metodopago_ve.setText("TARJETA");
                }else if( "2".equals(modelo.getValueAt(i, 5).toString()) ){
                    vv.c_metodopago_ve.setText("TRANSFERENCIA");
                }
                
                if( "0".equals(modelo.getValueAt(i, 14).toString()) ){
                    vv.c_tipoventa_ve.setText("CONTADO");
                }else{
                    vv.c_tipoventa_ve.setText("CREDITO");
                }
                
                vv.c_cliente_ve.setText( modelo.getValueAt(i, 18).toString() );
                
                vv.c_usuarioregistra_ve.setText( modelo.getValueAt(i, 17).toString() );
                
                CargarConsultaDetalle2(vv.c_folio_ve.getText());

                if(modelo.getValueAt(i, 9).toString().equals("0")){
                    vv.btn_reimprimir_ticket.setEnabled(false);
                    vv.btnDevolverProducto.setEnabled(false);
                }else{
                    vv.btn_reimprimir_ticket.setEnabled(true);
                    vv.btnDevolverProducto.setEnabled(true);
                }
            }
            abrirModalDetalleVenta();
        }
    }

    @Override
    public void sorterChanged(RowSorterEvent e) {
        
    }
    
    public void CargarCatPro(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        vv.cmbDepartamentoDetalle.removeAllItems();
        cpm.setId(-2);
        
        modelt = cpm.CargarCatPro();
        
        // Iterar sobre las filas del DefaultTableModel y agregar los usuarios al JComboBox
        for (int i = 0; i < modelt.getRowCount(); i++) {
            // Asumimos que la columna 0 es el 'id' y la columna 1 es el 'nombre'
            int id = (int) modelt.getValueAt(i, 0); // Obtén el 'id'
            String nombre = (String) modelt.getValueAt(i, 1); // Obtén el 'nombre'

            // Crear un nuevo objeto Usuario
            CatProListM catProList = new CatProListM(id, nombre);

            // Agregar el usuario al JComboBox
            vv.cmbDepartamentoDetalle.addItem(catProList);
        }
        
    }
    
    public void BuscarClientesCredito(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        
        cm.setId(-2);
        
        modelt = cm.CargarClientes();
        
        vv.cmbCliente.removeAllItems();
        
        // Agregar CLIENTE GENERAL
        vv.cmbCliente.addItem(new ClientesListM(0, "CLIENTE GENERAL"));
        
        // Iterar sobre las filas del DefaultTableModel y agregar los usuarios al JComboBox
        for (int i = 0; i < modelt.getRowCount(); i++) {
            // Asumimos que la columna 0 es el 'id' y la columna 1 es el 'nombre'
            int id = (int) modelt.getValueAt(i, 0); // Obtén el 'id'
            String nombre = (String) modelt.getValueAt(i, 1); // Obtén el 'nombre'

            // Crear un nuevo objeto Usuario
            ClientesListM clientesList = new ClientesListM(id, nombre);

            // Agregar el usuario al JComboBox
            vv.cmbCliente.addItem(clientesList);
        }
        
    }
    
    public void BuscarClientesAsignar(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        
        cm.setId(-2);
        
        modelt = cm.CargarClientes();
        
        vv.cmb_nuevoCliente_AC.removeAllItems();
        
        // Agregar CLIENTE GENERAL
        vv.cmb_nuevoCliente_AC.addItem(new ClientesListM(0, "CLIENTE GENERAL"));
        
        // Iterar sobre las filas del DefaultTableModel y agregar los usuarios al JComboBox
        for (int i = 0; i < modelt.getRowCount(); i++) {
            // Asumimos que la columna 0 es el 'id' y la columna 1 es el 'nombre'
            int id = (int) modelt.getValueAt(i, 0); // Obtén el 'id'
            String nombre = (String) modelt.getValueAt(i, 1); // Obtén el 'nombre'

            // Crear un nuevo objeto Usuario
            ClientesListM clientesList = new ClientesListM(id, nombre);

            // Agregar el usuario al JComboBox
            vv.cmb_nuevoCliente_AC.addItem(clientesList);
        }
        
    }
    
    public void CargarDetalleVentas(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
         };
        
        String fechaDe = null;
        String fechaAl = null;
        Date fecha_de;
        Date fecha_al;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        fecha_de = vv.fechaDeDetalle.getDate();
        fecha_al = vv.fechaAlDetalle.getDate();
        fechaDe = sdf.format(fecha_de)+" 00:00:00";
        fechaAl = sdf.format(fecha_al)+" 23:59:59";
        // System.out.println("Fecha De " + fechaDe);
        // System.out.println("Fecha Al " + fechaAl);
        
        String textoBusqueda;
        textoBusqueda = vv.txtTextoBusqueda.getText().toString();
        
        CatProListM catProSeleccionado = (CatProListM) vv.cmbDepartamentoDetalle.getSelectedItem();
        int idSeleccionado = catProSeleccionado.getId();
        // System.out.println("ID Cat: " + idSeleccionado);
        // String nombreSeleccionado = catProSeleccionado.getNombre();
        
        usuarioLogeado = GlobalVaribales.getInstance().usuarioLogueado;
        // System.out.println("Usuario Logueado: " + usuarioLogeado);
        
        UsuarioListM usuarioSeleccionado = (UsuarioListM) vv.cmbUsuariosDetalle.getSelectedItem();
        String userSeleccionado = usuarioSeleccionado.getUser();
        
        modelt = vm.CargarDetalleVentas(fechaDe,fechaAl,idSeleccionado,userSeleccionado,textoBusqueda);
        vv.tbllisDetalle.setModel(modelt);
        
        vv.tbllisDetalle.getTableHeader().setReorderingAllowed(false);
        
        vv.tbllisDetalle.getColumnModel().getColumn(0).setMaxWidth(75);
        vv.tbllisDetalle.getColumnModel().getColumn(0).setMinWidth(75);
        vv.tbllisDetalle.getColumnModel().getColumn(0).setPreferredWidth(75);//ID
        
        // vv.tbllisDetalle.getColumnModel().getColumn(2).setMaxWidth(50);
        // vv.tbllisDetalle.getColumnModel().getColumn(2).setMinWidth(50);
        vv.tbllisDetalle.getColumnModel().getColumn(2).setPreferredWidth(50);//ID
        
        // vv.tbllisDetalle.getColumnModel().getColumn(3).setMaxWidth(250);
        // vv.tbllisDetalle.getColumnModel().getColumn(3).setMinWidth(250);
        vv.tbllisDetalle.getColumnModel().getColumn(3).setPreferredWidth(250);//ID
        
    }
    
    public void CargarUsuarios(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        um.setId(-2);
        modelt = um.CargarUsuarios();
        
        vv.cmbUsuariosDetalle.addItem(new UsuarioListM(0, "TODOS", ""));
        
        // Iterar sobre las filas del DefaultTableModel y agregar los usuarios al JComboBox
        for (int i = 0; i < modelt.getRowCount(); i++) {
            // Asumimos que la columna 0 es el 'id' y la columna 1 es el 'nombre'
            int id = (int) modelt.getValueAt(i, 0); // Obtén el 'id'
            String nombre = (String) modelt.getValueAt(i, 1); // Obtén el 'nombre'
            String user = (String) modelt.getValueAt(i, 2); // Obtén el 'user'

            // Crear un nuevo objeto Usuario
            UsuarioListM usuario = new UsuarioListM(id, nombre, user);

            // Agregar el usuario al JComboBox
            vv.cmbUsuariosDetalle.addItem(usuario);
        }
    }
    
    public void CargarCuentasPorCobrar(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
         };
        
        String fechaDe = null;
        String fechaAl = null;
        Date fecha_de;
        Date fecha_al;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        fecha_de = vv.fechaDe.getDate();
        fecha_al = vv.fechaAl.getDate();
        fechaDe = sdf.format(fecha_de)+" 00:00:00";
        fechaAl = sdf.format(fecha_al)+" 23:59:59";
        
        cpcm.setID(-1);
        cpcm.setFECHADE(fechaDe);
        cpcm.setFECHAAL(fechaAl);
        
        modelt = cpcm.CargarCuentasPorCobrar();
        vv.tbllisCxC.setModel(modelt);
        
        vv.tbllisCxC.getTableHeader().setReorderingAllowed(false);
        
        /*vv.tbllisDetalle.getColumnModel().getColumn(0).setMaxWidth(75);
        vv.tbllisDetalle.getColumnModel().getColumn(0).setMinWidth(75);
        vv.tbllisDetalle.getColumnModel().getColumn(0).setPreferredWidth(75);//ID
        
        vv.tbllisDetalle.getColumnModel().getColumn(2).setMaxWidth(50);
        vv.tbllisDetalle.getColumnModel().getColumn(2).setMinWidth(50);
        vv.tbllisDetalle.getColumnModel().getColumn(2).setPreferredWidth(50);//ID
        
        vv.tbllisDetalle.getColumnModel().getColumn(3).setMaxWidth(250);
        vv.tbllisDetalle.getColumnModel().getColumn(3).setMinWidth(250);
        vv.tbllisDetalle.getColumnModel().getColumn(3).setPreferredWidth(250);//ID*/
    }
    
    public void GuardarAsignarCliente(){
        int idCliente = 0;
        int idVenta = 0;

        ClientesListM clienteSeleccionado = (ClientesListM) vv.cmb_nuevoCliente_AC.getSelectedItem();
        idCliente = clienteSeleccionado.getId();

        idVenta = Integer.parseInt(vv.txt_idVenta_AC.getText());

        // COMPARAR QUE NO SELECCIONE EL MISMO CLIENTE
        int idClienteActual = 0;
        idClienteActual = Integer.parseInt(vv.txt_idCliente_AC.getText());
       
        if( idCliente == idClienteActual ){
            JOptionPane.showMessageDialog(vv, "Seleccione un cliente diferente al actual.");
            return;
        }
        
        // usuarioLogeado = GlobalVaribales.getInstance().usuarioLogueado;
        
        if(!cm.ActualizarCliente(idVenta, idCliente, usuarioLogeado)){
            JOptionPane.showMessageDialog(vv, "Ocurrió un error al actualizar el cliente. Contacte a soporte técnico.");
            return;
        }
        
        JOptionPane.showMessageDialog(vv, "Cliente actualizado correctamente.");
        cerrarModal(vv.dAsignarCliente);
        CargarVentasCancelar();
        
    }
    
    public void CargarClientesSaldos(){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        cm.setId(-3);
        modelt = cm.CargarClientes();
        
        vv.tbllisClientesCreditos.setModel(modelt);
        vv.tbllisClientesCreditos.getTableHeader().setReorderingAllowed(false);
        
        vv.tbllisClientesCreditos.getColumnModel().getColumn(0).setMaxWidth(0);
        vv.tbllisClientesCreditos.getColumnModel().getColumn(0).setMinWidth(0);
        vv.tbllisClientesCreditos.getColumnModel().getColumn(0).setPreferredWidth(0);//ID
        
        // vv.tbllisDetalle.getColumnModel().getColumn(2).setMaxWidth(50);
        // vv.tbllisDetalle.getColumnModel().getColumn(2).setMinWidth(50);
        // vv.tbllisDetalle.getColumnModel().getColumn(2).setPreferredWidth(50);//ID
        
        vv.tbllisClientesCreditos.getColumnModel().getColumn(2).setMaxWidth(150);
        vv.tbllisClientesCreditos.getColumnModel().getColumn(2).setMinWidth(150);
        vv.tbllisClientesCreditos.getColumnModel().getColumn(2).setPreferredWidth(150);//ID
    }
    
    public void CargarMovimientosCreditos(int idCliente){
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        String fechaDe;
        String fechaAl;
        
        Date fecha_de;
        Date fecha_al;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        fecha_de = vv.fechaDeCreditos.getDate();
        fecha_al = vv.fechaAlCreditos.getDate();
        fechaDe = sdf.format(fecha_de)+" 00:00:00";
        fechaAl = sdf.format(fecha_al)+" 23:59:59";
        
        cm.setId(-4);
        cm.setEstatus(idCliente);
        cm.setNombre(fechaDe);
        cm.setTelefono(fechaAl);
        modelt = cm.CargarClientes();
        
        vv.tbllisCreditosMovimientos.setModel(modelt);
        vv.tbllisCreditosMovimientos.getTableHeader().setReorderingAllowed(false);
        
        vv.tbllisCreditosMovimientos.getColumnModel().getColumn(0).setMaxWidth(0);
        vv.tbllisCreditosMovimientos.getColumnModel().getColumn(0).setMinWidth(0);
        vv.tbllisCreditosMovimientos.getColumnModel().getColumn(0).setPreferredWidth(0);
        
        vv.tbllisCreditosMovimientos.getColumnModel().getColumn(1).setMaxWidth(0);
        vv.tbllisCreditosMovimientos.getColumnModel().getColumn(1).setMinWidth(0);
        vv.tbllisCreditosMovimientos.getColumnModel().getColumn(1).setPreferredWidth(0);
        
        vv.tbllisCreditosMovimientos.getColumnModel().getColumn(2).setMaxWidth(0);
        vv.tbllisCreditosMovimientos.getColumnModel().getColumn(2).setMinWidth(0);
        vv.tbllisCreditosMovimientos.getColumnModel().getColumn(2).setPreferredWidth(0);
        
        vv.tbllisCreditosMovimientos.getColumnModel().getColumn(3).setMaxWidth(100);
        vv.tbllisCreditosMovimientos.getColumnModel().getColumn(3).setMinWidth(100);
        vv.tbllisCreditosMovimientos.getColumnModel().getColumn(3).setPreferredWidth(100);
        
        vv.tbllisCreditosMovimientos.getColumnModel().getColumn(4).setMaxWidth(100);
        vv.tbllisCreditosMovimientos.getColumnModel().getColumn(4).setMinWidth(100);
        vv.tbllisCreditosMovimientos.getColumnModel().getColumn(4).setPreferredWidth(100);
        
        vv.tbllisCreditosMovimientos.getColumnModel().getColumn(5).setMaxWidth(150);
        vv.tbllisCreditosMovimientos.getColumnModel().getColumn(5).setMinWidth(150);
        vv.tbllisCreditosMovimientos.getColumnModel().getColumn(5).setPreferredWidth(150);
        
        vv.tbllisCreditosMovimientos.getColumnModel().getColumn(7).setMaxWidth(100);
        vv.tbllisCreditosMovimientos.getColumnModel().getColumn(7).setMinWidth(100);
        vv.tbllisCreditosMovimientos.getColumnModel().getColumn(7).setPreferredWidth(100);
        
        /*vv.tbllisClientesCreditos.getColumnModel().getColumn(0).setMaxWidth(0);
        vv.tbllisClientesCreditos.getColumnModel().getColumn(0).setMinWidth(0);
        vv.tbllisClientesCreditos.getColumnModel().getColumn(0).setPreferredWidth(0);//ID
        
        // vv.tbllisDetalle.getColumnModel().getColumn(2).setMaxWidth(50);
        // vv.tbllisDetalle.getColumnModel().getColumn(2).setMinWidth(50);
        // vv.tbllisDetalle.getColumnModel().getColumn(2).setPreferredWidth(50);//ID
        
        vv.tbllisClientesCreditos.getColumnModel().getColumn(2).setMaxWidth(150);
        vv.tbllisClientesCreditos.getColumnModel().getColumn(2).setMinWidth(150);
        vv.tbllisClientesCreditos.getColumnModel().getColumn(2).setPreferredWidth(150);//ID*/
    }
    
    public void GuardarAbonoCredito(){
        String nombreCliente = null;
        int idCliente = 0;
        String abonoTxt = null;
        double abono = 0.0;
        String comentariosTexto = null;
        String formaPago = null;

        nombreCliente = vv.txt_clienteActual_RA.getText();
        idCliente = Integer.parseInt(vv.txt_idCliente_RA.getText());
        comentariosTexto = vv.txt_comentarios_RA.getText();
        formaPago = vv.cmb_metodoPago_RA.getSelectedItem().toString();

        String texto = vv.txt_cantidadAbono_RA.getText().trim();

        if (texto.equals("")) {
            JOptionPane.showMessageDialog(null, "Ingresa un abono válido.");
            return;
        }
        
        if (!texto.matches("\\d+(\\.\\d+)?")) {
            JOptionPane.showMessageDialog(null, "Ingresa un abono válido.");
            return;
        }
        abono = Double.parseDouble(texto);
        
        if(abono <= 0){
            JOptionPane.showMessageDialog(null, "Ingresa un abono mayor a cero.");
            return;
        }

        // Crear un arreglo de botones personalizados
        Object[] opciones = {"Confirmar", "No"};

        // Mostrar el mensaje de confirmación con los botones personalizados
        int respuesta = JOptionPane.showOptionDialog(
            vv, 
            "¿Confirmar abono al cliente "+nombreCliente+"?", 
            "Confirmación", 
            JOptionPane.DEFAULT_OPTION, 
            JOptionPane.QUESTION_MESSAGE, 
            null, 
            opciones, 
            opciones[0] // Establecer "Confirmar" como opción predeterminada
        );

        // Procesar la respuesta
        if (respuesta == 0) {
            // JOptionPane.showMessageDialog(vv, "Registrar Abono");

            usuarioLogeado = GlobalVaribales.getInstance().usuarioLogueado;
            
            cam.setId(0);
            cam.setIdVenta(0);
            cam.setIdCliente(idCliente);
            cam.setTotal(abono);
            cam.setTipo("ABONO");
            cam.setFechaRegistro(null);
            cam.setUsuarioRegistra(usuarioLogeado);
            cam.setComentarios(comentariosTexto);
            cam.setFormaPago(formaPago);

            if(!cam.GuardarClientesAbonos()){
                JOptionPane.showMessageDialog(vv, "Ocurrió un error al registrar el abono. Contacte a soporte técnico.");
                return;
            }

            try {
                imprimirAbono(nombreCliente,abono,formaPago,comentariosTexto);
            } catch (IOException ex) {
                Logger.getLogger(VentasFrameC.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(vv, "Abono registrado correctamente.");
            // cerrarModal(vv.dAsignarCliente);
            // Cargar abonos
            CargarClientesSaldos();
            seleccionarClientePorId(vv.tbllisClientesCreditos, idCliente);
            CargarMovimientosCreditos(idCliente);
            cerrarModal(vv.dAbonosCreditos);
        }
    }
    
    public void seleccionarClientePorId(JTable tabla, int idBuscado) {
        for (int i = 0; i < tabla.getRowCount(); i++) {
            Object valor = tabla.getValueAt(i, 0); // columna 0

            if (valor != null && Integer.parseInt(valor.toString()) == idBuscado) {
                tabla.setRowSelectionInterval(i, i);
                tabla.scrollRectToVisible(tabla.getCellRect(i, 0, true));
                break;
            }
        }
    }
    
    public int imprimirAbono(String nombreCliente, double abono, String formaPago, String comentarios) throws IOException{
        
        /*int fila = 0;
        fila = vv.tbllisCreditosMovimientos.getSelectedRow();
        if( fila <= 0 ){
            JOptionPane.showMessageDialog(vv, "Seleccione un abono.");
        }*/
        
        DefaultTableModel modelt = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        em.setId(-1);
        em.setNom(null);
        em.setCal(null);
        em.setNum(null);
        em.setCol(null);
        em.setTel(null);
        em.setCel(null);
        em.setMun(null);
        em.setEst(null);
        em.setTex(null);
        modelt = em.CargarEmpresa();
        
        Date fechaActual = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        String fechaFormateada = formato.format(fechaActual);
        
        Ticket ticket = new Ticket();
        
        if(modelt.getRowCount()<=0){
            
        }else{
            Ticket.AddCabecera(modelt.getValueAt(0, 1).toString());
            Ticket.AddCabecera(Ticket.DarEspacio());
            Ticket.AddCabecera(modelt.getValueAt(0, 9).toString());
            Ticket.AddCabecera(Ticket.DarEspacio());
            Ticket.AddCabecera(modelt.getValueAt(0, 2).toString() + " #"+modelt.getValueAt(0, 3).toString() + " " + modelt.getValueAt(0, 4).toString());
            Ticket.AddCabecera(Ticket.DarEspacio());
            if(modelt.getValueAt(0, 5) == null){
                
            }else{
            }
            Ticket.AddCabecera(modelt.getValueAt(0, 7).toString() + ", " + modelt.getValueAt(0, 8).toString());
            Ticket.AddCabecera(Ticket.DarEspacio());
            Ticket.AddCabecera("WhatsApp: " + modelt.getValueAt(0, 6).toString() );
            Ticket.AddCabecera(Ticket.DarEspacio());
        }
        Ticket.AddCabecera(Ticket.DibujarLinea(29));
        Ticket.AddCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera("Caja UNICA");
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera("Le Atiende: " + GlobalVaribales.getInstance().nombreLogueado);
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera("Cliente: " + nombreCliente);
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera("Metodo de Pago: " + formaPago);
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera(fechaFormateada);
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        
        Ticket.AddSubCabecera(Ticket.DibujarLinea(29));
        
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera("Monto de Abono: " + abono);
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera("Comentarios:");
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        Ticket.AddSubCabecera(comentarios);
        Ticket.AddSubCabecera(Ticket.DarEspacio());
        
        if(modelt.getRowCount() <= 0){
            
        }else{
            Ticket.AddPieLinea(Ticket.DarEspacio());
            Ticket.AddPieLinea(Ticket.DarEspacio());
            Ticket.AddPieLinea(Ticket.DarEspacio());
            Ticket.AddPieLinea(Ticket.DarEspacio());
        }
        
        int a = 0;
        a = Ticket.ImprimirDocumento(true);
        if(a == 1){
            return 1;
        }
        return 0;
    
    }
    
    public void CancelarMovimientoAbonoCargo(int fila){
        String idMovimiento = null;
        int idVenta = 0;
        int idCliente = 0;
        String tipo = null;
        double total = 0;
        
        idMovimiento = vv.tbllisCreditosMovimientos.getValueAt(fila, 0).toString();
        idVenta = Integer.parseInt(vv.tbllisCreditosMovimientos.getValueAt(fila, 1).toString());
        idCliente = Integer.parseInt(vv.tbllisCreditosMovimientos.getValueAt(fila, 2).toString());
        tipo = vv.tbllisCreditosMovimientos.getValueAt(fila, 3).toString();
        total = Double.parseDouble(vv.tbllisCreditosMovimientos.getValueAt(fila, 4).toString());
        
        cam.setId(-1);
        cam.setIdVenta(idVenta);
        cam.setIdCliente(idCliente);
        cam.setTotal(total);
        cam.setTipo(tipo);
        cam.setFechaRegistro(null);
        cam.setUsuarioRegistra(idMovimiento);
        cam.setComentarios(null);
        cam.setFormaPago(null);

        if(!cam.GuardarClientesAbonos()){
            JOptionPane.showMessageDialog(vv, "Ocurrió un error al registrar el abono. Contacte a soporte técnico.");
            return;
        }
        
        JOptionPane.showMessageDialog(vv, "Abono cancelado correctamente.");
        
        CargarClientesSaldos();
        seleccionarClientePorId(vv.tbllisClientesCreditos, idCliente);
        CargarMovimientosCreditos(idCliente);
        cerrarModal(vv.dAbonosCreditos);
            
    }
    
    public double calcularTotalConDescuento(int fPago) {
        DefaultTableModel modelo = (DefaultTableModel) vv.tbllis.getModel();
        int totalFilas = modelo.getRowCount();

        double nuevoTotalGeneral = 0.0;

        // 1. Cargamos el mapa de descuentos en RAM una sola vez (¡Súper rápido!)
        java.util.Map<Integer, Double> deptosConDescuento = cpm.obtenerDescuentosDepartamentos();

        for (int i = 0; i < totalFilas; i++) {
            double cantidad = Double.parseDouble(modelo.getValueAt(i, 4).toString());
            double precioUnitario = Double.parseDouble(modelo.getValueAt(i, 3).toString());
            int idDepartamento = Integer.parseInt(modelo.getValueAt(i, 6).toString()); // Columna del depto

            double subtotalLinea = cantidad * precioUnitario;
            
            if( fPago != 1 ){

                // 2. Verificamos de forma instantánea si el departamento tiene descuento registrado
                if (deptosConDescuento.containsKey(idDepartamento)) {
                    double porcentajeDescuento = deptosConDescuento.get(idDepartamento);
                    double descuento = subtotalLinea * (porcentajeDescuento / 100.0);
                    subtotalLinea -= descuento; // Se aplica el descuento a la línea
                }
                
            }

            nuevoTotalGeneral += subtotalLinea;
        }

        return nuevoTotalGeneral;
    }
    
}
