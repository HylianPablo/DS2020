
package es.uva.inf.ds.vinoteca.domain.controllers;

import es.uva.inf.ds.vinoteca.common.IllegalDateException;
import es.uva.inf.ds.vinoteca.domain.models.Abonado;
import es.uva.inf.ds.vinoteca.domain.models.Factura;
import es.uva.inf.ds.vinoteca.domain.models.Pedido;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controlador del caso de uso "Consultar impagos".
 * @author pamarti
 * @author alerome
 * @author ivagonz
 */
public class ControladorCUConsultarImpagos {
    
    /**
     * Factoría de controladores del caso de uso.
     * @return Nueva instancia del controlador del caso de uso "Consultar impagos".
     */
    public static ControladorCUConsultarImpagos getController(){
        return new ControladorCUConsultarImpagos();
    }
    
    /**
     * Obtiene las facturas con sus correspondientes pedidos y abonados que han sido completadas treinta días antes de la fecha introducida.
     * @param fecha Cadena de caracteres que representa la fecha límite a partir de la cual obtener las facturas.
     * @return {@code ArrayList} de cadenas de caracteres que representa los datos de las facturas completadas antes de 
     * la fecha introducida, junto con sus pedidos y sus abonados correspondientes.
     * El {@code ArrayList} podrá estar vacío en caso de no existir ninguna factura que cumpla con los requisitos.
     */
    public ArrayList<String> consultarImpagos(String fecha){
        ArrayList<String> detalles = null;
        try {
            if(!comprobarFechaCorrecta(fecha)){
                return null;
            }
        } catch (IllegalDateException ex) {
            Logger.getLogger(ControladorCUConsultarImpagos.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList <Factura> facturas = Factura.consultaFacturasAntesDeFecha(fecha);
        ArrayList<ArrayList<Pedido>> matrizPedidos = new ArrayList<>();
        ArrayList<Abonado> abonados = new ArrayList<>();
        for(int i=0;i<facturas.size();i++){
            Factura f0 = facturas.get(0);
            ArrayList<Pedido> pedidos = f0.getPedidosAsociados();
            matrizPedidos.add(pedidos);
            Pedido p0 = pedidos.get(0);
            Abonado ab = p0.getAbonado();
            abonados.add(ab);
        }
        detalles = new ArrayList<>();
        for(int i = 0;i<facturas.size();i++){
            String detalle = "Factura: "+Integer.toString(facturas.get(i).getNumeroFactura()) +".| Abonado: "
                    + Integer.toString(abonados.get(0).getNumeroAbonado())+" ,";
            String pedidosDetalle="";
            for(int j =0; j<matrizPedidos.get(i).size();j++){
                pedidosDetalle= pedidosDetalle + "Pedido: " + Integer.toString(matrizPedidos.get(i).get(j).getNumeroPedido());
                if(j==matrizPedidos.get(i).size()-1){
                    pedidosDetalle = pedidosDetalle + ".";
                }else{
                    pedidosDetalle = pedidosDetalle + ", ";
                }
            }
            detalle = detalle + pedidosDetalle;
            detalles.add(detalle);
        }
        return detalles;
    }

    private boolean comprobarFechaCorrecta(String fecha) throws IllegalDateException{
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd",Locale.US);
        if(fecha==null ||!coincideFormato(fecha) ){
            throw new IllegalDateException("La fecha introducida es errónea.");
        }
        LocalDate fechaLDT = LocalDate.parse(fecha,formatter);
        LocalDate now = LocalDate.now();
        LocalDate limite = now.minusDays(30);
        return fechaLDT.isBefore(limite);
        //Comprobar que es 30 dias inferior a actual
    }
    
    private boolean coincideFormato(String fecha){
        if(fecha.matches("[1-2][0-9][0-9][0-9][-][0][1-9][-][0-2][1-9]"))
            return true;
        if(fecha.matches("[1-2][0-9][0-9][0-9][-][1][0-2][-][0-2][1-9]"))
            return true;
        if(fecha.matches("[1-2][0-9][0-9][0-9][-][0][1-9][-][3][0-1]"))
            return true;
        if(fecha.matches("[1-2][0-9][0-9][0-9][-][1][0-2][-][3][0-1]"))
            return true;
        return false;
    }
    
    


}