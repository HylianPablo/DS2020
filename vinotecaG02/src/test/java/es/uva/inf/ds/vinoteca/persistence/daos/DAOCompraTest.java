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
 * @author pablo
 */
public class DAOCompraTest {
    
    public DAOCompraTest() {
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
    public void JSONCorrectoObtener(){
        assertEquals("{\"importe\":\"20.0\",\"fechaPago\":\"2020-06-07T00:00\",\"completa\":\"0\"}",DAOCompra.consultaCompra(1));
    }
    
    @Test
    public void JSONErroneoObtener(){
        assertEquals("",DAOCompra.consultaCompra(-1));
    }
    
    @Test
    public void actualizarCorrecto(){
        DAOCompra.actualizarCompra(3);
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        String completa = null;
        try(  
            PreparedStatement ps = connection.getStatement("SELECT * FROM COMPRA c WHERE c.IDCOMPRA= 3");
            
        ){
            ResultSet result = ps.executeQuery();
            if(result.next()){
                completa=result.getString("RECIBIDACOMPLETA");
                
            }
            result.close();
        }catch(SQLException ex){
            Logger.getLogger(DAOCompra.class.getName()).log(Level.SEVERE,null,ex);
        }
        assertEquals("1",completa);
    }
}
