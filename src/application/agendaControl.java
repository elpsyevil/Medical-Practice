package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import jfxtras.scene.control.agenda.Agenda;

public class agendaControl implements Initializable {
	@FXML private Agenda agenda;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			// 1. Get a connection to database
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cabinet", "root" , "");
			
						
			myStmt = myConn.prepareStatement("select * from patient where doctor_id = (select id from employe where lastname = ?)");
			
			myStmt.setString(1, employeeContol.getD_id());
			
			// 3. Execute SQL query
			myRs = myStmt.executeQuery();
			// 4. Process the result set
			while (myRs.next()) {
				
				agenda.appointments().add(
				        new Agenda.AppointmentImplLocal()
				            .withStartLocalDateTime(myRs.getTimestamp("datCd").toLocalDateTime())
				            .withEndLocalDateTime(myRs.getTimestamp("datCf").toLocalDateTime())
				            .withDescription(String.valueOf(myRs.getInt("patient_id")))
				            .withAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("group1")) // you should use a map of AppointmentGroups
				            .withSummary(myRs.getString("lastname")+" "+myRs.getString("firstname"))
				            
				    );
			}
			
			
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}

}
