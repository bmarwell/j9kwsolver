/**
 * Copyright (c) 2013, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
 */
package de.bmarwell.j9kwsolver.domain;

/**
 * @author Benjamin Marwell
 *
 */
public enum YesNo {
	YES("yes"),
	NO("no");
	
	private String yesNoString;
	
	private YesNo(final String pYesNoString) {
		this.yesNoString = pYesNoString;
	}
	
	public String getYesNoString() {
		return this.yesNoString;
	}
	
}
