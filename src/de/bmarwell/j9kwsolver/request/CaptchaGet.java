/**
 * Copyright (c) 2010-2012, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
 */
package de.bmarwell.j9kwsolver.request;

import de.bmarwell.j9kwsolver.domain.YesNo;

/**
 * @author Benjamin Marwell
 *
 */
public class CaptchaGet implements CaptchaRequestInterface {
	private static final String URL = "http://www.9kw.eu/index.cgi";
	private static final String ACTION = "usercaptchanew";
	
	private String apikey;
	private String source = "jk9solver"; // toolname
	private boolean nocaptcha = false; // 1 = no response, 0 = "NO CAPTCHA".
	private YesNo text = YesNo.YES;
	private boolean mouse = true;
	private boolean confirm = true;
	private boolean selfsolve = true;
	private boolean selfonly = false;
	private boolean withok = true; // bitte usercaptchanewok nutzen 
	private boolean extended = true; // more information
	private boolean debug = false;
	
	public String getAction() {
		return ACTION;
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
	public YesNo getText() {
		return text;
	}
	public void setText(YesNo text) {
		this.text = text;
	}
	
	
	public boolean isNocaptcha() {
		return nocaptcha;
	}

	public void setNocaptcha(boolean nocaptcha) {
		this.nocaptcha = nocaptcha;
	}

	public boolean isMouse() {
		return mouse;
	}

	public void setMouse(boolean mouse) {
		this.mouse = mouse;
	}

	public boolean isConfirm() {
		return confirm;
	}

	public void setConfirm(boolean pConfirm) {
		this.confirm = pConfirm;
	}

	public boolean isSelfsolve() {
		return selfsolve;
	}

	public void setSelfsolve(boolean selfsolve) {
		this.selfsolve = selfsolve;
	}

	public boolean isSelfonly() {
		return selfonly;
	}

	public void setSelfonly(boolean selfonly) {
		this.selfonly = selfonly;
	}

	public boolean isWithok() {
		return withok;
	}

	public void setWithok(boolean withok) {
		this.withok = withok;
	}

	public boolean isExtended() {
		return extended;
	}

	public void setExtended(boolean extended) {
		this.extended = extended;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public String getUrl() {
		return URL;
	} 
	
}
