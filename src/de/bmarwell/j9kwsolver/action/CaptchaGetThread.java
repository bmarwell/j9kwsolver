/**
 * Copyright (c) 2013, Benjamin Marwell.  This file is
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
import de.bmarwell.j9kwsolver.request.CaptchaReturnExtended;
import de.bmarwell.j9kwsolver.request.CaptchaShow;
import de.bmarwell.j9kwsolver.service.PropertyService;
import de.bmarwell.j9kwsolver.util.HttpConnectorFactory;
import de.bmarwell.j9kwsolver.util.RequestToURI;
import de.bmarwell.j9kwsolver.util.ResponseUtils;

/**
 * With this thread, the API will call for an image, 
 * auto-accept it and retrieves its contents.
 * @author Benjamin Marwell
 */
public class CaptchaGetThread implements Callable<Captcha> {
	/**
	 * Logger for this class.
	 */
	private static final Logger LOG = 
			LoggerFactory.getLogger(CaptchaGetThread.class);

	/**
	 * Asks server for new captcha.
	 * @return CaptchaReturn or CaptchaReturnExtended if server assigned 
	 * a captcha. Otherwise null.
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
		LOG.debug("Requesting URI: {}.", uri);
		responseBody = HttpConnectorFactory.getBodyFromRequest(uri);
		LOG.debug("Response: {}.", responseBody);
		
		cr = ResponseUtils.captchaGetResponseToCaptchaReturn(responseBody);
		
		return cr;
	}
	
	/**
	 * Sends an accept request to server.
	 * @return true, if server recognized accept request. False otherwise.
	 */
	private boolean doAccept() {
		String responseBody = null;
		boolean accepted = false;
		
		CaptchaNewOk cno = new CaptchaNewOk();
		cno.setApikey(PropertyService.getProperty("apikey"));
		cno.setSource(PropertyService.getProperty("toolname"));
		
		URI uri = RequestToURI.captchaNewOkToURI(cno);
		LOG.debug("Requesting URI: {}.", uri);
		responseBody = HttpConnectorFactory.getBodyFromRequest(uri);
		
		/*
		 * Check if OK came
		 */
		LOG.debug("Server accept response: {}.", responseBody);
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
	 * @param cr the captcha return object.
	 * @return the Captcha received.
	 */
	private Captcha getCaptcha(final CaptchaReturn cr) {
		CaptchaReturnExtended cre = null;
		Captcha captcha = null;
		String responseBody = null;

		CaptchaShow cs = new CaptchaShow();
		cs.setApikey(PropertyService.getProperty("apikey"));
		cs.setSource(PropertyService.getProperty("toolname"));
		cs.setBase64(true);
		cs.setId(cr.getCaptchaID());
		if (PropertyService.getProperty("debug").equals("true")) {
			cs.setDebug(true);
		}

		URI uri = RequestToURI.captchaShowToURI(cs);
		LOG.debug("Requesting URI: {}.", uri);
		responseBody = HttpConnectorFactory.getBodyFromRequest(uri);

		if (StringUtils.isEmpty(responseBody)) {
			LOG.debug("No return for captchaShow.");

			return captcha;
		}

		/* OK, captcha gotten. */
		captcha = new Captcha();
		captcha.setId(cr.getCaptchaID());
		captcha.setConfirm(cr.isConfirm());
		captcha.setMouse(cr.isMouse());

		if (cr instanceof CaptchaReturnExtended) {
			cre = (CaptchaReturnExtended) cr;
			captcha.setConfirmtext(cre.getConfirmText());
		}

		// captcha.setConfirmtext();

		try {
			byte[] imgdata = Base64.decodeBase64(responseBody);
			ByteArrayInputStream stream = new ByteArrayInputStream(imgdata);
			BufferedImage bi = ImageIO.read(stream);
			captcha.setImage(bi);
		} catch (IOException e) {
			LOG.error("Could not read image Stream!");
		}

		if (captcha.getImage() == null) {
			/* Check if we actually saved the image */
			LOG.warn("Image found, but could not be saved to object!");
			
			return null;
		}
		
		return captcha;
	}
	
	/* (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public final Captcha call() throws Exception {
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
		if (Thread.currentThread().isInterrupted()) {
			throw new InterruptedException(); 
		}
		
		LOG.debug("CaptchaID {} gotten! Now accepting.", cr.getCaptchaID());
		
		accepted = doAccept();
		
		if (!accepted) {
			LOG.warn("Server didn't leave us Captcha {}.", cr.getCaptchaID());
			
			return null;
		} else {
			LOG.debug("Server assigned Captcha {} to us.", cr.getCaptchaID());
		}
		
		/*
		 * Step 3:
		 * Get Captcha Data.
		 */
		if (Thread.currentThread().isInterrupted()) {
			throw new InterruptedException(); 
		}
		
		captcha = getCaptcha(cr);
		
		return captcha;
	}

}
