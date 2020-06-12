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
    private LocalDateTime fechaCompraCompleta, fechaPago;
    private int idCompra;
    private double importe;
    
    public Compra(int id, double i, LocalDateTime fp){
        lineasCompra = null;
        importe = i;
        bodega = null;
        recibidaCompleta = false;
        fechaCompraCompleta = null;
        idCompra = id;
        fechaPago = fp;
    }
    
    public int getIdCompra(){
        return idCompra;
    }
    
    public static Compra getCompra(int id) {
        String compraJSONString = DAOCompra.consultaCompra(id);
        System.out.println("que pasa=");
        String importeJson=null; 
        String fechaJson=null; 
        JsonReaderFactory factory = Json.createReaderFactory(null);
        try(JsonReader reader = factory.createReader(new StringReader(compraJSONString));){
            JsonObject jsonobject = reader.readObject();
            importeJson = jsonobject.getString("importe");
            System.out.println("que pasa=" + importeJson);
            fechaJson = jsonobject.getString("fechaPago");
        }catch(Exception ex){
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE,null,ex);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm",Locale.US);
        LocalDateTime fechaPago = LocalDateTime.parse(fechaJson,formatter);
        double importe = Double.parseDouble(importeJson);
        Compra compra = new Compra(id, importe, fechaPago);
        return compra;
    }
    
    public Bodega getBodega(){
        System.out.println("123123213123");
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

    public boolean comprobarRecibidas() {
        boolean recibida = true;
        for (int i = 0; i < lineasCompra.size(); i++){
            recibida = lineasCompra.get(i).comprobarRecibida();
            if (recibida != true){
                return false;
            }
        }
        return true;
    }
}
