/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.domain.controllers;

import es.uva.inf.ds.vinoteca.domain.models.Empleado;
import java.time.LocalDateTime;
import java.time.Month;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author pamarti
 * @auhtor alerome
 * @author ivagonz
 */
public class ControladorCUIdentificarseTest {
    private ControladorCUIdentificarse cu;
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        cu = ControladorCUIdentificarse.getController();
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void factoria(){
        assertNotNull(ControladorCUIdentificarse.getController());
    }
    
    @Test
    public void empleadoCorrecto(){
        LocalDateTime ldt = LocalDateTime.of(2020,Month.JUNE,6,00,00);
        Empleado e = new Empleado("123456789","password",ldt);
        assertEquals(e.getNif(),cu.identificarEmpleado("123456789", "password").getNif());
        assertEquals(e.getPassword(),cu.identificarEmpleado("123456789", "password").getPassword());
        assertEquals(e.getFecha(),cu.identificarEmpleado("123456789", "password").getFecha());
    }
    
    @Test
    public void empleadoErroneo(){
        assertNull(cu.identificarEmpleado("111111111", "password"));
    }
    
    @Test
    public void empleadoNoActivo(){
        assertEquals("NotActivo",cu.identificarEmpleado("987654321", "password").getNif());
    }
}
