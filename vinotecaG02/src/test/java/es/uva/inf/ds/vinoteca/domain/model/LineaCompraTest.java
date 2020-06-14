package es.uva.inf.ds.vinoteca.domain.model;

import es.uva.inf.ds.vinoteca.domain.models.LineaCompra;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.Month;

/**
 * @author pamarti
 * @author alerome
 * @author ivagonz
 * 
 */
public class LineaCompraTest {

    private LineaCompra lineaCompra;

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        LocalDateTime fechaRecepcion = LocalDateTime.of(2020, Month.JANUARY, 01, 19, 30, 40);
        lineaCompra = new LineaCompra(1, fechaRecepcion, 1, false, 1, 1);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void constructorCorrecto() {
        assertEquals(1, lineaCompra.getUnidades());
        assertEquals("2020-01-01T19:30:40", lineaCompra.getFechaRecepcion().toString());
        assertEquals(1, lineaCompra.getCodigo());
        assertEquals(false,lineaCompra.comprobarRecibida());
        assertEquals(1, lineaCompra.getCodigoLinea());
    }

}