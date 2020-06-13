package es.uva.inf.ds.vinoteca.userinterface;

import es.uva.inf.ds.vinoteca.common.FacturaVencidaException;
import es.uva.inf.ds.vinoteca.domain.controllers.ControladorCUCrearPedido;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        try {
            boolean b = cuController.comprobarPlazosVencidos(idAbonado);
            if(!b){
                String textoCorreo = "Desde la vinoteca G02 le informamos de que aún tiene facturas vencidas y no puede "
                        + "realizar otro pedido en estos momentos.";
                view.setMensajeError("Tiene pedidos vencidos.");
            }
        } catch (FacturaVencidaException ex) {
            Logger.getLogger(ControladorVistaAtencionCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
