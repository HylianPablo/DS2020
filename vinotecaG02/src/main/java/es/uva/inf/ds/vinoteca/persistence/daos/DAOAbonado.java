package es.uva.inf.ds.vinoteca.persistence.daos;

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
 * Clase que representa el acceso a la tabla de la base de datos que contiene los datos de los abonados del sistema.
 * @author pamarti
 * @author alerome
 * @author ivagonz
 */
public class DAOAbonado {
    
    /**
     * Obtiene un JSON en forma de cadena de caracteres representando el abonado que se desea buscar en la base de datos. En caso de no existir retorna cadena vacía.
     * @param numeroAbonado Número entero que representa el abonado que se quiere buscar.
     * @return JSON en forma de cadena de caracteres que representa al abonado en caso de que el abonado exista o cadena vacía en caso contrario.
     */
    public static String consultaAbonado(int numeroAbonado){
        String abonadoJSONString = "";
        String openidref = null;
        String nif = null;
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        try(PreparedStatement ps = connection.getStatement("SELECT * FROM ABONADO a WHERE a.NUMEROABONADO = ?");)
        {
            ps.setInt(1, numeroAbonado);
            ResultSet result = ps.executeQuery();
            if(result.next()){
                openidref = result.getString("OPENIDREF");
                nif = result.getString("NIF");
            }
            
        }catch(SQLException ex){
            Logger.getLogger(DAOAbonado.class.getName()).log(Level.SEVERE,null,ex);
        }
        connection.closeConnection();
        if(nif!=null)
            abonadoJSONString = abonadoToJSONString(numeroAbonado,openidref,nif);
        return abonadoJSONString;
    }
    
    private static String abonadoToJSONString(int numeroAbonado, String openidref, String nif){
        String abonadoJSONString = "";
        JsonObject abonadoJSON = Json.createObjectBuilder().
                add("numeroAbonado",Integer.toString(numeroAbonado))
                .add("openIdRef",openidref)
                .add("nif",nif).build();
        try(
                StringWriter stringWriter = new StringWriter();
                JsonWriter writer = Json.createWriter(stringWriter);
                ){
           
            writer.writeObject(abonadoJSON);
            abonadoJSONString = stringWriter.toString();
        }catch(Exception ex){
            Logger.getLogger(DAOAbonado.class.getName()).log(Level.SEVERE,null,ex);
        }
        return abonadoJSONString;
    }
}
