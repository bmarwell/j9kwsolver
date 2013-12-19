/**
 * Copyright (c) 2010-2012, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
 */
package de.bmarwell.j9kwsolver.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class CaptchaReturnExtended extends CaptchaReturn {
	private String answerText = null;
	
	private boolean phrase = false;
	private boolean numeric = false;
	private boolean math = false;
	
	private int minLength = 0;
	private int maxLength = 0;
	private boolean confirmedCaptcha = false;
	
	private int width = 0;
	private int height = 0;
	
	public String getAnswerText() {
		return answerText;
	}

	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}

	public boolean isPhrase() {
		return phrase;
	}
	
	public void setPhrase(boolean phrase) {
		this.phrase = phrase;
	}
	public boolean isNumeric() {
		return numeric;
	}
	public void setNumeric(boolean numeric) {
		this.numeric = numeric;
	}
	public boolean isMath() {
		return math;
	}
	public void setMath(boolean math) {
		this.math = math;
	}
	public int getMinLength() {
		return minLength;
	}
	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}
	public int getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}
	public boolean isConfirmedCaptcha() {
		return confirmedCaptcha;
	}
	public void setConfirmedCaptcha(boolean confirmedCaptcha) {
		this.confirmedCaptcha = confirmedCaptcha;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	

}
