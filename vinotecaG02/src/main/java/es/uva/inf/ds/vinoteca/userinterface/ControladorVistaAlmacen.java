/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.userinterface;

import es.uva.inf.ds.vinoteca.domain.controllers.ControladorCUIdentificarse;
import es.uva.inf.ds.vinoteca.domain.controllers.ControladorCURegistrarRecepcionCompra;
import es.uva.inf.ds.vinoteca.domain.models.Bodega;
import es.uva.inf.ds.vinoteca.domain.models.LineaCompra;
import es.uva.inf.ds.vinoteca.domain.models.Referencia;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author alejandro
 */
public class ControladorVistaAlmacen {


    
    private final VistaAlmacen view;
    private final ControladorCURegistrarRecepcionCompra cuController;
    
    public ControladorVistaAlmacen(VistaAlmacen view){
        this.view=view;
        cuController = ControladorCURegistrarRecepcionCompra.getController();
    }

    public ControladorVistaAlmacen() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /*
    public static void mostrarLineasCompraNoCompletadas(ArrayList<LineaCompra> lcnr) {
        view.mostrarMensajeUsuario(lcnr);
    }*/
    
    public void procesaDatosIntroducirIdCompra(int idCompra){
        cuController.comprobarCompraNoCompletada(idCompra);
        Bodega b = cuController.getBodega();
        ArrayList<LineaCompra> lc = cuController.getLineasCompra();
        ArrayList<Referencia> ref = cuController.getReferencias();
        view.actualizarVista(b, lc, ref);
    }
    
    public void procesaDatosSeleccionaLineas(ArrayList<Integer> indice) throws SQLException{
        for(int i = 0; i < indice.size(); i++){
            cuController.actualizarLineaCompra(indice.get(i));
        }
    }
    
    //igual comprobarRecibidas no deberia retornar
    public void procesaDatosFinalizar(){
        cuController.comprobarRecibidas();
        if(cuController.getAllRefs()){
            ArrayList<LineaCompra> lcnr = cuController.getLineasCompraNoRecibidas();
            view.mostrarMensajeUsuario(lcnr);
        }
        cuController.actualizarPedidos();
        
    }
}
