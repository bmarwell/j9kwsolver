/**
 * Copyright (c) 2010-2012, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
 */
package de.bmarwell.j9kwsolver.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class CaptchaReturnExtended extends CaptchaReturn {
	private String confirmText = null;
	
	private boolean phrase = false;
	private boolean numeric = false;
	private boolean math = false;
	
	private int minLength = 0;
	private int maxLength = 0;
	private boolean confirmedCaptcha = false;
	
	private int width = 0;
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
