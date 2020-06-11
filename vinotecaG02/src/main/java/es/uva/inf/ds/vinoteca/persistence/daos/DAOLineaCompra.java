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
        LocalDateTime fechaRecepcion = null;
        int numeroUnidades = 0;
        int counter = 0;
        String recibida = null;
        boolean rec = false;
        int codigo = 0;
        ArrayList<Integer> unidades = new ArrayList<>();
        ArrayList<Integer> codigos = new ArrayList<>();
        ArrayList<Boolean> recibidas = new ArrayList<>();
        ArrayList<LocalDateTime> fechasRecepcion = new ArrayList<>();
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        try(
            PreparedStatement ps = connection.getStatement("SELECT * FROM LINEACOMPRA lc, COMPRA c WHERE c.IdCompra= ? "
                    + "AND c.IdCompra = lc.IdCompra");   
        ){
            ps.setInt(1, id);
            ResultSet result = ps.executeQuery();
            while(result.next()){
                counter ++;
                numeroUnidades = result.getInt("unidades");
                recibida = result.getString("recibida");
                if(recibida.equals("1")){
                    rec = true;
                }
                codigo = result.getInt("codigoreferencia");
                fechaRecepcion = result.getTimestamp("fecharecepcion").toLocalDateTime();
                //boolean recibida = result.getBoolean
                unidades.add(numeroUnidades);
                codigos.add(codigo);
                recibidas.add(rec);
                fechasRecepcion.add(fechaRecepcion);
            }
            result.close();
        }catch(SQLException ex){
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE,null,ex);
        }
        connection.closeConnection();
        if(counter!=0){
            lineaCompraJSONString = obtainLineaCompraJSONString(unidades, fechasRecepcion, codigos, recibidas);
        }
        return lineaCompraJSONString;
    }
   
    private static String obtainLineaCompraJSONString(ArrayList<Integer> unidades, ArrayList<LocalDateTime> fechasRecepcion
            , ArrayList<Integer> codigos, ArrayList<Boolean> recibidas) {
        String lineaCompraJSONString = "";
        //JsonReaderFactory factory = Json.createReaderFactory(null);
        JsonArrayBuilder array = Json.createArrayBuilder();
        for(int i=0;i<unidades.size();i++){
            array.add(Json.createObjectBuilder().add("unidades",Integer.toString(unidades.get(i)))
            .add("fechaRecepcion",fechasRecepcion.get(i).toString())
            .add("codigos",Integer.toString(codigos.get(i)))
            .add("recibidas", Boolean.toString(recibidas.get(i)))
            .build());
        }
        try(
                StringWriter stringWriter = new StringWriter();
                JsonWriter writer = Json.createWriter(stringWriter);
                ){   
            
            JsonObject jsonObj = Json.createObjectBuilder().add("lineasCompra",array).build();
            writer.writeObject(jsonObj);
            lineaCompraJSONString = stringWriter.toString();
        }catch(Exception ex){
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE,null,ex);
        }
        return lineaCompraJSONString;
    }
    
}
