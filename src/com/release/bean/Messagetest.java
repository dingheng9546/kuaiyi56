package com.release.bean;


public class Messagetest {

	private String message;
	private int status;

	public Messagetest(String message, int status) {
		super();
		this.message = message;
		this.status = status;

	}

	@Override
	public String toString() {
		return "status =" + status + ", message=" + message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Messagetest() {
		// TODO Auto-generated constructor stub
	}

}
