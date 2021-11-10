package media.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import media.dao.DaoException;
import media.dao.DaoFactory;
import media.dao.ModelDao;
import media.models.Realisateur;
import media.servlets.processing.DataProcessing;

/**
 * Servlet implementation class CreateRealisateur
 */
@WebServlet(urlPatterns={"/realisateur/create", "/realisateur/new"})
public class CreateRealisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ModelDao realisateurDao;



	public CreateRealisateur() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init() throws ServletException{
		this.realisateurDao=DaoFactory.getInstance().getDao("realisateur");
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");

		int status=200;
		String content=null;
		String erreur="";

		try {

			JsonObject data = DataProcessing.getJsonFromRequest(request);


			String nom=data.get("nom").getAsString();
			String prenom=data.get("prenom").getAsString();
			Integer age=data.get("age").getAsInt();
			String pays=data.get("pays").getAsString();

			if(nom!=null) {
				if(nom.length()>100) {
					erreur="le nom du realisateur doit contenir  moins de 100 caracteres.";
				}
			}else {erreur="le nom du realisateur est obligatoire";}

			if(prenom!=null) {
				if(prenom.length()<2) {
					erreur="le prenom du realisateur doit contenir moins de 100 caracteres.";
				}
			}else {erreur="le prenom de l'auteur est obligatoire";}

			if(age==null) {

				erreur="l'age du realisateur est obligatoire";
			}

			if(pays.length()>200) {
				erreur="le nom du pays d'origine du realisateur doit contenir  moins de 200 caracteres.";
			}

			if(erreur.equals("")) {
				Realisateur realisateur= new Realisateur(nom, prenom, age, pays);
				realisateurDao.create(realisateur);
				response.getWriter().write("ok");}

			else {
				response.setStatus(400);
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write("Erreur : "+erreur);
			}
		}catch (DaoException e) {
			e.printStackTrace();
			response.setStatus(500);
			response.getWriter().write("erreur DAO");
		}catch (JsonSyntaxException e) {
			e.printStackTrace();
			erreur += "Wrong JSON format";
			status = 400;
		}  catch (Exception e) {
			e.printStackTrace();
			erreur += "Unknown server error\n";
			status = 400;
		} finally {
			if(!erreur.equals("")) {
				content = "Erreur : \n"+erreur;
			}
			response.setStatus(status);
			response.getWriter().write(content);

		}

	}
}