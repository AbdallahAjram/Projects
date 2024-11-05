package model;

import java.sql.SQLException;

public class admin {
	private String admin_name;
	private String password;
	private String phone;
	public admin(String admin_name, String password, String phone) {
		this.admin_name = admin_name;
		this.password = password;
		this.phone = phone;
	}
	public int getAdmin_id() {
		DatabaseHelper dh = new DatabaseHelper();
		try {
			return dh.getAdminIdByPhone(phone);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return -1;
	}
	public String getAdmin_name() {
		return admin_name;
	}
	public void setAdmin_name(String admin_name) {
		this.admin_name = admin_name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	 
	
}
