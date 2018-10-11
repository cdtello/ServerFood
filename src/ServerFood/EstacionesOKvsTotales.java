/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerFood;

import java.io.Serializable;


/**
 *
 * @author C.TELLO
 */
public class EstacionesOKvsTotales implements Serializable{
    private int id_producto;
    private int id_pedido;
    private int estaciones_ok;
    private int estaciones_totales;

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public EstacionesOKvsTotales() {
    }

    public int getEstaciones_ok() {
        return estaciones_ok;
    }

    public void setEstaciones_ok(int estaciones_ok) {
        this.estaciones_ok = estaciones_ok;
    }

    public int getEstaciones_totales() {
        return estaciones_totales;
    }

    public void setEstaciones_totales(int estaciones_totales) {
        this.estaciones_totales = estaciones_totales;
    }
    
    
    
}
