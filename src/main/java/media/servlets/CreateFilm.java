package media.servlets;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import media.servlets.processing.DataProcessing;
import media.dao.DaoException;
import media.dao.DaoFactory;
import media.dao.ModelDao;
import media.models.Film;

/**
 * Servlet implementation class CreateFilm
 */
@WebServlet(urlPatterns={"/film/create", "/film/new"})
public class CreateFilm extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ModelDao filmDao;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateFilm() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init() throws ServletException{
		this.filmDao = DaoFactory.getInstance().getDao("film");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");

		int status = 200;
		String content = null;
		String error = "";

		try {

			Long idRealisateur = null; //NOT NULL car clee etrangere
			String titre = null; //Max 100 char + NOT NULL
			String description = null; //Max 500 char
			Integer dureeMinutes = null; // NOT NULL
			String genre = null; //Max 20 char
			
			JsonObject data = DataProcessing.getJsonFromRequest(request);
			
			HashMap<String,String> hmIdRealisateur = DataProcessing.checkParameter("id_realisateur", data, true, true);
			//NOT NULL donc isRequired = true et isNotNullable = true
			if(hmIdRealisateur.get("error").equals("")) {
				String idRealisateurAsString = hmIdRealisateur.get("parameter");
				if(!idRealisateurAsString.matches( "^\\d+$" )) {
					error += "The parameter "+"id_realisateur"+"must contain ONLY numbers\n";
				} else if(idRealisateurAsString != null) {
					idRealisateur = Long.valueOf(idRealisateurAsString);
				}
			} else {
				error += hmIdRealisateur.get("error");
			}
			
			HashMap<String,String> hmTitre = DataProcessing.checkParameter("titre", data, true, true);
			//NOT NULL donc isRequired = true et isNotNullable = true
			if(hmTitre.get("error").equals("")) {
				titre = hmTitre.get("parameter");
				if(titre.length() < 2) {
					error += "The parameter "+"titre"+" is too short (2 char. min)\n";
				} else if (titre.length() > 100) {
					error += "The parameter "+"titre"+" is too long (100 char. max)\n";
				}
			} else {
				error += hmTitre.get("error");
			}
			
			HashMap<String,String> hmDescription = DataProcessing.checkParameter("description", data, false, false);
			//Peut valoir NULL
			if(hmDescription.get("error").equals("")) {
				description = hmDescription.get("parameter");
				if(description != null && description.length() < 2) {
					error += "The parameter "+"description"+" is too short (2 char. min)\n";
				} else if (description != null && description.length() > 500) {
					error += "The parameter "+"description"+" is too long (500 char. max)\n";
				}
			} else {
				error += hmDescription.get("error");
			}
			
			HashMap<String,String> hmDureeMinutes = DataProcessing.checkParameter("duree_minutes", data, true, true);
			//NOT NULL donc isRequired = true et isNotNullable = true
			if(hmDureeMinutes.get("error").equals("")) {
				String dureeAsString = hmDureeMinutes.get("parameter");
				if(!dureeAsString.matches( "^\\d+$" )) {
					error += "The parameter "+"duree_minutes"+"must contain ONLY numbers\n";
				} else {
					dureeMinutes = Integer.valueOf(dureeAsString);
				}
			} else {
				error += hmDureeMinutes.get("error");
			}
			
			HashMap<String,String> hmGenre = DataProcessing.checkParameter("genre", data, false, false);
			//Peut valoir NULL
			if(hmGenre.get("error").equals("")) {
				genre = hmGenre.get("parameter");
				if(genre != null && genre.length() < 2) {
					error += "The parameter "+"genre"+" is too short (2 char. min)\n";
				} else if (genre != null && genre.length() > 20) {
					error += "The parameter "+"genre"+" is too long (20 char. max)\n";
				}
			} else {
				error += hmGenre.get("error");
			}
			
			if(error.equals("")) {
				Film film = new Film (idRealisateur, titre, description, dureeMinutes, genre);
				filmDao.create(film);
				String json = new Gson().toJson(film);
				response.setContentType("application/json");
				content = json.toString();
			} else {
				status = 400;
			}

		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			error += "Wrong JSON format";
			status = 400;
		} catch (DaoException e) { 
			e.printStackTrace(); 
			error += " 500 - "+e.getMessage(); 
			status = 500; 
		} catch (Exception e) {
			e.printStackTrace();
			error += "Unknown server error\n";
			status = 400;
		} finally {
			if(!error.equals("")) {
				content = "Error : \n"+error;
			}
			response.setStatus(status);
			response.getWriter().write(content);
		}
	}

}
