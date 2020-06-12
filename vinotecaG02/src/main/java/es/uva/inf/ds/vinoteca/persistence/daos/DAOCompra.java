/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.persistence.daos;

import es.uva.inf.ds.vinoteca.domain.models.Compra;
import es.uva.inf.ds.vinoteca.domain.models.Empleado;
import es.uva.inf.ds.vinoteca.persistence.dbaccess.DBConnection;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;

/**
 *
 * @author alejandro
 */
public class DAOCompra {
    
    
    //COMPLETAR
    public static String consultaCompra(int id) {
        String compraJSONString = "";
        LocalDateTime fechaInicio = null;
        double importe;
        
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        try(
            PreparedStatement ps = connection.getStatement("SELECT * FROM COMPRA c WHERE c.IdCompra= ?");
            
        ){
            ps.setInt(1, id);
            ResultSet result = ps.executeQuery();
            if(result.next()){
                fechaInicio=result.getTimestamp("fechapago").toLocalDateTime();
                importe=result.getDouble("importe");
                String imp = Double.toString(importe);
                compraJSONString = obtainCompraJSONString(fechaInicio, imp);
                //lo que se quiera de la compra
            }
            result.close();
        }catch(SQLException ex){
            Logger.getLogger(DAOCompra.class.getName()).log(Level.SEVERE,null,ex);
        }
        connection.closeConnection();
        return compraJSONString;
    }
    
    private static String obtainCompraJSONString(LocalDateTime fecha, String importe){
        String compraJSONString="";
        //JsonReaderFactory factory = Json.createReaderFactory(null);
        JsonObject compraJson = Json.createObjectBuilder()
                    .add("importe",importe)
                    .add("fechaPago",fecha.toString())
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

    public static void actualizarCompra(String compra) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
