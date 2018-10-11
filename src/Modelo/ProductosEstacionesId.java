package Modelo;
// Generated 25/07/2018 09:07:06 PM by Hibernate Tools 4.3.1



/**
 * ProductosEstacionesId generated by hbm2java
 */
public class ProductosEstacionesId  implements java.io.Serializable {


     private int idProducto;
     private int idEstacion;
     
     private int id_producto;
     private int id_estacion;

    public int getId_Producto() {
        return id_producto;
    }

    public void setId_Producto(int id_Producto) {
        this.id_producto = id_Producto;
    }

    public int getId_Estacion() {
        return id_estacion;
    }

    public void setId_Estacion(int id_Estacion) {
        this.id_estacion = id_Estacion;
    }
        
    public ProductosEstacionesId() {
    }

    public ProductosEstacionesId(int idProducto, int idEstacion) {
       this.idProducto = idProducto;
       this.idEstacion = idEstacion;
    }
   
    public int getIdProducto() {
        return this.idProducto;
    }
    
    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }
    public int getIdEstacion() {
        return this.idEstacion;
    }
    
    public void setIdEstacion(int idEstacion) {
        this.idEstacion = idEstacion;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof ProductosEstacionesId) ) return false;
		 ProductosEstacionesId castOther = ( ProductosEstacionesId ) other; 
         
		 return (this.getIdProducto()==castOther.getIdProducto())
 && (this.getIdEstacion()==castOther.getIdEstacion());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getIdProducto();
         result = 37 * result + this.getIdEstacion();
         return result;
   }   


}


