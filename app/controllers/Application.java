package controllers;

import play.*;
import play.db.Model;
import play.exceptions.TemplateNotFoundException;
import play.mvc.*;

import java.util.*;

import controllers.CRUD.ObjectType;

import models.Student;
import models.University;

public class Application extends Controller {

	public static void index() {
        render();
    }
	
	public static void show(String pageType) {
		render(pageType);
	}
}