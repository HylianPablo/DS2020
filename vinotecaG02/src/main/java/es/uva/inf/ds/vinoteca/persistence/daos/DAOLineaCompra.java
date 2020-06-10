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
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;

/**
 *
 * @author alejandro
 */
public class DAOLineaCompra {
    
    //Es posible que esto no funcione
    
    public static String consultaLineaCompra(int id){
        String lineaCompraJSONString = "";
        ArrayList<String> unidades = new ArrayList<>();
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        try(
            PreparedStatement ps = connection.getStatement("SELECT * FROM LINEACOMPRA lc, COMPRA c WHERE c.IdCompra= ? "
                    + "AND c.IdCompra = lc.IdCompra");   
        ){
            ps.setInt(1, id);
            ResultSet result = ps.executeQuery();
            while(result.next()){
                int numeroUnidades = result.getInt("unidades");
                String numero = Integer.toString(numeroUnidades);
                unidades.add(numero);
            }
            result.close();
        }catch(SQLException ex){
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE,null,ex);
        }
        lineaCompraJSONString = obtainLineaCompraJSONString(unidades);
        connection.closeConnection();
        return lineaCompraJSONString;
    }
   
    private static String obtainLineaCompraJSONString(ArrayList<String> unidades) {
        JsonObjectBuilder group = Json.createObjectBuilder();
        //JsonReaderFactory factory = Json.createReaderFactory(null);
        JsonArrayBuilder array = Json.createArrayBuilder();
        JsonObject lineaCompraJson = null;
        for(int i = 0; i < unidades.size(); i++){
            array.add(unidades.get(i));
        }
        JsonArray arr = array.build();
        try{   
            group.add("array", arr);
        }catch(Exception ex){
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE,null,ex);
        }
        return group.build().toString();
    }
    
}
