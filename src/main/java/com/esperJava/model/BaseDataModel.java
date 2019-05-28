package com.esperJava.model;

public class BaseDataModel {
	private String source_ip;
	private String usr_name;
	private String ec_Activity;
	private String ec_Outcome;
	
	
	public String getSource_ip() {
		return source_ip;
	}
	public void setSource_ip(String source_ip) {
		this.source_ip = source_ip;
	}
	public String getUsr_name() {
		return usr_name;
	}
	public void setUsr_name(String usr_name) {
		this.usr_name = usr_name;
	}
	public String getEc_Activity() {
		return ec_Activity;
	}
	public void setEc_Activity(String ec_Activity) {
		this.ec_Activity = ec_Activity;
	}
	public String getEc_Outcome() {
		return ec_Outcome;
	}
	public void setEc_Outcome(String ec_Outcome) {
		this.ec_Outcome = ec_Outcome;
	}

}
