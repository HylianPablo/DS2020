package es.uva.inf.ds.vinoteca.domain.models;

import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonReaderFactory;
import javax.json.JsonReader;
import javax.json.Json;
import javax.json.JsonObject;

import es.uva.inf.ds.vinoteca.persistence.daos.DAOAbonado;

/**
 * @author Ivan
 */
public class Abonado {

    private int numeroAbonado;
    private String openidref, nif;

    public Abonado(int numeroAbonado, String openidref, String nif) {
        this.numeroAbonado = numeroAbonado;
        this.openidref = openidref;
        this.nif = nif;
    }

    public static Abonado getAbonado(int numeroAbonado) {

        Abonado ab = null;
        String abonadoJSONString = DAOAbonado.consultaAbonado(numeroAbonado);
        String openidref;
        String nif;
        JsonReaderFactory factory = Json.createReaderFactory(null);
        try (JsonReader reader = factory.createReader(new StringReader(abonadoJSONString));) {
            JsonObject jsonobject = reader.readObject();
            openidref = jsonobject.getString("openIdRef");
            nif = jsonobject.getString("nif");
            ab = new Abonado(numeroAbonado, openidref, nif);
        } catch (Exception ex) {
            Logger.getLogger(DAOAbonado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ab;

    }

    public int getNumeroAbonado() {
        return numeroAbonado;
    }

    public void setNumeroAbonado(int numeroAbonado) {
        this.numeroAbonado = numeroAbonado;
    }

    public String getOpenIdRef() {
        return openidref;
    }

    public void setOpenIdRef(String openidref) {
        this.openidref = openidref;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

}
