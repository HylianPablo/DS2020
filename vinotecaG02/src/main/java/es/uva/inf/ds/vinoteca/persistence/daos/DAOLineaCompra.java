/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.persistence.daos;

import es.uva.inf.ds.vinoteca.persistence.dbaccess.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alejandro
 */
public class DAOLineaCompra {

    public static String consultaLineaCompra(int idCompra) {
        String lineaCompraJSONString = "";   
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        try(
            PreparedStatement ps = connection.getStatement("SELECT * FROM LINEACOMPRA lc, COMPRA c WHERE c.IdCompra= ? "
                    + "AND c.IdCompra = lc.IdCompra");           
        ){
            ps.setInt(1, idCompra);
            ResultSet result = ps.executeQuery();
            if(result.next()){
                //lo que se quiera de la linea de compra
            }
            result.close();
        }catch(SQLException ex){
            Logger.getLogger(DAOCompra.class.getName()).log(Level.SEVERE,null,ex);
        }
        connection.closeConnection();
        return lineaCompraJSONString;
    }
    
}
