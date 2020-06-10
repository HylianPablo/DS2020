/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.userinterface;

import es.uva.inf.ds.vinoteca.domain.models.Empleado;
import es.uva.inf.ds.vinoteca.domain.controllers.ControladorCUIdentificarse;

/**
 *
 * @author pablo
 */
public class CtrlVistaIdentificarse {
    private final VistaIdentificarse view;
    private final ControladorCUIdentificarse cuController;
    
    public CtrlVistaIdentificarse(VistaIdentificarse view){
        this.view=view;
        cuController = ControladorCUIdentificarse.getController();
    }
    
    public void checkLogin(String user, String password){
        Empleado empleado = cuController.identificarEmpleado(user, password);
        if(empleado == null){
            view.setMensajeError("El empleado no existe en la BD.");
        }else{
            if(empleado.getNif().equals("NotActivo")){
                view.setMensajeError("El empleado no está activo");
            }else{
                int rolEmp = empleado.getRol();
                GestorDeInterfazDeUsuario giu = new GestorDeInterfazDeUsuario();
                giu.elegirVista(rolEmp,view);
            }
        }
    }
}
