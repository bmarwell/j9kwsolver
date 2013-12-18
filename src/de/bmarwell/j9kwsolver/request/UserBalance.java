package de.bmarwell.j9kwsolver.request;

public class UserBalance implements CaptchaRequestInterface {
	private static final String url = "http://www.9kw.eu/index.cgi";
	private static final String action = "usercaptchaguthaben";
	private String apikey = null;
	
	public String getApikey() {
		return apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}

	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public String getAction() {
		return action;
	}

}
