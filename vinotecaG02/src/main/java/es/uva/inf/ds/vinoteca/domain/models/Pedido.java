package Diseño.Arquitectura.vinotecaG02.negocio.modelos;

import Diseño.Arquitectura.vinotecaG02.persistencia.DAOPedido;
import Análisis.Modelo del dominio.Entidades del dominio.EstadoPedido;
import java.util.Date;
import java.util.Currency;

public class Pedido {

	private int numero;

	private EstadoPedido estado;

	private Date fechaRealizacion;

	private String notaEntrega;

	private Currency importe;

	private Date[] fechaRecepcion;

	private Date[] fechaEntrega;

	private Abonado abonado;

	private Factura factura;

	public Abonado getAbonado() {
		return null;
	}

	public static Pedido getPedido() {
		return null;
	}

	public void pedido(String pedidoStr) {

	}

	public void cambiarEstadoPedidoPendiente() {

	}

	public boolean comprobarNoVencido() {
		return false;
	}

	public String getJSON() {
		return null;
	}

	public void actualizarEstadoACompletado() {

	}

}
