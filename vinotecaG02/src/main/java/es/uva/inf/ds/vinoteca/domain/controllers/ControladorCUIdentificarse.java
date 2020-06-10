/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.domain.controllers;

import es.uva.inf.ds.vinoteca.domain.models.Empleado;

/**
 *
 * @author pablo
 */
public class ControladorCUIdentificarse {
    
    public static ControladorCUIdentificarse getController(){
        return new ControladorCUIdentificarse();
    }
    
    public Empleado identificarEmpleado(String user, String password){
        Empleado empleado = Empleado.getEmpleadoPorLoginYPassword(user, password);
        if(empleado==null){
            return empleado;
        }
        if(!empleado.isActivo()){
            empleado.setNif("NotActivo");
        }
        
        return empleado;
    }
}
