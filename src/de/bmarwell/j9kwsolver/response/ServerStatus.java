/**
 * Copyright (c) 2010-2012, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
 */
package de.bmarwell.j9kwsolver.response;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * Server response object for server status.
 * @author Benjamin Marwell
 *
 */
public class ServerStatus {
	private int worker;
	private int inwork;
	private int queue;
	
	public int getWorker() {
		return worker;
	}
	public void setWorker(final int pWorker) {
		this.worker = pWorker;
	}
	public int getInwork() {
		return inwork;
	}
	public void setInwork(final int pInwork) {
		this.inwork = pInwork;
	}
	public int getQueue() {
		return queue;
	}
	public void setQueue(final int pQueue) {
		this.queue = pQueue;
	}
	
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
}
