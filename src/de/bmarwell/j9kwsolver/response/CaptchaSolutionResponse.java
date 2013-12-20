/**
 * Copyright (c) 2013, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
 */
package de.bmarwell.j9kwsolver.response;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Benjamin Marwell
 *
 */
public class CaptchaSolutionResponse {
	private boolean accepted = false;
	private int credits;
	
	public boolean isAccepted() {
		return accepted;
	}
	public void setAccepted(final boolean pAccepted) {
		this.accepted = pAccepted;
	}
	
	public int getCredits() {
		return credits;
	}
	public void setCredits(final int pCredits) {
		this.credits = pCredits;
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}
