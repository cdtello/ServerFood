/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerFood;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author C.TELLO
 */
public class ProductoTransmision implements Serializable{
    private String nombreCliente;
    private String direccionCliente;
    private String telefonoCliente;
    private int id_pedido;
    private int id_producto;
    private int cantidad;
    private int precio;
    private String observacion;
    private String salsas;
    private String nombre_producto;
    private ArrayList<Integer> destinos;
    private String entregarA;

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    public ProductoTransmision() {
        destinos = new ArrayList<>();
    }

    
    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
     
    public void insertarDestino(int destino)
    {
        destinos.add(destino);
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getSalsas() {
        return salsas;
    }

    public void setSalsas(String salsas) {
        this.salsas = salsas;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public ArrayList<Integer> getDestinos() {
        return destinos;
    }

    public void setDestinos(ArrayList<Integer> destinos) {
        this.destinos = destinos;
    }

    public String getEntregarA() {
        return entregarA;
    }

    public void setEntregarA(String entregarA) {
        this.entregarA = entregarA;
    }
    
    
    
}
