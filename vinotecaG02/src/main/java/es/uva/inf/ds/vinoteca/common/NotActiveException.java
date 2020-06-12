package es.uva.inf.ds.vinoteca.common;

/**
 * Excepción lanzada en caso de que el empleado no se encuentre activo en el momento.
 * @author pamarti
 * @author alerome
 * @author ivagonz
 */
public class NotActiveException extends Exception {
    
    public NotActiveException(String message){
        super(message);
    }
}
