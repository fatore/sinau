package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Department extends Model {

	public String name;
	public String phone;
	
	@ManyToOne
	public University university;
	
	@OneToMany(mappedBy="department", cascade=CascadeType.ALL)
	public List<Professor> professors;
	
	@OneToMany(mappedBy="department", cascade=CascadeType.ALL)
	public List<Course> courses;
	
	public Department(University university, String name, String phone) {
		this.name = name;
		this.phone = phone;
		this.university = university;
		this.professors = new ArrayList<Professor>();
		this.courses = new ArrayList<Course>();
	}
	
	public Department addProfessor(String username, String password, String fullname,
			String email, String phone, boolean active, Long departmentId,
			boolean coordinator) {
		Professor professor = new Professor(this, username, password, fullname, email,
				phone, active, departmentId, coordinator).save();
		this.professors.add(professor);
		this.save();
		return this;
	}
	
	public Department addCourse(String name, String area,
			Integer availableSeats) {
		Course course = new Course(this, name, area, availableSeats);
		this.courses.add(course);
		this.save();
		return this;
	}
}
