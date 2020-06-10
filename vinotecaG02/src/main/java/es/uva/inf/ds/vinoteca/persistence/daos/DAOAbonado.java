package es.uva.inf.ds.vinoteca.persistence.daos;

import es.uva.inf.ds.vinoteca.persistence.dbaccess.DBConnection;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;

public class DAOAbonado {

	public static String consultaAbonado(int numeroAbonado) {
		String abonadoJSONString = "";
		String openidref = null;
		String nif = null;
		DBConnection connection = DBConnection.getInstance();
		connection.openConnection();
		try (PreparedStatement ps = connection.getStatement("SELECT * FROM ABONADO a WHERE a.NUMEROABONADO = ?");) {
			ps.setString(1, Integer.toString(numeroAbonado));
			ResultSet result = ps.executeQuery();
			if (result.next()) {
				openidref = result.getString("OPENIDREF");
				nif = result.getString("NIF");
			}

		} catch (SQLException ex) {
			Logger.getLogger(DAOAbonado.class.getName()).log(Level.SEVERE, null, ex);
		}
		connection.closeConnection();
		abonadoJSONString = abonadoToJSONString(numeroAbonado, openidref, nif);
		return abonadoJSONString;
	}

	private static String abonadoToJSONString(int numeroAbonado, String openidref, String nif) {
		String abonadoJSONString = "";
		JsonObject abonadoJSON = Json.createObjectBuilder().add("numeroAbonado", Integer.toString(numeroAbonado))
				.add("openIdRef", openidref).add("nif", nif).build();
		try (StringWriter stringWriter = new StringWriter(); JsonWriter writer = Json.createWriter(stringWriter);) {

			writer.writeObject(abonadoJSON);
			abonadoJSONString = stringWriter.toString();
		} catch (Exception ex) {
			Logger.getLogger(DAOAbonado.class.getName()).log(Level.SEVERE, null, ex);
		}
		return abonadoJSONString;
	}

}
