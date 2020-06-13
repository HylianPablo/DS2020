package es.uva.inf.ds.vinoteca.domain.models;

import es.uva.inf.ds.vinoteca.persistence.daos.DAOAbonado;
import es.uva.inf.ds.vinoteca.persistence.daos.DAOEmpleado;
import es.uva.inf.ds.vinoteca.persistence.daos.DAOFactura;
import es.uva.inf.ds.vinoteca.persistence.daos.DAOPedido;
import es.uva.inf.ds.vinoteca.persistence.daos.DAOPersona;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import javax.json.JsonWriter;

/**
 * Modelo de los pedidos que procesa el sistema.
 * @author pamarti
 * @author alerome
 * @author ivagonz
 */
public class Pedido {

    private int numero, numeroFactura, numeroAbonado, estado, codigo;
    private LocalDateTime fechaRealizacion, fechaRecepcion, fechaEntrega;
    private String notaEntrega;
    private double importe;
    
    /**
     * Constructor de la clase Pedido.
     * @param numero Número entero encargado de identificar el pedido.
     * @param estado Número entero que representa el estado en que se encuentra el pedido.
     * @param fechaRealizacion Fecha en que se realizó el pedido.
     * @param notaEntrega Cadena de caractereres que representa la nota de entrega del pedido.
     * @param importe Número real que representa el importe del pedido.
     * @param fechaRecepcion Fecha en que se confirmó la recepción del pedido.
     * @param fechaEntrega Fecha en que se confirmó la entrega del pedido.
     * @param numeroFactura Número de la factura asociada al pedido.
     * @param numeroAbonado Número del abonado asociado al pedido.
     */
    public Pedido(int numero, int estado, LocalDateTime fechaRealizacion, String notaEntrega, double importe,
                    LocalDateTime fechaRecepcion, LocalDateTime fechaEntrega, int numeroFactura, int numeroAbonado){
        this.numero=numero;
        this.estado=estado;
        this.fechaRealizacion=fechaRealizacion;
        this.notaEntrega=notaEntrega;
        this.importe=importe;
        this.fechaRecepcion=fechaRecepcion;
        this.fechaEntrega=fechaEntrega;
        this.numeroFactura=numeroFactura;
        this.numeroAbonado=numeroAbonado;
    }
    
    public Pedido(int estado, LocalDateTime fechaRealizacion, String notaEntrega, double importe,
                    LocalDateTime fechaRecepcion, LocalDateTime fechaEntrega, int numeroFactura, int numeroAbonado){
        this.estado=estado;
        this.fechaRealizacion=fechaRealizacion;
        this.notaEntrega=notaEntrega;
        this.importe=importe;
        this.fechaRecepcion=fechaRecepcion;
        this.fechaEntrega=fechaEntrega;
        this.numeroFactura=numeroFactura;
        this.numeroAbonado=numeroAbonado;
    }
    
    /**
     * Devuelve el número que identifica el pedido.
     * @return Número entero que identifica el pedido.
     */
    public int getNumeroPedido(){
        return numero;
    }

    public static Pedido getPedido(int codPedido){
        String pedidoJSONString = DAOPedido.consultaPedido(codPedido);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm",Locale.US);
        int numeroJ;
        int estadoJ;
        int numeroFactura;
        int numeroAbonadoJ;
        LocalDateTime fechaRealizacionJ;
        String notaEntregaJ;
        double importeJ;
        LocalDateTime fechaRecepcionJ;
        LocalDateTime fechaEntregaJ;
        Pedido p = null;
        
        JsonReaderFactory factory = Json.createReaderFactory(null);
        try(JsonReader reader = factory.createReader(new StringReader(pedidoJSONString));){
            JsonObject obj = reader.readObject();
            numeroJ = Integer.parseInt(obj.getString("numero"));
            estadoJ = Integer.parseInt(obj.getString("estado"));
            fechaRealizacionJ = LocalDateTime.parse(obj.getString("fechaRealizacion"),formatter);
            notaEntregaJ = obj.getString("notaEntrega");
            importeJ = Double.parseDouble(obj.getString("importe"));
            fechaRecepcionJ = LocalDateTime.parse(obj.getString("fechaRecepcion"),formatter);
            fechaEntregaJ = LocalDateTime.parse(obj.getString("fechaEntrega"),formatter);
            numeroFactura = Integer.parseInt(obj.getString("numeroFactura"));
            numeroAbonadoJ = Integer.parseInt(obj.getString("numeroAbonado"));
            p = new Pedido(numeroJ,estadoJ,fechaRealizacionJ,notaEntregaJ,importeJ,fechaRecepcionJ,fechaEntregaJ,numeroFactura,numeroAbonadoJ); 
        }catch(Exception ex){
            Logger.getLogger(Factura.class.getName()).log(Level.SEVERE,null,ex);
        }
        return p;
    }
    
    /**
     * Modifica el número que identifica el pedido.
     * @param n Número entero que pasará a representar el pedido.
     */
    public void setNumeroPedido(int n){
        numero=n;
    }
    
    /**
     * Devuelve el estado en que se encuentra el pedido.
     * @return Número entero que representa el estado en que se encuentra el pedido.
     */
    public int getEstado(){
        return estado;
    }
    
    /**
     * Modifica el estado en que se encuentra el pedido.
     * @param e Número entero que representa el nuevo estado del pedido.
     */
    public void setEstado(int e){
        estado=e;
    }
    
    /**
     * Devuelve la fecha en que se realizó el pedido.
     * @return {@code LocalDateTime} que representa cuando se realizó el pedido.
     */
    public LocalDateTime getFechaRealizacion(){
        return fechaRealizacion;
    }
    
    /**
     * Modifica la fecha en que se realizó el pedido.
     * @param ldt {@code LocalDateTime} que representa la nueva fecha que representará cuando se realizó el pedido.
     */
    public void setFechaRealiazacion(LocalDateTime ldt){
        fechaRealizacion=ldt;
    }
    
    /**
     * Devuelve la nota de entrega del pedido.
     * @return Cadena de caracteres que representa la nota de entrega del pedido.
     */
    public String getNotaEntrega(){
        return notaEntrega;
    }
    
    /**
     * Modifica la nota de entrega del pedido.
     * @param n Cadena de caracteres que representa la nueva nota de entrega del pedido.
     */
    public void setNotaEntrega(String n){
        notaEntrega = n;
    }
    
    /**
     * Devuelve el importe del pedido.
     * @return Número real que representa el importe del pedido.
     */
    public double getImporte(){
        return importe;
    }
    
    /**
     * Modifica el importe del pedido.
     * @param d Número real que representael nuevo importe del pedido.
     */
    public void setImporte(double d){
        importe=d;
    }
    
    /**
     * Obtiene la fecha en que se confirmó la recepción del pedido.
     * @return {@code LocalDateTime} que representa la fecha de recepción del pedido.
     */
    public LocalDateTime getFechaRecepcion(){
        return fechaRecepcion;
    }
    
    /**
     * Modifica la fecha en que se confirmó la recepción del pedido.
     * @param ldt {@code LocalDateTime} que representa la nueva fecha de recepción del pedido.
     */
    public void setFechaRecepcion(LocalDateTime ldt){
        fechaRecepcion = ldt;
    }
    
    /**
     * Obtiene la fecha de entrega del pedido.
     * @return {@code LocalDateTime} que representa la fecha de entrega del pedido.
     */
    public LocalDateTime getFechaEntrega(){
        return fechaEntrega;
    }
    
    /**
     * Modifica la fecha de entrega del pedido.
     * @param ldt {@LocalDateTime} que representa la nueva fecha de entrega del pedido.
     */
    public void setFechaEntrega(LocalDateTime ldt){
        fechaEntrega = ldt;
    }
    
    /**
     * Obtiene el número de la factura asociada al pedido.
     * @return Número entero que representa el número de factura asociado al pedido.
     */
    public int getNumeroFactura(){
        return numeroFactura;
    }
    
    /**
     * Modifica el número de la factura asociada al pedido.
     * @param n Número entero que representa el nuevo número de factura asociado al pedido.
     */
    public void setNumeroFactura(int n){
        numeroFactura=n;
    }
    
    /**
     * Obtiene el número del abonado asociado al pedido.
     * @return Número entero que representa el número de abonado asociado al pedido.
     */
    public int getNumeroAbonado(){
        return numeroAbonado;
    }
    
    /**
     * Modifica el número de abonado asociado al pedido.
     * @param n Número entero que representa el nuevo número de abonado asociado al pedido.
     */
    public void setNumeroAbonado(int n){
        numeroAbonado = n;
    }
    
    /**
     * Devuelve el abonado asociado al pedido, tomando como precondición que la base de datos está bien formada y no hay pedidos sin abonados.
     * @return Instancia del abonado asociado al pedido.
     */
    public Abonado getAbonado(){
        Abonado ab = null;
        String abonadoJSONString = DAOAbonado.consultaAbonado(numeroAbonado);
        String openidref=null;
        String nif=null;
        String nifJson=null; 
        String nombreJson=null;
        String direccionJson=null;
        String apellidosJson=null;
        String telefonoJson=null;
        String emailJson=null;
        JsonReaderFactory factory = Json.createReaderFactory(null);
        try(JsonReader reader = factory.createReader(new StringReader(abonadoJSONString));){
            JsonObject jsonobject = reader.readObject();
            openidref = jsonobject.getString("openIdRef");
            nif = jsonobject.getString("nif");
        }catch(Exception ex){
            Logger.getLogger(DAOAbonado.class.getName()).log(Level.SEVERE,null,ex);
        }
                String personaJSONString = DAOPersona.consultaPersona(nif);
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
        Abonado abonado = new Abonado(numeroAbonado, openidref, nif, nombreJson, apellidosJson, direccionJson, telefonoJson, emailJson);
        return abonado;
    }

    /**
     * Actualiza el estado del pedido a completado.
     */
    public void actualizarEstadoACompletado() {
        estado = 2;
    }
    
    /**
     * Comprueba si un pedido ha vencido.
     * @return {@code True} en caso de que el pedido haya vencido y {@code false} en caso contrario.
     */
    public boolean comprobarNoVencido(){
        return DAOFactura.comprobarNoVencido(numeroFactura);
    }

    public LineaPedido crearLineaPedido(int idReferencia, int cantidad) {
        LineaPedido p = new LineaPedido(idReferencia, numero, cantidad);
        return p;
    }

    public void cambiarEstadoPendiente() {
        estado = 0;
    }
    
    public String getJson() {
        String newPedidoJSONString = "";
        JsonObject abonadoJSON = Json.createObjectBuilder()
                .add("numero",Integer.toString(numero))
                .add("estado",Integer.toString(estado))
                .add("fechaRealizacion",fechaRealizacion.toString())
                .add("notaEntrega", notaEntrega)
                .add("importe",Double.toString(importe))
                .add("fechaRecepcion",fechaRecepcion.toString())
                .add("fechaEntrega",fechaEntrega.toString())
                .add("numeroFactura",Integer.toString(numeroFactura))
                .add("numeroAbonado",Integer.toString(numeroAbonado))
                .add("codigo",Integer.toString(codigo))
                .build();
        try(
                StringWriter stringWriter = new StringWriter();
                JsonWriter writer = Json.createWriter(stringWriter);
                ){
           
            writer.writeObject(abonadoJSON);
            newPedidoJSONString = stringWriter.toString();
        }catch(Exception ex){
            Logger.getLogger(DAOAbonado.class.getName()).log(Level.SEVERE,null,ex);
        }
        return newPedidoJSONString;
    }
}
