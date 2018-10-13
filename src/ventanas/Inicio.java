/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ventanas;


import Controlador.Operaciones;
import Modelo.CategoriaPedidos;
import Modelo.CategoriaProductos; 
import Modelo.Pedidos;
import Modelo.Productos;
import Modelo.ProductosEstacionesId;
import java.awt.Image;
import java.awt.Toolkit;
import ServerFood.AdminAplicacion;
import ServerFood.ProductoTransmision;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 *
 * @author C.TELLO
 */
public class Inicio extends javax.swing.JFrame {

    public javax.swing.JLabel getEstadoP1()
    {return estadoP1;}
    public javax.swing.JLabel getEstadoP2()
    {return estadoP2;}
    public javax.swing.JLabel getEstadoP3()
    {return estadoP3;}
    public javax.swing.JLabel getEstadoP4()
    {return estadoP4;}
    public javax.swing.JLabel getEstadoP5()
    {return estadoP5;}
    
    private AdminAplicacion administrador;
    public Operaciones operaciones;
    private ArrayList<ProductoTransmision> productosTransmision; 
    public Inicio() {
        
        administrador = new AdminAplicacion();
        operaciones = new Operaciones();
        administrador.activaConeccion();
        administrador.setVentadaPrincipal(this);
        administrador.iniciarInterfazHilo();
        
        initComponents();
        ocultarPaneles();
        this.setLocationRelativeTo(null);
        soloPrecios(precioProducto);
        productosTransmision =  new ArrayList();
        llenar_combo_categorias();
        llenar_combo_catPedido();
        pintarTodosPaneles();
        
    }
    public void pintarTodosPaneles()
    {
        pintarJpanel(panelProductos);
        pintarJpanel(paneRealizarPedidos);
        pintarJpanel(panelPedidosPendientes);
        pintarJpanel(panelPedidosEntregados);
        pintarJpanel(panelDomiciliosPendientes);
        pintarJpanel(panelDomiciliosAsignados);
        pintarJpanel(panelPagos);
    }
    public class JPanelBackground extends JPanel{
        
        private Image background;
        private JPanel panel;
        // Metodo que es llamado automaticamente por la maquina virtual Java cada vez que repinta
        public void paintComponent(Graphics g) {

                /* Obtenemos el tamaño del panel para hacer que se ajuste a este
                cada vez que redimensionemos la ventana y se lo pasamos al drawImage */
                int width = this.getSize().width;
                int height = this.getSize().height;

                // Mandamos que pinte la imagen en el panel
                if (this.background != null) {
                        g.drawImage(this.background, 0, 0, width, height, null);
                }
                panel.paintComponents(g);
                super.paintComponent(g);
        }

        // Metodo donde le pasaremos la dirección de la imagen a cargar.
        public void setBackground(JPanel panel) {
                String ruta=("/imagenes/imgPaneles.png");
                // Construimos la imagen y se la asignamos al atributo background.
                this.panel = panel;
                this.panel.setOpaque(false);
                this.background = new ImageIcon(ruta).getImage();
                this.panel.repaint();
                repaint();
                panel = this;
        }
        
        };
    
    public void pintarJpanel(JPanel panel)
    {
        JPanelBackground prueba =  new JPanelBackground();
        prueba.setBackground(panel);
    }
    //********************************************************************************
    //*************************LLENAR TABLA DOMICILIOS-PENDIENTES********************************
    public void llenar_tabla_domicilios_pendientes(){
        
        List<Pedidos> listaDomiciliosPendientes = operaciones.consultarDomiciliosPendientes();
        
        String mat[][] = new String[listaDomiciliosPendientes.size()][5];
        for(int i = 0; i < listaDomiciliosPendientes.size();i++)
        {
            mat[i][0] = String.valueOf(listaDomiciliosPendientes.get(i).getNombreCliente());
            
            Locale locale = new Locale("es","CO");
            NumberFormat nf = DecimalFormat.getCurrencyInstance(locale);
            
            int precio = listaDomiciliosPendientes.get(i).getPrecioTotal();
            String precioFinal = nf.format(precio).substring(0,nf.format(precio).length()-3);
            
            mat[i][1] = listaDomiciliosPendientes.get(i).getDireccion();
            
            mat[i][2] = listaDomiciliosPendientes.get(i).getTelefonoCliente();
            mat[i][3] = precioFinal;
            mat[i][4] = listaDomiciliosPendientes.get(i).getHoraInicio().toString();
        }
        tablaDomiciliosPendientes.setModel(new javax.swing.table.DefaultTableModel(
        mat,
                new String[]{
                "Nombre","Direccion","Telefono","Precio Total","Hora Inicio"
                }
        ));     
    }
    public void Limpiar_tabla_tabla_domicilios_pendientes()
    { 
        ArrayList<Pedidos> listaDomiciliosPendientes = new ArrayList();
        String mat[][] = new String[listaDomiciliosPendientes.size()][5];
        tablaDomiciliosPendientes.setModel(new javax.swing.table.DefaultTableModel(
        mat,
                new String[]{
                "Nombre","Direccion","Telefono","Precio Total","Hora Inicio"
                }
        ));     
    }
    //********************************************************************************
    //*************************LLENAR TABLA PENDIENTES********************************
    public void llenar_tabla_pendientes(){
        
        List<Pedidos> listaPedidosPendientes = operaciones.consultarPedidosPendientes();
        Iterator<Pedidos> iter = listaPedidosPendientes.iterator();
        
        String mat[][] = new String[listaPedidosPendientes.size()][4];
        for(int i = 0; i < listaPedidosPendientes.size();i++)
        {
            if(listaPedidosPendientes.get(i).getId_categoria_pedidos() == 1)
            {
                mat[i][0] = "MESA: "+String.valueOf(listaPedidosPendientes.get(i).getMesa());
            }
            else if(listaPedidosPendientes.get(i).getId_categoria_pedidos() == 2)
            {
                mat[i][0] = String.valueOf(listaPedidosPendientes.get(i).getNombreCliente());
            }
            
            
            Locale locale = new Locale("es","CO");
            NumberFormat nf = DecimalFormat.getCurrencyInstance(locale);
            
            int precio = listaPedidosPendientes.get(i).getPrecioTotal();
            String precioFinal = nf.format(precio).substring(0,nf.format(precio).length()-3);
            
            mat[i][1] = precioFinal;
            
            mat[i][2] = String.valueOf(listaPedidosPendientes.get(i).getHoraInicio());
            mat[i][3] = String.valueOf(listaPedidosPendientes.get(i).getEstado());

        }
        tablaPedidosPendientes.setModel(new javax.swing.table.DefaultTableModel(
        mat,
                new String[]{
                "Nombre||Mesa","Precio Total","Hora Inicio","Estado"
                }
        ));     
    }
    public void Limpiar_tabla_pendientes()
    { 
        ArrayList<Pedidos> listaPedidosPendientes = new ArrayList();
        String mat[][] = new String[listaPedidosPendientes.size()][4];
        tablaPedidosPendientes.setModel(new javax.swing.table.DefaultTableModel(
        mat,
                new String[]{
                "Nombre||Mesa","Precio Total","Hora Inicio","Estado"
                }
        ));     
    }
    //********************************************************************************
    //*************************LLENAR TABLA ENTREGADOS********************************
    public void llenar_tabla_entregados(){
        
        List<Pedidos> listaPedidosEntregados = operaciones.consultarPedidosEntregados();
        Iterator<Pedidos> iter = listaPedidosEntregados.iterator();
        
        String mat[][] = new String[listaPedidosEntregados.size()][3];
        for(int i = 0; i < listaPedidosEntregados.size();i++)
        {
            if(listaPedidosEntregados.get(i).getId_categoria_pedidos() == 1)
            {
                mat[i][0] = "MESA: "+String.valueOf(listaPedidosEntregados.get(i).getMesa());
            }
            else if(listaPedidosEntregados.get(i).getId_categoria_pedidos() == 2)
            {
                mat[i][0] = String.valueOf(listaPedidosEntregados.get(i).getNombreCliente());
            }
            
            
            Locale locale = new Locale("es","CO");
            NumberFormat nf = DecimalFormat.getCurrencyInstance(locale);
            
            int precio = listaPedidosEntregados.get(i).getPrecioTotal();
            String precioFinal = nf.format(precio).substring(0,nf.format(precio).length()-3);
            
            mat[i][1] = precioFinal;
            
            mat[i][2] = String.valueOf(listaPedidosEntregados.get(i).getHoraInicio());
            

        }
        tablaPedidosEntregados.setModel(new javax.swing.table.DefaultTableModel(
        mat,
                new String[]{
                "Nombre||Mesa","Precio Total","Hora Inicio","Estado"
                }
        ));     
    }
    public void Limpiar_tabla_entregados()
    { 
        ArrayList<Pedidos> listaPedidosEntregados = new ArrayList();
        String mat[][] = new String[listaPedidosEntregados.size()][3];
        tablaPedidosEntregados.setModel(new javax.swing.table.DefaultTableModel(
        mat,
                new String[]{
                "Nombre||Mesa","Precio Total","Hora Inicio","Estado"
                }
        ));     
    }
    //********************************************************************************
    public void llenar_tabla_productos(){
        String mat[][] = new String[productosTransmision.size()][6];
        String totalPrecioPedido = "";
        int totalPrecioPagar = 0;
        for(int i = 0; i < productosTransmision.size();i++)
        {
            mat[i][0] = productosTransmision.get(i).getNombre_producto();
            
            Locale locale = new Locale("es","CO");
            NumberFormat nf = DecimalFormat.getCurrencyInstance(locale);
            
            int precio = productosTransmision.get(i).getPrecio();
            String precioFinal = nf.format(precio).substring(0,nf.format(precio).length()-3);
            
            mat[i][1] = precioFinal;
            mat[i][2] = String.valueOf(productosTransmision.get(i).getCantidad());
            mat[i][3] = productosTransmision.get(i).getObservacion();
            mat[i][4] = productosTransmision.get(i).getSalsas();
            
            int precioTotal = productosTransmision.get(i).getPrecio() * productosTransmision.get(i).getCantidad();
            totalPrecioPagar = totalPrecioPagar + precioTotal;
            String precioTotalFinal = nf.format(precioTotal).substring(0,nf.format(precioTotal).length()-3);
            totalPrecioPedido = nf.format(totalPrecioPagar).substring(0,nf.format(totalPrecioPagar).length()-3);
            
            mat[i][5] = precioTotalFinal;
        }
        tablaProductos.setModel(new javax.swing.table.DefaultTableModel(
        mat,
                new String[]{
                "Nombre Producto","Precio","Cantidad","Observaciones","Salsas","Precio Total"
                }
        ));
        
        totalPagar.setText(totalPrecioPedido);      
    }
    public void Limpiar_tabla_productos()
    {
        String mat[][] = new String[productosTransmision.size()][6];
        tablaProductos.setModel(new javax.swing.table.DefaultTableModel(
        mat,
                new String[]{
                "Nombre Producto","Precio","Cantidad","Observaciones","Salsas","Precio Total"
                }
        ));
        totalPagar.setText("");
        
    }
    public void llenar_combo_categorias() {        
        
        List<CategoriaProductos> lista = operaciones.consultarTodasCategorias();
        Iterator<CategoriaProductos> iter = lista.iterator();
        
        limpiarComboBox();
        comboCategoria.addItem("Seleccione Categoria");
        comboConsultarCat.addItem("Seleccione Categoria");
        comboCatAgregar.addItem("Seleccione Categoria");
        while(iter.hasNext())
        {
            CategoriaProductos catPro = iter.next();
            comboCategoria.addItem(catPro.getNombre());
            comboConsultarCat.addItem(catPro.getNombre());
            comboCatAgregar.addItem(catPro.getNombre());
            //System.out.println("Nombre: "+ catPro.getNombre());
        }   
}
    public void llenar_combo_catPedido() {        
        
        List<CategoriaPedidos> lista = operaciones.consultarTodasCategoriasPedidos();
        Iterator<CategoriaPedidos> iter = lista.iterator();
        
        comboCatPedido.addItem("Seleccione Categoria");
        while(iter.hasNext())
        {
            CategoriaPedidos catPedido = iter.next();
            if(catPedido.getId() != 3)
            {
                comboCatPedido.addItem(catPedido.getNombre());
            }
            
            
            //System.out.println("Nombre: "+ catPro.getNombre());
        }   
}
    public void limpiarComboBox()
    {
        comboCategoria.removeAllItems();
        comboConsultarCat.removeAllItems();
        comboConsutarProd.removeAllItems();
        comboCatAgregar.removeAllItems();
        comboProAgregar.removeAllItems();
        comboCatPedido.removeAllItems();
    }
    public void soloPrecios(JTextField textfield)
    {        
        textfield.addKeyListener(new KeyAdapter() {
        public void keyTyped(KeyEvent e){
            char c = e.getKeyChar();
            if(Character.isLetter(c)){
            e.consume();
            }}});
        
        textfield.addFocusListener(new FocusListener() {
        public void focusGained(FocusEvent e){
            if(precioProducto.getText().compareTo("")!=0)
                {
                    System.out.println("Enfocado");
                    String Fprecio1 = precioProducto.getText().replace(".", "");
                    String Fprecio2 = Fprecio1.replace("$", "");
                    precioProducto.setText(Fprecio2);
                }
        };
        public void focusLost(FocusEvent e){
            if(!e.isTemporary()){
                if(precioProducto.getText().compareTo("")!=0)
                {
                    System.out.println("Desenfocado");
                    String Fprecio1 = precioProducto.getText().replace(".", "");
                    String Fprecio2 = Fprecio1.replace("$", "");

                    int precio = Integer.parseInt(Fprecio2);
                    Locale locale = new Locale("es","CO");
                    NumberFormat nf = DecimalFormat.getCurrencyInstance(locale);

                    String numeroFinal = nf.format(precio).substring(0,nf.format(precio).length()-3);
                    precioProducto.setText(numeroFinal);
                    //System.out.println("Desenfocado");
                }                
            }
        };
        });
    }
    public Image getIconImage(){
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("imagenes/Logo.png"));
        return retValue;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnSalir = new javax.swing.JButton();
        jLabelLogo = new javax.swing.JLabel();
        LPlancha1 = new javax.swing.JLabel();
        LPlancha2 = new javax.swing.JLabel();
        LPlancha3 = new javax.swing.JLabel();
        LPlancha4 = new javax.swing.JLabel();
        LPlancha5 = new javax.swing.JLabel();
        estadoP1 = new javax.swing.JLabel();
        estadoP2 = new javax.swing.JLabel();
        estadoP3 = new javax.swing.JLabel();
        estadoP4 = new javax.swing.JLabel();
        estadoP5 = new javax.swing.JLabel();
        realizarPedidos = new javax.swing.JButton();
        pPendientes = new javax.swing.JButton();
        domPendiente = new javax.swing.JButton();
        domAsignado = new javax.swing.JButton();
        pEntregados = new javax.swing.JButton();
        btnProductos = new javax.swing.JButton();
        btnInventario = new javax.swing.JButton();
        paneRealizarPedidos = new javax.swing.JPanel();
        btnGenerarPedido = new javax.swing.JButton();
        comboCatAgregar = new javax.swing.JComboBox();
        comboProAgregar = new javax.swing.JComboBox();
        cantidadAgregar = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        observacionesAgregar = new javax.swing.JTextArea();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();
        jCheckBox7 = new javax.swing.JCheckBox();
        jCheckBox8 = new javax.swing.JCheckBox();
        jCheckBox9 = new javax.swing.JCheckBox();
        btnAgregarMostrar = new javax.swing.JButton();
        jLabel31 = new javax.swing.JLabel();
        labelPara = new javax.swing.JLabel();
        mesa_nombre = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaProductos = new javax.swing.JTable();
        jLabel33 = new javax.swing.JLabel();
        totalPagar = new javax.swing.JLabel();
        btnEliminarMostrar = new javax.swing.JButton();
        comboCatPedido = new javax.swing.JComboBox();
        panelPedidosPendientes = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaPedidosPendientes = new javax.swing.JTable();
        btnVerPendiente = new javax.swing.JButton();
        btnFinalizarPendiente = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        panelDomiciliosPendientes = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaDomiciliosPendientes = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        panelDomiciliosAsignados = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        panelPedidosEntregados = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablaPedidosEntregados = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        panelProductos = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        labelAdministracionProductos = new javax.swing.JLabel();
        nombreProducto = new javax.swing.JTextField();
        descripcionProducto = new javax.swing.JTextField();
        precioProducto = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        comboCategoria = new javax.swing.JComboBox();
        jLabel22 = new javax.swing.JLabel();
        checkSalchipapa = new javax.swing.JCheckBox();
        checkPlancha = new javax.swing.JCheckBox();
        checkHorno = new javax.swing.JCheckBox();
        checkCocina = new javax.swing.JCheckBox();
        btnConsultarProd = new javax.swing.JButton();
        btnAgregarProd = new javax.swing.JButton();
        btnModificarProd = new javax.swing.JButton();
        btnEliminarProd = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        comboConsultarCat = new javax.swing.JComboBox();
        comboConsutarProd = new javax.swing.JComboBox();
        btnAgregarCat = new javax.swing.JButton();
        btnEliminarCat = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        idProd = new javax.swing.JTextField();
        nombreCat = new javax.swing.JTextField();
        panelPagos = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jButton7 = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        fondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnSalir.setBackground(new java.awt.Color(153, 153, 153));
        btnSalir.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSalir.setForeground(new java.awt.Color(0, 0, 0));
        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        getContentPane().add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 10, -1, -1));

        jLabelLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Logo1.png"))); // NOI18N
        getContentPane().add(jLabelLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, -10, 210, 200));

        LPlancha1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        LPlancha1.setForeground(new java.awt.Color(255, 255, 255));
        LPlancha1.setText("Salchipapa");
        getContentPane().add(LPlancha1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, -1, -1));

        LPlancha2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        LPlancha2.setForeground(new java.awt.Color(255, 255, 255));
        LPlancha2.setText("Plancha");
        getContentPane().add(LPlancha2, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 10, -1, -1));

        LPlancha3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        LPlancha3.setForeground(new java.awt.Color(255, 255, 255));
        LPlancha3.setText("Horno");
        getContentPane().add(LPlancha3, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 10, -1, -1));

        LPlancha4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        LPlancha4.setForeground(new java.awt.Color(255, 255, 255));
        LPlancha4.setText("Cocina");
        getContentPane().add(LPlancha4, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 10, -1, -1));

        LPlancha5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        LPlancha5.setForeground(new java.awt.Color(255, 255, 255));
        LPlancha5.setText("Domicilios");
        getContentPane().add(LPlancha5, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 10, -1, -1));

        estadoP1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        estadoP1.setText("OK");
        getContentPane().add(estadoP1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, -1, -1));

        estadoP2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        estadoP2.setText("OK");
        getContentPane().add(estadoP2, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 10, -1, -1));

        estadoP3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        estadoP3.setText("OK");
        getContentPane().add(estadoP3, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 10, -1, -1));

        estadoP4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        estadoP4.setText("OK");
        getContentPane().add(estadoP4, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 10, -1, -1));

        estadoP5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        estadoP5.setText("OK");
        getContentPane().add(estadoP5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 10, -1, -1));

        realizarPedidos.setBackground(new java.awt.Color(153, 153, 153));
        realizarPedidos.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        realizarPedidos.setForeground(new java.awt.Color(0, 0, 0));
        realizarPedidos.setText("Realizar Pedido");
        realizarPedidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                realizarPedidosActionPerformed(evt);
            }
        });
        getContentPane().add(realizarPedidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 210, -1));

        pPendientes.setBackground(new java.awt.Color(153, 153, 153));
        pPendientes.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        pPendientes.setForeground(new java.awt.Color(0, 0, 0));
        pPendientes.setText("Pedidos Pendientes");
        pPendientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pPendientesActionPerformed(evt);
            }
        });
        getContentPane().add(pPendientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 210, -1));

        domPendiente.setBackground(new java.awt.Color(153, 153, 153));
        domPendiente.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        domPendiente.setForeground(new java.awt.Color(0, 0, 0));
        domPendiente.setText("Domicilios Pendientes");
        domPendiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                domPendienteActionPerformed(evt);
            }
        });
        getContentPane().add(domPendiente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 210, -1));

        domAsignado.setBackground(new java.awt.Color(153, 153, 153));
        domAsignado.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        domAsignado.setForeground(new java.awt.Color(0, 0, 0));
        domAsignado.setText("Domicilios Asignados");
        domAsignado.setActionCommand("Domicilios Pendientes");
        domAsignado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                domAsignadoActionPerformed(evt);
            }
        });
        getContentPane().add(domAsignado, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 210, -1));

        pEntregados.setBackground(new java.awt.Color(153, 153, 153));
        pEntregados.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        pEntregados.setForeground(new java.awt.Color(0, 0, 0));
        pEntregados.setText("Pedidos Entregados");
        pEntregados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pEntregadosActionPerformed(evt);
            }
        });
        getContentPane().add(pEntregados, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 210, -1));

        btnProductos.setBackground(new java.awt.Color(153, 153, 153));
        btnProductos.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnProductos.setForeground(new java.awt.Color(0, 0, 0));
        btnProductos.setText("Productos");
        btnProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductosActionPerformed(evt);
            }
        });
        getContentPane().add(btnProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, 190, -1));

        btnInventario.setBackground(new java.awt.Color(153, 153, 153));
        btnInventario.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        btnInventario.setForeground(new java.awt.Color(0, 0, 0));
        btnInventario.setText("Pagos");
        btnInventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInventarioActionPerformed(evt);
            }
        });
        getContentPane().add(btnInventario, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 490, 190, -1));

        paneRealizarPedidos.setBorder(new javax.swing.border.SoftBevelBorder(0));

        btnGenerarPedido.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnGenerarPedido.setText("Realizar Pedido");
        btnGenerarPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarPedidoActionPerformed(evt);
            }
        });

        comboCatAgregar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        comboCatAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCatAgregarActionPerformed(evt);
            }
        });

        comboProAgregar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        cantidadAgregar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cantidadAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cantidadAgregarActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Cantidad:");

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Producto:");

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Categoria:");

        observacionesAgregar.setColumns(20);
        observacionesAgregar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        observacionesAgregar.setRows(5);
        jScrollPane1.setViewportView(observacionesAgregar);

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Observaciones:");

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("Salsas:");

        jCheckBox1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCheckBox1.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox1.setText("Salsa1");

        jCheckBox2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCheckBox2.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox2.setText("Salsa4");

        jCheckBox3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCheckBox3.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox3.setText("Salsa2");

        jCheckBox4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCheckBox4.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox4.setText("Salsa3");

        jCheckBox5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCheckBox5.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox5.setText("Salsa5");

        jCheckBox6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCheckBox6.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox6.setText("Salsa6");

        jCheckBox7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCheckBox7.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox7.setText("Salsa7");
        jCheckBox7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox7ActionPerformed(evt);
            }
        });

        jCheckBox8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCheckBox8.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox8.setText("Salsa8");

        jCheckBox9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCheckBox9.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox9.setText("Todas");

        btnAgregarMostrar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnAgregarMostrar.setText("Agregar");
        btnAgregarMostrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarMostrarActionPerformed(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("Para:");

        labelPara.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelPara.setForeground(new java.awt.Color(255, 255, 255));
        labelPara.setText("?");

        mesa_nombre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        mesa_nombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mesa_nombreActionPerformed(evt);
            }
        });

        tablaProductos.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        tablaProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre Producto", "Precio", "Cantidad", "Observaciones", "Salsas", "Precio Total"
            }
        ));
        jScrollPane2.setViewportView(tablaProductos);

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setText("Total:");

        totalPagar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        totalPagar.setForeground(new java.awt.Color(255, 255, 255));
        totalPagar.setText("$$$$$$$$$$$$");

        btnEliminarMostrar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnEliminarMostrar.setText("Eliminar");
        btnEliminarMostrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarMostrarActionPerformed(evt);
            }
        });

        comboCatPedido.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        comboCatPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCatPedidoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout paneRealizarPedidosLayout = new javax.swing.GroupLayout(paneRealizarPedidos);
        paneRealizarPedidos.setLayout(paneRealizarPedidosLayout);
        paneRealizarPedidosLayout.setHorizontalGroup(
            paneRealizarPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneRealizarPedidosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneRealizarPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paneRealizarPedidosLayout.createSequentialGroup()
                        .addGap(431, 431, 431)
                        .addComponent(btnGenerarPedido)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(paneRealizarPedidosLayout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paneRealizarPedidosLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel33)
                        .addGap(56, 56, 56)
                        .addComponent(totalPagar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(320, 320, 320))
                    .addGroup(paneRealizarPedidosLayout.createSequentialGroup()
                        .addGroup(paneRealizarPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(39, 39, 39)
                        .addGroup(paneRealizarPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(comboProAgregar, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cantidadAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboCatAgregar, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(comboCatPedido, 0, 170, Short.MAX_VALUE))
                        .addGap(43, 43, 43)
                        .addGroup(paneRealizarPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paneRealizarPedidosLayout.createSequentialGroup()
                                .addComponent(labelPara, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(mesa_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1)
                            .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(paneRealizarPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(paneRealizarPedidosLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(paneRealizarPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(paneRealizarPedidosLayout.createSequentialGroup()
                                        .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(133, 133, 133))
                                    .addGroup(paneRealizarPedidosLayout.createSequentialGroup()
                                        .addGroup(paneRealizarPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(paneRealizarPedidosLayout.createSequentialGroup()
                                                .addComponent(jCheckBox1)
                                                .addGap(18, 18, 18)
                                                .addComponent(jCheckBox3)
                                                .addGap(18, 18, 18)
                                                .addComponent(jCheckBox4))
                                            .addGroup(paneRealizarPedidosLayout.createSequentialGroup()
                                                .addComponent(jCheckBox2)
                                                .addGap(18, 18, 18)
                                                .addComponent(jCheckBox5)
                                                .addGap(18, 18, 18)
                                                .addComponent(jCheckBox6))
                                            .addGroup(paneRealizarPedidosLayout.createSequentialGroup()
                                                .addComponent(jCheckBox7)
                                                .addGap(18, 18, 18)
                                                .addComponent(jCheckBox8)
                                                .addGap(18, 18, 18)
                                                .addComponent(jCheckBox9)))
                                        .addContainerGap(109, Short.MAX_VALUE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paneRealizarPedidosLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnEliminarMostrar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAgregarMostrar)
                                .addContainerGap())))))
        );
        paneRealizarPedidosLayout.setVerticalGroup(
            paneRealizarPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneRealizarPedidosLayout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addGroup(paneRealizarPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paneRealizarPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(comboCatPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel31))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paneRealizarPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelPara)
                        .addComponent(mesa_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(paneRealizarPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paneRealizarPedidosLayout.createSequentialGroup()
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(paneRealizarPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAgregarMostrar)
                            .addComponent(btnEliminarMostrar))
                        .addGap(8, 8, 8))
                    .addGroup(paneRealizarPedidosLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(paneRealizarPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(paneRealizarPedidosLayout.createSequentialGroup()
                                .addGroup(paneRealizarPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel29)
                                    .addComponent(jLabel30)
                                    .addComponent(comboCatAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(paneRealizarPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(paneRealizarPedidosLayout.createSequentialGroup()
                                        .addGroup(paneRealizarPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jCheckBox1)
                                            .addComponent(jCheckBox3)
                                            .addComponent(jCheckBox4))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(paneRealizarPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jCheckBox2)
                                            .addComponent(jCheckBox5)
                                            .addComponent(jCheckBox6))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(paneRealizarPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jCheckBox8)
                                            .addComponent(jCheckBox9)
                                            .addComponent(jCheckBox7)))))
                            .addGroup(paneRealizarPedidosLayout.createSequentialGroup()
                                .addComponent(jLabel28)
                                .addGap(18, 18, 18)
                                .addGroup(paneRealizarPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel27)
                                    .addComponent(comboProAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(paneRealizarPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel26)
                                    .addComponent(cantidadAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(paneRealizarPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(totalPagar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGenerarPedido)
                .addContainerGap())
        );

        getContentPane().add(paneRealizarPedidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 190, 940, 420));

        panelPedidosPendientes.setBorder(new javax.swing.border.SoftBevelBorder(0));

        tablaPedidosPendientes.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tablaPedidosPendientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Nombre_Mesa", "Precio Total", "Hora  Inicio"
            }
        ));
        tablaPedidosPendientes.setRowHeight(20);
        jScrollPane3.setViewportView(tablaPedidosPendientes);

        btnVerPendiente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnVerPendiente.setText("Ver Pedido");

        btnFinalizarPendiente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnFinalizarPendiente.setText("Finalizar Pedido");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Pedidos Pendientes");

        javax.swing.GroupLayout panelPedidosPendientesLayout = new javax.swing.GroupLayout(panelPedidosPendientes);
        panelPedidosPendientes.setLayout(panelPedidosPendientesLayout);
        panelPedidosPendientesLayout.setHorizontalGroup(
            panelPedidosPendientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPedidosPendientesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPedidosPendientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(panelPedidosPendientesLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnVerPendiente)
                        .addGap(18, 18, 18)
                        .addComponent(btnFinalizarPendiente)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPedidosPendientesLayout.createSequentialGroup()
                .addContainerGap(367, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(334, 334, 334))
        );
        panelPedidosPendientesLayout.setVerticalGroup(
            panelPedidosPendientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPedidosPendientesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(panelPedidosPendientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVerPendiente)
                    .addComponent(btnFinalizarPendiente))
                .addGap(21, 21, 21))
        );

        getContentPane().add(panelPedidosPendientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 190, 940, 420));

        panelDomiciliosPendientes.setBorder(new javax.swing.border.SoftBevelBorder(0));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Domicilios Pendientes");

        tablaDomiciliosPendientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Nombre", "Direccion", "Telefono", "Precio Total", "Hora Inicio"
            }
        ));
        jScrollPane4.setViewportView(tablaDomiciliosPendientes);

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("Ver");

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton2.setText("Asignar");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Encargado:");

        jTextField1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout panelDomiciliosPendientesLayout = new javax.swing.GroupLayout(panelDomiciliosPendientes);
        panelDomiciliosPendientes.setLayout(panelDomiciliosPendientesLayout);
        panelDomiciliosPendientesLayout.setHorizontalGroup(
            panelDomiciliosPendientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDomiciliosPendientesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDomiciliosPendientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDomiciliosPendientesLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDomiciliosPendientesLayout.createSequentialGroup()
                .addContainerGap(348, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(323, 323, 323))
        );
        panelDomiciliosPendientesLayout.setVerticalGroup(
            panelDomiciliosPendientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDomiciliosPendientesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelDomiciliosPendientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jLabel4)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        getContentPane().add(panelDomiciliosPendientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 190, 940, 420));

        panelDomiciliosAsignados.setBorder(new javax.swing.border.SoftBevelBorder(0));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Domicilios Asignados");

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Nombre", "Direccion", "Telefono", "Precio Total", "Encargado"
            }
        ));
        jScrollPane5.setViewportView(jTable3);

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton3.setText("Finalizar");

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton4.setText("Ver");

        javax.swing.GroupLayout panelDomiciliosAsignadosLayout = new javax.swing.GroupLayout(panelDomiciliosAsignados);
        panelDomiciliosAsignados.setLayout(panelDomiciliosAsignadosLayout);
        panelDomiciliosAsignadosLayout.setHorizontalGroup(
            panelDomiciliosAsignadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDomiciliosAsignadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDomiciliosAsignadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelDomiciliosAsignadosLayout.createSequentialGroup()
                        .addComponent(jScrollPane5)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDomiciliosAsignadosLayout.createSequentialGroup()
                        .addGap(0, 340, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addGap(336, 336, 336))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDomiciliosAsignadosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addContainerGap())
        );
        panelDomiciliosAsignadosLayout.setVerticalGroup(
            panelDomiciliosAsignadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDomiciliosAsignadosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 76, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDomiciliosAsignadosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelDomiciliosAsignadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap())
        );

        getContentPane().add(panelDomiciliosAsignados, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 190, 940, 420));

        panelPedidosEntregados.setBorder(new javax.swing.border.SoftBevelBorder(0));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Pedidos Entregados");

        tablaPedidosEntregados.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tablaPedidosEntregados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Tipo", "Nombre_Mesa", "Precio"
            }
        ));
        tablaPedidosEntregados.setRowHeight(20);
        jScrollPane6.setViewportView(tablaPedidosEntregados);

        jButton5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton5.setText("Ver");

        javax.swing.GroupLayout panelPedidosEntregadosLayout = new javax.swing.GroupLayout(panelPedidosEntregados);
        panelPedidosEntregados.setLayout(panelPedidosEntregadosLayout);
        panelPedidosEntregadosLayout.setHorizontalGroup(
            panelPedidosEntregadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPedidosEntregadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPedidosEntregadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPedidosEntregadosLayout.createSequentialGroup()
                        .addComponent(jScrollPane6)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPedidosEntregadosLayout.createSequentialGroup()
                        .addGap(0, 346, Short.MAX_VALUE)
                        .addGroup(panelPedidosEntregadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPedidosEntregadosLayout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(346, 346, 346))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPedidosEntregadosLayout.createSequentialGroup()
                                .addComponent(jButton5)
                                .addContainerGap())))))
        );
        panelPedidosEntregadosLayout.setVerticalGroup(
            panelPedidosEntregadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPedidosEntregadosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(panelPedidosEntregados, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 190, 940, 420));

        panelProductos.setBorder(new javax.swing.border.SoftBevelBorder(1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Descripcion Producto");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Nombre Producto");

        labelAdministracionProductos.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelAdministracionProductos.setForeground(new java.awt.Color(255, 255, 255));
        labelAdministracionProductos.setText("Administración De Productos");

        nombreProducto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        descripcionProducto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        precioProducto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Precio");

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Categoria");

        comboCategoria.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        comboCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCategoriaActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Estacion Encargada");

        checkSalchipapa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        checkSalchipapa.setForeground(new java.awt.Color(255, 255, 255));
        checkSalchipapa.setText("Salchipapa");
        checkSalchipapa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkSalchipapaActionPerformed(evt);
            }
        });

        checkPlancha.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        checkPlancha.setForeground(new java.awt.Color(255, 255, 255));
        checkPlancha.setText("Plancha");

        checkHorno.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        checkHorno.setForeground(new java.awt.Color(255, 255, 255));
        checkHorno.setText("Horno");

        checkCocina.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        checkCocina.setForeground(new java.awt.Color(255, 255, 255));
        checkCocina.setText("Cocina");
        checkCocina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkCocinaActionPerformed(evt);
            }
        });

        btnConsultarProd.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnConsultarProd.setText("Consultar");
        btnConsultarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarProdActionPerformed(evt);
            }
        });

        btnAgregarProd.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnAgregarProd.setText("Agregar");
        btnAgregarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarProdActionPerformed(evt);
            }
        });

        btnModificarProd.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnModificarProd.setText("Modificar");
        btnModificarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarProdActionPerformed(evt);
            }
        });

        btnEliminarProd.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnEliminarProd.setText("Eliminar");
        btnEliminarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProdActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Producto a Consultar");

        comboConsultarCat.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        comboConsultarCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboConsultarCatActionPerformed(evt);
            }
        });

        comboConsutarProd.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        btnAgregarCat.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnAgregarCat.setText("Agregar");
        btnAgregarCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarCatActionPerformed(evt);
            }
        });

        btnEliminarCat.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnEliminarCat.setText("Eliminar");
        btnEliminarCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarCatActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Categorias");

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("NOMBRE:");

        nombreCat.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N

        javax.swing.GroupLayout panelProductosLayout = new javax.swing.GroupLayout(panelProductos);
        panelProductos.setLayout(panelProductosLayout);
        panelProductosLayout.setHorizontalGroup(
            panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProductosLayout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelProductosLayout.createSequentialGroup()
                        .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(comboConsutarProd, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(26, 26, 26)
                        .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelProductosLayout.createSequentialGroup()
                                .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(comboCategoria, 0, 489, Short.MAX_VALUE)
                                    .addComponent(descripcionProducto)
                                    .addComponent(nombreProducto, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(precioProducto, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelProductosLayout.createSequentialGroup()
                                .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelProductosLayout.createSequentialGroup()
                                        .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(panelProductosLayout.createSequentialGroup()
                                                .addComponent(checkSalchipapa, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(checkPlancha, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(panelProductosLayout.createSequentialGroup()
                                                .addComponent(btnConsultarProd, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(btnAgregarProd, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(18, 18, 18)
                                        .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(btnModificarProd, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                                            .addComponent(checkHorno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(18, 18, 18)
                                        .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(checkCocina, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btnEliminarProd, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))
                                        .addGap(53, 53, 53))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelProductosLayout.createSequentialGroup()
                                        .addComponent(jLabel25)
                                        .addGap(18, 18, 18)))
                                .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(idProd, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(nombreCat, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(btnEliminarCat, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnAgregarCat, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)))
                                .addContainerGap(35, Short.MAX_VALUE))))
                    .addGroup(panelProductosLayout.createSequentialGroup()
                        .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(comboConsultarCat, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
            .addGroup(panelProductosLayout.createSequentialGroup()
                .addGap(281, 281, 281)
                .addComponent(labelAdministracionProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelProductosLayout.setVerticalGroup(
            panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProductosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelAdministracionProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(nombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(descripcionProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(precioProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(comboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelProductosLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(checkSalchipapa)
                            .addComponent(checkHorno)
                            .addComponent(checkCocina)
                            .addComponent(checkPlancha))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(comboConsultarCat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelProductosLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                        .addComponent(idProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addGap(12, 12, 12)
                        .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nombreCat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25))
                        .addGap(18, 18, 18)
                        .addComponent(btnAgregarCat)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConsultarProd)
                    .addComponent(btnModificarProd)
                    .addComponent(btnEliminarProd)
                    .addComponent(btnAgregarProd)
                    .addComponent(comboConsutarProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminarCat))
                .addGap(24, 24, 24))
        );

        getContentPane().add(panelProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 190, 940, 420));

        panelPagos.setBackground(new java.awt.Color(102, 102, 102));
        panelPagos.setBorder(new javax.swing.border.SoftBevelBorder(0));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Registro de Pagos");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Nombre:");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Identificacion:");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Observaciones:");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Valor:");

        jButton6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton6.setText("Registrar");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Desde:");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Hasta:");

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nombre", "Identificacion", "Descripcion", "Valor"
            }
        ));
        jScrollPane7.setViewportView(jTable5);

        jButton7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton7.setText("Consultar");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane8.setViewportView(jTextArea1);

        javax.swing.GroupLayout panelPagosLayout = new javax.swing.GroupLayout(panelPagos);
        panelPagos.setLayout(panelPagosLayout);
        panelPagosLayout.setHorizontalGroup(
            panelPagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPagosLayout.createSequentialGroup()
                .addGroup(panelPagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPagosLayout.createSequentialGroup()
                        .addGap(344, 344, 344)
                        .addComponent(jLabel12))
                    .addGroup(panelPagosLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelPagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 898, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPagosLayout.createSequentialGroup()
                                .addGroup(panelPagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel18))
                                .addGap(18, 18, 18)
                                .addGroup(panelPagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panelPagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelPagosLayout.createSequentialGroup()
                                        .addComponent(jLabel14)
                                        .addGap(18, 18, 18)
                                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelPagosLayout.createSequentialGroup()
                                        .addComponent(jLabel19)
                                        .addGap(18, 18, 18)
                                        .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(panelPagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelPagosLayout.createSequentialGroup()
                                        .addComponent(jLabel16)
                                        .addGap(18, 18, 18)
                                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel15))
                                    .addGroup(panelPagosLayout.createSequentialGroup()
                                        .addComponent(jButton7)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton6)))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        panelPagosLayout.setVerticalGroup(
            panelPagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPagosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addGroup(panelPagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPagosLayout.createSequentialGroup()
                        .addGroup(panelPagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelPagosLayout.createSequentialGroup()
                                .addGap(0, 55, Short.MAX_VALUE)
                                .addGroup(panelPagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jDateChooser2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton7)
                                        .addComponent(jButton6))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel18)
                                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel19))))
                            .addGroup(panelPagosLayout.createSequentialGroup()
                                .addGroup(panelPagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14)
                                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel16)
                                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel15))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32))
                    .addGroup(panelPagosLayout.createSequentialGroup()
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        getContentPane().add(panelPagos, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 190, 940, 420));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Administrar");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, -1, -1));

        fondo.setBackground(new java.awt.Color(255, 255, 255));
        fondo.setForeground(new java.awt.Color(0, 153, 255));
        fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondoscuro.jpg"))); // NOI18N
        getContentPane().add(fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(-39, -44, 1240, 710));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ocultarPaneles()
    {
        paneRealizarPedidos.setVisible(false);
        panelPedidosPendientes.setVisible(false);
        panelDomiciliosPendientes.setVisible(false);
        panelDomiciliosAsignados.setVisible(false);
        panelPedidosEntregados.setVisible(false);
        panelProductos.setVisible(false);
        panelPagos.setVisible(false);
                
    }
    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnSalirActionPerformed

    private void realizarPedidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_realizarPedidosActionPerformed
        ocultarPaneles();
        paneRealizarPedidos.setVisible(true);
        cantidadAgregar.setText("1");
    }//GEN-LAST:event_realizarPedidosActionPerformed

    private void pPendientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pPendientesActionPerformed
        ocultarPaneles();
        llenar_tabla_pendientes();
        panelPedidosPendientes.setVisible(true);        
    }//GEN-LAST:event_pPendientesActionPerformed

    private void domAsignadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_domAsignadoActionPerformed
        ocultarPaneles();
        panelDomiciliosAsignados.setVisible(true);        
    }//GEN-LAST:event_domAsignadoActionPerformed

    private void btnInventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInventarioActionPerformed
        ocultarPaneles();
        panelPagos.setVisible(true);        
    }//GEN-LAST:event_btnInventarioActionPerformed

    private void btnGenerarPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarPedidoActionPerformed

        Pedidos pedido;
        pedido = new Pedidos();
        
        int seleccion = comboCatPedido.getSelectedIndex();
        if (seleccion == 1)
        {
            int mesa = Integer.parseInt(mesa_nombre.getText());    
            pedido.setMesa(mesa);
            pedido.setNombreCliente("");
        }
        else if(seleccion == 2)
        {
            String nombre_cliente = mesa_nombre.getText();         
            pedido.setNombreCliente(nombre_cliente);
            pedido.setMesa(0);
        }
        int valor_total = Integer.parseInt(totalPagar.getText().replace(".","").replace("$", ""));
        pedido.setPrecioTotal(valor_total);
        
        pedido.setCategoriaPedidos(operaciones.consultarCategoriaXNombrePedido(comboCatPedido.getSelectedItem().toString()));
        pedido.setEstado(0);
        
        operaciones.insertarPedido(pedido);
        int id_pedido = operaciones.consultarIdUltimoPedidoRealizado();
        
        for(int i=0; i<productosTransmision.size() ; i++)
        {
            productosTransmision.get(i).setId_pedido(id_pedido);
            
            int idProducto = productosTransmision.get(i).getId_producto();
            int idPedido = productosTransmision.get(i).getId_pedido();
            int cantidad = productosTransmision.get(i).getCantidad();
            int precio = productosTransmision.get(i).getPrecio();
            String observaciones = productosTransmision.get(i).getObservacion();
            int estacionesTotales = operaciones.consultarEstacionesXProducto(operaciones.consultarProducto(idProducto)).size();
            
            operaciones.insertarProductosPedido(idProducto, idPedido, cantidad, precio*cantidad, observaciones, 0, estacionesTotales);
        }
        administrador.enviarPedidoRealizado(productosTransmision);
        
        comboCatPedido.setSelectedIndex(0);
        labelPara.setText("¿?");
        mesa_nombre.setText("");
        Limpiar_tabla_productos();
        productosTransmision = new ArrayList<>();
        
    }//GEN-LAST:event_btnGenerarPedidoActionPerformed

    private void domPendienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_domPendienteActionPerformed
        ocultarPaneles();
        //llenar_combo_categorias();
        panelDomiciliosPendientes.setVisible(true);
    }//GEN-LAST:event_domPendienteActionPerformed

    private void pEntregadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pEntregadosActionPerformed
        ocultarPaneles();
        llenar_tabla_entregados();
        panelPedidosEntregados.setVisible(true);
    }//GEN-LAST:event_pEntregadosActionPerformed

    private void btnProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductosActionPerformed
        ocultarPaneles();
        //llenar_combo_categorias();
        panelProductos.setVisible(true);
        idProd.setVisible(false);
    }//GEN-LAST:event_btnProductosActionPerformed

    private void checkCocinaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkCocinaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkCocinaActionPerformed

    private void btnAgregarCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarCatActionPerformed
        CategoriaProductos categoria;
        String nombre = nombreCat.getText();
        
        operaciones.insertarCategoria(nombre);
        idProd.setText("");
        nombreCat.setText("");
        
        ocultarPaneles();
        llenar_combo_categorias();
        panelProductos.setVisible(true);
        
    }//GEN-LAST:event_btnAgregarCatActionPerformed

    private void btnEliminarCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarCatActionPerformed
        
        CategoriaProductos  cat= operaciones.consultarCategoriaXNombre(nombreCat.getText());
        operaciones.eliminarCategoriaProductos(cat);
        llenar_combo_categorias();
        
        ocultarPaneles();
        llenar_combo_categorias();
        panelProductos.setVisible(true);
        
    }//GEN-LAST:event_btnEliminarCatActionPerformed

    private void comboConsultarCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboConsultarCatActionPerformed
        comboConsultarCat.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String seleccion = "";
                try{
                    seleccion = comboConsultarCat.getSelectedItem().toString();
                    if(seleccion.compareTo("Seleccione Categoria")!=0)
                {
                    int idCat = operaciones.consultarCategoriaXNombre(seleccion).getId();                    
                    comboConsutarProd.removeAllItems();
                    List<Productos> lista = operaciones.consultarProductosXCategorias(idCat);                    
                    Iterator<Productos> iter = lista.iterator();
                    comboConsutarProd.addItem("Seleccione Producto");
                    while(iter.hasNext())
                    {
                        Productos Pro = iter.next();
                        comboConsutarProd.addItem(Pro.getNombre());                        
                        System.out.println("Nombre: "+ Pro.getNombre());
                    }                     
                }
                }catch(Exception err){System.out.println("Se genero Error");}
                
                                
            }
        });
    }//GEN-LAST:event_comboConsultarCatActionPerformed

    private void comboCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCategoriaActionPerformed
        comboCategoria.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               
                               
            }
        });
    }//GEN-LAST:event_comboCategoriaActionPerformed

    private void checkSalchipapaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkSalchipapaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkSalchipapaActionPerformed

    private void btnAgregarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarProdActionPerformed
        String nombre = nombreProducto.getText();
        String descripcion = descripcionProducto.getText();
        int precio = Integer.parseInt(precioProducto.getText().replace("$", "").replace(".", ""));
        CategoriaProductos cat = operaciones.consultarCategoriaXNombre(comboCategoria.getSelectedItem().toString());
        
        Productos producto = new Productos();
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecio(precio);
        producto.setCategoriaProductos(cat);
        
        operaciones.insertarProducto(producto);
        
        Productos prod = operaciones.consultarProductoXNombre(nombre);
        if(checkSalchipapa.isSelected())
        {operaciones.insertarProductoEstacion(operaciones.consultarEstacionXId(1),prod);}
        if(checkPlancha.isSelected())
        {operaciones.insertarProductoEstacion(operaciones.consultarEstacionXId(2),prod);}
        if(checkHorno.isSelected())
        {operaciones.insertarProductoEstacion(operaciones.consultarEstacionXId(3),prod);}
        if(checkCocina.isSelected())
        {operaciones.insertarProductoEstacion(operaciones.consultarEstacionXId(4),prod);}
        
        limpiarCamposAdminProductos();
        
        
        
    }//GEN-LAST:event_btnAgregarProdActionPerformed
    public void limpiarCamposAdminProductos()
    {
        nombreProducto.setText("");
        descripcionProducto.setText("");
        precioProducto.setText("");
        checkSalchipapa.setSelected(false);
        checkPlancha.setSelected(false);
        checkHorno.setSelected(false);
        checkCocina.setSelected(false);
        comboCategoria.setSelectedIndex(0);
        comboConsultarCat.setSelectedIndex(0);
        comboConsutarProd.removeAllItems();
    }
    private void btnConsultarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarProdActionPerformed
        checkSalchipapa.setSelected(false);
        checkPlancha.setSelected(false);
        checkHorno.setSelected(false);
        checkCocina.setSelected(false);
        String nombreProd = comboConsutarProd.getSelectedItem().toString();
        Productos prod = operaciones.consultarProductoXNombre(nombreProd);
        
        nombreProducto.setText(prod.getNombre());
        descripcionProducto.setText(prod.getDescripcion());
        
        Locale locale = new Locale("es","CO");
        NumberFormat nf = DecimalFormat.getCurrencyInstance(locale);
        int precio = prod.getPrecio();
        String numeroFinal = nf.format(precio).substring(0,nf.format(precio).length()-3);
        
        precioProducto.setText(numeroFinal);
        comboCategoria.setSelectedIndex(prod.getId_categoria_productos());
        idProd.setText(String.valueOf(prod.getId()));
        //idProd.setVisible(false);
        List<ProductosEstacionesId>  lista = operaciones.consultarEstacionesXProducto(prod);       
        Iterator<ProductosEstacionesId> iter = lista.iterator();
        while(iter.hasNext())
        {
            ProductosEstacionesId pro_est = iter.next();
            if(pro_est.getId_Estacion()==1){checkSalchipapa.setSelected(true);}
            if(pro_est.getId_Estacion()==2){checkPlancha.setSelected(true);}
            if(pro_est.getId_Estacion()==3){checkHorno.setSelected(true);}
            if(pro_est.getId_Estacion()==4){checkCocina.setSelected(true);}
        } 
    }//GEN-LAST:event_btnConsultarProdActionPerformed

    private void btnAgregarMostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarMostrarActionPerformed
        
        
        String categoria = comboCatAgregar.getSelectedItem().toString();
        String producto = comboProAgregar.getSelectedItem().toString();
        int cantidad = Integer.parseInt(cantidadAgregar.getText());
        
        String observaciones = observacionesAgregar.getText();
        
        ProductoTransmision productoTransmision = new ProductoTransmision();
        productoTransmision.setId_producto(operaciones.consultarProductoXNombre(producto).getId());
        productoTransmision.setCantidad(cantidad);
        productoTransmision.setObservacion(observaciones);
        productoTransmision.setNombre_producto(producto);
        productoTransmision.setPrecio(operaciones.consultarProductoXNombre(producto).getPrecio());
        productoTransmision.setEntregarA(mesa_nombre.getText());
        
        //Falta meter id de pedido
        List<ProductosEstacionesId> listaEstaciones = operaciones.consultarEstacionesXProducto(operaciones.consultarProductoXNombre(producto));
        for(int i=0; i<listaEstaciones.size();i++)
        {
            productoTransmision.insertarDestino(listaEstaciones.get(i).getId_Estacion());
            System.out.println("Se inserto la estacion: "+ listaEstaciones.get(i).getId_Estacion());
        }
        productosTransmision.add(productoTransmision);
        llenar_tabla_productos();
        limpiarCamposRealizarPedido();
    }//GEN-LAST:event_btnAgregarMostrarActionPerformed
    public void limpiarCamposRealizarPedido()
    {
        //mesa_nombre.setText("");
        observacionesAgregar.setText("");
        cantidadAgregar.setText("");
        //labelPara.setText("¿?");
        //comboCatPedido.setSelectedIndex(0);
        comboCatAgregar.setSelectedIndex(0);
        comboProAgregar.removeAllItems();
        cantidadAgregar.setText("1");
    }
    private void cantidadAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cantidadAgregarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cantidadAgregarActionPerformed

    private void jCheckBox7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox7ActionPerformed

    private void comboCatAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCatAgregarActionPerformed
        comboCatAgregar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String seleccion = "";
                try{
                    seleccion = comboCatAgregar.getSelectedItem().toString();
                    if(seleccion.compareTo("Seleccione Categoria")!=0)
                {
                    int idCat = operaciones.consultarCategoriaXNombre(seleccion).getId();                    
                    comboProAgregar.removeAllItems();
                    List<Productos> lista = operaciones.consultarProductosXCategorias(idCat);                    
                    Iterator<Productos> iter = lista.iterator();
                    comboProAgregar.addItem("Seleccione Producto");
                    while(iter.hasNext())
                    {
                        Productos Pro = iter.next();
                        comboProAgregar.addItem(Pro.getNombre());                        
                        System.out.println("Nombre: "+ Pro.getNombre());
                    }                     
                }
                }catch(Exception err){System.out.println("Se genero Error");}
                
                                
            }
        });
    }//GEN-LAST:event_comboCatAgregarActionPerformed

    private void btnEliminarMostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarMostrarActionPerformed
        int eliminar = tablaProductos.getSelectedRow();
        productosTransmision.remove(eliminar);
        llenar_tabla_productos();
    }//GEN-LAST:event_btnEliminarMostrarActionPerformed

    private void comboCatPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCatPedidoActionPerformed
        comboCatPedido.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String seleccion = "";
                try{
                    int selecc = comboCatPedido.getSelectedIndex();
                    if(selecc != 0)
                {
                   if (selecc == 1)
                   {labelPara.setText("Mesa:");}
                   if (selecc == 2)
                   {labelPara.setText("Nombre:");}
                }
                }catch(Exception err){System.out.println("Se genero Error");}
                
                                
            }
        });
    }//GEN-LAST:event_comboCatPedidoActionPerformed

    private void mesa_nombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mesa_nombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mesa_nombreActionPerformed

    private void btnModificarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarProdActionPerformed
        String nomProd = nombreProducto.getText();
        String descProd = descripcionProducto.getText();
        int precio = Integer.parseInt(precioProducto.getText().replace(".", "").replace("$", ""));
        CategoriaProductos cat = operaciones.consultarCategoriaXNombre(comboCategoria.getSelectedItem().toString());
        
        Productos producto = operaciones.consultarProducto(Integer.parseInt(idProd.getText()));
        producto.setNombre(nomProd);
        producto.setDescripcion(descProd);
        producto.setPrecio(precio);
        producto.setCategoriaProductos(cat);
        
        operaciones.eliminarProductoEstacion(producto);
        
        if(checkSalchipapa.isSelected())
        {operaciones.insertarProductoEstacion(operaciones.consultarEstacionXId(1),producto);}
        if(checkPlancha.isSelected())
        {operaciones.insertarProductoEstacion(operaciones.consultarEstacionXId(2),producto);}
        if(checkHorno.isSelected())
        {operaciones.insertarProductoEstacion(operaciones.consultarEstacionXId(3),producto);}
        if(checkCocina.isSelected())
        {operaciones.insertarProductoEstacion(operaciones.consultarEstacionXId(4),producto);}
        
        operaciones.modificarProducto(producto);
        limpiarCamposAdminProductos();
    }//GEN-LAST:event_btnModificarProdActionPerformed

    private void btnEliminarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProdActionPerformed
        String nomProd = nombreProducto.getText();
        Productos producto = operaciones.consultarProductoXNombre(nomProd);
        operaciones.eliminarProductoEstacion(producto);
        operaciones.eliminarProducto(producto);
        limpiarCamposAdminProductos();
    }//GEN-LAST:event_btnEliminarProdActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

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
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Inicio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LPlancha1;
    private javax.swing.JLabel LPlancha2;
    private javax.swing.JLabel LPlancha3;
    private javax.swing.JLabel LPlancha4;
    private javax.swing.JLabel LPlancha5;
    private javax.swing.JButton btnAgregarCat;
    private javax.swing.JButton btnAgregarMostrar;
    private javax.swing.JButton btnAgregarProd;
    private javax.swing.JButton btnConsultarProd;
    private javax.swing.JButton btnEliminarCat;
    private javax.swing.JButton btnEliminarMostrar;
    private javax.swing.JButton btnEliminarProd;
    private javax.swing.JButton btnFinalizarPendiente;
    private javax.swing.JButton btnGenerarPedido;
    private javax.swing.JButton btnInventario;
    private javax.swing.JButton btnModificarProd;
    private javax.swing.JButton btnProductos;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnVerPendiente;
    private javax.swing.JTextField cantidadAgregar;
    private javax.swing.JCheckBox checkCocina;
    private javax.swing.JCheckBox checkHorno;
    private javax.swing.JCheckBox checkPlancha;
    private javax.swing.JCheckBox checkSalchipapa;
    private javax.swing.JComboBox comboCatAgregar;
    private javax.swing.JComboBox comboCatPedido;
    private javax.swing.JComboBox comboCategoria;
    private javax.swing.JComboBox comboConsultarCat;
    private javax.swing.JComboBox comboConsutarProd;
    private javax.swing.JComboBox comboProAgregar;
    private javax.swing.JTextField descripcionProducto;
    private javax.swing.JButton domAsignado;
    private javax.swing.JButton domPendiente;
    private javax.swing.JLabel estadoP1;
    private javax.swing.JLabel estadoP2;
    private javax.swing.JLabel estadoP3;
    private javax.swing.JLabel estadoP4;
    private javax.swing.JLabel estadoP5;
    private javax.swing.JLabel fondo;
    private javax.swing.JTextField idProd;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JCheckBox jCheckBox7;
    private javax.swing.JCheckBox jCheckBox8;
    private javax.swing.JCheckBox jCheckBox9;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelLogo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable5;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JLabel labelAdministracionProductos;
    private javax.swing.JLabel labelPara;
    private javax.swing.JTextField mesa_nombre;
    private javax.swing.JTextField nombreCat;
    private javax.swing.JTextField nombreProducto;
    private javax.swing.JTextArea observacionesAgregar;
    private javax.swing.JButton pEntregados;
    private javax.swing.JButton pPendientes;
    private javax.swing.JPanel paneRealizarPedidos;
    private javax.swing.JPanel panelDomiciliosAsignados;
    private javax.swing.JPanel panelDomiciliosPendientes;
    private javax.swing.JPanel panelPagos;
    private javax.swing.JPanel panelPedidosEntregados;
    private javax.swing.JPanel panelPedidosPendientes;
    private javax.swing.JPanel panelProductos;
    private javax.swing.JTextField precioProducto;
    private javax.swing.JButton realizarPedidos;
    private javax.swing.JTable tablaDomiciliosPendientes;
    private javax.swing.JTable tablaPedidosEntregados;
    private javax.swing.JTable tablaPedidosPendientes;
    private javax.swing.JTable tablaProductos;
    private javax.swing.JLabel totalPagar;
    // End of variables declaration//GEN-END:variables
}
