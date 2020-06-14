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
 * @author pamarti
 * @author alerome
 * @author ivagonz
 */
public class DAOBodegaTest {
    
    public DAOBodegaTest() {
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
    public void JSONCorrecto(){
        assertEquals("{\"cif\":\"111111111\",\"nombre\":\"bodega\",\"direccion\":\"calle falsa 0\"}", DAOBodega.consultaBodega(1));
    }
    
    @Test
    public void JSONErroneo(){
        assertEquals("",DAOBodega.consultaBodega(-1));
    }
}
