/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.domain.model;

import es.uva.inf.ds.vinoteca.domain.models.Abonado;
import es.uva.inf.ds.vinoteca.domain.models.Pedido;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
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
public class PedidoTest {
    
    private Pedido p;
    private LocalDateTime ldtRea= LocalDateTime.of(2020, Month.JUNE,6,00,00);
    private LocalDateTime ldtRe= LocalDateTime.of(2020,Month.JUNE,7,00,00);
    private LocalDateTime ldtE= LocalDateTime.of(2020,Month.JUNE,7,00,00);

    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        p = new Pedido(1,1,ldtRea,"fragil",10.0,ldtRe,ldtE,1,1);
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void constructorCorrecto(){
        assertSame(1,p.getNumeroPedido());
        assertSame(1,p.getEstado());
        assertEquals(ldtRea,p.getFechaRealizacion());
        assertEquals("fragil",p.getNotaEntrega());
        assertEquals(10.0,p.getImporte());
        assertEquals(ldtRe,p.getFechaRecepcion());
        assertEquals(ldtE,p.getFechaEntrega());
        assertSame(1,p.getNumeroFactura());
        assertSame(1,p.getNumeroAbonado());
    }
    
    @Test
    public void setterNumeroPedido(){
        p.setNumeroPedido(2);
        assertSame(2,p.getNumeroPedido());
    }
    
    @Test
    public void setterEstado(){
        p.setEstado(2);
        assertSame(2,p.getEstado());
    }
    
    @Test
    public void setterFechaRealizacion(){
        LocalDateTime dummy = LocalDateTime.of(2020,Month.JANUARY,1,00,00);
        p.setFechaRealiazacion(dummy);
        assertEquals(dummy,p.getFechaRealizacion());
    }
    
    @Test
    public void setterNotaEntrega(){
        p.setNotaEntrega("dummy");
        assertEquals("dummy",p.getNotaEntrega());
    }
    
    @Test
    public void setterImporte(){
        p.setImporte(200.0);
        assertEquals(200.0,p.getImporte());
    }
    
    @Test
    public void setterFechaRecepcion(){
        LocalDateTime dummy = LocalDateTime.of(2020,Month.JANUARY,1,00,00);
        p.setFechaRecepcion(dummy);
        assertEquals(dummy,p.getFechaRecepcion());
    }
    
    @Test
    public void setterFechaEntrega(){
        LocalDateTime dummy = LocalDateTime.of(2020,Month.JANUARY,1,00,00);
        p.setFechaEntrega(dummy);
        assertEquals(dummy,p.getFechaEntrega());
    }
    
    @Test
    public void setterNumeroFactura(){
        p.setNumeroFactura(10);
        assertSame(10,p.getNumeroFactura());
    }
    
    @Test
    public void setterNumeroAbonado(){
        p.setNumeroAbonado(10);
        assertSame(10,p.getNumeroAbonado());
    }
    
    @Test
    public void getAbonado(){
        Abonado ab = new Abonado(1,"referencia0","123456789","Pepe","Rodriguez Perez","Calle Falsa 123","999999999","email@email.com");
        assertSame(ab.getNumeroAbonado(),p.getAbonado().getNumeroAbonado());
        assertEquals(ab.getOpenIdRef(),p.getAbonado().getOpenIdRef());
        assertEquals(ab.getNif(),p.getAbonado().getNif());
        assertEquals("Pepe",ab.getNombre());
        assertEquals("Rodriguez Perez",ab.getApellidos());
        assertEquals("Calle Falsa 123",ab.getDireccion());
        assertEquals("999999999",ab.getTelefono());
        assertEquals("email@email.com",ab.getEmail());
    }
    
    @Test
    public void getPedido(){
        Pedido ped = Pedido.getPedido(1);
        assertEquals(1,ped.getEstado());
        assertEquals(1,ped.getNumeroAbonado());
        assertEquals(1,ped.getNumeroFactura());
        assertEquals(10.0,ped.getImporte());       
    }
    
    @Test
    public void testCambiarEstadoPendiente(){
        Pedido ped = Pedido.getPedido(2);
        assertEquals(2,ped.getEstado());
        ped.cambiarEstadoPendiente();
        assertEquals(1,ped.getEstado());
    }
    
    @Test
    public void testGetJson(){
        Pedido ped = Pedido.getPedido(1);
        String json = ped.getJson();
        assertEquals("{\"estado\":\"1\",\"notaEntrega\":\"fragil\",\"importe\":\"10.0\",\"numeroFactura\":\"1\",\"numeroAbonado\":\"1\",\"codigo\":\"0\"}",json);
    }
}
