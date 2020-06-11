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
 * Modelo de empleado que hace uso del sistema.
 * @author pamarti
 * @author alerome
 * @author ivagonz
 */
public class Empleado {
    private String nif, password;
    private LocalDateTime fechaInicio;
    
    /**
     * Constructor de la clase {@code Empleado}.
     * @param n Cadena de caracteres que representa el NIF del empleado. Se utiliza también a modo de nombre de usuario.
     * @param p Cadena de caracteres que representa la contraseña en el sistema del empleado.
     * @param ldt Fecha que representa el momento en que el empleado comenzó a trabajar en el sistema.
     * @throws {@code IllegalArgumentException} en caso de que la longitud del NIF exceda los nueve caracteres.
     */
    public Empleado(String n, String p, LocalDateTime ldt){
        if(nif.length()>9)
            throw new IllegalArgumentException();
        nif=n;
        password=p;
        fechaInicio=ldt;
    }
    
    /**
     * Modifica el NIF de un empleado.
     * @param nif Cadena de caracteres que representa el nuevo NIF del empleado.
     * @throws {@code IllegalArgumentException} en caso de que el nuevo NIF tenga más de nueve caracteres. 
     */
    public void setNif(String nif){
        if(nif.length()>9)
            throw new IllegalArgumentException("La longitud del NIF debe ser como mucho de 9 caracteres");
        this.nif=nif;
    }
    
    /**
     * Obtiene el NIF del empleado.
     * @return Cadena de caracteres que representa el NIF del empleado.
     */
    public String getNif(){
        return nif;
    }
    
    /**
     * Modifica la contraseña del empleado.
     * @param password Cadena de caracteres que representa la nueva contraseña del usuario.
     */
    public void setPassword(String password){
        this.password=password;
    }
    
    /**
     * Devuelve la contraseña del empleado.
     * @return Cadena de caracteres que representa la contraseña del empleado.
     */
    public String getPassword(){
        return password;
    }
    
    /**
     * Modifica la fecha en que el empleado comenzó a trabajar.
     * @param fecha {@code LocalDateTime} que representa la nueva fecha en que el empleado comenzó a trabajar.
     */
    public void setFecha(LocalDateTime fecha){
        fechaInicio=fecha;
    }
    
    /**
     * Obtiene la fecha en que el empleado comenzó a trabajar.
     * @return {@code LocalDateTime} que representa la fecha en que el empleado comenzó a trabajar.
     */
    public LocalDateTime getFecha(){
        return fechaInicio;
    }
    
    /**
     * Comprueba si el empleado está activo en el sistema.
     * @return {@code True} en caso de que el empleado esté activo y {@code false} en caso contrario.
     */
    public boolean isActivo(){
        return DAOEmpleado.empleadoActivo(nif);
    }
    
    /**
     * Devuelve el rol del empleado, pudiendo ser: Almacen, AtencionCliente o Contabilidad.
     * @return Número entero que representa el tipo de rol del empleado.
     */
    public int getRol(){
        return DAOEmpleado.selectRol(nif);
    }
    
    /**
     * Obtiene una instacia del empleado al que corresponden el nombre de usuario y la contraseña introducidos por parámetro.
     * @param user CAdena de caracteres que representa el nombre de usuario del empleado que se desea buscar.
     * @param password Cadena de caracteres que representa la contraseña del empleado que se desea buscar.
     * @return Instancia del empleado que se ha buscado en caso de que exista o {@code null} en caso contrario.
     */
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
