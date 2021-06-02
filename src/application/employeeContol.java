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
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class employeeContol implements Initializable{

	@FXML private Button btnBack;
	
	@FXML private TableView<Employee> table;
	
	@FXML private TableColumn<Employee,String> cnom;
	
	@FXML private TableColumn<Employee,String> cprenom;
	
	
	@FXML private TableColumn<Employee,String> statut;
	
	@FXML private TableColumn<Employee,Integer> cid;
	
	@FXML private TableColumn<Employee,Integer> cnumber;
	
	@FXML private TableColumn<Employee,Image> activ ;
	
	@FXML private TextField search;
	
	@FXML private DatePicker dateN;
	
	@FXML private ComboBox<String> type;
	
	@FXML private AnchorPane anchor;
	
	@FXML private TextField nom,prenom,cin,email,num,user;
	
	@FXML private PasswordField pass;
	
	@FXML private ComboBox<String> st;
	
	@FXML private DatePicker daten;
	
	public ObservableList<Employee> list = FXCollections.observableArrayList();
	
	public ObservableList<String> listType = FXCollections.observableArrayList("ID","Nom","Prenom","CIN");
	
	private static String d_id;
	
	public ObservableList<String> listst = FXCollections.observableArrayList("Docteur","Autre");

	
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
		
		type.setItems(listType);
		st.setItems(listst);

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			// 1. Get a connection to database
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cabinet", "root" , "");
			
			// 2. Create a statement
			myStmt = myConn.createStatement();
			
			// 3. Execute SQL query
			myRs = myStmt.executeQuery("select * from employe");
			
			// 4. Process the result set
			while (myRs.next()) {
				list.add(new Employee(myRs.getString("lastname"),myRs.getString("firstname"), myRs.getDate("datN").toLocalDate(),myRs.getString("statut"),myRs.getInt("id"), myRs.getInt("numero"),myRs.getString("cin"),myRs.getString("email"),myRs.getBoolean("Actif")));
				
			}
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}

		DateTimeFormatter myDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		cnom.setCellValueFactory(new PropertyValueFactory<Employee, String>("name"));
		cprenom.setCellValueFactory(new PropertyValueFactory<Employee, String>("Prenom"));
		cid.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("id"));
		cnumber.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("number"));
		
		statut.setCellValueFactory(new PropertyValueFactory<Employee, String>("statut"));
			
		
		 activ.setCellFactory(param -> {
		       //Set up the ImageView
		       final ImageView imageview = new ImageView();
		       imageview.setFitHeight(10);
		       imageview.setFitWidth(10);

		       //Set up the Table
		       TableCell<Employee, Image> cell = new TableCell<Employee, Image>() {
		           public void updateItem(Image item, boolean empty) {
		             if (item != null) {
		                  imageview.setImage(item);
		             }
		           }
		        };
		        // Attach the imageview to the cell
		        cell.setGraphic(imageview);
		        return cell;
		   });
		 activ.setCellValueFactory(new PropertyValueFactory<Employee, Image>("img"));
		
		
		table.setItems(list);
		
	}
	
		
	public void recherche(ActionEvent event) {
		
		list.clear();
		//table.setItems(list);
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		typeInfo tinfo = typeInfo.valueOf(type.getValue());
		
		
		try {
			// 1. Get a connection to database
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cabinet", "root" , "");
			
			// 2. Create a statement
			myStmt = myConn.prepareStatement("select * from employe where "+tinfo.toString() +"= ?");
			
			myStmt.setString(1,search.getText());
			
			// 3. Execute SQL query
			myRs = myStmt.executeQuery();
			
			// 4. Process the result set
			while (myRs.next()) {
				list.add(new Employee(myRs.getString("lastname"),myRs.getString("firstname"), myRs.getDate("datN").toLocalDate(),myRs.getString("statut"),myRs.getInt("id"), myRs.getInt("numero"),myRs.getString("cin"),myRs.getString("email"),myRs.getBoolean("Actif")));
			}
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
		activ.setCellFactory(param -> {
		       //Set up the ImageView
		       final ImageView imageview = new ImageView();
		       imageview.setFitHeight(10);
		       imageview.setFitWidth(10);

		       //Set up the Table
		       TableCell<Employee, Image> cell = new TableCell<Employee, Image>() {
		           public void updateItem(Image item, boolean empty) {
		             if (item != null) {
		                  imageview.setImage(item);
		             }
		           }
		        };
		        // Attach the imageview to the cell
		        cell.setGraphic(imageview);
		        return cell;
		   });
		 activ.setCellValueFactory(new PropertyValueFactory<Employee, Image>("img"));
		
		table.setItems(list);
	}
	
	public void delete(ActionEvent event) throws IOException {
		if (!MainController.getSt().toLowerCase().equals("admin")) {
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
			
			myStmt.executeUpdate("delete from employe where id='"+table.getSelectionModel().getSelectedItem().getId()+"'");
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
				myRs = myStmt.executeQuery("select * from employe");
				
				// 4. Process the result set
				while (myRs.next()) {
					list.add(new Employee(myRs.getString("lastname"),myRs.getString("firstname"), myRs.getDate("datN").toLocalDate(),myRs.getString("statut"),myRs.getInt("id"), myRs.getInt("numero"),myRs.getString("cin"),myRs.getString("email"),myRs.getBoolean("Actif")));
				}
			}
			catch (Exception exc) {
				exc.printStackTrace();
			}
			
			activ.setCellFactory(param -> {
			       //Set up the ImageView
			       final ImageView imageview = new ImageView();
			       imageview.setFitHeight(10);
			       imageview.setFitWidth(10);

			       //Set up the Table
			       TableCell<Employee, Image> cell = new TableCell<Employee, Image>() {
			           public void updateItem(Image item, boolean empty) {
			             if (item != null) {
			                  imageview.setImage(item);
			             }
			           }
			        };
			        // Attach the imageview to the cell
			        cell.setGraphic(imageview);
			        return cell;
			   });
			 activ.setCellValueFactory(new PropertyValueFactory<Employee, Image>("img"));
			table.setItems(list);

		}
		
		public void Clear(ActionEvent event) {
			search.clear();
			update();
			
		}
		
		public void afficherCalendrier(MouseEvent event) throws IOException {
			if (event.getClickCount() == 2) {
				d_id = table.getSelectionModel().getSelectedItem().getName();
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader(new File("D:\\ENSIAS\\JAVA\\CabinetMedical\\src\\application\\Calendrier.fxml").toURI().toURL());	
				Parent root = loader.load();
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.setResizable(false);
				primaryStage.show();
			}
			
		}

		public static String getD_id() {
			return d_id;
		}

		public static void setD_id(String d_id) {
			employeeContol.d_id = d_id;
		}
		
		public void afficherInfo(ActionEvent actionEvent) {
			
			Connection myConn = null;
			PreparedStatement myStmt = null;
			ResultSet myRs = null;
			
			try {
				// 1. Get a connection to database
				myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cabinet", "root" , "");
				
				// 2. Create a statement
				myStmt = myConn.prepareStatement("select * from employe where id = ?");
				
				myStmt.setInt(1,table.getSelectionModel().getSelectedItem().getId());
				
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
				if(MainController.getSt().toLowerCase().equals("admin")) {
				user.setText(myRs.getString("username"));
				pass.setText(myRs.getString("password"));
				}
				
				
			}
			catch (Exception exc) {
				exc.printStackTrace();
			}
			
		}
		
		public void Modify(ActionEvent actionEvent) throws IOException {
			if (!MainController.getSt().toLowerCase().equals("admin")) {
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
			myStmt.setString(10,user.getText());
			
			
			// 3. Execute SQL query
			myStmt.executeUpdate();
			}
			catch (Exception exc) {
				exc.printStackTrace();
			}
			
			update();
		}
		
	
}
}
}
