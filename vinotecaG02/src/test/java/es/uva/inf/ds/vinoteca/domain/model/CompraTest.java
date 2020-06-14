package es.uva.inf.ds.vinoteca.domain.model;

import es.uva.inf.ds.vinoteca.common.CompletadaException;
import es.uva.inf.ds.vinoteca.common.NullCompraException;
import es.uva.inf.ds.vinoteca.domain.models.Bodega;
import es.uva.inf.ds.vinoteca.domain.models.Compra;
import es.uva.inf.ds.vinoteca.domain.models.LineaCompra;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

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
    
    @Test
    public void testGetBodega() throws NullCompraException, CompletadaException{
        Compra c = Compra.getCompra(1);
        Bodega b = new Bodega("bodega", "111111111", "calle falsa 0");
        Bodega b2 = compra.getBodega();
        assertEquals(b2.getCIF(), b.getCIF());
        assertEquals(b2.getDireccion(), b.getDireccion());
        assertEquals(b2.getNombre(), b.getNombre());
    }
    
    @Test
    public void testGetCompra() throws NullCompraException, CompletadaException {
        Compra c = Compra.getCompra(2);
        assertEquals(20.0, c.getImporte());
        assertEquals("1", c.compruebaCompletado());
    }
    
    @Test
    public void testMarcarRecibidaCompleta() throws NullCompraException, CompletadaException{
        Compra c = Compra.getCompra(1);
        assertEquals("0", c.compruebaCompletado());
        c.marcarRecibidaCompleta();
        assertEquals("1", c.compruebaCompletado());
    }
    
    @Test
    public void testCompraNull(){
        assertThrows(NullCompraException.class, ()->{
            Compra c = Compra.getCompra(14);
        });
    }
    
    @Test
    public void testCompraYaCompletada() throws NullCompraException, CompletadaException{
        Compra c = Compra.getCompra(2);
        assertThrows(CompletadaException.class, ()->{
            c.compruebaCompletada();
        });
    }
    
    @Test
    public void testComprobarRecibidas() throws NullCompraException, CompletadaException{
        Compra c = Compra.getCompra(2);
        ArrayList <LineaCompra> lc = c.getLineasCompra();
        boolean allRecvs = c.comprobarRecibidas(lc);
        assertTrue(allRecvs);
    }
    
    @Test
    public void testGetLineasCompraNoRecibidas() throws NullCompraException, CompletadaException{
        Compra c = Compra.getCompra(1);
        ArrayList <LineaCompra> lc = c.getLineasCompra();
        boolean allRecvs = c.comprobarRecibidas(lc);
        ArrayList <LineaCompra> lcn = c.getLineasCompraNoRecibidas();
        assertSame(1, lcn.size());
    }
    
    @Test
    public void testGetLineasCompra() throws NullCompraException, CompletadaException{
        Compra c = Compra.getCompra(1);
        ArrayList <LineaCompra> lc = c.getLineasCompra();
        assertSame(3, lc.size());
    }
}