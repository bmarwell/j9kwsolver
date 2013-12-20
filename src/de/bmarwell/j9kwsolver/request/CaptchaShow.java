/**
 * Copyright (c) 2010-2012, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
 */
package de.bmarwell.j9kwsolver.request;

public class CaptchaShow implements CaptchaRequestInterface {
	private static final String URL = "http://www.9kw.eu/index.cgi";
	private static final String ACTION = "usercaptchashow";
	
	private String id;
	private String apikey;
	private String source;
	private boolean base64 = true;
	private boolean speed = false;
	private boolean nodraw = false;
	private boolean debug = false;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getApikey() {
		return apikey;
	}

	public void setApikey(String pApikey) {
		this.apikey = pApikey;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public boolean isBase64() {
		return base64;
	}

	public void setBase64(boolean pBase64) {
		this.base64 = pBase64;
	}

	public boolean isSpeed() {
		return speed;
	}

	public void setSpeed(boolean speed) {
		this.speed = speed;
	}

	public boolean isNodraw() {
		return nodraw;
	}

	public void setNodraw(boolean nodraw) {
		this.nodraw = nodraw;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
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
