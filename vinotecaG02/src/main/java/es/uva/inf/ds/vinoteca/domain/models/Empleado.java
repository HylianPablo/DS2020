/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.domain.models;

import es.uva.inf.ds.vinoteca.persistence.daos.DAOEmpleado;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;

import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;

/**
 *
 * @author pablo
 */
public class Empleado {
    private String nif, password;
    private LocalDateTime fechaInicio;
    
    public Empleado(String n, String p, LocalDateTime ldt){
        nif=n;
        password=p;
        fechaInicio=ldt;
    }
    
    public void setNif(String nif){
        this.nif=nif;
    }
            
    public String getNif(){
        return nif;
    }
    
    public void setPassword(String password){
        this.password=password;
    }
    
    public String getPassword(){
        return password;
    }
    
    public void setFecha(LocalDateTime fecha){
        fechaInicio=fecha;
    }
    
    public LocalDateTime getFecha(){
        return fechaInicio;
    }
    
    public boolean isActivo(){
        return DAOEmpleado.empleadoActivo(nif);
    }
    
    public int getRol(){
        return DAOEmpleado.selectRol(nif);
    }
    
    public static Empleado getEmpleadoPorLoginYPassword(String user, String password) {
        Empleado empleadoLogin = null;
        String empleadoJSONString = DAOEmpleado.consultaEmpleadoPorLoginYPassword(user,password); 
        if(!empleadoJSONString.equals("")){
            String nifJson=null; 
            String passJson =null; 
            String fechaInicioJson=null;
            String tipoEmpleadoJson = null;
            JsonReaderFactory factory = Json.createReaderFactory(null);
            try(JsonReader reader = factory.createReader(new StringReader(empleadoJSONString));){
                JsonObject jsonobject = reader.readObject();
                nifJson = jsonobject.getString("nif");
                passJson = jsonobject.getString("password");
                fechaInicioJson = jsonobject.getString("fechaInicio");
            //tipoEmpleadoJson = jsonobject.getString("tipoEmpleado");
            }catch(Exception ex){
                Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE,null,ex);
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm",Locale.US);
            LocalDateTime fechaInicioLDT = LocalDateTime.parse(fechaInicioJson,formatter);
            empleadoLogin = new Empleado(nifJson,passJson,fechaInicioLDT);
        }
        return empleadoLogin;
    }
    
}
