package de.bmarwell.j9kwsolver.response;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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
