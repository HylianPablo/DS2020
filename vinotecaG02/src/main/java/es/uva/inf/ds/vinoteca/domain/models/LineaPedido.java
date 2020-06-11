/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.domain.models;

import es.uva.inf.ds.vinoteca.persistence.daos.DAOEmpleado;
import es.uva.inf.ds.vinoteca.persistence.daos.DAOLineaCompra;
import es.uva.inf.ds.vinoteca.persistence.daos.DAOLineaPedido;
import java.io.StringReader;
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

/**
 *
 * @author alejandro
 */
public class LineaPedido {
    
    boolean completada;
    int unidades;
    
    public LineaPedido(boolean c, int u){
        unidades = u;
        completada = c;
    }
    
    public void marcarCompleto(){
        completada = true;
    }

    
    //revisar estoooooo
    public static ArrayList<LineaPedido> getLineasPedido(int idLineaCompra) {
        String lineasPedidoJSONString = DAOLineaPedido.consultaLineasPedido(idLineaCompra);
        ArrayList<LineaPedido> lpedidos = new ArrayList<>();
        int unidades = 0;
        boolean completada = false;
        JsonReaderFactory factory = Json.createReaderFactory(null);
        try(JsonReader reader = factory.createReader(new StringReader(lineasPedidoJSONString));){
            JsonObject mainObj = reader.readObject();
            JsonArray array = mainObj.getJsonArray("lineasCompra");
            for(int i=0;i<array.size();i++){
                JsonObject obj = (JsonObject) array.get(i);
                unidades = Integer.parseInt(obj.getString("unidades"));
                completada = Boolean.parseBoolean("completada");
                LineaPedido lp = new LineaPedido(completada, unidades);
                lp.marcarCompleto();
                lpedidos.add(lp);
            }
        }catch(Exception ex){
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE,null,ex);
        }
        return lpedidos;
    }
    
}
