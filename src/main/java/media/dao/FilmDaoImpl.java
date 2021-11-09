package media.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import media.models.Film;
import media.models.Model;

public class FilmDaoImpl implements ModelDao {

	private static final String SQL_INSERT = "INSERT INTO film(id_realisateur,titre,description,duree_minute,genre) VALUES (?,?,?,?,?)";
	private static final String SQL_SELECT_BY_ID = "SELECT id,id_realisateur,titre,description,duree_minute,genre FROM film WHERE id = ?";
	private static final String SQL_SELECT = "SELECT id,id_realisateur,titre,description,duree_minute,genre FROM film";
	private static final String SQL_UPDATE = "UPDATE film SET id_realisateur = ?,titre = ?,description = ?,duree_minute = ?,genre = ? WHERE id = ?";
	private static final String SQL_DELETE_BY_ID = "DELETE FROM film WHERE id = ?";

	private DaoFactory daoFactory;

	public FilmDaoImpl(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	//create
	@Override
	public void create(Model m) throws DaoException{
		if(!(m instanceof Film)) {
			throw new DaoException("Error while creating new Film : wrong data input type");
		} 
		Film film = (Film)m;
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rsKeys = null; 
		try {
			con = daoFactory.getConnection();
			pst = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

			pst.setLong(	1, film.getIdRealisateur()	);
			pst.setString(	2, film.getTitre()			);
			pst.setString(	3, film.getDescription()	);
			pst.setInt(		4, film.getDureeMinute()	);
			pst.setString(	5, film.getGenre()			);

			int statut = pst.executeUpdate();
			if ( statut == 0 ) {
				throw new DaoException( "Error while creating new Film (no insertion)" );
			}

			rsKeys = pst.getGeneratedKeys();
			if ( rsKeys.next() ) {
				film.setId( rsKeys.getLong( 1 ) );
			} else {
				throw new DaoException( "Error while creating new Film (no ID returned)" );
			}
			rsKeys.close();

			pst.close();		
		}  catch(SQLException ex) {
			throw new DaoException("Error while creating new Film : ",ex);
		} finally {
			daoFactory.releaseConnection(con);
		}
	}

	//read
	@Override
	public Film find( long id ) throws DaoException{
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rsKeys = null;
		Film film =  null;
		try {
			con = daoFactory.getConnection();
			pst = con.prepareStatement(SQL_SELECT_BY_ID);
			
			rsKeys  = pst.executeQuery();
			if ( rsKeys.next() ) {
		    	  film = map(rsKeys);
		      }
			rsKeys.close();

			pst.close();
		}  catch(SQLException ex) {
			throw new DaoException("Error while reading Film with ID : "+id+" : ",ex);
		} finally {
			daoFactory.releaseConnection(con);
		}
		return film;
	}

	//readAll
	@Override
	public List<Film> findAll() throws DaoException{
		List<Film> listFilms = new ArrayList<Film>();
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rsKeys = null;
		try {
			con = daoFactory.getConnection();
			pst = con.prepareStatement(SQL_SELECT);

			rsKeys  = pst.executeQuery();
			while ( rsKeys.next() ) {
				listFilms.add( map(rsKeys) );
			}
			rsKeys.close();

			pst.close();
		}  catch(SQLException ex) {
			throw new DaoException("Error while reading Film BDD : ",ex);
		} finally {
			daoFactory.releaseConnection(con);
		}
		return listFilms;
	}

	//update
	@Override
	public void update(Model m) throws DaoException{
		if(!(m instanceof Film)) {
			throw new DaoException("Error while updating Film : wrong data input type");
		} 
		Film film = (Film)m;
		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = daoFactory.getConnection();
			pst = con.prepareStatement(SQL_UPDATE);

			pst.setLong(	1, film.getIdRealisateur()	);
			pst.setString(	2, film.getTitre()			);
			pst.setString(	3, film.getDescription()	);
			pst.setInt(		4, film.getDureeMinute()	);
			pst.setString(	5, film.getGenre()			);
			pst.setLong(	6, film.getId()	);

			int statut = pst.executeUpdate();
			if ( statut == 0 ) {
				throw new DaoException( "Error while updating Film with ID : "+film.getId()+" (no insertion)" );
			}

			pst.close();
		}  catch(SQLException ex) {
			throw new DaoException("Error while updating Film with ID : "+film.getId()+" : ",ex);
		} finally {
			daoFactory.releaseConnection(con);
		}
	}

	//delete
	@Override
	public void delete(long id) throws DaoException{
		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = daoFactory.getConnection();
			pst = con.prepareStatement(SQL_DELETE_BY_ID);

			pst.setLong(1, id);

			int statut = pst.executeUpdate();
			if ( statut == 0 ) {
				throw new DaoException( "Error while deleting Film with ID : "+id );
			}

			pst.close();
		}  catch(SQLException ex) {
			throw new DaoException("Error while deleting Film with ID : "+id+" : ",ex);
		} finally {
			daoFactory.releaseConnection(con);
		}
	}

	private static Film map( ResultSet rs ) throws SQLException{
		Film f = new Film();
		f.setId(rs.getLong("id"));
		f.setIdRealisateur(rs.getLong("id_realisateur"));
		f.setTitre(rs.getString("titre"));
		f.setDescription(rs.getString("description"));
		f.setDureeMinute(rs.getInt("duree_minute"));
		f.setGenre(rs.getString("genre"));
		return f;
	}
}
