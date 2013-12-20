/**
 * Copyright (c) 2013, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
 */
package de.bmarwell.j9kwsolver.test;

import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.bmarwell.j9kwsolver.J9kwCaptchaAPI;
import de.bmarwell.j9kwsolver.J9kwServerAPI;
import de.bmarwell.j9kwsolver.J9kwUserAPI;
import de.bmarwell.j9kwsolver.domain.Captcha;
import de.bmarwell.j9kwsolver.domain.CaptchaSolution;
import de.bmarwell.j9kwsolver.response.CaptchaSolutionResponse;
import de.bmarwell.j9kwsolver.response.ServerStatus;
import de.bmarwell.j9kwsolver.service.PropertyService;
import de.bmarwell.j9kwsolver.util.HttpConnectorFactory;

/**
 * @author Benjamin Marwell
 *
 */
public class J9kwSolver {
	/**
	 * Minimal length for API key.
	 */
	private static final int APIKEY_MIN_LENGTH = 8;
	/**
	 * Thread sleep time for testing purposes.
	 */
	private static final int THEAD_SLEEP_TIME_MS = 2500;
	private static final Logger LOG = LoggerFactory.getLogger(J9kwSolver.class);
	
	/**
	 * API call to J9kwCaptchaAPI.getNewCaptcha().
	 * @return the Captcha provided by 9kw.
	 */
	private static Captcha getCaptcha() {
		Captcha captcha = null;
		J9kwCaptchaAPI jca = J9kwCaptchaAPI.getInstance();
		Future<Captcha> maybeResult = jca.getNewCaptcha(true);
		
		try {
			captcha = maybeResult.get();
			LOG.debug("Captcha: {}.", captcha);
		} catch (InterruptedException e) {
			LOG.error("Interrupted?!", e);
		} catch (ExecutionException e) {
			LOG.error("Could not execute?!", e);
		}
		
		return captcha;
	}
	
	/**
	 * Solves a Captcha, i.e. sending a solution to the server.
	 * @param captcha the Captcha which has been solved.
	 * @param solutionText the Text solution of this Captcha. Should be text
	 * for text captchas, Yes/No for Confirm captchas or coordinates for
	 * mouse/click captchas.
	 * @return true if server accepted the solution, otherwise false.
	 */
	private static boolean solveCaptcha(
			final Captcha captcha,
			final String solutionText) {
		CaptchaSolution solution = new CaptchaSolution();
		boolean accepted = false;
		solution.setCaptcha(captcha);
		solution.setCaptchaText(solutionText);

		J9kwCaptchaAPI jca = J9kwCaptchaAPI.getInstance();
		Future<CaptchaSolutionResponse> solveCaptcha = 
				jca.solveCaptcha(solution);
		
		try {
			CaptchaSolutionResponse acceptedSolution = solveCaptcha.get();
			LOG.debug(": {}.", acceptedSolution);
			accepted = acceptedSolution.isAccepted();
		} catch (InterruptedException e) {
			LOG.error("Interrupted?!", e);
		} catch (ExecutionException e) {
			LOG.error("Could not execute?!", e);
		}

		return accepted;
	}

	/**
	 * @param captcha the Captcha object to be solved.
	 * @return the user entered solution as String object.
	 */
	private static String getCaptchaSolution(final Captcha captcha) {
		/* Show image */
		J9kwShowImage display = new J9kwShowImage();
		String solution = display.show(captcha);

		return solution;
	}

	/**
	 * Easy pause for testing.
	 */
	public static void dosleep() {
		try {
			Thread.sleep(THEAD_SLEEP_TIME_MS);
		} catch (InterruptedException e) {
		}
	}
	
	/**
	 * Call to J9kwServerAPI.getServerStatus().
	 * @see {@link J9kwServerAPI#getServerStatus()}
	 * @return the Server status object or null, if unretrievable.
	 */
	public static ServerStatus getServerStatus() {
		J9kwServerAPI sa = J9kwServerAPI.getInstance();
		ServerStatus ss = sa.getServerStatus();
		
		LOG.debug("ServerStatus: {}.", ss);
		
		assert ss != null : "ServerStatus could not be retrieved.";
		
		return ss;
	}
	
	/**
	 * Call to J9kwUserAPI.getBalance().
	 * @See {@link J9kwUserAPI#getBalance()}
	 * @return balance as int or -1 if unretrievable.
	 */
	public static int getBalance() {
		J9kwUserAPI ua = J9kwUserAPI.getInstance();
		
		int balance = ua.getBalance();
		LOG.debug("Balance: {} credits.", balance);
		
		assert balance != -1 : "FIXME: Balance probably not detected.";
		
		return balance;
	}
	
	
	/**
	 * Retrieves the API Key.
	 */
	private static void testApiKey() {
		String apiKey = PropertyService.getProperty("apikey");
	
		assert apiKey != null : "apiKey konnte nicht gelesen werden.";
		assert apiKey != "" : "apiKey konnte nicht gelesen werden.";
		assert apiKey.length() > APIKEY_MIN_LENGTH : "apiKey ist zu kurz.";
	}

	/**
	 * @param args command line arguments.
	 */
	public static void main(final String[] args) {
		Captcha captcha = null;
		
		testApiKey();
		
		captcha = getCaptcha();

		if (captcha != null) {
			LOG.debug("Captcha received!");
		} else {
			LOG.debug("No captcha received.");
		}
		
		String solution = getCaptchaSolution(captcha);
		
		if (captcha != null) {
			solveCaptcha(captcha, solution);
		}
		
		getServerStatus();
		getBalance();
		
		// FIXME: Services should shutdown automatically. 
		HttpConnectorFactory.shutdownConnector();
		J9kwCaptchaAPI.getInstance().shutdownExecutor();
		
		Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
		LOG.debug("Threads running: {}.", threadSet);
	}
	

}
