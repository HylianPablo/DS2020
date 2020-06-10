/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.domain.models;

import es.uva.inf.ds.vinoteca.persistence.daos.DAOAbonado;
import es.uva.inf.ds.vinoteca.persistence.daos.DAOEmpleado;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;

/**
 *
 * @author pablo
 */
public class Pedido {
    private int numero,estado, numeroFactura, numeroAbonado;
    private LocalDateTime fechaRealizacion, fechaRecepcion, fechaEntrega;
    private String notaEntrega;
    private double importe;
    
    public Pedido(int numero, int estado, LocalDateTime fechaRealizacion, String notaEntrega, double importe,
                    LocalDateTime fechaRecepcion, LocalDateTime fechaEntrega, int numeroFactura, int numeroAbonado){
        this.numero=numero;
        this.estado=estado;
        this.fechaRealizacion=fechaRealizacion;
        this.notaEntrega=notaEntrega;
        this.importe=importe;
        this.fechaRecepcion=fechaRecepcion;
        this.fechaEntrega=fechaEntrega;
        this.numeroFactura=numeroFactura;
        this.numeroAbonado=numeroAbonado;
    }
    
    public int getNumeroPedido(){
        return numero;
    }
    
    public Abonado getAbonado(){
        Abonado ab = null;
        String abonadoJSONString = DAOAbonado.consultaAbonado(numeroAbonado);
        String openidref;
        String nif;
        JsonReaderFactory factory = Json.createReaderFactory(null);
        try(JsonReader reader = factory.createReader(new StringReader(abonadoJSONString));){
            JsonObject jsonobject = reader.readObject();
            openidref = jsonobject.getString("openIdRef");
            nif = jsonobject.getString("nif");
            ab = new Abonado(numeroAbonado,openidref,nif);
        }catch(Exception ex){
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE,null,ex);
        }
        return ab;
    }
}
