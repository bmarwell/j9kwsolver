package de.bmarwell.j9kwsolver.util;

import org.apache.commons.lang3.BooleanUtils;

public class BooleanUtils10 {
	public static String toIntegerString(boolean bool) {
		int boolint = BooleanUtils.toInteger(bool);
		
		return Integer.toString(boolint);
	}
}
