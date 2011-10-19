package controllers;

import models.User;
import play.classloading.enhancers.ControllersEnhancer.ByPass;
import play.mvc.*;

@With(Secure.class)
public class Admin extends Controller {

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
