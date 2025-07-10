package com.practice.spring.web.common;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@WebListener
@Component
@Slf4j
public class SessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		HttpSessionListener.super.sessionCreated(se);
		log.debug("-------------< session create ID-[{}]>-------------", se.getSession().getId());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		log.debug("-------------< kill session {}>-------------", se.getSession().getId());
		HttpSessionListener.super.sessionDestroyed(se);
		HttpSession session = se.getSession();
		ConnectionHolderInstHandler.remove(session);
	}
}
