package es.uva.inf.ds.vinoteca.domain.models;

import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import javax.json.JsonReaderFactory;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import es.uva.inf.ds.vinoteca.persistence.daos.DAOPedido;

import javax.json.JsonReader;
import javax.json.Json;
import javax.json.JsonObject;

import es.uva.inf.ds.vinoteca.persistence.daos.DAOAbonado;

/**
 * @author Ivan
 */
public class Abonado {

    private int numeroAbonado;
    private String openidref, nif;

    public Abonado(int numeroAbonado, String openidref, String nif) {
        this.numeroAbonado = numeroAbonado;
        this.openidref = openidref;
        this.nif = nif;
    }

    public static Abonado getAbonado(int numeroAbonado) {

        Abonado ab = null;
        String abonadoJSONString = DAOAbonado.consultaAbonado(numeroAbonado);
        String openidref;
        String nif;
        JsonReaderFactory factory = Json.createReaderFactory(null);
        try (JsonReader reader = factory.createReader(new StringReader(abonadoJSONString));) {
            JsonObject jsonobject = reader.readObject();
            openidref = jsonobject.getString("openIdRef");
            nif = jsonobject.getString("nif");
            ab = new Abonado(numeroAbonado, openidref, nif);
        } catch (Exception ex) {
            Logger.getLogger(DAOAbonado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ab;

    }

    public ArrayList<Pedido> getPedidos(){
        String pedidosJSONString = "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm",Locale.US);
        int numeroJ;
        int estadoJ;
        LocalDateTime fechaRealizacionJ;
        String notaEntregaJ;
        double importeJ;
        LocalDateTime fechaRecepcionJ;
        LocalDateTime fechaEntregaJ;
        int numeroAbonadoJ;
        ArrayList<Pedido> pedidos = new ArrayList<>();
        
        pedidosJSONString = DAOFactura.selectPedidosAsociados(this.numeroFactura);
        JsonReaderFactory factory = Json.createReaderFactory(null);
        try(JsonReader reader = factory.createReader(new StringReader(pedidosJSONString));){
            JsonObject mainObj = reader.readObject();
            JsonArray array = mainObj.getJsonArray("pedidos");
            for(int i =0; i<array.size();i++){
                JsonObject obj = (JsonObject) array.getJsonObject(i);
                numeroJ = Integer.parseInt(obj.getString("numero"));
                estadoJ = Integer.parseInt(obj.getString("estado"));
                fechaRealizacionJ = LocalDateTime.parse(obj.getString("fechaRealizacion"),formatter);
                notaEntregaJ = obj.getString("notaEntrega");
                importeJ = Double.parseDouble(obj.getString("importe"));
                fechaRecepcionJ = LocalDateTime.parse(obj.getString("fechaRecepcion"),formatter);
                fechaEntregaJ = LocalDateTime.parse(obj.getString("fechaEntrega"),formatter);
                numeroAbonadoJ = Integer.parseInt(obj.getString("numeroAbonado"));
                Pedido p = new Pedido(numeroJ,estadoJ,fechaRealizacionJ,notaEntregaJ,importeJ,fechaRecepcionJ,fechaEntregaJ,this.numeroFactura,numeroAbonadoJ);
                pedidos.add(p);
            }
        }catch(Exception ex){
            Logger.getLogger(Factura.class.getName()).log(Level.SEVERE,null,ex);
        }
        return pedidos;

        DAOPedido.consultaPedidos(String.valueOf(numeroAbonado));
        return null;
    }
    public int getNumeroAbonado() {
        return numeroAbonado;
    }

    public void setNumeroAbonado(int numeroAbonado) {
        this.numeroAbonado = numeroAbonado;
    }

    public String getOpenIdRef() {
        return openidref;
    }

    public void setOpenIdRef(String openidref) {
        this.openidref = openidref;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

}
