
package es.uva.inf.ds.vinoteca.domain.controllers;

import es.uva.inf.ds.vinoteca.domain.models.Abonado;
import es.uva.inf.ds.vinoteca.domain.models.Factura;
import es.uva.inf.ds.vinoteca.domain.models.Pedido;
import java.util.ArrayList;

/**
 * Controlador del caso de uso "Consultar impagos".
 * @author pamarti
 * @author alerome
 * @author ivagonz
 */
public class ControladorCUConsultarImpagos { //REVISAR
    
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
        ArrayList<String> detalles = new ArrayList<>();
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
    
    


}