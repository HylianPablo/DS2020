package es.uva.inf.ds.vinoteca.userinterface;

import es.uva.inf.ds.vinoteca.domain.controllers.ControladorCUConsultarImpagos;
import java.util.ArrayList;

/**
 * Controlador de la interfaz del caso de uso "Consultar impagos".
 * @author pamarti
 * @author alerome
 * @author ivagonz
 */
public class ControladorVistaContabilidad {
    private final VistaContabilidad view;
    private final ControladorCUConsultarImpagos cuController;
    
    /**
     * Constructor del controlador de la interfaz del caso de uso "Consultar impagos".
     * @param view Interfaz asociada al caso de uso.
     */
    public ControladorVistaContabilidad(VistaContabilidad view){
        this.view=view;
        cuController= ControladorCUConsultarImpagos.getController();
    }
    
    /**
     * Obtiene las facturas por debajo de la fecha introducida y muestra sus detalles en la interfaz.
     * @param fecha Fecha que representa el periodo a partir del cual buscar las facturas.
     */
    public void procesarIntroduceFecha(String fecha){
        ArrayList<String> detalles = cuController.consultarImpagos(fecha);
        if(detalles==null){
            view.setMensajeError("Introduzca una fecha válida, por favor.");
        }else{
            if(!detalles.isEmpty()){
                view.actualizarTabla(detalles);
            }else{
                view.setMensajeError("No hay facturas antes de la fecha seleccionada");
            }
        }
    }
    
    
}
