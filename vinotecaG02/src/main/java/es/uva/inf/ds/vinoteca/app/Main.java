/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.app;

import es.uva.inf.ds.vinoteca.userinterface.VistaAlmacen;
import es.uva.inf.ds.vinoteca.userinterface.VistaIdentificarse;

/**
 * Lanzador de la aplicación. Lanza la vista del caso de uso "Identificarse"
 * @author pamarti
 * @author alerome
 * @author ivagonz
 */
public class Main {
    private static VistaIdentificarse loginView;
    
    /**
     * Lanzador de la vista inicial de la aplicación.
     */
    public static void main(String args[]){
        java.awt.EventQueue.invokeLater(new Runnable(){
        public void run(){
            VistaAlmacen loginView = new VistaAlmacen();
            loginView.setVisible(true);
        }
    });
    }
}
