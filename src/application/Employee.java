package application;
import java.time.LocalDate;

import javafx.scene.image.Image;

public class Employee {
	private String name;
	private String prenom;
	private LocalDate dtN;
	private String statut;
	private int id;
	private int number;
	private String cin;
	private String email;
	private boolean actif;
	private Image img;
	
	
	
	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}

	public boolean isActif() {
		return actif;
	}

	public void setActif(boolean actif) {
		this.actif = actif;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public LocalDate getDtN() {
		return dtN;
	}

	public void setDtN(LocalDate dtN) {
		this.dtN = dtN;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	

	public Employee(String name, String prenom, LocalDate dtN, String statut, int id, int number, String cin,
			String email,boolean actif) {
		super();
		this.name = name;
		this.prenom = prenom;
		this.dtN = dtN;
		this.statut = statut;
		this.id = id;
		this.number = number;
		this.cin = cin;
		this.email = email;
		this.actif = actif;
		if (isActif()) {
			this.setImg(new Image("application/img/online.png"));
		}
		
		else this.setImg(new Image("application/img/offline.png"));

			
	}
	

}
