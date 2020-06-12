package es.uva.inf.ds.vinoteca.domain.models;

/**
 * Modelo que representa un abonado del sistema.
 * @author pamarti
 * @author alerome
 * @author ivagonz
 */
public class Abonado {
    private int numeroAbonado;
    private String openidref, nif;
    
    /**
     * Constructor de la clase.
     * @param numeroAbonado Número entero que representa el número de abonado en el sistema.
     * @param openidref Cadena de caracteres que representa el identificador de la referencia.
     * @param nif Cadena de caracteres que representa el NIF del abonado.
     * @throws {@code IllegalArgumentException} en caso de que la longitud del NIF supere los nueve caracteres.
     */
    public Abonado(int numeroAbonado, String openidref, String nif){
        if(nif.length()>9)
            throw new IllegalArgumentException("La longitud del NIF excede los nueve caracteres.");
        this.numeroAbonado = numeroAbonado;
        this.openidref = openidref;
        this.nif = nif;
    }
    
    /**
     * Obtiene el número identificador del abonado.
     * @return Número entero que representa al abonado.
     */
    public int getNumeroAbonado(){
        return numeroAbonado;
    }
    
    /**
     * Modifica el número identificador del abonado.
     * @param n Número entero que pasará a representar al abonado.
     */
    public void setNumeroAbonado(int n){
        numeroAbonado=n;
    }
    
    /**
     * Obtiene el identificador de la referencia del abonado.
     * @return Cadena de caracteres que representa el identificador de la referencia del abonado.
     */
    public String getOpenIdRef(){
        return openidref;
    }
    
    /**
     * Modifica el identificador de la referencia del abonado.
     * @param s Cadena de caracteres que representa el nuevo identificador de la referencia del abonado.
     */
    public void setOpenIdRef(String s){
        openidref=s;
    }
    
    /**
     * Obtiene el NIF del abonado.
     * @return Cadena de caracteres que representa el NIF del abonado.
     */
    public String getNIF(){
        return nif;
    }
    
    /**
     * Modifica el NIF del abonado.
     * @param s Cadena de caracteres que representa el nuevo NIF del abonado.
     * @throws {@code IllegalArgumentException} en caso de que la longitud del NIF exceda los nueve caracteres.
     */
    public void setNIF(String s){
        if(s.length()>9)
            throw new IllegalArgumentException("La longitud del NIF debe ser como mucho de 9 caracteres");
        nif=s;
    }
    
    
}
