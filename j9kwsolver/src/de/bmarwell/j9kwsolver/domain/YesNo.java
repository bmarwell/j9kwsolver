package de.bmarwell.j9kwsolver.domain;

public enum YesNo {
	YES("yes"),
	NO("no");
	
	private String yesNoString;
	
	private YesNo(String yesNoString) {
		this.yesNoString = yesNoString;
	}
	
	public String getYesNoString() {
		return this.yesNoString;
	}
	
}
