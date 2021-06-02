package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class patientController implements Initializable{

	@FXML private Button btnBack,clear;
	
	@FXML private TableView<Patient> table;
	
	@FXML private TableColumn<Patient,String> cnom;
	
	@FXML private TableColumn<Patient,String> cprenom;
	
	@FXML private TableColumn<Patient,LocalDate> cdateN;
	
	
    @FXML private TableColumn<Patient,Integer> cid;
	
	@FXML private TableColumn<Patient,Integer> cnumber;
	
	@FXML private TextField tdoc;
	
	@FXML private TextField tmal;
	
	@FXML private TextField tnum;
	
	@FXML private TextField tcin;
	
	@FXML private ComboBox<String> tdid,type;

	@FXML private TextField temail;
	
	@FXML private TextField search;
	
	@FXML private TextField nom;
	
	@FXML private TextField prenom;
	
	@FXML private DatePicker dateN;
	
	@FXML private DatePicker dateC;
	
	@FXML private AnchorPane anchor;
	
	@FXML private ComboBox<Integer> hour, minute, hour1,minute1;
	
	@FXML private Button ajouter,modifier,show;

	public ObservableList<Patient> list = FXCollections.observableArrayList();
	
	public ObservableList<Integer> listhour = FXCollections.observableArrayList();
	
	public ObservableList<Integer> listmin = FXCollections.observableArrayList();
	
	public ObservableList<String> Docteur = FXCollections.observableArrayList();
	
	public ObservableList<String> listType = FXCollections.observableArrayList("ID","Nom","Prenom","CIN");

	
	public void Back(ActionEvent event) throws IOException {
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
		Stage stage = (Stage) btnBack.getScene().getWindow();
	    stage.close();
	}

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
		type.setItems(listType);
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			// 1. Get a connection to database
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cabinet", "root" , "");
			
			// 2. Create a statement
			myStmt = myConn.createStatement();
			
			// 3. Execute SQL query
			myRs = myStmt.executeQuery("select * from patient");
			
			// 4. Process the result set
			while (myRs.next()) {
				list.add(new Patient(myRs.getString("lastname"),myRs.getString("firstname"), myRs.getDate("datN").toLocalDate(),myRs.getTimestamp("datCd").toLocalDateTime(),myRs.getString("maladie"),myRs.getInt("patient_id"), myRs.getInt("number"),myRs.getInt("doctor_id"),myRs.getString("cin"),myRs.getString("email"),myRs.getTimestamp("datCf").toLocalDateTime()));
			}
			
			myRs = myStmt.executeQuery("select * from employe where statut = 'docteur'");
			while (myRs.next()) {
				Docteur.add(myRs.getString("lastname"));
			}
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
		
		DateTimeFormatter myDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		cnom.setCellValueFactory(new PropertyValueFactory<Patient, String>("Nom"));
		cprenom.setCellValueFactory(new PropertyValueFactory<Patient, String>("Prenom"));
		cid.setCellValueFactory(new PropertyValueFactory<Patient, Integer>("id"));
		cnumber.setCellValueFactory(new PropertyValueFactory<Patient, Integer>("number"));
		cdateN.setCellValueFactory(new PropertyValueFactory<Patient, LocalDate>("dateN"));
		cdateN.setCellFactory(column -> {
		    return new TableCell<Patient, LocalDate>() {
		        @Override
		        protected void updateItem(LocalDate item, boolean empty) {
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
		tdid.setItems(Docteur);
		table.setItems(list);
		
	}
	
	
	
	public void openAjouter(ActionEvent event) throws IOException {
		final TranslateTransition tt = new TranslateTransition(Duration.millis(500),anchor);
        tt.setFromX(0);
        tt.setToX(1280);
        tt.play();
	}
	
	public void ajouterInfo(ActionEvent event) throws NullPointerException, IOException {
		if (MainController.getSt().toLowerCase().equals("docteur")) {
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader(new File("D:\\ENSIAS\\JAVA\\CabinetMedical\\src\\application\\Droit.fxml").toURI().toURL());	
			Parent root = loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.getIcons().add(new Image("application/img/icon.png"));
			primaryStage.setTitle("Cabinet Medical");
			primaryStage.setResizable(false);
			primaryStage.show();
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
		
		if (event.getSource() == ajouter)
		myStmt = myConn.prepareStatement("INSERT INTO `patient` (`patient_id`, `lastname`, `firstname`, `datN`, `datCd`, `maladie`, `number`, `doctor_id`, `email`, `cin`,`datCf`) VALUES (NULL,?,?,?,?,?,?,?,?,?,?)");
		
		else myStmt = myConn.prepareStatement("UPDATE `patient` SET `lastname` = ?, `firstname` = ?, `datN` = ?, `datCd` = ?,`maladie` = ?, `number` = ? ,`doctor_id` = ? ,`email` = ?, `cin` = ?, `datCf` = ? WHERE `patient`.`patient_id` = ?;");
																	
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
		if (event.getSource() == modifier)
		myStmt.setInt(11, table.getSelectionModel().getSelectedItem().getId());
		
		myStmt.executeUpdate();
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
		
		update();
		}
	
		
	}
	}
	
	public void Annuler(ActionEvent event) {
		nom.clear();
		prenom.clear();
		final TranslateTransition tt = new TranslateTransition(Duration.millis(500),anchor);
        tt.setFromX(1280);
        tt.setToX(0);
        tt.play();
		
	}
	
public void recherche(ActionEvent event) {
		
		list.clear();
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		typeInfo tinfo = typeInfo.valueOf(type.getValue());
		
		try {
			// 1. Get a connection to database
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cabinet", "root" , "");
			
			if (type.getValue().equals("ID"))
				myStmt = myConn.prepareStatement("select * from patient where patient_id = ?");
			else
			myStmt = myConn.prepareStatement("select * from patient where "+tinfo.toString() +"= ?");
			
			myStmt.setString(1,search.getText());
			
			// 3. Execute SQL query
			myRs = myStmt.executeQuery();
			
			// 4. Process the result set
			while (myRs.next()) {
				list.add(new Patient(myRs.getString("lastname"),myRs.getString("firstname"), myRs.getDate("datN").toLocalDate(),myRs.getTimestamp("datCd").toLocalDateTime(),myRs.getString("maladie"),myRs.getInt("patient_id"), myRs.getInt("number"),myRs.getInt("doctor_id"),myRs.getString("cin"),myRs.getString("email"),myRs.getTimestamp("datCf").toLocalDateTime()));
			}
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
		
		table.setItems(list);
	}

	public void delete(ActionEvent event) throws IOException {
		if (MainController.getSt().toLowerCase().equals("docteur")) {
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader(new File("D:\\ENSIAS\\JAVA\\CabinetMedical\\src\\application\\Droit.fxml").toURI().toURL());	
			Parent root = loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.getIcons().add(new Image("application/img/icon.png"));
			primaryStage.setTitle("Cabinet Medical");
			primaryStage.setResizable(false);
			primaryStage.show();
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
	
	if(surePopupControl.isConf()) {

	Connection myConn = null;
	Statement myStmt = null;
	
	if(!table.getSelectionModel().isEmpty()) {
	try {
		// 1. Get a connection to database
		myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cabinet", "root" , "");
		
		// 2. Create a statement
		myStmt = myConn.createStatement();
		
		myStmt.executeUpdate("delete from patient where patient_id='"+table.getSelectionModel().getSelectedItem().getId()+"'");
		// 4. Process the result set
		
	}
	catch (Exception exc) {
		exc.printStackTrace();
	}
	update();
	}
	}
}
}
	public void update() {
		list.clear();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			// 1. Get a connection to database
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cabinet", "root" , "");
			
			// 2. Create a statement
			myStmt = myConn.createStatement();
			
			// 3. Execute SQL query
			myRs = myStmt.executeQuery("select * from patient");
			
			// 4. Process the result set
			while (myRs.next()) {
				list.add(new Patient(myRs.getString("lastname"),myRs.getString("firstname"), myRs.getDate("datN").toLocalDate(),myRs.getTimestamp("datCd").toLocalDateTime(),myRs.getString("maladie"),myRs.getInt("patient_id"), myRs.getInt("number"),myRs.getInt("doctor_id"),myRs.getString("cin"),myRs.getString("email"),myRs.getTimestamp("datCf").toLocalDateTime()));
			}
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
		table.setItems(list);

	}
	
	public void afficherInfo(ActionEvent event) {
		
		nom.setText(table.getSelectionModel().getSelectedItem().getNom());
		prenom.setText(table.getSelectionModel().getSelectedItem().getPrenom());
		dateN.setValue(table.getSelectionModel().getSelectedItem().getDateN());
		dateC.setValue(table.getSelectionModel().getSelectedItem().getDateC().toLocalDate());
		tmal.setText(table.getSelectionModel().getSelectedItem().getMaladie());
		tnum.setText(String.valueOf(table.getSelectionModel().getSelectedItem().getNumber()));
		tcin.setText(table.getSelectionModel().getSelectedItem().getCin());
		temail.setText(table.getSelectionModel().getSelectedItem().getEmail());
		hour1.setValue(table.getSelectionModel().getSelectedItem().getDateC().getHour());
		minute1.setValue(table.getSelectionModel().getSelectedItem().getDateC().getMinute());
		hour.setValue(table.getSelectionModel().getSelectedItem().getDateCf().getHour());
		minute.setValue(table.getSelectionModel().getSelectedItem().getDateCf().getMinute());
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			// 1. Get a connection to database
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cabinet", "root" , "");
			
			// 2. Create a statement
			myStmt = myConn.createStatement();
			
			// 3. Execute SQL query
			myRs = myStmt.executeQuery("select * from employe where id = '"+table.getSelectionModel().getSelectedItem().getDoctor_id()+"'");
			
			// 4. Process the result set
			myRs.next();
			tdid.setValue(myRs.getString("lastname"));
			
			}
		
		catch (Exception exc) {
			exc.printStackTrace();
		}
		
	}
	
public void afficher(MouseEvent event) {
		if(event.getClickCount()==2) {
		nom.setText(table.getSelectionModel().getSelectedItem().getNom());
		prenom.setText(table.getSelectionModel().getSelectedItem().getPrenom());
		dateN.setValue(table.getSelectionModel().getSelectedItem().getDateN());
		dateC.setValue(table.getSelectionModel().getSelectedItem().getDateC().toLocalDate());
		tmal.setText(table.getSelectionModel().getSelectedItem().getMaladie());
		tnum.setText(String.valueOf(table.getSelectionModel().getSelectedItem().getNumber()));
		tcin.setText(table.getSelectionModel().getSelectedItem().getCin());
		temail.setText(table.getSelectionModel().getSelectedItem().getEmail());
		hour1.setValue(table.getSelectionModel().getSelectedItem().getDateC().getHour());
		minute1.setValue(table.getSelectionModel().getSelectedItem().getDateC().getMinute());
		hour.setValue(table.getSelectionModel().getSelectedItem().getDateCf().getHour());
		minute.setValue(table.getSelectionModel().getSelectedItem().getDateCf().getMinute());
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			// 1. Get a connection to database
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cabinet", "root" , "");
			
			// 2. Create a statement
			myStmt = myConn.createStatement();
			
			// 3. Execute SQL query
			myRs = myStmt.executeQuery("select * from employe where id = '"+table.getSelectionModel().getSelectedItem().getDoctor_id()+"'");
			
			// 4. Process the result set
			myRs.next();
			tdid.setValue(myRs.getString("lastname"));
			
			}
		
		catch (Exception exc) {
			exc.printStackTrace();
		}

		}
		
	}
	
	public void Clear(ActionEvent event) {
		search.clear();
		update();
		
	}
	
	public void openAgenda(ActionEvent actionEvent) throws IOException {
		employeeContol.setD_id(tdid.getValue());
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(new File("D:\\ENSIAS\\JAVA\\CabinetMedical\\src\\application\\Calendrier.fxml").toURI().toURL());	
		Parent root = loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.getIcons().add(new Image("application/img/icon.png"));
		primaryStage.setTitle("Cabinet Medical");
		primaryStage.setResizable(false);
		primaryStage.show();
		
	}
	
	
}
