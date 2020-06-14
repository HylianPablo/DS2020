package es.uva.inf.ds.vinoteca.persistence.daos;

import es.uva.inf.ds.vinoteca.domain.models.Empleado;
import es.uva.inf.ds.vinoteca.persistence.dbaccess.DBConnection;
import es.uva.inf.ds.vinoteca.userinterface.VistaAlmacen;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
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
            Logger.getLogger(DAOPedido.class.getName()).log(Level.SEVERE,null,ex);
        }
        connection.closeConnection();
        if(counter!=0)
            pedidosJSONString = pedidosToJSONStringP(numeros,estados,fechasRealizacion,notasEntrega,importes,fechasRecepcion,fechasEntrega,numerosAbonado,numeroFactura);
        return pedidosJSONString;
    }
    
    /**
     * Devuelve los pedidos asociados a una abonado determinado.
     * @param numeroFactura Número entero que representa la factura de la que se quiere obtener los pedidos.
     * @return JSON en forma de cadena de caracteres que representa los pedidos asociados a la factura. No se contempla la posibilidad de facturas sin pedidos.
     */
    public static String consultaPedidoAbonado(int numeroAbonado){
        String pedidosJSONstring="";
        String pedidosJSONString = "";
        int numero = -1;
        int estado = -1;
        LocalDateTime fechaRealizacion = null;
        String notaEntrega = null;
        double importe = 0.0;
        LocalDateTime fechaRecepcion = null;
        LocalDateTime fechaEntrega = null;
        int numeroFactura = -1;
        ArrayList<Integer> numeros = new ArrayList<>();
        ArrayList<Integer> estados = new ArrayList<>();
        ArrayList<LocalDateTime> fechasRealizacion = new ArrayList<>();
        ArrayList<String> notasEntrega = new ArrayList<>();
        ArrayList<Double> importes = new ArrayList<>();
        ArrayList<LocalDateTime> fechasRecepcion = new ArrayList<>();
        ArrayList<LocalDateTime> fechasEntrega = new ArrayList<>();
        ArrayList<Integer> numerosFactura = new ArrayList<>();
        DBConnection connection = DBConnection.getInstance();
        
        int counter=0;
        connection.openConnection();
        try(PreparedStatement ps = connection.getStatement("SELECT * FROM PEDIDO p WHERE p.NUMEROABONADO = ? AND p.FECHARECEPCION IS NOT NULL");)
        {
            ps.setInt(1, numeroAbonado);
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
                numeroFactura = rs.getInt("NUMEROFACTURA");
                numerosFactura.add(numeroFactura);
                
            }
        }catch(SQLException ex){
            Logger.getLogger(DAOPedido.class.getName()).log(Level.SEVERE,null,ex);
        }
        connection.closeConnection();
        if(counter!=0)
            pedidosJSONString = pedidosToJSONStringA(numeros,estados,fechasRealizacion,notasEntrega,importes,fechasRecepcion,fechasEntrega,numerosFactura,numeroAbonado);
        return pedidosJSONString;
    }
    
    private static String pedidosToJSONStringP(ArrayList<Integer> numeros, ArrayList<Integer> estados, ArrayList<LocalDateTime> fechasRealizacion,
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
    
    private static String pedidosToJSONStringA(ArrayList<Integer> numeros, ArrayList<Integer> estados, ArrayList<LocalDateTime> fechasRealizacion,
                                ArrayList<String> notasEntrega, ArrayList<Double> importes, ArrayList<LocalDateTime> fechasRecepcion,
                                ArrayList<LocalDateTime> fechasEntrega, ArrayList<Integer> numerosFactura, int numeroAbonado){
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
            .add("numeroAbonado",Integer.toString(numeroAbonado))
            .add("numeroFactura",Integer.toString(numerosFactura.get(i)))
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

     /**
     * Devuelve el pedido asociado a una línea de pedido determinada.
     * @param numeroFactura Número entero que representa la línea de pedido de la que se quiere obtener el pedido.
     * @return JSON en forma de cadena de caracteres que representa el pedido buscado. No se contempla la posibilidad de que exista una línea de pedido sin un pedido asociado.
     */
    public static String consultaPedido(int codigoLineaPedido){
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

    /**
     * Actualiza un pedido actualizando el estado en que se encuentra.
     * @param idPedido Número entero que representa el identificador del pedido a actualizar.
     */
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
    
    /**
     * Añade un nuevo pedido a la base de datos.
     * @param jsonNewPedido JSON en forma de cadena de caracteres que contiene los datos del pedido a introducir en la base de datos.
     */
    public static void añadirPedido(String jsonNewPedido) {
        JsonReaderFactory factory = Json.createReaderFactory(null);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        int numero = -1;
        int estado = -1;
        String notaEntrega = null;
        double importe = -1;
        int numeroFactura = -1;
        int numeroAbonado = -1;
        int codigo = -1;
        try(JsonReader reader = factory.createReader(new StringReader(jsonNewPedido));){
            JsonObject jsonobject = reader.readObject();
            estado = Integer.parseInt(jsonobject.getString("estado"));
            notaEntrega = jsonobject.getString("notaEntrega");         
            numeroFactura = Integer.parseInt(jsonobject.getString("numeroFactura"));
            numeroAbonado = Integer.parseInt(jsonobject.getString("numeroAbonado"));
            codigo = Integer.parseInt(jsonobject.getString("codigo"));
            importe = Double.parseDouble(jsonobject.getString("importe"));
        }catch(Exception ex){
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE,null,ex);
        }
        LocalDateTime now = LocalDateTime.now();
        Timestamp fechaRealizacionT = Timestamp.valueOf(now);
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        try (PreparedStatement ps = connection.getStatement("INSERT INTO PEDIDO (ESTADO,FECHAREALIZACION,NOTAENTREGA,IMPORTE,FECHARECEPCION,FECHAENTREGA,NUMEROFACTURA,NUMEROABONADO)" 
                + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)")) {
            ps.setInt(1, estado);
            ps.setTimestamp(2, fechaRealizacionT);
            ps.setString(3, notaEntrega);
            ps.setDouble(4, importe);
            ps.setNull(5, Types.TIMESTAMP);
            ps.setNull(6, Types.TIMESTAMP);
            ps.setNull(7, Types.INTEGER);
            ps.setInt(8, numeroAbonado);
            ps.executeUpdate();
        }
        catch (SQLException ex) {
            Logger.getLogger(VistaAlmacen.class.getName()).log(Level.SEVERE, null, ex);
        }
        connection.closeConnection();
    }
    
}
