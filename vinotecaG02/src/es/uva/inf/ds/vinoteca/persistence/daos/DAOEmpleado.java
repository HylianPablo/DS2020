/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.persistence.daos;

import es.uva.inf.ds.vinoteca.domain.controllers.Empleado;
import es.uva.inf.ds.vinoteca.persistence.dbaccess.DBConnection;
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

    public static String consultaEmpleadoPorLoginYPassword(String user, String password) {
        String empleadoJSONString = "";
        String nif=null;
        LocalDateTime fechaInicio = null;
        String tipoEmpleado=null;
        
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        try(
            PreparedStatement ps = connection.getStatement("SELECT * FROM EMPLEADO e WHERE e.login= ? AND e.password= ?");
            
        ){
            ps.setString(1, user);
            ps.setString(2,password);
            ResultSet result = ps.executeQuery();
            if(result.next()){
                nif=result.getString("nif");
                fechaInicio=result.getTimestamp("fechaInicio").toLocalDateTime();
                tipoEmpleado=result.getString("tipoEmpleado");
            }
            result.close();
        }catch(SQLException ex){
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE,null,ex);
        }
        connection.closeConnection();
        
        ArrayList<String> rolesEmpresa = getRolesEmpresa(nif);
        ArrayList<String> vinculaciones = getVinculaciones(nif);
        ArrayList<String> disponibilidades = getDisponibilidades(nif);
        empleadoJSONString = obtainEmpleadoJSONString(user,nif,password,fechaInicio,tipoEmpleado,
                rolesEmpresa,vinculaciones,disponibilidades);
        return empleadoJSONString;
    }
    
    private static ArrayList<String> getRolesEmpresa(String nif){
        LocalDateTime comienzoEnRol=null;
        int rol=0;
        ArrayList<String> rolesEmpresa = new ArrayList<>();
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        try(
            PreparedStatement ps = connection.getStatement("SELECT * FROM ROLESENEMPRESA r WHERE r.nif= ?"); 
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
            PreparedStatement ps = connection.getStatement("SELECT * FROM VINCULACIONCONLAEMPRESA v WHERE v.nif= ?"); 
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
            PreparedStatement ps = connection.getStatement("SELECT * FROM DISPONIBILIDADEMPLEADO d WHERE d.nif= ?"); 
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
    
    private static String obtainEmpleadoJSONString(String user, String nif,String password,
            LocalDateTime fechaInicio, String tipoEmpleado, ArrayList<String> rolesEmpresa,
            ArrayList<String> vinculaciones, ArrayList<String> disponibilidades){
        String empleadoJSONString="";
        JsonReaderFactory factory = Json.createReaderFactory(null);
        try(
                StringWriter stringWriter = new StringWriter();
                JsonWriter writer = Json.createWriter(stringWriter);
                ){
            /*JsonArrayBuilder rolesB =  Json.createArrayBuilder();
            for(int i=0;i<rolesEmpresa.size();i+=2){
                rolesB.add(Json.createObjectBuilder()
                        .add("comienzoEnRol",rolesEmpresa.get(i))
                        .add("rol", rolesEmpresa.get(i+1)).build());
            }
            JsonArray rolesJA = rolesB.build();
            
            JsonArrayBuilder vinculacionesB =  Json.createArrayBuilder();
            for(int i=0;i<vinculaciones.size();i+=2){
                rolesB.add(Json.createObjectBuilder()
                        .add("inicio",vinculaciones.get(i))
                        .add("vinculo", vinculaciones.get(i+1)).build());
            }
            JsonArray vinculacionesJA = vinculacionesB.build();
            
            JsonArrayBuilder disponibilidadesB =  Json.createArrayBuilder();
            for(int i=0;i<disponibilidades.size();i+=3){
                rolesB.add(Json.createObjectBuilder()
                        .add("comienzo",disponibilidades.get(i))
                        .add("finalPrevisto", disponibilidades.get(i+1))
                        .add("disponibilidad",disponibilidades.get(i+2)).build());
            }
            JsonArray disponibilidadesJA = disponibilidadesB.build();
            */
            
            JsonObject empleadoJson = Json.createObjectBuilder()
                    .add("login", user)
                    .add("nif",nif)
                    .add("password",password)
                    .add("fechaInicio",fechaInicio.toString())
                    .add("tipoEmpleado",tipoEmpleado)
                    .build();
            writer.writeObject(empleadoJson);
            empleadoJSONString = stringWriter.toString();
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
            PreparedStatement ps = connection.getStatement("SELECT * FROM DISPONIBILIDADEMPLEADO d WHERE d.nif= ? ORDER BY d.comienzo DESC");
            
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
            PreparedStatement ps = connection.getStatement("SELECT * FROM ROLESENEMPRESA r WHERE r.nif= ? ORDER BY r.ComienzoEnRol DESC");          
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
