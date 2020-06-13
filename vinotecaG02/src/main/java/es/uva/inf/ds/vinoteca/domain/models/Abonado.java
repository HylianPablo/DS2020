package es.uva.inf.ds.vinoteca.domain.models;

import es.uva.inf.ds.vinoteca.persistence.daos.DAOPersona;
import es.uva.inf.ds.vinoteca.common.AbonadoNotExistsException;
import es.uva.inf.ds.vinoteca.persistence.daos.DAOAbonado;
import es.uva.inf.ds.vinoteca.persistence.daos.DAOPedido;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;

/**
 * Modelo que representa un abonado del sistema.
 * @author pamarti
 * @author alerome
 * @author ivagonz
 */
public class Abonado extends Persona{

    private int numeroAbonado;
    private String openidref;
    
    /**
     * Constructor de la clase.
     * @param numeroAbonado Número entero que representa el número de abonado en el sistema.
     * @param openidref Cadena de caracteres que representa el identificador de la referencia.
     * @param nif Cadena de caracteres que representa el NIF del abonado.
     * @throws {@code IllegalArgumentException} en caso de que la longitud del NIF supere los nueve caracteres.
     */
    
    
    public Abonado(int numeroAbonado, String openidref, String nif, String nombre, String apellidos, String direccion, String telefono, String email){
        super(nif, nombre, apellidos, direccion, telefono, email);

        this.numeroAbonado = numeroAbonado;
        this.openidref = openidref;
    }
    
    /**
     * Obtiene el número identificador del abonado.
     * @return Número entero que representa al abonado.
     */
    public int getNumeroAbonado(){
        return numeroAbonado;
    }
    
    /**
     * Obtiene la instancia de un abonado a partir de su identificador.
     * @param id Número entero que representa el identificador del abonado.
     * @return Instancia del abonado buscado.
     * @throws {@code AbonadoNotExistsException} en caso de que el abonado no exista. 
     */
    public static Abonado getAbonado(int id) throws AbonadoNotExistsException {
        String abonadoJSONString = DAOAbonado.consultaAbonado(id);
        String openIdRefJson=null; 
        String nifJson=null; 
        String nombreJson=null;
        String direccionJson=null;
        String apellidosJson=null;
        String telefonoJson=null;
        String emailJson=null;
        JsonReaderFactory factory = Json.createReaderFactory(null);
        try(JsonReader reader = factory.createReader(new StringReader(abonadoJSONString));){
            JsonObject jsonobject = reader.readObject();
            openIdRefJson = jsonobject.getString("openIdRef");
            nifJson = jsonobject.getString("nif");
        }catch(Exception ex){
            throw new AbonadoNotExistsException("No existe un abonado con ese numero");
            //Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE,null,ex);            
        }
        String personaJSONString = DAOPersona.consultaPersona(nifJson);
        factory = Json.createReaderFactory(null);
        try(JsonReader reader = factory.createReader(new StringReader(personaJSONString));){
            JsonObject jsonobject = reader.readObject();
            nombreJson = jsonobject.getString("nombre");
            direccionJson = jsonobject.getString("direccion");
            apellidosJson = jsonobject.getString("apellidos");
            telefonoJson = jsonobject.getString("telefono");
            emailJson = jsonobject.getString("email");
        }catch(Exception ex){
            Logger.getLogger(DAOPersona.class.getName()).log(Level.SEVERE,null,ex);            
        }
        Abonado abonado = new Abonado(id, openIdRefJson, nifJson, nombreJson, apellidosJson, direccionJson, telefonoJson, emailJson);
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
    
    public String getNombre(){
        return super.getNombre();
    }
    
    public String getApellidos(){
        return super.getApellidos();
    }
    
    public String getEmail(){
        return super.getEmail();
    }
    
    /*
    public String getTelefono(){
        return super.getTelefono();
    }*/
    
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
        return super.getNif();
    }
    
    /**
     * Modifica el NIF del abonado.
     * @param s Cadena de caracteres que representa el nuevo NIF del abonado.
     * @throws {@code IllegalArgumentException} en caso de que la longitud del NIF exceda los nueve caracteres.
     */
    public void setNIF(String s){
        if(s.length()>9)
            throw new IllegalArgumentException("La longitud del NIF debe ser como mucho de 9 caracteres");
        super.setNif(s);
    }
    
    /**
     * Obtiene los pedidos asociados del abonado.
     * @return Lista de pedidos asociados al abonado.
     */
    public ArrayList<Pedido> getPedidos(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm",Locale.US);
        int numeroJ;
        int estadoJ;
        LocalDateTime fechaRealizacionJ;
        String notaEntregaJ;
        double importeJ;
        LocalDateTime fechaRecepcionJ;
        LocalDateTime fechaEntregaJ;
        int numeroFacturaJ;
        ArrayList<Pedido> pedidos = new ArrayList<>();
        String pedidosJSONString = DAOPedido.consultaPedidoAbonado(numeroAbonado);
        JsonReaderFactory factory = Json.createReaderFactory(null);
        try(JsonReader reader = factory.createReader(new StringReader(pedidosJSONString));){
            JsonObject mainObj = reader.readObject();
            JsonArray array = mainObj.getJsonArray("pedidos");
            for(int i =0; i<array.size();i++){
                JsonObject obj = (JsonObject) array.getJsonObject(i);
                numeroJ = Integer.parseInt(obj.getString("numero"));
                estadoJ = Integer.parseInt(obj.getString("estado"));
                fechaRealizacionJ = LocalDateTime.parse(obj.getString("fechaRealizacion"),formatter);
                notaEntregaJ = obj.getString("notaEntrega");
                importeJ = Double.parseDouble(obj.getString("importe"));
                fechaRecepcionJ = LocalDateTime.parse(obj.getString("fechaRecepcion"),formatter);
                fechaEntregaJ = LocalDateTime.parse(obj.getString("fechaEntrega"),formatter);
                numeroFacturaJ = Integer.parseInt(obj.getString("numeroFactura"));
                Pedido p = new Pedido(numeroJ,estadoJ,fechaRealizacionJ,notaEntregaJ,importeJ,fechaRecepcionJ,fechaEntregaJ,numeroFacturaJ,this.numeroAbonado);
                pedidos.add(p);
            }
        }catch(Exception ex){
            Logger.getLogger(Factura.class.getName()).log(Level.SEVERE,null,ex);
        }
        return pedidos;
    }
    
    
}
