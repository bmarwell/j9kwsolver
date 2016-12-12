/**
 * Copyright (c) 2013, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
 */
package de.bmarwell.j9kwsolver.response;

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
	
	/**
	 * Returns the text to be confirmed, if this is a confirm captcha.
	 * @return String which is to be confirmed.
	 */
	public String getConfirmText() {
		return confirmText;
	}

	/**
	 * Sets the text to be confirmed, if this is a confirm captcha.
	 * @param pConfirmText
	 */
	public void setConfirmText(final String pConfirmText) {
		this.confirmText = pConfirmText;
	}

	public boolean isPhrase() {
		return phrase;
	}
	
	public void setPhrase(final boolean pPhrase) {
		this.phrase = pPhrase;
	}
	
	/**
	 * Returns whether this is a numeric captcha.
	 * @return true if this is a numeric captcha.
	 */
	public boolean isNumeric() {
		return numeric;
	}
	
	/**
	 * Sets whether this is a numeric captcha.
	 * @param pNumeric true if this is a numeric captcha.
	 */
	public void setNumeric(final boolean pNumeric) {
		this.numeric = pNumeric;
	}
	
	/**
	 * Returns true if this is a mathematical captcha.
	 * @return true if this is a mathematical captcha.
	 */
	public boolean isMath() {
		return math;
	}
	
	/**
	 * Set to true if this is a mathematical captcha.
	 * @param pMath true if this is a mathematical captcha.
	 */
	public void setMath(final boolean pMath) {
		this.math = pMath;
	}
	
	/**
	 * Returns the minimum length of captcha text.
	 * @return the minimum length of captcha text.
	 */
	public int getMinLength() {
		return minLength;
	}
	
	/**
	 * Sets the minimum length of captcha text.
	 * @param pMinLength the minimum length of captcha text.
	 */
	public void setMinLength(final int pMinLength) {
		this.minLength = pMinLength;
	}
	
	/**
	 * /**
	 * Returns the maximum length of captcha text.
	 * @return the maximum length of captcha text.
	 */
	public int getMaxLength() {
		return maxLength;
	}
	
	/**
	 * Sets the maximum length of captcha text.
	 * @param pMaxLength the maximum length of captcha text.
	 */
	public void setMaxLength(final int pMaxLength) {
		this.maxLength = pMaxLength;
	}
	
	/**
	 * True if this captcha needs confirmation.
	 * @return true if this captcha needs confirmation.
	 */
	public boolean isConfirmedCaptcha() {
		return confirmedCaptcha;
	}
	
	/**
	 * Set to true if this captcha needs confirmation.
	 * @param pConfirmedCaptcha true if this captcha needs confirmation.
	 */
	public void setConfirmedCaptcha(final boolean pConfirmedCaptcha) {
		this.confirmedCaptcha = pConfirmedCaptcha;
	}
	
	/**
	 * Returns the width of the image.
	 * @return size in pixels.
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Sets the width of the image.
	 * @param pWidth size in pixels.
	 */
	public void setWidth(final int pWidth) {
		this.width = pWidth;
	}
	
	/**
	 * Returns the height of the image.
	 * @return the height of the image in pixels.
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Sets the height of the image.
	 * @param pHeight the height of the image in pixels.
	 */
	public void setHeight(final int pHeight) {
		this.height = pHeight;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	

}
