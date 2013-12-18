package de.bmarwell.j9kwsolver.request;

public class ServerCheck implements CaptchaRequestInterface {
	private static final String url = "http://www.9kw.eu/index.cgi";
	private static final String action = "userservercheck";
	
	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public String getAction() {
		return action;
	}

	
	
}
