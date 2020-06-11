/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uva.inf.ds.vinoteca.domain.controllers;

import es.uva.inf.ds.vinoteca.domain.models.Abonado;
import java.util.ArrayList;

/**
 *
 * @author Ivan
 */
public class ControladorCUCrearPedidoAbonado {

    private static ControladorCUCrearPedidoAbonado instance;

    private ControladorCUCrearPedidoAbonado() {
    }

    public static ControladorCUCrearPedidoAbonado getInstance() {
        if (instance == null)
            instance = new ControladorCUCrearPedidoAbonado();
        return instance;
    }

    public Abonado crearPedidoAbonado(int numID) {
        return Abonado.getAbonado(numID);
    }

    public void comprobarPlazosVencidos(Abonado abonado) {
        abonado.getPedidos();
    }

    public void comprobarReferencia(String ref, int cantidad) {

    }

}
