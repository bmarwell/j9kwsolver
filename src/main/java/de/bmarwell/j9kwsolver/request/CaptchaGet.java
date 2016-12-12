/**
 * Copyright (c) 2013, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
 */
package de.bmarwell.j9kwsolver.request;

import de.bmarwell.j9kwsolver.domain.YesNo;

/**
 * @author Benjamin Marwell
 *
 */
public class CaptchaGet implements CaptchaRequestInterface, ApiKeyRequest {
	/**
	 * The API base url for this request. 
	 */
	private static final String URL = "http://www.9kw.eu/index.cgi";
	/**
	 * The API action name for this request.
	 */
	private static final String ACTION = "usercaptchanew";
	
	/**
	 * The API key for this request.
	 */
	private String apikey;
	/**
	 * The tool name of this request.
	 */
	private String source = "jk9solver"; // toolname
	/**
	 * true if response should be supressed. false for "NO CAPTCHA".
	 */
	private boolean nocaptcha = false; // 1 = no response, 0 = "NO CAPTCHA".
	/**
	 * Set to YES if text captchas are allowed.
	 */
	private YesNo text = YesNo.YES;
	/**
	 * Set to true if mouse captchas are allowed.
	 */
	private boolean mouse = true;
	/**
	 * Set to true if confirm captchas are allowed.
	 */
	private boolean confirm = true;
	/**
	 * Set to true if solving your own captchas is allowed.
	 */
	private boolean selfsolve = true;
	/**
	 * Set to true if you are only solving your own captchas.
	 */
	private boolean selfonly = false;
	/**
	 * API description: bitte usercaptchanewok nutzen
	 * Whatever this means.
	 */
	private boolean withok = true; // bitte usercaptchanewok nutzen 
	/**
	 * Set to true for extended answer.
	 */
	private boolean extended = true; // more information
	/**
	 * Set to true for mock request - server will deliver a static image.
	 * Hint: Extended will not work with this. :-(
	 */
	private boolean debug = false;
	
	public String getAction() {
		return ACTION;
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
	public YesNo getText() {
		return text;
	}
	public void setText(final YesNo pText) {
		this.text = pText;
	}
	
	
	public boolean isNocaptcha() {
		return nocaptcha;
	}

	public void setNocaptcha(final boolean pNocaptcha) {
		this.nocaptcha = pNocaptcha;
	}

	public boolean isMouse() {
		return mouse;
	}

	public void setMouse(final boolean pMouse) {
		this.mouse = pMouse;
	}

	public boolean isConfirm() {
		return confirm;
	}

	public void setConfirm(final boolean pConfirm) {
		this.confirm = pConfirm;
	}

	public boolean isSelfsolve() {
		return selfsolve;
	}

	public void setSelfsolve(final boolean pSelfsolve) {
		this.selfsolve = pSelfsolve;
	}

	public boolean isSelfonly() {
		return selfonly;
	}

	public void setSelfonly(final boolean pSelfonly) {
		this.selfonly = pSelfonly;
	}

	public boolean isWithok() {
		return withok;
	}

	public void setWithok(final boolean pWithok) {
		this.withok = pWithok;
	}

	public boolean isExtended() {
		return extended;
	}

	public void setExtended(final boolean pExtended) {
		this.extended = pExtended;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(final boolean pDebug) {
		this.debug = pDebug;
	}

	public String getUrl() {
		return URL;
	}

}
