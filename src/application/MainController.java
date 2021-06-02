package application;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainController  {
	@FXML
	private Label Status;
	
	@FXML
	private TextField User;
	
	@FXML
	private PasswordField Pass;
	
	@FXML
	private Button Close;
	
	private static boolean admin = false;
	
	private static String username,st,nompre;

	public void Log(ActionEvent event) throws IOException, InterruptedException {
		
			Connection myConn = null;
			PreparedStatement myStmt = null;
			ResultSet myRs = null;
			try {
				// 1. Get a connection to database
				myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cabinet", "root" , "");
				
				// 2. Create a statement
				myStmt = myConn.prepareStatement("select * from employe where username = ?");
				
				myStmt.setString(1,User.getText());
				
				// 3. Execute SQL query
				myRs = myStmt.executeQuery();
				
				// 4. Process the result set
				myRs.next();
				
				
								
				
				if (Pass.getText().equals(myRs.getString("password"))) {
					username = User.getText();
					st=myRs.getString("statut");
					nompre = myRs.getString("lastname").toUpperCase()+" "+myRs.getString("firstname");
					myStmt = myConn.prepareStatement("UPDATE `employe` SET `Actif` = ? where username = ?");
					
					myStmt.setInt(1,1);
					myStmt.setString(2,User.getText());
					
					myStmt.executeUpdate();
					
						Status.setText("Login successful");
						Status.setTextFill(Color.GREEN);
						Stage primaryStage = new Stage();
						FXMLLoader loader = new FXMLLoader(new File("D:\\ENSIAS\\JAVA\\CabinetMedical\\src\\application\\profil.fxml").toURI().toURL());	
						Parent root = loader.load();
						Scene scene = new Scene(root);
						scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
						primaryStage.setScene(scene);
						primaryStage.getIcons().add(new Image("application/img/icon.png"));
						primaryStage.setTitle("Cabinet Medical");
						primaryStage.setResizable(false);
						primaryStage.show();
						Stage stage = (Stage) Close.getScene().getWindow();
					    stage.close();
	             }
				
				else {
					Status.setText("Login Failed");
					Status.setTextFill(Color.RED);
				}
				}
				
			catch (Exception exc) {
				exc.printStackTrace();
				
			}
			
			
		
	}

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		MainController.username = username;
	}

	public static boolean isAdmin() {
		return admin;
	}

	public static void setAdmin(boolean admin) {
		MainController.admin = admin;
	}
	
	public void Register(MouseEvent event) throws IOException {
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(new File("D:\\ENSIAS\\JAVA\\CabinetMedical\\src\\application\\Register.fxml").toURI().toURL());	
		Parent root = loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.getIcons().add(new Image("application/img/icon.png"));
		primaryStage.setTitle("Cabinet Medical");
		primaryStage.setResizable(false);
		primaryStage.show();
		Stage stage = (Stage) Close.getScene().getWindow();
	    stage.close();
		
	}

	public static String getSt() {
		return st;
	}

	public static void setSt(String st) {
		MainController.st = st;
	}

	public static String getNompre() {
		return nompre;
	}

	public static void setNompre(String nompre) {
		MainController.nompre = nompre;
	}
	
	
	
}

