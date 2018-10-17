/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

/**
 *
 * @author C.TELLO
 */
import Modelo.*;
import ServerFood.EstacionesOKvsTotales;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
public class Operaciones {
    
    public void insertarCategoria(String nombre)
    {
        SessionFactory sessionFactory = NewHibernateUtil.getSessionFactory();
        Session session;
        session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        Query queryConsulta = session.createSQLQuery("select id from categoria_productos order by id desc limit 1");
        int contadorCat = (int) queryConsulta.uniqueResult();
        contadorCat ++;
        System.out.println("El contador va en: "+contadorCat);
        Query queryInsertar = session.createSQLQuery("insert into categoria_productos(id,nombre)values("+contadorCat+",'"+nombre+"')");
        queryInsertar.executeUpdate();
        
        tx.commit();
        session.close();
        JOptionPane.showMessageDialog(null, "Categoria Creada Correctamente");
    }
    public CategoriaProductos consultarCategoriaXNombre(String categoria)
    {
        SessionFactory sessionFactory = NewHibernateUtil.getSessionFactory();
        Session session;
        session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        
        Query query = session.createSQLQuery("select * from categoria_productos where nombre = '"+categoria+"'");
        query.setResultTransformer(Transformers.aliasToBean(CategoriaProductos.class));
        CategoriaProductos cat = (CategoriaProductos) query.uniqueResult();
        tx.commit();
        session.close();
        return cat;
    }
    public List<CategoriaProductos> consultarTodasCategorias()
    {
        SessionFactory sessionFactory = NewHibernateUtil.getSessionFactory();
        Session session;
        session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createSQLQuery("select * from categoria_productos ORDER BY ID");
        query.setResultTransformer(Transformers.aliasToBean(CategoriaProductos.class));
        List<CategoriaProductos> lista = query.list();
        tx.commit();
        session.close();
        return lista;
    }
    public List<Productos> consultarTodosProductos()
    {
        SessionFactory sessionFactory = NewHibernateUtil.getSessionFactory();
        Session session;
        session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createSQLQuery("select * from productos ORDER BY ID");
        query.setResultTransformer(Transformers.aliasToBean(Productos.class));
        List<Productos> lista = query.list();
        tx.commit();
        session.close();
        return lista;
    }
    public CategoriaProductos consultarCategoriaXId(int id)
    {
        SessionFactory sessionFactory = NewHibernateUtil.getSessionFactory();
        Session session;
        session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        CategoriaProductos cat;
        cat = (CategoriaProductos)session.get(CategoriaProductos.class, id);
        tx.commit();
        session.close();        
        return cat;
    }
    public void eliminarCategoriaProductos(CategoriaProductos cat)
    {
        SessionFactory sessionFactory = NewHibernateUtil.getSessionFactory();
        Session session;
        session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.delete(cat);
        tx.commit();
        session.close();
        JOptionPane.showMessageDialog(null, "Categoria Eliminada Correctamente");
    }
    
    
    public List<Productos> consultarProductosXCategorias(int id_categoria)
    {
        SessionFactory sessionFactory = NewHibernateUtil.getSessionFactory();
        Session session;
        session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createSQLQuery("select * from productos where id_categoria_productos = '"+id_categoria+"' order by nombre");
        query.setResultTransformer(Transformers.aliasToBean(Productos.class));
        List<Productos> lista = query.list();
        tx.commit();
        session.close();
        return lista;
    }
    
    public void insertarProducto(Productos producto)
    {
        SessionFactory sessionFactory = NewHibernateUtil.getSessionFactory();
        Session session;
        session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        Query query = session.createSQLQuery("insert into productos(id_categoria_productos,nombre,descripcion,precio)values\n" +
"('"+producto.getCategoriaProductos().getId()+"','"+producto.getNombre()+"','"+producto.getDescripcion()+"',"+producto.getPrecio()+");");
        query.executeUpdate();
        
        //session.save(producto);
        tx.commit();
        session.close();
        
        //JOptionPane.showMessageDialog(null, "Producto Ingresado Correctamente");
    }
    public Productos consultarProductoXNombre(String producto)
    {
        SessionFactory sessionFactory = NewHibernateUtil.getSessionFactory();
        Session session;
        session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        
        Query query = session.createSQLQuery("select * from productos where nombre = '"+producto+"'");
        query.setResultTransformer(Transformers.aliasToBean(Productos.class));
        Productos prod = (Productos) query.uniqueResult();
        tx.commit();
        session.close();
        prod.setCategoriaProductos(consultarCategoriaXId(prod.getId_categoria_productos()));
        return prod;
    }
    public void insertarProductoEstacion(Estaciones estacion,Productos producto)
    {
        ProductosEstacionesId prod_estacion_id = new ProductosEstacionesId(producto.getId(),estacion.getId());
        ProductosEstaciones prod_estacion = new ProductosEstaciones(prod_estacion_id,estacion,producto);
        SessionFactory sessionFactory = NewHibernateUtil.getSessionFactory();
        Session session;
        session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        session.save(prod_estacion);
        tx.commit();
        session.close();
        //JOptionPane.showMessageDialog(null, "Producto Ingresado Correctamente"); 
    }
    public void eliminarProductoEstacion(Productos producto)
    {
        SessionFactory sessionFactory = NewHibernateUtil.getSessionFactory();
        Session session;
        session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        Query query = session.createSQLQuery("delete from productos_estaciones where id_producto = "+producto.getId());
        query.executeUpdate();
        
        //session.save(producto);
        tx.commit();
        session.close();
        
        //JOptionPane.showMessageDialog(null, "Producto Ingresado Correctamente");
    }
    public Productos consultarProducto(int producto)
    {
        SessionFactory sessionFactory = NewHibernateUtil.getSessionFactory();
        Session session;
        session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        Productos prod;
        prod = (Productos)session.get(Productos.class, producto);
        tx.commit();
        session.close();
        
        return prod;
    }
    public void modificarProducto(Productos producto)
    {
        SessionFactory sessionFactory = NewHibernateUtil.getSessionFactory();
        Session session;
        session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        
        session.update(producto);
        tx.commit();
        session.close();
        JOptionPane.showMessageDialog(null, "Producto Modificado Correctamente");
    }
    public void eliminarProducto(Productos producto)
    {
        SessionFactory sessionFactory = NewHibernateUtil.getSessionFactory();
        Session session;
        session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        session.delete(producto);
        
        tx.commit();
        session.close();
        JOptionPane.showMessageDialog(null, "Producto Eliminado Correctamente");
    }
    
    public Estaciones consultarEstacionXId(int id)
    {
        SessionFactory sessionFactory = NewHibernateUtil.getSessionFactory();
        Session session;
        session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        Estaciones est;
        est = (Estaciones)session.get(Estaciones.class, id);
        tx.commit();
        session.close();        
        return est;
    }
    public List<ProductosEstacionesId> consultarEstacionesXProducto(Productos producto)
    {
        SessionFactory sessionFactory = NewHibernateUtil.getSessionFactory();
        Session session;
        session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        
        Query query = session.createSQLQuery("select * from productos_estaciones where id_producto =" +producto.getId());
        query.setResultTransformer(Transformers.aliasToBean(ProductosEstacionesId.class));
        List<ProductosEstacionesId> lista = query.list();
        tx.commit();
        session.close();
        return lista;
    }
    public List<CategoriaPedidos> consultarTodasCategoriasPedidos()
    {
        SessionFactory sessionFactory = NewHibernateUtil.getSessionFactory();
        Session session;
        session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createSQLQuery("select * from categoria_pedidos ORDER BY ID");
        query.setResultTransformer(Transformers.aliasToBean(CategoriaPedidos.class));
        List<CategoriaPedidos> lista = query.list();
        tx.commit();
        session.close();
        return lista;
    }
    public int consultarIdUltimoPedidoRealizado()
    {
        SessionFactory sessionFactory = NewHibernateUtil.getSessionFactory();
        Session session;
        session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createSQLQuery("select id from pedidos order by id desc limit 1");
        int id_pedido = (int) query.uniqueResult();
        
        tx.commit();
        session.close();
        
        return id_pedido;
    }
    public void insertarPedido(Pedidos pedido)
    {
        SessionFactory sessionFactory = NewHibernateUtil.getSessionFactory();
        Session session;
        session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        int idCat = pedido.getCategoriaPedidos().getId();
        int precio = pedido.getPrecioTotal();
        String nombre = pedido.getNombreCliente();
        String telefono = pedido.getTelefonoCliente();
        int mesa = pedido.getMesa();
        String direccion = pedido.getDireccion();
        int estadoPedido = pedido.getEstado();
        
        Query query = session.createSQLQuery("insert into pedidos(id_categoria_pedidos,precio_total,nombre_cliente,telefono_cliente,mesa,direccion,fecha_pedido,hora_inicio,estado)\n" +
                "values"
                + "("+idCat+","+precio+",'"+nombre+"','"+telefono+"',"+mesa+",'"+direccion+"',now(),now(),"+estadoPedido+")");
        
        query.executeUpdate();
        
        tx.commit();
        session.close();
        JOptionPane.showMessageDialog(null, "Pedido Creado Correctamente");
    }
    public CategoriaPedidos consultarCategoriaXNombrePedido(String categoria)
    {
        SessionFactory sessionFactory = NewHibernateUtil.getSessionFactory();
        Session session;
        session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        
        Query query = session.createSQLQuery("select * from categoria_pedidos where nombre = '"+categoria+"'");
        query.setResultTransformer(Transformers.aliasToBean(CategoriaPedidos.class));
        CategoriaPedidos cat = (CategoriaPedidos) query.uniqueResult();
        tx.commit();
        session.close();
        return cat;
    }
    public void cambiarEstadoPedido(int idPedido,int nuevoEstado)
    {
        SessionFactory sessionFactory = NewHibernateUtil.getSessionFactory();
        Session session;
        session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        
        Query query = session.createSQLQuery("UPDATE pedidos  SET estado = "+nuevoEstado+" WHERE id = "+idPedido+"  ");
        query.executeUpdate();
        
        tx.commit();
        session.close();
        JOptionPane.showMessageDialog(null, "Pedido Actualizado");
    }
    public void insertarProductosPedido(int idProducto,int idPedido,int cantidad, int precioxCantidad,String observaciones,int estacionesOK,int estaciones_totales)
    {
        SessionFactory sessionFactory = NewHibernateUtil.getSessionFactory();
        Session session;
        session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        
        Query query = session.createSQLQuery("insert into productos_pedido(id_producto,id_pedido,cantidad,precio_x_cantidad,observaciones,estaciones_ok,estaciones_totales)values\n" +
              "( "+idProducto+" ,"+idPedido+", "+cantidad+" , "+precioxCantidad+",'"+observaciones+"',"+estacionesOK+","+estaciones_totales+")");
        query.executeUpdate();
        
        tx.commit();
        session.close();
        JOptionPane.showMessageDialog(null, "Pedido Actualizado");
    }
    public void actualizarProductosPedido(int idProducto,int idPedido)
    {
        int valor = 0;
        SessionFactory sessionFactory = NewHibernateUtil.getSessionFactory();
        Session session;
        session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        Query queryConsulta = session.createSQLQuery("select estaciones_ok from productos_pedido where id_producto = "+idProducto+" and id_pedido = "+idPedido+" limit 1"+idPedido);
        valor = (int) queryConsulta.uniqueResult();
        valor++;
        Query query = session.createSQLQuery("update productos_pedido set estaciones_ok = "+valor+" where id_producto = "+idProducto+" and id_pedido = "+idPedido);
        query.executeUpdate();
        
        Query queryConsultaEstaciones_ok = session.createSQLQuery("select id_producto,id_pedido,estaciones_ok,estaciones_totales from productos_pedido where id_pedido ="+idPedido);
        queryConsultaEstaciones_ok.setResultTransformer(Transformers.aliasToBean(EstacionesOKvsTotales.class));
        List<EstacionesOKvsTotales> lista = queryConsultaEstaciones_ok.list();
        Iterator<EstacionesOKvsTotales> iter = lista.iterator();
        int resultado = 0;
        while(iter.hasNext())
        {
            
            EstacionesOKvsTotales estacionesOKvsTotales = iter.next();
            if(estacionesOKvsTotales.getEstaciones_ok() == estacionesOKvsTotales.getEstaciones_totales())
            {
                System.out.println("producto finalizado");
            }
            else{
                System.out.println("producto Pendiente");
                resultado = 1;
            }
        }
        if(resultado == 0)
        {
            Query queryResultado = session.createSQLQuery("update pedidos set estado = 3 , hora_entrega = now() where id = "+idPedido);
            queryResultado.executeUpdate();
            System.out.println("*******Pedido  Finalizado******");
        }
        
        
        tx.commit();
        session.close();
    }
    /***********
     * 
     */
    public List<Pedidos> consultarPedidosPendientes()
    {
        SessionFactory sessionFactory = NewHibernateUtil.getSessionFactory();
        Session session;
        session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createSQLQuery("select id,id_categoria_pedidos,nombre_cliente,mesa,precio_total,hora_inicio,estado from pedidos where estado = 0 order by id desc");
        query.setResultTransformer(Transformers.aliasToBean(Pedidos.class));
        List<Pedidos> lista = query.list();
        tx.commit();
        session.close();
        return lista;
    }
    public List<Pedidos> consultarPedidosEntregados()
    {
        SessionFactory sessionFactory = NewHibernateUtil.getSessionFactory();
        Session session;
        session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createSQLQuery("select id,id_categoria_pedidos,nombre_cliente,mesa,precio_total,hora_inicio,estado from pedidos where estado = 3 order by id desc");
        query.setResultTransformer(Transformers.aliasToBean(Pedidos.class));
        List<Pedidos> lista = query.list();
        tx.commit();
        session.close();
        return lista;
    }
    public List<Pedidos> consultarDomiciliosPendientes()
    {
        SessionFactory sessionFactory = NewHibernateUtil.getSessionFactory();
        Session session;
        session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createSQLQuery("select id,id_categoria_pedidos,nombre_cliente,telefono_cliente,direccion,mesa,precio_total,hora_inicio,estado from pedidos where estado = 1 order by id desc");
        query.setResultTransformer(Transformers.aliasToBean(Pedidos.class));
        List<Pedidos> lista = query.list();
        tx.commit();
        session.close();
        return lista;
    }
    public void insertarPago(Pagos pago)
    {
        SessionFactory sessionFactory = NewHibernateUtil.getSessionFactory();
        Session session;
        session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        
        int identificacion = pago.getIdentificacion();
        String nombre = pago.getNombre().toUpperCase();
        String observacion = pago.getObservacion();
        int valor = pago.getValor();
        
        
        Query query = session.createSQLQuery("insert into pagos(identificacion,nombre,observacion,valor,fecha)\n" +
                "values"
                + "("+identificacion+",'"+nombre+"','"+observacion+"',"+valor+",now())");
        
        query.executeUpdate();
        
        tx.commit();
        session.close();
        JOptionPane.showMessageDialog(null, "Pago Registrado Correctamente");
    }
    public List<Pagos> consultarPagoPorNombre(String nombre)
    {
        SessionFactory sessionFactory = NewHibernateUtil.getSessionFactory();
        Session session;
        session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createSQLQuery("select id,identificacion,nombre,observacion,valor,fecha from pagos where nombre like '%"+nombre.toUpperCase()+"%'");
        query.setResultTransformer(Transformers.aliasToBean(Pagos.class));
        List<Pagos> lista = query.list();
        tx.commit();
        session.close();
        return lista;
    }
    public List<Pagos> consultarPagoPorIdentificacion(int identificacion)
    {
        SessionFactory sessionFactory = NewHibernateUtil.getSessionFactory();
        Session session;
        session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createSQLQuery("select id,identificacion,nombre,observacion,valor,fecha from pagos where identificacion = "+identificacion);
        query.setResultTransformer(Transformers.aliasToBean(Pagos.class));
        List<Pagos> lista = query.list();
        tx.commit();
        session.close(); 
        return lista;
    }
    public List<Pagos> consultarPagoPorFecha(Date fechaInicio, Date fechaFin)
    {
        SessionFactory sessionFactory = NewHibernateUtil.getSessionFactory();
        Session session;
        session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createSQLQuery("select id,identificacion,nombre,observacion,valor,fecha from pagos where fecha BETWEEN CAST ('"+fechaInicio+"' AS DATE) \n" +
        "AND CAST ('"+fechaFin+"' AS DATE)");
        query.setResultTransformer(Transformers.aliasToBean(Pagos.class));
        List<Pagos> lista = query.list();
        tx.commit();
        session.close();
        return lista;
    }
}
