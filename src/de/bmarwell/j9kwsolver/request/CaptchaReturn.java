/**
 * Copyright (c) 2010-2012, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
 */
package de.bmarwell.j9kwsolver.request;

public class CaptchaReturn {

	private String captchaID;
	private boolean text = false;
	private boolean mouse = false;
	private boolean confirm = false;
	
	public String getCaptchaID() {
		return captchaID;
	}
	public void setCaptchaID(String pCaptchaID) {
		this.captchaID = pCaptchaID;
	}
	
	
	public boolean isText() {
		return text;
	}
	public void setText(final boolean pText) {
		this.text = pText;
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
	
	public enum Field {
		// ID|text|confirm|antwort|mouse=0|phrase=0|numeric=0|math=0|min_len=1|max_len=20|confirm=1|w|h|
		// 11837102|text|||mouse=0|phrase=1|numeric=0|math=0|min_len=5|max_len=0|confirm=0|300|57|userstart=1387447122|startdate=1387447119|serverdate=1387447122|maxtimeout=35
		ID(0),
		TEXT(1),
		CONFIRM(2),
		CONFIRMTEXT(3),
		MOUSE(4),
		PHRASE(5),
		NUMERIC(6),
		MATH(7),
		MINLEN(8),
		MAXLEN(9),
		CONFIRMEDCAPTCHA(10),
		WIDTH(11),
		HEIGHT(12),
		USERSTARTTIME(13),
		CAPTCHASTARTTIME(14),
		SERVERSTARTTIME(15),
		MAXTIMEOUT(16)
		;
		
		private int position;
		
		private Field(final int pPosition) {
			this.position = pPosition;
		}
		
		public int getPosition() {
			return this.position;
		}
	}
	
}
