
package es.uva.inf.ds.vinoteca.domain.models;

import es.uva.inf.ds.vinoteca.persistence.daos.DAOFactura;
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
 * Modelo de las facturas utilizadas en el sistema.
 * @author pamarti
 * @author alerome
 * @author ivagonz
 */
public class Factura {
    private int numeroFactura,estado;
    private LocalDateTime fechaEmision,fechaPago;
    private double importe;
    private String idExtractoBancario;
    
    /**
     * Constructor de la clase Factura.
     * @param numeroFactura Numero entero que representa el número de la factura.
     * @param fechaEmision Fecha en que se emitió la factura.
     * @param importe Número real que representa el importe de la factura.
     * @param estado Número entero que representa el estado de la factura.
     * @param fechaPago Fecha en que se pagó la factura.
     * @param idExtractoBancario Cadena de caracteres que representa el extracto bancario de la factura.
     */
    public Factura(int numeroFactura, LocalDateTime fechaEmision, double importe, int estado, LocalDateTime fechaPago,
                    String idExtractoBancario){
        this.numeroFactura=numeroFactura;
        this.fechaEmision = fechaEmision;
        this.importe = importe;
        this.estado=estado;
        this.fechaPago=fechaPago;
        this.idExtractoBancario=idExtractoBancario;
    }

    Factura(int unidades, LocalDateTime fechaRecepcion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Obtiene el número de la factura.
     * @return Número entero que identifica la factura.
     */
    public int getNumeroFactura(){
        return numeroFactura;
    }
    
    /**
     * Modifica el número de la factura.
     * @param n Número entero que representa el nuevo número de la factura.
     */
    public void setNumeroFactura(int n){
        numeroFactura=n;
    }
    
    /**
     * Obtiene la fecha en que se emitió la factura.
     * @return Fecha en que se emitió la factura.
     */
    public LocalDateTime getFechaEmision(){
        return fechaEmision;
    }
    
    /**
     * Modifica la fecha en que se emitió la factura.
     * @param ldt Nueva fecha que indica cuando se emitió la factura.
     */
    public void setFechaEmision(LocalDateTime ldt){
        fechaEmision=ldt;
    }
    
    /**
     * Obtiene el importe de la factura.
     * @return número real que representa el importe de la factura.
     */
    public double getImporte(){
        return importe;
    }
    
    /**
     * Modifica el importe de la factura.
     * @param d Número real que representa el nuevo importe de la factura.
     */
    public void setImporte(double d){
        importe=d;
    }
    
    /**
     * Obtiene el estado de la factura.
     * @return Número entero que representa el estado de la factura.
     */
    public int getEstado(){
        return estado;
    }
    
    /**
     * Modifica el estado de la factura.
     * @param e Número entero que representa el nuevo estado de la factura.
     */
    public void setEstado(int e){
        estado=e;
    }
    
    /**
     * Obtiene la fecha en que se realizó el pago de la factura.
     * @return Fecha en que se realizó el pago de la factura.
     */
    public LocalDateTime getFechaPago(){
        return fechaPago;
    }
    
    /**
     * Modifica la fecha en que se realizó el pago de la factura.
     * @param ldt Fecha actualizada en que se realizó el pago de la factura.
     */
    public void setFechaPago(LocalDateTime ldt){
        fechaPago=ldt;
    }
    
    /**
     * Obtiene el extracto bancario de la factura.
     * @return Cadena de caracteres que representa el extracto bancario de la factura.
     */
    public String getIdExtractoBancario(){
        return idExtractoBancario;
    }
    
    /**
     * Modifica el extracto bancario de la factura.
     * @param s Cadena de caracteres que representa el nuevo extracto bancario de la factura.
     */
    public void setIdExtractoBancario(String s){
        idExtractoBancario=s;
    }
    
    /**
     * Obtiene los pedidos de una factura.
     * @return {@code ArrayList} que contiene los pedidos de una factura. No se contemplan facturas sin pedidos.
     */
    public ArrayList<Pedido> getPedidosAsociados(){
        String pedidosJSONString = "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm",Locale.US);
        int numeroJ;
        int estadoJ;
        LocalDateTime fechaRealizacionJ;
        String notaEntregaJ;
        double importeJ;
        LocalDateTime fechaRecepcionJ;
        LocalDateTime fechaEntregaJ;
        int numeroAbonadoJ;
        ArrayList<Pedido> pedidos = new ArrayList<>();
        
        pedidosJSONString = DAOPedido.selectPedidosAsociados(this.numeroFactura);
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
                numeroAbonadoJ = Integer.parseInt(obj.getString("numeroAbonado"));
                Pedido p = new Pedido(numeroJ,estadoJ,fechaRealizacionJ,notaEntregaJ,importeJ,fechaRecepcionJ,fechaEntregaJ,this.numeroFactura,numeroAbonadoJ);
                pedidos.add(p);
            }
        }catch(Exception ex){
            Logger.getLogger(Factura.class.getName()).log(Level.SEVERE,null,ex);
        }
        return pedidos;
    }
    
    
    /**
     * Obtiene las facturas emitidas treinta días antes de la fecha introducida.
     * @param fecha Cadena de caracteres que representa la fecha utilizada como límite.
     * @return Lista de facturas que cumplen el plazo inferior a treinta días antes de la fecha.
     */
    public static ArrayList<Factura> consultaFacturasAntesDeFecha(String fecha){
        ArrayList<Factura> facturas = new ArrayList<>();
        int numeroFactura;
        LocalDateTime fechaEmision;
        double importe;
        int estado;
        LocalDateTime fechaPago;
        String idExtractoBancario;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm",Locale.US);
        
        String facturasJSONString = DAOFactura.consultaFacturasAntesDeFecha(fecha);
        JsonReaderFactory factory = Json.createReaderFactory(null);
        try(JsonReader reader = factory.createReader(new StringReader(facturasJSONString));){
            JsonObject mainObj = reader.readObject();
            JsonArray array = mainObj.getJsonArray("facturas");
            for(int i=0;i<array.size();i++){
                JsonObject obj = (JsonObject) array.get(i);
                numeroFactura = Integer.parseInt(obj.getString("numeroFactura"));
                fechaEmision = LocalDateTime.parse(obj.getString("fechaEmision"),formatter);
                importe = Double.parseDouble(obj.getString("importe"));
                estado = Integer.parseInt(obj.getString("estado"));
                fechaPago = LocalDateTime.parse(obj.getString("fechaPago"),formatter);
                idExtractoBancario = obj.getString("idExtractoBancario");
                Factura f = new Factura(numeroFactura,fechaEmision,importe,estado,fechaPago,idExtractoBancario);
                facturas.add(f);
            }
        }catch(Exception ex){
            Logger.getLogger(Factura.class.getName()).log(Level.SEVERE,null,ex);
        }
        return facturas;
    }
}
