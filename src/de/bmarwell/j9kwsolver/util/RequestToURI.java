/**
 * Copyright (c) 2010-2012, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
 */
package de.bmarwell.j9kwsolver.util;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.bmarwell.j9kwsolver.request.CaptchaGet;
import de.bmarwell.j9kwsolver.request.CaptchaNewOk;
import de.bmarwell.j9kwsolver.request.CaptchaShow;
import de.bmarwell.j9kwsolver.request.CaptchaSolve;
import de.bmarwell.j9kwsolver.request.ServerCheck;
import de.bmarwell.j9kwsolver.request.UserBalance;

/**
 * @author Benjamin Marwell
 * This class will build URIs based on the request objects.
 */
public class RequestToURI {
	static final Logger log = LoggerFactory.getLogger(RequestToURI.class); 

	public static URI ServerStatusToURI(ServerCheck sc) {
		URI uri = null;
		
		URI apiURI = StringToURI(sc.getUrl());
		URIBuilder builder = new URIBuilder(apiURI)
			.addParameter("action", sc.getAction())
			;
		
		try {
			uri = builder.build();
		} catch (URISyntaxException e) {
			log.error("Konnte URI nicht erstellen!", e);
		}
		
		return uri;
	}
	
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
			.addParameter("withok", BooleanUtils10.toIntegerString(cg.isWithok()))
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

	/**
	 * @param ub
	 * @return
	 */
	public static URI UserBalanceToURI(UserBalance ub) {
		URI uri = null;

		if (ub == null) {
			return null;
		}

		URI apiURI = StringToURI(ub.getUrl());

		URIBuilder builder = new URIBuilder(apiURI)
		.addParameter("action", ub.getAction())
		.addParameter("apikey", ub.getApikey())
		;

		try {
			uri = builder.build();
		} catch (URISyntaxException e) {
			log.error("Konnte URI nicht erstellen!", e);
		}

		return uri;
	}

	public static URI captchaShowToURI(CaptchaShow cs) {
		URI uri = null;

		if (cs == null) {
			return null;
		}
		
		URI apiURI = StringToURI(cs.getUrl());
		
		URIBuilder builder = new URIBuilder(apiURI)
			.addParameter("id", cs.getId())
			.addParameter("action", cs.getAction())
			.addParameter("apikey", cs.getApikey())
			.addParameter("source", cs.getSource())
			.addParameter("debug", BooleanUtils10.toIntegerString(cs.isDebug()))
			.addParameter("base64", BooleanUtils10.toIntegerString(cs.isBase64()))
			;

		try {
			uri = builder.build();
		} catch (URISyntaxException e) {
			log.error("Konnte URI nicht erstellen!", e);
		}

		return uri;
	}

	/**
	 * @param solve
	 * @return
	 */
	public static URI captchaSolveToURI(CaptchaSolve solve) {
		URI uri = null;

		if (solve == null) {
			return null;
		}
		
		URI apiURI = StringToURI(solve.getUrl());
		
		URIBuilder builder = new URIBuilder(apiURI)
			.addParameter("id", solve.getId())
			.addParameter("action", solve.getAction())
			.addParameter("apikey", solve.getApikey())
			.addParameter("source", solve.getSource())
			.addParameter("debug", BooleanUtils10.toIntegerString(solve.isDebug()))
			.addParameter("captcha", solve.getCaptcha())
			.addParameter("extended", BooleanUtils10.toIntegerString(solve.isExtended()))
			;

		try {
			uri = builder.build();
		} catch (URISyntaxException e) {
			log.error("Konnte URI nicht erstellen!", e);
		}

		return uri;
	}
}
