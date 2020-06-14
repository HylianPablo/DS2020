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
 * Clase que representa el acceso a la tabla de la base de datos que contiene los datos de las referencias del sistema.
 * @author pamarti
 * @author alerome
 * @author ivagonz
 */
public class DAOReferencia {
    
     /**
     * Obtiene un JSON en forma de cadena de caracteres representando la referencia que se desea buscar en la base de datos. En caso de no existir retorna cadena vacía.
     * @param id Número entero que representa el identificador de la referencia que se quiere buscar.
     * @return JSON en forma de cadena de caracteres que representa la referencia en caso de que esta exista o cadena vacía en caso contrario.
     */
    public static String consultaReferencia(int codigo) {
        String referenciaJSONString = "";
        double precio = -1.0;
        boolean esPorCajas = false;
        String esPc = null;
        boolean disponible = false;
        String disp = null;
        int contenido = -1;
        int codigoRef = -1;
        
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        ResultSet result = null;
        try(
            PreparedStatement ps = connection.getStatement("SELECT * FROM REFERENCIA r WHERE r.CODIGO= ?");
            
        ){
            ps.setInt(1, codigo);
            result = ps.executeQuery();
            if(result!=null && result.next()){
                precio=result.getDouble("precio");
                contenido=result.getInt("contenidoencl");
                codigoRef=result.getInt("codigo");
                esPc=result.getString("esporcajas");
                disp=result.getString("disponible");
                if(esPc.equals("1")){
                    esPorCajas=true;
                }
                if(disp.equals("1")){
                    disponible=true;
                }
                
            }
        }catch(SQLException ex){
            Logger.getLogger(DAOCompra.class.getName()).log(Level.SEVERE,null,ex);
        }finally{
            try {
                if(result!=null)
                result.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOReferencia.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        connection.closeConnection();
        if(esPc!=null)
            referenciaJSONString = obtainReferenciaJSONString(precio, contenido, codigoRef, esPorCajas, disponible);
        return referenciaJSONString;
    }
    
    private static String obtainReferenciaJSONString(Double precio, int contenido, int codigoR, boolean porCajas, boolean disponible){
        String referenciaJSONString="";
        //JsonReaderFactory factory = Json.createReaderFactory(null);
        JsonObject referenciaJson = Json.createObjectBuilder()
                    .add("precio",Double.toString(precio))
                    .add("contenido",Integer.toString(contenido))
                    .add("codigoRef",Integer.toString(codigoR))
                    .add("porCajas",Boolean.toString(porCajas))
                    .add("disponible",Boolean.toString(disponible))
                    .build();
        try(
                StringWriter stringWriter = new StringWriter();
                JsonWriter writer = Json.createWriter(stringWriter);
                ){
           
            writer.writeObject(referenciaJson);
            referenciaJSONString = stringWriter.toString();
        }catch(Exception ex){
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE,null,ex);
        }
        return referenciaJSONString;
    }
    
}
