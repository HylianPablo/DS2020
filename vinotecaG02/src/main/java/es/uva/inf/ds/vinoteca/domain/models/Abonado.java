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
    
    //Hacer setters y getters
    
    
}
