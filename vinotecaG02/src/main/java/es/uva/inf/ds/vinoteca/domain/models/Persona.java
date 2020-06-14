package es.uva.inf.ds.vinoteca.domain.models;

/**
 * Modelo que representa una persona del sistema.
 * @author pamarti
 * @author alerome
 * @author ivagonz
 */
public class Persona {
    
    private String nif, nombre, apellidos, direccion, telefono, email;
    
    /**
     * Constructor de la clase persona.
     * @param nif Cadena de caracteres que representa el NIF de la persona.
     * @param nombre Cadena de caracteres que representa el nombre de la persona.
     * @param apellidos Cadena de caractereres que representa los apellidos de la persona.
     * @param direccion Cadena de caracteres que representa la dirección de la persona.
     * @param telefono Cadena de caracteres que representa el teléfono de la persona.
     * @param email Cadena de caracteres que representa el email de la persona.
     */
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
    
    /**
     * Obtiene el nombre de la persona.
     * @return Cadena de caracteres que representa el nombre de la persona.
     */
    public String getNombre(){
        return nombre;
    }
    
    /**
     * Obtiene los apellidos de la persona.
     * @return Cadena de caracteres que representa los apellidos de la persona.
     */
    public String getApellidos(){
        return apellidos;
    }
    
    /**
     * Obtiene el teléfono de la persona.
     * @return Cadena de caracteres que representa el teléfono de la persona.
     */
    public String getTelefono(){
        return telefono;
    }
    
    /**
     * Obtiene la direccion de la persona.
     * @return Cadena de caracteres que representa la direccion de la persona.
     */
    public String getDireccion(){
        return direccion;
    }
    
    /**
     * Obtiene el email de la persona.
     * @return Cadena de caracteres que representa el email de la persona.
     */
    public String getEmail(){
        return email;
    }
    
    /**
     * Obtiene el NIF de la persona.
     * @return Cadena de caracteres que representa el NIF de la persona.
     */
    public String getNif(){
        return nif;
    }
    
    /**
     * Modifica el NIF de la persona.
     * @param s Cadena de caracteres que representa el nuevo NIF de la persona.
     */
    public void setNif(String s){
        if(s.length()>9)
            throw new IllegalArgumentException("La longitud del NIF excede los nueve caracteres.");
        nif = s;
    }
}
