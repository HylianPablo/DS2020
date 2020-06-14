package es.uva.inf.ds.vinoteca.persistence.daos;

import es.uva.inf.ds.vinoteca.domain.models.Empleado;
import es.uva.inf.ds.vinoteca.persistence.dbaccess.DBConnection;
import es.uva.inf.ds.vinoteca.userinterface.VistaAlmacen;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;

/**
 * Clase que representa el acceso a la tabla de la base de datos que contiene los datos de las compras del sistema.
 * @author pamarti
 * @author alerome
 * @author ivagonz
 */
public class DAOCompra {
    
    /**
     * Obtiene un JSON en forma de cadena de caracteres representando la compra que se desea buscar en la base de datos. En caso de no existir retorna cadena vacía.
     * @param id Número entero que representa el identificador de la compra que se quiere buscar.
     * @return JSON en forma de cadena de caracteres que representa al abonado en caso de que la compra exista o cadena vacía en caso contrario.
     */
    public static String consultaCompra(int id) {
        String compraJSONString = "";
        String idd = Integer.toString(id);
        LocalDateTime fechaInicio = null;
        String completa = null;
        double importe = -1.0;
        String imp = null;
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        ResultSet result = null;
        try(
            PreparedStatement ps = connection.getStatement("SELECT * FROM COMPRA c WHERE c.IDCOMPRA= ?");
            
        ){
            ps.setString(1, idd);
            result = ps.executeQuery();
            if(result!=null && result.next()){
                fechaInicio=result.getTimestamp("fechapago").toLocalDateTime();
                completa=result.getString("RECIBIDACOMPLETA");
                importe=result.getDouble("importe");
                imp = Double.toString(importe);
                
            }
            
        }catch(SQLException ex){
            Logger.getLogger(DAOCompra.class.getName()).log(Level.SEVERE,null,ex);
        }finally{
            try {
                if(result!=null)
                result.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOCompra.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        connection.closeConnection();
        if(fechaInicio!=null)
            compraJSONString = obtainCompraJSONString(fechaInicio, imp, completa);
        return compraJSONString;
    }
    
    private static String obtainCompraJSONString(LocalDateTime fecha, String importe, String completa){
        String compraJSONString="";
        JsonObject compraJson = Json.createObjectBuilder()
                    .add("importe",importe)
                    .add("fechaPago",fecha.toString())
                    .add("completa",completa)
                    .build();
        try(
                StringWriter stringWriter = new StringWriter();
                JsonWriter writer = Json.createWriter(stringWriter);
                ){
           
            writer.writeObject(compraJson);
            compraJSONString = stringWriter.toString();
        }catch(Exception ex){
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE,null,ex);
        }
        return compraJSONString;
    }

    /**
     * Actualiza una compra marcándola como completada y utilizando la fecha actual como fecha en que se completó la compra.
     * @param idCompra Número entero que representa el identificador de la compra.
     */
    public static void actualizarCompra(int idCompra) {
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(now);
        try (PreparedStatement ps = connection.getStatement("UPDATE COMPRA SET FECHACOMPRACOMPLETADA = ?, RECIBIDACOMPLETA = ? WHERE IDCOMPRA = ?")) {
            ps.setTimestamp(1, timestamp);
            ps.setString(2, "1");
            ps.setInt(3, idCompra);
            ps.executeUpdate();
        }
        catch (SQLException ex) {
            Logger.getLogger(VistaAlmacen.class.getName()).log(Level.SEVERE, null, ex);
        }
        connection.closeConnection();
    }

}
