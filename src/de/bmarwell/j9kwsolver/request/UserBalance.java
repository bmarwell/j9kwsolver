/**
 * Copyright (c) 2013, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
 */
package de.bmarwell.j9kwsolver.request;

/**
 * Request object for users balance.
 * @author Benjamin Marwell
 *
 */
public class UserBalance implements CaptchaRequestInterface, ApiKeyRequest {
	private static final String URL = "http://www.9kw.eu/index.cgi";
	private static final String ACTION = "usercaptchaguthaben";
	private String apikey = null;
	
	public String getApikey() {
		return apikey;
	}

	public void setApikey(final String pApikey) {
		this.apikey = pApikey;
	}

	@Override
	public String getUrl() {
		return URL;
	}

	@Override
	public String getAction() {
		return ACTION;
	}

}
