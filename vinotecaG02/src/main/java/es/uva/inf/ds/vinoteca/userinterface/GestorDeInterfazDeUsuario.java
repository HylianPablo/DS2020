/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.userinterface;

/**
 * Lanzador de interfaces en función del rol del empleado identificado en el sistema.
 * @author pamarti
 * @author alerome
 * @author ivagonz
 */
public class GestorDeInterfazDeUsuario {
    
    private static VistaContabilidad contabilidadView;
    
    /**
     * Constructor de la clase.
     */
    public GestorDeInterfazDeUsuario(){
        
    }
    
    /**
     * Elige la interfaz que se lanzará después de que el empleado se identifique correctamente. Cierra la vista de "Identificar empleados".
     * @param rol Número entero que representa el rol del empleado que se ha identificado en el sistema. {1 : Almacén, 2 : AtenciónCliente, 3 : Contabilidad}
     * @param loginView Interfaz del caso de uso anterior que debe cerrarse. 
     */
    public void elegirVista(int rol, VistaIdentificarse loginView){
        switch (rol){
            case 1:
                loginView.setVisible(false);
                loginView.dispose();
                java.awt.EventQueue.invokeLater(() -> {
                    contabilidadView = new VistaContabilidad();
                    contabilidadView.setVisible(true);
        });
                break;
            case 2:
                //lanzar caso 2
                break;
            case 3:
                //lanzar caso 3
                break;
            default:
                //lanzar excepcion
        }
    }
    
}
