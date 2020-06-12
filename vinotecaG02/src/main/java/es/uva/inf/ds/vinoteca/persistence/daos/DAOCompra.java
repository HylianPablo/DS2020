/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.persistence.daos;

import es.uva.inf.ds.vinoteca.domain.models.Compra;
import es.uva.inf.ds.vinoteca.domain.models.Empleado;
import es.uva.inf.ds.vinoteca.persistence.dbaccess.DBConnection;
import es.uva.inf.ds.vinoteca.userinterface.VistaAlmacen;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import static java.time.LocalDateTime.now;
import java.time.format.DateTimeFormatter;
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
        String idd = Integer.toString(id);
        LocalDateTime fechaInicio = null;
        double importe;
        System.out.println("HASTA AQUI LLEGA" + id);
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        try(
            PreparedStatement ps = connection.getStatement("SELECT * FROM COMPRA c WHERE c.IDCOMPRA= ?");
            
        ){
            ps.setString(1, idd);
            ResultSet result = ps.executeQuery();
            if(result.next()){
                System.out.println("HASTA AQUI LLEGA??????????????");
                fechaInicio=result.getTimestamp("fechapago").toLocalDateTime();
                System.out.println("fecha" + fechaInicio);
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

    public static void actualizarCompra(int idCompra) {
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(now);
        System.out.println("me gustaria que llegara aqui");
        try (PreparedStatement ps = connection.getStatement("UPDATE COMPRA SET FECHACOMPRACOMPLETADA = ?, RECIBIDACOMPLETA = ? WHERE IDCOMPRA = ?")) {
            ps.setTimestamp(1, timestamp);
            ps.setString(2, "1");
            ps.setInt(3, idCompra);
            
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
