/**
 * Copyright (c) 2010-2012, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
 */
package de.bmarwell.j9kwsolver.domain;

import java.awt.image.BufferedImage;

/**
 * Saves a captcha with its informations.
 * @author bmarwell
 *
 */
public class Captcha {

	private String id;
	private String source;
	private boolean mouse;
	private boolean confirm;
	private BufferedImage image;
	
	
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
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
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
	
	
}
