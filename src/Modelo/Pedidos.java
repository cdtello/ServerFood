package Modelo;
// Generated 25/07/2018 09:07:06 PM by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Pedidos generated by hbm2java
 */
public class Pedidos  implements java.io.Serializable {


     private int id;
     private CategoriaPedidos categoriaPedidos;
     private int precio_total;
     private String nombre_cliente;
     private String telefono_cliente;
     private Integer mesa;
     private String direccion;
     private Date fechaPedido;
     private Date hora_inicio;
     private Date hora_entrega;
     private Set productosPedidos = new HashSet(0);
     private int estado;
     private int id_categoria_pedidos;

    public int getId_categoria_pedidos() {
        return id_categoria_pedidos;
    }

    public void setId_categoria_pedidos(int id_categoria_pedidos) {
        this.id_categoria_pedidos = id_categoria_pedidos;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
     
     
    public Pedidos() {
    }

	
    public Pedidos(int id, CategoriaPedidos categoriaPedidos, int precioTotal) {
        this.id = id;
        this.categoriaPedidos = categoriaPedidos;
        this.precio_total = precioTotal;
    }
    public Pedidos(int id, CategoriaPedidos categoriaPedidos, int precioTotal, String nombreCliente, String telefonoCliente, Integer mesa, String direccion, Date fechaPedido, Date horaInicio, Date horaEntrega, Set productosPedidos) {
       this.id = id;
       this.categoriaPedidos = categoriaPedidos;
       this.precio_total = precioTotal;
       this.nombre_cliente = nombreCliente;
       this.telefono_cliente = telefonoCliente;
       this.mesa = mesa;
       this.direccion = direccion;
       this.fechaPedido = fechaPedido;
       this.hora_inicio = horaInicio;
       this.hora_entrega = horaEntrega;
       this.productosPedidos = productosPedidos;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public CategoriaPedidos getCategoriaPedidos() {
        return this.categoriaPedidos;
    }
    
    public void setCategoriaPedidos(CategoriaPedidos categoriaPedidos) {
        this.categoriaPedidos = categoriaPedidos;
    }
    public int getPrecioTotal() {
        return this.precio_total;
    }
    
    public void setPrecioTotal(int precioTotal) {
        this.precio_total = precioTotal;
    }
    public String getNombreCliente() {
        return this.nombre_cliente;
    }
    
    public void setNombreCliente(String nombreCliente) {
        this.nombre_cliente = nombreCliente;
    }
    public String getTelefonoCliente() {
        return this.telefono_cliente;
    }
    
    public void setTelefonoCliente(String telefonoCliente) {
        this.telefono_cliente = telefonoCliente;
    }
    public Integer getMesa() {
        return this.mesa;
    }
    
    public void setMesa(Integer mesa) {
        this.mesa = mesa;
    }
    public String getDireccion() {
        return this.direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public Date getFechaPedido() {
        return this.fechaPedido;
    }
    
    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }
    public Date getHoraInicio() {
        return this.hora_inicio;
    }
    
    public void setHoraInicio(Date horaInicio) {
        this.hora_inicio = horaInicio;
    }
    public Date getHoraEntrega() {
        return this.hora_entrega;
    }
    
    public void setHoraEntrega(Date horaEntrega) {
        this.hora_entrega = horaEntrega;
    }
    public Set getProductosPedidos() {
        return this.productosPedidos;
    }
    
    public void setProductosPedidos(Set productosPedidos) {
        this.productosPedidos = productosPedidos;
    }




}


