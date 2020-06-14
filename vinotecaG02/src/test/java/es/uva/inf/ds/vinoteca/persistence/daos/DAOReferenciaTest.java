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
public class DAOReferenciaTest {
    
    public DAOReferenciaTest() {
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
        assertEquals("{\"precio\":\"10.0\",\"contenido\":\"33\",\"codigoRef\":\"1\",\"porCajas\":\"true\",\"disponible\":\"true\"}",
                DAOReferencia.consultaReferencia(1));
    }
    
    @Test
    public void JSONErroneo(){
        assertEquals("",DAOReferencia.consultaReferencia(-1));
    }
}
