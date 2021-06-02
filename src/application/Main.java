package application;
	
import java.io.File;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import java.sql.*;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(new File("D:\\ENSIAS\\JAVA\\CabinetMedical\\src\\application\\Log.fxml").toURI().toURL());	
			Parent root = loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			//setUserAgentStylesheet(STYLESHEET_CASPIAN);
			primaryStage.getIcons().add(new Image("application/img/icon.png"));
			primaryStage.setTitle("Cabinet Medical");
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws SQLException,ClassCastException {
		launch(args);
		if(Platform.isImplicitExit()) {
			Connection myConn = null;
			PreparedStatement myStmt = null;
						
			try {
				// 1. Get a connection to database
				myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cabinet", "root" , "");
				
				// 2. Create a statement
				myStmt = myConn.prepareStatement("select * from employe where lastname = ?");
				
				myStmt = myConn.prepareStatement("UPDATE `employe` SET `Actif` = ? WHERE `employe`.`username` = ?;");
				
				myStmt.setInt(1, 0);
				myStmt.setString(2, MainController.getUsername());
				myStmt.executeUpdate();

			}
			catch (Exception exc) {
				exc.printStackTrace();
			}
		}
		
	}
}
