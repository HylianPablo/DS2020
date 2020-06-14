package es.uva.inf.ds.vinoteca.persistence.daos;

import es.uva.inf.ds.vinoteca.domain.models.Empleado;
import es.uva.inf.ds.vinoteca.persistence.dbaccess.DBConnection;
import es.uva.inf.ds.vinoteca.userinterface.VistaAlmacen;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import javax.json.JsonWriter;

/**
 * Clase que representa el acceso a la tabla de la base de datos que contiene los datos de las líneas de pedido del sistema.
 * @author pamarti
 * @author alerome
 * @author ivagonz
 */
public class DAOLineaPedido {

     /**
     * Obtiene un JSON en forma de cadena de caracteres representando las líneas de pedido que se desean buscar en la base de datos. En caso de no existir retorna cadena vacía.
     * @param id Número entero que representa el identificador del pedido a la cual pertenecen las líneas de pedido que se quieren buscar.
     * @return JSON en forma de cadena de caracteres que representa las líneas de pedido en caso de que existan o cadena vacía en caso contrario.
     */
    public static String consultaLineasPedido(int id){
        String lineaPedidoJSONString = "";
        int numeroUnidades = 0;
        int counter = 0;
        String comp = null;
        boolean completada = false;
        int numeroPedido = 0;
        int codigo = 0;
        ArrayList<Integer> unidades = new ArrayList<>();
        ArrayList<Integer> numerosPedido = new ArrayList<>();
        ArrayList<Boolean> completadas = new ArrayList<>();
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        ResultSet result = null;
        try(
            PreparedStatement ps = connection.getStatement("SELECT * FROM LINEAPEDIDO lp WHERE lp.IdLineaCompra= ? ");   
        ){
            ps.setInt(1, id);
            result = ps.executeQuery();
            if(result!=null){
            while(result.next()){
                counter ++;
                numeroUnidades = result.getInt("unidades");
                comp = result.getString("completada");
                numeroPedido = result.getInt("numeropedido");
                if(comp.equals("1")){
                    completada = true;
                }
                numerosPedido.add(numeroPedido);
                unidades.add(numeroUnidades);
                completadas.add(completada);
            }
            }
            
        }catch(SQLException ex){
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE,null,ex);
        }finally{
            try {
                result.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOLineaPedido.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        connection.closeConnection();
        if(counter!=0){
            lineaPedidoJSONString = obtainLineaPedidoJSONString(unidades, completadas, numerosPedido);
        }
        return lineaPedidoJSONString;
    }
   
    private static String obtainLineaPedidoJSONString(ArrayList<Integer> unidades, ArrayList<Boolean> completadas, ArrayList<Integer> numerosPedido) {
        String lineaPedidoJSONString = "";
        //JsonReaderFactory factory = Json.createReaderFactory(null);
        JsonArrayBuilder array = Json.createArrayBuilder();
        for(int i=0;i<unidades.size();i++){
            array.add(Json.createObjectBuilder().add("unidades",Integer.toString(unidades.get(i)))
            .add("recibidas", Boolean.toString(completadas.get(i)))
            .add("numeros",Integer.toString(numerosPedido.get(i)))
            .build());
        }
        try(
                StringWriter stringWriter = new StringWriter();
                JsonWriter writer = Json.createWriter(stringWriter);
                ){   
            
            JsonObject jsonObj = Json.createObjectBuilder().add("lineasCompra",array).build();
            writer.writeObject(jsonObj);
            lineaPedidoJSONString = stringWriter.toString();
        }catch(Exception ex){
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE,null,ex);
        }
        return lineaPedidoJSONString;
    }

    /**
     * Actualiza una línea de pedido marcándola como completada.
     * @param id Número entero que representa el identificador de la línea de compra a la que va asociada la línea de pedido.
     * @throws {@code SQLException} en caso de que la actualización no se pueda realizar. 
     */
    public static void actualizarLineasDePedido(int id) throws SQLException {
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        try (PreparedStatement ps = connection.getStatement("UPDATE LINEAPEDIDO SET COMPLETADA = ? WHERE IDLINEACOMPRA = ?")) {
            ps.setString(1, "1");
            ps.setInt(2, id);
            ps.executeUpdate();
        }
        catch (SQLException ex) {
            Logger.getLogger(VistaAlmacen.class.getName()).log(Level.SEVERE, null, ex);
        }
        connection.closeConnection();
    }

    /**
     * Añade una nueva línea de pedido a la base de datos.
     * @param jsonNewLinea JSON en forma de cadena de caracteres que contiene los datos de la línea de pedido a insertar en la base de datos.
     */
    public static void añadirLineaPedido(String jsonNewLinea) {
        JsonReaderFactory factory = Json.createReaderFactory(null);
        int codigoReferencia = 1;
        int codigoPedido = 1;
        int unidadesLinea = 1;
        String Completada = "0";
        int IdLineaCompra = 1;
        try(JsonReader reader = factory.createReader(new StringReader(jsonNewLinea));){
            JsonObject jsonobject = reader.readObject();
            codigoReferencia = Integer.parseInt(jsonobject.getString("codReferencia"));
            codigoPedido = Integer.parseInt(jsonobject.getString("codPedido"));
            unidadesLinea = Integer.parseInt(jsonobject.getString("unidades"));
        }catch(Exception ex){
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE,null,ex);
        }
        codigoPedido = getLastCodigo();
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        try (PreparedStatement ps = connection.getStatement("INSERT INTO LINEAPEDIDO (UNIDADES,COMPLETADA,CODIGOREFERENCIA,NUMEROPEDIDO,IDLINEACOMPRA)"
                + "VALUES (?,?,?,?,?)")) {
            ps.setInt(3, codigoReferencia);
            ps.setInt(4, codigoPedido);
            ps.setString(2, Completada);
            ps.setInt(5, IdLineaCompra);
            ps.setInt(1, unidadesLinea);
            ps.executeUpdate();
        }
        catch (SQLException ex) {
            Logger.getLogger(VistaAlmacen.class.getName()).log(Level.SEVERE, null, ex);
        }
        connection.closeConnection();
    }
    
    private static int getLastCodigo() {
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        int codigo = 0;
        ResultSet rs = null;
        try (PreparedStatement ps = connection.getStatement("SELECT MAX(p.NUMERO) FROM PEDIDO p ")) {
            rs = ps.executeQuery();
            if(rs!=null && rs.next()){
                codigo = rs.getInt(1);
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(VistaAlmacen.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOLineaPedido.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        connection.closeConnection();
        return codigo;
    }
}
