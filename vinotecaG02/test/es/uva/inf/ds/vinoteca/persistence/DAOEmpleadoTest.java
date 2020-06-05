/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.persistence;

import es.uva.inf.ds.vinoteca.persistence.daos.DAOEmpleado;
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
public class DAOEmpleadoTest {
    String dummyJSON = "{ \"login\":\"dummy\", \"nif\":\"123456789Z\","
            + "\"password\":\"dummypw\",\"fechaInicio\":\"2020-03-08 00:00\","
            + "\"tipoEmpleado\":\"1\"}";
    
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
    public void selectEmpleado(){
        assertEquals(dummyJSON,
                DAOEmpleado.consultaEmpleadoPorLoginYPassword("dummy", "dummypw"));
    }
}
