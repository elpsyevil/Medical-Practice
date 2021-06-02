package application;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Patient {
	
	private String nom;
	private String prenom;
	private LocalDate dateN;
	private LocalDateTime dateCd;
	private String maladie;
	private int id;
	private int number;
	private int doctor_id;
	private String cin;
	private String email;
	private LocalDateTime dateCf;
	private LocalDate dRdv;

	
	
	
	

	public Patient(String nom, String prenom, LocalDate dateN, LocalDateTime dateCd, String maladie, int id, int number,
			int doctor_id, String cin, String email, LocalDateTime dateCf) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.dateN = dateN;
		this.dateCd = dateCd;
		this.maladie = maladie;
		this.id = id;
		this.number = number;
		this.doctor_id = doctor_id;
		this.cin = cin;
		this.email = email;
		this.dateCf = dateCf;
		
	}
	
	

	public LocalDate getdRdv() {
		return dRdv;
	}



	public void setdRdv(LocalDate dRdv) {
		this.dRdv = dRdv;
	}



	public LocalDateTime getDateCd() {
		return dateCd;
	}



	public void setDateCd(LocalDateTime dateCd) {
		this.dateCd = dateCd;
	}



	public LocalDateTime getDateCf() {
		return dateCf;
	}



	public void setDateCf(LocalDateTime dateCf) {
		this.dateCf = dateCf;
	}



	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public LocalDate getDateN() {
		return dateN;
	}

	public void setDateN(LocalDate dateN) {
		this.dateN = dateN;
	}

	public LocalDateTime getDateC() {
		return dateCd;
	}

	public void setDateC(LocalDateTime dateC) {
		this.dateCd = dateC;
	}

	public String getMaladie() {
		return maladie;
	}

	public void setMaladie(String maladie) {
		this.maladie = maladie;
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

	public int getDoctor_id() {
		return doctor_id;
	}

	public void setDoctor_id(int doctor_id) {
		this.doctor_id = doctor_id;
	}

	
	
	
	
  }
