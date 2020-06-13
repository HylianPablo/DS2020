/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.domain.model;

import es.uva.inf.ds.vinoteca.domain.models.Factura;
import es.uva.inf.ds.vinoteca.domain.models.Pedido;
import java.time.LocalDate;
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
        //assertSame(1,f.getPedidosAsociados().get(0).getNumeroPedido());
        //assertSame(2,f.getPedidosAsociados().get(1).getNumeroPedido());
        assertSame(1,f.getPedidosAsociados().get(0).getEstado());
        assertSame(2,f.getPedidosAsociados().get(1).getEstado());
        assertEquals(ldtR,f.getPedidosAsociados().get(0).getFechaRealizacion());
        assertEquals(ldtR,f.getPedidosAsociados().get(1).getFechaRealizacion());
        assertEquals("fragil",f.getPedidosAsociados().get(0).getNotaEntrega());
        assertEquals("fragil",f.getPedidosAsociados().get(1).getNotaEntrega());
        assertEquals(10.0,f.getPedidosAsociados().get(0).getImporte());
        assertEquals(10.0,f.getPedidosAsociados().get(1).getImporte());
        assertEquals(ldtRe,f.getPedidosAsociados().get(0).getFechaRecepcion());
        assertEquals(ldtRe,f.getPedidosAsociados().get(1).getFechaRecepcion());
        assertEquals(ldtP,f.getPedidosAsociados().get(0).getFechaEntrega());
        assertEquals(ldtP,f.getPedidosAsociados().get(1).getFechaEntrega());
        assertSame(1,f.getPedidosAsociados().get(0).getNumeroFactura());
        assertSame(1,f.getPedidosAsociados().get(1).getNumeroFactura());
        assertSame(1,f.getPedidosAsociados().get(0).getNumeroAbonado());
        assertSame(1,f.getPedidosAsociados().get(1).getNumeroAbonado());
        assertSame(2,f.getPedidosAsociados().size());
        //assertArrayEquals(pedidos.toArray(),f.getPedidosAsociados().toArray());
    }
    
    @Test
    public void facturasANTESfecha(){
        LocalDateTime fechaE = LocalDateTime.of(2019,Month.MAY,6,00,00);
        LocalDateTime fechaP = LocalDateTime.of(2019,Month.JULY,6,00,00);
        Factura f2 = new Factura(2,fechaE,20.0,1,fechaP,"extractobancario");
        assertSame(f2.getNumeroFactura(),Factura.consultaFacturasAntesDeFecha("2020-01-01").get(0).getNumeroFactura());
        assertEquals(f2.getFechaEmision(),Factura.consultaFacturasAntesDeFecha("2020-01-01").get(0).getFechaEmision());
        assertEquals(f2.getImporte(),Factura.consultaFacturasAntesDeFecha("2020-01-01").get(0).getImporte());
        assertSame(f2.getEstado(),Factura.consultaFacturasAntesDeFecha("2020-01-01").get(0).getEstado());
        assertEquals(f2.getFechaPago(),Factura.consultaFacturasAntesDeFecha("2020-01-01").get(0).getFechaPago());
        assertEquals(f2.getIdExtractoBancario(),Factura.consultaFacturasAntesDeFecha("2020-01-01").get(0).getIdExtractoBancario());
        assertSame(1,Factura.consultaFacturasAntesDeFecha("2020-01-01").size());
    }
    
    @Test
    public void facturasMISMAfecha(){
        assertSame(0,Factura.consultaFacturasAntesDeFecha("2019-05-06").size());
    }
    
}
