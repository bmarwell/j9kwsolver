/**
 * Copyright (c) 2013, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
 */
package de.bmarwell.j9kwsolver.domain;

import java.awt.image.BufferedImage;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Saves a captcha with its informations.
 * @author Benjamin Marwell
 *
 */
public class Captcha {

	/**
	 * The id of the 9kw captcha.
	 */
	private String id;
	/**
	 * The text to be confirmed.
	 */
	private String confirmtext = null;
	/**
	 * true, if this is a click/mouse captcha.
	 */
	private boolean mouse;
	/**
	 * true, if this is a confirm/yesno captcha.
	 */
	private boolean confirm;
	/**
	 * The image of the captcha.
	 */
	private BufferedImage image;
	
	
	
	/**
	 * Returns the text to be confirmed.
	 * @return the text to be confirmed.
	 */
	public String getConfirmtext() {
		return confirmtext;
	}
	
	/**
	 * Sets the text to be confirmed.
	 * @param pConfirmtext the text to be confirmed.
	 */
	public void setConfirmtext(final String pConfirmtext) {
		this.confirmtext = pConfirmtext;
	}
	
	/**
	 * Returns the image of the captcha.
	 * @return the image of the captcha.
	 */
	public BufferedImage getImage() {
		return image;
	}
	
	/**
	 * Sets the image of the captcha.
	 * @param pImage the image of the captcha.
	 */
	public void setImage(final BufferedImage pImage) {
		this.image = pImage;
	}
	
	/**
	 * returns the id of the captcha.
	 * @return the id of the captcha.
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Sets the ID of the captcha.
	 * @param pId the id of the captcha.
	 */
	public void setId(final String pId) {
		this.id = pId;
	}
	
	/**
	 * Returns whether this is a click captcha.
	 * @return true if this is a click captcha.
	 */
	public boolean isMouse() {
		return mouse;
	}
	
	/**
	 * Sets this to click captcha.
	 * @param pMouse set to true if this is a click captcha. 
	 */
	public void setMouse(final boolean pMouse) {
		this.mouse = pMouse;
	}
	
	/**
	 * returns true if this is a confirm captcha.
	 * @return true if this is a confirm captcha.
	 */
	public boolean isConfirm() {
		return confirm;
	}
	
	/**
	 * Set to true if this is a confirm captcha.
	 * @param pConfirm true if this is a confirm captcha.
	 */
	public void setConfirm(final boolean pConfirm) {
		this.confirm = pConfirm;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}
