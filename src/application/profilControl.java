package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import jfxtras.scene.control.agenda.Agenda;

public class profilControl implements Initializable {
	
	@FXML JFXButton info,rdv,stat,lout,back;
	
	@FXML Pane p_info,p_rdv,p_stat;
	
	@FXML AnchorPane patientInfo;
	
	@FXML Label labnom;
	
	@FXML Button modifier;
	
	@FXML TextField nom,prenom,cin,email,num,user;
	
	@FXML PasswordField pass;
	
	@FXML ComboBox<String> st;
	
	@FXML DatePicker daten;
	
	@FXML CheckBox check;
	
	@FXML private TableView<Patient> table;
	
	@FXML private TableColumn<Patient,String> cnom;
	
	@FXML private TableColumn<Patient,String> cprenom;
	
	@FXML private TableColumn<Patient,LocalDateTime> cdate;
	
	@FXML Agenda agenda;
	
	@FXML AnchorPane anchorAcc;
	
	@FXML Label labNom,Bonjour;
	
	@FXML VBox vAcc;
	
	private static int id,count=0;
	
	public static int getId() {
		return id;
	}



	public static void setId(int id) {
		profilControl.id = id;
	}

	public ObservableList<Patient> list = FXCollections.observableArrayList();
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
		
		if (count==0) {
		
		final ScaleTransition scaleIn = new ScaleTransition(Duration.seconds(1), Bonjour);
		
		final FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), Bonjour);
		
				
		final FadeTransition labnomFade = new FadeTransition(Duration.seconds(1), labnom);
		
		scaleIn.setByX(-0.2);
		scaleIn.setByY(-0.2);
		//scaleIn.play();
		
		fadeIn.setByValue(1);
		
		labnomFade.setByValue(1);
		
		final TranslateTransition tr = new TranslateTransition(Duration.millis(400), anchorAcc);
		
		tr.setByX(-1286);
		
		
		SequentialTransition seqTransition = new SequentialTransition (
		         new PauseTransition(Duration.millis(1500)), // wait a second
		         fadeIn
		     );
		SequentialTransition seqTransition2 = new SequentialTransition (
		         new PauseTransition(Duration.millis(1500)), // wait a second
		         scaleIn
		     );
		SequentialTransition seqTransition3 = new SequentialTransition (
		         new PauseTransition(Duration.millis(4200)), // wait a second
		         labnomFade
		     );
		SequentialTransition seqTransition4 = new SequentialTransition (
		         new PauseTransition(Duration.millis(4000)), // wait a second
		         tr
		     );
		
		seqTransition.play();
		seqTransition2.play();
		seqTransition3.play();
		seqTransition4.play();
		
		
		count++;
		
		}
		
		else {
			labnom.setOpacity(1);
			anchorAcc.toBack();
		}
		
		
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		int id = 0;
		
		try {
			// 1. Get a connection to database
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cabinet", "root" , "");
			
			// 2. Create a statement
			myStmt = myConn.prepareStatement("select * from employe where username = ?");
			
			myStmt.setString(1, MainController.getUsername());
			
			// 3. Execute SQL query
			myRs = myStmt.executeQuery();
			
			myRs.next();
			
			labnom.setText(myRs.getString("firstname")+" "+myRs.getString("lastname"));
			
			id = myRs.getInt("id");
			
			myStmt = myConn.prepareStatement("select * from patient where doctor_id = ?");
			
			myStmt.setInt(1, id);
			
			// 3. Execute SQL query
			myRs = myStmt.executeQuery();
			// 4. Process the result set
			while (myRs.next()) {
				list.add(new Patient(myRs.getString("lastname"),myRs.getString("firstname"), myRs.getDate("datN").toLocalDate(),myRs.getTimestamp("datCd").toLocalDateTime(),myRs.getString("maladie"),myRs.getInt("patient_id"), myRs.getInt("number"),myRs.getInt("doctor_id"),myRs.getString("cin"),myRs.getString("email"),myRs.getTimestamp("datCf").toLocalDateTime()));
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
		
		
		DateTimeFormatter myDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		cnom.setCellValueFactory(new PropertyValueFactory<Patient, String>("nom"));
		cprenom.setCellValueFactory(new PropertyValueFactory<Patient, String>("prenom"));
		cdate.setCellValueFactory(new PropertyValueFactory<Patient, LocalDateTime>("dateCd"));
		cdate.setCellFactory(column -> {
		    return new TableCell<Patient, LocalDateTime>() {
		        @Override
		        protected void updateItem(LocalDateTime item, boolean empty) {
		            super.updateItem(item, empty);

		            if (item == null || empty) {
		                setText(null);
		                setStyle("");
		            } else {
		                // Format date.
		                setText(myDateFormatter.format(item));

		                // Style all dates in March with a different color.
		                
		            }
		        }
		    };
		});
		
		table.setItems(list);
		afficherInfo();
		
		
	}
	

	
	public void menu(ActionEvent event) throws IOException {
		
		if (event.getSource() == info) {
			p_info.toFront();
		}
		
		else if (event.getSource() == rdv) {
			p_rdv.toFront();
		}
		
		else if (event.getSource() == stat) {
			p_stat.toFront();
		}
		
		else if (event.getSource() == lout) {
			count=0;
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
			Stage stage = (Stage) info.getScene().getWindow();
		    stage.close();
		}
		
		else if (event.getSource() == back) {
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader(new File("D:\\ENSIAS\\JAVA\\CabinetMedical\\src\\application\\Main.fxml").toURI().toURL());	
			Parent root = loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.getIcons().add(new Image("application/img/icon.png"));
			primaryStage.setTitle("Cabinet Medical");
			primaryStage.setResizable(false);
			primaryStage.show();
			Stage stage = (Stage) info.getScene().getWindow();
		    stage.close();
		}
		
		
	}

	public void afficherInfo() {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			// 1. Get a connection to database
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cabinet", "root" , "");
			
			// 2. Create a statement
			myStmt = myConn.prepareStatement("select * from employe where username = ?");
			
			myStmt.setString(1, MainController.getUsername());
			
			// 3. Execute SQL query
			myRs = myStmt.executeQuery();
			
			myRs.next();
			
			nom.setText(myRs.getString("lastname"));
			prenom.setText(myRs.getString("firstname"));
			daten.setValue(myRs.getDate("datN").toLocalDate());
			st.setValue(myRs.getString("statut"));
			num.setText(String.valueOf(myRs.getInt("numero")));
			cin.setText(myRs.getString("cin"));
			email.setText(myRs.getString("email"));
			user.setText(myRs.getString("username"));
			pass.setText(myRs.getString("password"));
			
			
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
		
		
		
	}
	
	public void Show(ActionEvent event) {
		if (check.isSelected()){
			 pass.setPromptText(pass.getText());
			 pass.setText(""); 
			  

			  }else {
			 pass .setText(pass.getPromptText());
			 pass.setPromptText("");
			 pass.setDisable(false);
			 }
	}
	
	public void Modifier(ActionEvent event) throws IOException {
		if (modifier.getText().equals("Modifier")) {
		nom.setEditable(true);
		nom.setStyle("-fx-border-color: red;");
		
		prenom.setEditable(true);
		prenom.setStyle("-fx-border-color: red;");
		
		cin.setEditable(true);
		cin.setStyle("-fx-border-color: red;");
		
		email.setEditable(true);
		email.setStyle("-fx-border-color: red;");
		
		st.setDisable(false);
		st.setStyle("-fx-border-color: red;");
		
		daten.setDisable(false);
		daten.setStyle("-fx-border-color: red;");
		
		user.setEditable(true);
		user.setStyle("-fx-border-color: red;");
		
		pass.setEditable(true);
		pass.setStyle("-fx-border-color: red;");
		
		num.setEditable(true);
		num.setStyle("-fx-border-color: red;");
		
		modifier.setText("Enregistrer");
		}
		
		else {
			
			
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader(new File("D:\\ENSIAS\\JAVA\\CabinetMedical\\src\\application\\surePopup.fxml").toURI().toURL());	
			Parent root = loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.getIcons().add(new Image("application/img/icon.png"));
			primaryStage.setTitle("Cabinet Medical");
			primaryStage.setResizable(false);
			primaryStage.showAndWait();
			Connection myConn=null;
			PreparedStatement myStmt=null;
			
			
			
			if(surePopupControl.isConf()) {

			try {
				
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cabinet", "root" , "");
			myStmt = myConn.prepareStatement("UPDATE `employe` SET `lastname` = ?, firstname = ?, `datN` = ?, `numero` = ?, `statut` = ?, `email` = ?, `cin` = ?, `username` = ?, `password` = ? WHERE `employe`.`username` = ?;");
			
			myStmt.setString(1,nom.getText());
			myStmt.setString(2,prenom.getText());
			myStmt.setDate(3,Date.valueOf(daten.getValue()));
			myStmt.setString(4,num.getText());
			myStmt.setString(5,st.getValue());
			myStmt.setString(6,email.getText());
			myStmt.setString(7,cin.getText());
			myStmt.setString(8,user.getText());
			myStmt.setString(9,pass.getText());
			myStmt.setString(10,MainController.getUsername());
			
			
			// 3. Execute SQL query
			myStmt.executeUpdate();
			}
			catch (Exception exc) {
				exc.printStackTrace();
			}
			
			nom.setEditable(false);
			nom.setStyle("-fx-border-color: white;");
			
			prenom.setEditable(false);
			prenom.setStyle("-fx-border-color: white;");
			
			cin.setEditable(false);
			cin.setStyle("-fx-border-color: white;");
			
			email.setEditable(false);
			email.setStyle("-fx-border-color: white;");
			
			st.setDisable(true);
			st.setStyle("-fx-border-color: white;");
			
			daten.setDisable(true);
			daten.setStyle("-fx-border-color: white;");
			
			user.setEditable(false);
			user.setStyle("-fx-border-color: white;");
			
			pass.setEditable(false);
			pass.setStyle("-fx-border-color: white;");
			
			num.setEditable(false);
			num.setStyle("-fx-border-color: white;");
			
			MainController.setUsername(user.getText());
			modifier.setText("Modifier");
			labnom.setText(prenom.getText());
			}
			
		}
			
		
	}
	
	public void Afficher(MouseEvent event) throws IOException {
		if (event.getClickCount()==2) {
			id = Integer.valueOf(agenda.selectedAppointments().get(0).getDescription());
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader(new File("D:\\ENSIAS\\JAVA\\CabinetMedical\\src\\application\\patientInfo.fxml").toURI().toURL());	
			Parent root = loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.getIcons().add(new Image("application/img/icon.png"));
			primaryStage.setTitle("Cabinet Medical");
			primaryStage.setResizable(false);
			primaryStage.showAndWait();
			update();
			
		}
		
	}
	
	public void update() {
		list.clear();
		agenda.appointments().clear();
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		int id = 0;
		
		try {
			// 1. Get a connection to database
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cabinet", "root" , "");
			
			// 2. Create a statement
			myStmt = myConn.prepareStatement("select * from employe where username = ?");
			
			myStmt.setString(1, MainController.getUsername());
			
			// 3. Execute SQL query
			myRs = myStmt.executeQuery();
			
			myRs.next();
			
			labnom.setText(myRs.getString("firstname")+" "+myRs.getString("lastname"));
			
			id = myRs.getInt("id");
			
			myStmt = myConn.prepareStatement("select * from patient where doctor_id = ?");
			
			myStmt.setInt(1, id);
			
			// 3. Execute SQL query
			myRs = myStmt.executeQuery();
			// 4. Process the result set
			while (myRs.next()) {
				list.add(new Patient(myRs.getString("lastname"),myRs.getString("firstname"), myRs.getDate("datN").toLocalDate(),myRs.getTimestamp("datCd").toLocalDateTime(),myRs.getString("maladie"),myRs.getInt("patient_id"), myRs.getInt("number"),myRs.getInt("doctor_id"),myRs.getString("cin"),myRs.getString("email"),myRs.getTimestamp("datCf").toLocalDateTime()));
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
		
		
		
		cnom.setCellValueFactory(new PropertyValueFactory<Patient, String>("nom"));
		cprenom.setCellValueFactory(new PropertyValueFactory<Patient, String>("prenom"));
		
		table.setItems(list);
		afficherInfo();
		
	}


	public void toProfil(MouseEvent actionEvent) {
		final TranslateTransition tr = new TranslateTransition(Duration.millis(200), anchorAcc);
		
		tr.setByX(-1286);
		//tr.play();
		
	}



	public static int getCount() {
		return count;
	}



	public static void setCount(int count) {
		profilControl.count = count;
	}
	
	
	
}
