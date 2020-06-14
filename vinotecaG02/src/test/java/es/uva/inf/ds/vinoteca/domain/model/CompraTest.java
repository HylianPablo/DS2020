package es.uva.inf.ds.vinoteca.domain.model;

import es.uva.inf.ds.vinoteca.domain.models.Compra;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.Month;

/**
 *
 * @author pamarti
 * @author alerome
 * @author ivagonz
 */

public class CompraTest {

    private Compra compra;

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        LocalDateTime fechaCompra = LocalDateTime.of(2020, Month.JANUARY, 01, 19, 30, 40);
        compra = new Compra(1, 20.50, fechaCompra, "0");
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void constructorCorrecto() {
        assertEquals(1, compra.getIdCompra());
        assertEquals(20.50, compra.getImporte());
        assertEquals("2020-01-01T19:30:40",compra.getFechaPago().toString());
    }
}