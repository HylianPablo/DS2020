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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;

/**
 *
 * @author alejandro
 */
public class DAOReferencia {
    
    public static String consultaReferencia(int codigo) {
        String referenciaJSONString = "";
        double precio = 0;
        boolean esPorCajas = false;
        String esPc = null;
        boolean disponible = false;
        String disp = null;
        int contenido = 0;
        int codigoRef = 0;
        
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        System.out.println("me quedo aqui???");
        try(
            PreparedStatement ps = connection.getStatement("SELECT * FROM REFERENCIA r WHERE r.CODIGO= ?");
            
        ){
            ps.setInt(1, codigo);
            ResultSet result = ps.executeQuery();
            if(result.next()){
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
                referenciaJSONString = obtainReferenciaJSONString(precio, contenido, codigoRef, esPorCajas, disponible);
                System.out.println(referenciaJSONString);
            }
            result.close();
        }catch(SQLException ex){
            Logger.getLogger(DAOCompra.class.getName()).log(Level.SEVERE,null,ex);
        }
        connection.closeConnection();
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
