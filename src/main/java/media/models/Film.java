package media.models;

public class Film extends Model {
	private Long idRealisateur;
	private String titre; //Max 100 char
	private String description; //Max 500 char
	private int dureeMinutes;
	private String genre; //Max 20 char
	
	public Film() {
		super();
	}

	public Film(Long id, Long id_realisateur, String titre, String description, int duree_minute, String genre) {
		super();
		this.id = id;
		this.idRealisateur = id_realisateur;
		this.titre = titre;
		this.description = description;
		this.dureeMinutes = duree_minute;
		this.genre = genre;
	}
	
	public Film(Long id_realisateur, String titre, String description, int duree_minute, String genre) {
		super();
		this.idRealisateur = id_realisateur;
		this.titre = titre;
		this.description = description;
		this.dureeMinutes = duree_minute;
		this.genre = genre;
	}

	public Long getIdRealisateur() {
		return idRealisateur;
	}

	public void setIdRealisateur(Long id_realisateur) {
		this.idRealisateur = id_realisateur;
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

	public int getDureeMinutes() {
		return dureeMinutes;
	}

	public void setDureeMinutes(int duree_minute) {
		this.dureeMinutes = duree_minute;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}
}
