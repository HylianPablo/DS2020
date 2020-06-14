package es.uva.inf.ds.vinoteca.persistence.daos;

import es.uva.inf.ds.vinoteca.domain.models.Empleado;
import es.uva.inf.ds.vinoteca.persistence.dbaccess.DBConnection;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonWriter;

/**
 * Clase que representa el acceso a la tabla de la base de datos que contiene los datos de las líneas de compra del sistema.
 * @author pamarti
 * @author alerome
 * @author ivagonz
 */
public class DAOLineaCompra {
    
    /**
     * Obtiene un JSON en forma de cadena de caracteres representando las líneas de compra que se desean buscar en la base de datos. En caso de no existir retorna cadena vacía.
     * @param id Número entero que representa el identificador de la compra a la cual pertenecen las líneas de compra que se quieren buscar.
     * @return JSON en forma de cadena de caracteres que representa las líneas de compra en caso de que existan o cadena vacía en caso contrario.
     */
    public static String consultaLineaCompra(int id){
        String lineaCompraJSONString = "";
        LocalDateTime fechaRecepcion = null;
        int numeroUnidades = 0;
        int counter = 0;
        String recibida = null;
        boolean rec = false;
        int codigo = 0;
        int idL = 0;
        ArrayList<Integer> unidades = new ArrayList<>();
        ArrayList<Integer> codigos = new ArrayList<>();
        ArrayList<Integer> ids = new ArrayList<>();
        ArrayList<Boolean> recibidas = new ArrayList<>();
        ArrayList<LocalDateTime> fechasRecepcion = new ArrayList<>();
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        ResultSet result = null;
        try(
            PreparedStatement ps = connection.getStatement("SELECT * FROM LINEACOMPRA lc WHERE lc.IdCompra= ? ");   
        ){
            ps.setInt(1, id);
            result = ps.executeQuery();
            while(result.next()){
                rec = false;
                counter ++;
                numeroUnidades = result.getInt("unidades");
                idL = result.getInt("id");
                recibida = result.getString("recibida");
                if(recibida.equals("1")){
                    rec = true;
                }
                codigo = result.getInt("codigoreferencia");
                fechaRecepcion = result.getTimestamp("fecharecepcion").toLocalDateTime();
                unidades.add(numeroUnidades);
                codigos.add(codigo);
                ids.add(idL);
                recibidas.add(rec);
                fechasRecepcion.add(fechaRecepcion);
            }
        }catch(SQLException ex){
            Logger.getLogger(DAOEmpleado.class.getName()).log(Level.SEVERE,null,ex);
        }finally{
            try {
                result.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOLineaCompra.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        connection.closeConnection();
        if(counter!=0){
            lineaCompraJSONString = obtainLineaCompraJSONString(unidades, fechasRecepcion, codigos, recibidas, ids);
        }
        return lineaCompraJSONString;
    }
   
    private static String obtainLineaCompraJSONString(ArrayList<Integer> unidades, ArrayList<LocalDateTime> fechasRecepcion
            , ArrayList<Integer> codigos, ArrayList<Boolean> recibidas, ArrayList<Integer> ids) {
        String lineaCompraJSONString = "";
        JsonArrayBuilder array = Json.createArrayBuilder();
        for(int i=0;i<unidades.size();i++){
            array.add(Json.createObjectBuilder().add("unidades",Integer.toString(unidades.get(i)))
            .add("fechaRecepcion",fechasRecepcion.get(i).toString())
            .add("codigos",Integer.toString(codigos.get(i)))
            .add("recibidas", Boolean.toString(recibidas.get(i)))
            .add("ids", Integer.toString(ids.get(i)))
            .build());
        }
        try(
                StringWriter stringWriter = new StringWriter();
                JsonWriter writer = Json.createWriter(stringWriter);
                ){   
            
            JsonObject jsonObj = Json.createObjectBuilder().add("lineasCompra",array).build();
            writer.writeObject(jsonObj);
            lineaCompraJSONString = stringWriter.toString();
        }catch(Exception ex){
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE,null,ex);
        }
        return lineaCompraJSONString;
    }
    
}
