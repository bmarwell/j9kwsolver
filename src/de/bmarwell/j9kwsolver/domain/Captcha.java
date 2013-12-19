/**
 * Copyright (c) 2010-2012, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
 */
package de.bmarwell.j9kwsolver.domain;

import java.awt.image.BufferedImage;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Saves a captcha with its informations.
 * @author bmarwell
 *
 */
public class Captcha {

	private String id;
	private String confirmtext = null;
	private boolean mouse;
	private boolean confirm;
	private BufferedImage image;
	
	
	
	public String getConfirmtext() {
		return confirmtext;
	}
	public void setConfirmtext(String confirmtext) {
		this.confirmtext = confirmtext;
	}
	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public void setConfirm(boolean confirm) {
		this.confirm = confirm;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}
