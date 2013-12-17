package de.bmarwell.j9kwsolver.request;

public class CaptchaReturn {

	private String captchaID;
	private boolean mouse;
	private boolean confirm;
	
	public String getCaptchaID() {
		return captchaID;
	}
	public void setCaptchaID(String captchaID) {
		this.captchaID = captchaID;
	}
	public boolean isMouse() {
		return mouse;
	}
	public void setMouse(boolean mouse) {
		this.mouse = mouse;
	}
	public boolean isConfirm() {
		return confirm;
	}
	public void setConfirm(boolean confirm) {
		this.confirm = confirm;
	}
	
	
}
