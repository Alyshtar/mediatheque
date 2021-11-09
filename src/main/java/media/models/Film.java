package media.models;

public class Film extends Model {
	private Long id_realisateur;
	private String titre; //Max 100 char
	private String description; //Max 500 char
	private int duree_minute;
	private String genre; //Max 20 char
	
	public Film() {
		super();
	}

	public Film(Long id, Long id_realisateur, String titre, String description, int duree_minute, String genre) {
		super();
		this.id = id;
		this.id_realisateur = id_realisateur;
		this.titre = titre;
		this.description = description;
		this.duree_minute = duree_minute;
		this.genre = genre;
	}
	
	public Film(Long id_realisateur, String titre, String description, int duree_minute, String genre) {
		super();
		this.id_realisateur = id_realisateur;
		this.titre = titre;
		this.description = description;
		this.duree_minute = duree_minute;
		this.genre = genre;
	}

	public Long getId_realisateur() {
		return id_realisateur;
	}

	public void setId_realisateur(Long id_realisateur) {
		this.id_realisateur = id_realisateur;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDuree_minute() {
		return duree_minute;
	}

	public void setDuree_minute(int duree_minute) {
		this.duree_minute = duree_minute;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}
}
