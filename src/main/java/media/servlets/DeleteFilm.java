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
 * Servlet implementation class DeleteFilm
 */
@WebServlet("/film/delete")
public class DeleteFilm extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ModelDao filmDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteFilm() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init() throws ServletException{
    	this.filmDao = DaoFactory.getInstance().getDao("film");
    }

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");

		int status = 200;
		String content = null;
		String error = "";
		
		try {
			
			Long id_film = null;
			
			if(request.getParameter("id_film") != null) {
				id_film = Long.valueOf(request.getParameter("id_film"));
			} else {
				JsonObject data = DataProcessing.getJsonFromRequest(request);

				HashMap<String,String> hmIdFilm = DataProcessing.checkParameter("id_film", data, true, true);	

				if(hmIdFilm.get("error").equals("")) {
					String id_filmAsString = hmIdFilm.get("parameter");
					id_film = Long.valueOf(id_filmAsString);
				}else{
					error += hmIdFilm.get("error");
				}
			}
			
			if(error.equals("")) {
				Film film = (Film) filmDao.find(id_film);
				filmDao.delete(id_film);
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
