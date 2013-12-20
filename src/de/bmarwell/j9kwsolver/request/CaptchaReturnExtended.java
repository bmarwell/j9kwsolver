/**
 * Copyright (c) 2013, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
 */
package de.bmarwell.j9kwsolver.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * The offered captcha with extended information.
 * @author Benjamin Marwell
 *
 */
public class CaptchaReturnExtended extends CaptchaReturn {
	/**
	 * The text to be confirmed, if this is a confirm captcha.
	 */
	private String confirmText = null;
	
	private boolean phrase = false;
	/**
	 * A numeric captcha.
	 */
	private boolean numeric = false;
	/**
	 * A mathematic captcha.
	 */
	private boolean math = false;
	
	/**
	 * The minimum text input length.
	 */
	private int minLength = 0;
	/**
	 * The maximum text input length.
	 */
	private int maxLength = 0;
	/**
	 * True if this captcha needs to be confirmed.
	 */
	private boolean confirmedCaptcha = false;
	
	/**
	 * The height of the image.
	 */
	private int width = 0;
	/**
	 * The width of the image.
	 */
	private int height = 0;
	
	public String getConfirmText() {
		return confirmText;
	}

	public void setConfirmText(final String pConfirmText) {
		this.confirmText = pConfirmText;
	}

	public boolean isPhrase() {
		return phrase;
	}
	
	public void setPhrase(final boolean pPhrase) {
		this.phrase = pPhrase;
	}
	public boolean isNumeric() {
		return numeric;
	}
	public void setNumeric(final boolean pNumeric) {
		this.numeric = pNumeric;
	}
	public boolean isMath() {
		return math;
	}
	public void setMath(final boolean pMath) {
		this.math = pMath;
	}
	public int getMinLength() {
		return minLength;
	}
	public void setMinLength(final int pMinLength) {
		this.minLength = pMinLength;
	}
	public int getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(final int pMaxLength) {
		this.maxLength = pMaxLength;
	}
	public boolean isConfirmedCaptcha() {
		return confirmedCaptcha;
	}
	public void setConfirmedCaptcha(final boolean pConfirmedCaptcha) {
		this.confirmedCaptcha = pConfirmedCaptcha;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(final int pWidth) {
		this.width = pWidth;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(final int pHeight) {
		this.height = pHeight;
	}
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	

}
