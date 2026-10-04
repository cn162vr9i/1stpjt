package com.practice.spring.web.entity;

import java.io.Serializable;

public class EmailMessageDTO implements Serializable {

	private String to;
	private String body;

	public EmailMessageDTO(final String to, final String body) {
		this.body = body;
		this.to = to;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "To:" + this.to + "Body:" + this.body;
	}
}
