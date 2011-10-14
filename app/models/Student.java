package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity
public class Student extends User {
	
	public Long regNumber;
	public String address;
	public Integer semester;
	public Date beganAt;
	public boolean scholar;
	
	@ManyToMany(cascade=CascadeType.PERSIST)
	public List<Subject> subjects;
	
	public Student(String username, String password, String fullname,
			String email, String phone, boolean active, Long regNumber,
			String address, Integer semester, Date beganAt) {
		super(username, password, fullname, email, phone, active);
		this.regNumber = regNumber;
		this.address = address;
		this.semester = semester;
		this.beganAt = beganAt;
		this.subjects = new ArrayList<Subject>();
	}

	@Override
	public String toString() {
		return fullname;
	}
}
