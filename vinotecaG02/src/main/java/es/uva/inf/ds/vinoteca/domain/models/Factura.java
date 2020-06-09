/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.domain.models;

import es.uva.inf.ds.vinoteca.persistence.daos.DAOEmpleado;
import es.uva.inf.ds.vinoteca.persistence.daos.DAOFactura;
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
 * @author pablo
 */
public class Factura {
    private int numeroFactura,estado;
    private LocalDateTime fechaEmision,fechaPago;
    private double importe;
    private String idExtractoBancario;
    
    public Factura(int numeroFactura, LocalDateTime fechaEmision, double importe, int estado, LocalDateTime fechaPago,
                    String idExtractoBancario){
        this.numeroFactura=numeroFactura;
        this.fechaEmision = fechaEmision;
        this.importe = importe;
        this.estado=estado;
        this.fechaPago=fechaPago;
        this.idExtractoBancario=idExtractoBancario;
    }
    
    public ArrayList<Pedido> getPedidosAsociados(){
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
            JsonArray array = reader.readArray();
            for(int i =0; i<array.size();i++){
                JsonObject obj = (JsonObject) array.getJsonObject(i);
                numeroJ = Integer.parseInt(obj.getString("numero"));
                estadoJ = Integer.parseInt(obj.getString("estado"));
                fechaRealizacionJ = LocalDateTime.parse(obj.getString("fechaRealizacion"),formatter);
                notaEntregaJ = obj.getString("notaEntrega");
                importeJ = Double.parseDouble(obj.getString("importe"));
                fechaRecepcionJ = LocalDateTime.parse(obj.getString("fechaRecepcion"),formatter);
                fechaEntregaJ = LocalDateTime.parse(obj.getString("fechaEntrega"),formatter);
                numeroAbonadoJ = Integer.parseInt("numeroAbonado");
                Pedido p = new Pedido(numeroJ,estadoJ,fechaRealizacionJ,notaEntregaJ,importeJ,fechaRecepcionJ,fechaEntregaJ,this.numeroFactura,numeroAbonadoJ);
                pedidos.add(p);
            }
        }catch(Exception ex){
            Logger.getLogger(Factura.class.getName()).log(Level.SEVERE,null,ex);
        }
        return pedidos;
    }
    
    //Hacer setters y getters
    
    public static ArrayList<Factura> consultaFacturasAntesDeFecha(String fecha){
        ArrayList<Factura> facturas = new ArrayList<>();
        int numeroFactura;
        LocalDateTime fechaEmision;
        double importe;
        int estado;
        LocalDateTime fechaPago;
        String idExtractoBancario;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm",Locale.US);
        
        String facturasJSONString = DAOFactura.consultaFacturasAntesDeFecha(fecha);
        JsonReaderFactory factory = Json.createReaderFactory(null);
        try(JsonReader reader = factory.createReader(new StringReader(facturasJSONString));){
            JsonArray array = reader.readArray();
            for(int i=0;i<array.size();i++){
                JsonObject obj = (JsonObject) array.get(i);
                numeroFactura = Integer.parseInt(obj.getString("numeroFactura"));
                fechaEmision = LocalDateTime.parse(obj.getString("fechaEmision"),formatter);
                importe = Double.parseDouble(obj.getString("importe"));
                estado = Integer.parseInt(obj.getString("estado"));
                fechaPago = LocalDateTime.parse(obj.getString("fechaPago"),formatter);
                idExtractoBancario = obj.getString("idExtractoBancario");
                Factura f = new Factura(numeroFactura,fechaEmision,importe,estado,fechaPago,idExtractoBancario);
                facturas.add(f);
            }
        }catch(Exception ex){
            Logger.getLogger(Factura.class.getName()).log(Level.SEVERE,null,ex);
        }
        return facturas;
    }
}
