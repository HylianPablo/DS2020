package es.uva.inf.ds.vinoteca.domain.model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import es.uva.inf.ds.vinoteca.domain.models.Empleado;
import java.sql.SQLException;
import java.time.LocalDateTime;
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
    Empleado e;
    LocalDateTime ldt;
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        ldt = LocalDateTime.of(2020,10,3,00,00);
        e = new Empleado("dummy","123456789Z","dummypw",ldt,"1");
    }
    
    @AfterEach
    public void tearDown() {
    }
    
    @Test
    public void getClient(){
        assertEquals(e,Empleado.getEmpleadoPorLoginYPassword("123456789Z", "dummypw"));
    }
    
    @Test //revisar warnings
    public void getClientNotExists(){
        assertThrows(SQLException.class,()->{
            Empleado.getEmpleadoPorLoginYPassword("noexiste", "noexiste");
        });
    }
    
    @Test
    public void checkActivo(){
        assertTrue(e.isActivo());
    }
    
    @Test
    public void notActivo(){
        Empleado e2 = new Empleado("dummy2","123456789A","dummypw",ldt,"1");
        assertFalse(e2.isActivo());
    }

    @Test
    public void leerJSON(){
        
    }
}
