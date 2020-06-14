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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author pamarti
 * @author alerome
 * @author ivagonz
 */
public class DAOLineaPedidoTest {
    
    public DAOLineaPedidoTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }
    
    @Test
    public void JSONErroneo(){
        assertEquals("",DAOLineaPedido.consultaLineasPedido(-1));
    }
    
    @Test
    public void actualizarCorrecto(){
        try{
            DAOLineaPedido.actualizarLineasDePedido(2);
        }catch(SQLException e){
            
        }
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        String completa = null;
        try(  
            PreparedStatement ps = connection.getStatement("SELECT * FROM LINEAPEDIDO lp WHERE lp.CodigoReferencia= 2"); ResultSet result = ps.executeQuery();
            
        ){
            if(result.next()){
                completa=result.getString("COMPLETADA");
                
            }
        }catch(SQLException ex){
            Logger.getLogger(DAOCompra.class.getName()).log(Level.SEVERE,null,ex);
        }
        connection.closeConnection();
        assertEquals("1",completa);
    }
    
    @Test
    public void insertarCorrecto(){
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        int countInit = -1;
        try(  
            PreparedStatement ps = connection.getStatement("SELECT COUNT(*) AS total FROM LINEAPEDIDO lp"); ResultSet result = ps.executeQuery();
            
        ){
            if(result.next()){
                countInit=result.getInt("total");
                
            }
        }catch(SQLException ex){
            Logger.getLogger(DAOCompra.class.getName()).log(Level.SEVERE,null,ex);
        }
        connection.closeConnection();
        DAOLineaPedido.añadirLineaPedido("{\"codReferencia\":\"1\",\"codPedido\":\"0\",\"unidades\":\"2\"}");
        connection.openConnection();
        int countFinal = -1;
        try(  
            PreparedStatement ps = connection.getStatement("SELECT COUNT(*) AS total FROM LINEAPEDIDO lp"); ResultSet result = ps.executeQuery();
            
        ){
            if(result.next()){
                countFinal=result.getInt("total");
                
            }
        }catch(SQLException ex){
            Logger.getLogger(DAOCompra.class.getName()).log(Level.SEVERE,null,ex);
        }
        connection.closeConnection();
        assertSame(countFinal,countInit+1);
    }
}
