package es.uva.inf.ds.vinoteca.domain.model;

import es.uva.inf.ds.vinoteca.domain.models.Persona;
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
public class PersonaTest {
    
    private Persona p;
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        p = new Persona("123456789","Pepe","Rodriguez Perez","Calle Falsa 123","999999999","email@email.com");
    }
    
    @AfterEach
    public void tearDown() {
    }
    
    @Test
    public void constructorCorrecto(){
        assertEquals("123456789",p.getNif());
        assertEquals("Pepe",p.getNombre());
        assertEquals("Rodriguez Perez",p.getApellidos());
        assertEquals("Calle Falsa 123",p.getDireccion());
        assertEquals("999999999",p.getTelefono());
        assertEquals("email@email.com",p.getEmail());
    }
    
    @Test
    public void constructorErroneo(){
        assertThrows(IllegalArgumentException.class, ()->{
            Persona p2 = new Persona("1234567890","Pepe","Rodriguez Perez","Calle Falsa 123","999999999","email@email.com");
        });
    }
    
    @Test
    public void setterNIF(){
        p.setNif("987654321");
        assertEquals("987654321",p.getNif());
    }
    
    @Test
    public void setterNIFErroneo(){
        assertThrows(IllegalArgumentException.class, ()->{
            p.setNif("1234567890");
        });
    }

}
