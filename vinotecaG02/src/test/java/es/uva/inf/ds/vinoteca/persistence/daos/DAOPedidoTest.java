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
public class DAOPedidoTest {
    
    public DAOPedidoTest() {
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
    public void pedidosExistentes(){
        assertEquals("{\"pedidos\":[{\"numero\":\"3\",\"estado\":\"2\",\"fechaRealizacion\":\"2020-06-06T00:00\",\"notaEntrega\":\"fragil\",\"importe\":\"10.0\",\"fechaRecepcion\":\"2020-06-07T00:00\",\"fechaEntrega\":\"2020-06-07T00:00\",\"numeroAbonado\":\"1\",\"numeroFactura\":\"2\"},{\"numero\":\"4\",\"estado\":\"1\",\"fechaRealizacion\":\"2020-06-06T00:00\",\"notaEntrega\":\"fragil\",\"importe\":\"10.0\",\"fechaRecepcion\":\"2020-06-07T00:00\",\"fechaEntrega\":\"2020-06-07T00:00\",\"numeroAbonado\":\"2\",\"numeroFactura\":\"2\"}]}",
                DAOPedido.selectPedidosAsociados(2));
    }
    
    @Test
    public void pedidosNoExistentes(){
        assertEquals("",DAOPedido.selectPedidosAsociados(-1));
    }
    
    @Test
    public void consultaPedidoAbonadoCorrecto(){
        assertEquals("{\"pedidos\":[{\"numero\":\"1\",\"estado\":\"1\",\"fechaRealizacion\":\"2020-06-06T00:00\",\"notaEntrega\":\"fragil\",\"importe\":\"10.0\",\"fechaRecepcion\":\"2020-06-07T00:00\",\"fechaEntrega\":\"2020-06-07T00:00\",\"numeroAbonado\":\"1\",\"numeroFactura\":\"1\"}"
                + ",{\"numero\":\"2\",\"estado\":\"2\",\"fechaRealizacion\":\"2020-06-06T00:00\",\"notaEntrega\":\"fragil\",\"importe\":\"10.0\",\"fechaRecepcion\":\"2020-06-07T00:00\",\"fechaEntrega\":\"2020-06-07T00:00\",\"numeroAbonado\":\"1\",\"numeroFactura\":\"1\"},{\"numero\":\"3\""
                + ",\"estado\":\"2\",\"fechaRealizacion\":\"2020-06-06T00:00\",\"notaEntrega\":\"fragil\",\"importe\":\"10.0\",\"fechaRecepcion\":\"2020-06-07T00:00\",\"fechaEntrega\":\"2020-06-07T00:00\",\"numeroAbonado\":\"1\",\"numeroFactura\":\"2\"}]}",DAOPedido.consultaPedidoAbonado(1));
    }
    
    @Test
    public void consultaPedidoAbonadoErroneo(){
        assertEquals("",DAOPedido.consultaPedidoAbonado(-1));
    }
    
    @Test
    public void consultaPedidoLineaPedidoCorrecto(){
        assertEquals("{\"numero\":\"1\",\"estado\":\"1\",\"fechaRealizacion\":\"2020-06-06T00:00\",\"notaEntrega\":"
                + "\"fragil\",\"importe\":\"10.0\",\"fechaRecepcion\":\"2020-06-07T00:00\",\"fechaEntrega\":\"2020-06-07T00:00\""
                + ",\"numeroAbonado\":\"1\",\"numeroFactura\":\"1\",\"codigoLineaPedido\":\"1\"}",DAOPedido.consultaPedido(1));
    }
    
    @Test
    public void consultaPedidoLineaPedidoErroneo(){
        assertEquals("",DAOPedido.consultaPedido(-1));
    }
    
    @Test
    public void actualizarPedidoCorrecto(){
        DAOPedido.actualizaPedido(4);
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        int estado = -1;
        try(PreparedStatement ps = connection.getStatement("SELECT * FROM PEDIDO p WHERE p.NUMEROABONADO = 2");)
        {
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                estado = rs.getInt("ESTADO");
            }
            
        }catch(SQLException ex){
            Logger.getLogger(DAOPedido.class.getName()).log(Level.SEVERE,null,ex);
        }
        assertSame(2,estado);
    }
    
    @Test
    public void insertCorrecto(){
        DBConnection connection = DBConnection.getInstance();
        connection.openConnection();
        int countInit = -1;
        try(  
            PreparedStatement ps = connection.getStatement("SELECT COUNT(*) AS total FROM PEDIDO p");
            
        ){
            ResultSet result = ps.executeQuery();
            if(result.next()){
                countInit=result.getInt("total");
                
            }
            result.close();
        }catch(SQLException ex){
            Logger.getLogger(DAOCompra.class.getName()).log(Level.SEVERE,null,ex);
        }
        connection.closeConnection();
        DAOPedido.añadirPedido("{\"estado\":\"1\",\"notaEntrega\":\"notaEntrega\",\"importe\":\"50.0\",\"numeroFactura\":\"0\",\"numeroAbonado\":\"1\",\"codigo\":\"0\"}");
        connection.openConnection();
        int countFinal = -1;
        try(  
            PreparedStatement ps = connection.getStatement("SELECT COUNT(*) AS total FROM PEDIDO p");
            
        ){
            ResultSet result = ps.executeQuery();
            if(result.next()){
                countFinal=result.getInt("total");
                
            }
            result.close();
        }catch(SQLException ex){
            Logger.getLogger(DAOCompra.class.getName()).log(Level.SEVERE,null,ex);
        }
        connection.closeConnection();
        assertSame(countFinal,countInit+1);
    }

}
