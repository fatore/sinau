package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class University extends Model {

	public String name;
	public String city;
	public String address;
	public String zipCode;
	
	@OneToMany(mappedBy="university", cascade=CascadeType.ALL)
	public List<Department> departments;
	
	public University(String name, String city, String address, String zipCode) {
		this.name = name;
		this.city = city;
		this.address = address;
		this.zipCode = zipCode;
		this.departments = new ArrayList<Department>();
	}
	
	public University addDepartment(String name, String phone) {
		Department newDept = new Department(this, name, phone).save();
		this.departments.add(newDept);
		this.save();
		return this;
	}
}
