/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.persistence.daos;

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
        assertEquals("{\"pedidos\":[{\"numero\":\"3\",\"estado\":\"2\",\"fechaRealizacion\":\"2020-06-06T00:00\",\"notaEntrega\":\"fragil\",\"importe\":\"10.0\",\"fechaRecepcion\":\"2020-06-07T00:00\",\"fechaEntrega\":\"2020-06-07T00:00\",\"numeroAbonado\":\"1\",\"numeroFactura\":\"2\"}]}",
                DAOPedido.selectPedidosAsociados(2));
    }

}
