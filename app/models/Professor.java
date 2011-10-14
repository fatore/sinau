package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Professor extends User {
	
	public boolean coordinator;
	
	@ManyToOne
	public Department department;
	
	@ManyToMany(cascade=CascadeType.PERSIST)
	public List<Subject> subjects;
	
	public Professor(String username, String password, String fullname,
			String email, String phone, boolean active,	boolean coordinator) {
		super(username, password, fullname, email, phone, active);
		this.coordinator = coordinator;
		this.subjects = new ArrayList<Subject>();
	}
	
	@Override
	public String toString() {
		return fullname;
	}
}
