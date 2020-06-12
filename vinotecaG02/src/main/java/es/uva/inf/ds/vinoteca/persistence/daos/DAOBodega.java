/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.persistence.daos;

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
public class DAOBodega {
    
    //COMPLETAR
    public static String consultaBodega(int id) {
        String bodegaJSONString = "";     
        String cif, nombre, direccion;
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
                bodegaJSONString = obtainBodegaSONString(cif, nombre, direccion);
            }
            result.close();
        }catch(SQLException ex){
            Logger.getLogger(DAOCompra.class.getName()).log(Level.SEVERE,null,ex);
        }
        connection.closeConnection();
        return bodegaJSONString;
    }
    
    private static String obtainBodegaSONString(String cif, String nombre, String direccion){
        String bodegaJSONString="";
        //JsonReaderFactory factory = Json.createReaderFactory(null);
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
            System.out.println(bodegaJSONString);
        }catch(Exception ex){
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE,null,ex);
        }
        return bodegaJSONString;
    }
}
