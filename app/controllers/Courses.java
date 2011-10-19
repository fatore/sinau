package controllers;

import java.util.List;

import play.db.Model;
import play.mvc.With;
import controllers.CRUD;
import controllers.CRUD.ObjectType;

@Check("admin")
@With(Secure.class)
public class Courses extends CRUD {
	

}
