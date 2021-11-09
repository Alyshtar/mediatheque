package media.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DaoFactory {
	private String url;
	private String username;
	private String passwd;
	private Connection con = null;

	private static DaoFactory instanceSingleton = null;
	
	private DaoFactory(String url, String username, String passwd) {
		this.url = url;
		this.username = username;
		this.passwd = passwd;
	}
	
	public static DaoFactory getInstance() {
		if ( DaoFactory.instanceSingleton == null ) {
			try {
				ClassLoader cl = Thread.currentThread().getContextClassLoader();
				InputStream propertiesFile = cl.getResourceAsStream("/media/config/config.properties");
				Properties properties = new Properties();
				properties.load(propertiesFile);

				String db_driver = properties.getProperty("db_driver");
				Class.forName(db_driver);

				String db_host = properties.getProperty("db_host");
				String db_name = properties.getProperty("db_name");
				String db_user = properties.getProperty("db_user");
				String db_pass = properties.getProperty("db_pass");

				DaoFactory.instanceSingleton = new DaoFactory("jdbc:postgresql://"+db_host+"/"+db_name,db_user,db_pass);
			} catch(ClassNotFoundException e) {
				e.printStackTrace();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		return DaoFactory.instanceSingleton;
	}
	
	Connection getConnection() throws SQLException {
		if ( this.con == null ) {
			this.con = DriverManager.getConnection(url,username,passwd);
		}
		return this.con;
	}
	
	void releaseConnection( Connection connectionRendue ) {
		if (this.con==null) {
			return;
		}
		try {
			if ( ! this.con.isValid(10) ) {
				this.con.close();
				this.con = null;
			}
		} catch (SQLException e) {
			con = null;
		}
	}
	
	public ModelDao getDao(String nom_table) {
		if(nom_table.equals("film")) {
			return new FilmDaoImpl(this);
		} else if (nom_table.equals("realisateur")) {
			return new RealisateurDaoImpl(this);
		}
		return null;
	}
}
