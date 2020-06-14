package es.uva.inf.ds.vinoteca.domain.models;

import es.uva.inf.ds.vinoteca.persistence.daos.DAOBodega;
import es.uva.inf.ds.vinoteca.persistence.daos.DAOEmpleado;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;

/**
 * Modelo que representa una bodega del sistema.
 * 
 * @author pamarti
 * @author alerome
 * @author ivagonz
 */
public class Bodega {

    private String nombre, cif, direccion;

    /**
     * Constructor de la clase.
     * 
     * @param n Cadena de caracteres que representa el nombre de la bodega.
     * @param c Cadena de caracteres que representa el CIF de la bodega. No podrá
     *          ser superior a nueve caracteres.
     * @param d Cadena de caracteres que representa la direccion de la bodega.
     */
    public Bodega(String n, String c, String d) {
        if (c.length() > 9)
            throw new IllegalArgumentException("La longitud del CIF no debe exceder los nueve caracteres.");
        nombre = n;
        cif = c;
        direccion = d;
    }

    /**
     * Obtiene el nombre de la bodega.
     * 
     * @return Cadena de caracteres que representa el nombre de la bodega.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Modifica el nombre de la bodega.
     * 
     * @param nombre Cadena de caracteres que representa el nombre de la bodega.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el CIF de la bodega.
     * 
     * @return Cadena de caracteres que representa el CIF de la bodega.
     */
    public String getCIF() {
        return cif;
    }
    
    public void setCIF(String c) {
        cif = c;
    }

    /**
     * Modifica el cif de la bodega.
     * 
     * @param cif Cadena de caracteres que representa el cif de la bodega.
     */
    public void setCif(String cif) {
        if (cif.length() > 9)
            throw new IllegalArgumentException("La longitud del CIF no debe exceder los nueve caracteres.");
        this.cif = cif;
    }

    /**
     * Obtiene la direccion de la bodega.
     * 
     * @return Cadena de caracteres que representa la direccion de la bodega.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Modifica la direccion de la bodega.
     * 
     * @param direccion Cadena de caracteres que representa la direccion de la
     *                  bodega.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene uns instancia de una bodega a partir de su identificador.
     * 
     * @param id Número entero que representa el identificador de la bodega a
     *           obtener.
     * @return Instancia de la bodega buscada.
     */
    public static Bodega getBodega(int id) {
        String bodegaJSONString = DAOBodega.consultaBodega(id);
        String nombreJson = null;
        String cifJson = null;
        String direccionJson = null;
        JsonReaderFactory factory = Json.createReaderFactory(null);
        try (JsonReader reader = factory.createReader(new StringReader(bodegaJSONString));) {
            JsonObject jsonobject = reader.readObject();
            nombreJson = jsonobject.getString("nombre");
            cifJson = jsonobject.getString("cif");

            direccionJson = jsonobject.getString("direccion");
        } catch (Exception ex) {
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE, null, ex);
        }
        Bodega bodega = new Bodega(nombreJson, cifJson, direccionJson);
        return bodega;
    }
}
