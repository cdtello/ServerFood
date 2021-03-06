package Modelo;
// Generated 25/07/2018 09:07:06 PM by Hibernate Tools 4.3.1



/**
 * ProductosPedidoId generated by hbm2java
 */
public class ProductosPedidoId  implements java.io.Serializable {


     private int idProducto;
     private int idPedido;
     private int cantidad;
     private int precioXCantidad;
     private String observaciones;
     private int estacionesOk;
     private int estacionesTotales;

    public ProductosPedidoId() {
    }

	
    public ProductosPedidoId(int idProducto, int idPedido, int cantidad, int precioXCantidad, int estacionesOk, int estacionesTotales) {
        this.idProducto = idProducto;
        this.idPedido = idPedido;
        this.cantidad = cantidad;
        this.precioXCantidad = precioXCantidad;
        this.estacionesOk = estacionesOk;
        this.estacionesTotales = estacionesTotales;
    }
    public ProductosPedidoId(int idProducto, int idPedido, int cantidad, int precioXCantidad, String observaciones, int estacionesOk, int estacionesTotales) {
       this.idProducto = idProducto;
       this.idPedido = idPedido;
       this.cantidad = cantidad;
       this.precioXCantidad = precioXCantidad;
       this.observaciones = observaciones;
       this.estacionesOk = estacionesOk;
       this.estacionesTotales = estacionesTotales;
    }
   
    public int getIdProducto() {
        return this.idProducto;
    }
    
    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }
    public int getIdPedido() {
        return this.idPedido;
    }
    
    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }
    public int getCantidad() {
        return this.cantidad;
    }
    
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public int getPrecioXCantidad() {
        return this.precioXCantidad;
    }
    
    public void setPrecioXCantidad(int precioXCantidad) {
        this.precioXCantidad = precioXCantidad;
    }
    public String getObservaciones() {
        return this.observaciones;
    }
    
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    public int getEstacionesOk() {
        return this.estacionesOk;
    }
    
    public void setEstacionesOk(int estacionesOk) {
        this.estacionesOk = estacionesOk;
    }
    public int getEstacionesTotales() {
        return this.estacionesTotales;
    }
    
    public void setEstacionesTotales(int estacionesTotales) {
        this.estacionesTotales = estacionesTotales;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof ProductosPedidoId) ) return false;
		 ProductosPedidoId castOther = ( ProductosPedidoId ) other; 
         
		 return (this.getIdProducto()==castOther.getIdProducto())
 && (this.getIdPedido()==castOther.getIdPedido())
 && (this.getCantidad()==castOther.getCantidad())
 && (this.getPrecioXCantidad()==castOther.getPrecioXCantidad())
 && ( (this.getObservaciones()==castOther.getObservaciones()) || ( this.getObservaciones()!=null && castOther.getObservaciones()!=null && this.getObservaciones().equals(castOther.getObservaciones()) ) )
 && (this.getEstacionesOk()==castOther.getEstacionesOk())
 && (this.getEstacionesTotales()==castOther.getEstacionesTotales());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getIdProducto();
         result = 37 * result + this.getIdPedido();
         result = 37 * result + this.getCantidad();
         result = 37 * result + this.getPrecioXCantidad();
         result = 37 * result + ( getObservaciones() == null ? 0 : this.getObservaciones().hashCode() );
         result = 37 * result + this.getEstacionesOk();
         result = 37 * result + this.getEstacionesTotales();
         return result;
   }   


}


