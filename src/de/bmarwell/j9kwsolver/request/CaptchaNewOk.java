/**
 * Copyright (c) 2010-2012, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
 */
package de.bmarwell.j9kwsolver.request;

public class CaptchaNewOk implements CaptchaRequestInterface {
	private static final String URL = "http://www.9kw.eu/index.cgi"; 
	private static final String ACTION = "usercaptchanewok";
	
	private String apikey;
	private String source;
	
	public String getApikey() {
		return apikey;
	}
	public void setApikey(final String pApikey) {
		this.apikey = pApikey;
	}
	public String getSource() {
		return source;
	}
	public void setSource(final String pSource) {
		this.source = pSource;
	}
	public String getUrl() {
		return URL;
	}
	public String getAction() {
		return ACTION;
	}
	
	
	
}
