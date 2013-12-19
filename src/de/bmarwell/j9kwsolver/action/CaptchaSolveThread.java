/**
 * Copyright (c) 2010-2012, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
 */
package de.bmarwell.j9kwsolver.action;

import java.util.concurrent.Callable;

import de.bmarwell.j9kwsolver.domain.CaptchaSolution;
import de.bmarwell.j9kwsolver.response.CaptchaSolutionResponse;

/**
 * @author Benjamin Marwell
 *
 */
public class CaptchaSolveThread implements Callable<CaptchaSolutionResponse> {

	private CaptchaSolution solution = null;
	
	public CaptchaSolution getSolution() {
		return solution;
	}

	public void setSolution(CaptchaSolution solution) {
		this.solution = solution;
	}

	/* (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public CaptchaSolutionResponse call() throws Exception {
		CaptchaSolutionResponse solutionResponse = new CaptchaSolutionResponse();
		
		return solutionResponse;
	}

}
