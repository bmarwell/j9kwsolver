/**
 * Copyright (c) 2013, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
 */
package de.bmarwell.j9kwsolver.request;

/**
 * @author Benjamin Marwell
 *
 */
public interface ApiKeyRequest {

	/**
	 * Returns the 9kw API Key for this request.
	 * @return the API key or null if not set.
	 */
	String getApikey();
	
	/**
	 * Sets the 9kw API key for this request.
	 * @param pApikey the API Key to set.
	 */
	void setApikey(String pApikey);

}
