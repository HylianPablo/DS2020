package es.uva.inf.ds.vinoteca.userinterface;

import es.uva.inf.ds.vinoteca.domain.controllers.ControladorCUCrearPedidoAbonado;
import es.uva.inf.ds.vinoteca.domain.models.Abonado;

/**
 * @author Ivan
 */
public class ControladorVistaAtencionCliente {

	private final VistaAtencionCliente view;
	private final ControladorCUCrearPedidoAbonado cuController;
	private Abonado abonado;

	public ControladorVistaAtencionCliente(VistaAtencionCliente view) {
		this.view = view;
		cuController = ControladorCUCrearPedidoAbonado.getInstance();
	}

	public void procesaIntroducirNumeroAbonado(int numAbonado) {
		// int numAbonado = view.getNumeroAbonado();
		if (numAbonado < 0) {
			view.setMensajeError("El número del abonado debe ser positivo");
		} else {
			abonado = cuController.crearPedidoAbonado(numAbonado);
		}

	}

	public void procesaConfirmacion() {
		cuController.comprobarPlazosVencidos(abonado);
	}

	public String generarTexto() {
		return null;
	}

	public void procesaMensajeError(Exception e) {

	}

	public void procesaIntroducirReferencia() {

	}

	public void registrarPedido() {

	}

}
