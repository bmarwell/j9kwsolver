package de.bmarwell.j9kwsolver.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponseUtils {
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
				log.warn("Key-Value bei Split auf '=' nicht 2: {}.", item);
				continue;
			}
			
			if (!NumberUtils.isDigits(keyValue[1])) {
				log.warn("Key-Value hat nicht-nummerischen Wert: {}", item);
				continue;
			} else {
				value = NumberUtils.toInt(keyValue[1]);
			}
			
			result.put(keyValue[0], value);
		}
		
		return result;
	}
	
	public static Map<String, String> StringResponseToStringMap(String response) {
		// TODO: mock
		return null;
	}
}
