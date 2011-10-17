package controllers;

import models.User;

public class Security extends Secure.Security {

	static boolean authenticate(String username, String password) {
        return User.connect(username, password) != null;
    }
	
	public static void onDisconnected() {
		Application.index();
	}
	
	public static void onAuthenticated() {
		Admin.index();
	}
	
	public static boolean check(String profile) {
		if (profile.equals("admin")) {
			return models.Admin.find("byUsername", connected()).first() != null;
		}
		return false;
	}
}
