package es.uva.inf.ds.vinoteca.userinterface;

import es.uva.inf.ds.vinoteca.common.IllegalDateException;
import es.uva.inf.ds.vinoteca.domain.controllers.ControladorCUConsultarImpagos;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

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
     * Comprueba que la fecha introducida es correcta y en ese caso, activa el caso de uso.
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
