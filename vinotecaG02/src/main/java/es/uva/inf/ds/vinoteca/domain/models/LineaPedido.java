/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.domain.models;

import es.uva.inf.ds.vinoteca.persistence.daos.DAOAbonado;
import es.uva.inf.ds.vinoteca.persistence.daos.DAOEmpleado;
import es.uva.inf.ds.vinoteca.persistence.daos.DAOLineaPedido;
import java.io.StringReader;
import java.io.StringWriter;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import javax.json.JsonWriter;

/**
 * Modelo que representa una línea de pedido del sistema.
 * @author pamarti
 * @author alerome
 * @author ivagonz
 */
public class LineaPedido {
    
    boolean completada;
    int unidades, codigoPedido, codigoReferencia;
    
    /**
     * Constructor de la clase.
     * @param c Valor booleano que representa si la línea de pedido ha sido completada.
     * @param u Número entero que representa las unidades de la línea de pedido.
     * @param cPed Número entero que representa el identificador del pedido asociado a la línea de pedido.
     */
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
    
    /**
     * Marca la línea de pedido como completada.
     */
    public void marcarCompleto(){
        completada = true;
    }
    
    /**
     * Obtiene el número de unidades de la línea de pedido.
     * @return Número entero que representa las unidades de la línea de pedido.
     */
    public int getUnidades(){
        return unidades;
    }

    /**
     * Obtiene el código del pedido asociado a la línea de pedido.
     * @return Número entero que representa el identificador del pedido asociado a la línea de pedido.
     */
    public int getCodigoPedido(){
        return codigoPedido;
    }
    
    /**
     *  Obtiene una lista de instancias de líneas de pedido relacionadas con una línea de compra.
     * @param idLineaCompra Número entero que representa el identificador de la línea de compra.
     * @return Lista de instancias de líneas de pedido relacionadas con una línea de compra.
     */
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
                completada = Boolean.parseBoolean(obj.getString("completada"));
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

    /**
     * Comprueba si una línea de pedido ha sido completada.
     * @return {@code True} en caso de que la línea de pedido haya sido completada y {@code false} en caso contrario.
     */
    public boolean checkCompleto() {
        return completada;
    }

    /**
     * Obtiene el JSON que representa la instancia de la línea de pedido.
     * @return JSON en forma de cadena de caracteres que representa la línea de pedido.
     */
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
