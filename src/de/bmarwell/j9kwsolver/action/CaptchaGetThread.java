/**
 * Copyright (c) 2010-2012, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
 */
package de.bmarwell.j9kwsolver.action;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.Callable;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.bmarwell.j9kwsolver.domain.Captcha;
import de.bmarwell.j9kwsolver.request.CaptchaGet;
import de.bmarwell.j9kwsolver.request.CaptchaNewOk;
import de.bmarwell.j9kwsolver.request.CaptchaReturn;
import de.bmarwell.j9kwsolver.request.CaptchaShow;
import de.bmarwell.j9kwsolver.service.PropertyService;
import de.bmarwell.j9kwsolver.util.HttpConnectorFactory;
import de.bmarwell.j9kwsolver.util.RequestToURI;

/**
 * With this thread, the API will call for an image, auto-accept it and retrieves
 * its contents.
 * @author bmarwell
 */
public class CaptchaGetThread implements Callable<Captcha> {
	private static final Logger log = LoggerFactory.getLogger(CaptchaGetThread.class);

	/**
	 * Asks server for new captcha.
	 * @return
	 */
	private CaptchaReturn getRequest() {
		String responseBody = null;
		CaptchaReturn cr = null;
		
		CaptchaGet cg = new CaptchaGet();
		cg.setApikey(PropertyService.getProperty("apikey"));
		cg.setExtended(true);
		cg.setSource(PropertyService.getProperty("toolname"));
		
		if (PropertyService.getProperty("debug").equals("true")) {
			cg.setDebug(true);
		}
		
		URI uri = RequestToURI.captchaGetToURI(cg);
		log.debug("Requesting URI: {}.", uri);
		responseBody = HttpConnectorFactory.getBodyFromRequest(uri);
		log.debug("Response: {}.", responseBody);
		
		cr = RequestToURI.captchaGetResponseToCaptchaReturn(responseBody);
		
		return cr;
	}
	
	/**
	 * Sends an accept request to server.
	 * @return
	 */
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

	/**
	 * Gets the Captcha object containing the image.
	 * @param captchaId
	 * @return
	 */
	private Captcha getCaptcha(String captchaId) {
		Captcha captcha = null;
		String responseBody = null;
		
		CaptchaShow cs = new CaptchaShow();
		cs.setApikey(PropertyService.getProperty("apikey"));
		cs.setSource(PropertyService.getProperty("toolname"));
		cs.setBase64(true);
		cs.setId(captchaId);
		if (PropertyService.getProperty("debug").equals("true")) {
			cs.setDebug(true);
		}

		URI uri = RequestToURI.captchaShowToURI(cs);
		log.debug("Requesting URI: {}.", uri);
		responseBody = HttpConnectorFactory.getBodyFromRequest(uri);
		
		if (StringUtils.isEmpty(responseBody)) {
			log.debug("No return for captchaShow.");
			
			return captcha;
		}
		
		/* OK, captcha gotten. */
		captcha = new Captcha();
		captcha.setId(captchaId);
		
		try {
			byte[] imgdata = Base64.decodeBase64(responseBody);
			ByteArrayInputStream stream = new ByteArrayInputStream(imgdata);
			BufferedImage bi = ImageIO.read(stream);
			captcha.setImage(bi);
		} catch (IOException e) {
			log.error("Could not read image Stream!");
		}
		
		log.debug("Response - Bild: {}.", StringUtils.substring(responseBody, 0, 15));
		
		if (captcha.getImage() == null) {
			/* Check if we actually saved the image */
			log.warn("Image found, but could not be saved to object!");
			
			captcha = null;
		}
		
		return captcha;
	}
	
	/* (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 */
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
		captcha = getCaptcha(cr.getCaptchaID());
		
		return captcha;
	}

}
