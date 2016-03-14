import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Test {
	public static void main(String args[]) {
	      Connection c = null;
	      try {
	         Class.forName("org.postgresql.Driver");
	         c = DriverManager
	            .getConnection("jdbc:postgresql://localhost:5432/NarvarDB",
	            "postgres", "postgres");
	         
	         PreparedStatement stmt = null;
	 		try {
	 			String qry = "insert into CUSTOMERS(name, street, city, zip) values (?, ?, ?, ?)";
	 			stmt = c.prepareStatement(qry);
	 			stmt.setString(1, "punnya");
	 			stmt.setString(2, "lincoln");
	 			stmt.setString(3, "alameda");
	 			stmt.setInt(4, 94601);
	 			stmt.executeUpdate();
	 		} catch (SQLException e) {
	 			// TODO Auto-generated catch block
	 			e.printStackTrace();
	 		} finally {
	 			if (null != stmt) {
	 				try {
	 					stmt.close();
	 				} catch (SQLException e) {
	 					// TODO Auto-generated catch block
	 					e.printStackTrace();
	 				}
	 			}
	 		}

	      } catch (Exception e) {
	         e.printStackTrace();
	         System.err.println(e.getClass().getName()+": "+e.getMessage());
	         System.exit(0);
	      }
	      System.out.println("Opened database successfully");
	   }
}
