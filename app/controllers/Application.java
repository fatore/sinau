package controllers;

import play.*;
import play.db.Model;
import play.exceptions.TemplateNotFoundException;
import play.mvc.*;

import java.util.*;

import controllers.CRUD.ObjectType;

import models.Student;
import models.University;
import models.User;

public class Application extends Controller {

	@Before
	public static void setConnectedUser() {
		if (Security.isConnected()) {
			User user = User.find("byUsername", Security.connected()).first();
			renderArgs.put("user", user.fullname);
		}
	}
	
	public static void index() {
        render();
    }
	
	public static void showPage(String page) {
		try {
			ObjectType type;
			String pageController = page.substring(0, 1).toUpperCase() + page.substring(1);
			Class classType = Class.forName("controllers." + pageController);
			type = ObjectType.get(classType);
			render("Application/" + page + ".html", type);
		} catch (Exception e) {
			render("Application/index.html");
		}
	}
}