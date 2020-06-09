package Diseño.Arquitectura.vinotecaG02.negocio.modelos;

import Diseño.Arquitectura.vinotecaG02.persistencia.DAOLineaPedido;
import java.util.ArrayList;

public class LineaPedido {

	private int unidades;

	private boolean completada;

	private Pedido pedido;

	private LineaCompra lineaCompra;

	public static ArrayList getLineasPedido(int numero) {
		return null;
	}

	public void marcarCompleto() {

	}

	public boolean checkCompleto() {
		return false;
	}

	public void lineaPedido(String ref, int cantidad) {

	}

	public String getJSON() {
		return null;
	}

}
