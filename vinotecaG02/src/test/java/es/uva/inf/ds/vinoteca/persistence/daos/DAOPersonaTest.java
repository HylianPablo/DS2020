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
public class DAOPersonaTest {
    
    public DAOPersonaTest() {
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
        assertEquals("{\"nombre\":\"Pepe\",\"apellidos\":\"Rodriguez Perez\",\"direccion\":\"Calle Falsa 123\",\"telefono\":\"999999999\",\"email\":\"email@email.com\"}",
                DAOPersona.consultaPersona("123456789"));
    }
    
    @Test
    public void JSONErroneo(){
        assertEquals("",DAOPersona.consultaPersona("-1"));
    }
}
