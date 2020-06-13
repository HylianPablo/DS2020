package es.uva.inf.ds.vinoteca.persistence.daos;

import es.uva.inf.ds.vinoteca.persistence.dbaccess.DBConnection;
import es.uva.inf.ds.vinoteca.userinterface.VistaAlmacen;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonWriter;

/**
 * Clase que representa el acceso a la tabla de la base de datos que contiene los datos de los pedidos del sistema.
 * @author pamarti
 * @author alerome
 * @author ivagonz
 */
public class DAOPedido {
    
    /**
     * Devuelve los pedidos asociados a una factura determinada.
     * @param numeroFactura Número entero que representa la factura de la que se quiere obtener los pedidos.
     * @return JSON en forma de cadena de caracteres que representa los pedidos asociados a la factura. No se contempla la posibilidad de facturas sin pedidos.
     */
    public static String selectPedidosAsociados(int numeroFactura){
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
        
        int counter=0;
        connection.openConnection();
        try(PreparedStatement ps = connection.getStatement("SELECT * FROM PEDIDO p WHERE p.NUMEROFACTURA = ?");)
        {
            ps.setInt(1, numeroFactura);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                counter++;
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
        if(counter!=0)
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

    public static String consultaPedido(int codigoLineaPedido){ //REVISAR: POSIBLEMENTE VAYA EN DAOPEDIDO
        String pedidoJSONString = "";
        int numero = -1;
        int estado = -1;
        LocalDateTime fechaRealizacion = null;
        String notaEntrega = null;
        double importe = 0.0;
        LocalDateTime fechaRecepcion = null;
        LocalDateTime fechaEntrega = null;
        int numeroAbonado = -1;
        int numeroFactura = -1;
        DBConnection connection = DBConnection.getInstance();  
        int counter=0;
        connection.openConnection();
        try(PreparedStatement ps = connection.getStatement("SELECT * FROM PEDIDO p WHERE p.NUMERO = ?");)
        {
            ps.setInt(1, codigoLineaPedido);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                counter++;
                numero = rs.getInt("NUMERO");
                estado = rs.getInt("ESTADO");
                fechaRealizacion = rs.getTimestamp("FECHAREALIZACION").toLocalDateTime();
                notaEntrega = rs.getString("NOTAENTREGA");
                importe = rs.getDouble("IMPORTE");
                fechaRecepcion = rs.getTimestamp("FECHARECEPCION").toLocalDateTime();
                fechaEntrega = rs.getTimestamp("FECHAENTREGA").toLocalDateTime();
                numeroAbonado = rs.getInt("NUMEROABONADO");    
                numeroFactura = rs.getInt("NUMEROFACTURA");
            }
        }catch(SQLException ex){
            Logger.getLogger(DAOFactura.class.getName()).log(Level.SEVERE,null,ex);
        }
        connection.closeConnection();
        if(counter!=0)
            pedidoJSONString = pedidoToJSONString(numero,estado,fechaRealizacion,notaEntrega,importe,fechaRecepcion,fechaEntrega,numeroAbonado,codigoLineaPedido, numeroFactura);
        return pedidoJSONString;
    }
    
    private static String pedidoToJSONString(int numero, int estado, LocalDateTime fechaRealizacion,
                                String notaEntrega, double importe, LocalDateTime fechaRecepcion,
                                LocalDateTime fechaEntrega, int numeroAbonado, int codigoLineaPedido,
                                int numeroFactura){
        String pedidoJSONString = "";
        JsonObject compraJson = Json.createObjectBuilder()
            .add("numero",Integer.toString(numero))
            .add("estado",Integer.toString(estado))
            .add("fechaRealizacion",fechaRealizacion.toString())
            .add("notaEntrega",notaEntrega)
            .add("importe",Double.toString(importe))
            .add("fechaRecepcion",fechaRecepcion.toString())
            .add("fechaEntrega",fechaEntrega.toString())
            .add("numeroAbonado",Integer.toString(numeroAbonado))
            .add("numeroFactura",Integer.toString(numeroFactura))
            .add("codigoLineaPedido",Integer.toString(codigoLineaPedido))
            .build();
                try(
                StringWriter stringWriter = new StringWriter();
                JsonWriter writer = Json.createWriter(stringWriter);
                ){
           
            writer.writeObject(compraJson);
            pedidoJSONString = stringWriter.toString();
        }catch(Exception ex){
            Logger.getLogger(DAOFactura.class.getName()).log(Level.SEVERE,null,ex);
        }
        return pedidoJSONString;
    }

    public static void actualizaPedido(int idPedido) {
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        try (PreparedStatement ps = connection.getStatement("UPDATE PEDIDO SET ESTADO = ? WHERE NUMERO = ?")) {
            ps.setInt(1, 2);
            ps.setInt(2, idPedido);
            ps.executeUpdate();
        }
        catch (SQLException ex) {
            Logger.getLogger(VistaAlmacen.class.getName()).log(Level.SEVERE, null, ex);
        }
        connection.closeConnection();
    }
    
}
