/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.persistence.daos;

import es.uva.inf.ds.vinoteca.domain.models.Empleado;
import es.uva.inf.ds.vinoteca.persistence.dbaccess.DBConnection;
import es.uva.inf.ds.vinoteca.userinterface.VistaAlmacen;
import java.io.StringReader;
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
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import javax.json.JsonWriter;

/**
 *
 * @author alejandro
 */
public class DAOLineaPedido {

    public static String consultaLineasPedido(int id){
        String lineaPedidoJSONString = "";
        int numeroUnidades = 0;
        int counter = 0;
        String comp = null;
        boolean completada = false;
        int numeroPedido = 0;
        int codigo = 0;
        ArrayList<Integer> unidades = new ArrayList<>();
        ArrayList<Integer> numerosPedido = new ArrayList<>();
        ArrayList<Boolean> completadas = new ArrayList<>();
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        try(
            PreparedStatement ps = connection.getStatement("SELECT * FROM LINEAPEDIDO lp WHERE lp.IdLineaCompra= ? ");   
        ){
            ps.setInt(1, id);
            ResultSet result = ps.executeQuery();
            while(result.next()){
                counter ++;
                numeroUnidades = result.getInt("unidades");
                comp = result.getString("completada");
                numeroPedido = result.getInt("numeropedido");
                if(comp.equals("1")){
                    completada = true;
                }
                numerosPedido.add(numeroPedido);
                unidades.add(numeroUnidades);
                completadas.add(completada);
            }
            result.close();
        }catch(SQLException ex){
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE,null,ex);
        }
        connection.closeConnection();
        if(counter!=0){
            lineaPedidoJSONString = obtainLineaPedidoJSONString(unidades, completadas, numerosPedido);
        }
        return lineaPedidoJSONString;
    }
   
    private static String obtainLineaPedidoJSONString(ArrayList<Integer> unidades, ArrayList<Boolean> completadas, ArrayList<Integer> numerosPedido) {
        String lineaPedidoJSONString = "";
        //JsonReaderFactory factory = Json.createReaderFactory(null);
        JsonArrayBuilder array = Json.createArrayBuilder();
        for(int i=0;i<unidades.size();i++){
            array.add(Json.createObjectBuilder().add("unidades",Integer.toString(unidades.get(i)))
            .add("recibidas", Boolean.toString(completadas.get(i)))
            .add("numeros",Integer.toString(numerosPedido.get(i)))
            .build());
        }
        try(
                StringWriter stringWriter = new StringWriter();
                JsonWriter writer = Json.createWriter(stringWriter);
                ){   
            
            JsonObject jsonObj = Json.createObjectBuilder().add("lineasCompra",array).build();
            writer.writeObject(jsonObj);
            lineaPedidoJSONString = stringWriter.toString();
        }catch(Exception ex){
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE,null,ex);
        }
        return lineaPedidoJSONString;
    }

    public static void actualizarLineasDePedido(int id) throws SQLException {
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        System.out.println("me gustaria que llegara aqui");
        try (PreparedStatement ps = connection.getStatement("UPDATE LINEAPEDIDO SET COMPLETADA = ? WHERE IDLINEACOMPRA = ?")) {
            ps.setString(1, "1");
            ps.setInt(2, id);
            
            System.out.println("me gustaria que llegara aqui2");
            // execute the java preparedstatement
            ps.executeUpdate();
        }
        catch (SQLException ex) {
            Logger.getLogger(VistaAlmacen.class.getName()).log(Level.SEVERE, null, ex);
        }
        connection.closeConnection();
    }
}
