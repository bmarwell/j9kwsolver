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
