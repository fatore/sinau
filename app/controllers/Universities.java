package controllers;

import java.util.List;

import controllers.CRUD.ObjectType;

import play.classloading.enhancers.ControllersEnhancer.ByPass;
import play.db.Model;
import play.exceptions.TemplateNotFoundException;
import play.mvc.With;

import models.University;

@Check("admin")
@With(Secure.class)
@CRUD.For(University.class)
public class Universities extends CRUD {
	
}
