/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.domain.model;

import es.uva.inf.ds.vinoteca.domain.models.Factura;
import es.uva.inf.ds.vinoteca.domain.models.Pedido;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author pablo
 */
public class FacturaTest {
    private Factura f;
    private LocalDateTime ldtE = LocalDateTime.of(2020,Month.JUNE,6,00,00);
    private LocalDateTime ldtP = LocalDateTime.of(2020,Month.JULY,6,00,00);
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        f = new Factura(1,ldtE,20.0,1,ldtP,"extractobancario");
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void constructorCorrecto(){
        assertSame(1,f.getNumeroFactura());
        assertEquals(ldtE,f.getFechaEmision());
        assertEquals(20.0,f.getImporte());
        assertSame(1,f.getEstado());
        assertEquals(ldtP,f.getFechaPago());
        assertEquals("extractobancario",f.getIdExtractoBancario());
    }
    
    @Test
    public void setterNumeroFactura(){
        f.setNumeroFactura(2);
        assertSame(2,f.getNumeroFactura());
    }
    
    @Test
    public void setterFechaEmision(){
        LocalDateTime ldtDummy = LocalDateTime.of(2000,Month.JANUARY,4,00,00);
        f.setFechaEmision(ldtDummy);
        assertEquals(ldtDummy,f.getFechaEmision());
    }
    
    @Test
    public void setterImporte(){
        f.setImporte(30.0);
        assertEquals(30.0,f.getImporte());
    }
    
    @Test
    public void setterEstado(){
        f.setEstado(2);
        assertSame(2,f.getEstado());
    }
    
    @Test
    public void setterFechaPago(){
        LocalDateTime ldtDummy = LocalDateTime.of(2000,Month.JANUARY,4,00,00);
        f.setFechaPago(ldtDummy);
        assertEquals(ldtDummy,f.getFechaPago());
    }
    
    @Test
    public void setterIdExtractoBancario(){
        f.setIdExtractoBancario("dummy");
        assertEquals("dummy",f.getIdExtractoBancario());
    }
    
    @Test
    public void getPedidosAsociados(){
        ArrayList<Pedido> pedidos = new ArrayList<>();
        LocalDateTime ldtR = LocalDateTime.of(2020,Month.JUNE,6,00,00);
        LocalDateTime ldtRe = LocalDateTime.of(2020,Month.JUNE,7,00,00);
        LocalDateTime ldtP = LocalDateTime.of(2020,Month.JUNE,7,00,00);
        Pedido p = new Pedido(1,1,ldtR,"fragil",10.0,ldtRe,ldtP,1,1);
        Pedido p2 = new Pedido(2,2,ldtR,"fragil",10.0,ldtRe,ldtP,1,1);
        pedidos.add(p);
        pedidos.add(p2);
        assertSame(1,f.getPedidosAsociados().get(0).getNumeroPedido());
        assertSame(2,f.getPedidosAsociados().get(1).getNumeroPedido());
        assertArrayEquals(pedidos.toArray(),f.getPedidosAsociados().toArray());
    }
}
