package de.bmarwell.j9kwsolver.action;

import java.net.URI;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.bmarwell.j9kwsolver.domain.Captcha;
import de.bmarwell.j9kwsolver.request.CaptchaGet;
import de.bmarwell.j9kwsolver.request.CaptchaNewOk;
import de.bmarwell.j9kwsolver.request.CaptchaReturn;
import de.bmarwell.j9kwsolver.service.PropertyService;
import de.bmarwell.j9kwsolver.util.HttpConnectorFactory;
import de.bmarwell.j9kwsolver.util.RequestToURI;

public class CaptchaGetThread implements Callable<Captcha> {
	private static final Logger log = LoggerFactory.getLogger(CaptchaGetThread.class);

	private CaptchaReturn getRequest() {
		String responseBody = null;
		CaptchaReturn cr = null;
		
		CaptchaGet cg = new CaptchaGet();
		cg.setDebug(true);
		cg.setApikey(PropertyService.getProperty("apikey"));
		cg.setExtended(true);
		cg.setSource(PropertyService.getProperty("toolname"));
		
		URI uri = RequestToURI.captchaGetToURI(cg);
		log.debug("Requesting URI: {}.", uri);
		responseBody = HttpConnectorFactory.getBodyFromRequest(uri);
		log.debug("Response: {}.", responseBody);
		
		cr = RequestToURI.captchaGetResponseToCaptchaReturn(responseBody);
		
		return cr;
	}
	
	private boolean doAccept() {
		String responseBody = null;
		boolean accepted = false;
		
		CaptchaNewOk cno = new CaptchaNewOk();
		cno.setApikey(PropertyService.getProperty("apikey"));
		cno.setSource(PropertyService.getProperty("toolname"));
		
		URI uri = RequestToURI.captchaNewOkToURI(cno);
		log.debug("Requesting URI: {}.", uri);
		responseBody = HttpConnectorFactory.getBodyFromRequest(uri);
		
		/*
		 * Check if OK came
		 */
		log.debug("Server accept response: {}.", responseBody);
		if(StringUtils.isEmpty(responseBody)) {
			/* Empty answer: probably server error */
			accepted = false;
		} else if (StringUtils.containsIgnoreCase(responseBody, "ok")) {
			/* contains ok: good. */
			accepted = true;
		} else {
			/* does not contain ok: not so good. */
			accepted = false;
		}
		
		return accepted;
	}

	@Override
	public Captcha call() throws Exception {
		Captcha captcha = null;
		boolean accepted = false;
		CaptchaReturn cr = null;
		
		if (Thread.currentThread().isInterrupted()) {
			throw new InterruptedException(); 
		}
		
		/* 
		 * Step 1:
		 * See if Captcha is available 
		 */
		cr = getRequest();
		
		if (cr == null) {
			return captcha;
		}
		
		if (Thread.currentThread().isInterrupted()) {
			throw new InterruptedException(); 
		}
		
		/*
		 * Step 2:
		 * Send "Accepting Captcha". 
		 */
		log.debug("CaptchaID {} gotten! Now accepting.", cr.getCaptchaID());
		
		accepted = doAccept();
		
		if (!accepted) {
			log.warn("Server didn't leave us Captcha {}.", cr.getCaptchaID());
		} else {
			log.debug("Server assigned Captcha {} to us.", cr.getCaptchaID());
		}
		
		/*
		 * Step 3:
		 * Get Captcha Data.
		 */
		//TODO: Get actual Captcha.

		return captcha;
	}

}
