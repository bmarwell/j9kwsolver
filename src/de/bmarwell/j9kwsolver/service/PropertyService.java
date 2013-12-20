/**
 * Copyright (c) 2010-2012, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
 */
package de.bmarwell.j9kwsolver.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Properties;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Property Singleton Service. 
 * @author Benjamin Marwell
 *
 */
public class PropertyService {
	private static final Logger log = LoggerFactory.getLogger(PropertyService.class); 

	public static String getProperty(String propName) {
		return PropertySingleton.getProperties().getProperty(propName);
	}
	
	
	/**
	 * Inner singleton class.
	 * Easy, threadsafe, fast.
	 * @author Benjamin Marwell
	 *
	 */
	private static class PropertySingleton {
		private static final Properties PROPS = initializeProperties();
		private static final String APIKEY_FILE_PATH = "/.config/j9kwsolver/apikey";
		
		public static Properties getProperties() {
			return PROPS;
		}
		
		private static Properties initializeProperties() {
			Properties props = new Properties();
			String apiKey = new String();
			Scanner in = null;
			
			String userHome = System.getProperty( "user.home" );
			log.debug("ApiKeyFile = {}.", userHome + APIKEY_FILE_PATH);
			
			try {
				File apikeyFile = new File(userHome, APIKEY_FILE_PATH);
				in = new Scanner(apikeyFile);
				apiKey = in.nextLine();
			} catch (FileNotFoundException e) {
				log.warn("Konnte apikey-File nicht lesen!", e);
			} finally {
				IOUtils.closeQuietly(in);
			}
			
			String debug = System.getProperty("debug", "false");
			
			props.put("apikey", apiKey);
			props.put("toolname", "j9kwsolver");
			props.put("debug", debug);
			
			return props;
		}
	}
}
