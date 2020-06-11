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
public class ControladorCUConsultarImpagos { //REVISAR
    
    public static ControladorCUConsultarImpagos getController(){
        return new ControladorCUConsultarImpagos();
    }
    
    public ArrayList<String> consultarImpagos(String fecha){
        ArrayList <Factura> facturas = Factura.consultaFacturasAntesDeFecha(fecha);
        ArrayList<ArrayList<Pedido>> matrizPedidos = new ArrayList<>();
        ArrayList<Abonado> abonados = new ArrayList<>();
        for(int i=0;i<facturas.size();i++){
            Factura f0 = facturas.get(0); //Elige usuario o todas????
            ArrayList<Pedido> pedidos = f0.getPedidosAsociados();
            matrizPedidos.add(pedidos);
            Pedido p0 = pedidos.get(0);
            Abonado ab = p0.getAbonado();
            abonados.add(ab);
        }
        ArrayList<String> detalles = new ArrayList<>();
        for(int i = 0;i<facturas.size();i++){
            String detalle = "Factura: "+Integer.toString(facturas.get(i).getNumeroFactura()) +", Abonado: "
                    + Integer.toString(abonados.get(0).getNumeroAbonado())+" ,";
            String pedidosDetalle="";
            for(int j =0; j<matrizPedidos.get(i).size();j++){
                pedidosDetalle= pedidosDetalle + "Pedido: " + Integer.toString(matrizPedidos.get(i).get(j).getNumeroPedido());
                if(j==matrizPedidos.get(i).size()-1){
                    pedidosDetalle = pedidosDetalle + ".";
                }else{
                    pedidosDetalle = pedidosDetalle + ", ";
                }
            }
            detalle = detalle + pedidosDetalle;
            detalles.add(detalle);
        }
        return detalles;
    }
    
    


}