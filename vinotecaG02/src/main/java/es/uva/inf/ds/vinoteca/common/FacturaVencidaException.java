package es.uva.inf.ds.vinoteca.common;

/**
 * Excepción lanzada en caso de que un abonado tenga facturas vencidas e intente generar un nuevo pedido.
 * @author pamarti
 * @author alerome
 * @author ivagonz
 */
public class FacturaVencidaException extends Exception{

    public FacturaVencidaException (String message) {
        super(message);
    }
    
}
