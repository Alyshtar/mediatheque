package media.dao;

import java.util.List;

import media.models.Realisateur;
import media.models.Model;

public class RealisateurDaoImpl implements ModelDao {
	//create
	@Override
	public void create(Model m) throws DaoException{
		if(!(m instanceof Realisateur)) {
			//erreur
		} else {
			
		}
	}
	
	//read
	@Override
	public Realisateur find( long id ) throws DaoException{
		return null;
	}
	
	//readAll
	@Override
	public List<Realisateur> findAll() throws DaoException{
		return null;
	}
	
	//update
	@Override
	public void update(Model m) throws DaoException{
		if(!(m instanceof Realisateur)) {
			//erreur
		} else {
			
		}
	}
	
	//delete
	@Override
	public void delete(long id) throws DaoException{
		
	}
}
