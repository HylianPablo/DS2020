/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.domain.controllers;

import es.uva.inf.ds.vinoteca.common.FacturaVencidaException;
import es.uva.inf.ds.vinoteca.domain.models.Abonado;
import java.util.ArrayList;
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
public class ControladorCUCrearPedidoTest {

    private ControladorCUCrearPedido cu;

    @BeforeAll
    public static void setUpClass() {
        
    }
    
    @BeforeEach
    public void setUp() {
        cu = new ControladorCUCrearPedido();
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @AfterEach
    public void tearDown() {
    }
    
    @Test
    public void testComprobarAbonado() {   
        Abonado abon = cu.comprobarAbonado(1);
        assertEquals("123456789",abon.getNif());
        assertEquals("Pepe",abon.getNombre());
        assertEquals("Rodriguez Perez",abon.getApellidos());
        assertEquals("Calle Falsa 123",abon.getDireccion());
        assertEquals("999999999",abon.getTelefono());
        assertEquals("email@email.com",abon.getEmail());
    }
    
    @Test
    public void testGetDatos() {   
        Abonado abon = cu.comprobarAbonado(1);
        ArrayList<String> dat = cu.getDatos();
        assertEquals(4, dat.size());
    }
    
    @Test
    public void testCcomprobarPlazosVencidos() throws FacturaVencidaException {   
        Abonado abon = cu.comprobarAbonado(1);
        assertTrue(cu.comprobarPlazosVencidos());
    }
    

}