package avecode.persistence;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbManager implements IDbManager {
	private Connection con;
	
	@Override
	public Connection getConnection() {
		if (null == con) {
			synchronized (this) {
				if (null == con) {
					try {
					       Class.forName("org.postgresql.Driver");
					       con = DriverManager
					          .getConnection("jdbc:postgresql://localhost:5432/NarvarDB", //local NarvarDB
					          "postgres", "postgres");  
					       con.setAutoCommit(false);
					       System.out.println("Opened database successfully"); 
					    } catch (Exception e) {
					       e.printStackTrace();
				    }
				
				}
			}
		}
		return con;
	}
}