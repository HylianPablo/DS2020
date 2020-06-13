package es.uva.inf.ds.vinoteca.domain.controllers;

import es.uva.inf.ds.vinoteca.common.AbonadoNotExistsException;
import es.uva.inf.ds.vinoteca.domain.models.Abonado;
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
    
    Abonado b;
    
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
}
