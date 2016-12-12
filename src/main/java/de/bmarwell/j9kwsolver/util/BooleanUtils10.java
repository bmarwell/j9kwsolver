/**
 * Copyright (c) 2013, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
 */
package de.bmarwell.j9kwsolver.util;

import org.apache.commons.lang3.BooleanUtils;

/**
 * @author Benjamin Marwell
 * Some Boolean Utils.
 */
public final class BooleanUtils10 {
	/**
	 * Empty private default constructor for utility class BooleanUtils10.
	 */
	private BooleanUtils10() { }
	
	/**
	 * Returns a textual representation of 1 for true or 0 for false.
	 * @param bool - a boolean.
	 * @return either 0 or 1.
	 */
	public static String toIntegerString(final boolean bool) {
		int boolint = BooleanUtils.toInteger(bool);
		
		return Integer.toString(boolint);
	}
}
