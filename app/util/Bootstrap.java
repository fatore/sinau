package util;
import models.Department;
import models.University;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.libs.WS;
import play.test.Fixtures;


@OnApplicationStart
public class Bootstrap extends Job {

	public void doJob() {
		//GarapaWS.getDataFromWS();
	}
}
