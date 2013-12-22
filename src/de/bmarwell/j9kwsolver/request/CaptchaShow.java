/**
 * Copyright (c) 2013, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
 */
package de.bmarwell.j9kwsolver.request;

/**
 * Requests the captcha image.
 * @author Benjamin Marwell
 *
 */
public class CaptchaShow implements CaptchaRequestInterface, ApiKeyRequest {
	/**
	 * The API URL for this request.
	 */
	private static final String URL = "http://www.9kw.eu/index.cgi";
	/**
	 * The API action for this request.
	 */
	private static final String ACTION = "usercaptchashow";
	
	/**
	 * The ID of the captcha to be shown.
	 */
	private String id;
	/**
	 * The api key of the user.
	 */
	private String apikey;
	/**
	 * The name of this tool.
	 */
	private String source;
	/**
	 * Set to true if ansewer should be base64 encoded.
	 */
	private boolean base64 = true;
	private boolean speed = false;
	private boolean nodraw = false;
	/**
	 * Set to true to receive a debug captcha image.
	 */
	private boolean debug = false;

	public String getId() {
		return id;
	}

	public void setId(final String pId) {
		this.id = pId;
	}

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

	public boolean isBase64() {
		return base64;
	}

	public void setBase64(final boolean pBase64) {
		this.base64 = pBase64;
	}

	public boolean isSpeed() {
		return speed;
	}

	public void setSpeed(final boolean pSpeed) {
		this.speed = pSpeed;
	}

	public boolean isNodraw() {
		return nodraw;
	}

	public void setNodraw(final boolean pNodraw) {
		this.nodraw = pNodraw;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(final boolean pDebug) {
		this.debug = pDebug;
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
