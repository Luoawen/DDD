package cn.m2c.scm.application.utils.expressUtil;

import java.util.HashMap;
import java.util.Map.Entry;


public class TaskRequest {
	private String company;
	private String number;
	private String from;
	private String to;
	private String key;
	private String src;

	private HashMap<String, String> parameters = new HashMap<String, String>();

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public HashMap<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(HashMap<String, String> parameters) {
		this.parameters = parameters;
	}

	@Override
	public String toString() {
		return JacksonHelper.toJSON(this);
	}

}
