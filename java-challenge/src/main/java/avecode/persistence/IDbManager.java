package avecode.persistence;

import java.sql.Connection;

public interface IDbManager {
	public Connection getConnection();
}
