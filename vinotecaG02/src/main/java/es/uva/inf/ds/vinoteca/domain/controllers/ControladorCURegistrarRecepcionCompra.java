/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.domain.controllers;

import es.uva.inf.ds.vinoteca.domain.models.Bodega;
import es.uva.inf.ds.vinoteca.domain.models.Compra;
import es.uva.inf.ds.vinoteca.domain.models.Referencia;
import es.uva.inf.ds.vinoteca.domain.models.LineaCompra;
import java.util.ArrayList;

/**
 *
 * @author alejandro
 */
public class ControladorCURegistrarRecepcionCompra {
    
    Compra c ;
    Bodega b ;
    ArrayList<LineaCompra> lc;
    int id;
    Referencia r;
        
    public static ControladorCURegistrarRecepcionCompra getController(){
        return new ControladorCURegistrarRecepcionCompra();
    }
    
    public void comprobarCompraNoCompletada(int idCompra){
        c = Compra.getCompra(id);
        b = c.getBodega();
        String nombre = b.getNombre();
        lc = c.getLineasCompra();
        for (int i = 0; i < lc.size(); i++){
            lc.get(i).getUnidades();
            r = lc.get(i).getReferencia();
            r.getCodigo();
            r.getContenido();
            r.getPrecio();
            r.comprobarDisponible();
            r.comprobarPorCajas();
        }
    }
    
    public void actualizarLineaCompra(int i){
        LineaCompra.marcarRecibido();
    }
    
    public boolean comprobarRecibidas(){
        
    }
    
    public void actualizarPedidos(){
        
    }
}
