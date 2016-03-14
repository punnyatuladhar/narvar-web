package avecode.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import avecode.model.ui.CustomerRequest;
import avecode.persistence.IDbManager;

public class CustomerManager implements ICustomerManager {
	@Autowired
	private IDbManager dbMgr;
	
	@Override
	public String add(CustomerRequest customer) {
		PreparedStatement stmt = null;
		Connection con = null;
		String res;
		try {
			String qry = "insert into CUSTOMERS(name, street, city, zip) values (?, ?, ?, ?)";
			con = dbMgr.getConnection();
			stmt = con.prepareStatement(qry);
			stmt.setString(1, customer.name);
			stmt.setString(2, customer.street);
			stmt.setString(3, customer.city);
			stmt.setInt(4, customer.zip);
			stmt.executeUpdate();
			con.commit();
			res = "OK";
			System.out.println("inserted - " + customer.name + customer.street + customer.zip);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res = e.getMessage();
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
		return res;
	}
	
	@Override
	public List<CustomerRequest> get() {
		PreparedStatement stmt = null;
		Connection con = null;
		List<CustomerRequest> custs = new ArrayList<CustomerRequest>();
		
		try {
			String qry = "select * from customers";
			con = dbMgr.getConnection();
			stmt = con.prepareStatement(qry);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				CustomerRequest req = new CustomerRequest();
				req.name = rs.getString("name");
				req.street = rs.getString("street");
				req.city = rs.getString("city");
				req.zip = rs.getInt("zip");
				
				custs.add(req);
			}
			
			return custs;
			
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
		
		return null;
	}
	
	@Override
	public String update(String name, CustomerRequest req) {
		PreparedStatement stmt = null;
		Connection con = null;
		String res;
		
		try {
			String qry = "update customers set street = ?, city = ?, zip = ? where name = ?";
			con = dbMgr.getConnection();
			stmt = con.prepareStatement(qry);
			stmt.setString(1, req.street);
			stmt.setString(2, req.city);
			stmt.setInt(3, req.zip);
			stmt.setString(4, name);
			stmt.executeUpdate();
			con.commit();
			
			res = "OK";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res = e.getMessage();
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
		
		return res;
	}
	
	@Override
	public String delete(String name) {
		PreparedStatement stmt = null;
		Connection con = null;
		String res;
		
		try {
			String qry = "delete from customers where name = ?";
			con = dbMgr.getConnection();
			stmt = con.prepareStatement(qry);
			stmt.setString(1, name);
			stmt.executeUpdate();
			con.commit();
			
			res = "OK";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res = e.getMessage();
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
		
		return res;
	}
}
