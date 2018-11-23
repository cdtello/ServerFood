/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerFood;


import Vista.InterfazHilo;
import java.awt.Color;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import ventanas.Inicio;

/**
 *
 * @author C.TELLO
 */
public class AdminAplicacion implements Runnable{
    
    private int conectado1 = 0;
    private int conectado2 = 0;
    private int conectado3 = 0;
    private int conectado4 = 0;
    private int conectado5 = 0;

    
    
    ServerSocket socketServidor;
    AdministracionHilos clienteHilos = null;
    InterfazHilo interfazHilo = null;
    Thread hilo = null;
    int puerto = 5050;
    int contador = 0;
    ArrayList <String> nombres;
    ArrayList <String> ids;
    ArrayList<AdministracionHilos> arregloHilos;
    public Inicio ventanaPricipal;
    
    
    public int getConectado1() {
        return conectado1;
    }

    public void setConectado1(int conectado1) {
        this.conectado1 = conectado1;
    }

    public int getConectado2() {
        return conectado2;
    }

    public void setConectado2(int conectado2) {
        this.conectado2 = conectado2;
    }

    public int getConectado3() {
        return conectado3;
    }

    public void setConectado3(int conectado3) {
        this.conectado3 = conectado3;
    }

    public int getConectado4() {
        return conectado4;
    }

    public void setConectado4(int conectado4) {
        this.conectado4 = conectado4;
    }

    public int getConectado5() {
        return conectado5;
    }

    public void setConectado5(int conectado5) {
        this.conectado5 = conectado5;
    }
    public void setVentadaPrincipal(Inicio ventanaPricipal)
    {
        this.ventanaPricipal = ventanaPricipal;
    }
    public AdminAplicacion()
    {   
        arregloHilos = new ArrayList();
    }
    public void iniciarInterfazHilo()
    {
        interfazHilo = new InterfazHilo(this);
        interfazHilo.start();
    }
    public int buscarIdEstacion(int IdSocket)
    {
        int resultado = 0;
        for (int i = 0; i< arregloHilos.size(); i++)
        {
            if(arregloHilos.get(i).estacion.getIdSocket() == IdSocket)
            {
                return arregloHilos.get(i).estacion.getId();
            }
        }
        return resultado;
    }
    public void desconectarHilo(int IdSocket)
    {
        int id = buscarIdEstacion(IdSocket);
        switch (id){
            case 1:
                conectado1 = 0;
                contador --;
                break;
            case 2:
                conectado2 = 0;
                contador --;
                break;
            case 3:
                conectado3 = 0;
                contador --;
                break;
            case 4:
                conectado4 = 0;
                contador --;
                break;
            case 5:
                conectado5 = 0;
                contador --;               
                break;
        }
    }

    public void conectarHilo(int id)
    {
        //System.out.println("Entre con el id: "+id);
        switch(id){
            case 1:
            {
                ventanaPricipal.getEstadoP1().setText("Conectado");
                ventanaPricipal.getEstadoP1().setForeground(Color.GREEN);
                setConectado1(1);
                contador ++;
                break;
            }
            case 2:
            {
                ventanaPricipal.getEstadoP2().setText("Conectado");
                ventanaPricipal.getEstadoP2().setForeground(Color.GREEN);
                setConectado2(1);
                contador ++; 
                break;
            }
            case 3:
            {
                ventanaPricipal.getEstadoP3().setText("Conectado");
                ventanaPricipal.getEstadoP3().setForeground(Color.GREEN);
                setConectado3(1);
                contador ++; 
                break;
            }
            case 4:
            {
                ventanaPricipal.getEstadoP4().setText("Conectado");
                ventanaPricipal.getEstadoP4().setForeground(Color.GREEN);
                setConectado4(1);
                contador ++; 
               break;
            }
            case 5:
            {
                ventanaPricipal.getEstadoP5().setText("Conectado");
                ventanaPricipal.getEstadoP5().setForeground(Color.GREEN);
                setConectado5(1);
                contador ++; 
                break;
            }
            
        }
    }
        //************************CONEXION CON HILOS**************************
    public void activaConeccion() {
        // Inicio server
        
        try {

            
            socketServidor = new ServerSocket(puerto);
            System.out.println("Servidor Iniciado en el puerto: "+ puerto);
            iniciar(); 
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }
    @Override
    public void run() {
        
        
        while (hilo != null) {
            cerrarHilo();
            //System.out.println("Cerrando Hilos Nulos");
            try {
                    agregarHilo(socketServidor.accept());
                    System.out.println("Conexion Cliente Exitosa");
                    
            } catch (IOException ie) {
                System.out.println("Acceptance Error: " + ie);
            }
        }
    }
    
    public void iniciar() 
    {
    if (hilo == null) 
        {
        hilo = new Thread(this);
        hilo.start(); // Inicializa el metodo run()
        }
    }

    public int buscarHiloRepetido(AdministracionHilos clienteHilos)
    {
        int resultado = 0; 
        for (int i = 0;i < arregloHilos.size(); i++)
            {
                if(clienteHilos.estacion.getId() == arregloHilos.get(i).estacion.getId())
                {
                    resultado ++;
                }
            }
        return resultado;
    }
    public void agregarHilo(Socket socket) {
        clienteHilos = new AdministracionHilos(this, socket);
        try {
            System.out.println("El buscador arrojo: "+buscarHiloRepetido(clienteHilos));
            arregloHilos.add(clienteHilos);
            clienteHilos.open();  // Obtiene los flujos de entrada y salida
            clienteHilos.start(); // Ejecuta el metodo run()
            System.out.println("numero de Hilos hasta ahora:  "+arregloHilos.size());
            System.out.println("numero de Clientes hasta ahora:  "+contador);
        } catch (IOException ioe) {
            System.out.println("Error opening thread: " + ioe);
        }
    }
    public void cerrarHilo()
    {
        for(int i=0; i<arregloHilos.size();i++)
        {
            if(arregloHilos.get(i)==null)
            {
                arregloHilos.remove(i);
            }
        }
    }
    public void EliminarHilo(int idSocket)
    {
        for(int i=0; i<arregloHilos.size();i++)
        {
            System.out.println("Validando Estacion: "+arregloHilos.get(i).estacion.getNombre());
            if(arregloHilos.get(i).estacion.getIdSocket() == idSocket)
            {
                arregloHilos.remove(i);
                System.out.println("Eliminado");
            }
        }
    }
    
    public void enviarProductoRealizado(ProductoTransmision producto)
    {
        for(int i = 0; i < producto.getDestinos().size(); i++)
        {
            int destino = producto.getDestinos().get(i);
            System.out.println("Buscando estacion: "+destino);
            int encontrado = 0;
            
            for(int j = 0; j<arregloHilos.size(); j++)
            {
                System.out.println("Comparando estacion: "+destino+" con :"+arregloHilos.get(j).estacion.getId());
                if(arregloHilos.get(j).estacion.getId() == destino)
                {
                    System.out.println("Envio a la Estacion: "+arregloHilos.get(j).estacion.getId());
                    arregloHilos.get(j).enviarProducto(producto);
                    encontrado = 1;
                }
            }
            
            if(encontrado == 0)
            {
                JOptionPane.showMessageDialog(null, "Estacion Destino Desconectada...");
            }
        }
    }
    
    public void enviarPedidoRealizado(ArrayList<ProductoTransmision> pedido)
    {
        System.out.println("Tamaño de pedido a enviar: "+pedido.size());
        for(int i=0; i<pedido.size(); i++)
        {
            enviarProductoRealizado(pedido.get(i));
        }
    }
}
