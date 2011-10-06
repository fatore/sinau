import java.util.Date;

import models.Course;
import models.Department;
import models.Professor;
import models.Student;
import models.Subject;
import models.University;

import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;


public class RelationsTests extends UnitTest {

	@Test
	public void testUnivDeptProf() {	
		Fixtures.deleteDatabase();
		
		University univ = new University("USP", "Sao Carlos", "Centro", "13560-800").save();
		assertNotNull(univ);
		
		univ.addDepartment("SCC", "PHONE");
		univ.addDepartment("SSC", "PHONE");

		assertEquals(1, University.count());
		assertEquals(2, Department.count());

		Department dp = Department.find("byUniversity", univ).first();
		assertEquals("USP", dp.university.name);
		
		dp.addProfessor("bob", "secret", "Bob the Man", 
				"bob@gmail.com", "(099)-9999-9999", true, new Long(1), true);
		
		assertEquals(1, Professor.count());
		
		univ.delete();
		
		assertEquals(0, Professor.count());
		assertEquals(0, University.count());
		assertEquals(0, Department.count());
	}
	
	@Test
	public void testDepCourseSubj() {
		Fixtures.deleteDatabase();
		
		University univ = new University("USP", "Sao Carlos", "Centro", "13560-800").save();
		assertNotNull(univ);
		
		univ.addDepartment("SCC", "PHONE");
		univ.addDepartment("SSC", "PHONE");

		assertEquals(1, University.count());
		assertEquals(2, Department.count());

		Department dp = Department.find("byUniversity", univ).first();
		assertEquals("USP", dp.university.name);
		
		dp.addProfessor("bob", "secret", "Bob the Man", 
				"bob@gmail.com", "(099)-9999-9999", true, new Long(1), true);
		
		new Student("bob", "secret", "Bob the Man", 
				"bob@gmail.com", "(099)-9999-9999", true, new Long(6426826), 
				"address", 1, new Date()).create();
		
		
		assertEquals(1, Professor.count());
		
		dp.addCourse("BCC", "Computing", 100);
		dp.addCourse("EC", "Computing", 50);
		dp.addCourse("Info", "Computing", 50);
		
		Course course = Course.find("byName", "BCC").first();
		assertNotNull(course);
		assertEquals(new Integer(100), course.availableSeats);
		
		assertEquals(3, Course.count());
		
		course.addSubject("cg", "make games", 100, new Date(), 100);
		
		assertEquals(1, Subject.count());
		
		univ.delete();
		
		assertEquals(0, University.count());
		assertEquals(0, Department.count());
		assertEquals(0, Professor.count());
		assertEquals(0, Subject.count());
		assertEquals(1, Student.count());
	}
	
	@Test
	public void testStudentProfSubj() {
		Fixtures.deleteDatabase();
		
		University univ = new University("USP", "Sao Carlos", "Centro", "13560-800").save();
		assertNotNull(univ);
		
		univ.addDepartment("SCC", "PHONE");
		univ.addDepartment("SSC", "PHONE");

		assertEquals(1, University.count());
		assertEquals(2, Department.count());

		Department dp = Department.find("byUniversity", univ).first();
		assertEquals("USP", dp.university.name);
		
		dp.addProfessor("bob", "secret", "Bob the Man", 
				"bob@gmail.com", "(099)-9999-9999", true, new Long(1), true);
		
		Student student = new Student("bob", "secret", "Bob the Man", 
				"bob@gmail.com", "(099)-9999-9999", true, new Long(6426826), 
				"address", 1, new Date()).save();
		
		
		assertEquals(1, Professor.count());
		
		dp.addCourse("BCC", "Computing", 100);
		dp.addCourse("EC", "Computing", 50);
		dp.addCourse("Info", "Computing", 50);
		
		Course course = Course.find("byName", "BCC").first();
		assertNotNull(course);
		assertEquals(new Integer(100), course.availableSeats);
		
		assertEquals(3, Course.count());
		
		course.addSubject("cg", "make games", 100, new Date(), 100);
		
		assertEquals(1, Subject.count());
		
		Subject subject = Subject.find("byName", "cg").first();
		subject.linkUser(student.id);
		
		Student student2 = Student.findById(student.id);
		
		assertNotNull(student2);
		assertEquals(1, student2.subjects.size());
	}
}















