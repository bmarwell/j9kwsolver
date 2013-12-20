/**
 * Copyright (c) 2013, Benjamin Marwell.  This file is
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
	public void setCaptcha(final Captcha pCaptcha) {
		this.captcha = pCaptcha;
	}
	public String getCaptchaText() {
		return captchaText;
	}
	public void setCaptchaText(final String pCaptchaText) {
		this.captchaText = pCaptchaText;
	}
	public YesNo getConfirmCaptchaCorrect() {
		return confirmCaptchaCorrect;
	}
	public void setConfirmCaptchaCorrect(final YesNo pConfirmCaptchaCorrect) {
		this.confirmCaptchaCorrect = pConfirmCaptchaCorrect;
	}
	public String getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(final String pCoordinates) {
		this.coordinates = pCoordinates;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
