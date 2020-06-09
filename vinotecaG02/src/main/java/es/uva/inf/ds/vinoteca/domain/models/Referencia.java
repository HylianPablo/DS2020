package Diseño.Arquitectura.vinotecaG02.negocio.modelos;

import Diseño.Arquitectura.vinotecaG02.persistencia.DAOReferencia;
import java.util.Currency;

public class Referencia {

	private int codigo;

	private boolean esPorCajas;

	private int contenidoEnCL;

	private Currency precio;

	private boolean disponible;

	public static Referencia getReferencia(String ref) {
		return null;
	}

	public void referencia(String referenciaStr) {

	}

	public boolean checkDisponible() {
		return false;
	}

}
