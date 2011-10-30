import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import models.Admin;
import models.Course;
import models.Department;
import models.Professor;
import models.Student;
import models.Subject;
import models.University;
import models.User;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import play.libs.WS;
import play.test.Fixtures;
import play.test.UnitTest;
import util.GarapaWS;

public class WSTest extends UnitTest {

	@Test
	public void getDataFromWS() {
		GarapaWS.getDataFromWS();
	}
}
