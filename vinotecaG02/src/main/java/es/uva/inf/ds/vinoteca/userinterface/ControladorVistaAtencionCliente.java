package es.uva.inf.ds.vinoteca.userinterface;

import es.uva.inf.ds.vinoteca.domain.controllers.ControladorCUConsultarImpagos;
import es.uva.inf.ds.vinoteca.domain.controllers.ControladorCUCrearPedidoAbonado;

/**
 * @author Ivan
 */
public class ControladorVistaAtencionCliente{

	private final VistaAtencionCliente view;
	private final ControladorCUCrearPedidoAbonado cuController;

	public ControladorVistaAtencionCliente(VistaAtencionCliente view){
		this.view = view;
		cuController = ControladorCUCrearPedidoAbonado.getInstance();
	}

	public void procesaIntroducirNumeroAbonado() {
		int numAbonado = view.getNumeroAbonado();
		cuController.crearPedidoAbonado(numAbonado);
	}

	public void procesaConfirmacion() {

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
