package es.uva.inf.ds.vinoteca.domain.controllers;

import es.uva.inf.ds.vinoteca.common.AbonadoNotExistsException;
import es.uva.inf.ds.vinoteca.domain.models.Abonado;
import es.uva.inf.ds.vinoteca.domain.models.Pedido;
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
            Pedido newPedido = new Pedido(1,null,"notaEntrega",0.0,null,null,0,0);
        }
    }
}
