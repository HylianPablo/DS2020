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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
public class Bodega {
    
    String nombre, cif, direccion;
    
    public Bodega(String n, String c, String d){
        nombre = n;
        cif = c;
        direccion = d;
    }
    
    public String getNombre(){
        return nombre;
    }   
    
    public static Bodega getBodega(int id) {
        String bodegaJSONString = DAOBodega.consultaBodega(id);
        String nombreJson = null;
        String cifJson = null;
        String direccionJson = null;
        //lo que tenga el json
        JsonReaderFactory factory = Json.createReaderFactory(null);
        try(JsonReader reader = factory.createReader(new StringReader(bodegaJSONString));){
            JsonObject jsonobject = reader.readObject();
            nombreJson = jsonobject.getString("nombre");
            cifJson = jsonobject.getString("cif");
            
            direccionJson = jsonobject.getString("direccion");
            //coger del json
        }catch(Exception ex){
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE,null,ex);
        }
        //crear objeto compra
        Bodega bodega = new Bodega(nombreJson, cifJson, direccionJson);
        
        return bodega;
    } 
}
