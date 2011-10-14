package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class Subject extends Model {
	
	public String name;
	
	@Lob
	public String description;  
	public Integer workload;
	public Date schedule;
	public Integer room;
	
	@ManyToOne
	public Course course;
	
	@ManyToMany
	public List<User> users;

	public Subject(Course course, String name, String description,
			Integer workload, Date schedule, Integer room) {
		super();
		this.course = course;
		this.name = name;
		this.description = description;
		this.workload = workload;
		this.schedule = schedule;
		this.room = room;
		this.users = new ArrayList<User>();
	}
	
	public Subject linkUser(Long userId) {
		User user = User.findById(userId);
		try {
			if (user.getClass().equals(Class.forName("models.Student"))) {
				Student student = (Student) user;
				student.subjects.add(this);
				student.save();
			} else if (user.getClass().equals(Class.forName("models.Professor"))) {
				Professor professor = (Professor) user;
				professor.subjects.add(this);
				professor.save();
			} else {
				return this;
			}
			this.users.add(user);
			this.save();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
