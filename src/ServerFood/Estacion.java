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
public class Estacion implements Serializable{
    //public ArrayList<Producto> listaProductos;
    private String nombre;
    private int idSocket;
    private int id;
    private int estado;

    public int getIdSocket() {
        return idSocket;
    }

    public void setIdSocket(int idSocket) {
        this.idSocket = idSocket;
    }
    
    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Estacion(String nombre, int id) {
        this.nombre = nombre;
        this.id = id;
        //this.listaProductos = new ArrayList();
        this.estado = 0;
        this.idSocket = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}
