/**
 * Copyright (c) 2010-2012, Benjamin Marwell.  This file is
 * licensed under the Affero General Public License version 3 or later.  See
 * the COPYRIGHT file.
 */
package de.bmarwell.j9kwsolver.response;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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
	public void setWorker(int worker) {
		this.worker = worker;
	}
	public int getInwork() {
		return inwork;
	}
	public void setInwork(int inwork) {
		this.inwork = inwork;
	}
	public int getQueue() {
		return queue;
	}
	public void setQueue(int queue) {
		this.queue = queue;
	}
	
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
}
