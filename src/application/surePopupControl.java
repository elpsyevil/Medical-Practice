package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class surePopupControl {
	@FXML private Button Oui, Non;
	private static boolean Conf;
	
	public void ouiAction(ActionEvent event) {
		setConf(true);
		Stage stage = (Stage) Oui.getScene().getWindow();
	    stage.close();
		
	}
	
	public void nonAction(ActionEvent event) {
		setConf(false);
		Stage stage = (Stage) Oui.getScene().getWindow();
	    stage.close();
		
		
	}

	public static boolean isConf() {
		return Conf;
	}

	public static void setConf(boolean conf) {
		Conf = conf;
	}
	
	

}
