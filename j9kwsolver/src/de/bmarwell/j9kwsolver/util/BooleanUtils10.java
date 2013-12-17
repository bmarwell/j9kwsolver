/**
 * Copyright (c) 2010-2012, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
 */
package de.bmarwell.j9kwsolver.util;

import org.apache.commons.lang3.BooleanUtils;

public class BooleanUtils10 {
	public static String toIntegerString(boolean bool) {
		int boolint = BooleanUtils.toInteger(bool);
		
		return Integer.toString(boolint);
	}
}
