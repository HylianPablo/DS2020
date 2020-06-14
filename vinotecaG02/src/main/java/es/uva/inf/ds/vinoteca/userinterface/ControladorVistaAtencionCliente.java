package es.uva.inf.ds.vinoteca.userinterface;

import es.uva.inf.ds.vinoteca.common.ReferenciaNoDisponibleException;
import es.uva.inf.ds.vinoteca.common.FacturaVencidaException;
import es.uva.inf.ds.vinoteca.domain.controllers.ControladorCUCrearPedido;
import es.uva.inf.ds.vinoteca.domain.models.Abonado;
import es.uva.inf.ds.vinoteca.domain.models.Referencia;
import java.util.ArrayList;
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
    
    /**
     * Constructor del controlador de la interfaz del caso de uso "Crear pedido abonado".
     * @param view Interfaz asociada al caso de uso.
     */
    public ControladorVistaAtencionCliente(VistaAtencionCliente view){
        idAbonado=-1;
        this.view = view;
        cuController = ControladorCUCrearPedido.getController();
    }

    /**
     * Comprueba que el abonado existe en el sistema, habilitando la confirmación para generar un nuevo pedido.
     * @param idAbonado Número entero que representa el identificador del abonado.
     */
    public void procesaDatosIntroducirNumeroAbonado(int idAbonado) {
        this.idAbonado=idAbonado;
        Abonado ab = cuController.comprobarAbonado(idAbonado);
        if(ab == null){
            view.setMensajeError("El empleado no existe en la BD.");
        }else{
            ArrayList<String> datos = cuController.getDatos();
            view.actualizarVista(datos);
        }
    }
    
    /**
     * Comprueba que el abonado no tiene pedidos vencidos.
     */
    public void procesaConfirmacion(){
        try {
            boolean b = cuController.comprobarPlazosVencidos();
            if(!b){
                String textoCorreo = "Desde la vinoteca G02 le informamos de que aún tiene facturas vencidas y no puede "
                        + "realizar otro pedido en estos momentos.";
                view.setMensajeError("Tiene pedidos vencidos.");
            }
        } catch (FacturaVencidaException ex) {
            Logger.getLogger(ControladorVistaAtencionCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Comprueba si la referencia está disponible y de ser así muestra sus datos.
     * @param idReferencia Número entero que representa el identificador de la referencia que se quiere comprar.
     * @param cantidad Número entero que representa la cantidad que se quiere comprar.
     * @throws {@code ReferenciaNoDisponibleException} en caso de que la referencia no se encuentre disponible. 
     */
    public void procesaIntroducirReferencia(int idReferencia, int cantidad) throws ReferenciaNoDisponibleException {
        Referencia r = cuController.comprobarReferencia(idReferencia, cantidad);
        if(r == null){
            view.setMensajeError("La referencia no existe en la BD");
            view.funcionBandera();
        }
        boolean b2 = cuController.comprobarDisponibilidad();
        if(!b2){
            view.setMensajeError("La referencia no esta disponible");
            view.funcionBandera();
        }
    }

    /**
     * Termina el proceso del caso de uso.
     */
    public void procesarTerminarProceso() {
        cuController.registrarPedido();
    }
}
