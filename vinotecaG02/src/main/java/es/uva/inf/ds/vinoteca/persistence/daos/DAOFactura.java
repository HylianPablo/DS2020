/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.persistence.daos;

import es.uva.inf.ds.vinoteca.domain.models.Empleado;
import es.uva.inf.ds.vinoteca.persistence.dbaccess.DBConnection;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonWriter;

/**
 *
 * @author pablo
 */
public class DAOFactura {
    
    public static String consultaFacturasAntesDeFecha(String fecha){
        String facturasJSONString = "";
        int numeroFactura = -1;
        LocalDateTime fechaEmision = null;
        double importe = -1.0;
        int estado = -1;
        LocalDateTime fechaPago = null;
        String idExtractoBancario = null;
        
        ArrayList<Integer> numerosFactura = new ArrayList<>();
        ArrayList<LocalDateTime> fechasEmision = new ArrayList<>();
        ArrayList<Double> importes = new ArrayList<>();
        ArrayList<Integer> estados = new ArrayList<>();
        ArrayList<LocalDateTime> fechasPago = new ArrayList<>();
        ArrayList<String> idExtractosBancarios = new ArrayList<>();
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        try(PreparedStatement ps = connection.getStatement("SELECT * FROM FACTURA f WHERE f.FECHAEMISION <= ?");)
        {
            ps.setString(1,fecha);
            ResultSet result = ps.executeQuery();
            while(result.next()){
                numeroFactura = result.getInt("NUMEROFACTURA");
                numerosFactura.add(numeroFactura);
                fechaEmision = result.getTimestamp("FECHAEMISION").toLocalDateTime();
                fechasEmision.add(fechaEmision);
                importe = result.getDouble("IMPORTE");
                importes.add(importe);
                estado = result.getInt("ESTADO");
                estados.add(estado);
                fechaPago = result.getTimestamp("FECHAPAGO").toLocalDateTime();
                fechasPago.add(fechaPago);
                idExtractoBancario = result.getString("IDEXTRACTOBANCARIO");
                idExtractosBancarios.add(idExtractoBancario);
            }
        }catch(SQLException ex){
            Logger.getLogger(DAOFactura.class.getName()).log(Level.SEVERE,null,ex);
        }
        connection.closeConnection();
        facturasJSONString = facturasToJSONString(numerosFactura,fechasEmision,importes,estados,fechasPago,idExtractosBancarios);
        return facturasJSONString;
    }
    
    private static String facturasToJSONString(ArrayList<Integer> numerosFactura, ArrayList<LocalDateTime> fechasEmision,
            ArrayList<Double>importes, ArrayList<Integer> estados, ArrayList<LocalDateTime> fechasPago, ArrayList<String> idExtractosBancarios){
        String facturasJSONString = "";
        JsonArrayBuilder array = Json.createArrayBuilder();
        for(int i=0;i<numerosFactura.size();i++){
            array.add(Json.createObjectBuilder().add("numeroFactura",Integer.toString(numerosFactura.get(i)))
            .add("fechaEmision",fechasEmision.get(i).toString())
            .add("importe",Double.toString(importes.get(i)))
            .add("estado",Integer.toString(estados.get(i)))
            .add("fechaPago",fechasPago.get(i).toString())
            .add("idExtractoBancario",idExtractosBancarios.get(i))
            .build());
        }
        try(
                StringWriter stringWriter = new StringWriter();
                JsonWriter writer = Json.createWriter(stringWriter);
                ){
           
            JsonObject jsonObj = Json.createObjectBuilder().add("facturas",array).build();
            writer.writeObject(jsonObj);
            facturasJSONString = stringWriter.toString();
        }catch(Exception ex){
            Logger.getLogger(DAOFactura.class.getName()).log(Level.SEVERE,null,ex);
        }
        return facturasJSONString;
    }
    
    public static String selectPedidosAsociados(int numeroFactura){ //REVISAR: POSIBLEMENTE VAYA EN DAOPEDIDO
        String pedidosJSONString = "";
        int numero = -1;
        int estado = -1;
        LocalDateTime fechaRealizacion = null;
        String notaEntrega = null;
        double importe = 0.0;
        LocalDateTime fechaRecepcion = null;
        LocalDateTime fechaEntrega = null;
        int numeroAbonado = -1;
        ArrayList<Integer> numeros = new ArrayList<>();
        ArrayList<Integer> estados = new ArrayList<>();
        ArrayList<LocalDateTime> fechasRealizacion = new ArrayList<>();
        ArrayList<String> notasEntrega = new ArrayList<>();
        ArrayList<Double> importes = new ArrayList<>();
        ArrayList<LocalDateTime> fechasRecepcion = new ArrayList<>();
        ArrayList<LocalDateTime> fechasEntrega = new ArrayList<>();
        ArrayList<Integer> numerosAbonado = new ArrayList<>();
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        try(PreparedStatement ps = connection.getStatement("SELECT * FROM PEDIDO p WHERE p.NUMEROFACTURA = ?");)
        {
            ps.setInt(1, numeroFactura);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                numero = rs.getInt("NUMERO");
                numeros.add(numero);
                estado = rs.getInt("ESTADO");
                estados.add(estado);
                fechaRealizacion = rs.getTimestamp("FECHAREALIZACION").toLocalDateTime();
                fechasRealizacion.add(fechaRealizacion);
                notaEntrega = rs.getString("NOTAENTREGA");
                notasEntrega.add(notaEntrega);
                importe = rs.getDouble("IMPORTE");
                importes.add(importe);
                fechaRecepcion = rs.getTimestamp("FECHARECEPCION").toLocalDateTime();
                fechasRecepcion.add(fechaRecepcion);
                fechaEntrega = rs.getTimestamp("FECHAENTREGA").toLocalDateTime();
                fechasEntrega.add(fechaEntrega);
                numeroAbonado = rs.getInt("NUMEROABONADO");
                numerosAbonado.add(numeroAbonado);
                
            }
        }catch(SQLException ex){
            Logger.getLogger(DAOFactura.class.getName()).log(Level.SEVERE,null,ex);
        }
        connection.closeConnection();
        pedidosJSONString = pedidosToJSONString(numeros,estados,fechasRealizacion,notasEntrega,importes,fechasRecepcion,fechasEntrega,numerosAbonado,numeroFactura);
        return pedidosJSONString;
    }
    
    
    private static String pedidosToJSONString(ArrayList<Integer> numeros, ArrayList<Integer> estados, ArrayList<LocalDateTime> fechasRealizacion,
                                ArrayList<String> notasEntrega, ArrayList<Double> importes, ArrayList<LocalDateTime> fechasRecepcion,
                                ArrayList<LocalDateTime> fechasEntrega, ArrayList<Integer> numerosAbonado, int numeroFactura){
        String pedidosJSONString = "";
        JsonArrayBuilder array = Json.createArrayBuilder();
        for(int i=0;i<numeros.size();i++){
            array.add(Json.createObjectBuilder().add("numero",Integer.toString(numeros.get(i)))
            .add("estado",Integer.toString(estados.get(i)))
            .add("fechaRealizacion",fechasRealizacion.get(i).toString())
            .add("notaEntrega",notasEntrega.get(i))
            .add("importe",Double.toString(importes.get(i)))
            .add("fechaRecepcion",fechasRecepcion.get(i).toString())
            .add("fechaEntrega",fechasEntrega.get(i).toString())
            .add("numeroAbonado",Integer.toString(numerosAbonado.get(i)))
            .add("numeroFactura",Integer.toString(numeroFactura))
            .build());
        }
        try(
                StringWriter stringWriter = new StringWriter();
                JsonWriter writer = Json.createWriter(stringWriter);
                ){
           
            JsonObject jsonObj = Json.createObjectBuilder().add("pedidos",array).build();
            writer.writeObject(jsonObj);
            pedidosJSONString = stringWriter.toString();
        }catch(Exception ex){
            Logger.getLogger(DAOFactura.class.getName()).log(Level.SEVERE,null,ex);
        }
        return pedidosJSONString;
    }
}
