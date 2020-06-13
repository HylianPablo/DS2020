/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.domain.models;

import es.uva.inf.ds.vinoteca.persistence.daos.DAOAbonado;
import es.uva.inf.ds.vinoteca.persistence.daos.DAOEmpleado;
import es.uva.inf.ds.vinoteca.persistence.daos.DAOLineaCompra;
import es.uva.inf.ds.vinoteca.persistence.daos.DAOLineaPedido;
import java.io.StringReader;
import java.io.StringWriter;
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
import javax.json.JsonWriter;

/**
 *
 * @author alejandro
 */
public class LineaPedido {
    
    boolean completada;
    int unidades, codigoPedido, codigoReferencia;
    
    public LineaPedido(boolean c, int u, int cPed){
        unidades = u;
        completada = c;
        codigoPedido = cPed;
    }
    
    public LineaPedido(int cRef, int cPed, int u){
        unidades = u;
        codigoReferencia = cRef;
        codigoPedido = cPed;
    }
    
    public void marcarCompleto(){
        completada = true;
    }
    
    public int getCodigoPedido(){
        return codigoPedido;
    }
    
    //revisar estoooooo
    public static ArrayList<LineaPedido> getLineasPedido(int idLineaCompra) {
        String lineasPedidoJSONString = DAOLineaPedido.consultaLineasPedido(idLineaCompra);
        ArrayList<LineaPedido> lpedidos = new ArrayList<>();
        int unidades = 0;
        int codigo = 0;
        boolean completada = false;
        JsonReaderFactory factory = Json.createReaderFactory(null);
        try(JsonReader reader = factory.createReader(new StringReader(lineasPedidoJSONString));){
            JsonObject mainObj = reader.readObject();
            JsonArray array = mainObj.getJsonArray("lineasCompra");
            for(int i=0;i<array.size();i++){
                JsonObject obj = (JsonObject) array.get(i);
                unidades = Integer.parseInt(obj.getString("unidades"));
                completada = Boolean.parseBoolean("completada");
                codigo = Integer.parseInt(obj.getString("numeros"));
                LineaPedido lp = new LineaPedido(completada, unidades, codigo);
                lp.marcarCompleto();
                lpedidos.add(lp);
            }
        }catch(Exception ex){
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE,null,ex);
        }
        return lpedidos;
    }

    public boolean checkCompleto() {
        return completada;
    }

    public String getJson() {
        String newLineaPedidoJSONString = "";
        JsonObject abonadoJSON = Json.createObjectBuilder()
                .add("codReferencia",Integer.toString(codigoReferencia))
                .add("codPedido",Integer.toString(codigoPedido))
                .add("unidades",Integer.toString(unidades)).build();
        try(
                StringWriter stringWriter = new StringWriter();
                JsonWriter writer = Json.createWriter(stringWriter);
                ){
           
            writer.writeObject(abonadoJSON);
            newLineaPedidoJSONString = stringWriter.toString();
        }catch(Exception ex){
            Logger.getLogger(DAOAbonado.class.getName()).log(Level.SEVERE,null,ex);
        }
        return newLineaPedidoJSONString;
    }
    
}
