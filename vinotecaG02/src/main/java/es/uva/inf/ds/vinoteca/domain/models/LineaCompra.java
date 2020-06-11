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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import javax.json.JsonValue;

/**
 *
 * @author alejandro
 */
public class LineaCompra {
    
    private int unidades, codigo;
    private String referencia;
    private boolean recibida;
    private LocalDateTime fechaRecepcion;
    private ArrayList<LineaCompra> listaLineas;
    
    public LineaCompra(int u, LocalDateTime fr, int c, boolean r){
        unidades = u;
        recibida = r;
        fechaRecepcion = fr;
        listaLineas = null;
        codigo = c;
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
        
    public void marcarRecibido(){
        recibida = true;
        fechaRecepcion = LocalDateTime.now();
    }
    
    public void actualizaLineasPedido(){
    }
    
    public Referencia getReferencia(){
        return Referencia.getReferencia(codigo);
    }
    
    //COMPLETAR
    public static ArrayList<LineaCompra> getLineaCompra(int idCompra) {
        String lineasCompraJSONString = DAOLineaCompra.consultaLineaCompra(idCompra);
        //lo que tenga el json
        int unidades = 0;
        int codigo = 0;
        LocalDateTime fechaRecepcion;
        boolean rec = false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm",Locale.US);
        ArrayList<LineaCompra> lcompras = new ArrayList<>();
        JsonReaderFactory factory = Json.createReaderFactory(null);
        try(JsonReader reader = factory.createReader(new StringReader(lineasCompraJSONString));){
            JsonObject mainObj = reader.readObject();
            JsonArray array = mainObj.getJsonArray("lineasCompra");
            //coger del json
            for(int i=0;i<array.size();i++){
                JsonObject obj = (JsonObject) array.get(i);
                unidades = Integer.parseInt(obj.getString("unidades"));
                rec = Boolean.parseBoolean("recibidas");
                fechaRecepcion = LocalDateTime.parse(obj.getString("fechaRecepcion"),formatter);
                codigo = Integer.parseInt(obj.getString("codigo"));
                LineaCompra lc = new LineaCompra(unidades, fechaRecepcion, codigo, rec);
                lcompras.add(lc);
            }
        }catch(Exception ex){
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE,null,ex);
        }
        return lcompras;
        //crear objeto lineaCompra
        //añadirlo a array retornar array
    }
    
    /*
    public boolean comprobarAllRecibidas(){
        
    }*/
}
