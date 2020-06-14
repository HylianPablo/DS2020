package es.uva.inf.ds.vinoteca.common;

/**
 * Excepción lanzada en caso de que una fecha introducida sea errónea.
 * @author pamarti
 * @author alerome
 * @author ivagonz
 */
public class ReferenciaNoValidaException extends Exception{

    public ReferenciaNoValidaException(String message) {
        super(message);
    }
    
}
