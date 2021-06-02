package application;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class registerComtrol implements Initializable {
	
	@FXML TextField nom,prenom,user,num,email,cin;
	
	@FXML PasswordField pass;
	
	@FXML DatePicker date;
	
	@FXML ComboBox<String> status;
	
	@FXML Label lab;
	
	public ObservableList<String> listst = FXCollections.observableArrayList("Docteur","Autre");

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		status.setItems(listst);
	}

	public void Register(ActionEvent event) {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cabinet", "root" , "");
			
			// 2. Create a statement
			
		if (nom.getText().isEmpty() || prenom.getText().isEmpty() || status.getValue()==null || user.getText().isEmpty() || pass.getText().isEmpty() || num.getText().isEmpty() || date.getValue()==null || cin.getText().isEmpty() || email.getText().isBlank() ) {
			
			lab.setTextFill(Color.RED);
			lab.setText("Tous les champs sont obligatoires");
		}
		else {
		myStmt = myConn.prepareStatement("select * from employe where username = ?");
		
		myStmt.setString(1,user.getText());
		
		// 3. Execute SQL query
		myRs = myStmt.executeQuery();
		
		if(myRs.next()) {
			lab.setTextFill(Color.RED);
			lab.setText("Nom d'utilisateur existant");
			
		}
		
		else {
		
					// 2. Create a statement
			myStmt = myConn.prepareStatement("INSERT INTO `employe` (`id`, `lastname`, `firstname`, `datN`, `numero`, `statut`,`email`,`cin`, `username`, `password`) VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			
			myStmt.setString(1,nom.getText());
			myStmt.setString(2,prenom.getText());
			myStmt.setDate(3,Date.valueOf(date.getValue()));
			myStmt.setString(4,num.getText());
			myStmt.setString(5,status.getValue());
			myStmt.setString(6,email.getText());
			myStmt.setString(7,cin.getText());
			myStmt.setString(8,user.getText());
			myStmt.setString(9,pass.getText());
			
			// 3. Execute SQL query
			myStmt.executeUpdate();
			
			
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader(new File("D:\\ENSIAS\\JAVA\\CabinetMedical\\src\\application\\Log.fxml").toURI().toURL());	
			Parent root = loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.getIcons().add(new Image("application/img/icon.png"));
			primaryStage.setTitle("Cabinet Medical");
			primaryStage.setResizable(false);
			primaryStage.show();
			Stage stage = (Stage) user.getScene().getWindow();
		    stage.close();
		}
		}
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
		
	}
	
	public void Back(ActionEvent actionEvent) throws IOException {
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(new File("D:\\ENSIAS\\JAVA\\CabinetMedical\\src\\application\\Log.fxml").toURI().toURL());	
		Parent root = loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.getIcons().add(new Image("application/img/icon.png"));
		primaryStage.setTitle("Cabinet Medical");
		primaryStage.setResizable(false);
		primaryStage.show();
		Stage stage = (Stage) num.getScene().getWindow();
	    stage.close();
		
	}
	
}
