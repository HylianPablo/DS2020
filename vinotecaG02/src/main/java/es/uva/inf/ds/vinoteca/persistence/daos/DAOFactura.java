package es.uva.inf.ds.vinoteca.persistence.daos;

import es.uva.inf.ds.vinoteca.persistence.dbaccess.DBConnection;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonWriter;

/**
 * Clase que representa el acceso a la tabla de la base de datos que contiene los datos de las facturas del sistema.
 * @author pamarti
 * @author alerome
 * @author ivagonz
 */
public class DAOFactura {
    
    /**
     * Consulta las facturas emitidas antes de una determinada fecha.
     * @param fecha Cadena de caracteres que representa la fecha a partir de la cual se obtendrán las facturas.
     * @return JSON en forma de cadena de caracteres que representa las facturas que se hayan emitido anteriormente a la fecha introducida como parámetro. En caso de no existir ninguna devuelve cadena vacía.
     */
    public static String consultaFacturasAntesDeFecha(String fecha){
        String facturasJSONString = "";
        int numeroFactura = -1;
        LocalDateTime fechaEmision = null;
        double importe = -1.0;
        int estado = -1;
        LocalDateTime fechaPago = null;
        String idExtractoBancario = null;
        int counter =0;
        
        ArrayList<Integer> numerosFactura = new ArrayList<>();
        ArrayList<LocalDateTime> fechasEmision = new ArrayList<>();
        ArrayList<Double> importes = new ArrayList<>();
        ArrayList<Integer> estados = new ArrayList<>();
        ArrayList<LocalDateTime> fechasPago = new ArrayList<>();
        ArrayList<String> idExtractosBancarios = new ArrayList<>();
        Timestamp timestamp = null;
        
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = dateFormat.parse(fecha);
            timestamp = new java.sql.Timestamp(parsedDate.getTime());
        } catch(Exception e) { //this generic but you can control another types of exception
    // look the origin of excption 
        }
                
                
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        try(PreparedStatement ps = connection.getStatement("SELECT * FROM FACTURA f WHERE f.FECHAEMISION < ?");)
        {
            ps.setTimestamp(1,timestamp);
            ResultSet result = ps.executeQuery();
            while(result.next()){
                counter++;
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
        if(counter!=0)
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
    
    public static boolean comprobarNoVencido(int numeroFactura){
        int estadoFactura = -1;
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        try(PreparedStatement ps = connection.getStatement("SELECT * FROM FACTURA f WHERE f.NUMEROFACTURA = ?");)
        {
            ps.setInt(1,numeroFactura);
            ResultSet result = ps.executeQuery();
            if(result.next()){
                estadoFactura = result.getInt("ESTADO");
            }
            
        }catch(SQLException ex){
            Logger.getLogger(DAOFactura.class.getName()).log(Level.SEVERE,null,ex);
        }
        connection.closeConnection();
        return (estadoFactura!=2);
    }
    
    
}
