/**
 * Copyright (c) 2013, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
 */
package de.bmarwell.j9kwsolver.domain;

/**
 * Boolean for true/false with 1/0 output.
 * @author Benjamin Marwell
 *
 */
public enum TrueFalse {
	TRUE(1),
	FALSE(0);
	
	private int code;
	
	private TrueFalse(final int pCode) {
		this.code = pCode;
	}
	
	public int getCode() {
		return this.code;
	}
}
