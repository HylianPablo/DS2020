/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.domain.controllers;

import es.uva.inf.ds.vinoteca.domain.models.Empleado;

/**
 * Controlador del caso de uso "Identificar empleado", encargado de comprobar si un empleado existe en el sistema y está activo.
 * @author pamarti
 * @author alerome
 * @author ivagonz
 */
public class ControladorCUIdentificarse {
    
    /**
     * Factoría de controladores de caso de uso "Identificar empleado".
     * @return Nuevo controlador del caso de uso "Identificar empleado".
     */
    public static ControladorCUIdentificarse getController(){
        return new ControladorCUIdentificarse();
    }
    
    /**
     * Obtiene la instancia del empleado que se ha identificado en el sistema en caso de que exista.
     * @param user Cadena de caracteres que representa el nombre de usuario del empleado que quiere identificarse en el sistema.
     * @param password Cadena de caracteres que representa la contraseña del empleado que quiere identificarse en el sistema.
     * @return Instancia del empleado que se ha identificado en el sistema en caso de que exista. {@code Null} en caso contrario.
     */
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
