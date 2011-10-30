package util;

import java.text.SimpleDateFormat;
import java.util.Date;

import models.Admin;
import models.Course;
import models.Department;
import models.Professor;
import models.Student;
import models.Subject;
import models.University;
import models.User;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import play.libs.WS;
import play.test.Fixtures;

public class GarapaWS {

	public static void getDataFromWS() {
		try {
			getUniversity();
			getDepartment();
			getCourses();
			getSubjects();
			getUsers();

		} catch (Exception e) {
			System.out.println("Error: Web Service not Available.");
			e.printStackTrace();
		}
	}

	private static void getUniversity() {
		if (University.count() == 0) {
			Document document = WS.url(
					"http://garapa.intermidia.icmc.usp.br:8080/Sinau/rest/universidades")
					.get().getXml("ISO-8859-1");

			NodeList nodeList = document.getDocumentElement().getElementsByTagName("universidade");
			if (nodeList != null && nodeList.getLength() > 0) {
				for (int i = 0; i < 3; i++) {
					//				for (int i = 0; i < nodeList.getLength(); i++) {
					Element element = (Element) nodeList.item(i);

					String name = element.getElementsByTagName("nome").item(0).getFirstChild().getNodeValue();
					String city = element.getElementsByTagName("cidade").item(0).getFirstChild().getNodeValue();
					String address = element.getElementsByTagName("endereco").item(0).getFirstChild().getNodeValue();
					String zipCode = element.getElementsByTagName("cep").item(0).getFirstChild().getNodeValue();
					new University(name, city, address, zipCode).save();					
				}
			}
			System.out.println("Success: Universities Loaded.");
		} else {
			System.out.println("Error: Universities Table Not Empty.");
		}
	}

	private static void getDepartment() {
		if (Department.count() == 0) {
			Document document = WS.url(
					"http://garapa.intermidia.icmc.usp.br:8080/Sinau/rest/departamentos")
					.get().getXml("ISO-8859-1");

			NodeList nodeList = document.getDocumentElement().getElementsByTagName("departamento");
			if (nodeList != null && nodeList.getLength() > 0) {
				//				for (int i = 0; i < nodeList.getLength(); i++) {
				for (int i = 0; i < 12; i++) {
					Element element = (Element) nodeList.item(i);

					String name = element.getElementsByTagName("nome").item(0).getFirstChild().getNodeValue();
					String phone = element.getElementsByTagName("telefone").item(0).getFirstChild().getNodeValue();
					Long univId = Long.parseLong(element.getElementsByTagName("iduniversidade").
							item(0).getFirstChild().getNodeValue());

					//University university = University.findById(univId);
					University university = (University) University.findAll().get(univId.intValue() - 1);
					university.addDepartment(name, phone);
				}
			}
			System.out.println("Success: Departments Loaded.");
		} else {
			System.out.println("Error: Departments Table Not Empty.");
		}
	}

	private static void getCourses() {
		if (Course.count() == 0) {
			Document document = WS.url(
					"http://garapa.intermidia.icmc.usp.br:8080/Sinau/rest/cursos")
					.get().getXml("ISO-8859-1");

			NodeList nodeList = document.getDocumentElement().getElementsByTagName("curso");
			if (nodeList != null && nodeList.getLength() > 0) {
				for (int i = 0; i < 6; i++) {
					Element element = (Element) nodeList.item(i);

					String name = element.getElementsByTagName("nome").item(0).getFirstChild().getNodeValue();
					String area = element.getElementsByTagName("area").item(0).getFirstChild().getNodeValue();
					Integer availableSeats = Integer.parseInt(element.getElementsByTagName("vagas").
							item(0).getFirstChild().getNodeValue());
					Long deptId = Long.parseLong(element.getElementsByTagName("iddepartamento").
							item(0).getFirstChild().getNodeValue());

//					Department department = Department.findById(deptId);
					Department department = (Department) Department.findAll().get(deptId.intValue() - 1);
					department.addCourse(name, area, availableSeats);
				}
			}
			System.out.println("Success: Courses Loaded.");
		} else {
			System.out.println("Error: Courses Table Not Empty.");
		}
	}

	private static void getSubjects() throws Exception {
		if (Subject.count() == 0) {
			Document document = WS.url(
					"http://garapa.intermidia.icmc.usp.br:8080/Sinau/rest/disciplinas")
					.get().getXml("ISO-8859-1");

			NodeList nodeList = document.getDocumentElement().getElementsByTagName("disciplina");
			if (nodeList != null && nodeList.getLength() > 0) {
				for (int i = 0; i < 2; i++) {
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

//					Course course = Course.findById(courseId);
					Course course = (Course) Course.findAll().get(courseId.intValue() - 1);
					course.addSubject(name, description, workload, schedule, room);
				}
			}
			System.out.println("Success: Subjects Loaded.");
		} else {
			System.out.println("Error: Subjects Table Not Empty.");
		}
	}

	private static void getUsers() throws Exception {
		if (User.count() == 0) {
			Document document = WS.url(
					"http://garapa.intermidia.icmc.usp.br:8080/Sinau/rest/usuarios")
					.get().getXml("ISO-8859-1");

			NodeList nodeList = document.getDocumentElement().getElementsByTagName("usuario");
			if (nodeList != null && nodeList.getLength() > 0) {
				for (int i = 0; i < 27; i++) {
					Element element = (Element) nodeList.item(i);

					String username = element.getElementsByTagName("nomeUsuario").item(0).getFirstChild().getNodeValue();
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
			getAdmins();
			getStudents();
			getProfessors();
			System.out.println("Success: Users Loaded.");
		} else {
			System.out.println("Error: Users Table Not Empty.");
		}
	}

	private static void getAdmins() {
		Document document = WS.url(
				"http://garapa.intermidia.icmc.usp.br:8080/Sinau/rest/administradores")
				.get().getXml("ISO-8859-1");

		NodeList nodeList = document.getDocumentElement().getElementsByTagName("admin");
		if (nodeList != null && nodeList.getLength() > 0) {
			for (int i = 0; i < 2; i++) {
				Element element = (Element) nodeList.item(i);

				String role = element.getElementsByTagName("cargo").item(0).getFirstChild().getNodeValue();

				Long userId = Long.parseLong(element.getElementsByTagName("idusuario").
						item(0).getFirstChild().getNodeValue());

//				Admin admin = User.findById(userId);
				System.out.println("trying to save user id = " + userId);
				Admin admin = (Admin) User.findAll().get(userId.intValue() - 2);
				admin.role = role;
				admin.save();
			}
		}
		System.out.println("Success: Admins Loaded.");
	}

	private static void getStudents() throws Exception {
		Document document = WS.url(
				"http://garapa.intermidia.icmc.usp.br:8080/Sinau/rest/alunos")
				.get().getXml("ISO-8859-1");

		NodeList nodeList = document.getDocumentElement().getElementsByTagName("aluno");
		if (nodeList != null && nodeList.getLength() > 0) {
			for (int i = 0; i < 18; i++) {
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

				System.out.println("trying to save user id = " + userId);
				
//				Student student = User.findById(userId);
				try {
					Student student = (Student) User.findAll().get(userId.intValue() - 2);
					
					student.regNumber = regNumber;
					student.address = address;
					student.semester = semester;
					student.beganAt = beganAt;
					student.scholar = scholar;
	
					student.save();
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
		}
		System.out.println("Success: Students Loaded.");
	}
	
	private static void getProfessors() throws Exception {
		Document document = WS.url(
				"http://garapa.intermidia.icmc.usp.br:8080/Sinau/rest/professores")
				.get().getXml("ISO-8859-1");

		NodeList nodeList = document.getDocumentElement().getElementsByTagName("professor");
		if (nodeList != null && nodeList.getLength() > 0) {
			for (int i = 0; i < 6; i++) {
				Element element = (Element) nodeList.item(i);

				boolean coordinator = Boolean.parseBoolean(element.getElementsByTagName("coordenador")
						.item(0).getFirstChild().getNodeValue());

				Long userId = Long.parseLong(element.getElementsByTagName("idusuario").
						item(0).getFirstChild().getNodeValue());

				Long deptId = Long.parseLong(element.getElementsByTagName("iddepartamento").
						item(0).getFirstChild().getNodeValue());

				System.out.println("trying to save user id = " + userId);
				
				try {
	//				Professor professor = User.findById(userId);
					Professor professor = (Professor) User.findAll().get(userId.intValue() - 2);
					professor.coordinator = coordinator;
					professor.save();
	
	//				Department department = Department.findById(deptId);
					Department department = (Department) Department.findAll().get(deptId.intValue() - 1);
					department.addProfessor(professor);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
		System.out.println("Success: Professors Loaded.");
	}
}
