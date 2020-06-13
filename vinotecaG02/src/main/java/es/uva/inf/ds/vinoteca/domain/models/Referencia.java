package es.uva.inf.ds.vinoteca.domain.models;

import es.uva.inf.ds.vinoteca.persistence.daos.DAOEmpleado;
import es.uva.inf.ds.vinoteca.persistence.daos.DAOReferencia;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;

/**
 * Modelo de las referencias que procesa el sistema.
 * @author pamarti
 * @author alerome
 * @author ivagonz
 */
public class Referencia {
    
    boolean esPorCajas, disponible;
    int codigo, contenidoEnCL;
    double precio;
    
    /**
     * Constructor de la clase.
     * @param c Número entero que representa el código de la referencia.
     * @param ce Número entero que representa el contenido en centilitros de la referencia.
     * @param p Número real que representa el precio de la referencia.
     * @param ep Valor booleano que representa si la referencia es por cajas o no.
     * @param d Valor booleano que representa si una referencia está disponible o no.
     */
    public Referencia(int c, int ce, double p, boolean ep, boolean d){
        codigo = c;
        contenidoEnCL = ce;
        precio = p;
        esPorCajas = ep;
        disponible = d;
    }
    
    /**
     * Obtiene el código de la referencia.
     * @return Número entero que representa el identificador de la referencia.
     */
    public int getCodigo(){
        return codigo;
    }
    
    /**
     * Obtiene el contenido en centilitros de la referencia.
     * @return Número entero que representa la cantidad en centilitros de la referencia.
     */
    public int getContenido(){
        return contenidoEnCL;
    }
    
    /**
     * Obtiene el precio de la referencia.
     * @return Número real que representa el precio de la referencia.
     */
    public double getPrecio(){
        return precio;
    }
    
    /**
     * Comprueba si la referencia se efectúa por cajas.
     * @return {@code True} en caso de que la referencia se efectúe por cajas y {@code false} en caso contrario.
     */
    public boolean comprobarPorCajas(){
        return esPorCajas;
    }
    
    /**
     * Comprueba si la referencia se encuentra disponible.
     * @return {@code True} en caso de que la referencia se encuentre disponible y {@code false} en caso contrario.
     */
    public boolean comprobarDisponible(){
        return disponible;
    }

    /**
     * Obtiene la instancia de una referencia a partir de su identificador.
     * @param codigo Número entero que representa el código de la referencia.
     * @return Instancia de la referencia buscada.
     */
    public static Referencia getReferencia(int codigo) {
        String referenciaJSONString = DAOReferencia.consultaReferencia(codigo);
        String precioJson=null; 
        String codigoJson=null;
        String diponibleJson=null;
        String esPorCajasJson=null;
        String contenidoEnCLJson=null;
        JsonReaderFactory factory = Json.createReaderFactory(null);
        try(JsonReader reader = factory.createReader(new StringReader(referenciaJSONString));){
            JsonObject jsonobject = reader.readObject();
            precioJson = jsonobject.getString("precio");
            diponibleJson = jsonobject.getString("disponible");
            esPorCajasJson = jsonobject.getString("porCajas");
            contenidoEnCLJson = jsonobject.getString("contenido");
        }catch(Exception ex){
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE,null,ex);
        }
        double precio = Double.parseDouble(precioJson);
        int contenido = Integer.parseInt(contenidoEnCLJson);
        boolean esPorCajas = Boolean.parseBoolean(esPorCajasJson);
        boolean disponible = Boolean.parseBoolean(diponibleJson);
        Referencia referencia = new Referencia(codigo, contenido, precio, esPorCajas, disponible);
        return referencia;
    }
    
}
