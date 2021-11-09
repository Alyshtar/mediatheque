package media.models;

public class Realisateur extends Model {
	private String nom;  //Max 100 char
	private String prenom; //Max 100 char
	private Integer age;
	private String pays; //Max 200 char
	
	
	public Realisateur() {
		super();
	}


	public Realisateur(String nom, String prenom, Integer age, String pays) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.age = age;
		this.pays = pays;
	}
	
	public Realisateur(Long id, String nom, String prenom, Integer age, String pays) {
		super();
		this.id=id;
		this.nom = nom;
		this.prenom = prenom;
		this.age = age;
		this.pays = pays;
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


	public Integer getAge() {
		return age;
	}


	public void setAge(Integer age) {
		this.age = age;
	}


	public String getPays() {
		return pays;
	}


	public void setPays(String pays) {
		this.pays = pays;
		
	}
	
	
	
	
	
	
	
	 
	

}
