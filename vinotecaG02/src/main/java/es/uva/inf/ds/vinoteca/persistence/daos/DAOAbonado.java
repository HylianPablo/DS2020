package es.uva.inf.ds.vinoteca.persistence.daos;

import es.uva.inf.ds.vinoteca.persistence.dbaccess.DBConnection;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonWriter;


public class DAOAbonado {

	 
	public String consultaAbonado(int numID) {
		String abonadoJSONString = "";
		DBConnection dbConnection = DBConnection.getInstance();
		dbConnection.openConnection();
		try(PreparedStatement ps = dbConnection.getStatement("SELECT * FROM ABONADO a WHERE f.NUMEROABONADO = ?");)
        {
            ps.setInt(1,numID);
            ResultSet result = ps.executeQuery();
            
        }catch(SQLException ex){
            Logger.getLogger(DAOAbonado.class.getName()).log(Level.SEVERE,null,ex);
        }

		return null;
	}

	private String abonadoToJSONString(){
		return null;
	}

}
