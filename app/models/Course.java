package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Course extends Model {
	
	public String name;
	public String area;
	public Integer availableSeats;
	
	@ManyToOne
	public Department department;
	
	@OneToMany(mappedBy="course", cascade=CascadeType.ALL)
	public List<Subject> subjects;

	public Course(Department department, String name, String area,
			Integer availableSeats) {
		this.department = department;
		this.name = name;
		this.area = area;
		this.availableSeats = availableSeats;
		this.subjects = new ArrayList<Subject>();
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public Course addSubject(String name, String description,
			Integer workload, Date schedule, Integer room) {
		Subject subject = new Subject(this, name, description, workload, schedule, room);
		this.subjects.add(subject);
		this.save();
		return this;
	}
}
