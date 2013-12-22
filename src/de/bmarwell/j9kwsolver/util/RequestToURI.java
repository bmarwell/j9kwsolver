/**
 * Copyright (c) 2013, Benjamin Marwell.  This file is
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
 * This class will build URIs based on the request objects.
 * @author Benjamin Marwell
 */
public final class RequestToURI {
	/**
	 * The logger instance for this class.
	 */
	static final Logger LOG = LoggerFactory.getLogger(RequestToURI.class); 
	
	/**
	 * Empty private default constructor for utility class RequestToURI.
	 */
	private RequestToURI() { }

	/**
	 * Returns the URI for requesting the server status.
	 * @param sc the URI for requesting the server status.
	 * @return the URI for requesting the server status.
	 */
	public static URI serverStatusToURI(final ServerCheck sc) {
		URI uri = null;
		
		URI apiURI = stringToURI(sc.getUrl());
		URIBuilder builder = new URIBuilder(apiURI)
			.addParameter("action", sc.getAction())
			;
		
		try {
			uri = builder.build();
		} catch (URISyntaxException e) {
			LOG.error("Konnte URI nicht erstellen!", e);
		}
		
		return uri;
	}
	
	/**
	 * The CaptchaGet-Request URI Builder.
	 * @param cg The Get request for the new captcha.
	 * @return the URI for the API request.
	 */
	public static URI captchaGetToURI(final CaptchaGet cg) {
		URI uri = null;
		
		URI apiURI = stringToURI(cg.getUrl());
		
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
			LOG.error("Konnte URI nicht erstellen!", e);
		}
		
		return uri;
	}
	
	/**
	 * Sends Accept to the 9kw Captcha Service.
	 * @param cno the CaptchaNewOk Object for API request.
	 * @return the URI for the API request.
	 */
	public static URI captchaNewOkToURI(final CaptchaNewOk cno) {
		URI uri = null;
		
		URI apiURI = stringToURI(cno.getUrl());
		
		URIBuilder builder = new URIBuilder(apiURI)
			.addParameter("action", cno.getAction())
			.addParameter("apikey", cno.getApikey())
			.addParameter("source", cno.getSource())
			;
		
		try {
			uri = builder.build();
		} catch (URISyntaxException e) {
			LOG.error("Konnte URI nicht erstellen!", e);
		}
		
		return uri;
	}

	/**
	 * A generic String To URI-function, ignoring throwables.
	 * @param uristring - a uri in String representation.
	 * @return null or the String as URI.
	 */
	public static URI stringToURI(final String uristring) {
		URI uri = null;
		
		try {
			uri = new URI(uristring);
		} catch (URISyntaxException e) {
			LOG.error("Konnte keine URI bilden!", e);
		}
		
		return uri;
	}

	/**
	 * @param ub - User Balance object.
	 * @return API-URIor null if ub-object is invalid.
	 */
	public static URI userBalanceToURI(final UserBalance ub) {
		URI uri = null;

		if (ub == null) {
			return null;
		}

		URI apiURI = stringToURI(ub.getUrl());

		URIBuilder builder = new URIBuilder(apiURI)
		.addParameter("action", ub.getAction())
		.addParameter("apikey", ub.getApikey())
		;

		try {
			uri = builder.build();
		} catch (URISyntaxException e) {
			LOG.error("Konnte URI nicht erstellen!", e);
		}

		return uri;
	}

	/**
	 * Converts a request object for retrieving the captcha image
	 * to an URI for 9kw API.
	 * @param cs the CaptchaShow request.
	 * @return the URI for the API request.
	 */
	public static URI captchaShowToURI(final CaptchaShow cs) {
		URI uri = null;

		if (cs == null) {
			return null;
		}

		URI apiURI = stringToURI(cs.getUrl());

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
			LOG.error("Konnte URI nicht erstellen!", e);
		}

		return uri;
	}

	/**
	 * @param solve the solve request which should be send to the server.
	 * @return URI object to call for solving, or null if URI could 
	 * not be built.
	 */
	public static URI captchaSolveToURI(final CaptchaSolve solve) {
		URI uri = null;

		if (solve == null) {
			return null;
		}
		
		URI apiURI = stringToURI(solve.getUrl());
		
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
			LOG.error("Konnte URI nicht erstellen!", e);
		}

		return uri;
	}
}
