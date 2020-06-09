/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uva.inf.ds.vinoteca.userinterface;

import es.uva.inf.ds.vinoteca.domain.controllers.ControladorCUCrearPedidoAbonado;

/**
 *
 * @author Ivan
 */
public class ControladorVistaAtencionCliente {

    private final VistaAtencionCliente view;
    private final ControladorCUCrearPedidoAbonado cuController;

    public ControladorVistaAtencionCliente(VistaAtencionCliente view) {
        this.view = view;
        cuController = ControladorCUCrearPedidoAbonado.getController();
    }

    public void procesaIntroducirNumeroAbonado(int numAbonado) {
        if (numAbonado < 0) {
            // TODO: Lanzar excepcion
        } else {
            cuController.crearPedidoAbonado(numAbonado);
        }
    }
}
