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
public class DAOEmpleadoTest {
    
    public DAOEmpleadoTest() {
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
    public void consultaEmpleadoCorrecta(){
        assertEquals("{\"nif\":\"123456789\",\"password\":\"password\",\"fechaInicio\":\"2020-06-06T00:00\"}",
                DAOEmpleado.consultaEmpleadoPorLoginYPassword("123456789", "password"));
        
    }
    @Test
    public void consultaEmpleadoErronea(){
        assertEquals("",DAOEmpleado.consultaEmpleadoPorLoginYPassword("121212123", "password"));
    }
    
    @Test
    public void consultaEmpleadoActivo(){ //Que el empleado exista o no tiene otra comprobación. Tiene como precondición que el empleado exista.
        assertTrue(DAOEmpleado.empleadoActivo("123456789"));
    }
    
    @Test
    public void consultaEmpleadoNoActivo(){
        assertFalse(DAOEmpleado.empleadoActivo("987654321"));
    }
    
    @Test
    public void consultaRolEmpleado(){
        assertSame(1,DAOEmpleado.selectRol("123456789"));
    }
    
    
}
