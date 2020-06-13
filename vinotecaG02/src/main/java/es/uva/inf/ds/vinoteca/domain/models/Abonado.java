package es.uva.inf.ds.vinoteca.domain.models;

import es.uva.inf.ds.vinoteca.common.AbonadoNotExistsException;
import es.uva.inf.ds.vinoteca.common.CompletadaException;
import es.uva.inf.ds.vinoteca.common.NullCompraException;
import es.uva.inf.ds.vinoteca.persistence.daos.DAOAbonado;
import es.uva.inf.ds.vinoteca.persistence.daos.DAOCompra;
import es.uva.inf.ds.vinoteca.persistence.daos.DAOEmpleado;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;

/**
 * Modelo que representa un abonado del sistema.
 * @author pamarti
 * @author alerome
 * @author ivagonz
 */
public class Abonado {

    private int numeroAbonado;
    private String openidref, nif;
    
    /**
     * Constructor de la clase.
     * @param numeroAbonado Número entero que representa el número de abonado en el sistema.
     * @param openidref Cadena de caracteres que representa el identificador de la referencia.
     * @param nif Cadena de caracteres que representa el NIF del abonado.
     * @throws {@code IllegalArgumentException} en caso de que la longitud del NIF supere los nueve caracteres.
     */
    public Abonado(int numeroAbonado, String openidref, String nif){
        if(nif.length()>9)
            throw new IllegalArgumentException("La longitud del NIF excede los nueve caracteres.");
        this.numeroAbonado = numeroAbonado;
        this.openidref = openidref;
        this.nif = nif;
    }
    
    /**
     * Obtiene el número identificador del abonado.
     * @return Número entero que representa al abonado.
     */
    public int getNumeroAbonado(){
        return numeroAbonado;
    }
    
    public static Abonado getAbonado(int id) throws AbonadoNotExistsException {
        String abonadoJSONString = DAOAbonado.consultaAbonado(id);
        String openIdRefJson=null; 
        String nifJson=null; 
        JsonReaderFactory factory = Json.createReaderFactory(null);
        try(JsonReader reader = factory.createReader(new StringReader(abonadoJSONString));){
            JsonObject jsonobject = reader.readObject();
            openIdRefJson = jsonobject.getString("OPENIDREF");
            nifJson = jsonobject.getString("NIF");
        }catch(Exception ex){
            throw new AbonadoNotExistsException("No existe un abonado con ese numero");
            //Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE,null,ex);            
        }
        Abonado abonado = new Abonado(id, openIdRefJson, nifJson);
        return abonado;
    }
    
    /**
     * Modifica el número identificador del abonado.
     * @param n Número entero que pasará a representar al abonado.
     */
    public void setNumeroAbonado(int n){
        numeroAbonado=n;
    }
    
    /**
     * Obtiene el identificador de la referencia del abonado.
     * @return Cadena de caracteres que representa el identificador de la referencia del abonado.
     */
    public String getOpenIdRef(){
        return openidref;
    }
    
    /**
     * Modifica el identificador de la referencia del abonado.
     * @param s Cadena de caracteres que representa el nuevo identificador de la referencia del abonado.
     */
    public void setOpenIdRef(String s){
        openidref=s;
    }
    
    /**
     * Obtiene el NIF del abonado.
     * @return Cadena de caracteres que representa el NIF del abonado.
     */
    public String getNIF(){
        return nif;
    }
    
    /**
     * Modifica el NIF del abonado.
     * @param s Cadena de caracteres que representa el nuevo NIF del abonado.
     * @throws {@code IllegalArgumentException} en caso de que la longitud del NIF exceda los nueve caracteres.
     */
    public void setNIF(String s){
        if(s.length()>9)
            throw new IllegalArgumentException("La longitud del NIF debe ser como mucho de 9 caracteres");
        nif=s;
    }
    
    
}
