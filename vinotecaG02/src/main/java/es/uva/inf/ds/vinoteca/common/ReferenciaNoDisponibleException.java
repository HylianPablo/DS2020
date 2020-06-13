package es.uva.inf.ds.vinoteca.common;

/**
 * Excepción lanzada en caso de que una fecha introducida sea errónea.
 * @author pamarti
 * @author alerome
 * @author ivagonz
 */
public class ReferenciaNoDisponibleException extends Exception{

    public ReferenciaNoDisponibleException(String message) {
        super(message);
    }
}
