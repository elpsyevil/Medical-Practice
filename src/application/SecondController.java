package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SecondController implements Initializable {
	
	@FXML
	Button btnPatient;
	@FXML
	AnchorPane anchor;
	@FXML private Button imgSpin;
	@FXML ImageView img1,img2,img3;
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		List<ImageView> imgList = new LinkedList<ImageView>();
		imgList.add(img1);
		imgList.add(img2);
		imgList.add(img3);
		
			final FadeTransition fadeOut = new FadeTransition(Duration.millis(1000));
	       final FadeTransition fadeOut2 = new FadeTransition(Duration.millis(1000));
	       final FadeTransition fadeOut3 = new FadeTransition(Duration.millis(1000));		
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), ev -> {
			if(img1.getOpacity()==1) {
	        fadeOut.setNode(img1);
	        fadeOut.setToValue(0);       
	        fadeOut.playFromStart();
	        fadeOut2.setNode(img2);
	        fadeOut2.setToValue(1);       
	        fadeOut2.playFromStart();
	        
			}
			
			if(img2.getOpacity()==1) {
				fadeOut2.setNode(img2);
		        fadeOut2.setToValue(0);       
		        fadeOut2.playFromStart();
		        fadeOut3.setNode(img3);
		        fadeOut3.setToValue(1);       
		        fadeOut3.playFromStart();
				}
			
			if(img3.getOpacity()==1) {
				fadeOut3.setNode(img3);
		        fadeOut3.setToValue(0);       
		        fadeOut3.playFromStart();
		        fadeOut.setNode(img1);
		        fadeOut.setToValue(1);       
		        fadeOut.playFromStart();
				}
			
		}));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.playFromStart();
		
	}
	
	
	public void openPatient(ActionEvent event) throws IOException {
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(new File("D:\\ENSIAS\\JAVA\\CabinetMedical\\src\\application\\Patient.fxml").toURI().toURL());	
		Parent root = loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.getIcons().add(new Image("application/img/icon.png"));
		primaryStage.setTitle("Cabinet Medical");
		primaryStage.setResizable(false);
		primaryStage.show();
		Stage stage = (Stage) btnPatient.getScene().getWindow();
	    stage.close();
		
	}
	
	public void openEmployee(ActionEvent event) throws IOException {
		
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(new File("D:\\ENSIAS\\JAVA\\CabinetMedical\\src\\application\\Emloyee.fxml").toURI().toURL());	
		Parent root = loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.getIcons().add(new Image("application/img/icon.png"));
		primaryStage.setTitle("Cabinet Medical");
		primaryStage.setResizable(false);
		primaryStage.show();
		Stage stage = (Stage) btnPatient.getScene().getWindow();
	    stage.close();
		
		
	}
	
	public void Logout(ActionEvent event) throws IOException {
		profilControl.setCount(0);
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
		Stage stage = (Stage) btnPatient.getScene().getWindow();
	    stage.close();
		
		
	}
	
	public void openProfil(ActionEvent event) throws IOException {
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
		Stage stage = (Stage) btnPatient.getScene().getWindow();
	    stage.close();
	}
	
	
	
}
