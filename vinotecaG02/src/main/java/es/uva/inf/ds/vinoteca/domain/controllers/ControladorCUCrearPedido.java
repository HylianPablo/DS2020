package es.uva.inf.ds.vinoteca.domain.controllers;

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
    public static ControladorCUCrearPedido getController(){
        return new ControladorCUCrearPedido();
    }
}
