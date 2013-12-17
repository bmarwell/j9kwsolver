/**
 * Copyright (c) 2010-2012, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
 */
package de.bmarwell.j9kwsolver.util;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.bmarwell.j9kwsolver.request.CaptchaGet;
import de.bmarwell.j9kwsolver.request.CaptchaNewOk;
import de.bmarwell.j9kwsolver.request.CaptchaReturn;
import de.bmarwell.j9kwsolver.request.CaptchaReturnExtended;

/**
 * @author Benjamin Marwell
 * This class will build URIs based on the request objects.
 */
public class RequestToURI {
	private static final Logger log = LoggerFactory.getLogger(RequestToURI.class); 

	/**
	 * The CaptchaGet-Request URI Builder.
	 * @param cg
	 * @return
	 */
	public static URI captchaGetToURI(CaptchaGet cg) {
		URI uri = null;
		
		URI apiURI = StringToURI(cg.getUrl());
		
		URIBuilder builder = new URIBuilder(apiURI)
			.addParameter("debug", BooleanUtils10.toIntegerString(cg.isDebug()))
			.addParameter("action", cg.getAction())
			.addParameter("apikey", cg.getApikey())
			.addParameter("source", cg.getSource())
			.addParameter("nocaptcha", BooleanUtils10.toIntegerString(cg.isNocaptcha()))
			.addParameter("extended", BooleanUtils10.toIntegerString(cg.isExtended()))
			.addParameter("text", cg.getText().getYesNoString())
			;
		
		try {
			uri = builder.build();
		} catch (URISyntaxException e) {
			log.error("Konnte URI nicht erstellen!", e);
		}
		
		return uri;
	}
	
	/**
	 * Sends Accept to the 9kw Captcha Service.
	 * @param cno
	 * @return
	 */
	public static URI captchaNewOkToURI(CaptchaNewOk cno) {
		URI uri = null;
		
		URI apiURI = StringToURI(cno.getUrl());
		
		URIBuilder builder = new URIBuilder(apiURI)
			.addParameter("action", cno.getAction())
			.addParameter("apikey", cno.getApikey())
			.addParameter("source", cno.getSource())
			;
		
		try {
			uri = builder.build();
		} catch (URISyntaxException e) {
			log.error("Konnte URI nicht erstellen!", e);
		}
		
		return uri;
	}

	/**
	 * Parses the content from the response, if any.
	 * @param response
	 * @return
	 */
	public static CaptchaReturn captchaGetResponseToCaptchaReturn(
			CloseableHttpResponse response) {
		CaptchaReturn cr = null;
		
		if (response == null) {
			return cr;
		}
		
		HttpEntity he = response.getEntity();
		
		if (he == null) {
			log.debug("Entity ist leer!");
			
			return cr;
		}
		
		String requestContent = null;
		StringWriter writer = new StringWriter();

		try {
			IOUtils.copy(
					he.getContent(), 
					writer, 
					"UTF-8");
			he.getContent().close();
			requestContent = writer.toString();
		} catch (IllegalStateException | IOException e) {
			log.error("Konnte Body nicht in String umwandeln.", e);
			
			return cr;
		}
		
		if (StringUtils.isEmpty(requestContent)) {
			log.debug("Content ist leer!");
			
			return cr;
		}
		
		/* Nun geht das umwandeln */
		if (StringUtils.contains(requestContent, "phrase")) {
			cr = new CaptchaReturnExtended();
		} else {
			cr = new CaptchaReturn();
		}
		
		log.debug("Body: {}.", requestContent);
		
		return cr;
	}
	
	/**
	 * A generic String To URI-function, ignoring throwables.
	 * @param uristring - a uri in String representation.
	 * @return null or the String as URI.
	 */
	public static URI StringToURI(String uristring) {
		URI uri = null;
		
		try {
			uri = new URI(uristring);
		} catch (URISyntaxException e) {
			log.error("Konnte keine URI bilden!", e);
		}
		
		return uri;
	}
}
