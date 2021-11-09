package media.dao;

import java.util.List;

import media.models.Film;
import media.models.Model;

public class FilmDaoImpl implements ModelDao {
	//create
	@Override
	public void create(Model m) throws DaoException{
		if(!(m instanceof Film)) {
			//erreur
		} else {
			
		}
	}
	
	//read
	@Override
	public Film find( long id ) throws DaoException{
		return null;
	}
	
	//readAll
	@Override
	public List<Film> findAll() throws DaoException{
		return null;
	}
	
	//update
	@Override
	public void update(Model m) throws DaoException{
		if(!(m instanceof Film)) {
			//erreur
		} else {
			
		}
	}
	
	//delete
	@Override
	public void delete(long id) throws DaoException{
		
	}
}
