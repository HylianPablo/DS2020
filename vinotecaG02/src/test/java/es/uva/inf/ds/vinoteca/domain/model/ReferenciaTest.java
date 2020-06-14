package es.uva.inf.ds.vinoteca.domain.model;

import es.uva.inf.ds.vinoteca.common.NullCompraException;
import es.uva.inf.ds.vinoteca.common.ReferenciaNoDisponibleException;
import es.uva.inf.ds.vinoteca.common.ReferenciaNoValidaException;
import es.uva.inf.ds.vinoteca.domain.models.Compra;
import es.uva.inf.ds.vinoteca.domain.models.Referencia;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author pamarti
 * @author alerome
 * @author ivagonz
 * 
 */
public class ReferenciaTest {

    private Referencia referencia;

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        referencia = new Referencia(1, 1, 2.0, false, true);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void constructorCorrecto() {
        assertEquals(1,referencia.getCodigo());
        assertEquals(1,referencia.getContenido());
        assertEquals(2.0,referencia.getPrecio());
        assertEquals(false,referencia.comprobarPorCajas());
    }
    
    @Test
    public void getReferencia() throws ReferenciaNoValidaException, ReferenciaNoDisponibleException {
        Referencia r = Referencia.getReferencia(1);
        assertEquals(true,r.comprobarPorCajas());
        assertEquals(33,r.getContenido());
        assertEquals(10.0, r.getPrecio());
        assertEquals(true,r.comprobarDisponible());
    }
    
    @Test
    public void testComprobarDisponible() throws ReferenciaNoValidaException{
        Referencia r = Referencia.getReferencia(2);
        assertThrows(ReferenciaNoDisponibleException.class, ()->{
            r.comprobarDisponible();
        });
    }
}