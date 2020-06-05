/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.userinterface;

/**
 *
 * @author pablo
 */
public class GestorDeInterfazDeUsuario {
    
    private static VistaContabilidad contabilidadView;
    
    public GestorDeInterfazDeUsuario(){
        
    }
    
    public void elegirVista(int rol, VistaIdentificarse loginView){
        switch (rol){
            case 1:
                loginView.setVisible(false);
                loginView.dispose();
                java.awt.EventQueue.invokeLater(() -> {
                    contabilidadView = new VistaContabilidad();
                    contabilidadView.setVisible(true);
        });
                break;
            case 2:
                //lanzar caso 2
                break;
            case 3:
                //lanzar caso 3
                break;
            default:
                //lanzar excepcion
        }
    }
    
}
