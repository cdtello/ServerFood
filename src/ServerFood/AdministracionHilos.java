/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerFood;


import Controlador.Operaciones;
import Modelo.Pedidos;
import Modelo.Productos;
import Modelo.ProductosEstacionesId;
import ServerFood.Estacion;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author C.TELLO
 */
public class AdministracionHilos extends Thread{
    private Socket socket = null;
    private AdminAplicacion server = null;
    private boolean ejecutar = true;
    private int estadoConexion = 0;
    private int repetido = 0;
    Estacion estacion;
    
    ObjectOutputStream objectOutputStream; // flujo de salida hacia el servidor
    ObjectInputStream objectInputStream; // flujo de entrada del servidor
    Boolean bandera=true;
    FileWriter fw = null;
    PrintWriter pw = null;

    public int getRepetido() {
        return repetido;
    }

    public void setRepetido(int repetido) {
        this.repetido = repetido;
    }

    
    public AdministracionHilos(AdminAplicacion administrador, Socket socketcito) {
       server = administrador;  
       socket = socketcito;
       estacion = new Estacion("",0);
    }
    public void enviarProducto(ProductoTransmision producto)
    {
        try {
            objectOutputStream.writeObject(producto);
            System.out.println("Se envio Producto");
            
        } catch (IOException ex) {
            System.out.println("Error al enviar Producto");
        }
    }

    
    public void run() 
    {     
        try {
           while(ejecutar)
           {
                try {
                    if(estadoConexion == 0 )
                    {
                        Estacion estacion =(Estacion) objectInputStream.readObject();
                        if(estacion.getEstado() == 0)
                        {                       
                            this.estacion.setId(estacion.getId());
                            this.estacion.setNombre(estacion.getNombre());
                            
                            System.out.println("************INFO ESTACION CONECTADA*************");
                            System.out.println(this.estacion.getIdSocket());
                            System.out.println(this.estacion.getNombre());
                            System.out.println(this.estacion.getId());
                            
                            //***Envio Confirmacion de Estacion Conectada****
                            setRepetido(server.buscarHiloRepetido(this));
                            if(repetido == 1)
                            {
                                estadoConexion = 1;
                                estacion.setEstado(1);
                                objectOutputStream.writeObject(estacion);
                                server.conectarHilo(estacion.getId());
                                //**********************************************
                                //**********************************************
                                //Envio de todos los productos para la Estacion Domicilios
                                if(this.estacion.getId() == 5)
                                {
                                    ArrayList<ProductosDomicilio> inicializarDomicilios = new ArrayList();
                                    
                                    Operaciones op = new Operaciones();
                                    List<Productos> lista = op.consultarTodosProductos();
                                    Iterator<Productos> iter = lista.iterator();
                                    
                                    while(iter.hasNext())
                                    {
                                        Productos pro = iter.next();
                                        ProductosDomicilio pdom = new ProductosDomicilio();
                                        pdom.setId(pro.getId());
                                        pdom.setIdCategoria(pro.getId_categoria_productos());
                                        pdom.setNombre(pro.getNombre());
                                        pdom.setPrecio(pro.getPrecio());
                                        
                                        inicializarDomicilios.add(pdom);
                                    } 
                                    objectOutputStream.writeObject(inicializarDomicilios);
                                    System.out.println("Se enviaron todos los productos a la Estacion");
                                }
                            }
                            else if(repetido > 1)
                            {
                                estadoConexion = 0;
                                estacion.setEstado(0);
                                objectOutputStream.writeObject(estacion);
                                server.EliminarHilo(this.estacion.getIdSocket());
                                this.ejecutar = false;
                            }  
                        }
                        else
                        {
                            System.out.println("Conexion no permitida, Cliente ya Conectado a otra Estacion");
                            server.desconectarHilo(estacion.getIdSocket());
                            server.EliminarHilo(this.estacion.getIdSocket()); 
                        }                        
                    }
                    else if(estadoConexion == 1)
                    {
                        if(this.estacion.getId()==5)
                        {
                            System.out.println("Esperando Pedido Domicilio.....");
                            ArrayList<ProductoTransmision> productosDomicilio=(ArrayList<ProductoTransmision>) objectInputStream.readObject();
                            System.out.println("Se recibio el Domicilio");
                            
                            Operaciones op = new Operaciones();
                            Pedidos pedido;
                            pedido = new Pedidos();
                            pedido.setNombreCliente(productosDomicilio.get(0).getNombreCliente());
                            pedido.setTelefonoCliente(productosDomicilio.get(0).getTelefonoCliente());
                            pedido.setDireccion(productosDomicilio.get(0).getDireccionCliente());
                            pedido.setEstado(1);
                            int precioTotal = 0;
                            for(int i=0; i<productosDomicilio.size();i++)
                            {
                                precioTotal = precioTotal + productosDomicilio.get(i).getPrecio() * productosDomicilio.get(i).getCantidad();
                            }
                            pedido.setPrecioTotal(precioTotal);
                            pedido.setCategoriaPedidos(op.consultarCategoriaXNombrePedido("DOMICILIO"));
                            pedido.setMesa(0);
                            
                            op.insertarPedido(pedido);
                            
                            int id_pedido = op.consultarIdUltimoPedidoRealizado();
        
                            for(int i=0; i<productosDomicilio.size() ; i++)
                            {
                                productosDomicilio.get(i).setId_pedido(id_pedido);
                                System.out.println(productosDomicilio.get(i).getNombre_producto());
                                System.out.println(productosDomicilio.get(i).getId_producto());
                                int idProducto = productosDomicilio.get(i).getId_producto();
                                int idPedido = productosDomicilio.get(i).getId_pedido();
                                int cantidad = productosDomicilio.get(i).getCantidad();
                                int precio = productosDomicilio.get(i).getPrecio();
                                String observaciones = productosDomicilio.get(i).getObservacion();
                                int estacionesTotales = op.consultarEstacionesXProducto(op.consultarProducto(idProducto)).size();

                                op.insertarProductosPedido(idProducto, idPedido, cantidad, precio*cantidad, observaciones, 0, estacionesTotales);
                                List<ProductosEstacionesId> listaEstaciones = op.consultarEstacionesXProducto(op.consultarProducto(idProducto));
                                
                                for(int j=0; j<listaEstaciones.size();j++)
                                {
                                    productosDomicilio.get(i).insertarDestino(listaEstaciones.get(j).getId_Estacion());
                                    System.out.println("Se inserto la estacion: "+ listaEstaciones.get(j).getId_Estacion());
                                }
                            }
                            server.enviarPedidoRealizado(productosDomicilio);
                            JOptionPane.showMessageDialog(null, "se envio domicilio a la estacion correctamente");
                            server.ventanaPricipal.llenar_tabla_domicilios_pendientes();
                            
                        }else{
                            System.out.println("Esperando Respuesta Cliente.....");
                            ProductoTransmision producto= (ProductoTransmision) objectInputStream.readObject();
                            int id_producto = producto.getId_producto();
                            int id_pedido = producto.getId_pedido();
                            server.ventanaPricipal.operaciones.actualizarProductosPedido(id_producto, id_pedido);
                            server.ventanaPricipal.llenar_tabla_pendientes();
                            server.ventanaPricipal.llenar_tabla_entregados();
                        }
                        
                    }                 

                } catch (ClassNotFoundException ex) {
                    System.out.println("Error"+ex); 
                }
           }
        } catch (IOException ex) {
            System.out.println("\n Se desconecto la Estacion: "+estacion.getId());
            System.out.println("\n con puerto: "+estacion.getIdSocket());
            server.desconectarHilo(estacion.getIdSocket());
            server.EliminarHilo(estacion.getIdSocket());
            
            try {
                Thread.sleep(600);
            } catch (InterruptedException ex1) {
                Logger.getLogger(AdministracionHilos.class.getName()).log(Level.SEVERE, null, ex1);
            }
            this.ejecutar = false;
            
        }
    }
    //*****************************************************************
    //*****************************************************************
    // Establecer flujos de entrada y salida
    public void open() throws IOException {
        // Establece el flujo de salida para los objetos
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream()); // salisa hacia el servidor
        objectOutputStream.flush(); // vacía el búfer de salida para enviar información de encabezado
        // Establece el fluojo de entrada para los objetos
        objectInputStream = new ObjectInputStream(socket.getInputStream());  // entrada de datos del servidor
        estacion.setIdSocket(socket.getPort());
    }
    
    public void close() throws IOException {
        if (objectInputStream != null)
           objectInputStream.close();
        if (objectInputStream != null)
           objectInputStream.close();
        if (socket != null)
           socket.close();
    }
}
