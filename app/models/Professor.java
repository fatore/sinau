package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Professor extends User {
	
	public Long departmentId;
	public boolean coordinator;
	
	@ManyToOne
	public Department department;
	
	@ManyToMany(cascade=CascadeType.PERSIST)
	public List<Subject> subjects;
	
	public Professor(Department department, String username, String password, String fullname,
			String email, String phone, boolean active, Long departmentId,
			boolean coordinator) {
		super(username, password, fullname, email, phone, active);
		this.departmentId = departmentId;
		this.coordinator = coordinator;
		this.department = department;
		this.subjects = new ArrayList<Subject>();
	}
}
