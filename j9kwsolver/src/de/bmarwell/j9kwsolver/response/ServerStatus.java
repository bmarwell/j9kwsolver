package de.bmarwell.j9kwsolver.response;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ServerStatus {
	private int worker;
	private int avg24h;
	private int avg1h;
	private int avg15m;
	private int avg5m;
	private int inwork;
	private int queue;
	
	public int getWorker() {
		return worker;
	}
	public void setWorker(int worker) {
		this.worker = worker;
	}
	public int getAvg24h() {
		return avg24h;
	}
	public void setAvg24h(int avg24h) {
		this.avg24h = avg24h;
	}
	public int getAvg1h() {
		return avg1h;
	}
	public void setAvg1h(int avg1h) {
		this.avg1h = avg1h;
	}
	public int getAvg15m() {
		return avg15m;
	}
	public void setAvg15m(int avg15m) {
		this.avg15m = avg15m;
	}
	public int getAvg5m() {
		return avg5m;
	}
	public void setAvg5m(int avg5m) {
		this.avg5m = avg5m;
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
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	
}
