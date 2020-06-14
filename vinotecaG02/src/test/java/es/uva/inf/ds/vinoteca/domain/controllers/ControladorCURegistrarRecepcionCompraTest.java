/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.domain.controllers;

import es.uva.inf.ds.vinoteca.common.CompletadaException;
import es.uva.inf.ds.vinoteca.common.ReferenciaNoValidaException;
import es.uva.inf.ds.vinoteca.domain.models.Compra;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ControladorCURegistrarRecepcionCompraTest {

    private ControladorCURegistrarRecepcionCompra cu;

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        cu = ControladorCURegistrarRecepcionCompra.getController();
    }

    @AfterEach
    public void tearDown() {
    }
    
    @Test
    public void constructorCorrecto(){
        assertNotNull(cu);
    }
    
    @Test
    public void comprobarCompraCorrecta(){
        LocalDateTime ldtC = LocalDateTime.of(2020,Month.JUNE,06,00,00);
        Compra c = new Compra(1,20.0,ldtC,"1");
        assertEquals(1,c.getIdCompra());
        assertEquals(20.0,c.getImporte());
        assertEquals(ldtC,c.getFechaPago());
    }
    
    @Test
    public void comprobarCompraError(){
        try {
            assertNull(cu.comprobarCompraNoCompletada(-1));
        } catch (ReferenciaNoValidaException | CompletadaException ex) {
            Logger.getLogger(ControladorCURegistrarRecepcionCompraTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}