/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.domain.models;

import es.uva.inf.ds.vinoteca.persistence.daos.DAOBodega;
import es.uva.inf.ds.vinoteca.persistence.daos.DAOCompra;
import es.uva.inf.ds.vinoteca.persistence.daos.DAOEmpleado;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;

/**
 *
 * @author alejandro
 */
public class Compra {
    
    private Bodega bodega;
    private ArrayList<LineaCompra> lineasCompra;
    private boolean recibidaCompleta;
    private LocalDate fechaCompraCompleta, fechaPago;
    private int idCompra;
    
    public Compra(ArrayList<LineaCompra> lc, Bodega b, int id){
        lineasCompra = lc;
        bodega = b;
        recibidaCompleta = false;
        fechaCompraCompleta = null;
        fechaPago = null;  
        idCompra = id;
    }
    
    public Bodega getBodega(){
        //return Bodega.
        // ? ??  ? ? ? ?
        return Bodega.getBodega(idCompra);
    }
    
    public boolean comprobarRecibidas(){
        for(int i = 0; i < LineaCompra.getNumeroLineas(); i++){
            if (!LineaCompra.comprobarRecibida()){
                return false;
            }
        }
        return true;        
    }
    
    
    public void marcarRecibidaCompleta(){
        recibidaCompleta = true;
        fechaCompraCompleta = LocalDate.now();
    }
    
    //SERIA MEJOR PASAR DIRECTAMENTE IDCOMPRA Y QUE LA FUNCION NO TUVIERA PARAMETROS
    public ArrayList<LineaCompra> getLineasCompra(){
        return LineaCompra.getLineaCompra(idCompra);
    }
    
    public boolean compruebaCompletado(){
        return recibidaCompleta;
    }
    
    /*
    public ArrayList<LineaCompra> getLineasCompra(int idCompra){
        return Compra;
    }*/

    //COMPLETAR
    public Compra getCompra(int id) {
        String compraJSONString = DAOCompra.consultaCompra(id);
        //lo que tenga el json
        JsonReaderFactory factory = Json.createReaderFactory(null);
        try(JsonReader reader = factory.createReader(new StringReader(compraJSONString));){
            JsonObject jsonobject = reader.readObject();
            //coger del json
        }catch(Exception ex){
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE,null,ex);
        }
        //crear objeto compra
        Compra compra = new Compra(loginJson,nifJson,passJson,fechaInicioLDT,tipoEmpleadoJson);
        return compra;
    }
}
