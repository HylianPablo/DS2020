package es.uva.inf.ds.vinoteca.domain.models;

import es.uva.inf.ds.vinoteca.common.CompletadaException;
import es.uva.inf.ds.vinoteca.common.NullCompraException;
import es.uva.inf.ds.vinoteca.persistence.daos.DAOCompra;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;

/**
 * Modelo que representa una compra del sistema.
 * @author pamarti
 * @author alerome
 * @author ivagonz
 */
public class Compra {
    
    private Bodega bodega;
    private ArrayList<LineaCompra> lineasCompra;
    private ArrayList<LineaCompra> lineasCompraNoRecibidas;
    private boolean recibidaCompleta;
    private LocalDateTime fechaCompraCompleta, fechaPago;
    private int idCompra;
    private double importe;
    private String completada;
    
    /**
     * Constructor de la clase.Sólo se utilizan los atributos referentes al caso de uso.
     * @param id Número entero que representa el identificador de la compra.
     * @param i Número real que representa el importe de la compra.
     * @param fp {@code LocalDateTime} que representa la fecha de pago de la compra.
     * @param c Representa si la compra esta completada
     */
    public Compra(int id, double i, LocalDateTime fp, String c){
        lineasCompra = new ArrayList<>();
        importe = i;
        bodega = null;
        recibidaCompleta = false;
        completada = c;
        fechaCompraCompleta = null;
        idCompra = id;
        fechaPago = fp;
        lineasCompraNoRecibidas = new ArrayList<>();
    }
    
    /**
     * Obtiene el identificador de la compra.
     * @return Número entero que representa el identificador de la compra.
     */
    public int getIdCompra(){
        return idCompra;
    }
    
    /**
     * Obtiene el importe de la compra.
     * @return Número real que representa el importe de la compra.
     */
    public double getImporte(){
        return importe;
    }
    
    /**
     * Obtiene la fecha en que se completó el pago de la compra.
     * @return {@code LocalDateTime} que representa la fecha de pago de la compra.
     */
    public LocalDateTime getFechaPago(){
        return fechaPago;
    }
    
    /**
     * Obtiene una instancia de compra a partir de su identificador.
     * @param id Número entero que representa el identificador de la compra.
     * @return Instancia de la compra buscada.
     * @throws {@code NullCompraException} en caso de que la compra buscada no exista.
     * @throws {@code CompletadaException} en caso de que la compra buscada no haya sido completada.
     */
    public static Compra getCompra(int id) throws NullCompraException, CompletadaException  {
        String compraJSONString = DAOCompra.consultaCompra(id);
        String importeJson=null; 
        String fechaJson=null; 
        String completaJson=null;
        JsonReaderFactory factory = Json.createReaderFactory(null);
        try(JsonReader reader = factory.createReader(new StringReader(compraJSONString));){
            JsonObject jsonobject = reader.readObject();
            importeJson = jsonobject.getString("importe");
            fechaJson = jsonobject.getString("fechaPago");
            completaJson = jsonobject.getString("completa");
        }catch(Exception ex){
            throw new NullCompraException("La compra no se encuentra en el sistema");            
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm",Locale.US);
        LocalDateTime fechaPago = LocalDateTime.parse(fechaJson,formatter);
        double importe = Double.parseDouble(importeJson);
        Compra compra = new Compra(id, importe, fechaPago, completaJson);
        return compra;
    }
    
    /**
     * Obtiene la bodega asociada a la compra.
     * @return Instancia de la bodega asociada a la compra.
     */
    public Bodega getBodega(){
        return Bodega.getBodega(idCompra);
    }    
    
    /**
     * Marca al compra tanto como recibida como completa.
     */
    public void marcarRecibidaCompleta(){
        recibidaCompleta = true;
        fechaCompraCompleta = LocalDateTime.now();
    }
    
    /**
     * Obtiene las líneas de compra asociadas a la compra.
     * @return Lista de las instancias de las líneas de compra asociadas a la compra.
     */
    public ArrayList<LineaCompra> getLineasCompra(){
        lineasCompra = LineaCompra.getLineaCompra(idCompra);
        return lineasCompra;
    }
    
    /**
     * Comprueba que la compra se ha completado.
     * @return {@code True} en caso de que la compra esté completa y {@code false} en caso contrario.
     */
    public boolean compruebaCompletado(){
        return recibidaCompleta;
    }
    
    /**
     * Obtiene las líneas de compra no recibidas asociadas a la compra.
     * @return Lista de las instancias de las líneas de compra no recibidas  asociadas a la compra.
     */
    public ArrayList<LineaCompra> getLineasCompraNoRecibidas(){
        return lineasCompraNoRecibidas;
    }
    
    /**
     * Comprueba que todas las líneas de compra asociadas a la compra se han recibido.
     * @param array
     * @return {@code True} en caso de que todas las líneas de compra se hayan recibido y {@code false} en caso contrario.
     */
    public boolean comprobarRecibidas(ArrayList<LineaCompra> array) {
        boolean recibida = true;
        boolean bandera = true;
        for (int i = 0; i < array.size(); i++){
            recibida = array.get(i).comprobarRecibida();
            if (recibida != true){
                bandera = false;
                lineasCompraNoRecibidas.add(array.get(i));
            }
        }
        return bandera;
    }

    public void compruebaCompletada() throws CompletadaException {
        if(completada.equals("1")){
            throw new CompletadaException("La compra ya esta completada");
        }
    }
}
