package es.uva.inf.ds.vinoteca.domain.controllers;

import es.uva.inf.ds.vinoteca.common.AbonadoNotExistsException;
import es.uva.inf.ds.vinoteca.common.ReferenciaNoDisponibleException;
import es.uva.inf.ds.vinoteca.domain.models.Abonado;
import es.uva.inf.ds.vinoteca.domain.models.LineaPedido;
import es.uva.inf.ds.vinoteca.domain.models.Pedido;
import es.uva.inf.ds.vinoteca.domain.models.Referencia;
import es.uva.inf.ds.vinoteca.persistence.daos.DAOLineaPedido;
import es.uva.inf.ds.vinoteca.persistence.daos.DAOPedido;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controlador del caso de uso "Crear pedido de Abonado".
 * @author pamarti
 * @author alerome
 * @author ivagonz
 */
public class ControladorCUCrearPedido {
    
    /**
     * Factoría de controladores del caso de uso.
     * @return Nueva instancia del controlador del caso de uso "Crear pedido de Abonado".
     */
    
    private Abonado b;
    private Referencia r;
    private Pedido newPedido;
    private LineaPedido newLineaPedido;
    
    public static ControladorCUCrearPedido getController(){
        return new ControladorCUCrearPedido();
    }

    public void crearPedidoAbonado(int idAbonado) {
        try{
            b = Abonado.getAbonado(idAbonado);
        }catch(AbonadoNotExistsException ex){
            Logger.getLogger(ControladorCURegistrarRecepcionCompra.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    
    public void comprobarPlazosVencidos(int idAbonado){ //Necesario idAbonado?? comprobar
        ArrayList<Pedido> pedidos = b.getPedidos();
        boolean bandera = true;
        for(int i=0;i<pedidos.size();i++){
            if(pedidos.get(i).comprobarNoVencido()){
                bandera=false;
                break;
            }
        }
        if(bandera){
            newPedido = new Pedido(1,LocalDateTime.now(),"notaEntrega",0.0,null,null,0,b.getNumeroAbonado());
        }
    }

    public void comprobarReferencia(int idReferencia, int cantidad) throws ReferenciaNoDisponibleException {
        r = null;
        r = Referencia.getReferencia(idReferencia);
        newPedido.setImporte(cantidad * r.getPrecio());
        if(r==null){
            throw new ReferenciaNoDisponibleException("No existe una referencia con esa id");
        }
        boolean disponible = r.comprobarDisponible();
        if(!disponible){
            throw new ReferenciaNoDisponibleException("La referencia no esta disponible");
        }
        newLineaPedido = newPedido.crearLineaPedido(idReferencia, cantidad);
        String jsonNewLinea = newLineaPedido.getJson();
        DAOLineaPedido.añadirLineaPedido(jsonNewLinea);
        //falta crear pedido bien
    }

    public void registrarPedido() {
        newPedido.cambiarEstadoPendiente();
        String jsonNewPedido = newPedido.getJson();
        DAOPedido.añadirPedido(jsonNewPedido);
    }
}
