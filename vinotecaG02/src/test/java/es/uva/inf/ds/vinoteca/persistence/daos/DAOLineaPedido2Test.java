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
 * @author pamarti
 * @author alerome
 * @author ivagonz
 */
public class DAOLineaPedido2Test {
    
    public DAOLineaPedido2Test() {
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
        assertEquals("{\"lineasCompra\":[{\"unidades\":\"1\",\"completada\":\"true\",\"numeros\":\"1\"},{\"unidades\":\"2\",\"completada\":\"true\",\"numeros\":\"2\"},{\"unidades\":\"3\",\"completada\":\"true\",\"numeros\":\"3\"},{\"unidades\":\"3\",\"completada\":\"false\",\"numeros\":\"3\"},{\"unidades\":\"2\",\"completada\":\"false\",\"numeros\":\"4\"},{\"unidades\":\"2\",\"completada\":\"false\",\"numeros\":\"4\"},{\"unidades\":\"2\",\"completada\":\"false\",\"numeros\":\"4\"},{\"unidades\":\"2\",\"completada\":\"false\",\"numeros\":\"4\"},{\"unidades\":\"2\",\"completada\":\"false\",\"numeros\":\"4\"}]}",DAOLineaPedido.consultaLineasPedido(1));
    }
}
