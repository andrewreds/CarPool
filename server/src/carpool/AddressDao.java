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
		" SELECT id, suburb, eta_city, eta_home, contact_name, email, phone, rego FROM carpool_request; ";
	/**
	 * List all requests
	 * @return
	 */
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
				carpool.setSuburb(r.getString(2));
				carpool.setEtaCity(r.getString(3));
				carpool.setEtaHome(r.getString(4));
				carpool.setContactName(r.getString(5));
				carpool.setEmail(r.getString(6));
				carpool.setPhone(r.getString(7));
				carpool.setRego(r.getString(8));
				
				carpoolRequestList.add(carpool);
			}
			
		}catch(SQLException e){
			throw new RuntimeException(e);
		}finally{
			closeConenction(conn);
		}
		
		return carpoolRequestList;
	}
	
	
	private static final String SQL_SEARCH_BY_POSTCODE =
		" SELECT c.id, eta_city, eta_home, contact_name, email, phone, rego " +
		" FROM carpool_request c " +
		" INNER JOIN passing_suburb p ON p.request_id = c.id " +
		" WHERE p.suburb = ? ";
	public List<CarpoolRequest> searchByPostcode(String suburb){
		List<CarpoolRequest> carpoolRequestList = new ArrayList<CarpoolRequest>();
		
		Connection conn = null;
		try{
			conn = getConnection();
			PreparedStatement ps = conn.prepareStatement(SQL_SEARCH_BY_POSTCODE);
			ps.setString(1, suburb);
			
			ResultSet r = ps.executeQuery();
			while(r.next()){
				CarpoolRequest carpool = new CarpoolRequest();
				carpool.setId(r.getInt(1));
				carpool.setSuburb(r.getString(2));
				carpool.setEtaCity(r.getString(3));
				carpool.setEtaHome(r.getString(4));
				carpool.setContactName(r.getString(5));
				carpool.setEmail(r.getString(6));
				carpool.setPhone(r.getString(7));
				
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
		" INSERT INTO carpool_request (suburb, eta_city, eta_home, contact_name, email, phone, rego) VALUES (?, ?, ?, ?, ?, ?, ?); ";
	public void saveAddress(CarpoolRequest carpoolReq){
		Connection conn = null;
		try{
			conn = getConnection();
			PreparedStatement ps = conn.prepareStatement(SQL_SAVE_CARPOOL_REQUEST);
			ps.setString(1, carpoolReq.getSuburb());
			ps.setString(2, carpoolReq.getEtaCity());
			ps.setString(3, carpoolReq.getEtaHome());
			ps.setString(4, carpoolReq.getContactName());
			ps.setString(5, carpoolReq.getEmail());
			ps.setString(6, carpoolReq.getPhone());
			ps.setString(7, carpoolReq.getRego());
			
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
