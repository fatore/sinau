package controllers;

import play.*;
import play.db.Model;
import play.exceptions.TemplateNotFoundException;
import play.mvc.*;
import play.mvc.results.RenderHtml;
import play.utils.HTML;

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
}