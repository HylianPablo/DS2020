/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.userinterface;

import es.uva.inf.ds.vinoteca.domain.controllers.ControladorCUIdentificarse;
import es.uva.inf.ds.vinoteca.domain.controllers.ControladorCURegistrarRecepcionCompra;
import java.util.ArrayList;

/**
 *
 * @author alejandro
 */
public class ControladorVistaAlmacen {
    
    private final VistaAlmacen view;
    private final ControladorCURegistrarRecepcionCompra cuController;
    
    public ControladorVistaAlmacen(VistaAlmacen view){
        this.view=view;
        cuController = ControladorCURegistrarRecepcionCompra.getController();
    }
    
    public void procesaDatosIntroducirIdCompra(int idCompra){
        cuController.comprobarCompraNoCompletada(idCompra);
        
    }
    
    public void procesaDatosSeleccionaLineas(int indice){
        cuController.actualizarLineaCompra(indice);
    }
    
    //igual comprobarRecibidas no deberia retornar
    public void procesaDatosFinalizar(){
        cuController.comprobarRecibidas();
        
    }
}
