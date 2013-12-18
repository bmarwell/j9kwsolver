/**
 * Copyright (c) 2010-2012, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
 */
package de.bmarwell.j9kwsolver;

import java.net.URI;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.bmarwell.j9kwsolver.request.UserBalance;
import de.bmarwell.j9kwsolver.service.PropertyService;
import de.bmarwell.j9kwsolver.util.HttpConnectorFactory;
import de.bmarwell.j9kwsolver.util.RequestToURI;

/**
 * @author Benjamin Marwell
 *
 */
public class J9kwUserAPI {
	private static final Logger log = LoggerFactory.getLogger(J9kwUserAPI.class);
	private static Lock httpLock = new ReentrantLock();
	
	/**
	 * Empty hidden default constructor
	 */
	private J9kwUserAPI() {}
	
	
	
	/**
	 * Locks the instance.
	 */
	private void lock() {
		httpLock.lock();
	}
	
	/**
	 * unlocks the instance.
	 */
	private void unlock() {
		httpLock.unlock();
	}
	
	/**
	 * Gets the apikey users's balance.
	 * @return - the balance in credits.
	 */
	public int getBalance() {
		int balance = 0;
		
		UserBalance ub = new UserBalance();
		ub.setApikey(PropertyService.getProperty("apikey"));
		
		lock();
		URI scuri = RequestToURI.UserBalanceToURI(ub);
		String userbalanceresponse = HttpConnectorFactory.getBodyFromRequest(scuri);
		unlock();
		
		if (NumberUtils.isDigits(userbalanceresponse)) {
			balance = NumberUtils.toInt(userbalanceresponse);
		}
		
		return balance;
	}
	
	/**
	 * Returns the only instance of this api.
	 * @return instance of this API.
	 */
	public static J9kwUserAPI getInstance() {
		return SingletonHolder.instance;
	}
	
	/**
	 * @author Benjamin Marwell
	 *
	 */
	private static class SingletonHolder {
		private static J9kwUserAPI instance = new J9kwUserAPI();
	}
}
