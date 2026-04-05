package com.practice.spring.web.common;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@WebListener
@Component
@Slf4j
public class SessionListener implements HttpSessionListener, ServletContextListener {

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

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO 自動生成されたメソッド・スタブ
		ServletContextListener.super.contextDestroyed(sce);
	}
}
