package media.dao;

import java.util.List;

import media.dao.DaoException;
import media.models.Model;

public interface ModelDao {
	//create
	void  create(Model m) throws DaoException;
	
	//read
	Model find( long id ) throws DaoException;
	
	//readAll
	List<? extends Model>  findAll() throws DaoException;
	
	//update
	void update(Model m) throws DaoException;
	
	//delete
	void delete(long id) throws DaoException;
}
