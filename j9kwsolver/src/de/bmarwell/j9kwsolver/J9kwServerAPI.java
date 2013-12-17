/**
 * Copyright (c) 2010-2012, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
 */
package de.bmarwell.j9kwsolver;

import java.net.URI;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.http.impl.client.CloseableHttpClient;

import de.bmarwell.j9kwsolver.request.ServerCheck;
import de.bmarwell.j9kwsolver.response.ServerStatus;
import de.bmarwell.j9kwsolver.util.HttpConnectorFactory;
import de.bmarwell.j9kwsolver.util.RequestToURI;

public class J9kwServerAPI {
	private static Lock httpLock = new ReentrantLock();
	
	/**
	 * Empty hidden default constructor
	 */
	private J9kwServerAPI() {}
	
	
	
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
	
	public ServerStatus getServerStatus() {
		ServerCheck sc = new ServerCheck();
		ServerStatus ss = new ServerStatus();
		String serverstate = null;

		httpLock.lock();
		
		URI scuri = RequestToURI.ServerStatusToURI(sc);
		serverstate = HttpConnectorFactory.getBodyFromRequest(scuri);
		httpLock.unlock();
		
		// TODO: Parse;
		
		return ss;
	}
	
	/**
	 * @return
	 */
	public static J9kwServerAPI getInstance() {
		return SingletonHolder.instance;
	}
	
	/**
	 * @author Benjamin Marwell
	 *
	 */
	private static class SingletonHolder {
		private static J9kwServerAPI instance = new J9kwServerAPI();
	}

}
