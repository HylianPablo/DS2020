
package es.uva.inf.ds.vinoteca.domain.models;
/**
 *
 * @author alejandro
 */    
import es.uva.inf.ds.vinoteca.persistence.daos.DAOAbonado;
import es.uva.inf.ds.vinoteca.persistence.daos.DAOCompra;
import es.uva.inf.ds.vinoteca.persistence.daos.DAOEmpleado;
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
    
    public Pedido(int numero, int estado, LocalDateTime fechaRealizacion, String notaEntrega, double importe,
                    LocalDateTime fechaRecepcion, LocalDateTime fechaEntrega, int numeroFactura, int numeroAbonado, int codigo){
        this.numero=numero;
        this.estado=estado;
        this.fechaRealizacion=fechaRealizacion;
        this.notaEntrega=notaEntrega;
        this.importe=importe;
        this.fechaRecepcion=fechaRecepcion;
        this.fechaEntrega=fechaEntrega;
        this.numeroFactura=numeroFactura;
        this.numeroAbonado=numeroAbonado;
        this.codigo=codigo;
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
            p = new Pedido(numeroJ,estadoJ,fechaRealizacionJ,notaEntregaJ,importeJ,fechaRecepcionJ,fechaEntregaJ,numeroFactura,numeroAbonadoJ,codPedido); 
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
        String openidref;
        String nif;
        JsonReaderFactory factory = Json.createReaderFactory(null);
        try(JsonReader reader = factory.createReader(new StringReader(abonadoJSONString));){
            JsonObject jsonobject = reader.readObject();
            openidref = jsonobject.getString("openIdRef");
            nif = jsonobject.getString("nif");
            ab = new Abonado(numeroAbonado,openidref,nif);
        }catch(Exception ex){
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE,null,ex);
        }
        return ab;
    }

    public void actualizarEstadoACompletado() {
        estado = 2;
    }
}
