/**
 * Copyright (c) 2010-2012, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
 */
package de.bmarwell.j9kwsolver.domain;

public enum TrueFalse {
	TRUE(1),
	FALSE(0);
	
	private int code;
	
	private TrueFalse(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return this.code;
	}
}
