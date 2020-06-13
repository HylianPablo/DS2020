/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.domain.models;

/**
 *
 * @author alejandro
 */
public class Persona {
    
    private String nif, nombre, apellidos, direccion, telefono, email;
    
    public Persona(String nif, String nombre, String apellidos, String direccion, String telefono, String email){
        if(nif.length()>9)
            throw new IllegalArgumentException("La longitud del NIF excede los nueve caracteres.");
        this.nif = nif;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public String getApellidos(){
        return apellidos;
    }
    
    public String getTelefono(){
        return telefono;
    }
    
    public String getEmail(){
        return email;
    }
    
    public String getNif(){
        return nif;
    }
    
    public void setNif(String s){
        nif = s;
    }
}
