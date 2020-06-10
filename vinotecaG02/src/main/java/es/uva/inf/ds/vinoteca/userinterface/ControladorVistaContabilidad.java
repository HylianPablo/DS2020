/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.userinterface;

import es.uva.inf.ds.vinoteca.domain.controllers.ControladorCUConsultarImpagos;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

/**
 *
 * @author pablo
 */
public class ControladorVistaContabilidad {
    private final VistaContabilidad view;
    private final ControladorCUConsultarImpagos cuController;
    
    public ControladorVistaContabilidad(VistaContabilidad view){
        this.view=view;
        cuController= ControladorCUConsultarImpagos.getController();
    }
    
    public void procesarIntroduceFecha(String fecha){
        if(!comprobarFechaCorrecta(fecha)){
            view.setMensajeError("Introduzca una fecha válida, por favor.");
   
        }else{
            ArrayList<String> detalles = cuController.consultarImpagos(fecha);
            view.actualizarTabla(detalles);
        }
    }
    
    private boolean comprobarFechaCorrecta(String fecha){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd",Locale.US);
        if(fecha==null ||!coincideFormato(fecha) ){
            return false;
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
