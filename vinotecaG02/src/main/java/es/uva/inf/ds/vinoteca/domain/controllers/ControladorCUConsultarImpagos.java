/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.domain.controllers;

import es.uva.inf.ds.vinoteca.domain.models.Abonado;
import es.uva.inf.ds.vinoteca.domain.models.Factura;
import es.uva.inf.ds.vinoteca.domain.models.Pedido;
import java.util.ArrayList;

/**
 *
 * @author pablo
 */
public class ControladorCUConsultarImpagos {
    
    public static ControladorCUConsultarImpagos getController(){
        return new ControladorCUConsultarImpagos();
    }
    
    public void consultarImpagos(String fecha){
        if(!comprobarFechaCorrecta(fecha)){
            //lanzar excepcion
        }
        ArrayList <Factura> facturas = Factura.consultaFacturasAntesDeFecha(fecha);
        Factura f0 = facturas.get(0); //Elige usuario o todas????
        ArrayList<Pedido> pedidos = f0.getPedidosAsociados();
        Pedido p0 = pedidos.get(0);
        Abonado ab = p0.getAbonado();
    }
    
    private boolean comprobarFechaCorrecta(String fecha){
        return (fecha.matches("[0-9][0-9][0-9][0-9][-][0-9][0-9][-][0-9][0-9]"));
        //Comprobar que es 30 dias inferior a actual
    }


}