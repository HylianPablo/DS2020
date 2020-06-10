/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.persistence.daos;

import es.uva.inf.ds.vinoteca.domain.models.Empleado;
import es.uva.inf.ds.vinoteca.persistence.dbaccess.DBConnection;
import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
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
import javax.json.JsonReaderFactory;
import javax.json.JsonWriter;

/**
 *
 * @author pablo
 */
public class DAOEmpleado {

    public static String consultaEmpleadoPorLoginYPassword(String nif, String password) {
        String empleadoJSONString = "";
        LocalDateTime fechaInicio = null;
        String tipoEmpleado=null;
        
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        try(
            PreparedStatement ps = connection.getStatement("SELECT * FROM EMPLEADO e WHERE e.NIF= ? AND e.PASSWORD= ?");
            
        ){
            System.out.println(nif);
            System.out.println(password);
            ps.setString(1, nif);
            ps.setString(2,password);
            ResultSet result = ps.executeQuery();
            if(result.next()){
                fechaInicio=result.getTimestamp("fechainicioenempresa").toLocalDateTime();
                System.out.println(fechaInicio);
                ArrayList<String> rolesEmpresa = getRolesEmpresa(nif);
                ArrayList<String> vinculaciones = getVinculaciones(nif);
                ArrayList<String> disponibilidades = getDisponibilidades(nif);
                empleadoJSONString = obtainEmpleadoJSONString(nif,password,fechaInicio,
                rolesEmpresa,vinculaciones,disponibilidades);
                //tipoEmpleado=result.getString("tipoEmpleado");
            }
            result.close();
        }catch(SQLException ex){
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE,null,ex);
        }
        connection.closeConnection();
        
        System.out.println("RETURN: "+empleadoJSONString);
        return empleadoJSONString;
    }
    
    private static ArrayList<String> getRolesEmpresa(String nif){
        LocalDateTime comienzoEnRol=null;
        int rol=0;
        ArrayList<String> rolesEmpresa = new ArrayList<>();
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        try(
            PreparedStatement ps = connection.getStatement("SELECT * FROM ROLESENEMPRESA r WHERE r.empleado= ?"); 
        ){
            ps.setString(1, nif);
            ResultSet result = ps.executeQuery();
            while(result.next()){
                comienzoEnRol=result.getTimestamp("ComienzoEnRol").toLocalDateTime();
                rolesEmpresa.add(comienzoEnRol.toString());
                rol=result.getInt("Rol");
                rolesEmpresa.add(Integer.toString(rol));
            }
            result.close();
        }catch(SQLException ex){
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE,null,ex);
        }
        connection.closeConnection();
        return rolesEmpresa;
    }
    
    private static ArrayList<String> getVinculaciones(String nif){
        LocalDateTime inicio=null;
        int vinculo=0;
        ArrayList<String> vinculaciones = new ArrayList<>();
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        try(
            PreparedStatement ps = connection.getStatement("SELECT * FROM VINCULACIONCONLAEMPRESA v WHERE v.empleado= ?"); 
        ){
            ps.setString(1, nif);
            ResultSet result = ps.executeQuery();
            while(result.next()){
                inicio=result.getTimestamp("inicio").toLocalDateTime();
                vinculaciones.add(inicio.toString());
                vinculo=result.getInt("Vinculo");
                vinculaciones.add(Integer.toString(vinculo));
            }
            result.close();
        }catch(SQLException ex){
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE,null,ex);
        }
        connection.closeConnection();
        return vinculaciones;
    }
    
    private static ArrayList<String> getDisponibilidades(String nif){
        LocalDateTime comienzo=null;
        LocalDateTime finalPrevisto =null;
        int disponibilidad=0;
        ArrayList<String> disponibilidades = new ArrayList<>();
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        try(
            PreparedStatement ps = connection.getStatement("SELECT * FROM DISPONIBILIDADEMPLEADO d WHERE d.empleado= ?"); 
        ){
            ps.setString(1, nif);
            ResultSet result = ps.executeQuery();
            while(result.next()){
                comienzo=result.getTimestamp("Comienzo").toLocalDateTime();
                disponibilidades.add(comienzo.toString());
                finalPrevisto=result.getTimestamp("FinalPrevisto").toLocalDateTime();
                disponibilidades.add(finalPrevisto.toString());
                disponibilidad=result.getInt("Disponibilidad");
                disponibilidades.add(Integer.toString(disponibilidad));
            }
            result.close();
        }catch(SQLException ex){
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE,null,ex);
        }
        connection.closeConnection();
        return disponibilidades;
    }
    
    private static String obtainEmpleadoJSONString(String nif,String password,
            LocalDateTime fechaInicio, ArrayList<String> rolesEmpresa,
            ArrayList<String> vinculaciones, ArrayList<String> disponibilidades){
        String empleadoJSONString="";
        //JsonReaderFactory factory = Json.createReaderFactory(null);
        JsonObject empleadoJson = Json.createObjectBuilder()
                    .add("nif",nif)
                    .add("password",password)
                    .add("fechaInicio",fechaInicio.toString())
                    .build();
        try(
                StringWriter stringWriter = new StringWriter();
                JsonWriter writer = Json.createWriter(stringWriter);
                ){
           
            writer.writeObject(empleadoJson);
            empleadoJSONString = stringWriter.toString();
            System.out.println(empleadoJSONString);
        }catch(Exception ex){
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE,null,ex);
        }
        return empleadoJSONString;
    }
    
    public static boolean empleadoActivo(String nif){
        int d=0;
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        try(
            PreparedStatement ps = connection.getStatement("SELECT * FROM DISPONIBILIDADEMPLEADO d WHERE d.empleado= ? ORDER BY d.comienzo DESC");
            
        ){
            ps.setString(1, nif);
            ResultSet result = ps.executeQuery();
            if(result.next()){
                d=result.getInt("disponibilidad");
            }
            result.close();
        }catch(SQLException ex){
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE,null,ex);
        }
        connection.closeConnection();
        return(d==3);
    }
    
    public static int selectRol(String nif){
        int r=0;
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        try(
            PreparedStatement ps = connection.getStatement("SELECT * FROM ROLESENEMPRESA r WHERE r.empleado= ? ORDER BY r.ComienzoEnRol DESC");          
        ){
            ps.setString(1, nif);
            ResultSet result = ps.executeQuery();
            if(result.next()){
                r=result.getInt("rol");
            }
            result.close();
        }catch(SQLException ex){
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE,null,ex);
        }
        connection.closeConnection();
        return r;
    } 
}
