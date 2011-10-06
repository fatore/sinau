package models;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="sinau_admin")
public class Admin extends User {
	
	public String role;

	public Admin(String username, String password, String fullname,
			String email, String phone, boolean active, String role) {
		super(username, password, fullname, email, phone, active);
		this.role = role;
	} 
}
