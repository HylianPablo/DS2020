/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.domain.models;

import es.uva.inf.ds.vinoteca.common.CompletadaException;
import es.uva.inf.ds.vinoteca.common.NullCompraException;
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
    private ArrayList<LineaCompra> lineasCompraNoRecibidas;
    private boolean recibidaCompleta;
    private LocalDateTime fechaCompraCompleta, fechaPago;
    private int idCompra;
    private double importe;
    
    public Compra(int id, double i, LocalDateTime fp){
        lineasCompra = new ArrayList<>();
        importe = i;
        bodega = null;
        recibidaCompleta = false;
        fechaCompraCompleta = null;
        idCompra = id;
        fechaPago = fp;
        lineasCompraNoRecibidas = new ArrayList<>();
    }
    
    public int getIdCompra(){
        return idCompra;
    }
    
    public static Compra getCompra(int id) throws NullCompraException, CompletadaException  {
        String compraJSONString = DAOCompra.consultaCompra(id);
        String importeJson=null; 
        String fechaJson=null; 
        String completaJson=null;
        JsonReaderFactory factory = Json.createReaderFactory(null);
        try(JsonReader reader = factory.createReader(new StringReader(compraJSONString));){
            JsonObject jsonobject = reader.readObject();
            importeJson = jsonobject.getString("importe");
            fechaJson = jsonobject.getString("fechaPago");
            completaJson = jsonobject.getString("completa");
        }catch(Exception ex){
            throw new NullCompraException("La compra no se encuentra en el sistema");            
        }
        if(completaJson.equals("1")){
            throw new CompletadaException("La compra ya esta completada");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm",Locale.US);
        LocalDateTime fechaPago = LocalDateTime.parse(fechaJson,formatter);
        double importe = Double.parseDouble(importeJson);
        Compra compra = new Compra(id, importe, fechaPago);
        return compra;
    }
    
    public Bodega getBodega(){
        return Bodega.getBodega(idCompra);
    }    
    
    public void marcarRecibidaCompleta(){
        recibidaCompleta = true;
        fechaCompraCompleta = LocalDateTime.now();
    }
    
    public ArrayList<LineaCompra> getLineasCompra(){
        lineasCompra = LineaCompra.getLineaCompra(idCompra);
        return lineasCompra;
    }
    
    public boolean compruebaCompletado(){
        return recibidaCompleta;
    }
    
    public ArrayList<LineaCompra> getLineasCompraNoRecibidas(){
        return lineasCompraNoRecibidas;
    }
    
    public boolean comprobarRecibidas() {
        boolean recibida = true;
        boolean bandera = true;
        for (int i = 0; i < lineasCompra.size(); i++){
            recibida = lineasCompra.get(i).comprobarRecibida();
            if (recibida != true){
                bandera = false;
                lineasCompraNoRecibidas.add(lineasCompra.get(i));
            }
        }
        return bandera;
    }
}
