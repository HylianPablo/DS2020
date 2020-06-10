/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.domain.model;

import es.uva.inf.ds.vinoteca.domain.models.Empleado;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
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
public class EmpleadoTest {
    private Empleado e;
    private LocalDateTime ldt = LocalDateTime.of(2020,Month.JUNE,6,00,00);
    

    @org.junit.jupiter.api.BeforeAll
    public static void setUpClass() throws Exception {
        
    }

    @org.junit.jupiter.api.AfterAll
    public static void tearDownClass() throws Exception {
    }

    @org.junit.jupiter.api.BeforeEach
    public void setUp() throws Exception {
        e = new Empleado("123456789","password",ldt);
    }

    @org.junit.jupiter.api.AfterEach
    public void tearDown() throws Exception {
    }
    
    @org.junit.jupiter.api.Test
    public void constructorCorrecto(){
        assertEquals("123456789",e.getNif());
        assertEquals("password",e.getPassword());
        assertEquals(ldt,e.getFecha());
    }
    
    @org.junit.jupiter.api.Test
    public void setterNIF(){
        e.setNif("111111111");
        assertEquals("111111111",e.getNif());
    }
    
    @org.junit.jupiter.api.Test
    public void setterPassword(){
        e.setPassword("dummy");
        assertEquals("dummy",e.getPassword());
    }
    
    @org.junit.jupiter.api.Test
    public void setterFecha(){
        LocalDateTime ldt2 = LocalDateTime.of(2000,Month.JANUARY,4,00,00);
        e.setFecha(ldt2);
        assertEquals(ldt2,e.getFecha());
    }
    
    @org.junit.jupiter.api.Test
    public void empleadoActivo(){
        assertTrue(e.isActivo());
    }
    
    @org.junit.jupiter.api.Test
    public void empleadoNoActivo(){
        Empleado e2 = new Empleado("987654321","password",ldt);
        assertTrue(e.isActivo());
    }
    
    @org.junit.jupiter.api.Test
    public void consultaEmpleadoCorrecta(){
        assertEquals(e.getNif(),Empleado.getEmpleadoPorLoginYPassword("123456789", "password").getNif());
        assertEquals(e.getPassword(),Empleado.getEmpleadoPorLoginYPassword("123456789", "password").getPassword());
        assertEquals(e.getFecha(),Empleado.getEmpleadoPorLoginYPassword("123456789", "password").getFecha());
    }
    
    @org.junit.jupiter.api.Test
    public void consultaEmpleadoErronea(){
        assertNull(Empleado.getEmpleadoPorLoginYPassword("121212123", "password"));
    }
}

