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
public class DAOAbonadoTest {
    
    
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
    public void consultaAbonadoCorrecta(){
       assertEquals("{\"numeroAbonado\":\"1\",\"openIdRef\":\"referencia0\",\"nif\":\"123456789\"}",
               DAOAbonado.consultaAbonado(1));
    }
    
    @Test
    public void consultaAbonadoInexistente(){
        assertEquals("",DAOAbonado.consultaAbonado(-1));
    }

    
}
