package models;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import play.db.jpa.Model;

@Entity
@Table(name="sinau_user")
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class User extends Model {
	
	public String username;
	public String password;
	public String fullname;
	public String email;
	public String phone;
	public boolean active;
	
	public User(String username, String password, String fullname, String email,
			String phone, boolean active) {
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.email = email;
		this.phone = phone;
		this.active = active;
	}
	
	public static User connect(String username, String password) {
		return find("byUsernameAndPassword", username, password).first();
	}
}
