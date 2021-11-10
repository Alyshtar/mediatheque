package media.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import media.models.Realisateur;
import media.models.Model;

public class RealisateurDaoImpl implements ModelDao {

	private static final String SQL_INSERT       = "INSERT INTO realisateur(nom,prenom,age, pays) VALUES(?,?,?,?)";
	private static final String SQL_SELECT       = "SELECT id,nom,prenom,age, pays FROM realisateur";
	private static final String SQL_SELECT_BY_ID = "SELECT id,nom,prenom,age, pays FROM realisateur WHERE id = ?";
	private static final String SQL_DELETE_BY_ID = "DELETE FROM realisateur WHERE id = ? ";
	private static final String SQL_UPDATE_BY_ID = "UPDATE realisateur SET  nom=?, prenom=?, age=?, pays=? WHERE id = ? ";

	private DaoFactory factory;

	public RealisateurDaoImpl(DaoFactory factory) {
		this.factory = factory;	
	}


	//create
	@Override
	public void create(Model m) throws DaoException{
		if(!(m instanceof Realisateur)) {
			throw new DaoException("Error, your parameter is not a realisateur");
		} 

		// Creation de l'objet realisateur (� partir du cast de m en Realisateur) + creation de l'objet con de type Connection
		Realisateur realisateur=(Realisateur)m;	
		Connection con=null;

		try {
			con = factory.getConnection();

			//
			PreparedStatement pst = con.prepareStatement( SQL_INSERT, Statement.RETURN_GENERATED_KEYS );

			// Remplir l'objet realisateur 
			pst.setString(1, realisateur.getNom());
			pst.setString(2, realisateur.getPrenom());
			pst.setInt(3, realisateur.getAge());
			pst.setString(4, realisateur.getPays());

			int statut = pst.executeUpdate();

			//
			if ( statut == 0 ) {
				throw new DaoException( "Failed to create the Realisateur (no addition)" );
			}

			//
			ResultSet rsKeys = pst.getGeneratedKeys();
			if ( rsKeys.next() ) {
				realisateur.setId( rsKeys.getLong( 1 ) );
			} else {
				throw new DaoException( "Failed to create Realisateur (ID not returned)" );
			}
			rsKeys.close();
			pst.close();

		} catch (SQLException e) {
			throw new DaoException("Failed to create Realisateur",e);
		} finally {
			factory.releaseConnection(con);
		}

	}

	//read
	@Override
	public Realisateur find( long id ) throws DaoException{
		Realisateur 		realisateur=null;
		Connection			con=null;
		PreparedStatement 	pst= null;
		ResultSet 			rs=null;

		try {
			con = factory.getConnection();
			pst = con.prepareStatement( SQL_SELECT_BY_ID );
			pst.setLong(1, id);
			rs  = pst.executeQuery();
			if ( rs.next() ) {
				realisateur = map(rs); 									
			}
			rs.close();
			pst.close();
		}catch(SQLException ex) {
			throw new DaoException("Realisateur database search error", ex);
		} finally {
			factory.releaseConnection(con);
		}
		return realisateur;

	}

	//readAll
	@Override
	public List<Realisateur> findAll() throws DaoException{
		List<Realisateur> listeRealisateur = new ArrayList<>();
		Connection   con=null;
		try {
			con = factory.getConnection();
			PreparedStatement pst = con.prepareStatement( SQL_SELECT );
			ResultSet         rs  = pst.executeQuery();
			while ( rs.next() ) {
				listeRealisateur.add( map(rs) ); 									
			}
			rs.close();
			pst.close();
		} catch(SQLException ex) {
			throw new DaoException("Realisateur BDD read error", ex);
		} finally {
			factory.releaseConnection(con);
		}
		return listeRealisateur;
	}

	//update
	@Override
	public void update(Model m) throws DaoException{
		if(!(m instanceof Realisateur)) {
			throw new DaoException("Error, your parameter is not a realisateur");
		} 
		Realisateur realisateur=(Realisateur)m;	
		Connection con=null;

		try {
			con = factory.getConnection();

			//
			PreparedStatement pst = con.prepareStatement( SQL_UPDATE_BY_ID);

			// 
			pst.setString(1, realisateur.getNom());
			pst.setString(2, realisateur.getPrenom());
			pst.setInt(3, realisateur.getAge());
			pst.setString(4, realisateur.getPays());
			pst.setLong(5, realisateur.getId());

			int statut = pst.executeUpdate();

			//
			if ( statut == 0 ) {
				throw new DaoException( "Failed to update the Realisateur (" +realisateur.getId()+ ")" );
			}
			pst.close();

		}catch(SQLException ex) {
			throw new DaoException("Failed to update the Realisateur", ex);
		} finally {
			factory.releaseConnection(con);
		}
	}

	//delete
	@Override
	public void delete(long id) throws DaoException{
		Connection   con=null;
		try {
			con = factory.getConnection();
			PreparedStatement pst = con.prepareStatement( SQL_DELETE_BY_ID );
			pst.setLong(1, id);
			int statut = pst.executeUpdate();
			if ( statut == 0 ) {
				throw new DaoException("Failed to delete the Realisateur("+id+")");
			}
			pst.close();
		} catch(SQLException ex) {
			throw new DaoException("Failed to delete the Realisateur", ex);
		} finally {
			factory.releaseConnection(con);
		}

	}

	private static Realisateur map( ResultSet resultSet ) throws SQLException {
		Realisateur realisateur = new Realisateur();
		realisateur.setId(resultSet.getLong("id"));
		realisateur.setNom(resultSet.getString("nom"));
		realisateur.setPrenom(resultSet.getString("prenom"));
		realisateur.setAge(resultSet.getInt("age"));
		realisateur.setPays(resultSet.getString("pays"));
		return realisateur;
	}



}
