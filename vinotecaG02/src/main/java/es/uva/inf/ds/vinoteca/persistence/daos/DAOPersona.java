/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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


public class DAOPersona {

    public static String consultaPersona(String nif){
        String personaJSONString = "";
        String nombre = null;
        String apellidos = null;
        String direccion = null;
        String telefono = null;
        String email = null;
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        ResultSet result = null;
        try(PreparedStatement ps = connection.getStatement("SELECT * FROM PERSONA p WHERE p.NIF = ?");)
        {
            ps.setString(1, nif);
            result = ps.executeQuery();
            if(result!=null && result.next()){
                nombre = result.getString("NOMBRE");
                apellidos = result.getString("APELLIDOS");
                direccion = result.getString("DIRECCION");
                telefono = result.getString("TELEFONO");
                email = result.getString("EMAIL");
            }        
        }catch(SQLException ex){
            Logger.getLogger(DAOAbonado.class.getName()).log(Level.SEVERE,null,ex);
        }finally{
            try {
                result.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOPersona.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        connection.closeConnection();
        if(nombre!=null)
            personaJSONString = abonadoToJSONString(nombre,apellidos,direccion,telefono,email);
        return personaJSONString;
    }
    
    private static String abonadoToJSONString(String nombre, String apellidos, String direccion, String telefono, String email){
        String personaJSONString = "";
        JsonObject personaJSON = Json.createObjectBuilder().
                add("nombre",nombre)
                .add("apellidos",apellidos)
                .add("direccion",direccion)
                .add("telefono",telefono)
                .add("email",email)
                .build();
        try(
                StringWriter stringWriter = new StringWriter();
                JsonWriter writer = Json.createWriter(stringWriter);
                ){
           
            writer.writeObject(personaJSON);
            personaJSONString = stringWriter.toString();
        }catch(Exception ex){
            Logger.getLogger(DAOAbonado.class.getName()).log(Level.SEVERE,null,ex);
        }
        return personaJSONString;
    }
    
    
}
