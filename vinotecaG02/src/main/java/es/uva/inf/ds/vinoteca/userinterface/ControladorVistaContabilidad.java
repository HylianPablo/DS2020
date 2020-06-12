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
        try{
            if(!comprobarFechaCorrecta(fecha)){
                view.setMensajeError("Introduzca una fecha válida, por favor.");
            }else{
                ArrayList<String> detalles = cuController.consultarImpagos(fecha);
                if(!detalles.isEmpty()){
                    view.actualizarTabla(detalles);
                }else{
                    view.setMensajeError("No hay facturas antes de la fecha seleccionada");
                }
            }
        }catch(IllegalDateException ex){
            Logger.getLogger(ControladorVistaContabilidad.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    
    private boolean comprobarFechaCorrecta(String fecha) throws IllegalDateException{
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd",Locale.US);
        if(fecha==null ||!coincideFormato(fecha) ){
            throw new IllegalDateException("La fecha introducida es errónea.");
        }
        LocalDate fechaLDT = LocalDate.parse(fecha,formatter);
        LocalDate now = LocalDate.now();
        LocalDate limite = now.minusDays(30);
        return fechaLDT.isBefore(limite);
        //Comprobar que es 30 dias inferior a actual
    }
    
    private boolean coincideFormato(String fecha){
        if(fecha.matches("[1-2][0-9][0-9][0-9][-][0][1-9][-][0-2][1-9]"))
            return true;
        if(fecha.matches("[1-2][0-9][0-9][0-9][-][1][0-2][-][0-2][1-9]"))
            return true;
        if(fecha.matches("[1-2][0-9][0-9][0-9][-][0][1-9][-][3][0-1]"))
            return true;
        if(fecha.matches("[1-2][0-9][0-9][0-9][-][1][0-2][-][3][0-1]"))
            return true;
        return false;
    }
}
