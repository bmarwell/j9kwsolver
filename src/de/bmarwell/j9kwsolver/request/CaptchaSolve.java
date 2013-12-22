/**
 * Copyright (c) 2013, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
 */
package de.bmarwell.j9kwsolver.request;

import de.bmarwell.j9kwsolver.service.PropertyService;

/**
 * @author Benjamin Marwell
 *
 */
public class CaptchaSolve implements CaptchaRequestInterface, ApiKeyRequest {
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

	public void setDebug(final boolean pDebug) {
		this.debug = pDebug;
	}

	public String getSource() {
		return source;
	}

	public void setSource(final String pSource) {
		this.source = pSource;
	}

	public boolean isExtended() {
		return extended;
	}

	public void setExtended(final boolean pExtended) {
		this.extended = pExtended;
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

	public void setApikey(final String pApikey) {
		this.apikey = pApikey;
	}

	public String getId() {
		return id;
	}

	public void setId(final String pId) {
		this.id = pId;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(final String pCaptcha) {
		this.captcha = pCaptcha;
	}

	public static void setUrl(final String pUrl) {
		CaptchaSolve.url = pUrl;
	}

	public static void setAction(final String pAction) {
		CaptchaSolve.action = pAction;
	}
	
}
