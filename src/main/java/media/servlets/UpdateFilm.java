package media.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonSyntaxException;

import media.dao.DaoException;
import media.dao.DaoFactory;
import media.dao.ModelDao;

/**
 * Servlet implementation class UpdateFilm
 */
@WebServlet("/film/update")
public class UpdateFilm extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ModelDao filmDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateFilm() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init() throws ServletException{
    	this.filmDao = DaoFactory.getInstance().getDao("film");
    }

	/**
	 * @see HttpServlet#doPut(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");

		int status = 200;
		String content = null;
		String erreur = "";
		
		try {
			
			
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			erreur += "Wrong JSON format";
			status = 400;
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
