/**
 * Copyright (c) 2010-2012, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
 */
package de.bmarwell.j9kwsolver.domain;

public enum YesNo {
	YES("yes"),
	NO("no");
	
	private String yesNoString;
	
	private YesNo(String yesNoString) {
		this.yesNoString = yesNoString;
	}
	
	public String getYesNoString() {
		return this.yesNoString;
	}
	
}
