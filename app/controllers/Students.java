package controllers;

import java.util.List;

import controllers.CRUD.ObjectType;

import play.*;
import play.db.Model;
import play.mvc.*;

@Check("admin")
@With(Secure.class)
public class Students extends CRUD {

}
