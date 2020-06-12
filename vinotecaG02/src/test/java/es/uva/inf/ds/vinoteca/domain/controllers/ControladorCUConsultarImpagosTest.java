/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.domain.controllers;

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
public class ControladorCUConsultarImpagosTest {
    
    private ControladorCUConsultarImpagos cu;
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        cu = ControladorCUConsultarImpagos.getController();
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void consultarImpagosString(){
        assertEquals("Factura: 2. Abonado: 1 ,Pedido: 3.",cu.consultarImpagos("2020-01-01").get(0));
    }
}
