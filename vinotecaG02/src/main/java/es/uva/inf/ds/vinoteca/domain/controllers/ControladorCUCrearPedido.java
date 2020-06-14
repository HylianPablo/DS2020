package es.uva.inf.ds.vinoteca.domain.controllers;

import es.uva.inf.ds.vinoteca.common.AbonadoNotExistsException;
import es.uva.inf.ds.vinoteca.common.ReferenciaNoDisponibleException;
import es.uva.inf.ds.vinoteca.common.FacturaVencidaException;
import es.uva.inf.ds.vinoteca.common.ReferenciaNoValidaException;
import es.uva.inf.ds.vinoteca.domain.models.Abonado;
import es.uva.inf.ds.vinoteca.domain.models.LineaPedido;
import es.uva.inf.ds.vinoteca.domain.models.Pedido;
import es.uva.inf.ds.vinoteca.domain.models.Persona;
import es.uva.inf.ds.vinoteca.domain.models.Referencia;
import es.uva.inf.ds.vinoteca.persistence.daos.DAOLineaPedido;
import es.uva.inf.ds.vinoteca.persistence.daos.DAOPedido;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controlador del caso de uso "Crear pedido de Abonado".
 * @author pamarti
 * @author alerome
 * @author ivagonz
 */
public class ControladorCUCrearPedido {
    
    /**
     * Factoría de controladores del caso de uso.
     * @return Nueva instancia del controlador del caso de uso "Crear pedido de Abonado".
     */
    
    private Abonado b;
    private Referencia r;
    private Pedido newPedido;
    private LineaPedido newLineaPedido;
    private Persona p;
    private boolean temp;

    public static ControladorCUCrearPedido getController(){
        return new ControladorCUCrearPedido();
    }

    /**
     * Comprueba que el abonado existe en el sistema.
     * @param idAbonado Número entero que representa el identificador del abonado.
     */
    public Abonado comprobarAbonado(int idAbonado) {
        b = null;
        try{
            b = Abonado.getAbonado(idAbonado);
        }catch(AbonadoNotExistsException ex){
            Logger.getLogger(ControladorCURegistrarRecepcionCompra.class.getName()).log(Level.SEVERE,null,ex);
        }
        return b;
    }
    
    /**
     * Obtiene los datos del abonado.
     * @return Lista de cadenas de caracteres que contiene los datos del abonado.
     */
    public ArrayList<String> getDatos(){
        ArrayList<String> datos = new ArrayList<>();       
        datos.add(b.getNombre());
        datos.add(b.getApellidos());
        datos.add(b.getTelefono());
        datos.add(b.getEmail());
        return datos;
    }
    /**
     * Comprueba si el abonado tiene pedidos vencidos antes de crear el nuevo pedido.
     * @return {@code True} en caso de que el abonado no tenga pedidos vencidos y {@code false} en caso contrario.
     * @throws es.uva.inf.ds.vinoteca.common.FacturaVencidaException 
     */
    public boolean comprobarPlazosVencidos() throws FacturaVencidaException{ //Necesario idAbonado?? comprobar
        ArrayList<Pedido> pedidos = b.getPedidos();
        boolean bandera = true;
        try{
            for(int i=0;i<pedidos.size();i++){
                pedidos.get(i).comprobarNoVencido();                   
            }
        }catch(FacturaVencidaException ex){
            bandera=false;
            Logger.getLogger(ControladorCURegistrarRecepcionCompra.class.getName()).log(Level.SEVERE,null,ex);
        }
        if(bandera){
            newPedido = new Pedido(1,LocalDateTime.now(),"notaEntrega",0.0,null,null,0,b.getNumeroAbonado());
        }
        return bandera;
    }

    /**
     * Comprueba que la referencia es válida y de serlo inicia el proceso para crear un nuevo pedido.
     * @param idReferencia Número entero que representa el identificador de la referencia.
     * @param cantidad Número entero que representa la cantidad escogida.
     * @return 
     * @throws es.uva.inf.ds.vinoteca.common.ReferenciaNoDisponibleException 
     */
    public Referencia comprobarReferencia(int idReferencia, int cantidad) throws ReferenciaNoDisponibleException {
        temp = true;
        r = null;
        try{
            r = Referencia.getReferencia(idReferencia);
        }catch(ReferenciaNoValidaException ex){
            Logger.getLogger(ControladorCURegistrarRecepcionCompra.class.getName()).log(Level.SEVERE,null,ex);
        }   
        if(r!=null){
            newPedido.setImporte(cantidad * r.getPrecio());            
            try{
                r.comprobarDisponible();                   
                
            }catch(ReferenciaNoDisponibleException ex){
                temp = false;
                Logger.getLogger(ControladorCURegistrarRecepcionCompra.class.getName()).log(Level.SEVERE,null,ex);
            } 
            newLineaPedido = newPedido.crearLineaPedido(idReferencia, cantidad);  
        } 
        return r;  
    }
    
    public boolean comprobarDisponibilidad(){
        return temp;
    }

    /**
     * Registra un nuevo pedido en el sistema.
     */
    public void registrarPedido() {
        newPedido.cambiarEstadoPendiente();
        String jsonNewPedido = newPedido.getJson();
        DAOPedido.añadirPedido(jsonNewPedido);
        String jsonNewLinea = newLineaPedido.getJson();
        DAOLineaPedido.añadirLineaPedido(jsonNewLinea);
    }
}
