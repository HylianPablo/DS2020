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
    
    private int unidades, codigo, codigoLinea;
    private String referencia;
    private boolean recibida;
    private LocalDateTime fechaRecepcion;
    
    public LineaCompra(int u, LocalDateTime fr, int c, boolean r, int cL){
        unidades = u;
        recibida = r;
        fechaRecepcion = fr;
        codigo = c;
        codigoLinea = cL;
    }
        
    public ArrayList<LineaPedido> actualizaLineasPedido(){
        return LineaPedido.getLineasPedido(codigo);
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
    
    public Referencia getReferencia(){
        return Referencia.getReferencia(codigo);
    }
    
    public int getCodigoLinea(){
        return codigoLinea;
    }
    
    //COMPLETAR
    public static ArrayList<LineaCompra> getLineaCompra(int idCompra) {
        String lineasCompraJSONString = DAOLineaCompra.consultaLineaCompra(idCompra);
        int unidades = 0;
        int codigo = 0;
        int codigoL = 0;
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
                codigo = Integer.parseInt(obj.getString("codigos"));
                codigoL = Integer.parseInt(obj.getString("ids"));
                LineaCompra lc = new LineaCompra(unidades, fechaRecepcion, codigo, rec, codigoL);
                lcompras.add(lc);
            }
        }catch(Exception ex){
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE,null,ex);
        }
        return lcompras;
    }
}
