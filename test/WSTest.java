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

public class WSTest extends UnitTest {

	@Test
	public void parseUniversity() {
		Document document = WS
				.url("http://garapa.intermidia.icmc.usp.br:8080/Sinau/rest/universidade")
				.get().getXml("ISO-8859-1");

		Element docElem = document.getDocumentElement();

		NodeList nodelist = docElem.getElementsByTagName("universidade");
		if (nodelist != null && nodelist.getLength() > 0) {
			for (int i = 0; i < nodelist.getLength(); i++) {
				Element el = (Element) nodelist.item(i);
				University university = getUniversity(el);
				university.save();
			}
		}
	}

	private University getUniversity(Element element) {

		String zipCode = getTextValue(element, "cep");
		String city = getTextValue(element, "cidade");
		String address = getTextValue(element, "endereco");
		String name = getTextValue(element, "nome");
		//		int x = Integer.parseInt(getTextValue(empEl, "numero"));

		University university = new University(name, city, address,
				zipCode);

		return university;
	}

	private String getTextValue(Element element, String tagName) {
		String textVal = null;
		NodeList nl = element.getElementsByTagName(tagName);
		if (nl != null && nl.getLength() > 0) {
			Element el = (Element) nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}

		return textVal;
	}

	@SuppressWarnings("unused")
	public void getSubjects() throws Exception {
		if (Subject.count() == 0) {



			Document document = WS.url(
					"http://garapa.intermidia.icmc.usp.br:8080/Sinau/rest/disciplinas")
					.get().getXml("ISO-8859-1");

			NodeList nodeList = document.getDocumentElement().getElementsByTagName("disciplina");
			if (nodeList != null && nodeList.getLength() > 0) {
				for (int i = 0; i < nodeList.getLength(); i++) {
					Element element = (Element) nodeList.item(i);

					String name = element.getElementsByTagName("nome").item(0).getFirstChild().getNodeValue();
					String description = element.getElementsByTagName("descricao").item(0).getFirstChild().getNodeValue();
					Integer workload = Integer.parseInt(element.getElementsByTagName("cargaHoraria").
							item(0).getFirstChild().getNodeValue());
					Date schedule = new SimpleDateFormat("yyyy-MM-dd").parse(element.getElementsByTagName("horario").
							item(0).getFirstChild().getNodeValue());
					Integer room = Integer.parseInt(element.getElementsByTagName("sala").
							item(0).getFirstChild().getNodeValue());
					Long courseId = Long.parseLong(element.getElementsByTagName("cursoIdcurso").
							item(0).getFirstChild().getNodeValue());

					Course course = Course.findById(courseId);
					course.addSubject(name, description, workload, schedule, room);
				}
			}
			System.out.println("Success: Subjects Loaded.");
		} else {
			System.out.println("Error: Subjects Table Not Empty.");
		}
	}

	@Test
	public  void getUsers() throws Exception {
		Fixtures.deleteDatabase();
		if (User.count() == 0) {

			File fXmlFile = new File("/home/fm/users.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

			Document document = WS.url(
					"http://garapa.intermidia.icmc.usp.br:8080/Sinau/rest/usuarios")
					.get().getXml("UTF-8");
			document = dBuilder.parse(fXmlFile);

			NodeList nodeList = document.getDocumentElement().getElementsByTagName("usuario");
			if (nodeList != null && nodeList.getLength() > 0) {
				for (int i = 0; i < nodeList.getLength(); i++) {
					Element element = (Element) nodeList.item(i);
					String username = element.getElementsByTagName("username").item(0).getFirstChild().getNodeValue();
					String password = element.getElementsByTagName("senha").item(0).getFirstChild().getNodeValue();
					String fullname = element.getElementsByTagName("nome").item(0).getFirstChild().getNodeValue();
					String email = element.getElementsByTagName("email").item(0).getFirstChild().getNodeValue();
					String phone = element.getElementsByTagName("telefone").item(0).getFirstChild().getNodeValue();
					boolean active = Boolean.parseBoolean(element.getElementsByTagName("ativo")
							.item(0).getFirstChild().getNodeValue());

					String type = element.getElementsByTagName("tipo").item(0).getFirstChild().getNodeValue();

					if (type.toUpperCase().equals("ADMIN")) {
						new Admin(username, password, fullname, email, phone, active, null).save();
					}
					if (type.toUpperCase().equals("ALUNO")) {
						new Student(username, password, fullname, email, phone, active, null, null, null, null).save();
					}
					if (type.toUpperCase().equals("PROFESSOR")) {
						new Professor(username, password, fullname, email, phone, active, false).save();
					}
				}
			}
			//			getAdmins();
			//			getStudents();
			getProfessors();
			System.out.println("Success: Users Loaded.");
		} else {
			System.out.println("Error: Users Table Not Empty.");
		}
	}

	private static void getAdmins() {
		Document document = WS.url(
				"http://garapa.intermidia.icmc.usp.br:8080/Sinau/rest/administradores")
				.get().getXml("UTF-8");

		NodeList nodeList = document.getDocumentElement().getElementsByTagName("admin");
		if (nodeList != null && nodeList.getLength() > 0) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				Element element = (Element) nodeList.item(i);

				String role = element.getElementsByTagName("cargo").item(0).getFirstChild().getNodeValue();

				Long userId = Long.parseLong(element.getElementsByTagName("idusuario").
						item(0).getFirstChild().getNodeValue());

				Admin admin = User.findById(userId);
				admin.role = role;
				admin.save();
			}
		}
	}

	private static void getStudents() throws Exception {
		Document document = WS.url(
				"http://garapa.intermidia.icmc.usp.br:8080/Sinau/rest/alunos")
				.get().getXml("ISO-8859-1");

		NodeList nodeList = document.getDocumentElement().getElementsByTagName("aluno");
		if (nodeList != null && nodeList.getLength() > 0) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				Element element = (Element) nodeList.item(i);

				Long regNumber = Long.parseLong(element.getElementsByTagName("matriculaNusp").
						item(0).getFirstChild().getNodeValue());
				String address = element.getElementsByTagName("endereco").item(0).getFirstChild().getNodeValue();
				Integer semester = Integer.parseInt(element.getElementsByTagName("semestre").
						item(0).getFirstChild().getNodeValue());
				Date beganAt = new SimpleDateFormat("yyyy-MM-dd").parse(element.getElementsByTagName("anoInicio").
						item(0).getFirstChild().getNodeValue());
				boolean scholar = Boolean.parseBoolean(element.getElementsByTagName("bolsista")
						.item(0).getFirstChild().getNodeValue());

				Long userId = Long.parseLong(element.getElementsByTagName("idusuario").
						item(0).getFirstChild().getNodeValue());

				Student student = User.findById(userId);

				student.regNumber = regNumber;
				student.address = address;
				student.semester = semester;
				student.beganAt = beganAt;
				student.scholar = scholar;

				student.save();
			}
		}
	}
	private static void getProfessors() throws Exception {
		Document document = WS.url(
				"http://garapa.intermidia.icmc.usp.br:8080/Sinau/rest/professores")
				.get().getXml("ISO-8859-1");

		NodeList nodeList = document.getDocumentElement().getElementsByTagName("professor");
		if (nodeList != null && nodeList.getLength() > 0) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				Element element = (Element) nodeList.item(i);

				boolean coordinator = Boolean.parseBoolean(element.getElementsByTagName("coordenador")
						.item(0).getFirstChild().getNodeValue());

				Long userId = Long.parseLong(element.getElementsByTagName("idusuario").
						item(0).getFirstChild().getNodeValue());

				Long deptId = Long.parseLong(element.getElementsByTagName("iddepartamento").
						item(0).getFirstChild().getNodeValue());

				Professor professor = User.findById(userId);
				professor.coordinator = coordinator;
				professor.save();

				Department department = Department.findById(deptId);
				department.addProfessor(professor);
			}
		}
	}

}
