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
import es.uva.inf.ds.vinoteca.domain.models.LineaPedido;
import es.uva.inf.ds.vinoteca.domain.models.Pedido;
import es.uva.inf.ds.vinoteca.persistence.daos.DAOCompra;
import es.uva.inf.ds.vinoteca.persistence.daos.DAOLineaPedido;
import es.uva.inf.ds.vinoteca.persistence.daos.DAOPedido;
import java.util.ArrayList;

/**
 *
 * @author alejandro
 */
public class ControladorCURegistrarRecepcionCompra {
    
    Compra c ;
    Bodega b ;
    Pedido p ;
    ArrayList<LineaCompra> lc;
    ArrayList<LineaPedido> lp;
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
    
    
    //como añadir esto para que sea loop, instruccion 10.1 en adelante y acabar la instruccion de añadir a base de datos la lineaPedido
    public void actualizarLineaCompra(int i){
        lc.get(i).marcarRecibido();
        lp = lc.get(i).actualizaLineasPedido();
        //acabAAAAAAAAAAAAAAar 
        String lineaPedidoJSONString = pasarLineasPedidoAJsonString(lp);
        //acabaAAAAAAAAAAAAAAAAAr
        DAOLineaPedido.insertarLineasPedido(lineaPedidoJSONString);
    }
    
    //completar, pasar el array de lineas de pedido a json
    private String pasarLineasPedidoAJsonString(ArrayList<LineaPedido> lp){
        
    }
    
    //esta deberia ser void, ojo al diseño
    public void comprobarRecibidas(){
        boolean allRecvs = c.comprobarRecibidas();
        if(allRecvs){
            c.marcarRecibidaCompleta();
            //acabAAAAAAAAAAAAAAAAAAar
            String compraJSON = c.getJSON();   
            //acabaAAAAAAAAAAAAAAAAAAAr
            DAOCompra.actualizarCompra(compraJSON);
        }else{
            //SEGUIRRRRRRRRRRRR
        }        
    }
  
       
    public void actualizarPedidos(){
        boolean bandera = true;
        for(int i = 0; i < lp.size(); i++){
            boolean completo = lp.get(i).checkCompleto();
            if(!completo){
                bandera = false;
            }
        }
        if(bandera){
            p = Pedido.getPedido(lp.get(0).getCodigoPedido());
            p.actualizarEstadoACompletado();
            //ACABAR LOS METODOS DE PASAR A JSON Y DE INSERTAR EN LA BASE DE DATOS
            String pedidoJSONString = p.getJSON();
            DAOPedido.actualizaPedido(pedidoJSONString);
        }
    }
}
