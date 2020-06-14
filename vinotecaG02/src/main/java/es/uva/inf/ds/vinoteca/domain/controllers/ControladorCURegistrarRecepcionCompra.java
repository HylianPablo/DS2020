/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.domain.controllers;

import es.uva.inf.ds.vinoteca.common.CompletadaException;
import es.uva.inf.ds.vinoteca.common.NullCompraException;
import es.uva.inf.ds.vinoteca.common.ReferenciaNoValidaException;
import es.uva.inf.ds.vinoteca.domain.models.Bodega;
import es.uva.inf.ds.vinoteca.domain.models.Compra;
import es.uva.inf.ds.vinoteca.domain.models.Referencia;
import es.uva.inf.ds.vinoteca.domain.models.LineaCompra;
import es.uva.inf.ds.vinoteca.domain.models.LineaPedido;
import es.uva.inf.ds.vinoteca.domain.models.Pedido;
import es.uva.inf.ds.vinoteca.persistence.daos.DAOCompra;
import es.uva.inf.ds.vinoteca.persistence.daos.DAOLineaPedido;
import es.uva.inf.ds.vinoteca.persistence.daos.DAOPedido;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alejandro
 */
public class ControladorCURegistrarRecepcionCompra {
    
    private Compra c ;
    private Bodega b ;
    private Pedido p ;
    private LineaCompra l;
    private Referencia r;
    private boolean bandera;
    private ArrayList<LineaCompra> lc;
    private ArrayList<LineaCompra> lcnr;
    private ArrayList<Referencia> refs;
    private ArrayList<LineaPedido> lp;
    private int id;
    private boolean allRecvs;
    //Referencia r;
        
    public static ControladorCURegistrarRecepcionCompra getController(){
        return new ControladorCURegistrarRecepcionCompra();
    }
    
    public Compra comprobarCompraNoCompletada(int idCompra) throws ReferenciaNoValidaException, CompletadaException {
        ArrayList<LineaPedido> lpedidos = LineaPedido.getLineasPedido(1);
        for (int i = 0; i < lpedidos.size(); i++){
            System.out.println(lpedidos.get(i).checkCompleto());
        }
        refs = new ArrayList<>();
        bandera = false;
        c = null;
        try{
            c = Compra.getCompra(idCompra);
        }catch(NullCompraException ex){
            Logger.getLogger(ControladorCURegistrarRecepcionCompra.class.getName()).log(Level.SEVERE,null,ex);
        }
        if(c!=null){
            try{
                c.compruebaCompletada();
            }catch(CompletadaException ex){
                bandera = true;
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
        return c;
    }
    
    public boolean comprobarCompraCompletada(){
        return bandera;
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
        for (int x = 0; x < lc.size(); x++){
            if(lc.get(x).getCodigoLinea()==i){
                l = lc.get(x);
            }
        }
        l.marcarRecibido();
        lp = l.actualizaLineasPedido();
        DAOLineaPedido.actualizarLineasDePedido(l.getCodigoLinea());
    }
    
    public void comprobarRecibidas(){
        allRecvs = c.comprobarRecibidas(lc);
        if(allRecvs){
            c.marcarRecibidaCompleta();
            DAOCompra.actualizarCompra(c.getIdCompra());
        }
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
        boolean temporaBool = true;
        for(int i = 0; i < lp.size(); i++){
            boolean completo = lp.get(i).checkCompleto();
            if(completo){
            } else {
                temporaBool = false;
            }
        }
        if(temporaBool){
            p = Pedido.getPedido(lp.get(0).getCodigoPedido());
            p.actualizarEstadoACompletado();
            DAOPedido.actualizaPedido(lp.get(0).getCodigoPedido());
        }
    }
}
