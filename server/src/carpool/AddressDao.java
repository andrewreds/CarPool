package carpool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddressDao {
	private String jdbcUrl;
	private String jdbcUsername;
	private String jdbcPassword;
	
	public AddressDao(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ResourceBundle resourceBundle = ResourceBundle.getBundle("jdbc");
		jdbcUrl = resourceBundle.getString("jdbc_url");
		jdbcUsername = resourceBundle.getString("jdbc_username");
		jdbcPassword = resourceBundle.getString("jdbc_password");
	}
	
	
	private static final String SQL_LIST_CARPOOL_REQUEST =
		" SELECT id, from_address, to_address, contact_name, email, phone FROM carpool_request; ";
	public List<CarpoolRequest> listCarpoolRequests(){
		List<CarpoolRequest> carpoolRequestList = new ArrayList<CarpoolRequest>();
		
		Connection conn = null;
		try{
			conn = getConnection();
			PreparedStatement ps = conn.prepareStatement(SQL_LIST_CARPOOL_REQUEST);			
			ResultSet r = ps.executeQuery();
			while(r.next()){
				CarpoolRequest carpool = new CarpoolRequest();
				carpool.setId(r.getInt(1));
				carpool.setFrom(r.getString(2));
				carpool.setTo(r.getString(3));
				carpool.setContactName(r.getString(4));
				carpool.setEmail(r.getString(5));
				carpool.setPhone(r.getString(6));
				
				carpoolRequestList.add(carpool);
			}
			
		}catch(SQLException e){
			throw new RuntimeException(e);
		}finally{
			closeConenction(conn);
		}
		
		return carpoolRequestList;
	}
	
	
	private static final String SQL_SAVE_CARPOOL_REQUEST =
		" INSERT INTO carpool_request (from_address, to_address, contact_name, email, phone) VALUES (?, ?, ?, ?, ?); ";
	public void saveAddress(CarpoolRequest carpoolReq){
		Connection conn = null;
		try{
			conn = getConnection();
			PreparedStatement ps = conn.prepareStatement(SQL_SAVE_CARPOOL_REQUEST);
			ps.setString(1, carpoolReq.getFrom());
			ps.setString(2, carpoolReq.getTo());
			ps.setString(3, carpoolReq.getContactName());
			ps.setString(4, carpoolReq.getPhone());
			ps.setString(5, carpoolReq.getEmail());
			
			int id = ps.executeUpdate();
			System.out.println("carpool request saved: " + id);
		}catch(SQLException e){
			throw new RuntimeException(e);
		}finally{
			closeConenction(conn);
		}

	}
	
	private Connection getConnection(){
		try {
			Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
			return conn;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("cannot get connection", e);
		}
	}
	
	private void closeConenction(Connection conn){
		try {
			if(conn != null){
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("cannot close connection", e);
		}
	}
}
