package media.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonSyntaxException;


import media.dao.DaoFactory;
import media.dao.ModelDao;


@WebServlet("/realisateur/delete")
public class DeleteRealisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String PARAM_ID_REALISATEUR="id";
	private ModelDao realisateurDao;
	
       
   
    public DeleteRealisateur() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
    public void init() throws ServletException{
    	this.realisateurDao=DaoFactory.getInstance().getDao("realisateur");
    }

	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		String idRealisateur=request.getParameter(PARAM_ID_REALISATEUR);
		
		int status = 200;
		String content = null;
		String erreur = "";
		
		try {
			Long id = Long.parseLong(idRealisateur);
			realisateurDao.delete(id);
			response.getWriter().write("ok");
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