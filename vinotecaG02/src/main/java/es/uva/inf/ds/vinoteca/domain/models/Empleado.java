/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.domain.models;

import es.uva.inf.ds.vinoteca.common.DNIPassNotValidException;
import es.uva.inf.ds.vinoteca.common.NotActiveException;
import es.uva.inf.ds.vinoteca.persistence.daos.DAOEmpleado;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
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
        if(n.length()>9)
            throw new IllegalArgumentException("La longitud del NIF excede los nueve caracteres.");
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
    public boolean isActivo() throws NotActiveException{
        boolean b = DAOEmpleado.empleadoActivo(nif);
        if(!b){
            throw new NotActiveException("El empleado no se encuentra activo actualmente");
        }
        return b;
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
    public static Empleado getEmpleadoPorLoginYPassword(String user, String password) throws DNIPassNotValidException {
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
            }catch(Exception ex){
                throw new DNIPassNotValidException("El empleado no se encuentra en el sistema.");
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm",Locale.US);
            LocalDateTime fechaInicioLDT = LocalDateTime.parse(fechaInicioJson,formatter);
            empleadoLogin = new Empleado(nifJson,passJson,fechaInicioLDT);
        }
        return empleadoLogin;
    }
    
}
