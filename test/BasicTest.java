
import models.Admin;
import models.Department;
import models.Professor;
import models.Student;
import models.University;
import models.User;

import org.junit.*;

import java.util.*;
import play.test.*;

public class BasicTest extends UnitTest {


	@Test
	public void createAndRetrieveAdmin() {
		Fixtures.deleteDatabase();
	    // Create a new user and save it
		new Admin("bob", "secret", "Bob the Man", "bob@gmail.com", 
				"(099)-9999-9999", true, "Web Master").save();
	    
	    // Retrieve the user with e-mail address bob@gmail.com
	    User bob = User.find("byEmail", "bob@gmail.com").first();
	    
	    // Test 
	    assertNotNull(bob);
	    assertEquals("Bob the Man", bob.fullname);
	    
	    Admin _bob = (Admin) bob;
	    assertNotNull(_bob);
	    assertEquals("Web Master", _bob.role);
	    
	    Admin __bob = Admin.find("byUsername", "bob").first();
	    assertNotNull(__bob);
	    assertEquals("Web Master", __bob.role);
	}
	
	@Test
	public void createAndRetrieveProfessor() {	
		Fixtures.deleteDatabase();
	    // Create a new user and save it
		new Professor(null, "bob", "secret", "Bob the Man", 
				"bob@gmail.com", "(099)-9999-9999", true, new Long(1), true).create();
	    
	    // Retrieve the user with e-mail address bob@gmail.com
	    User bob = User.find("byEmail", "bob@gmail.com").first();
	    
	    // Test 
	    assertNotNull(bob);
	    assertEquals("Bob the Man", bob.fullname);
	    
	    Professor _bob = (Professor) bob;
	    assertNotNull(_bob);
	    assertEquals(new Long(1), _bob.departmentId);
	    
	    Professor __bob = Professor.find("byUsername", "bob").first();
	    assertNotNull(__bob);
	    assertEquals(true, __bob.coordinator);
	}
	
	@Test
	public void createAndRetrieveStudent() {	
		Fixtures.deleteDatabase();
	    // Create a new user and save it
		new Student("bob", "secret", "Bob the Man", 
				"bob@gmail.com", "(099)-9999-9999", true, new Long(6426826), "address", 1, new Date()).create();
	    
	    // Retrieve the user with e-mail address bob@gmail.com
	    User bob = User.find("byEmail", "bob@gmail.com").first();
	    
	    // Test 
	    assertNotNull(bob);
	    assertEquals("Bob the Man", bob.fullname);
	    
	    Student _bob = (Student) bob;
	    assertNotNull(_bob);
	    assertEquals("address", _bob.address);
	    
	    Student __bob = Student.find("byUsername", "bob").first();
	    assertNotNull(__bob);
	    assertEquals(true, __bob.active);
	}
	
	@Test
	public void createAndRetrieveUniv() {	
		Fixtures.deleteDatabase();
	    // Create a new user and save it
		University univ = new University("USP", "Sao Carlos", "Centro", "13560-800").save();
	    
	    // Test 
	    assertNotNull(univ);
	    assertEquals("USP", univ.name);
	    
	    University _univ = University.find("byName", "USP").first();
	    assertNotNull(_univ);
	    assertEquals("USP", _univ.name);
	}
}



















