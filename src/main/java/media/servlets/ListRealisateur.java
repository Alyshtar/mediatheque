package media.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import media.dao.DaoException;
import media.dao.DaoFactory;
import media.dao.ModelDao;

@WebServlet(urlPatterns={"/realisateur/list", "/realisateurs"})
public class ListRealisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ModelDao realisateurDao;
   
	
    public ListRealisateur() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init() throws ServletException{
    	this.realisateurDao = DaoFactory.getInstance().getDao("realisateur");
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		
		int status = 200;
		String content = null;
		String erreur = "";
		
		try {
			
			String json = new Gson().toJson(realisateurDao.findAll());
			response.setContentType("application/json");
			response.getWriter().write(json.toString());
			
		} catch (DaoException e) {
			e.printStackTrace();
			erreur += " 500 - "+e.getMessage();
			status = 500;
		} catch (Exception e) {
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
