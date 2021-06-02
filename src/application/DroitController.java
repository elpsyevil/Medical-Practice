package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DroitController {

	
	@FXML Button Close;
	
	public void Fermer(ActionEvent event) {
		
		Stage stage = (Stage) Close.getScene().getWindow();
	    stage.close();

		
	}
}
