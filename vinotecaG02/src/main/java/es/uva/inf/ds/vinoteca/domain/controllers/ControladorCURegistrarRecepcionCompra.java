/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.domain.controllers;

import es.uva.inf.ds.vinoteca.domain.models.LineaCompra;

/**
 *
 * @author alejandro
 */
public class ControladorCURegistrarRecepcionCompra {
    
    public static ControladorCURegistrarRecepcionCompra getController(){
        return new ControladorCURegistrarRecepcionCompra();
    }
    
    public void comprobarCompraNoCompletada(int idCompra){
        
    }
    
    public void actualizarLineaCompra(int i){
        LineaCompra.marcarRecibido();
    }
    
    public boolean comprobarRecibidas(){
        
    }
    
    public void actualizarPedidos(){
        
    }
}
