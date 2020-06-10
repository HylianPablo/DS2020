/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uva.inf.ds.vinoteca.domain.models;

import java.time.LocalDateTime;

/**
 *
 * @author Ivan
 */
public class Pedido {
	private int numero, estado, numeroFactura, numeroAbonado;
	private LocalDateTime fechaRealizacion, fechaRecepcion, fechaEntrega;
	private String notaEntrega;
	private double importe;

	public Pedido(int numero, int estado, LocalDateTime fechaRealizacion, String notaEntrega, double importe,
			LocalDateTime fechaRecepcion, LocalDateTime fechaEntrega, int numeroFactura, int numeroAbonado) {
		this.numero = numero;
		this.estado = estado;
		this.fechaRealizacion = fechaRealizacion;
		this.notaEntrega = notaEntrega;
		this.importe = importe;
		this.fechaRecepcion = fechaRecepcion;
		this.fechaEntrega = fechaEntrega;
		this.numeroFactura = numeroFactura;
		this.numeroAbonado = numeroAbonado;
	}

	public static ArrayList<Pedido> getPedidosAbonado(int numeroAbonado){
		return null;
	}

	public int getNumeroPedido() {
		return numero;
	}

}
