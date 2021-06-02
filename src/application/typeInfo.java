package application;

public enum typeInfo {
	ID("id"),
	Nom("lastname"),
	Prenom("firstname"),
	CIN("cin");
	
	private String f = "";
	
	typeInfo(String f){
	    this.f = f;
	  }
	
	  public String toString(){
	    return f;
	  }

}
