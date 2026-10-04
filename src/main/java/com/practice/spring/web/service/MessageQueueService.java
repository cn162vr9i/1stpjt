package com.practice.spring.web.service;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.practice.spring.web.entity.EmailMessageDTO;

@Component
public class MessageQueueService {

	@JmsListener(destination = "${my.queue.destination}")
	public void receiveMessage(final EmailMessageDTO message) {
		System.out.println("★★★★★recieved message:" + message.toString());
	}
}