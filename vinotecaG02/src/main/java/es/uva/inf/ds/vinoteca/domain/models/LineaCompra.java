/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.domain.models;

import es.uva.inf.ds.vinoteca.persistence.daos.DAOCompra;
import es.uva.inf.ds.vinoteca.persistence.daos.DAOEmpleado;
import es.uva.inf.ds.vinoteca.persistence.daos.DAOLineaCompra;
import java.io.StringReader;
import java.time.LocalDate;
import java.util.ArrayList;
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
public class LineaCompra {
    
    private int unidades;
    private String referencia;
    private boolean recibida;
    private LocalDate fechaRecepcion;
    private ArrayList<LineaCompra> listaLineas;
    
    public LineaCompra(int u, String r){
        unidades = u;
        referencia = r;
        recibida = false;
        fechaRecepcion = null;
        listaLineas = null;
    }
    
    public void add(LineaCompra linea){
        listaLineas.add(linea);
    }
    
    public int getNumeroLineas(){
        return listaLineas.size();
    }
    
    public boolean comprobarRecibida() {
        return recibida;
    }
    
    public int getUnidades(){
        return unidades;
    }
    
    public String getReferencia(){
        return referencia;
    }
    
    public void marcarRecibido(){
        recibida = true;
        fechaRecepcion = LocalDate.now();
    }
    
    public void actualizaLineasPedido(){
    }
    
    //COMPLETAR
    public static ArrayList<LineaCompra> getLineaCompra(int idCompra) {
        String lineasCompraJSONString = DAOLineaCompra.consultaLineaCompra(idCompra);
        //lo que tenga el json
        JsonReaderFactory factory = Json.createReaderFactory(null);
        try(JsonReader reader = factory.createReader(new StringReader(lineasCompraJSONString));){
            JsonObject jsonobject = reader.readObject();
            //coger del json
        }catch(Exception ex){
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE,null,ex);
        }
        //crear objeto lineaCompra
        //añadirlo a array retornar array
    }
    
    /*
    public boolean comprobarAllRecibidas(){
        
    }*/
}
