/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.userinterface;

import es.uva.inf.ds.vinoteca.domain.models.Empleado;
import es.uva.inf.ds.vinoteca.domain.controllers.ControladorCUIdentificarse;

/**
 * Controlador de la interfaz del caso de uso "Identificar empleado". Gestionará el inicio de sesión de los empleados y los correspondientes posibles errores.
 * @author pamarti
 * @author alerome
 * @author ivagonz
 */
public class CtrlVistaIdentificarse {
    private final VistaIdentificarse view;
    private final ControladorCUIdentificarse cuController;
    
    /**
     * Constructor del controlador de la interfaz del caso de uso "Identificar empleado".
     * @param view Interfaz asociada al controlador.
     */
    public CtrlVistaIdentificarse(VistaIdentificarse view){
        this.view=view;
        cuController = ControladorCUIdentificarse.getController();
    }
    
    /**
     * Comprueba que el empleado existe en el sistema y está activo. En caso contrario indicará a la vista que muestre el correspondiente mensaje de error.
     * @param user Nombre de usuario del empleado que quiere identificarse en el sistema.
     * @param password Contraseña del empleado que quiere identificarse en el sistema.
     */
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
