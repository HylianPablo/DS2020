/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.domain.models;

import es.uva.inf.ds.vinoteca.common.ReferenciaNoDisponibleException;
import es.uva.inf.ds.vinoteca.persistence.daos.DAOCompra;
import es.uva.inf.ds.vinoteca.persistence.daos.DAOEmpleado;
import es.uva.inf.ds.vinoteca.persistence.daos.DAOReferencia;
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
public class Referencia {
    
    boolean esPorCajas, disponible;
    int codigo, contenidoEnCL;
    double precio;
    
    public Referencia(int c, int ce, double p, boolean ep, boolean d){
        codigo = c;
        contenidoEnCL = ce;
        precio = p;
        esPorCajas = ep;
        disponible = d;
    }
    
    public int getCodigo(){
        return codigo;
    }
    
    public int getContenido(){
        return contenidoEnCL;
    }
    
    public double getPrecio(){
        return precio;
    }
    
    public boolean comprobarPorCajas(){
        return esPorCajas;
    }
    
    public boolean comprobarDisponible(){
        return disponible;
    }

    public static Referencia getReferencia(int codigo) {
        String referenciaJSONString = DAOReferencia.consultaReferencia(codigo);
        //lo que tenga el json
        String precioJson=null; 
        String codigoJson=null;
        String diponibleJson=null;
        String esPorCajasJson=null;
        String contenidoEnCLJson=null;
        JsonReaderFactory factory = Json.createReaderFactory(null);
        try(JsonReader reader = factory.createReader(new StringReader(referenciaJSONString));){
            JsonObject jsonobject = reader.readObject();
            precioJson = jsonobject.getString("precio");
            diponibleJson = jsonobject.getString("disponible");
            esPorCajasJson = jsonobject.getString("porCajas");
            contenidoEnCLJson = jsonobject.getString("contenido");
            //coger del json
        }catch(Exception ex){
            //throw new ReferenciaNoDisponibleException("No existe una referencia con esa id");
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE,null,ex);
        }
        //crear objeto compra
        double precio = Double.parseDouble(precioJson);
        int contenido = Integer.parseInt(contenidoEnCLJson);
        boolean esPorCajas = Boolean.parseBoolean(esPorCajasJson);
        boolean disponible = Boolean.parseBoolean(diponibleJson);
        Referencia referencia = new Referencia(codigo, contenido, precio, esPorCajas, disponible);
        return referencia;
    }
    
}
