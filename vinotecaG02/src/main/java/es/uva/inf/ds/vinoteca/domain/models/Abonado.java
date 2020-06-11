/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.domain.models;

/**
 *
 * @author pablo
 */
public class Abonado {
    private int numeroAbonado;
    private String openidref, nif;
    
    public Abonado(int numeroAbonado, String openidref, String nif){
        this.numeroAbonado = numeroAbonado;
        this.openidref = openidref;
        this.nif = nif;
    }
    
    public int getNumeroAbonado(){
        return numeroAbonado;
    }
    
    public void setNumeroAbonado(int n){
        numeroAbonado=n;
    }
    
    public String getOpenIdRef(){
        return openidref;
    }
    
    public void setOpenIdRef(String s){
        openidref=s;
    }
    
    public String getNIF(){
        return nif;
    }
    
    public void setNIF(String s){
        if(s.length()>9)
            throw new IllegalArgumentException("La longitud del NIF debe ser como mucho de 9 caracteres");
        nif=s;
    }
    
    
}
