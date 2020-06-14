package es.uva.inf.ds.vinoteca.common;

/**
 * Excepción lanzada en caso de que el empleado no exista en el sistema.
 * @author pamarti
 * @author alerome
 * @author ivagonz
 */
public class NullCompraException extends Exception {
    
    public NullCompraException(String message){
        super(message);
    }
    
}
