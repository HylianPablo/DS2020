/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.userinterface;

import es.uva.inf.ds.vinoteca.domain.controllers.ControladorCUConsultarImpagos;

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
        if(fecha==null || fecha.equals("")){
            //Lanzar excepcion
        }
        cuController.consultarImpagos(fecha);
    }
}
