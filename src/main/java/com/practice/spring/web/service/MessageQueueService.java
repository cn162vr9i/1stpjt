package com.practice.spring.web.service;

import org.springframework.jms.annotation.JmsListener;

import com.practice.spring.web.entity.EmailMessageDTO;

public class MessageQueueService {

	@JmsListener(destination = "${my.queue.destination}")
	public void receiveMessage(final EmailMessageDTO message) {
		
		
		System.out.println(message.toString());
	}

}
