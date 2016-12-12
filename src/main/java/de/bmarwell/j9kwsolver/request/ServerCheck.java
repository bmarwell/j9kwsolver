package de.bmarwell.j9kwsolver.request;

public class ServerCheck implements CaptchaRequestInterface {
	private static final String URL = "http://www.9kw.eu/index.cgi";
	private static final String ACTION = "userservercheck";
	
	@Override
	public String getUrl() {
		return URL;
	}

	@Override
	public String getAction() {
		return ACTION;
	}

	
	
}
