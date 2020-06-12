package es.uva.inf.ds.vinoteca.common;

/**
 * Excepción lanzada en caso de que el empleado no exista en el sistema.
 * @author pamarti
 * @author alerome
 * @author ivagonz
 */
public class DNIPassNotValidException extends Exception {
    
    public DNIPassNotValidException(String message){
        super(message);
    }
    
}
