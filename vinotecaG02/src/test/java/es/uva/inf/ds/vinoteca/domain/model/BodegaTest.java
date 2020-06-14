
package es.uva.inf.ds.vinoteca.domain.model;

import es.uva.inf.ds.vinoteca.domain.models.Bodega;
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

public class BodegaTest {

    private Bodega bodega;

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        bodega = new Bodega("bodega1", "123456789", "Avenida ficticia 666");
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void constructorCorrecto() {
        assertEquals("bodega1", bodega.getNombre());
        assertEquals("123456789", bodega.getCIF());
        assertEquals("Avenida ficticia 666", bodega.getDireccion());

    }

    @Test
    public void constructorErroneo() {
        assertThrows(IllegalArgumentException.class, () -> {
            Bodega bodega1 = new Bodega("bodega1", "1234567890", "Avenida ficticia 666");
        });
    }

    @Test
    public void setterNombreBodega() {
        bodega.setNombre("bodega1Nueva");
        assertEquals("bodega1Nueva", bodega.getNombre());
    }

    @Test
    public void setterCifBodega() {
        bodega.setNombre("987654321");
        assertEquals("987654321", bodega.getCIF());
    }

    @Test
    public void setterCifBodegaErroneo() {
        assertThrows(IllegalArgumentException.class, () -> {
            bodega.setCif("1234567890");
        });
    }

    @Test
    public void setterDireccionBodega() {
        bodega.setNombre("Otra avenida ficticia 666");
        assertEquals("Otra avenida ficticia 666", bodega.getDireccion());
    }

}