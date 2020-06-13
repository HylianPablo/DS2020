package es.uva.inf.ds.vinoteca.domain.models;

import es.uva.inf.ds.vinoteca.persistence.daos.DAOEmpleado;
import es.uva.inf.ds.vinoteca.persistence.daos.DAOLineaCompra;
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
 * Modelo que representa una línea de compra del sistema.
 * @author pamarti
 * @author alerome
 * @author ivagonz
 */
public class LineaCompra {
    
    private int unidades, codigo, codigoLinea, idCompra;
    private String referencia;
    private boolean recibida;
    private LocalDateTime fechaRecepcion;
    
    /**
     * Constructor de la clase.
     * @param u Número entero que representa las unidades del producto de la línea de compra.
     * @param fr {@code LocalDateTime} que representa la fecha en que se recibió la línea de compra.
     * @param c Número entero que representa el código de la referencia asociada.
     * @param r Valor booleano que indica si la línea de compra ha sido recibida o no.
     * @param cL Número entero que representa el código de la línea compra.
     */
    public LineaCompra(int u, LocalDateTime fr, int c, boolean r, int cL, int idCompra){
        unidades = u;
        recibida = r;
        fechaRecepcion = fr;
        codigo = c;
        codigoLinea = cL;
        this.idCompra=idCompra;
    }
    
    /**
     * Obtiene las líneas de pedido asociadas a la línea de compra.
     * @return Lista de líneas de compra.
     */
    public ArrayList<LineaPedido> actualizaLineasPedido(){ //nombre de mierda CAMBIAR
        return LineaPedido.getLineasPedido(codigo);
    }
    
    /**
     * Comprueba si la línea de compra está marcada como recibida.
     * @return {@code True} si se ha recibido la línea de compra y {@code false} en caso contrario.
     */
    public boolean comprobarRecibida() {
        return recibida;
    }
    
    /**
     * Obtiene el número de unidades de la línea de compra.
     * @return Número entero que representa el número de unidades de la línea de compra.
     */
    public int getUnidades(){
        return unidades;
    }
    
    /**
     * Marca como recibida la línea de compra.
     */
    public void marcarRecibido(){
        recibida = true;
        fechaRecepcion = LocalDateTime.now();
    }
    
    /**
     * Obtiene la referencia asociada a la línea de compra.
     * @return Instancia de la referencia asociada a la línea de compra.
     */
    public Referencia getReferencia(){
        return Referencia.getReferencia(codigo);
    }
    
    /**
     * Obtiene el identificador de la línea de compra.
     * @return Número entero que representa el identificador de la línea de compra.
     */
    public int getCodigoLinea(){
        return codigoLinea;
    }
    
    /**
     * Obtiene las líneas de compra asociadas a una compra.
     * @param idCompra Número entero que representa el identificador de una compra.
     * @return Lista de las líneas de compra asociadas a una compra.
     */
    public static ArrayList<LineaCompra> getLineaCompra(int idCompra) {
        String lineasCompraJSONString = DAOLineaCompra.consultaLineaCompra(idCompra);
        int unidades = 0;
        int codigo = 0;
        int codigoL = 0;
        LocalDateTime fechaRecepcion;
        boolean rec = false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm",Locale.US);
        ArrayList<LineaCompra> lcompras = new ArrayList<>();
        JsonReaderFactory factory = Json.createReaderFactory(null);
        try(JsonReader reader = factory.createReader(new StringReader(lineasCompraJSONString));){
            JsonObject mainObj = reader.readObject();
            JsonArray array = mainObj.getJsonArray("lineasCompra");
            //coger del json
            for(int i=0;i<array.size();i++){
                JsonObject obj = (JsonObject) array.get(i);
                unidades = Integer.parseInt(obj.getString("unidades"));
                rec = Boolean.parseBoolean("recibidas");
                fechaRecepcion = LocalDateTime.parse(obj.getString("fechaRecepcion"),formatter);
                codigo = Integer.parseInt(obj.getString("codigos"));
                codigoL = Integer.parseInt(obj.getString("ids"));
                LineaCompra lc = new LineaCompra(unidades, fechaRecepcion, codigo, rec, codigoL);
                lcompras.add(lc);
            }
        }catch(Exception ex){
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE,null,ex);
        }
        return lcompras;
    }
}
