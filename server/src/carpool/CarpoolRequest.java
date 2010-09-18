package carpool;

public class CarpoolRequest {
	private int id;
	private String suburb;
	private String etaCity;
	private String etaHome;
	private String contactName;
	private String email;
	private String phone;
	private String rego;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSuburb() {
		return suburb;
	}
	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEtaCity() {
		return etaCity;
	}
	public void setEtaCity(String etaCity) {
		this.etaCity = etaCity;
	}
	public String getEtaHome() {
		return etaHome;
	}
	public void setEtaHome(String etaHome) {
		this.etaHome = etaHome;
	}
	public String getRego() {
		return rego;
	}
	public void setRego(String rego) {
		this.rego = rego;
	}
}
