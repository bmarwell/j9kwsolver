/**
 * Copyright (c) 2010-2012, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
 */
package de.bmarwell.j9kwsolver.request;

/**
 * Request object for users balance.
 * @author Benjamin Marwell
 *
 */
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
