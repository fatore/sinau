package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.w3c.dom.Element;

import play.db.jpa.Model;

@Entity
public class Department extends Model {

	public String name;
	public String phone;
	
	@ManyToOne
	public University university;
	
	@OneToMany(mappedBy="department", cascade=CascadeType.PERSIST)
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
	
	@Override
	public String toString() {
		return name;
	}
	
	public Department addProfessor(Professor professor) {	
		professor.department = this;
		professor.save();
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
