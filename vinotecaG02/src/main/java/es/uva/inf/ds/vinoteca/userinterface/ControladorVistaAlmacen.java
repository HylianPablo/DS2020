package es.uva.inf.ds.vinoteca.userinterface;

import es.uva.inf.ds.vinoteca.common.CompletadaException;
import es.uva.inf.ds.vinoteca.common.ReferenciaNoValidaException;
import es.uva.inf.ds.vinoteca.domain.controllers.ControladorCURegistrarRecepcionCompra;
import es.uva.inf.ds.vinoteca.domain.models.Compra;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Controlador de la interfaz del caso de uso "Registrar compra".
 * @author pamarti
 * @author alerome
 * @author ivagonz
 */
public class ControladorVistaAlmacen {

    private final VistaAlmacen view;
    private final ControladorCURegistrarRecepcionCompra cuController;
    
    /**
     * Constructor del controlador de la interfaz del caso de uso "Registrar compra".
     * @param view Interfaz asociada al caso de uso.
     */
    public ControladorVistaAlmacen(VistaAlmacen view){
        this.view=view;
        cuController = ControladorCURegistrarRecepcionCompra.getController();
    }
    
    /**
     * Comprueba que la compra existe y muestra sus datos.
     * @param idCompra Número entero que representa el identificador de la compra.
     * @throws es.uva.inf.ds.vinoteca.common.ReferenciaNoValidaException
     * @throws es.uva.inf.ds.vinoteca.common.CompletadaException
     */
    public void procesaDatosIntroducirIdCompra(int idCompra) throws ReferenciaNoValidaException, CompletadaException {
        Compra c = cuController.comprobarCompraNoCompletada(idCompra);
        if(c == null){
            view.setMensajeError("La compra no existe en la BD");
        }
        boolean b2 = cuController.comprobarCompraCompletada();
        if(b2){
            view.setMensajeError("La compra ya se encuentra completada");
        }else{          
            String nombreB = cuController.getNombreBodega();
            ArrayList<Integer> unidadesLinea = cuController.getNumeroUnidadesLineasCompra();
            ArrayList<Integer> codigosLinea= cuController.getCodigosLineasCompra();
            ArrayList<Integer> codigosReferencia = cuController.getCodigosReferencias();
            view.actualizarVista(nombreB, codigosLinea, unidadesLinea, codigosReferencia);
        }
    }
    
    public void procesaDatosSeleccionaLineas(ArrayList<Integer> indice) throws SQLException{
        for(int i = 0; i < indice.size(); i++){
            cuController.actualizarLineaCompra(indice.get(i));
        }
    }
    
    //igual comprobarRecibidas no deberia retornar
    public void procesaDatosFinalizar(){
        cuController.comprobarRecibidas();
        if(!cuController.getAllRefs()){
            ArrayList<Integer> lcnr = cuController.getIdLineasCompraNoRecibidas();
            view.mostrarMensajeUsuario(lcnr);
        }
        cuController.actualizarPedidos();
        
    }
}
