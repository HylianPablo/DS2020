package es.uva.inf.ds.vinoteca.userinterface;

import es.uva.inf.ds.vinoteca.domain.controllers.ControladorCUCrearPedido;

/**
 * Controlador de la interfaz del caso de uso "Crear pedido de Abonado".
 * @author pamarti
 * @author alerome
 * @author ivagonz
 */
public class ControladorVistaAtencionCliente {
    
    private final VistaAtencionCliente view;
    private final ControladorCUCrearPedido cuController;
    
    public ControladorVistaAtencionCliente(VistaAtencionCliente view){
        this.view = view;
        cuController = ControladorCUCrearPedido.getController();
    }
}
