package controllers;

import java.util.List;

import controllers.CRUD.ObjectType;

import play.db.Model;
import play.mvc.With;

@Check("admin")
@With(Secure.class)
public class Subjects extends CRUD {

}
