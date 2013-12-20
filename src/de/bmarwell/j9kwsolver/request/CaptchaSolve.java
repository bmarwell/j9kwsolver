/**
 * Copyright (c) 2010-2012, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
 */
package de.bmarwell.j9kwsolver.request;

import de.bmarwell.j9kwsolver.service.PropertyService;

/**
 * @author Benjamin Marwell
 *
 */
public class CaptchaSolve implements CaptchaRequestInterface {
	private static String url = "http://www.9kw.eu/index.cgi";
	private static String action = "usercaptchacorrect";
	
	private String apikey = null;
	private String id = null;
	private boolean extended = true;
	private String source = PropertyService.getProperty("toolname");
	private boolean debug = false;
	
	/**
	 * Contains the answer. Possible values: <br />
	 * "yes|no", "nnnxmmm" (coordinates) or "text".
	 */
	private String captcha = null;
	
	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public boolean isExtended() {
		return extended;
	}

	public void setExtended(boolean extended) {
		this.extended = extended;
	}

	/* (non-Javadoc)
	 * @see de.bmarwell.j9kwsolver.request.CaptchaRequestInterface#getUrl()
	 */
	@Override
	public String getUrl() {
		return url;
	}

	/* (non-Javadoc)
	 * @see de.bmarwell.j9kwsolver.request.CaptchaRequestInterface#getAction()
	 */
	@Override
	public String getAction() {
		return action;
	}

	public String getApikey() {
		return apikey;
	}

	public void setApikey(String pApikey) {
		this.apikey = pApikey;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String pCaptcha) {
		this.captcha = pCaptcha;
	}

	public static void setUrl(String url) {
		CaptchaSolve.url = url;
	}

	public static void setAction(String pAction) {
		CaptchaSolve.action = pAction;
	}
	
}
