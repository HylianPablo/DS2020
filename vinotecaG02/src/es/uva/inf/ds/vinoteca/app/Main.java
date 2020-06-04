/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.app;

import es.uva.inf.ds.vinoteca.userinterface.VistaIdentificarse;

/**
 *
 * @author pablo
 */
public class Main {
    private static VistaIdentificarse loginView;
    
    public static void main(String args[]){
        java.awt.EventQueue.invokeLater(new Runnable(){
        public void run(){
            loginView = new VistaIdentificarse();
            loginView.setVisible(true);
        }
    });
    }
}
