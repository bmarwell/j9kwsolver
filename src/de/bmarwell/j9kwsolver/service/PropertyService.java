/**
 * Copyright (c) 2013, Benjamin Marwell.  This file is
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
public final class PropertyService {
	/**
	 * The logger instance for this utility class.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(PropertyService.class); 

	/**
	 * Returns the given named property for this application.
	 * @param propName the property name.
	 * @return the value of this property, maybe null if it doesn't exist.
	 */
	public static String getProperty(final String propName) {
		return PropertySingleton.getProperties().getProperty(propName);
	}
	
	/**
	 * Empty private default constructor - utility class.
	 */
	private PropertyService() { }
	
	/**
	 * Inner singleton class.
	 * Easy, threadsafe, fast.
	 * @author Benjamin Marwell
	 *
	 */
	private static class PropertySingleton {
		/**
		 * The properties singleton.
		 */
		private static final Properties PROPS = initializeProperties();
		/**
		 * The unix default search path for the API Key.
		 */
		private static final String APIKEY_FILE_PATH = "/.config/j9kwsolver/apikey";
		
		/**
		 * Returns the properties instance.
		 * @return the properties instance.
		 */
		public static Properties getProperties() {
			return PROPS;
		}
		
		/**
		 * Initializes the property instance.
		 * @return an instance of Properties.
		 */
		private static Properties initializeProperties() {
			Properties props = new Properties();
			String apiKey = new String();
			Scanner in = null;
			
			String userHome = System.getProperty("user.home");
			LOG.debug("ApiKeyFile = {}.", userHome + APIKEY_FILE_PATH);
			
			try {
				File apikeyFile = new File(userHome, APIKEY_FILE_PATH);
				in = new Scanner(apikeyFile);
				apiKey = in.nextLine();
			} catch (FileNotFoundException e) {
				LOG.warn("Konnte apikey-File nicht lesen!", e);
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
