/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.persistence.daos;

import java.sql.SQLException;
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
public class DAOFacturaTest {
    
    public DAOFacturaTest() {
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
    public void consultaFacturasExistentes(){
        assertEquals("{\"facturas\":[{\"numeroFactura\":\"2\",\"fechaEmision\":\"2019-05-06T00:00\",\"importe\":\"20.0\",\"estado\":\"1\",\"fechaPago\":\"2019-07-06T00:00\",\"idExtractoBancario\":\"extractobancario\"}]}",
                DAOFactura.consultaFacturasAntesDeFecha("2020-01-01"));
    }
    
    @Test
    public void consultaFacturasInexistentes(){
        assertEquals("",DAOFactura.consultaFacturasAntesDeFecha("1000-01-01"));
    }
    
    @Test
    public void facturaNoVencida() throws SQLException{
        assertTrue(DAOFactura.comprobarNoVencido(1));
    }
    
    @Test
    public void facturaVencida() throws SQLException{
        assertFalse(DAOFactura.comprobarNoVencido(3));
    }

   
}
