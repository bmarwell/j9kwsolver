/**
 * Copyright (c) 2010-2012, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
 */
package de.bmarwell.j9kwsolver;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class J9kwCaptchaAPI {
	private static Lock httpLock = new ReentrantLock();
	
	/**
	 * Empty hidden default constructor
	 */
	private J9kwCaptchaAPI() {}
	
	
	
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
	 * @return
	 */
	public static J9kwCaptchaAPI getInstance() {
		return SingletonHolder.instance;
	}
	
	/**
	 * @author Benjamin Marwell
	 *
	 */
	private static class SingletonHolder {
		private static J9kwCaptchaAPI instance = new J9kwCaptchaAPI();
	}
}
