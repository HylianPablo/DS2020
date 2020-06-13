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
    private int idAbonado;
    
    public ControladorVistaAtencionCliente(VistaAtencionCliente view){
        idAbonado=-1;
        this.view = view;
        cuController = ControladorCUCrearPedido.getController();
    }

    public void procesaDatosIntroducirNumeroAbonado(int idAbonado) {
        this.idAbonado=idAbonado;
        cuController.crearPedidoAbonado(idAbonado);
    }
    
    public void procesaConfirmacion(){
        cuController.comprobarPlazosVencidos(idAbonado);
    }
}
