package es.uva.inf.ds.vinoteca.userinterface;

/**
 * @author Ivan
 */
public class VistaAtencionCliente extends javax.swing.JFrame {

	private ControladorVistaAtencionCliente controller;

	public VistaAtencionCliente(){
		controller = new ControladorVistaAtencionCliente(this);
	}

	public void setMensajeError(String message){
		//TODO: Mostrar error
	}

	public void buscarAbonado() {

	}

	public void introducirNúmeroAbonado(int numID) {

	}

	public void cancelarProceso() {

	}

	public void introducirConfirmacion() {

	}

	public void introducirReferencia(String ref, int cantidad) {

	}

	public void terminarProceso() {

	}

	public int getNumeroAbonado() {
		return 0;
	}

	public void solicitarConfirmacion() {

	}

	public void mensajeError(Exception e) {

	}

	public String getReferencia() {
		return null;
	}

	public int getCantidad() {
		return 0;
	}

}
