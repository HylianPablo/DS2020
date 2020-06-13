package es.uva.inf.ds.vinoteca.persistence.daos;

import es.uva.inf.ds.vinoteca.domain.models.Empleado;
import es.uva.inf.ds.vinoteca.persistence.dbaccess.DBConnection;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;

/**
 * Clase que representa el acceso a la tabla de la base de datos que contiene los datos de las bodegas del sistema.
 * @author pamarti
 * @author alerome
 * @author ivagonz
 */
public class DAOBodega {
    
    /**
     * Obtiene un JSON en forma de cadena de caracteres representando la bodega que se desea buscar en la base de datos. En caso de no existir retorna cadena vacía.
     * @param id Número entero que representa el identificador de la bodega que se quiere buscar.
     * @return JSON en forma de cadena de caracteres que representa al abonado en caso de que la bodega exista o cadena vacía en caso contrario.
     */
    public static String consultaBodega(int id) {
        String bodegaJSONString = "";     
        String cif = null;
        String nombre= null;
        String direccion = null;
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        try(
            PreparedStatement ps = connection.getStatement("SELECT * FROM BODEGA b WHERE b.ID= ?");   
        ){
            ps.setInt(1, id);
            ResultSet result = ps.executeQuery();
            if(result.next()){
                cif = result.getString("cif");
                nombre = result.getString("nombre");
                direccion = result.getString("direccion");
                
            }
            result.close();
        }catch(SQLException ex){
            Logger.getLogger(DAOCompra.class.getName()).log(Level.SEVERE,null,ex);
        }
        connection.closeConnection();
        if(cif!=null)
            bodegaJSONString = obtainBodegaSONString(cif, nombre, direccion);
        return bodegaJSONString;
    }
    
    private static String obtainBodegaSONString(String cif, String nombre, String direccion){
        String bodegaJSONString="";
        JsonObject bodegaJson = Json.createObjectBuilder()
                    .add("cif",cif)
                    .add("nombre",nombre)
                    .add("direccion",direccion)
                    .build();
        try(
                StringWriter stringWriter = new StringWriter();
                JsonWriter writer = Json.createWriter(stringWriter);
                ){
           
            writer.writeObject(bodegaJson);
            bodegaJSONString = stringWriter.toString();
        }catch(Exception ex){
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE,null,ex);
        }
        return bodegaJSONString;
    }
}
