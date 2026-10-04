package com.practice.spring.web.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.practice.spring.web.entity.EmailMessageDTO;

@Service
public class JmsMessageProducer {

	private final JmsTemplate jmsTmplt;

	@Value("${my.queue.destination}")
	private String destination;
	
	public JmsMessageProducer(final JmsTemplate jmsTmplt) {
		this.jmsTmplt = jmsTmplt;
	}

	public void send(EmailMessageDTO msg) {
		jmsTmplt.convertAndSend("sample-queue", msg);
		System.out.println("Sent message:" + msg.getBody());
	}
}
