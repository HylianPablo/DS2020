/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.domain.controllers;

import es.uva.inf.ds.vinoteca.common.CompletadaException;
import es.uva.inf.ds.vinoteca.common.DNIPassNotValidException;
import es.uva.inf.ds.vinoteca.common.NullCompraException;
import es.uva.inf.ds.vinoteca.domain.models.Bodega;
import es.uva.inf.ds.vinoteca.domain.models.Compra;
import es.uva.inf.ds.vinoteca.domain.models.Empleado;
import es.uva.inf.ds.vinoteca.domain.models.Referencia;
import es.uva.inf.ds.vinoteca.domain.models.LineaCompra;
import es.uva.inf.ds.vinoteca.domain.models.LineaPedido;
import es.uva.inf.ds.vinoteca.domain.models.Pedido;
import es.uva.inf.ds.vinoteca.persistence.daos.DAOCompra;
import es.uva.inf.ds.vinoteca.persistence.daos.DAOLineaPedido;
import es.uva.inf.ds.vinoteca.persistence.daos.DAOPedido;
import es.uva.inf.ds.vinoteca.userinterface.ControladorVistaAlmacen;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonWriter;

/**
 *
 * @author alejandro
 */
public class ControladorCURegistrarRecepcionCompra {
    
    Compra c ;
    Bodega b ;
    Pedido p ;
    LineaCompra l;
    Referencia r;
    ArrayList<LineaCompra> lc;
    ArrayList<LineaCompra> lcnr;
    ArrayList<Referencia> refs;
    ArrayList<LineaPedido> lp;
    int id;
    boolean allRecvs;
    //Referencia r;
        
    public static ControladorCURegistrarRecepcionCompra getController(){
        return new ControladorCURegistrarRecepcionCompra();
    }
    
    public void comprobarCompraNoCompletada(int idCompra){
        refs = new ArrayList<>();
        try{
            c = Compra.getCompra(idCompra);
        }catch(NullCompraException | CompletadaException ex){
            Logger.getLogger(ControladorCURegistrarRecepcionCompra.class.getName()).log(Level.SEVERE,null,ex);
        }
        b = c.getBodega();
        String nombre = b.getNombre();
        lc = c.getLineasCompra();
        for (int i = 0; i < lc.size(); i++){
            r = lc.get(i).getReferencia();
            refs.add(r);
        }
    }
    
    public ArrayList<Integer> getCodigosLineasCompra(){
        ArrayList<Integer> codigos = new ArrayList<>();
        for(int i = 0; i < lc.size(); i++){
            codigos.add(lc.get(i).getCodigoLinea());
        }
        return codigos;
    }
    
    public ArrayList<Integer> getNumeroUnidadesLineasCompra(){
        ArrayList<Integer> numeros = new ArrayList<>();
        for(int i = 0; i < lc.size(); i++){
            numeros.add(lc.get(i).getUnidades());
        }
        return numeros;
    }
    
    public String getNombreBodega(){
        return b.getNombre();
    }
    
    public ArrayList<Integer> getCodigosReferencias(){
        ArrayList<Integer> codigos = new ArrayList<>();
        for(int i = 0; i < refs.size(); i++){
            codigos.add(refs.get(i).getCodigo());
        }
        return codigos;
    }
    
    public void actualizarLineaCompra(int i) throws SQLException {
        for (int p = 0; p < lc.size(); p++){
            if(lc.get(p).getCodigoLinea()==i){
                l = lc.get(p);
            }
        }
        l.marcarRecibido();
        lp = l.actualizaLineasPedido();
        DAOLineaPedido.actualizarLineasDePedido(l.getCodigoLinea());
    }
    
    /*
    private String pasarLineasPedidoAJsonString(ArrayList<LineaPedido> lp){
        String estadoLineaPedidoJSONString = "";
        JsonArrayBuilder array = Json.createArrayBuilder();
        for(int i=0;i<lp.size();i++){
            array.add(Json.createObjectBuilder().add("estado",Boolean.toString(lp.get(i).checkCompleto()))
            .build());
        }
        try(
                StringWriter stringWriter = new StringWriter();
                JsonWriter writer = Json.createWriter(stringWriter);
                ){   
            
            JsonObject jsonObj = Json.createObjectBuilder().add("lineasPedido",array).build();
            writer.writeObject(jsonObj);
            estadoLineaPedidoJSONString = stringWriter.toString();
        }catch(Exception ex){
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE,null,ex);
        }
        return estadoLineaPedidoJSONString;
    }*/
    
    public void comprobarRecibidas(){
        allRecvs = c.comprobarRecibidas();
        c.marcarRecibidaCompleta();
        DAOCompra.actualizarCompra(c.getIdCompra());
    }
    
    public boolean getAllRefs(){
        return allRecvs;
    }
    
    public ArrayList<Integer> getIdLineasCompraNoRecibidas(){
        ArrayList<Integer> codigos = new ArrayList<>();
        ArrayList<LineaCompra> lcnrr = c.getLineasCompraNoRecibidas();
        for(int i = 0; i < lcnrr.size(); i++){
            codigos.add(lcnrr.get(i).getCodigoLinea());
        }
        return codigos;
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
            DAOPedido.actualizaPedido(lp.get(0).getCodigoPedido());
        }
    }
}
