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
public class DAOLineaCompraTest {
    
    public DAOLineaCompraTest() {
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
    public void accesoCorrecto(){
        assertEquals("{\"lineasCompra\":[{\"unidades\":\"1\",\"fechaRecepcion\":\"2020-06-07T00:00\",\"codigos\":\"1\",\"recibidas\":\"true\",\"ids\":\"1\"},"+
        "{\"unidades\":\"1\",\"fechaRecepcion\":\"2020-05-07T00:00\",\"codigos\":\"1\",\"recibidas\":\"true\",\"ids\":\"4\"},"+
        "{\"unidades\":\"1\",\"fechaRecepcion\":\"2020-03-07T00:00\",\"codigos\":\"1\",\"recibidas\":\"true\",\"ids\":\"5\"}]}",DAOLineaCompra.consultaLineaCompra(1));
    }
    
    @Test
    public void accesoErroneo(){
        assertEquals("",DAOLineaCompra.consultaLineaCompra(-1));
    }
}
