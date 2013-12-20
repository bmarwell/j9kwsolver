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

import de.bmarwell.j9kwsolver.request.CaptchaReturn;
import de.bmarwell.j9kwsolver.request.CaptchaReturnExtended;
import de.bmarwell.j9kwsolver.response.CaptchaSolutionResponse;

public class ResponseUtils {
	/**
	 * 
	 */
	private static final int EXTENDED_ANSWER_MINLENGTH = 11;
	private static Logger log = LoggerFactory.getLogger(ResponseUtils.class);
	/**
	 * @param response
	 * @return null if response is null or empty, else a HashMap.
	 */
	public static Map<String, Integer> StringResponseToIntMap(String response) {
		Map<String, Integer> result = new HashMap<String, Integer>();
		
		if (StringUtils.isEmpty(response)) {
			/* 
			 * Response should be like so:
			 * worker=15|avg24h=12s|avg1h=12s|avg15m=13s|avg5m=13s|inwork=8|queue=0|queue1=0|queue2=0|workermouse=13|workerconfirm=14|workertext=13
			 */
			return null;
		}
		
		List<String> serverstatelist = Arrays.asList(StringUtils.split(response, '|'));
		
		/* Iterate each item in response */
		for (String item : serverstatelist) {
			String[] keyValue = StringUtils.split(item, '=');
			int value = 0;
			
			if (keyValue.length != 2) {
				log.warn("Key-Value splitting on '=' doesn't return 2 items: {}.", item);
				continue;
			}
			
			String key = keyValue[0];
			String textvalue = keyValue[1];
			textvalue = StringUtils.removeEnd(textvalue, "s");
			
			if (!NumberUtils.isDigits(textvalue)) {
				log.warn("Key-Value where value is non-numeric: {}", item);
				continue;
			} else {
				value = NumberUtils.toInt(textvalue);
			}
			
			result.put(key, value);
		}
		
		return result;
	}
	
	public static Map<String, String> StringResponseToStringMap(String response) {
		// TODO: mock
		return null;
	}

	/**
	 * @param responseBody
	 * @return
	 */
	public static CaptchaSolutionResponse captchaSolveToCaptchaSolutionResponse(
			String response) {
		CaptchaSolutionResponse csr = new CaptchaSolutionResponse();
		csr.setAccepted(false);
		csr.setCredits(0);
		
		if (StringUtils.isEmpty(response)) {
			RequestToURI.log.debug("Content ist leer!");
			
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
			RequestToURI.log.error("Unknown response for solve request! {}.", response);
			csr.setAccepted(false);
			csr.setCredits(0);
		}
		
		return csr;
	}

	/**
	 * @param response
	 * @return
	 */
	private static int parseCredits(String response) {
		int credits = 0;
		
		if (StringUtils.isEmpty(response)) {
			return credits;
		}
		
		String[] splitresponse = StringUtils.splitPreserveAllTokens(response, '|');
		
		if (splitresponse.length != 2) {
			log.error("Response doesn't contain two items: {}.", 
					ToStringBuilder.reflectionToString(splitresponse));
			return credits;
		}
		
		// TODO: Enum for numbered field.
		String stringCredits = splitresponse[1];
		
		if (NumberUtils.isDigits(stringCredits)) {
			credits = NumberUtils.toInt(stringCredits, 0);
			log.debug("Found reward: {} credits.", credits);
		}
		
		return credits;
	}

	/**
	 * Parses the content from the response, if any.
	 * @param response
	 * @return
	 */
	public static CaptchaReturn captchaGetResponseToCaptchaReturn(
			String response) {
		CaptchaReturn cr = null;
		
		if (StringUtils.isEmpty(response)) {
			RequestToURI.log.debug("Content ist leer!");
			
			return cr;
		}
		
		/* converting answer to object */
		if (StringUtils.containsIgnoreCase(response, "NO CAPTCHA")) {
			/* No captcha available */
			cr = null;
			RequestToURI.log.debug("No captcha available atm: {}.", response);
		} else if (StringUtils.contains(response, "phrase")) {
			/* Extended Answer */
			CaptchaReturnExtended cre = ResponseUtils.getExtendedFromResponse(response);
			RequestToURI.log.debug("CRE: {}.", cre);
			cr = cre;
		} else {
			/* 
			 * simple response contains only digits or few extra information
			 * INT or INT|mouse or INT|confirm
			 */
			RequestToURI.log.debug("Simple response: {}.", response);
			String[] splitresponse = StringUtils.split(response, '|');
			
			if (splitresponse.length < 1) {
				RequestToURI.log.warn("Simple response doesn't contain enough items");
				return cr;
			}
			
			if (!NumberUtils.isDigits(splitresponse[0])) {
				RequestToURI.log.error("Response's first item isn't a captcha id."
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
	 * @param response
	 * @return
	 */
	private static CaptchaReturnExtended getExtendedFromResponse(String response) {
		/* 
		 * Extended response contains phrase keyword
		 * ID|text|confirm|antwort|mouse=0|phrase=0|numeric=0|math=0|min_len=1|max_len=20|confirm=1|w|h|
		 * 11837102|text|||mouse=0|phrase=1|numeric=0|math=0|min_len=5|max_len=0|confirm=0|300|57|userstart=1387447122|startdate=1387447119|serverdate=1387447122|maxtimeout=35
		 */
		RequestToURI.log.debug("Extended response: {}.", response);
		String[] splitresponse = StringUtils.splitPreserveAllTokens(response, '|');
		
		RequestToURI.log.debug("Splitresponse: {}.", 
				ToStringBuilder.reflectionToString(
						splitresponse, 
						ToStringStyle.MULTI_LINE_STYLE)
		);
		
		/* Check item count */
		if (splitresponse.length < EXTENDED_ANSWER_MINLENGTH) {
			RequestToURI.log.warn("Extended response doesn't contain enough items");
			return null;
		}
		
		/* check first item is digits */
		if (!NumberUtils.isDigits(splitresponse[0])) {
			RequestToURI.log.error("Response's first item isn't a captcha id."
					+ " Found {} instead.", splitresponse[0]);
			return null;
		}
		
		/* Now create captcha extended item and fill it */
		CaptchaReturnExtended cre = new CaptchaReturnExtended();
		cre.setCaptchaID(splitresponse[CaptchaReturn.Field.ID.getPosition()]);
		
		/* if text returned, set text */
		if (StringUtils.equals(splitresponse[CaptchaReturn.Field.TEXT.getPosition()], "text")) {
			RequestToURI.log.debug("Setting text captcha.");
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
