/**
 * Copyright (c) 2010-2012, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
 */
package de.bmarwell.j9kwsolver.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Benjamin Marwell
 *
 */
public class CaptchaSolution {
	/**
	 * The captcha which was solved.
	 */
	private Captcha captcha;
	
	/* SolveTypes */
	private String captchaText = null;
	private YesNo confirmCaptchaCorrect = null;
	private String coordinates = null;
	
	public Captcha getCaptcha() {
		return captcha;
	}
	public void setCaptcha(Captcha captcha) {
		this.captcha = captcha;
	}
	public String getCaptchaText() {
		return captchaText;
	}
	public void setCaptchaText(String captchaText) {
		this.captchaText = captchaText;
	}
	public YesNo getConfirmCaptchaCorrect() {
		return confirmCaptchaCorrect;
	}
	public void setConfirmCaptchaCorrect(YesNo confirmCaptchaCorrect) {
		this.confirmCaptchaCorrect = confirmCaptchaCorrect;
	}
	public String getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}
