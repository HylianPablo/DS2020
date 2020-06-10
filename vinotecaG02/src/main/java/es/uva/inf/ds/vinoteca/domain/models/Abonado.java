package Diseño.Arquitectura.vinotecaG02.negocio.modelos;

import Diseño.Arquitectura.vinotecaG02.persistencia.DAOAbonado;
import Análisis.Modelo del dominio.Entidades del dominio.Persona;
import java.util.ArrayList;

/**
 * @author Ivan
 */
public class Abonado extends Persona {

	private int numeroAbonado;

	//private int ¿openIDref?;

	public static Abonado getAbonado(int numID){
		String pedidosJSONString = "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm",Locale.US);
        int numeroJ;
        int estadoJ;
        LocalDateTime fechaRealizacionJ;
        String notaEntregaJ;
        double importeJ;
        LocalDateTime fechaRecepcionJ;
        LocalDateTime fechaEntregaJ;
        int numeroAbonadoJ;
        ArrayList<Pedido> pedidos = new ArrayList<>();
        
        pedidosJSONString = DAOFactura.selectPedidosAsociados(this.numeroFactura);
        JsonReaderFactory factory = Json.createReaderFactory(null);
        try(JsonReader reader = factory.createReader(new StringReader(pedidosJSONString));){
            JsonArray array = reader.readArray();
            for(int i =0; i<array.size();i++){
                JsonObject obj = (JsonObject) array.getJsonObject(i);
                numeroJ = Integer.parseInt(obj.getString("numero"));
                estadoJ = Integer.parseInt(obj.getString("estado"));
                fechaRealizacionJ = LocalDateTime.parse(obj.getString("fechaRealizacion"),formatter);
                notaEntregaJ = obj.getString("notaEntrega");
                importeJ = Double.parseDouble(obj.getString("importe"));
                fechaRecepcionJ = LocalDateTime.parse(obj.getString("fechaRecepcion"),formatter);
                fechaEntregaJ = LocalDateTime.parse(obj.getString("fechaEntrega"),formatter);
                numeroAbonadoJ = Integer.parseInt("numeroAbonado");
                Pedido p = new Pedido(numeroJ,estadoJ,fechaRealizacionJ,notaEntregaJ,importeJ,fechaRecepcionJ,fechaEntregaJ,this.numeroFactura,numeroAbonadoJ);
                pedidos.add(p);
            }
        }catch(Exception ex){
            Logger.getLogger(Factura.class.getName()).log(Level.SEVERE,null,ex);
        }
        return pedidos;

	}

	public void abonado(String abonadoString) {

	}

	public int getID() {
		return 0;
	}

	public ArrayList getPedidos() {
		return null;
	}

}
