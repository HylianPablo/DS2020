package es.uva.inf.ds.vinoteca.domain.model;

import es.uva.inf.ds.vinoteca.domain.models.LineaPedido;
import java.util.ArrayList;
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
public class LineaPedidoTest {

    private LineaPedido lineaPedido;

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        lineaPedido = new LineaPedido(false, 1, 1);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void constructorCorrecto() {
        assertEquals(false,lineaPedido.checkCompleto());
        assertEquals(1,lineaPedido.getUnidades());
        assertEquals(1,lineaPedido.getCodigoPedido());
    }
    
    @Test
    public void getLineasPedido() {
        ArrayList<LineaPedido> lpedidos = LineaPedido.getLineasPedido(1);
        assertEquals(4, lpedidos.size());
    }
    
    @Test
    public void getJson() {
        ArrayList<LineaPedido> lpedidos = LineaPedido.getLineasPedido(1);
        String json = lpedidos.get(0).getJson();
        assertEquals(json, "{\"codReferencia\":\"0\",\"codPedido\":\"1\",\"unidades\":\"1\"}");
    }
    
    @Test
    public void getMarcarCompleto() {
        ArrayList<LineaPedido> lpedidos = LineaPedido.getLineasPedido(1);
        lpedidos.get(3).marcarCompleto();
        assertTrue(lpedidos.get(3).checkCompleto());
    }
}