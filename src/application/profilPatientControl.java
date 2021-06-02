package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class profilPatientControl implements Initializable {
	
@FXML private TextField tdoc;
	
	@FXML private TextField tmal;
	
	@FXML private TextField tnum;
	
	@FXML private TextField tcin;
	
	@FXML private ComboBox<String> tdid;

	@FXML private TextField temail;
	
	@FXML private TextField search;
	
	@FXML private TextField nom;
	
	@FXML private TextField prenom;
	
	@FXML private DatePicker dateN;
	
	@FXML private DatePicker dateC;
	
	@FXML private AnchorPane anchor;
	
	@FXML private ComboBox<Integer> hour, minute, hour1,minute1;
	
	@FXML private TextArea textA;
	
	private String ord;
	
	public ObservableList<Integer> listhour = FXCollections.observableArrayList();
	
	public ObservableList<Integer> listmin = FXCollections.observableArrayList();
	
	public ObservableList<String> Docteur = FXCollections.observableArrayList();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
				
		for (int i=0;i<24;i++)
			listhour.add(i);
		for (int i=0;i<60;i++)
			listmin.add(i);
		hour.setItems(listhour);
		minute.setItems(listmin);
		hour1.setItems(listhour);
		minute1.setItems(listmin);
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		
		try {
			// 1. Get a connection to database
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cabinet", "root" , "");
			
			// 2. Create a statement
			myStmt = myConn.createStatement();
			
			// 3. Execute SQL query
			myRs = myStmt.executeQuery("select * from patient where patient_id = '"+profilControl.getId()+"'");
			
			myRs.next();
				Patient P = new Patient(myRs.getString("lastname"),myRs.getString("firstname"), myRs.getDate("datN").toLocalDate(),myRs.getTimestamp("datCd").toLocalDateTime(),myRs.getString("maladie"),myRs.getInt("patient_id"), myRs.getInt("number"),myRs.getInt("doctor_id"),myRs.getString("cin"),myRs.getString("email"),myRs.getTimestamp("datCf").toLocalDateTime());
			
			
			nom.setText(P.getNom());
			prenom.setText(P.getPrenom());
			dateN.setValue(P.getDateN());
			dateC.setValue(P.getDateC().toLocalDate());
			tmal.setText(P.getMaladie());
			tnum.setText(String.valueOf(P.getNumber()));
			tcin.setText(P.getCin());
			temail.setText(P.getEmail());
			hour1.setValue(P.getDateC().getHour());
			minute1.setValue(P.getDateC().getMinute());
			hour.setValue(P.getDateCf().getHour());
			minute.setValue(P.getDateCf().getMinute());
			
			
			
			/*ord = "\r\n               Ordonnance Medicale\r\n\r\n\r\n"+"Nom : "+P.getNom()+" \r\n" + 
					"\r\n" + 
					"Prenom : "+P.getPrenom()+"\r\n" + 
					"\r\n" + 
					"Date de Naissance : "+P.getDateN()+"\r\n" + 
					"\r\n" + 
					"Adresse e-mail : "+P.getEmail()+"\r\n"+"\r\n"+
					P.getMaladie();*/
			ord = "\r\n" + 
					"               Ordonnance Medicale\r\n" + 
					"\r\n" + 
					"\r\n" + 
					"Docteur "+MainController.getNompre()+"\r\n" +
					"\r\n" + 
					"	Nom : "+nom.getText().toUpperCase()+" \r\n" + 
					"\r\n" + 
					"	Prenom : "+prenom.getText()+"\r\n" + 
					"\r\n" + 
					"	Date de Naissance : "+dateN.getValue()+"\r\n" + 
					"\r\n" + 
					"\r\n" + 
					tmal.getText();
			textA.setText(ord);
			
			myRs = myStmt.executeQuery("select * from employe where id = '"+P.getDoctor_id()+"'");
				
			myRs.next();
			tdid.setValue(myRs.getString("lastname"));
			
			
			myRs = myStmt.executeQuery("select * from employe where statut = 'docteur'");
			while (myRs.next()) {
				Docteur.add(myRs.getString("lastname"));
			}
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
		
			
	}
	
public void ajouterInfo(ActionEvent event) throws NullPointerException, IOException {
	
	Stage primaryStage = new Stage();
	FXMLLoader loader = new FXMLLoader(new File("D:\\ENSIAS\\JAVA\\CabinetMedical\\src\\application\\surePopup.fxml").toURI().toURL());	
	Parent root = loader.load();
	Scene scene = new Scene(root);
	scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	primaryStage.setScene(scene);
	primaryStage.setResizable(false);
	primaryStage.showAndWait();
	
	if(surePopupControl.isConf()) {
		
		Connection myConn=null;
		PreparedStatement myStmt=null;
		ResultSet myRs = null;
		int id = 0;
		
		

		try {
			
		myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cabinet", "root" , "");
		
		myStmt = myConn.prepareStatement("select * from employe where lastname = ?");
		
		myStmt.setString(1, tdid.getValue());
		
		myRs = myStmt.executeQuery();
		
		myRs.next();
		
		id = myRs.getInt("id");
		
		
		
		myStmt = myConn.prepareStatement("UPDATE `patient` SET `lastname` = ?, `firstname` = ?, `datN` = ?, `datCd` = ?,`maladie` = ?, `number` = ? ,`doctor_id` = ? ,`email` = ?, `cin` = ?, `datCf` = ? WHERE `patient`.`patient_id` = ?;");
																	
		myStmt.setString(1,nom.getText());
		myStmt.setString(2,prenom.getText());
		myStmt.setDate(3,Date.valueOf(dateN.getValue()));
		myStmt.setTimestamp(4, Timestamp.valueOf(dateC.getValue().atTime(hour1.getValue(), minute1.getValue())));
		myStmt.setString(5,tmal.getText());
		myStmt.setString(6,tnum.getText());
		myStmt.setInt(7,id);
		myStmt.setString(8,temail.getText());
		myStmt.setString(9,tcin.getText());
		myStmt.setTimestamp(10, Timestamp.valueOf(dateC.getValue().atTime(hour.getValue(), minute.getValue())));
		myStmt.setInt(11, profilControl.getId());
		
		myStmt.executeUpdate();
		}
		catch (Exception exc) {
			exc.printStackTrace();
			
			
		}
		
						
		/*ord = "\r\n               Ordonnance Medicale\r\n\r\n\r\n"+"Nom : "+nom.getText()+" \r\n" + 
				"\r\n" + 
				"Prenom : "+prenom.getText()+"\r\n" + 
				"\r\n" + 
				"Date de Naissance : "+dateN.getValue()+"\r\n" + 
				"\r\n" + 
				"Adresse e-mail : "+temail.getText()+"\r\n"+"\r\n"+
				tmal.getText();*/
		
		ord = "\r\n" + 
				"               Ordonnance Medicale\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"Docteur "+MainController.getNompre()+"\r\n" +
				"\r\n" + 
				"	Nom : "+nom.getText().toUpperCase()+" \r\n" + 
				"\r\n" + 
				"	Prenom : "+prenom.getText()+"\r\n" + 
				"\r\n" + 
				"	Date de Naissance : "+dateN.getValue()+"\r\n" + 
				"\r\n" + 
				"\r\n" + 
				tmal.getText();
		textA.setText(ord);

		
	}
}

	public void Print(ActionEvent actionEvent) {
		ord = textA.getText();
		File ordonnance = new File("ordonnance.txt");
		FileWriter fw;
		
		try {
		      //Création de l'objet
		      fw = new FileWriter(ordonnance);
		     
		      //On écrit la chaîne
		      fw.write(ord);
		      //On ferme le flux
		      fw.close();
		}catch (FileNotFoundException e) {
		      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
		 FileChooser fileChooser = new FileChooser();
		 fileChooser.setTitle("Save file");
		 FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
	     fileChooser.getExtensionFilters().add(extFilter);
		File dest = fileChooser.showSaveDialog(new Stage());
		if (dest != null) {
		    try {
		        Files.copy(ordonnance.toPath(), dest.toPath());
		    } catch (IOException ex) {
		        // handle exception...
		    }
		}

}
}
