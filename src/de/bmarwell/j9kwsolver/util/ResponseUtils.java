/**
 * Copyright (c) 2013, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
 */
package de.bmarwell.j9kwsolver.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.bmarwell.j9kwsolver.response.CaptchaReturn;
import de.bmarwell.j9kwsolver.response.CaptchaReturnExtended;
import de.bmarwell.j9kwsolver.response.CaptchaSolutionResponse;

/**
 * Utility methods for parsing responses from the server.
 * @author Benjamin Marwell
 *
 */
public final class ResponseUtils {
	/**
	 * The minimum length for the extended answer (for validating).
	 */
	private static final int EXTENDED_ANSWER_MINLENGTH = 11;
	/**
	 * The logger for this class.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(ResponseUtils.class);
	
	/**
	 * Empty private constructor for utility class.
	 */
	private ResponseUtils() { }
	
	/**
	 * @param response The response sent by the server.
	 * @return null if response is null or empty, else a HashMap.
	 */
	public static Map<String, Integer> stringResponseToIntMap(
			final String response) {
		Map<String, Integer> result = new HashMap<String, Integer>();
		
		if (StringUtils.isEmpty(response)) {
			/* 
			 * Response should be like so:
			 * worker=15|avg24h=12s|avg1h=12s|avg15m=13s|avg5m=13s|inwork=8|
			 *     queue=0|queue1=0|queue2=0|workermouse=13|
			 *     workerconfirm=14|workertext=13
			 */
			return null;
		}
		
		List<String> serverstatelist = Arrays.asList(StringUtils.split(response, '|'));
		
		/* Iterate each item in response */
		for (String item : serverstatelist) {
			String[] keyValue = StringUtils.split(item, '=');
			int value = 0;
			
			if (keyValue.length != 2) {
				LOG.warn("Key-Value splitting on '=' doesn't return 2 items: {}.", item);
				continue;
			}
			
			String key = keyValue[0];
			String textvalue = keyValue[1];
			textvalue = StringUtils.removeEnd(textvalue, "s");
			
			if (!NumberUtils.isDigits(textvalue)) {
				LOG.warn("Key-Value where value is non-numeric: {}", item);
				continue;
			} else {
				value = NumberUtils.toInt(textvalue);
			}
			
			result.put(key, value);
		}
		
		return result;
	}
	
	public static Map<String, String> stringResponseToStringMap(final String response) {
		// TODO: mock
		return null;
	}

	/**
	 * @param response the response sent by the server.
	 * @return the response of the server for solving a captcha.
	 */
	public static CaptchaSolutionResponse captchaSolveToCaptchaSolutionResponse(
			final String response) {
		CaptchaSolutionResponse csr = new CaptchaSolutionResponse();
		csr.setAccepted(false);
		csr.setCredits(0);
		
		if (StringUtils.isEmpty(response)) {
			RequestToURI.LOG.debug("Content ist leer!");
			
			return csr;
		}
		
		/*
		 * Possible outcome:
		 * NOT OK
		 * OK
		 * OK|8
		 */
		if (StringUtils.containsIgnoreCase(response, "not")) {
			csr.setAccepted(false);
		} else if (StringUtils.containsIgnoreCase(response, "ok")) {
			csr.setAccepted(true);
			csr.setCredits(parseCredits(response));
		} else {
			RequestToURI.LOG.error("Unknown response for solve request! {}.", response);
			csr.setAccepted(false);
			csr.setCredits(0);
		}
		
		return csr;
	}

	/**
	 * @param response the response sent by the server.
	 * @return the number of credits assigned by the server for solving.
	 */
	private static int parseCredits(final String response) {
		int credits = 0;
		
		if (StringUtils.isEmpty(response)) {
			return credits;
		}
		
		String[] splitresponse = StringUtils.splitPreserveAllTokens(response, '|');
		
		if (splitresponse.length != 2) {
			LOG.error("Response doesn't contain two items: {}.", 
					ToStringBuilder.reflectionToString(splitresponse));
			return credits;
		}
		
		// TODO: Enum for numbered field.
		String stringCredits = splitresponse[1];
		
		if (NumberUtils.isDigits(stringCredits)) {
			credits = NumberUtils.toInt(stringCredits, 0);
			LOG.debug("Found reward: {} credits.", credits);
		}
		
		return credits;
	}

	/**
	 * Parses the content from the response, if any.
	 * @param response the response sent by the server.
	 * @return a CaptchaReturn object if captcha was offered, otherwise null.
	 */
	public static CaptchaReturn captchaGetResponseToCaptchaReturn(
			final String response) {
		CaptchaReturn cr = null;
		
		if (StringUtils.isEmpty(response)) {
			RequestToURI.LOG.debug("Content ist leer!");
			
			return cr;
		}
		
		/* converting answer to object */
		if (StringUtils.containsIgnoreCase(response, "NO CAPTCHA")) {
			/* No captcha available */
			cr = null;
			RequestToURI.LOG.debug("No captcha available atm: {}.", response);
		} else if (StringUtils.contains(response, "phrase")) {
			/* Extended Answer */
			CaptchaReturnExtended cre = ResponseUtils.getExtendedFromResponse(response);
			RequestToURI.LOG.debug("CRE: {}.", cre);
			cr = cre;
		} else {
			/* 
			 * simple response contains only digits or few extra information
			 * INT or INT|mouse or INT|confirm
			 */
			RequestToURI.LOG.debug("Simple response: {}.", response);
			String[] splitresponse = StringUtils.split(response, '|');
			
			if (splitresponse.length < 1) {
				RequestToURI.LOG.warn("Simple response doesn't contain enough items");
				return cr;
			}
			
			if (!NumberUtils.isDigits(splitresponse[0])) {
				RequestToURI.LOG.error("Response's first item isn't a captcha id."
						+ " Found {} instead.", splitresponse[0]);
				return cr;
			}
			
			cr = new CaptchaReturn();
			cr.setCaptchaID(splitresponse[0]);
			// TODO: add items
		}
		
		return cr;
	}

	/**
	 * Parses the response for fields to set.
	 * @param response the response sent by the server.
	 * @return the CaptchaReturn Object with
	 * information about the captcha assigned.
	 */
	private static CaptchaReturnExtended getExtendedFromResponse(
			final String response) {
		/* 
		 * Extended response contains phrase keyword
		 * ID|text|confirm|antwort|mouse=0|phrase=0|
		 *     numeric=0|math=0|min_len=1|max_len=20|confirm=1|w|h|
		 * e.g.
		 * 11837102|text|||mouse=0|phrase=1|numeric=0|math=0|min_len=5|
		 *     max_len=0|confirm=0|300|57|userstart=1387447122|
		 *     startdate=1387447119|serverdate=1387447122|maxtimeout=35
		 */
		RequestToURI.LOG.debug("Extended response: {}.", response);
		String[] splitresponse = StringUtils.splitPreserveAllTokens(response, '|');
		
		RequestToURI.LOG.debug("Splitresponse: {}.", 
				ToStringBuilder.reflectionToString(
						splitresponse, 
						ToStringStyle.MULTI_LINE_STYLE)
		);
		
		/* Check item count */
		if (splitresponse.length < EXTENDED_ANSWER_MINLENGTH) {
			RequestToURI.LOG.warn("Extended response doesn't contain enough items");
			return null;
		}
		
		/* check first item is digits */
		if (!NumberUtils.isDigits(splitresponse[0])) {
			RequestToURI.LOG.error("Response's first item isn't a captcha id."
					+ " Found {} instead.", splitresponse[0]);
			return null;
		}
		
		/* Now create captcha extended item and fill it */
		CaptchaReturnExtended cre = new CaptchaReturnExtended();
		cre.setCaptchaID(splitresponse[CaptchaReturn.Field.ID.getPosition()]);
		
		/* if text returned, set text */
		if (StringUtils.equals(splitresponse[CaptchaReturn.Field.TEXT.getPosition()], "text")) {
			RequestToURI.LOG.debug("Setting text captcha.");
			cre.setText(true);
		}
		
		/* Just confirm? */
		if (StringUtils.equals(splitresponse[CaptchaReturn.Field.CONFIRM.getPosition()], "text")) {
			cre.setConfirm(true);
		}
		
		/* Has solved text */
		if (StringUtils.isNotEmpty(splitresponse[CaptchaReturn.Field.CONFIRMTEXT.getPosition()])) {
			cre.setConfirmText(splitresponse[CaptchaReturn.Field.CONFIRMTEXT.getPosition()]);
		}
		
		/* Mouse event? */
		if (StringUtils.equals(splitresponse[CaptchaReturn.Field.MOUSE.getPosition()], "mouse=1")) {
			cre.setMouse(true);
		}
		
		// TODO: Add items
		
		return cre;
	}
}
