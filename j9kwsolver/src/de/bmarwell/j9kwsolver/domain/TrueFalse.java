package de.bmarwell.j9kwsolver.domain;

public enum TrueFalse {
	TRUE(1),
	FALSE(0);
	
	private int code;
	
	private TrueFalse(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return this.code;
	}
}
