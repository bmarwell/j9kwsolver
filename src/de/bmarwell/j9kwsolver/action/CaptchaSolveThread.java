/**
 * Copyright (c) 2010-2012, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
 */
package de.bmarwell.j9kwsolver.action;

import java.net.URI;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.bmarwell.j9kwsolver.domain.CaptchaSolution;
import de.bmarwell.j9kwsolver.request.CaptchaSolve;
import de.bmarwell.j9kwsolver.response.CaptchaSolutionResponse;
import de.bmarwell.j9kwsolver.service.PropertyService;
import de.bmarwell.j9kwsolver.util.HttpConnectorFactory;
import de.bmarwell.j9kwsolver.util.RequestToURI;
import de.bmarwell.j9kwsolver.util.ResponseUtils;

/**
 * @author Benjamin Marwell
 *
 */
public class CaptchaSolveThread implements Callable<CaptchaSolutionResponse> {
	private static final Logger LOG = LoggerFactory.getLogger(CaptchaSolveThread.class);

	private CaptchaSolution solution = null;

	public CaptchaSolution getSolution() {
		return solution;
	}

	public void setSolution(final CaptchaSolution pSolution) {
		this.solution = pSolution;
	}
	
	private CaptchaSolutionResponse solveCaptcha() {
		String responseBody = null;

		CaptchaSolve solveRequest = new CaptchaSolve();
		solveRequest.setApikey(PropertyService.getProperty("apikey"));
		solveRequest.setExtended(true);
		solveRequest.setId(solution.getCaptcha().getId());
		
		/* set correct solution text */
		if (solution.getCaptcha().isConfirm()) {
			solveRequest.setCaptcha(solution.getConfirmCaptchaCorrect().getYesNoString());
		} else if (solution.getCaptcha().isMouse()) {
			solveRequest.setCaptcha(solution.getCoordinates());
		} else {
			solveRequest.setCaptcha(solution.getCaptchaText());
		}
		
		/* set debug if set */
		if (PropertyService.getProperty("debug").equals("true")) {
			solveRequest.setDebug(true);
		}

		URI uri = RequestToURI.captchaSolveToURI(solveRequest);
		LOG.debug("Requesting URI: {}.", uri);
		responseBody = HttpConnectorFactory.getBodyFromRequest(uri);
		LOG.debug("Response: {}.", responseBody);
		
		CaptchaSolutionResponse solutionResponse = null;
		solutionResponse = ResponseUtils.captchaSolveToCaptchaSolutionResponse(responseBody);
//		 = new CaptchaSolutionResponse();
		
		return solutionResponse;
	}

	/**
	 * Check if provided solution can be valid.
	 * @return
	 */
	private boolean isValidSolution() {
		if (solution == null) {
			/* cannot submit without ID */
			return false;
		}
		
		if (solution.getCaptcha() == null) {
			/* cannot submit without ID */
			return false;
		}
		
		if (StringUtils.isEmpty(solution.getCaptcha().getId())) {
			/* cannot submit without ID */
			return false;
		}

		/* all okay */
		return true;
	}

	/* (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public final CaptchaSolutionResponse call() throws Exception {
		CaptchaSolutionResponse solveCaptcha = null;

		if (!isValidSolution()) {
			LOG.debug("Solution cannot be valid at all!");
			return null;
		}

		if (Thread.currentThread().isInterrupted()) {
			throw new InterruptedException(); 
		}

		solveCaptcha = solveCaptcha();

		return solveCaptcha;
	}

}
