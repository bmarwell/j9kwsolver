/**
 * Copyright (c) 2010-2012, Benjamin Marwell.  This file is
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
import de.bmarwell.j9kwsolver.response.ServerStatus;
import de.bmarwell.j9kwsolver.service.PropertyService;
import de.bmarwell.j9kwsolver.util.HttpConnectorFactory;

/**
 * @author Benjamin Marwell
 *
 */
public class J9kwSolver {
	private static final Logger log = LoggerFactory.getLogger(J9kwSolver.class);
	
	/**
	 * API call to J9kwCaptchaAPI.getNewCaptcha()
	 * @return
	 */
	private static Captcha getCaptcha() {
		Captcha captcha = null;
		J9kwCaptchaAPI jca = J9kwCaptchaAPI.getInstance();
		Future<Captcha> maybeResult = jca.getNewCaptcha(true);
		
		try {
			captcha = maybeResult.get();
		} catch (InterruptedException e) {
			log.error("Interrupted?!", e);
		} catch (ExecutionException e) {
			log.error("Could not execute?!", e);
		}
		
		return captcha;
	}
	
	/**
	 * Easy pause for testing.
	 */
	public static void dosleep() {
		try {
			Thread.sleep(2500);
		} catch (InterruptedException e) {
		}
	}
	
	/**
	 * Call to J9kwServerAPI.getServerStatus().
	 * @see {@link J9kwServerAPI#getServerStatus()}
	 */
	public static ServerStatus getServerStatus() {
		J9kwServerAPI sa = J9kwServerAPI.getInstance();
		ServerStatus ss = sa.getServerStatus();
		
		log.debug("ServerStatus: {}.", ss);
		
		assert ss != null : "ServerStatus could not be retrieved.";
		
		return ss;
	}
	
	/**
	 * Call to J9kwUserAPI.getBalance().
	 * @See {@link J9kwUserAPI#getBalance()}
	 */
	public static int getBalance() {
		J9kwUserAPI ua = J9kwUserAPI.getInstance();
		
		int balance = ua.getBalance();
		log.debug("Balance: {} credits.", balance);
		
		assert balance != 0 : "FIXME: Balance probably not detected.";
		
		return balance;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Captcha captcha = null;
		String apiKey = null;
		apiKey = PropertyService.getProperty("apikey");

		assert apiKey != null : "apiKey konnte nicht gelesen werden.";
		assert apiKey != "" : "apiKey konnte nicht gelesen werden.";
		assert apiKey.length() > 8 : "apiKey ist zu kurz.";
		
//		while (captcha == null) {
			captcha = getCaptcha();
//		}
		if (captcha != null) {
			log.debug("Captcha received!");
		} else {
			log.debug("No captcha received.");
		}
		
		getServerStatus();
		getBalance();
		
		// XXX: Services should shutdown automatically. 
		HttpConnectorFactory.shutdownConnector();
		J9kwCaptchaAPI.getInstance().shutdownExecutor();
		
		Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
		log.debug("Threads running: {}.", threadSet);
	}
	

}
